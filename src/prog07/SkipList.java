package prog07;

import java.util.LinkedList;
import java.util.Random;

public class SkipList<K extends Comparable<K>, V> extends DummyList<K, V> {

	private LinkedList<DummyList<K, Node>> lists = new LinkedList<DummyList<K, Node>>();

	SkipList() {
		DummyList<K, Node> list = new DummyList<K, Node>();
		list.dummy.value = dummy;
		lists.add(list);
	}

	static Random random = new Random(15);

	static int extraLevels() {
		int extra = 0;
		// while (Math.random() > 0.5)
		while (random.nextInt() % 2 == 0)
			extra++;
		return extra;
	}

	@Override
	public boolean containsKey(Object keyAsObject) {
		K key = (K) keyAsObject;

		Node start = lists.getFirst().dummy;
		for (DummyList<K, Node> list : lists) {
			Node<K, Node> previous = list.findPrevious(key, start);
			start = previous.value;
		}

		return containsKey(key, findPrevious(key, start));
	}

	@Override
	public V get(Object keyAsObject) {
		K key = (K) keyAsObject;
		Node start = lists.getFirst().dummy;
		for (DummyList<K, Node> list : lists) {
			Node<K, Node> previous = list.findPrevious(key, start);
			start = previous.value;
		}
		return get(key, findPrevious(key, start));
	}

	@Override
	public V remove(Object keyAsObject) {
		K key = (K) keyAsObject;
		Node start = lists.getFirst().dummy;
		for (DummyList<K, Node> list : lists) {
			Node<K, Node> previous = list.findPrevious(key, start);
			start = previous.value;
			if (previous.next != null && previous.next.key.equals(key)) {
				previous.next = previous.next.next;
			}
		}
		return remove(key, findPrevious(key, start));
	}

	@Override
	public V put(K key, V value) {

		Node start = lists.getFirst().dummy;
		for (DummyList<K, Node> list : lists) {
			Node<K, Node> previous = list.findPrevious(key, start);
			start = previous.value;
		}

		if (containsKey(key, findPrevious(key, start))) {
			return put(key, value, findPrevious(key, start));
		}
		int extras = extraLevels();
		while (extras > lists.size()) {
			DummyList<K, Node> list = new DummyList<K, Node>();
			list.dummy.value = lists.getFirst().dummy;
			lists.addFirst(list);
		}
		int levels = lists.size();
		Node<K, Node> keyNode = null;
		start = lists.getFirst().dummy;
		for (DummyList<K, Node> list : lists) {
			Node<K, Node> previous = list.findPrevious(key, start);
			start = previous.value;
			if (levels <= extras) {
				list.put(key, null, previous);
			}
			if (keyNode != null) {
				keyNode.value = previous.next;
			}
			keyNode = previous.next;
			levels--;
		}
		Node<K, V> previous = findPrevious(key, start);
		put(key, value, previous);
		if (keyNode != null) {
			keyNode.value = previous.next;

		}
		return null;

	}

	protected void makeTestLists() {
		lists.clear();
		Node dummy = this.dummy;
		DummyList<K, Node> list;
		do {
			list = new DummyList<K, Node>();
			Node node = dummy;
			Node node2 = list.dummy;
			while (node.next != null && node.next.next != null) {
				node = node.next.next;
				node2.next = new Node<K, Node>((K) node.key, node, null);
				list.size++;
				node2 = node2.next;
			}
			list.dummy.value = dummy;
			dummy = list.dummy;
			if (list.size() > 0 || lists.size() == 0)
				lists.addFirst(list);
		} while (list.size() > 0);
	}

	protected void checkLists() {
		int level = lists.size() + 1;
		for (int i = 0; i < lists.size(); i++) {
			level--;
			Node<K, Node> dummy = lists.get(i).dummy;
			Node dummy2 = i + 1 < lists.size() ? lists.get(i + 1).dummy : this.dummy;
			if (dummy.value == null)
				System.out.println("Dummy in list at level " + level + " has null value.");
			if (dummy.value != dummy2)
				System.out.println("Dummy in list at level " + level + " does not point to dummy in next lower list.");
			for (Node<K, Node> node = dummy.next; node != null; node = node.next) {
				if (node.value == null) {
					System.out.println("Node with key " + node.key + " in list level " + level + " has null value.");
					continue;
				}
				Node node2 = dummy2.next;
				while (node2 != null && node.value != node2)
					node2 = node2.next;
				if (node.value != node2) {
					System.out.println("Node with key " + node.key + " in list level " + level
							+ " does not point to node in next lower list.");
					continue;
				}
				if (node.key != node2.key) {
					System.out.println("Node with key " + node.key + " in list level " + level
							+ " points down to node with key " + node2.key);
				}
			}
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (DummyList<K, Node> list : lists)
			s = s + list.keySet() + "\n";
		return s + super.toString();
	}

	public static void main(String[] args) {
		System.out.println("Testing SkipList");
		SkipList<String, Integer> map = new SkipList<String, Integer>();
		for (int i = 1; i < 26; i++) {
			String key = "" + (char) ('A' + i);
			Integer val = i;
			System.out.println("put(" + key + "," + val + ")=" + map.put(key, val));
		}
		// map.makeTestLists();
		System.out.println(map);
		map.checkLists();

		System.out.println("containsKey(A)=" + map.containsKey("A"));
		System.out.println("containsKey(C)=" + map.containsKey("C"));
		System.out.println("containsKey(L)=" + map.containsKey("L"));
		System.out.println("containsKey(M)=" + map.containsKey("M"));
		System.out.println("containsKey(Z)=" + map.containsKey("Z"));
		System.out.println("containsKey(Zoe)=" + map.containsKey("Zoe"));

		System.out.println("get(A)=" + map.get("A"));
		System.out.println("get(C)=" + map.get("C"));
		System.out.println("get(L)=" + map.get("L"));
		System.out.println("get(M)=" + map.get("M"));
		System.out.println("get(Z)=" + map.get("Z"));
		System.out.println("get(Zoe)=" + map.get("Zoe"));

		System.out.println("remove(A)=" + map.remove("A"));
		System.out.println("remove(C)=" + map.remove("C"));
		System.out.println("remove(L)=" + map.remove("L"));
		System.out.println("remove(M)=" + map.remove("M"));
		System.out.println("remove(Z)=" + map.remove("Z"));
		System.out.println("remove(Zoe)=" + map.remove("Zoe"));

		System.out.println(map);

		System.out.println("put(A,10)=" + map.put("A", 10));
		System.out.println("put(A,11)=" + map.put("A", 11));
		System.out.println("put(M,20)=" + map.put("M", 20));
		System.out.println("put(M,21)=" + map.put("M", 21));
		System.out.println("put(Z,30)=" + map.put("Z", 30));
		System.out.println("put(Z,31)=" + map.put("Z", 31));

		System.out.println(map);
	}
}
