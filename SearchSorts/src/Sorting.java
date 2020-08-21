import java.util.Arrays;

public class Sorting {
    // Insertion Sort
    // Time Complexity: worst case O(n^2) i.e reversed array and best case O(n) i.e sorted array
    // Space Complexity: O(1)
    // sorting-in-place and decently fast for small arrays
    // stable

    // to sort randomly ordered arrays with distinct keys IS uses approx (1/4)N^2 compares and (1/4)N^2 exchanges on avg
    // twice as fast as selection sort
    // if array is in ascending (sorted) order, IS makes N-1 compares and 0 exchanges
    public static int[] insertionSort(int[] nums) {
        // in iteration i swap a[i] with each larger entry to its left
        for (int i = 1; i < nums.length; i++) {
            // this is the moving marker that divides the sorted-unsorted sections of array
            int key = nums[i];
            int j = i - 1;
            // this loop shifts all elements to the right
            // repeat until this unsorted section (left of the key marker) is empty
            while (j >= 0 && nums[j] > key) {
                // move up nums[j] to 1 position next i.e shift to the right
                nums[j + 1] = nums[j];
                j --;
            }
            // now insert key to it's correct position!
            nums[j + 1] = key;
        }
        return nums;

        // Shellsort is a simple extension of insertion sort that gains
        // speed by allowing exchanges of array entries that are far apart, to produce partially
        // sorted arrays that can be efficiently sorted, eventually by insertion sort
    }

    // Selection Sort
    // Time Complexity: always O(n^2) no benefit if list is already sorted bc always have to find the next smallest item
    // Space Complexity: O(1)
    // sorting-in-place. particularly slow bc it has to go through entire array each time to find the smallest item
    // good for finding the top X elements
    // not stable

    // selection sort uses (1/2)N^2 compares and N exchanges to sort an array of length n
    public static int[] selectionSort(int[] nums) {
        // find the smallest item in the remaining array and exchange it with the leftmost unsorted element i.e bring
        // it to the starting position

        // i.e find the smallest, swap that with nums[i], increment i
        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = nums[minIndex];
            nums[minIndex] = nums[i];
            nums[i] = temp;
        }
        return nums;
    }

    // Bubble Sort
    // Time Complexity: worst case O(n^2) i.e reversed sorted array and best case O(n) i.e sorted but this is always O(n^2)
    // Space Complexity: O(1)
    // sorting-in-place
    public static int[] bubbleSort(int[] nums) {
        // repeatedly compares adjacent items --> larger ones bubble to the right
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j+1]) {
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;

                }
            }
        }
        return nums;
    }

    // Merge Sort
    // Time Complexity: always good performance O(nlogn)
    // Space Complexity:
    // divide and conquer algorithm -i.e recursively breaking down problem into 2 or more sub-problems of the same type
    // until these become easier to solve directly; then combine solutions into the final solution for the main problem
    // not in place but stable

    // basic idea is to sort (as combining) already sorted lists

    // merge sort uses NlogN compares and 6NlogN array access to sort an array of size n
    // merge sort uses extra space proportional to N hence the auxiliary array hence merge sort isn't in-place
    // n work per merge step
    // log n merge steps
    public static int[] mergeSort(int[] nums, int lo, int hi) {
        // Merge nums[lo..mid] with nums[mid+1..hi]

        // this is the splitting / dividing work going down
        if (lo == hi) {
            return nums;
        }
        int mid = (hi + lo)/2;
        mergeSort(nums, lo, mid); // divide up left half
        mergeSort(nums, mid + 1, hi); // divide up right half
        return merge(nums, lo, mid + 1, hi); // merge results back up
    }

    // put the result of merging the subarrays nums[lo..mid] with nums[mid+1..hi] into a single ordered array,
    // leaving the result in nums[lo..hi]
    public static int[] merge(int[] nums, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        int[] aux = new int[nums.length];

        for (int k = lo; k <= hi; k++) { // copy nums[lo..hi] to aux[lo..hi]
            aux[k] = nums[k];
        }
        for (int k = lo; k <= hi; k++) { // merge back to a[lo..hi]
            if (i > mid) {
                nums[k] = aux[j++];
            }
            else if (j > hi) {
                nums[k] = aux[i++];
            }
            else if (aux[i] < aux[j]) {
                nums[k] = aux[i++];
            }
            else {
                nums[k] = aux[j++];
            }
        }
        return nums;
    }

    // Quick Sort
    // Time Complexity: worst case O(n^2) i.e sorted array and best / avg case O(nlogn) depends on selecting pivot
    // Space Complexity:
    // divide and conquer algorithm
    // in-place but not stable
    // Although both Quicksort and Mergesort have an average time complexity of O(n log n), Quicksort is the preferred
    // algorithm, as it has an O(log(n)) space complexity. Mergesort, on the other hand, requires O(n) extra storage,
    // which makes it quite expensive for arrays.

    // basic idea is to divide array into 2 smaller subs the low and the highs, then recursively sort the subs
    // move items smaller than pivot to its left and greater than to its right

    // best case: quick sort uses NlogN compares and worst case (1/2)N^2

    public static int[] quickSort( int[] nums, int lo, int hi) {
        // btw it's better to shuffle array first
        // https://cuvids.io/app/video/125/watch/
        if (lo < hi) {
            // partitioning index. nums[p] is now at the right place
            int p = partition(nums, lo, hi);
            // recursively sort elements before and after partitioning element
            quickSort(nums, lo, p - 1); // Sort left part nums[lo ... p-1]
            quickSort(nums, p + 1, hi); // Sort right part nums[p+1 ... hi]
        }
        return nums;
    }

    // Partition into nums[lo..i-1], nums[i], nums[i+1..hi]
    public static int partition(int[] nums, int lo, int hi) {
        //int pivot = nums[hi]; // takes last element (instead of randomly) as its pivot
        int i = lo, j = hi + 1; // left and right scan indices
        int pivot = nums[lo]; // partitioning item
        while (true) {
            // scan right, scan left, check for scan complete, and swap
            while (nums[++i] < pivot) {
                // find item on the left to swap
                if (i == hi) {
                    break;
                }
            }
            while (pivot < nums[--j]) {
                // find item on the right to swap
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                // check if pointers cross
                break;
            }
            // swap nums[i] and nums[j]
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        // Put pivot = nums[j] into position with nums[lo..j-1] <= nums[j] <= nums[j+1..hi]
        // swap with partitioning item
        int temp = nums[lo];
        nums[lo] = nums[j];
        nums[j] = temp;
        // return index of item now known to be in place
        return j;
    }


    public static void main(String[] args) {
        int[] nums = {12, 11, 13, 5, 6};
        System.out.println("InsertionSort: " + Arrays.toString(insertionSort(nums)));

        System.out.println("SelectionSort: " + Arrays.toString(selectionSort(nums)));

        System.out.println("BubbleSort: " + Arrays.toString(bubbleSort(nums)));

        System.out.println("MergeSort: " + Arrays.toString(mergeSort(nums, 0, nums.length - 1)));

        System.out.println( "QuickSort: " + Arrays.toString(quickSort(nums, 0, nums.length - 1)));

    }
}
