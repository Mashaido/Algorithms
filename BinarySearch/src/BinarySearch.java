
public class BinarySearch {
    public static int binarySearch(int valueToFind, int[] nums) {
        // initialize a low and high index
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (valueToFind < nums[mid]) {
                // valueToFind is smaller, so ignore the right half
                high = mid - 1;
            }
            else if (valueToFind > nums[mid]) {
                // valueToFind is greater, so ignore the left half
                low = mid + 1;
            }
            else {
                // valueToFind is present at the middle of list
                return mid;
            }
        }
        // // valueToFind isn't in the list
        return -1;
    }

    public static void main(String[] args ) {
        //BinarySearch bs = new BinarySearch();
        int[] nums = {4, 9, 11, 12, 14, 15, 18, 53, 82, 98, 99};
        int valueToFind = 82;
        int result = binarySearch(valueToFind, nums);
        if (result == -1) {
            System.out.println("value not present!");
        }
        else {
            System.out.println("element found at index " + result);
        }
    }
}


