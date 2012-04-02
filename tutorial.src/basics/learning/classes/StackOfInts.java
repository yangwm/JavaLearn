package basics.learning.classes;

public class StackOfInts {
    
    private int[] stack;
    private int next = 0;  // index of last item in stack + 1
    
    public StackOfInts(int size) {
        //create an array large enough to hold the stack
        stack = new int[size];
    }
    
    public void push(int on) {
        if (next < stack.length)
           stack[next++] = on;
    }
    public boolean isEmpty() {
        return (next == 0);
    }
    
    public int pop(){
        if (!isEmpty()) 
           return stack[--next]; // top item on stack
        else
           return 0;
    }
    
    public int getStackSize() {
        return next;
    }
    
    private class StepThrough {         
        // start stepping through at i=0
        private int i = 0; 
        
        // increment index
        public void increment() {
            if ( i < stack.length)
               i++;
        }
        
        // retrieve current element
        public int current() {
            return stack[i];
        }
        
        // last element on stack?
        public boolean isLast(){
            if (i == getStackSize() - 1)
               return true;
            else
               return false;
        }
    }
    
    public StepThrough stepThrough() {
        return new StepThrough();
    }
    
    public static void main(String[] args) {
        
        // instantiate outer class as "stackOne"
        StackOfInts stackOne = new StackOfInts(15);
        
        // populate stackOne
        for (int j = 0 ; j < 15 ; j++) {
            stackOne.push(2*j);
        }
        
        // instantiate inner class as "iterator"
        StepThrough iterator = stackOne.stepThrough();
        // or StepThrough iterator = stackOne.new StepThrough();
        
        // print out stackOne[i], one per line
        while(!iterator.isLast()) {
            System.out.print(iterator.current() + " ");
            iterator.increment();
        }
        System.out.println();
        
    }
    
}
