
public class Solutions {

    /*
    given a string check if it is Pangram or not
    a pangram is a sentence containing every letter in the English Alphabet

    examples : "The quick brown fox jumps over the lazy dog” is a Pangram [Contains all the characters from ‘a’ to ‘z’]

    O(N) with an input string of length of n
     */
    public static boolean checkPangram(String sentence) {
        // String alphabet = "abcdefghijklmnopqrstuvwxyz";
        // String s = sentence.toLowerCase();
        if (sentence.length() < 26) {
            return false;
        }
        String sentenceLowerCase = sentence.toLowerCase(); // assuming we cared about case
        // String sentenceCleaned = sentenceLowerCase.replaceAll("\\W", ""); // anything that isn't a word character

        boolean[] visited = new boolean[26];

        for (int i = 0; i < sentenceLowerCase.length(); i++) {
            if ('a' <= sentenceLowerCase.charAt(i) && sentenceLowerCase.charAt(i) <= 'z') { // if sandwiched between a and z
                int index = sentenceLowerCase.charAt(i) - 'a'; // how far is the char from a... index 0 through 25
                visited[index] = true; // use it as the index i.e whenever we see a char, mark the char as visited
            }
        }
        for (boolean v: visited) {
            if (!v) { // if (v == false)
                return false;
            }
        }
        return true;
    }

        /*
    given two strings find the length of the longest common prefix


    examples : "prefix” and "prefetch" yields 4
         */
     public static int longestCommonPrefix(String s, String t) {
         // counter to store length of lcp
         int len = 0;

         // which is the shortest word?
         int shortest = Math.min(s.length(), t.length());

         // traverse through each pos (shortest word length), if characters match, increase counter index
         for (int i = 0; i < shortest; i++) {
             if (s.charAt(i) == t.charAt(i)) {
                 len += 1;
             }
         }
         // if they stopped matching, exit and return the counter index position
         return len;
     }

     // this actually is java's indexOf()
     public static int subStringSearchBruteForce(String text, String pattern) {
         // note that the furthest this first/outer i pointer can go is text.len - t.len
         // ith char in text string
         for (int i = 0; i < text.length() - pattern.length(); i++) {
             // for every value of i, create a new j index pointer for pattern string
             int j;
             // check whether j matches ith char
             for (j = 0; j < pattern.length(); j++){
                 if (pattern.charAt(j) != text.charAt(i+j)) {
                     // exit the inner loop to increment the outer
                     break;
                 }
             }
             // index in text where pattern starts
             if (j == pattern.length()) {
                 return i;
             }
         }

         // return pos of 1st occurence of first character of t

         // else return not found
         return -1;
     }

        public static void main(String[] args) {
        System.out.println("");

        String sentence = "The quick brown fox jumps over the lazy doG!";
        System.out.println(checkPangram(sentence));

        System.out.println("");

        String s = "prefetch";
        String t = "prefix";
        System.out.println(longestCommonPrefix(s,t));

        System.out.println("");


        }
}
