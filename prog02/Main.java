package prog02;

/**
 *
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
             to talk to the user.
      @param pd The PhoneDirectory object to use
             to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You clicked the red x, restarting.");
				break;
			case 0:
				name = ui.getInfo("Enter name");
				if(name == null || name.length() == 0) break;
				number = ui.getInfo("Enter number");
				if(number == null) break;
				oldNumber = pd.addOrChangeEntry(name,number);
				if (oldNumber == null)
					ui.sendMessage(name + " was added to the directory\n" + "New number: " + number);
				else 
					ui.sendMessage("Number for " + name + "was changed\n" + "Old number: " + oldNumber + "\n" + "New number: "+ number);
				break;
			case 1:
				name = ui.getInfo("Enter name");
				if(name == null || name.length() == 0) break;
				else {
					number = pd.lookupEntry(name);
					ui.sendMessage("The number for " + name + " is " + number);
				}
				break;
			case 2:
				name = ui.getInfo("Enter name");
				number = pd.lookupEntry(name);
				if (number == null) break;
				pd.removeEntry(name);
				break;
			case 3:
				pd.save();
				break;
			case 4:
				pd.save();
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}
