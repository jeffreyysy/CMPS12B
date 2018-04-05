//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//January 17, 2018
//sorts an array of numbers the same way in various methods and finds the biggest and smallest index number

public class Recursion {
  public static void main(String[] args) {
    //main method same as the given one from instructions
    int[] A = {-1, 2, 6, 12, 9, 2, -5, -2, 8, 5, 7};
    int[] B = new int[A.length];
    int[] C = new int[A.length];
    int minIndex = minArrayIndex(A, 0, A.length - 1);
    int maxIndex = maxArrayIndex(A, 0, A.length - 1);

    for(int x: A) System.out.print(x + " ");
    System.out.println();

    System.out.println( "minIndex = " + minIndex);
    System.out.println( "maxIndex = " + maxIndex);
    reverseArray1(A, A.length, B);
    for(int x: B) System.out.print(x + " ");
    System.out.println();

    reverseArray2(A, A.length, C);
    for(int x: C) System.out.print(x + " ");
    System.out.println();

    reverseArray3(A, 0, A.length - 1);
    for(int x: A) System.out.print(x + " ");
    System.out.println();
  }

  //reverses array by copying array X's left position into the array Y's right position
  public static void reverseArray1(int[] X, int n, int[] Y) {
    if(n == 0) {
      return;
    }
    else {
      Y[n-1] = X[X.length - n];
      reverseArray1(X, n - 1, Y);
    }
  }

  //reverses array by copying array X's right position into the array Y's left position
  public static void reverseArray2(int[] X, int n, int[] Y) {
    if (n == 0) {
      return;
    }
    else {
      Y[Y.length - n] = X[n-1];
      reverseArray2(X, n - 1, Y);
    }
  }

  //reverses the subarray X[i,..,j] consisting of those elements from index i to index j.
  public static void reverseArray3(int[] X, int i, int j) {
    if (j <= i) {
      return;
    }
    else {
      int position = X[i];
      X[i] = X[j];
      X[j] = position;
      reverseArray3(X, i+1, j-1);
    }
  }

  // got some help from https://stackoverflow.com/questions/36456362/find-index-of-max-element
  //returns the index of the maximum element in the subarray X[p,..,r],
  public static int maxArrayIndex(int[] X, int p, int r) {
    int q;
    if (p < r) {
      q = (p + r) / 2;
      int valone = maxArrayIndex(X, p, q);
      int valtwo = maxArrayIndex(X, q+1, r);
      int index = max(X, valone, valtwo);
      return index;
    }
    return r;
  }

  //computes the bigger value from the two subarrays and returns the index of the biggest value
  public static int max(int[] X, int p, int r) {
    int index;
    if(X[p] > X[r]) {
      index = p;
    }
    else {
      index = r;
    }
    return index;
  }

  //returns the index of the minimum element in the subarray X[p,..,r]
  public static int minArrayIndex(int[] X, int p, int r) {
    int q;
    if (p < r) {
      q = (p + r) / 2;
      int valone = minArrayIndex(X, p, q);
      int valtwo = minArrayIndex(X, q+1, r);
      int index = min(X, valone, valtwo);
      return index;
    }
    return r;
  }

  ////computes the smaller value from the two subarrays and returns the index of the smallest value
  public static int min(int[] X, int p, int r) {
    int index;
    if(X[p] < X[r]) {
      index = p;
    }
    else {
      index = r;
    }
    return index;
  }


}
