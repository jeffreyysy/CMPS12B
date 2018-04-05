//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//February 3, 2018
//Dictionary ADT with nodes

public class Dictionary implements DictionaryInterface{
  public class Node {
    String key;
    String val;
    Node next;

    Node(String key, String value) {
      this.key = key;
      this.val = value;
      next = null;
    }
  }
  private Node head;
  private Node curr;
  private int numItems;

  public Dictionary() {
    head = null;
    curr = null;
    numItems = 0;
  }

  private Node findKey(String key) {
    Node N = head;
    while(N != null) {
      if(!N.key.equals(key)) {
        N = N.next;
      }
      else {
        return N;
      }
    }
    return null;
  }

  public boolean isEmpty() {
    return (numItems == 0);
  }

  public int size() {
    return numItems;
  }

  public String lookup(String key){
    Node N = head;
    if(N != null) {
      while(N != null) {
        if(N.key.equals(key)) {
          return N.val;
        }
        N = N.next;
      }
    }
    return null;
  }

  public void insert(String key, String value) {
    if(findKey(key) != null) {
      throw new DuplicateKeyException("cannot insert duplicate keys");
    }
    Node N = new Node(key, value);
    if(head != null) {
      curr.next = N;
      curr = curr.next;
    }
    else {
      head = N;
      curr = N;
    }
    numItems++;
  }

  public void delete(String key) throws KeyNotFoundException {
  	if (lookup(key) == null) {
  		throw new KeyNotFoundException("cannot delete non-existent key");
  	}
    Node N = head;
    if(numItems > 1) {
      if(!N.key.equals(key)) {
        while(!N.next.key.equals(key)) {
          N = N.next;
        }
        N.next = N.next.next;
      }
      else {
        head = N.next;
      }
    }
    else {
      Node H = head;
      head = head.next;
      H.next = null;
    }
    numItems--;
  }

  public void makeEmpty() {
    head = null;
    numItems = 0;
  }

  public String toString() {
    Node N = head;
    String S = "";
    if(N == null) {
      return "";
    }
    else{
      while(N != null) {
        S = S + N.key + " " + N.val + "\n";
        N = N.next;
      }
      return S;
    }
  }

}
