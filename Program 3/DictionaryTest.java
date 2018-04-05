//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//February 3, 2018
//Dictionary test file

public class DictionaryTest {
  public static void main(String[] args) {
    Dictionary A = new Dictionary();

    //testing isEmpty()
    System.out.println("testing isEmpty()");
    System.out.println("True or False, the dictionary is empty: " + A.isEmpty()); //return true
    System.out.println();

    //testing size()
    System.out.println("testing size()");
    System.out.println("The size of the dictionary is: " + A.size()); //return 0
    System.out.println();

    //testing insert()
    System.out.println("testing insert(), testing isEmpty(), testing size()");
    A.insert("1","z");
    A.insert("2","y");
    A.insert("3","x");
    A.insert("4","w");
    A.insert("5","v");
    A.insert("6","a");
    A.insert("7","b");
    A.insert("8","c");
    //A.insert("6","d"); //will cause a duplicate key exception
    System.out.println(A); //return 8 keys and values
    System.out.println("True or False, the dictionary is empty: " + A.isEmpty()); //return false
    System.out.println("The size of the dictionary is: " + A.size()); //return 8
    System.out.println();

    //testing lookup()
    String v;
    System.out.println("testing lookup()");
    v = A.lookup("1");
    System.out.println("key=1 "+(v==null?"not found":("value="+v))); //return key=1 value=z
    v = A.lookup("6");
    System.out.println("key=6 "+(v==null?"not found":("value="+v))); //return key=6 value=a
    v = A.lookup("20");
    System.out.println("key=20 "+(v==null?"not found":("value="+v))); //return key=20 not found
    System.out.println();

    //testing delete()
    System.out.println("testing delete(), testing isEmpty(), testing size()");
    A.delete("2");
    A.delete("4");
    A.delete("6");
    //A.delete("20"); //key not found exception
    System.out.println(A); //return 5 keys and values
    System.out.println("True or False, the dictionary is empty: " + A.isEmpty()); //return false
    System.out.println("The size of the dictionary is: " + A.size()); //return 5
    System.out.println();

    //testing makeEmpty()
    System.out.println("test makeEmpty(), testing isEmpty(), testing size()");
    System.out.println("True or False, the dictionary is empty: " + A.isEmpty()); //confirms dictionary is not empty
    System.out.println("The size of the dictionary is: " + A.size()); //confirms dictionary size
    A.makeEmpty(); //makes the dictionary empty
    System.out.println("True or False, the dictionary is empty: " + A.isEmpty()); //returns true
    System.out.println("The size of the dictionary is: " + A.size()); //returns 0
    System.out.println(A); //nothing to print since dictionary is empty

  }
}
