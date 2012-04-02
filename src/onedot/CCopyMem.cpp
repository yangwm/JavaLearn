
int main() 
{
	char *array = new char[640 * 1024 * 1024];
	delete array;
	return 0;
}

/*

D:\study\tempProject\JavaLearn\src\onedot>g++ CCopyMem.cpp -o CCopyMem

D:\study\tempProject\JavaLearn\src\onedot>CCopyMem.exe


*/
