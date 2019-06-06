package prog06;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import prog02.GUI2;

public class WordPath {
    static GUI2 ui = new GUI2();
    List<Node> nodes = new ArrayList<Node>();

    public static void main(String[] args) {
    	WordPath game = new WordPath();
    	game.loadDictionary(ui.getInfo("Enter dictionary file:"));
    	String start = ui.getInfo("Enter starting word:");
        String target = ui.getInfo("Enter target word:");
    	String[] commands = { "Human plays.", "Computer plays." };
        int i = ui.getCommand(commands);
        if (i == 1) {
        	 game.solve(start, target);
        } else {
            game.play(start, target);
        }
    } 
    void play(String start, String target) {
        do {
            ui.sendMessage("Current word: " + start + "\n" + "Target word: " + target);
            String word = ui.getInfo("What is your next word?");
            if (this.find(word) == null) {
                ui.sendMessage(String.valueOf(word) + " is not in the dictionary.");
                continue;
            }
            if (!WordPath.oneDegree(start, word)) {
                ui.sendMessage("Sorry, but " + word + " differs by more than one letter from " + start);
                continue;
            }
            if (word.equals(target)) {
                ui.sendMessage("You win!");
                return;
            }
            start = word;
        } while (true);
    }
    static boolean oneDegree(String snow, String slow) {
        if (snow.length() != slow.length()) {
            return false;
        }
        int count = 0;
        int i = 0;
        while (i < snow.length()) {
            if (snow.charAt(i) != slow.charAt(i)) {
                ++count;
            }
            ++i;
        }
        if (count == 1) {
            return true;
        }
        return false;
    }
    boolean loadDictionary(String file) {
        try {
            Scanner in = new Scanner(new File(file));
            while (in.hasNextLine()) {
            	this.nodes.add(new Node(in.nextLine()));
            }
            return true;
        }
        catch (Exception e) {
            ui.sendMessage("Uh oh: " + e);
            return false;
        }
    }
    private static class Node {
        String word;
        Node previous;

        Node(String word) {
            this.word = word;
        }
    }

    Node find(String word) {
        int i = 0;
        while (i < this.nodes.size()) {
            if (word.equals(this.nodes.get((int)i).word)) {
                return this.nodes.get(i);
            }
            ++i;
        }
        return null;
    }
    void clearAllPrevious() {
        for (int i = 0; i < this.nodes.size(); i++) {
            ((Node) this.nodes.get(i)).previous = null;
        }
    }
    void solve(String start, String target) {
        clearAllPrevious();
        Queue<Node> queue = new LinkedList();
        Node startNode = find(start);
        queue.offer(startNode);
        while (!queue.isEmpty()) {
            Node node = (Node) queue.poll();
            System.out.println("DEQUEUE: " + node.word);
            System.out.print("ENQUEUE:");
            for (int i = 0; i < this.nodes.size(); i++) {
                Node next = (Node) this.nodes.get(i);
                if (next != startNode && next.previous == null && oneDegree(node.word, next.word)) {
                    next.previous = node;
                    queue.offer(next);
                    System.out.print(" " + next.word);
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
            System.out.println();
           }
}
}
