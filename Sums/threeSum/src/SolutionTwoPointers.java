
/*
Time Complexity: O(n^2)
this uses sorting and two pointers, optimizing our brute force approach!
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionTwoPointers {
    public static List<List<Integer>> threeSum(int[] nums) {
        // sort the given array so that we can use pointers
        Arrays.sort(nums);
        // iterate through the array while in each iteration, fix the first element nums[i] and find the other
        // two whose sum along with nums[i] yields our target
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            // initialize and set two pointers, one rightmost and the other leftmost right after nums[i] position
            int lo = i + 1;
            int hi = nums.length - 1;
            while (lo <= hi) {
                System.out.println(nums[i] + ": " + nums[lo] + "low");
                System.out.println(nums[i]+ ": " + nums[hi] + "high");

                // keep track of our triple summation
                int sum = nums[i] + nums[lo] + nums[hi];
                if (sum == 0) {
                    // this is it!
                    List<Integer> tripNums = new ArrayList<>();
                    tripNums.add(nums[i]);
                    tripNums.add(nums[lo]);
                    tripNums.add(nums[hi]);
                    res.add(tripNums);
                    ///return new ArrayList<Integer>(nums[i], nums[lo], nums[hi]);
                    lo++; ///check that low<high
                    hi--;

                }
                else if (sum < 0) {
                    // shift lower pointer up by 1
                    lo++;
                }
                else {
                    // shift upper pointer down by 1
                    hi--;
                }
            }
        }
        return res;
    }


    // dict of lists where ... hash set
    public static void main (String[] args) {
        int[] nums = new int[] {-1,0,1,2,-1,-4};
        List<List<Integer>> res = threeSum(nums);
        System.out.println(res);
    }
}







//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class SolutionTwoPointers {
//    public List<List<Integer>> threeSum(int[] nums) {
//        // sort the given array so that we can use pointers just like in twoSumII (additionally skip repeating values)
//        Arrays.sort(nums);
//        List<List<Integer>> res = new ArrayList<>();
//        // iterate through array
//        for (int i = 0; i < nums.length; i++) {
//            if (nums[i] > 0) {
//                // if current value is greater than zero, break from the loop
//                break;
//            }
//            if (nums[i] != nums[i-1]) {
//                twoSumII(nums, i, res);
//            }
//        }
//        return res;
//    }
//
//    private void twoSumII(int[] nums, int i, List<List<Integer>> res) {
//        // set low
//        int lo = i + 1;
//        // set high pointer to last element
//        int hi = nums.length - 1;
//        while (lo <= hi) {
//            int sum = nums[i] + nums[lo] + nums[hi];
//            if (sum == 0) {
//                // this is it, we found our triplet!
//                res.add((Arrays.asList(nums[i], nums[lo], nums[hi])));
//            }
//            else if (sum < 0) {
//                // increment lo pointer up by 1
//                lo++;
//            }
//            else {
//                // decrement hi pointer down by 1
//                hi--;
//            }
//        }
//    }
//}
//
//
