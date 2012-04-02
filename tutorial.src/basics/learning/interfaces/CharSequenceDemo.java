package basics.learning.interfaces;

//CharSequenceDemo presents a String value -- backwards.
public class CharSequenceDemo implements CharSequence {
    private String s;

    public CharSequenceDemo(String s) {
        //It would be much more efficient to just reverse the string
        //in the constructor. But a lot less fun!
        this.s = s;
    }

    //If the string is backwards, the end is the beginning!
    private int fromEnd(int i) {
        return s.length() - 1 - i;
    }

    public char charAt(int i) {
        if ((i < 0) || (i >= s.length())) {
            throw new StringIndexOutOfBoundsException(i);
        }
        return s.charAt(fromEnd(i));
    }

    public int length() {
        return s.length();
    }

    public CharSequence subSequence(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > s.length()) {
            throw new StringIndexOutOfBoundsException(end);
        }
        if (start > end) {
            throw new StringIndexOutOfBoundsException(start - end);
        }
        StringBuilder sub = 
            new StringBuilder(s.subSequence(fromEnd(end), fromEnd(start)));
        return sub.reverse();
    }

    public String toString() {
        StringBuilder s = new StringBuilder(this.s);
        return s.reverse().toString();
    }

    //Random int from 0 to max.
    private static int random(int max) {
        return (int) Math.round(Math.random() * max + 0.5);
    }

    public static void main(String[] args) {
        CharSequenceDemo s =
            new CharSequenceDemo("Write a class that implements the CharSequence interface found in the java.lang package.");

        //exercise charAt() and length()
        for (int i = 0; i < s.length(); i++) {
            System.out.println(s.charAt(i));
        }

        //exercise subSequence() and length();
        int start = random(s.length() - 1);
        int end = random(s.length() - 1 - start) + start;
        System.out.println(s.subSequence(start, end));

        //exercise toString();
        System.out.println(s);

    }
}
/*
.
e
g
a
k
c
a
p
 
g
n
a
l
.
a
v
a
j
 
e
h
t
 
n
i
 
d
n
u
o
f
 
e
c
a
f
r
e
t
n
i
 
e
c
n
e
u
q
e
S
r
a
h
C
 
e
h
t
 
s
t
n
e
m
e
l
p
m
i
 
t
a
h
t
 
s
s
a
l
c
 
a
 
e
t
i
r
W
lc 
.egakcap gnal.avaj eht ni dnuof ecafretni ecneuqeSrahC eht stnemelpmi taht ssalc a etirW
*///:
