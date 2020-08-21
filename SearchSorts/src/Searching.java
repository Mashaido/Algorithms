import java.util.Arrays;

public class Searching {
    // Linear Search for unordered array O(n)
    // Time Complexity: O(n)
    public static int linearSearch(int[] nums, int valueToFind) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == valueToFind) {
                // return the position of my valuetoFind
                return i;
            }
        }
        return -1;
    }

    // Binary Search for an ordered array O(logn)
    // Time Complexity: O(logn)
    // Space Complexity: O(1) iterative approach better than O(logn) recursive which calls stack space
    // divide and conquer algorithm
    public static int binarySearch(int[] nums, int valueToFind) {
        int lo = 0; // low index
        int hi = nums.length - 1; // high index
        while (lo <= hi) {
            // mid index
            int mid = (hi + lo)/2;
            if (valueToFind == nums[mid]) {
                // found it!
                return mid;
            }
            else if (valueToFind < nums[mid]) {
                // ignore the top/right half and focus on the bottom/left half
                hi = mid - 1;
            }
            else {
                lo = mid + 1;
            }
        }
        // not found :(
        return -1;
    }


    // binary search algorithm and hash tables allow significantly faster searching in comparison to Linear search



    public static void main(String[] args) {
        int[] nums = {12, 4, 9, 18, 53, 82, 15, 99, 98, 14, 11};
        int valueToFind = 15;
        int valueToFind2 = 42;
        System.out.println(linearSearch(nums, valueToFind));
        System.out.println(linearSearch(nums, valueToFind2));

        int[] nums2 = {4, 9, 11, 12, 14, 15, 18, 53, 82, 98, 99};
        int valueToFind3 = 2;
        int result = binarySearch(nums2, valueToFind3);
        if (result == -1) {
            System.out.println("element not present!");
        }
        else {
            System.out.println("element found at index " + result);
        }
    }
}
