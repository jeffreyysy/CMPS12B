//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//January 22, 2018
//reads a file and determines if the word is there or not based on the arguments provided

import java.io.*;
import java.util.Scanner;

public class Search {
  public static void main(String[] args) throws IOException{
    Scanner in = null;
    String line = null;
    //int lineCount = 0;

    //check number of command line arguments is at least 2 (one file and at least one word to search)
    if(args.length < 2) {
      System.out.println("Usage: <input file> and search word");
      System.exit(1);
    }
    //opens file
    in = new Scanner(new File(args[0]));
    //read lines from in and gets the total lines in the txt file
    in.useDelimiter("\\Z"); // matches the end of file character
    String s = in.next(); // read in whole file as a single String
    in.close();
    String[] lines = s.split("\n"); // split s into individual lines
    int lineCount = lines.length; //extract length of the resulting array

    String[] token = new String[lineCount];
    int[] lineNumber = new int[lineCount];
    in = new Scanner(new File(args[0]));

    //assigns the String on the line to the corresponding token
    for(int i = 0; i < lineCount; i++){
      line = in.nextLine();
      token[i] = line;
    }

    //assigns the number to the corresponding line
    for(int i = 0; i < lineCount; i++) {
      lineNumber[i] = i + 1;
    }

    //sorts the string array token
    mergeSort(token, lineNumber, 0, lineCount - 1);

    //prints the arguments
    for(int i = 0; i < args.length - 1; i++) {
       System.out.println(binarySearch(token, lineNumber, 0, lineCount - 1, args[i+1]));
    }

    //close FileReverse
    in.close();
  }

  // sort subarray A[p...r]
  public static void mergeSort(String[] word, int[] lineNumber, int p, int r) {
    int q;
    if (p < r) {
      q = (p + r) / 2;
      mergeSort(word, lineNumber, p, q);
      mergeSort(word, lineNumber, q + 1, r);
      merge(word, lineNumber, p, q, r);
    }
  }

  // merges sorted subarrays A[p..q] and A[q+1..r]
  public static void merge(String[] word, int[] lineNumber, int p, int q, int r) {
    int n1 = q - p + 1;
    int n2 = r - q;
    String[] Lword = new String[n1];
    String[] Rword = new String[n2];
    int[] Lnum = new int[n1];
    int[] Rnum = new int[n2];
    int i, j, k;

    for(i = 0; i < n1; i++) {
      Lword[i] = word[p + i];
      Lnum[i] = lineNumber[p + i];
    }

    for(j = 0; j < n2; j++) {
      Rword[j] = word[q + j + 1];
      Rnum[j] = lineNumber[q + j + 1];
    }

    i = 0;
    j = 0;

    for(k = p; k <= r; k++) {
      if(i < n1 && j < n2) {
        if(Lword[i].compareTo(Rword[j]) > 0) {
          word[k] = Lword[i];
          lineNumber[k] = Lnum[i];
          i++;
        }
        else {
          word[k] = Rword[j];
          lineNumber[k] = Rnum[j];
          j++;
        }
      }
      else if(i < n1) {
        word[k] = Lword[i];
        lineNumber[k] = Lnum[i];
        i++;
      }
      else {
        word[k] = Rword[j];
        lineNumber[k] = Rnum[j];
        j++;
      }
    }
  }

  // pre: Array A[p..r] is sorted
  public static String binarySearch(String[] word, int[] lineNumber, int p, int r, String target) {
    int q;
    if(p > r) {
      return target + " not found";
    }
    else {
      q = (p + r) / 2;
      if(word[q].compareTo(target) == 0) {
        return target + " found on line " + lineNumber[q];
      }
      else if (word[q].compareTo(target) < 0) {
        return binarySearch(word, lineNumber, p, q - 1, target);
      }
      else {
        return binarySearch(word, lineNumber, q + 1, r, target);
      }
    }
  }


}
