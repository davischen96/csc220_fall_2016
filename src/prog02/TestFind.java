package prog02;

public class TestFind extends SortedPD {
  public static void main (String[] args) {
    TestFind tr = new TestFind();
    tr.test();
  }

  DirectoryEntry[] dir = { new DirectoryEntry("AA", "a"),
                           new DirectoryEntry("B", "b"),
                           new DirectoryEntry("C", "c"),
                           new DirectoryEntry("D", "d"),
                           new DirectoryEntry("E", "e") };

  public void test () {
    theDirectory = new DirectoryEntry[6];

    for (int i = 0; i < 5; i++)
      theDirectory[i] = dir[i];
    size = 5;

    System.out.println("TestFind");
    try {
      System.out.println(find("A") == 0);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("AA") == 0);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("AAA") == 1);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("B") == 1);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("BB") == 2);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("C") == 2);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("CC") == 3);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("D") == 3);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("DD") == 4);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("E") == 4);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
    try {
      System.out.println(find("EE") == 5);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println(false);
    }
  }
}
