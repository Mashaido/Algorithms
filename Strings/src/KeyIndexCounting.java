//
///*
//sort an array a[] of N integers between 0 and R-1
//
//time and space each proportional to N+R. R is the number of diff character value ex 256 ASCII values
//
//linear time stable sorting method.
// */
//
//public class KeyIndexCounting {
//
//    int N = a.length;
//    int[] count = new int[R+1];
//
//    // count frequencies of each letter using key as index
//    for (int i = 0; i < N; i++) {
//        count[a[i]+1]++; // count[a[i]+1] = count[a[i]+1] + 1
//    }
//
//    // compute frequency cumulates which specify destinations
//    for (int r = 0; r < R; r++) {
//        count[r + 1] += count[r];
//    }
//
//    // access cumulates using key as index to move items. cumulants tell us where keys go in the output
//    for (int i = 0; i < N; i++) {
//        aux[count[a[i]]++] = a[i];
//    }
//
//    // copy back (sorted array) into original array
//    for (int i = 0; i < N; i++) {
//        a[i] = aux[i];
//    }
//}
//
//
