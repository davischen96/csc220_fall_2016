package prog07;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DummyList<K extends Comparable<K>, V> extends AbstractMap<K, V> {

	protected static class Node<K extends Comparable<K>, V> implements Map.Entry<K, V> {

		K key;
		V value;
		Node<K, V> next;

		Node(K key, V value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}
	}

	int size = 0;
	protected Node<K, V> dummy = new Node<K, V>(null, null, null);

	protected class Iter implements Iterator<Map.Entry<K, V>> {
		Node<K, V> previous = dummy;

		@Override
		public boolean hasNext() {
			// STEP 5: Replace the following line with the correct answer.
			return previous.next != null;

		}

		@Override
		public Map.Entry<K, V> next() {
			// STEP 5: Move previous forward and replace the following line
			// with the correct answer.
			previous = previous.next;
			return previous;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public boolean containsKey(Object keyAsObject) {
		K key = (K) keyAsObject;
		return containsKey(key, findPrevious(key, dummy));
	}

	protected boolean containsKey(K key, Node<K, V> previous) {
		return previous.next != null && key.equals(previous.next.key);
	}

	protected Node<K, V> findPrevious(K key, Node start) {
		// System.out.print(start.key + " ");
		Node<K, V> node = start;
		while (node.next != null && key.compareTo(node.next.key) > 0) {
			node = node.next;
		}
		return node;
	}

	@Override
	public V get(Object keyAsObject) {
		K key = (K) keyAsObject;
		return get(key, findPrevious(key, dummy));
	}

	protected V get(K key, Node<K, V> previous) {
		if (previous.next != null && previous.next.key.equals(key)) {
			return previous.next.getValue();
		}
		return null;
	}

	@Override
	public V remove(Object keyAsObject) {
		K key = (K) keyAsObject;
		return remove(key, findPrevious(key, dummy));
	}

	protected V remove(K key, Node<K, V> previous) {
		V value = null;
		if (previous != null && previous.next != null && previous.next.key.equals(key)) {
			size--;
			value = previous.next.getValue();
			previous.next = previous.next.next;
		}
		return value;
	}

	@Override
	public V put(K keyAsObject, V value) {
		K key = keyAsObject;
		return put(key, value, findPrevious(key, dummy));
	}

	protected V put(K key, V value, Node<K, V> previous) {
		if (previous.next != null && previous.next.key.equals(key)) {
			V old = previous.next.getValue();
			previous.next.setValue(value);
			return old;
		} else {
			size += 1;
			previous.next = new Node<K, V>(key, value, previous.next);
			return null;
		}

	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	protected class Setter extends AbstractSet<Map.Entry<K, V>> {
		@Override
		public Iterator<Map.Entry<K, V>> iterator() {
			return new Iter();
		}

		@Override
		public int size() {
			return size;
		}
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return new Setter();
	}

	protected void makeTestList() {
		for (int i = 24; i > 0; i -= 2) {
			dummy.next = new Node("" + (char) ('A' + i), i, dummy.next);
			size++;
		}
	}

	public static void main(String[] args) {
		DummyList<String, Integer> map = new DummyList<String, Integer>();
		map.makeTestList();
		System.out.println(map);
		System.out.println("containsKey(A) = " + map.containsKey("A"));
		System.out.println("containsKey(C) = " + map.containsKey("C"));
		System.out.println("containsKey(L) = " + map.containsKey("L"));
		System.out.println("containsKey(M) = " + map.containsKey("M"));
		System.out.println("containsKey(Y) = " + map.containsKey("Y"));
		System.out.println("containsKey(Z) = " + map.containsKey("Z"));

		System.out.println("get(A) = " + map.get("A"));
		System.out.println("get(C) = " + map.get("C"));
		System.out.println("get(L) = " + map.get("L"));
		System.out.println("get(M) = " + map.get("M"));
		System.out.println("get(Y) = " + map.get("Y"));
		System.out.println("get(Z) = " + map.get("Z"));

		System.out.println("remove(A) = " + map.remove("A"));
		System.out.println("remove(C) = " + map.remove("C"));
		System.out.println("remove(L) = " + map.remove("L"));
		System.out.println("remove(M) = " + map.remove("M"));
		System.out.println("remove(Y) = " + map.remove("Y"));
		System.out.println("remove(Z) = " + map.remove("Z"));

		System.out.println(map);

		System.out.println("put(A,7) = " + map.put("A", 7));
		System.out.println("put(A,9) = " + map.put("A", 9));
		System.out.println("put(M,17) = " + map.put("M", 17));
		System.out.println("put(M,19) = " + map.put("M", 19));
		System.out.println("put(Z,3) = " + map.put("Z", 3));
		System.out.println("put(Z,20) = " + map.put("Z", 20));

		System.out.println(map);
	}
}
