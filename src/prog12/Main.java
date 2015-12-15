package prog12;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		Browser browser = new BetterBrowser();
		// SearchEngine newgle = new Newgle();
		SearchEngine newgle = new Newgle2();

		List<String> startingURLs = new ArrayList<String>();
		// startingURLs.add("http://www.cs.miami.edu");
		// startingURLs.add("http://www.cs.miami.edu/~vjm/csc220/google/mary.html");
		startingURLs.add("http://web.cs.miami.edu/home/jgmaster/google");
		List<String> temp = new ArrayList<String>();

		for (int i = 0; i < startingURLs.size(); i++) {
			temp.add(BetterBrowser.reversePathURL(startingURLs.get(i)));
		}

		startingURLs = temp;

		newgle.gather(browser, startingURLs);

		List<String> keyWords = new ArrayList<String>();
		if (false) {
			keyWords.add("mary");
			keyWords.add("jack");
			keyWords.add("jill");
		} else {
			// keyWords.add("Victor");
			// keyWords.add("Milenkovic");
			keyWords.add("3");
			keyWords.add("5");
			keyWords.add("20");
		}

		String[] urls = newgle.search(keyWords, 10);

		System.out.println("Found " + keyWords + " on");
		for (int i = 0; i < urls.length; i++) {
			// System.out.println(urls[i]);
			System.out.println(BetterBrowser.inverseReversePathURL(urls[i]));
		}
	}
}
