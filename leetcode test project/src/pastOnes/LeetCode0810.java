package pastOnes;


import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.List;

public class LeetCode0810 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    // [E] LeetCode#202     Happy Number
    public boolean isHappy(int n) {
        HashSet<Integer> repeatDetector = new HashSet<>();
        String nums = String.valueOf(n);
        int sum = 0;
        for (int i = 0; i < nums.length(); i++)
            sum += (int) Math.pow((nums.charAt(i) - '0'), 2);

        while (sum != 1) {
            int tmp = 0;
            nums = String.valueOf(sum);
            for (int i = 0; i < nums.length(); i++)
                tmp += (int) Math.pow((nums.charAt(i) - '0'), 2);

            sum = tmp;
            if (repeatDetector.contains(sum)) return false;
            repeatDetector.add(sum);
        }
        return true;
    }


    // [E] LeetCode#141     Linked List Cycle
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null ) return false;
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null) {
            if (slow == fast) return true;
            slow = slow.next;
            if (fast.next == null) fast = null;
            else fast = fast.next.next;
        }
        return false;
    }


    // [E] LeetCode#1       Two Sum
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> numsHash = new HashMap<>();
        int[] result = new int[2];

        for (int i = 0; i < nums.length; i++) {
            int tmp = target - nums[i];
            if (numsHash.containsKey(tmp)) {
                result[0] = numsHash.get(tmp);
                result[1] = i;
            }
            numsHash.put(nums[i], i);
        }
        return result;
    }


    // [M] LeetCode#15      3Sum
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) return new ArrayList<>();
        Arrays.sort(nums);
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || (nums[i] != nums[i - 1])) {
                int lo = i + 1, hi = nums.length - 1, sum = -nums[i];
                while (lo < hi) {
                    if (nums[lo] + nums[hi] == sum) {
                        result.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                        while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                        while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                        hi--;
                        lo++;
                    }
                    else if (nums[lo] + nums[hi] < sum) lo++;
                    else hi--;
                }
            }
        }
        return result;
    }


    // [M] LeetCode#16      3Sum Closest
    public int threeSumClosest(int[] nums, int target) {
        if (nums.length == 3) return nums[0] + nums[1] + nums[2];

        int distance = Integer.MAX_VALUE;
        int result = 0;
        quickSort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int lo = i + 1, hi = nums.length - 1;

                while (lo < hi) {
                    int sum = nums[i] + nums[lo] + nums[hi];

                    if (Math.abs(target - sum) < distance) {
                        distance = Math.abs(target - sum);
                        result = sum;
                    }

                    if (sum == target) return sum;
                    else if (sum < target) lo++;
                    else hi--;
                }
            }
        }
        return result;
    }
    public void quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }
    public void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, lo, j);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);
    }
    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] <= a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;
    }
    private void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }




    public static void main(String[] args) {
        LeetCode0810 leetCode0810 = new LeetCode0810();
        int[] a = {-1,0,1,1,55};

        System.out.println(leetCode0810.threeSumClosest(a, 3));

//        for (List<Integer> list : leetCode0810.threeSum(a)) {
//            for (int i : list)
//                System.out.print(i + " ");
//            System.out.println();
//        }
    }
 }
