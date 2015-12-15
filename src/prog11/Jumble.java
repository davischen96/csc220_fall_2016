package prog11;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import prog02.GUI;
import prog02.UserInterface;

public class Jumble {
	/**
	 * Sort the letters in a word.
	 * 
	 * @param word
	 *            Input word to be sorted, like "computer".
	 * @return Sorted version of word, like "cemoptru".
	 */
	public static String sort(String word) {
		char[] sorted = new char[word.length()];
		for (int n = 0; n < word.length(); n++) {
			char c = word.charAt(n);
			int i = n;

			while (i > 0 && c < sorted[i - 1]) {
				sorted[i] = sorted[i - 1];
				i--;
			}

			sorted[i] = c;
		}

		return new String(sorted, 0, word.length());
	}

	public static void main(String[] args) {
		UserInterface ui = new GUI();
		// Map<String, String> map = new TreeMap<String, String>();
		// BTree<String, String> map = new BTree<String, String>();
		// Map<String, String> map = new DummyList<String, String>();
		// Map<String, String> map = new SkipList<String, String>();
		// Map<String, String> map = new ChainedHashTable<String, String>();
		// Map<String, String> map = new OpenHashTable<String, String>();
		Map<String, List<String>> map = new OpenHashTable<String, List<String>>();
		// Map<String, List<String>> map = new ChainedHashTable<String,
		// List<String>>();

		Scanner in = null;
		do {
			try {
				in = new Scanner(new File(ui.getInfo("Enter word file.")));
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Try again.");
			}
		} while (in == null);

		int n = 0;
		while (in.hasNextLine()) {
			String word = in.nextLine();
			if (n++ % 1000 == 0)
				System.out.println(word + " sorted is " + sort(word));
			// map.put(sort(word), word);
			// EXERCISE: Insert an entry for word into map.
			// What is the key? What is the value?
			List<String> list = map.get(sort(word));
			if (list == null)
				list = new ArrayList<String>();
			list.add(word);
			map.put(sort(word), list);
		}

		while (true) {
			String jumble = ui.getInfo("Enter jumble.");
			if (jumble == null)
				break;

			// EXERCISE: Look up the jumble in the map.
			// What key do you use?
			// String word = map.get(sort(jumble));
			List<String> word = map.get(sort(jumble));

			if (word == null)
				ui.sendMessage("No match for " + jumble);
			else
				ui.sendMessage(jumble + " unjumbled is " + word);

		}

		int lfirst;
		String sorted;
		String sorted2;
		try {
			sorted = sort(ui.getInfo("Enter letters from clues."));
			lfirst = Integer.parseInt(ui.getInfo("How many letters in the first word?"));
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Try again.");
			return;
		}

		for (String sorted1 : map.keySet()) {
			if (sorted1.length() == lfirst) {
				StringBuilder sb = new StringBuilder(sorted.length());
				int index = 0;
				for (int i = 0; i < sorted.length(); i++) {
					if (index < lfirst && sorted.charAt(i) == sorted1.charAt(index))
						index++;
					else
						sb.append(sorted.charAt(i));
				}
				if (index == sorted1.length()) {
					sorted2 = sb.toString();
					if (map.containsKey(sorted2)) {
						ui.sendMessage(map.get(sorted1) + " " + map.get(sorted2));
					}

				}

			}
		}

	}

}
