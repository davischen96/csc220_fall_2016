package prog12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Newgle2 implements SearchEngine {

	HardDisk<PageFile> pageDisk = new HardDisk<PageFile>();
	PageTrie page2Index = new PageTrie();

	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
	WordTable word2Index = new WordTable();

	@Override
	public void gather(Browser browser, List<String> startingURLs) {

		word2Index.read(wordDisk);
		page2Index.read(pageDisk);

	}

	public class PageComparator implements Comparator<Long> {
		// pass to the PriorityQueue of matching page indices.
		@Override
		public int compare(Long urlRefs1, Long urlRefs2) {
			return pageDisk.get(urlRefs1).getRefCount() - pageDisk.get(urlRefs2).getRefCount();
		}
	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		// Iterator into list of page ids for each key word.
		Iterator<Long>[] pageIndexIterators = new Iterator[keyWords.size()];

		// Current page index in each list, just ``behind'' the iterator.
		long[] currentPageIndices = new long[pageIndexIterators.length];

		// LEAST popular page is at top of heap so if heap has numResults
		// elements and the next match is better than the least popular page
		// in the queue, the least popular page can be thrown away.
		PriorityQueue<Long> bestPageIndices = new PriorityQueue<Long>(new PageComparator());

		for (int i = 0; i < keyWords.size(); i++) {
			String wordToLookUp = keyWords.get(i);
			Long keyWordIndex = word2Index.get(wordToLookUp);

			if (keyWordIndex != null) {
				List<Long> listOfPageIndices = wordDisk.get(keyWordIndex);
				pageIndexIterators[i] = listOfPageIndices.iterator();
				currentPageIndices[i] = listOfPageIndices.get(0);
			} else {
				return new String[] { "No results found" };
			}
		}

		while (moveForward(currentPageIndices, pageIndexIterators)) {
			if (allEqual(currentPageIndices)) {
				bestPageIndices.offer(currentPageIndices[0]);
			}
		}

		String[] results = new String[10];

		for (int i = results.length - 1; i > -1; i--) {
			Long pageIndex = bestPageIndices.poll();
			if (pageIndex != null) {
				results[i] = pageDisk.get(pageIndex).url;
			}
		}

		return Arrays.stream(results).filter(url -> url != null).toArray(size -> new String[size]);
	}

	/**
	 * If all the currentPageIndices are the same (because are just starting or
	 * just found a match), move them all forward: call next() for each page
	 * index iterator and put the result into current page indices.
	 *
	 * If they are not all the same, don't move the largest one(s) forward.
	 * (There may be more than one equal to the largest index in current page
	 * indices.)
	 *
	 * Return false if hasNext() is false for any iterator.
	 *
	 * @param currentPageIndices
	 *            array of current page indices
	 * @param pageIndexIterators
	 *            array of iterators with next page indices
	 * @return true if all minimum page indices updates, false otherwise
	 */
	private boolean moveForward(long[] currentPageIndices, Iterator<Long>[] pageIndexIterators) {

		if (allEqual(currentPageIndices)) {
			for (int i = 0; i < pageIndexIterators.length; i++) {
				if (pageIndexIterators[i].hasNext()) {
					currentPageIndices[i] = pageIndexIterators[i].next();
				} else {
					return false;
				}
			}
		} else {
			long max = -1;

			for (int i = 0; i < currentPageIndices.length; i++) {
				if (currentPageIndices[i] > max) {
					max = currentPageIndices[i];
				}
			}

			for (int i = 0; i < pageIndexIterators.length; i++) {
				while (currentPageIndices[i] < max) {
					if (pageIndexIterators[i].hasNext())
						currentPageIndices[i] = pageIndexIterators[i].next();
					else
						return false;
				}
			}
		}
		return true;

	}

	/**
	 * Check if all elements in an array are equal.
	 *
	 * @param currentPageIndices
	 *            an array of numbers
	 * @return true if all are equal, false otherwise
	 */
	private boolean allEqual(long[] currentPageIndices) {
		for (int i = 1; i < currentPageIndices.length; i++) {
			if (currentPageIndices[i] != currentPageIndices[0]) {
				return false;
			}
		}
		// return currentPageIndices.length > 0;
		return true;
	}

	public Long indexWord(String word) {
		Long nextWordIndex = wordDisk.newFile();
		List<Long> wordList = new ArrayList<Long>();

		wordDisk.put(nextWordIndex, wordList);
		word2Index.put(word, nextWordIndex);
		System.out.println("indexing word " + nextWordIndex + "(" + word + ")[]");
		return nextWordIndex;
	}

	public Long indexPage(String url) {
		Long nextIndex = pageDisk.newFile();
		PageFile newPage = new PageFile(nextIndex, url);

		pageDisk.put(nextIndex, newPage);
		page2Index.put(url, nextIndex);
		System.out.println("indexing page " + nextIndex + "(" + url + ")0");
		return nextIndex;
	}

}
