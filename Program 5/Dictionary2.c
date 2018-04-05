//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//March 12, 2018
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "Dictionary.h"

const int tableSize = 101;

typedef struct NodeObj {
  char* key;
  char* value;
  struct NodeObj* next;
}
NodeObj;

typedef NodeObj* Node;

Node newNode(char* k, char* v) {
  Node N = malloc(sizeof(NodeObj));
  assert(N!=NULL);
  N->key = k;
  N->value = v;
  N->next = NULL;
  return(N);
}

void freeNode(Node* pN) {
  if( pN!=NULL && *pN!=NULL) {
    free(*pN);
    *pN = NULL;
  }
}

typedef struct DictionaryObj {
  Node* table;
  int numItems;
}
DictionaryObj;
typedef DictionaryObj* Dictionary;

void deleteAll(Node N) {
  if(N != NULL) {
    deleteAll(N->next);
    freeNode(&N);
  }
}

Dictionary newDictionary(void) {
  Dictionary D = malloc(sizeof(DictionaryObj));
  assert(D!=NULL);
  D->table = calloc(tableSize, sizeof(DictionaryObj));
  assert(D->table != NULL);
  D->numItems = 0;
  return D;
}

void freeDictionary(Dictionary* pD) {
  if(pD != NULL && *pD != NULL) {
    makeEmpty(*pD);
    free((*pD)->table);
    free(*pD);
    *pD = NULL;
  }
}

unsigned int rotate_left(unsigned int value, int shift) {
  int sizeInBits = 8*sizeof(unsigned int);
  shift = shift & (sizeInBits - 1);
  if ( shift == 0)
    return value;
  return (value <<shift) | (value >> (sizeInBits - shift));
}

unsigned int pre_hash(char* input) {
  unsigned int result = 0xBAE86554;
  while(*input) {
    result ^= *input++;
    result = rotate_left(result, 5);
  }
  return result;
}

int hash(char* key) {
  return pre_hash(key)%tableSize;
}

Node findKey(Dictionary D, char* k) {
  Node N;
  N = D->table[hash(k)];
  while(N != NULL) {
    if(strcmp(N->key,k) == 0) {
      break;
    }
    N = N->next;
  }
  return N;
}

int isEmpty(Dictionary D) {
  if( D == NULL) {
    fprintf(stderr, "Dictionary Error: calling isEmpty() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  return(D->numItems==0);
}

int size(Dictionary D) {
  if( D == NULL) {
    fprintf(stderr, "Dictionary Error: calling size() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  return(D->numItems);
}

char* lookup(Dictionary D, char* k){
  if(D == NULL) {
    fprintf(stderr, "Dictionary Error: calling lookup() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }

  if(findKey(D,k) != NULL) {
    return findKey(D,k)->value;
  }
  else {
    return NULL;
  }
}

void insert(Dictionary D, char* k, char* v){
  if(D == NULL) {
    fprintf(stderr, "Dictionary Error: calling insert() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  if(findKey(D, k) != NULL){
    fprintf(stderr, "Dictionary Error: calling insert() on empty Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  if(D->table[hash(k)] != NULL) {
    Node N = D->table[hash(k)];
    while(N->next != NULL) {
      N = N->next;
    }
    N->next = newNode(k,v);
    D->numItems++;
  }
  else {
    Node N = newNode(k, v);
    D->table[hash(k)] = N;
    D->numItems++;
  }
}

void makeEmpty(Dictionary D){
  if(D==NULL){
    fprintf(stderr, "Dictionary Error: calling makeEmpty() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  int i;
  for(i = 0; i < tableSize; i++) {
    deleteAll(D->table[i]);
    D->table[i] = NULL;
  }
  D->numItems = 0;
}

void delete(Dictionary D, char* k){
  if(D == NULL) {
    fprintf(stderr, "Dictionary Error: calling delete() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  Node N = findKey(D, k);
  Node A;
  if(N==NULL) {
    fprintf(stderr, "Dictionary Error: calling delete() on empty Dictionary reference\n");
    exit(EXIT_FAILURE);
  }

  if(N == D->table[hash(k)]) {
    D->table[hash(k)] = N->next;
    N = NULL;
  }
  else if(N != D->table[hash(k)] && N->next != NULL) {
    A = D->table[hash(k)];
    while(A->next != N) {
      A = A->next;
    }
    A->next = N->next;
  }
  else {
    N = NULL;
  }
  freeNode(&N);
  D->numItems--;
}

void printDictionary(FILE* out, Dictionary D) {
  Node N;
  if(D == NULL) {
    fprintf(stderr, "Dictionary Error: calling printDictionary() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  int i;
  for(i = 0; i < tableSize; i++) {
    N= D->table[i];
    while(N != NULL) {
      fprintf(out, "%s %s \n", N->key, N->value);
      N = N->next;
    }
  }

}
