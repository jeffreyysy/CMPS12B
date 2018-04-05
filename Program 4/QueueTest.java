//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//February 23, 2018
//test file

public class QueueTest {
  public static void main(String[] args) {
    Queue Q = new Queue();
    System.out.println(Q.isEmpty()); //return true

    //adding new ints
    Q.enqueue((int)1);
    Q.enqueue((int)2);
    Q.enqueue((int)3);
    Q.enqueue((int)4);

    System.out.println("Front: " + Q.peek()); //returns 1, because first item
    System.out.println(Q.isEmpty()); //return false
    System.out.println("length: " + Q.length()); //4, since we added 4
    System.out.println(Q); // prints 1 2 3 4

    //deletes items
    Q.dequeue();
    Q.dequeue();

    System.out.println(Q.isEmpty()); //return false, dequeued the first 2 only
    System.out.println(Q); //prints the last 2 numbers, 3 4

    //deltes the last 2 numbers
    Q.dequeue();
    Q.dequeue();

    System.out.println(Q.isEmpty()); //return true
    System.out.println(Q); //prints nothing

    //since enpty queue, there should be QueueEmptyException
    //Q.dequeueAll();

    // nothing to peek, will return QueueEmptyException
    //System.out.println(Q.peek());

  }
}
