package prog04;

public class TestAdd extends SortedDLLPD {
	public static void main(String[] args) {
		try {
			new TestAdd().test();
			System.out.println("Testing complete. No errors to report.");
		} catch (Error err) {
			System.out.println("ERROR: " + err.getMessage());
		}
	}

	private void test() throws Error {
		final String AaronNumber = "0000";
		final String AdamNumber = "AAAA";
		final String BenNumber = "1111";
		final String BenNewNumber = "NEW_BEN_NUMBER";

		this.addOrChangeEntry("Adam", AdamNumber);

		if (head == null || tail == null) {
			throw new Error(String.format("%s was null even though 'Adam' was added to the list",
					head == null ? "head" : "tail"));
		}

		DLLEntry Adam = find("Adam");
		// if we can't find adam after adding him OR he isn't equal to the
		// head/tail of the list
		if (Adam == null || Adam != head || Adam != tail) {
			throw new Error("Adam was not correctly added as the head/tail of the list!");
		}

		this.addOrChangeEntry("Aaron", AaronNumber);
		DLLEntry Aaron = find("Aaron");
		if (head != Aaron || Aaron == null || Aaron.getPrevious() != null) {
			throw new Error("Aaron was not correctly inserted at the head of the list");
		} else if (Aaron.getNext() != Adam) {
			String name = Aaron.getNext() == null ? "no next" : Aaron.getNext().getName();
			throw new Error("Aaron did not have his next correctly set as Adam; next:" + name);
		} else if (Adam.getPrevious() != Aaron) {
			String name = Adam.getPrevious() == null ? "no previous" : Adam.getPrevious().getName();
			throw new Error("Adam's previous was not set to Aaron; previous" + name);
		}

		this.addOrChangeEntry("Ben", BenNumber);
		DLLEntry Ben = find("Ben");
		if (tail != Ben || Ben == null) {
			throw new Error("Ben was not correctly inserted at the tail");
		} else if (Adam.getNext() != Ben) {
			String name = Adam.getNext() == null ? "no next" : Adam.getNext().getName();
			throw new Error("Adam did not have his next correctly set as Ben; next:" + name);
		} else if (Ben.getPrevious() != Adam) {
			String name = Ben.getPrevious() == null ? "no previous" : Ben.getPrevious().getName();
			throw new Error("Ben's previous was not set to Adam; previous" + name);
		}

		if (!BenNumber.equals(this.addOrChangeEntry("Ben", BenNewNumber))) {
			throw new Error("Ben's number was not correctly changed from old to new");
		}
	}
}
