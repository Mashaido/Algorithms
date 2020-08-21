
class SolutionBruteForce {
    // O(n**2)
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++){
            // for each element, find its complement by looping through the rest of array
            for (int j = i + 1; j < nums.length; j++) {
                if ((nums[i] + nums[j]) == target) {
                    return new int[] {i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}


