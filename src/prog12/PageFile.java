package prog12;

public class PageFile {
	public final Long index;
	public final String url;
	private int refCount = 0;

	public PageFile(Long index, String url) {
		this.index = index;
		this.url = url;
	}

	public int getRefCount() {
		return refCount;
	}

	public void setRefCount(int ref) {
		refCount = ref;
	}

	public int incRefCount() {
		++refCount;
		System.out.println("inc ref " + index + "(" + url + ")" + refCount);
		return refCount;
	}

	@Override
	public String toString() {
		return index + "(" + url + ")" + refCount;
	}
}
