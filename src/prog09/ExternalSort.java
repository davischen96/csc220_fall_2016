package prog09;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class ExternalSort {
	static Scanner openIn(File file) {
		try {
			return new Scanner(file);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	static PrintWriter openOut(File file) {
		try {
			return new PrintWriter(file);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	// Read this to make sure you understand file I/O.
	static void copy(String from, String to) {
		File inFile = new File(from);
		Scanner in = openIn(inFile);
		File outFile = new File(to);
		PrintWriter out = openOut(outFile);

		while (in.hasNextLine()) {
			String line = in.nextLine();
			out.println(line);
		}
		in.close();
		out.close();
	}

	// Merge the first n lines of in0 with the first n lines of in1 and
	// write the output to out. in0 or in1 may have less than n lines.
	static void merge(int n, Scanner in0, Scanner in1, PrintWriter out) {
		int a = 0;
		int b = 0;
		String a1 = null;
		String b1 = null;

		while (a < n || b < n || a1 != null || b1 != null) {
			if (a1 == null && a < n) {
				if (in0.hasNextLine()) {
					a1 = in0.nextLine();
				}
				a++;
			}
			if (b1 == null && b < n) {
				if (in1.hasNextLine()) {
					b1 = in1.nextLine();
				}
				b++;
			}
			if (a1 != null && (b1 == null || a1.compareTo(b1) <= 0)) {
				out.println(a1);
				a1 = null;
			} else if (b1 != null) {
				out.println(b1);
				b1 = null;
			}
		}

	}

	static void sort(String from, String to) {
		File inFile = new File(from);
		File outFile = new File(to);

		File[] tmpFile0 = { new File("tmp00"), new File("tmp01") };
		File[] tmpFile1 = { new File("tmp10"), new File("tmp11") };
		File[][] tmpFiles = { tmpFile0, tmpFile1 };

		int size = 0;

		int i = 0;
		int j = 0;

		// Split the input into two temporary file.
		{
			Scanner in = openIn(inFile);
			PrintWriter[] outTmp = { openOut(tmpFiles[i][0]), openOut(tmpFiles[i][1]) };
			while (in.hasNextLine()) {
				String line = in.nextLine();
				outTmp[j].println(line);
				j = 1 - j;
				size++;
			}
			in.close();
			outTmp[0].close();
			outTmp[1].close();
		}

		// Merge groups of 1, 2, 4, 8, ...
		int n = 1;
		while (2 * n < size) {
			Scanner[] inTmp = { openIn(tmpFiles[i][0]), openIn(tmpFiles[i][1]) };
			PrintWriter[] outTmp = { openOut(tmpFiles[1 - i][0]), openOut(tmpFiles[1 - i][1]) };
			while (inTmp[0].hasNextLine() || inTmp[1].hasNextLine()) {
				merge(n, inTmp[0], inTmp[1], outTmp[j]);
				j = 1 - j;
			}
			inTmp[0].close();
			inTmp[1].close();
			outTmp[0].close();
			outTmp[1].close();

			i = 1 - i;
			n *= 2;
		}

		// Merge the two (sorted) temporary files.
		{
			Scanner[] inTmp = { openIn(tmpFiles[i][0]), openIn(tmpFiles[i][1]) };
			PrintWriter out = openOut(outFile);
			merge(n, inTmp[0], inTmp[1], out);
			inTmp[0].close();
			inTmp[1].close();
			out.close();
		}
	}

	public static void main(String[] args) {
		sort("numbers.txt", "sorted.txt");
	}
}
