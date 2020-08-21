public class SolutionTwoPointers {
    // utilize the fact that input array is sorted! so no need to use hashtable (O(n)) or brute force (O(n**2))
    // O(n) where each element is visited at most once
    public int[] twoSumII(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {
            int sum = nums[lo] + nums[hi];
            if (sum == target) {
                // this is it!
                return new int[] {lo + 1, hi + 1};
            }
            else if (sum < target) {
                // shift lower pointer up by 1
                lo++;
            }
            else {
                // shift higher pointer down by 1
                hi--;
            }
        }
        return new int[] {-1, -1};
    }
}


