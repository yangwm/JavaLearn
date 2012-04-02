package basics.learning.annotations;

// complier error
/*@Author(
   name = "Benjamin Franklin",
   date = "3/27/2003"
)*/
public class MyClass {

}

interface Closable {
    void close();
}

class File implements Closable {
    @Override
    public void close() {
        //... close this file...
    }
}

