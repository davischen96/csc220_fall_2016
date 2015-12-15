package prog02;

public class TestAdd extends SortedPD {
  public static void main (String[] args) {
    TestAdd tr = new TestAdd();
    tr.test();
  }

  DirectoryEntry[] dir = { new DirectoryEntry("A", "a"),
                           new DirectoryEntry("B", "b"),
                           new DirectoryEntry("C", "c"),
                           new DirectoryEntry("D", "d"),
                           new DirectoryEntry("E", "e") };

  public void test () {
    for (int i = 0; i < 5; i++)
      theDirectory[i] = dir[i];
    size = 5;
    
//    System.out.println("print1\n" + toString());

    removeEntry("B");
    
//    System.out.println("print2\n" + toString());

    System.out.println("test addOrChangeEntry");
    String c = addOrChangeEntry("C", "cc");
    
//    System.out.println("print3\n" + toString());
    
    System.out.println("c is " + c);
//	  System.out.println("break");
//	  							System.out.println(c != null);
//	  							System.out.println(c.equals("cc"));
	  							System.out.println(c != null && c.equals("cc"));
//	  System.out.println("break");
    System.out.println(size == 4);
    try {
    	System.out.println("index: " + find("B"));
   // 	System.out.println("index: " + theDirectory[find("B")].getName();
      System.out.println(addOrChangeEntry("B", "bb") == null);
      System.out.println(size == 5);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    String names = "";
    for (int i = 0; i < size; i++)
      if (theDirectory[i] != null)
        names = names + theDirectory[i].getName();
    System.out.println(names.equals("ABCDE"));
    System.out.println(names);
//    	System.out.println("break");
//    			System.out.println(theDirectory.length);
    			System.out.println(theDirectory.length == 5);
//	  System.out.println("break");
    System.out.println(size == 5);
    System.out.println(addOrChangeEntry("F", "f") == null);
    System.out.println(size == 6);
//	  System.out.println("break");
//	  System.out.println(theDirectory.length);
	  		System.out.println(theDirectory.length == 10);
//	  System.out.println("break");
    names = "";
    for (int i = 0; i < size; i++)
      if (theDirectory[i] != null)
        names = names + theDirectory[i].getName();
    System.out.println(names.equals("ABCDEF"));
    System.out.println(names);
  }
}
