package prog02;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {

	@Override
	public String addOrChangeEntry(String name, String number) {
		int index = find(name);
		modified = true;
		if (index < size && theDirectory[index].getName().equals(name)) {
			String oldNumber = theDirectory[index].getNumber();
			theDirectory[index].setNumber(number);
			return oldNumber;
		} else {
			if (size >= theDirectory.length)
				reallocate();
			for (int i = size; i > index; i--) {
				theDirectory[i] = theDirectory[i - 1];
			}

			theDirectory[index] = new DirectoryEntry(name, number);
			size++;

			return null;
		}
	}

	@Override
	protected int find(String name) {
		int first = 0, last = size;

		while (first < last) {
			int middle = (first + last) / 2;
			if (theDirectory[middle].getName().compareTo(name) < 0) {
				first = middle + 1;
			} else {
				last = middle;
			}
		}
		return first;

	}

	@Override
	public String removeEntry(String name) {
		int index = find(name);
		if (index < size && theDirectory[index].getName().equals(name)) {
			DirectoryEntry entry = theDirectory[index];
			for (int i = index; i < size - 1; i++) {
				theDirectory[i] = theDirectory[i + 1];
			}
			size--;
			modified = true;
			return entry.getNumber();
		} else
			return null;
	}

}
