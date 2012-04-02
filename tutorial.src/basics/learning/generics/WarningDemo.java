package basics.learning.generics;

public class WarningDemo {
    public static void main(String[] args){
        Box<Integer> bi;
        bi = createBox();
    }

    /**
     * Pretend that this method is part of an old library,
     * written before generics. It returns
     * Box instead of Box<T>.
     */
    static Box createBox(){
        return new Box();
    }
}
