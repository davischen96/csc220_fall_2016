package prog09;

import java.util.Random;

import prog02.GUI;

public class SortTest<E extends Comparable<E>> {

	public void test(Sorter<E> sorter, E[] array) {
		long start, end;
		double time = 0.0;
		E[] copy = array.clone();
		start = System.nanoTime();
		sorter.sort(copy);
		end = System.nanoTime();
		time = (double) (end - start) / 1000000;
		System.out.println(sorter);
		if (array.length < 101) {

			for (int i = 0; i < copy.length; i++)
				System.out.print(copy[i] + " ");
		}

		// System.out.println();
		System.out.println(time + " ms");
	}

	static GUI ui = new GUI();

	public static void main(String[] args) {
		Integer[] array = { 3, 1, 4, 1, 5, 9, 2, 6 };

		// Scanner reader = new Scanner(System.in);
		// String n = reader.next();
		// args[0] = n;
		int j = 1000;
		while (j < 100000000) {
			if (j > 0) {
				// Print out command line argument if there is one.
				// System.out.println("args[0] = " + args[0]);

				// Create a random object to call random.nextInt() on.
				Random random = new Random(0);

				// Make array.length equal to args[0] and fill it with random
				// integers:

				Integer[] randArray = new Integer[j];
				System.out.println("n = " + j);
				for (int i = 0; i < randArray.length; i++) {
					randArray[i] = random.nextInt();
				}
				SortTest<Integer> tester = new SortTest<Integer>();
				if (j < 1000000) {
					tester.test(new InsertionSort<Integer>(), randArray);
				}
				tester.test(new HeapSort<Integer>(), randArray);
				tester.test(new QuickSort<Integer>(), randArray);
				tester.test(new MergeSort<Integer>(), randArray);

			} else {

				SortTest<Integer> tester = new SortTest<Integer>();
				tester.test(new InsertionSort<Integer>(), array);
				tester.test(new HeapSort<Integer>(), array);
				tester.test(new QuickSort<Integer>(), array);
				tester.test(new MergeSort<Integer>(), array);
			}
			int x = 10;
			j = j * x;
		}
	}
}

class InsertionSort<E extends Comparable<E>> implements Sorter<E> {
	@Override
	public void sort(E[] array) {
		for (int n = 0; n < array.length; n++) {
			E data = array[n];
			int i = n;
			// while array[i-1] > data move array[i-1] to array[i] and
			// decrement i
			while (i > 0 && array[i - 1].compareTo(data) > 0) {
				array[i] = array[i - 1];
				i--;
			}
			array[i] = data;
		}
	}
}

class HeapSort<E extends Comparable<E>> implements Sorter<E> {

	private E[] array;

	private void swap(int i, int j) {
		E data = array[i];
		array[i] = array[j];
		array[j] = data;
	}

	@Override
	public void sort(E[] array) {
		this.array = array;

		for (int i = parent(array.length - 1); i >= 0; i--)
			swapDown(i, array.length - 1);

		for (int n = array.length - 1; n >= 0; n--) {
			swap(0, n);
			swapDown(0, n - 1);
		}
	}

	public void swapDown(int root, int end) {
		// Calculate the left child of root.
		int leftChild = left(root);
		int rightChild;
		int biggerChild;
		// while the left child is still in the array
		while (leftChild <= end) {
			// calculate the right child
			rightChild = right(root);
			// if the right child is in the array and
			// it is bigger than than the left child
			if (rightChild <= end && array[rightChild].compareTo(array[leftChild]) >= 0) {
				// bigger child is right child
				biggerChild = rightChild;
				// else
			} else {
				// bigger child is left child
				biggerChild = leftChild;
			}
			// if the root is not less than the bigger child
			if (root >= biggerChild) {
				// return
				return;
			}
			// swap the root with the bigger child
			swap(root, biggerChild);
			// update root and calculate left child
			root = biggerChild;
			leftChild = left(root);
		}
	}

	private int left(int i) {
		return 2 * i + 1;
	}

	private int right(int i) {
		return 2 * i + 2;
	}

	private int parent(int i) {
		return (i - 1) / 2;
	}
}

class QuickSort<E extends Comparable<E>> implements Sorter<E> {

	private E[] array;

	private void swap(int i, int j) {
		E data = array[i];
		array[i] = array[j];
		array[j] = data;
	}

	@Override
	public void sort(E[] array) {
		this.array = array;
		sort(0, array.length - 1);
	}

	private void sort(int left, int right) {
		if (left >= right)
			return;

		swap(left, (left + right) / 2);

		E pivot = array[left];
		int a = left + 1;
		int b = right;
		while (a <= b) {
			// Move a forward if array[a] <= pivot
			if (array[a].compareTo(pivot) <= 0) {
				a++;
			} else if (array[b].compareTo(pivot) > 0) {
				// Move b backward if array[b] > pivot
				b--;
			} else {
				// Otherwise swap array[a] and array[b]
				swap(a, b);
			}
		}

		swap(left, b);

		sort(left, b - 1);
		sort(b + 1, right);
	}
}

class MergeSort<E extends Comparable<E>> implements Sorter<E> {

	private E[] array, array2;

	@Override
	public void sort(E[] array) {
		this.array = array;
		array2 = array.clone();
		sort(0, array.length - 1);
	}

	private void sort(int left, int right) {
		if (left >= right)
			return;

		int middle = (left + right) / 2;
		sort(left, middle);
		sort(middle + 1, right);

		int i = left;
		int a = left;
		int b = middle + 1;
		while (a <= middle || b <= right) {
			if (a <= middle && b <= right) {
				// If both a <= middle and b <= right
				// copy the smaller of array[a] or array[b] to array2[i]
				if (array[a].compareTo(array[b]) <= 0) {
					array2[i++] = array[a++];
				} else {
					array2[i++] = array[b++];
				}
			} else if (b <= right) {
				array2[i++] = array[b++];
			} else if (a <= middle) {
				array2[i++] = array[a++];
			}
			// Otherwise just copy the remaining elements to array2
		}

		System.arraycopy(array2, left, array, left, right - left + 1);
	}
}
