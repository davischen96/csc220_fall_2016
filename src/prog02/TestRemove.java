package prog02;

public class TestRemove extends SortedPD {
  public static void main (String[] args) {
    TestRemove tr = new TestRemove();
    tr.test();
  }

  DirectoryEntry[] dir = { new DirectoryEntry("A", "a"),
                           new DirectoryEntry("B", "b"),
                           new DirectoryEntry("C", "c"),
                           new DirectoryEntry("D", "d"),
                           new DirectoryEntry("E", "e") };

  public void test () {
    System.out.println("test removeEntry");

    for (int i = 0; i < 4; i++)
      theDirectory[i] = dir[i];
    size = 4;
    try {
	  System.out.println("break");
      						System.out.println(removeEntry("CC") == null);
	  System.out.println("break");
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    };

    for (int i = 0; i < 4; i++)
      theDirectory[i] = dir[i];
    size = 4;
    String bnum = removeEntry("B");
    System.out.println(bnum != null && bnum.equals("b"));
    System.out.println(size == 3);
	  System.out.println("break");
	  System.out.println(theDirectory.length);
	  				System.out.println(theDirectory.length == 5);
	  System.out.println("break");
    for (int i = 1; i < size; i++)
      System.out.println(theDirectory[i] == dir[i+1]);

    for (int i = 0; i < 5; i++)
      theDirectory[i] = dir[i];
    size = 5;
    
    try {
      System.out.println(removeEntry("B") == "b");
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    };
    System.out.println(size == 4);
	  System.out.println("break");
	  System.out.println(theDirectory.length);
	  				System.out.println(theDirectory.length == 5);
	  System.out.println("break");
    for (int i = 1; i < 4; i++)
      System.out.println(theDirectory[i] == dir[i+1]);
  }
}
