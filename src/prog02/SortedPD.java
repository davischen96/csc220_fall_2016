package prog02;

/**
 *
 * @author dc
 */
public class SortedPD extends ArrayBasedPD {
	@Override
	public String toString () {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append(
				String.format("%d:\t%s\t%s\n", i, theDirectory[i].getName(), theDirectory[i].getNumber())
			);
		}
		return s.toString();
	}
	
	@Override
	public String addOrChangeEntry(String name, String number) {
		/*
		 * 1. let index be the position of where name is/should be 2. if
		 * theDirectory[index].getName() is the name we're looking for. a.
		 * change b. return oldNumber 3. otherwise a. increment size b. if
		 * theDirectory.length is <= size then reallocate c. for (int i = size;
		 * i > index; i--) {theDirectory[i] = theDirectory[i-1]} d.
		 * theDirectory[index] = new DirectoryEntry(name, number); e. return
		 * null
		 */
		int index = find(name);
//		System.out.println("index is " + index);
		modified = true;
		if (index < size && theDirectory[index] != null && theDirectory[index].getName().equals(name)) {
			String oldNumber = theDirectory[index].getNumber();
			theDirectory[index].setNumber(number);
			return oldNumber;
		}
		
		if (size >= theDirectory.length) {
			reallocate();
		}

		for (int i = size; i > index; i--) {
			theDirectory[i] = theDirectory[i - 1];
		}
		
		size += 1;

		theDirectory[index] = new DirectoryEntry(name, number);


		return null;
	}

	/**
	 * Find an entry in the directory.
	 * 
	 * @param name
	 *            The name to be found
	 * @return the index of the entry with that name or, if it is not there,
	 *         where it should be added
	 */
	@Override
	protected int find(String name) {
		int low = 0, high = size - 1;

		while (low <= high) {
			int mid = (low + high) / 2;
			int comp = theDirectory[mid].getName().compareTo(name);

			if (comp < 0) {
				low = mid + 1;
			} else if (comp > 0) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return low;
	}

	/**
	 * Remove an entry from the directory.
	 * 
	 * @param name
	 *            The name of the person to be removed
	 * @return The current number. If not in directory, null is returned
	 */
	/*
	 * 1. (find name) 2. if theDirectory[index].getName() is the name we're
	 * looking for. a. for loop which decreases size (moves everything to the
	 * left) b. size -- c. return the entry that was removed 3. otherwise return
	 * null
	 */
	@Override
	public String removeEntry(String name) {
		int index = find(name);
		
		if (index + 1 >= size) {
			return null;
		}

		
		DirectoryEntry entry = theDirectory[index];
		for (int i = index; i < size - 1; i++) {
			theDirectory[i] = theDirectory[i + 1];
		}
		size--;
		modified = true;
		return entry.getNumber();
	}
}
