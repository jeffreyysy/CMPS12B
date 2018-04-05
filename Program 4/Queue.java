//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//February 23, 2018
//Queue ADT with nodes

public class Queue implements QueueInterface{
  private class Node {
    Object item;
    Node next;

    Node(Object newItem) {
      item = newItem;
      next = null;
    }
  }
  private Node head;
  private Node tail;
  private int numItems;

  public Queue() {
    head = null;
    tail = null;
    numItems = 0;
  }

  public boolean isEmpty() {
    return (numItems == 0);
  }

  public int length() {
    return numItems;
  }

  public void enqueue(Object newItem) {
    if(!isEmpty()) {
      Node N = head;
      while(N.next != null) {
        N = N.next;
      }
      N.next = new Node(newItem);
    }
    else {
      head = new Node(newItem);
    }
    numItems++;
  }

  public Object dequeue() throws QueueEmptyException {
    if(head == null) {
      throw new QueueEmptyException("empty queue, cannot dequeue");
    }
    else {
      Node N = head;
      head = N.next;
      numItems--;
      return N.item;
    }
  }

  public Object peek() throws QueueEmptyException {
    if(isEmpty()) {
      throw new QueueEmptyException("empty queue, cannot peek");
    }
    else {
      return head.item;
    }
  }

  public void dequeueAll() throws QueueEmptyException {
    if(isEmpty()) {
      throw new QueueEmptyException("empty queue, cannot dequeue all");
    }
    else {
      head = null;
      tail = null;
      numItems = 0;
    }
  }

  public String toString() {
    Node N = head;
    String S = "";
    if(N == null) {
      return "";
    }
    else{
      while(N != null) {
        S = S + N.item + " ";
        N = N.next;
      }
      return S;
    }
  }
}
