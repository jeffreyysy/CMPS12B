//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//March 12, 2018
//test file for Dictionary.c

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"Dictionary.h"

#define MAX_LEN 180

int main(int argc, char* argv[]){
   Dictionary A = newDictionary();
   char* k;
   char* v;
   char* word1[] = {"one","two","three","four","five","six"};
   char* word2[] = {"eleven","twelve","thirteen","fourteen","fifteen","sixteen"};
   int i;

   printf("%s\n", (isEmpty(A)?"true":"false")); //true
   printf("%d\n", size(A)); //0, since its empty

   for(i = 0; i < 6; i++){
      insert(A, word1[i], word2[i]);
   }

   printDictionary(stdout, A);
   printf("%s\n", (isEmpty(A)?"true":"false")); //false, inputed 6 new elements
   printf("%d\n", size(A)); //size: 6

   for(i = 0; i < 6; i++){
      k = word1[i];
      v = lookup(A, k);
      printf("key=\"%s\" %s\"%s\"\n", k, (v==NULL?"not found ":"value="), v);
   }

   //insert(A, "two", "twelve"); // error: duplicate keys

   delete(A, "one");
   delete(A, "four");
   delete(A, "six");

   printDictionary(stdout, A);

   for(i = 0; i < 6; i++){
      k = word1[i];
      v = lookup(A, k);
      printf("key=\"%s\" %s\"%s\"\n", k, (v==NULL?"not found ":"value="), v);
   }

   //delete(A, "five");  // error: key not found

   printf("%s\n", (isEmpty(A)?"true":"false")); //false
   printf("%d\n", size(A)); //3
   makeEmpty(A);
   printf("%s\n", (isEmpty(A)?"true":"false")); //true

   freeDictionary(&A);

   return(EXIT_SUCCESS);
}
