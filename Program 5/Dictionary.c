//Jeffrey Yeung
//jeyyeung
//CMPS 12B
//March 12, 2018
//Dictionary with hash tables/linked list

//structure follows similarly https://users.soe.ucsc.edu/~dustinadams/CMPS12B/examples/Lectures/C_Programs/DictionaryADT/Dictionary.c

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "Dictionary.h"

const int tableSize = 101;

// rotate_left()
// rotate the bits in an unsigned int
unsigned int rotate_left(unsigned int value, int shift) {
  int sizeInBits = 8*sizeof(unsigned int);
  shift = shift & (sizeInBits - 1);
  if ( shift == 0)
    return value;
  return (value <<shift) | (value >> (sizeInBits - shift));
}

// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash(char* input) {
  unsigned int result = 0xBAE86554;
  while(*input) {
    result ^= *input++;
    result = rotate_left(result, 5);
  }
  return result;
}

// hash()
// turns a string into an int in the range 0 to tableSize-1
int hash(char* key) {
  return pre_hash(key)%tableSize;
}

// private types and functions ------------------------------------------------

// NodeObj
typedef struct NodeObj {
  char* key;
  char* value;
  struct NodeObj* next;
}
NodeObj;

// Node
typedef NodeObj* Node;

// newNode()
// constructor for private Node type
Node newNode(char* k, char* v) {
  Node N = malloc(sizeof(NodeObj));
  assert(N!=NULL);
  N->key = k;
  N->value = v;
  N->next = NULL;
  return(N);
}

// freeNode()
// destructor for private Node type
void freeNode(Node* pN) {
  if( pN!=NULL && *pN!=NULL) {
    free(*pN);
    *pN = NULL;
  }
}
// ListObj
typedef struct ListObj{
  Node head;
}
ListObj;

typedef ListObj* List;

List newList(void) {
  List L = malloc(sizeof(ListObj));
  assert(L != NULL);
  L->head = NULL;
  return L;
}

// deleteAll()
// deletes all Nodes in the subtree rooted at N.
void deleteAll(Node N) {
  if(N->next != NULL) {
    deleteAll(N->next);
  }
  freeNode(&N);
}

// DictionaryObj
typedef struct DictionaryObj {
  List table;
  int numItems;
}
DictionaryObj;
typedef DictionaryObj* Dictionary;

// public functions -----------------------------------------------------------

// newDictionary()
// constructor for the Dictionary type
Dictionary newDictionary(void) {
  Dictionary D = malloc(sizeof(DictionaryObj));
  assert(D!=NULL);
  D->table = calloc(tableSize, sizeof(ListObj));
  D->numItems = 0;
  return D;
}

// freeDictionary()
// destructor for the Dictionary type
void freeDictionary(Dictionary* pD){
	if( pD!=NULL && *pD!=NULL ){
		if(!isEmpty(*pD)){
			makeEmpty(*pD);
		}
    free((*pD)->table);
		free(*pD);
		*pD = NULL;
	}
}

// isEmpty()
// returns 1 (true) if D is empty, 0 (false) otherwise
// pre: none
int isEmpty(Dictionary D) {
  if( D == NULL) {
    fprintf(stderr, "Dictionary Error: calling isEmpty() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  return(D->numItems==0);
}

// size()
// returns the number of (key, value) pairs in D
// pre: none
int size(Dictionary D) {
  if( D == NULL) {
    fprintf(stderr, "Dictionary Error: calling size() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  return(D->numItems);
}

// lookup()
// returns the value v such that (k, v) is in D, or returns NULL if no
// such value v exists.
// pre: none
char* lookup(Dictionary D, char* k){
	if(D == NULL) {
    fprintf(stderr, "Dictionary Error: calling lookup() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
	int index = hash(k);
	List L = &D->table[index];
	if(D->table == NULL){
		return NULL;
	}
	Node N = L->head;
	Node H = NULL;
	while(N != NULL) {
		if(N->key == k) {
			H = N;
		}
		N = N->next;
	}
	if(H == NULL) {
		return NULL;
	}
	else {
		return H->value;
	}
}

// insert()
// inserts new (key,value) pair into D
// pre: lookup(D, k)==NULL
void insert(Dictionary D, char* k, char* v){
  if(D == NULL) {
    fprintf(stderr, "Dictionary Error: calling insert() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  if(lookup(D, k) != NULL){
    fprintf(stderr, "Dictionary Error: calling insert() on empty Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
	int index = hash(k);
	List L = &D->table[index];
	Node N = newNode(k, v);
	N->next = L->head;
	L->head = N;
	D->numItems++;
}

// delete()
// deletes pair with the key k
// pre: lookup(D, k)!=NULL
void delete(Dictionary D, char* k) {
	if(D == NULL) {
    fprintf(stderr, "Dictionary Error: calling delete() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
  if(lookup(D, k) == NULL){
    fprintf(stderr, "Dictionary Error: calling delete() on empty Dictionary reference\n");
    exit(EXIT_FAILURE);
  }

	int i, index = hash(k);
	List L = &D->table[index];
	Node prev, curr, N, A = L->head;
	for(i = 0; A != NULL; i++) {
		if(strcmp(A->key, k) == 0) {
			break;
		}
		A = A->next;
	}
	if(A != L->head) {
		N = A;
		prev = L->head;
		curr = L->head->next;
		for(i = 0; curr != N; i++) {
			curr = curr->next;
			prev = prev->next;
		}
		prev->next = N->next;
		N->next = NULL;
	}
	else {
		N = L->head;
		L->head = L->head->next;
		N->next = NULL;
	}
	D->numItems--;
	freeNode(&N);
}

// makeEmpty()
// re-sets D to the empty state.
// pre: none
void makeEmpty(Dictionary D){
  if(D==NULL){
    fprintf(stderr, "Dictionary Error: calling makeEmpty() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
	int i;
	for(i = 0; i < tableSize; i++) {
		List L = &D->table[i];
		if(L->head != NULL) {
			deleteAll(L->head);
		}
	}
  List* pL = &D->table;
  if(pL != NULL && *pL != NULL) {
    free(*pL);
    *pL = NULL;
  }
	D->table = NULL;
	D->numItems = 0;
}

// printDictionary()
// pre: none
// prints a text representation of D to the file pointed to by out
void printDictionary(FILE* out, Dictionary D) {
	if(D == NULL) {
    fprintf(stderr, "Dictionary Error: calling printDictionary() on NULL Dictionary reference\n");
    exit(EXIT_FAILURE);
  }
	int i, j;
	for(i = 0; i < tableSize; i++) {
		List L = &D->table[i];
		Node N = L->head;
		for(j = 0; N != NULL; j++) {
			fprintf(out, "%s %s\n", N->key, N->value);
			N = N->next;
		}
	}
}
