/*
Time Complexity: O(n^3)
this is a naive approach that uses 3 nested loops
it still produces duplicates though :( i.e assumes distinct integers in array
 */

import java.util.ArrayList;
import java.util.List;

public class SolutionBruteForce {
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> tripNums = new ArrayList<>();
                        tripNums.add(nums[i]);
                        tripNums.add(nums[j]);
                        tripNums.add(nums[k]);
                        res.add(tripNums);
                    }
                }
            }
        }
        return res;
    }
    public static void main (String[] args) {
        int[] nums = new int[] {-1,0,1,2,-1,-4};
        List<List<Integer>> res = threeSum(nums);
        System.out.println(res);
    }
}


