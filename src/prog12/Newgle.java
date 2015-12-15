package prog12;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Newgle implements SearchEngine {

	HardDisk<PageFile> pageDisk = new HardDisk<PageFile>();
	PageTrie page2Index = new PageTrie();

	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
	WordTable word2Index = new WordTable();

	@Override
	public void gather(Browser browser, List<String> startingURLs) {

		System.out.println("gather " + startingURLs);

		Queue<Long> urlQueue = new ArrayDeque<Long>();
		addToQueue(urlQueue, startingURLs);

		while (!urlQueue.isEmpty()) {
			Long urlIndex = urlQueue.poll();
			PageFile page = pageDisk.get(urlIndex);
			System.out.println("dequeued " + urlIndex + "(" + page.url + ")" + page.getRefCount());
			if (browser.loadPage(page.url)) {
				List<String> urlsOnPage = browser.getURLs();
				addToQueue(urlQueue, urlsOnPage);

				urlsOnPage.stream().distinct().map(url -> pageDisk.get(page2Index.get(url)))
						.forEach(p -> p.incRefCount());

				List<String> wordsOnPage = browser.getWords().stream().distinct().collect(Collectors.toList());

				wordsOnPage.stream().filter(word -> !word2Index.containsKey(word)).forEach(this::indexWord);
				/*
				 * .forEach(word -> { Long index = this.indexWord(word);
				 * List<Long> pagesWithWord = wordDisk.get(index);
				 * pagesWithWord.add(urlIndex); //
				 * wordDisk.get(index).add(urlIndex); System.out.println(
				 * "add page " + urlIndex + "(" + word + ")" + pagesWithWord);
				 * });
				 */

				wordsOnPage.stream().forEach(word -> {
					List<Long> pagesWithWord = wordDisk.get(word2Index.get(word));
					pagesWithWord.add(urlIndex);
					// wordDisk.get(index).add(urlIndex);
					System.out.println("add page " + word2Index.get(word) + "(" + word + ")" + pagesWithWord);
				});

				// .map(word -> )
				// .forEach(wordList -> {
				// wordList.add(urlIndex)
				// });
			}
		}
		word2Index.write(wordDisk);
		page2Index.write(pageDisk);

	}

	private void addToQueue(Queue q, List<String> toAdd) {
		toAdd.stream().distinct().filter(page -> !page2Index.containsKey(page)).map(this::indexPage).forEach(q::offer);
	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		return new String[0];
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
