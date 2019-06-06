package prog11;

import java.io.File;
import java.util.Scanner;

import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
		Map<String, List<String>> map = new ChainedHashTable<String, List<String>>();
		// Map<String,String> map = new DummyList<String,String>();
		// Map<String,String> map = new SkipList<String,String>();

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

			// EXERCISE: Insert an entry for word into map.
			// What is the key? What is the value?
			String sortedWord = sort(word);
			if (!map.containsKey(sortedWord)) {
				List<String> list2 = new ArrayList<String>();
				list2.add(word);
				map.put(sortedWord, list2);
			} else {
				List<String> list2 = map.get(sortedWord);
				list2.add(word);
				map.put(sortedWord, list2);
			}
		}
		while (true) {
			String jumble = ui.getInfo("Enter jumble.");
			if (jumble == null || jumble == "")
				break;
			// EXERCISE: Look up the jumble in the map.
			// What key do you use?
			List<String> myList = map.get(sort(jumble));
			if (myList == null)
				ui.sendMessage("There is no jumble");
			else
				ui.sendMessage("The jumble is " + myList);
			jumble = ui.getInfo("Enter jumble");
		}
		String key2 = null;
		while (true) {
			int leng = -1;
			String word = ui.getInfo("Enter clue word jumble.");
			if (word == null)
				break;
			String clue = sort(word);
			while (leng < 0) {
				String len = ui.getInfo("Length of the first pun word");
				try {
					leng = Integer.parseInt(len);
					if (leng < 0)
						ui.sendMessage("The number is not positive, please enter a positive number");
				} catch (Exception e) {
					ui.sendMessage("Not a number");
				}
				for (String key1 : map.keySet()) {
					if (leng == key1.length()) {
						key2 = check(clue, key1);
						if (key2 != null) {
							if (map.containsKey(key2)) {
								List<String> words1 = map.get(sort(key1));
								List<String> words = map.get(sort(key2));
								ui.sendMessage(clue + " unjumbled could be " + words1 + " and " + words);
							}
						}

					}
				}

			}
		}
	}

	public static String check(String jumble, String keyOne) {
		String key2 = "";
		int clueIndex = 0;
		int keyIndex = 0;
		for (clueIndex = 0; clueIndex < jumble.length(); clueIndex++) {
			if (keyIndex >= keyOne.length()) {
				key2 += jumble.charAt(clueIndex);
			} else {
				if (jumble.charAt(clueIndex) == keyOne.charAt(keyIndex)) {
					keyIndex++;
				} else if (jumble.charAt(clueIndex) < keyOne.charAt(keyIndex)) {
					key2 += jumble.charAt(clueIndex);
				} else {
					return null;
				}
			}
		}
		if (key2.length() != (jumble.length() - keyOne.length()))
			return null;
		else
			return key2;
	}
}
