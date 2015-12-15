package prog06;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import prog02.GUI;

public class WordPath {
	static GUI ui = new GUI();

	private static class Node {
		String word;
		Node previous;

		Node(String word) {
			this.word = word;
		}
	}

	List<Node> nodes = new ArrayList<Node>();

	public static void main(String[] args) {
		WordPath game = new WordPath();

		game.loadDictionary(ui.getInfo("Enter dictionary file:"));
		// game.loadDictionary("words");
		String start = ui.getInfo("Enter starting word:");
		String target = ui.getInfo("Enter target word:");
		if (start == null || start.length() == 0 || target == null || target.length() == 0) {
			ui.sendMessage("You did not enter anything!");
			return;
		}
		String Commands[] = { "Computer Plays.", "Human Plays." };
		int c = ui.getCommand(Commands);
		if (c == 1) {
			game.play(start, target);
		} else {
			game.solve(start, target);
		}

	}

	void play(String start, String target) {
		while (true) {
			ui.sendMessage("Current word: " + start + "\nTarget word: " + target);
			String s = ui.getInfo("Enter the next word: ");
			if (find(s) == null) {
				ui.sendMessage(s + " is not in the dictionary.");
			} else if (!oneDegree(start, s)) {
				ui.sendMessage("Sorry, but " + s + " differs by more than one letter from " + start);
			} else {
				if (s.equals(target)) {
					ui.sendMessage("You win!");
					return;
				}
				start = s;
			}
		}
	}

	void solve(String start, String target) {
		Queue<Node> queue = new LinkedList();

		Node startNode = find(start);
		queue.offer(startNode);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			for (int c = 0; c < nodes.size(); c++) {
				Node next = nodes.get(c);
				if ((next != startNode) && (next.previous == null) && (oneDegree(node.word, next.word))) {
					next.previous = node;
					queue.offer(next);
					if (next.word.equals(target)) {
						ui.sendMessage("Got to " + target + " from " + node.word);
						String s = node.word + "\n" + target;
						while (node != startNode) {
							node = node.previous;
							s = node.word + "\n" + s;
						}
						ui.sendMessage(s);
						return;
					}
				}
			}
		}
	}

	void loadDictionary(String file) {
		try {
			Scanner n = new Scanner(new File(file));
			while (n.hasNextLine()) {
				String word = n.nextLine();
				Node node = new Node(word);
				nodes.add(node);
			}
		} catch (Exception e) {
			ui.sendMessage("Error: " + e);
		}

	}

	Node find(String word) {
		for (int i = 0; i < nodes.size(); i++) {
			if (word.equals((nodes.get(i)).word)) {
				return nodes.get(i);
			}
		}
		return null;
	}

	static boolean oneDegree(String wordOne, String wordTwo) {
		if (wordOne.length() != wordTwo.length()) {
			return false;
		}
		int count = 0;
		for (int i = 0; i < wordOne.length(); i++) {
			if (wordOne.charAt(i) != wordTwo.charAt(i)) {
				count++;
			}
		}
		return count == 1;
	}

}
