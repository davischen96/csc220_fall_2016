package prog02;

/**
 *
 * @author dc
 */
public class Main {

	/**
	 * Processes user's commands on a phone directory.
	 * 
	 * @param fn
	 *            The file containing the phone directory.
	 * @param ui
	 *            The UserInterface object to use to talk to the user.
	 * @param pd
	 *            The PhoneDirectory object to use to process the phone
	 *            directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = { "Add/Change Entry", "Look Up Entry", "Remove Entry", "Save Directory", "Exit" };

		String name, number;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case 0: // Add/change Entry
				name = ui.getInfo("Enter name");
				if (name == null || name.equals(""))
					break;

				number = pd.lookupEntry(name);
				String newNumber = null;

				while (newNumber == null || "".equals(newNumber)) {
					newNumber = ui.getInfo("Enter number");
				}

				pd.addOrChangeEntry(name, newNumber);

				if (number == null) {
					ui.sendMessage(String.format("%s was added to the directory.\nNew number: %s", name, newNumber));
				} else {
					ui.sendMessage(String.format("Number for %s has been changed.\nOld Number: %s\nNew Number: %s",
							name, number, newNumber));
				}

				break;
			case 1: // Look up entry
				/*
				 * 1. prompt user for name 2. look up number using name 3. if
				 * number is null, tell user 4. otherwise, send back name and
				 * number
				 */
				name = ui.getInfo("Enter name");
				if (name == null || name.equals(""))
					break;

				number = pd.lookupEntry(name);

				if (number == null) {
					ui.sendMessage(String.format("%s is not listed in the directory.", name));
				} else {
					ui.sendMessage(String.format("The number for %s is %s", name, number));
				}

				break;
			case 2: // Remove Entry
				// implement
				/*
				 * prompt user for name look up name if name = null, say name
				 * isn't there else remove name and tell user
				 */
				name = ui.getInfo("Enter name");
				if (name == null || name.equals(""))
					break;

				number = pd.lookupEntry(name);

				if (number == null) {
					ui.sendMessage(String.format("%s is not listed in the directory.", name));
				} else {
					pd.removeEntry(name);
					ui.sendMessage(String.format("Removed entry with name %s and number %s.", name, number));
				}
				break;
			case 3: // Save
				pd.save();
				break;
			case 4: // Exit
				return;
			}
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}
