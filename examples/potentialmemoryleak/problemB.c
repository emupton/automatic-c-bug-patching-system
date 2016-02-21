#include <stdio.h>
#include <stdlib.h>

int main(){
	int *bob = (int *)malloc(sizeof(int));
	int x=5+4;//just test line
	realloc(bob, sizeof(long));
}