/*
Least Significant Digit for string sorting

time O(WN)
space O(N+R)

consider characters from right-to-left <-<- i.e ones, tens, hundreds, thousands ... decimal places

stably sort using dth character as the key (using key-index counting)

sort must be stable(arrows do not cross) relative order is maintained

input keys must be same in length
 */
public class LSDRadixSort {
    public static void sort(String[] a, int W) { // sort a[] on leading W characters
        // w the number of characters per string
        int R = 256; // radix R
        int N = a.length;
        String[] aux = new String[N];

        // do index-counting for each digit from right to left
        // input keys of same length!
        for (int d = W-1; d >= 0; d--) {
            int[] count = new int[R+1];
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1] ++;
            }
            for (int r = 0; r < R; r++) {
                count[r + 1] += count[r];
            }
            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }
    }
}
