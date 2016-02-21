#include <stdio.h>
#include <stdlib.h>

int main(){
	int *p = (int *)malloc(sizeof(int));
	int x=5+4;//just test line
	realloc(p, sizeof(long));
	free(p);
}
