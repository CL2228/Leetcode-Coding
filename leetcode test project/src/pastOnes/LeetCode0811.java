package pastOnes;

import java.util.*;

public class LeetCode0811 {


    // [M] LeetCode#454     4Sum II
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> sumAB = new HashMap<>();
        int count = 0;

        for (int k : nums1) {
            for (int i : nums2) {
                if (sumAB.containsKey(k + i))
                    sumAB.replace(k + i, sumAB.get(k + i) + 1);
                else
                    sumAB.put(k + i, 1);
            }
        }

        for (int n3: nums3) {
            for (int n4 : nums4) {
                if (sumAB.containsKey(-n3 - n4))
                    count += sumAB.get(-n3 - n4);
            }
        }

        return count;
    }



    // [M] LeetCode#18      4Sum
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) return new LinkedList<>();
        List<List<Integer>> result = new LinkedList<>();
        quickSort(nums);
        for (int l1 = 0; l1 < nums.length - 3; l1++) {
            if (l1 == 0 || (nums[l1] != nums[l1 - 1])) {
                for (int l2 = l1 + 1; l2 < nums.length - 2; l2++) {
                    int sumtar = target - nums[l1] - nums[l2];
                    if (l2 == l1 + 1 || (nums[l2] != nums[l2 - 1])) {
                        int lo = l2 + 1, hi = nums.length - 1;
                        while (lo < hi) {
                            if (nums[lo] + nums[hi] == sumtar) {
                                result.add(Arrays.asList(nums[l1], nums[l2], nums[lo], nums[hi]));
                                while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                                while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                                lo++;
                                hi--;
                            }
                            else if (nums[lo] + nums[hi] > sumtar)  hi--;
                            else lo++;
                        }
                    }
                }
            }
        }
        return result;
    }
    private void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }
    private void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, lo, j);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);
    }
    private void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] <= a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j)
                break;
            exch(a, i, j);
        }
        return j;
    }


    // [H] LeetCode#354     Russian Doll Envelops
    public int maxEnvelopes(int[][] envelopes) {
        return 0;
    }
    private class Edge {

    }




    public static void visualizeDoubleList(List<List<Integer>> input) {
        for (List<Integer> list : input) {
            for (int i : list)
                System.out.print(i + " ");
            System.out.println();
        }
    }


    public static void main(String[] args) {
        LeetCode0811 leetCode0811 = new LeetCode0811();

        int[] a = {2,2,2,2,2};
        visualizeDoubleList(leetCode0811.fourSum(a, 8));
    }


}
