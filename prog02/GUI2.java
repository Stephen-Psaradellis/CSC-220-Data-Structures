package prog02;

import java.lang.reflect.InvocationTargetException;

import javax.swing.*;

/**
 *
 * @author vjm
 */
public class GUI2 implements UserInterface {

	InnerGUI gui;

	/** Creates a new instance of GUI */
	public GUI2() {
		gui = new InnerGUI();
	}

	private static class InnerGUI implements Runnable {

		public String[] commands;
		public String message;
		public String prompt;

		public int choice;
		public String response;

		public TYPE type;

		public enum TYPE {
			COMMAND, MESSAGE, INFO
		};

		public void getCommand(String[] commands) {
			choice = JOptionPane.showOptionDialog(null, // No parent
					"Select a Command", // Prompt message
					"PhoneDirectory", // Window title
					JOptionPane.YES_NO_CANCEL_OPTION, // Option type
					JOptionPane.QUESTION_MESSAGE, // Message type
					null, // Icon
					commands, // List of commands
					commands[commands.length - 1]);

		}

		public void sendMessage(String message) {
			JOptionPane.showMessageDialog(null, message);
		}

		public void getInfo(String prompt) {
			response = JOptionPane.showInputDialog(prompt);
		}

		public void run() {

			switch (this.type) {
			case COMMAND:
				getCommand(commands);
				break;
			case MESSAGE:
				sendMessage(message);
				break;
			case INFO:
				getInfo(prompt);
				break;

			}

		}
	}

	/**
	 * presents set of commands for user to choose one of
	 * 
	 * @param commands
	 *            the commands to choose from
	 * @return the index of the command in the array
	 */
	public int getCommand(String[] commands) {

		gui.commands = commands;
		gui.type = InnerGUI.TYPE.COMMAND;

		try {
			SwingUtilities.invokeAndWait(gui);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return gui.choice;
	}

	/**
	 * tell the user something
	 * 
	 * @param message
	 *            string to print out to the user
	 */
	public void sendMessage(String message) {

		gui.message = message;
		gui.type = InnerGUI.TYPE.MESSAGE;

		try {
			SwingUtilities.invokeAndWait(gui);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * prompts the user for a string
	 * 
	 * @param prompt
	 *            the request
	 * @return what the user enters, null if nothing
	 */
	public synchronized String getInfo(String prompt) {

		gui.prompt = prompt;
		gui.type = InnerGUI.TYPE.INFO;

		try {
			SwingUtilities.invokeAndWait(gui);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return gui.response;
	}


	public static void main(String[] args) {
		UserInterface ui = new GUI2();
		String[] commands = { "hello", "how", "are", "you" };
		int choice = ui.getCommand(commands);
		ui.sendMessage("You chose " + choice);
		String result = ui.getInfo("say something");
		ui.sendMessage("You said " + result);

	}
}
