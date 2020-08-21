import java.util.HashMap;

public class SolutionHashTable {
    // a more efficient way to check if the complement exists in an array by looking up its index
    // O(n) bc the hashtable reduces lookup time to O(1)
    public int[] twoSum(int[] nums, int target) {
        // create a hashtable
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // add the element plus corresponding index to the hashtable
            hashMap.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int compl = target - nums[i];
            // check if the complement exists in our hashtable && its value ins't the index corresponding to nums[i] i.e not a repeat of nums[i]
            if (hashMap.containsKey(compl) && hashMap.get(compl) != i) {
                return new int[] {i, hashMap.get(compl)};
            }
        }
        return new int[]{-1, -1};
    }
}


