package prog04;

/**
 * This is an implementation of the prog02.PhoneDirectory interface that uses a
 * doubly linked list to store the data.
 * 
 * @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
	/**
	 * Add an entry to the directory.
	 * 
	 * @param name
	 *            The name of the new person
	 * @param number
	 *            The number of the new person
	 * @return
	 */
	@Override
	public String addOrChangeEntry(String name, String number) {
		DLLEntry entry = find(name);
		if (entry != null && entry.getName().equals(name)) {
			String oldNumber = entry.getNumber();
			entry.setNumber(number);
			return oldNumber;
		}

		DLLEntry newEntry = new DLLEntry(name, number);
		if (head == null) {
			head = newEntry;
			tail = newEntry;
			return null;
		}

		if (entry == null) {
			tail.setNext(newEntry);
			newEntry.setPrevious(tail);
			tail = newEntry;
		} else if (head == tail || entry == head) {
			newEntry.setNext(head);
			head.setPrevious(newEntry);
			head = newEntry;
		} else {
			newEntry.setNext(entry);
			newEntry.setPrevious(entry.getPrevious());
			entry.getPrevious().setNext(newEntry);
			entry.setPrevious(newEntry);
		}
		return null;
	}

	/**
	 * Find an entry in the directory.
	 * 
	 * @param name
	 *            The name to be found
	 * @return The entry with the same name, or the name after where it should
	 *         be, or null if it is not there.
	 */
	@Override
	protected DLLEntry find(String name) {
		for (DLLEntry entry = head; entry != null; entry = entry.getNext()) {
			int comp = entry.getName().compareTo(name);
			if (comp >= 0)
				return entry;
		}
		return null;
	}

	@Override
	public String removeEntry(String name) {
		// Call find to find the entry to remove.
		DLLEntry entry = find(name);
		// If it is not there, forget it!
		if (entry == null || !entry.getName().equals(name))
			return null;

		DLLEntry next = entry.getNext();
		DLLEntry previous = entry.getPrevious();
		// Get the next entry and the previous entry.
		if (previous == null && next == null) {
			head = null;
			tail = null;
		}
		// Two cases: previous is null (entry is head) or not
		else if (entry.getPrevious() == null) {
			head = next;
			head.setPrevious(null);
			entry.setPrevious((null));
			entry.setNext(null);
		}
		// Two cases: next is null (entry is tail) or not
		else if (entry.getNext() == null) {
			tail = previous;
			tail.setNext(null);
			entry.setPrevious((null));
			entry.setNext(null);
		} else {
			previous.setNext(next);
			next.setPrevious(previous);
			entry.setPrevious((null));
			entry.setNext(null);
		}

		modified = true;
		return entry.getNumber();
	}
}
