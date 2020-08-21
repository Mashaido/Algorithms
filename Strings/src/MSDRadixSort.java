import edu.princeton.cs.algs4.Insertion;

/*
Least Significant Digit for string sorting

time O(WN)
space O(N+R)

consider characters from left-to-right ->->

stably sort using dth character as the key (using key-index counting)

sort must be stable(arrows do not cross) relative order is maintained

input keys can vary in length
 */
public class MSDRadixSort {
    private static int R = 256; // radix
    private static final int M = 15; // cutoff for small subarrays
    private static String[] aux; // auxiliary array for distribution
    private static int charAt(String s, int d)
    { if (d < s.length()) return s.charAt(d); else return -1; }
    public static void sort(String[] a)
    {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N-1, 0);
    }
    private static void sort(String[] a, int lo, int hi, int d)
    { // Sort from a[lo] to a[hi], starting at the dth character.
        if (hi <= lo + M)
        { insertion(a, lo, hi, d); return; }
        int[] count = new int[R+2]; // Compute frequency counts.
        for (int i = lo; i <= hi; i++)
            count[charAt(a[i], d) + 2]++;
        for (int r = 0; r < R+1; r++) // Transform counts to indices.
            count[r+1] += count[r];
        for (int i = lo; i <= hi; i++) // Distribute.
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        for (int i = lo; i <= hi; i++) // Copy back.
            a[i] = aux[i - lo];
        // Recursively sort for each character value.
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r+1] - 1, d+1);
    }

    // return dth character of s, -1 if d = length of string
    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1], d); j--)
                exch(a, j, j-1);
    }
    // exchange a[i] and a[j]
    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // is v less than w, starting at character d
    private static boolean less(String v, String w, int d) {
        assert v.substring(0, d).equals(w.substring(0, d));
        return v.substring(d).compareTo(w.substring(d)) < 0;
    }
}
