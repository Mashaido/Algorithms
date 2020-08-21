public class SubStringSearch {

    // substring search--find a single string in text
    // (next will be pattern matching--find one of a specified set of strings in text)
    // worst case: quadratic -MN compares i.e time dependent on length of text and pattern
    // needs backup for every mismatch :( --> maybe maintain a buffer of last M characters
    public static int bruteForce(String text, String pattern) {
        int textLength = text.length(); // N
        int patternLength = pattern.length(); // M

        for (int i = 0; i < (textLength - patternLength); i++) {
            int j;
            for (j = 0; j < patternLength; j++) {
                if (text.charAt(i + j)!= pattern.charAt(j)) {
                    // exit inner loop and start afresh onto next ith character of given text string
                    break;
                }
            }
            // if we've reached the end of pattern string
            if (j == patternLength) {
                // give us the index where pattern begins in text string!
                return i;
            }
        }
        // else not found :(
        return -1; // or just textLength
    }

    // this algorithm is a clever method to always avoid backing-up to start matching all over again!
    // at the point of no match, instead of going back in textLength only move back pointer in patternLength and if it
    // moves back till hitting 0th index, increment pointer in textLength
    // find matching prefix-suffix situations in pattern string i.e when going back on pattern string find a suffix that
    // was a prefix in the same pattern string and go to that index point of 1st occurrence then continue forward comparing
    // the next character with text's pointer
    // bild a prefix suffix table
    // O(M+N) -linear
    // OHHHHHHHHHHHHHHH SO THIS WON'T WORK IF I DONT HAVE A MATCHING SUFFIX AND PREFIX N PATTERNSTRING??????????
    // AAAAAAAAAAAAAND WHICH IS THE BEST OUT OF THE 3 OR SHOULD WE KNOW ALL???????????????/
    public static int knuthMorrisPratt(String text, String pattern) {
        int textLength = text.length(); // N
        int patternLength = pattern.length(); // M

        // create array to hold the longest prefix suffix values in pattern string
        int[] lps = new int[patternLength];
        // index used in pattern string
        int j = 0;

        // preprocess pattern string i.e calculate lps[] on input pattern string
        computeLPS(pattern, patternLength, lps);

        // index used in text string
        int i = 0; // or start it at 1 the 1st char in text string then a few things change ahead
        // stopping criteria is exhausting textLength
        while (i < textLength) {
            if (pattern.charAt(j) == text.charAt(i)) {
                // increment both pointers since it's a match!
                j++;
                i++;
            }
            // if we've reached the end of pattern string
            if (j == patternLength) {
                System.out.println("found pattern beginning at index " + (i - j)); // OHHHHHHHHHHHHHHH WHY -J???????????
                // give us the index where pattern begins in text string!
                j = lps[j-1]; // OHHHHHHHHHHHHHHH WHY -J???????????
                return i-j;
            }
            // mismatch!
            else if (i < textLength && pattern.charAt(j)!= text.charAt(i)) {
                if (j!= 0) {
                    // take pointer j back --https://www.youtube.com/watch?v=V5-7GzOfADQ
                    j = lps[j-1];
                }
                else {
                    // increment pointer i since j already at pos 0
                    i++;
                }
            }
        }
        return -1;
    }

    // this also answers: Given a string s, find length of the longest prefix which is also suffix
    // the prefix and suffix should not overlap
    private static void computeLPS(String pattern, int patternLength, int[] lps) {
        // length of previous longest prefix suffix
        int len = 0; // length of matching prefix-suffix
        int k = 1;
        lps[0] = 0; // lps[0] always 0 i.e starting point of pattern string

        // this loop calculates lps[i] for i=1 through patternLength-1
        while (k < patternLength) {
            if (pattern.charAt(k) == pattern.charAt(len)) {
                len++; // increment matching prefix-suffix length
                // only increment len when there's a match!
                lps[k] = len; // fill in lps table array with matching prefix-suffix length. this helps us backtrack pattern
                k++; // move k pointer to next character
            }
            else {
                // pattern.charAt(k) != pattern.charAt(len)
                if (len == 0) {
                    lps[k] = 0; // lps[k] = len
                    k++; // move k pointer to next character
                }
                else {
                    // len!= 0
                    len = lps[len - 1];

                }
            }
        }
    }
}
