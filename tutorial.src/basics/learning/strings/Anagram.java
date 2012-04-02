package basics.learning.strings;

public class Anagram {

    /**
     * Tests whether the passed-in strings are anagrams --
     * containing the exact same number of each letter.
     * Punctuation, case, and order don't matter.
     * 
     * @return true if the strings are anagrams; otherwise, false
     */
    public static boolean areAnagrams(String string1,
                                      String string2) {

        String workingCopy1 = removeJunk(string1);
        String workingCopy2 = removeJunk(string2);

        workingCopy1 = workingCopy1.toLowerCase();
        workingCopy2 = workingCopy2.toLowerCase();

        workingCopy1 = sort(workingCopy1);
        workingCopy2 = sort(workingCopy2);
        
        System.out.println("workingCopy1=" + workingCopy1);
        System.out.println("workingCopy2=" + workingCopy2);

        return workingCopy1.equals(workingCopy2);
    }

    /**
     * Removes punctuation & spaces -- everything except 
     * letters from the passed-in string.
     * 
     * @return a stripped copy of the passed-in string
     */
    protected static String removeJunk(String string) {
        int i, len = string.length();
        StringBuilder dest = new StringBuilder(len);
        char c;

        for (i = (len - 1); i >= 0; i--) {
            c = string.charAt(i);
            if (Character.isLetter(c)) {
                dest.append(c);
            }
        }

        return dest.toString();
    }

    /** 
     * Sorts the passed-in string.  
     * 
     * @return a sorted copy of the passed-in string
     */
    protected static String sort(String string) {
        char[] charArray = string.toCharArray();

        java.util.Arrays.sort(charArray);

        return new String(charArray);
    }

    public static void main(String[] args) {
        String string1 = "Cosmo and Laine:";
        String string2 = "Maid, clean soon!";

        System.out.println();
        System.out.println("Testing whether the following "
                         + "strings are anagrams:");
        System.out.println("    String 1: " + string1);
        System.out.println("    String 2: " + string2);
        System.out.println();

        if (areAnagrams(string1, string2)) {
            System.out.println("They ARE anagrams!");
        } else {
            System.out.println("They are NOT anagrams!");
        }
        System.out.println();
    }
}
