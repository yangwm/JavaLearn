
int main() 
{
	long startTime = ;
	char *array = new char[640 * 1024 * 1024];
	delete array;
	long totalTime =  - startTime;

	printf("%d \n", totalTime);

	return 0;
}

/*

D:\study\tempProject\JavaLearn\src\onedot>g++ CCopyMem.cpp -o CCopyMem

D:\study\tempProject\JavaLearn\src\onedot>CCopyMem.exe


*/
