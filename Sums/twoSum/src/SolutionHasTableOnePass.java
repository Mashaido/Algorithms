
import java.util.HashMap;

public class SolutionHasTableOnePass {
    public int[] twoSum(int[] nums, int target) {
        // create a hashtable
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        // iterate through array and compute the complement of each item
        for (int i = 0; i < nums.length; i++) {
            int compl = target - nums[i];
            // check if item already exists in hashtable
            if (hashMap.containsKey(compl)) {
                return new int[] {i, hashMap.get(compl)};
            }
            hashMap.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }
}


