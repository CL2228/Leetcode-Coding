package pastOnes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LeetCode0816 {


    // [M] LeetCode#15      3Sum
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) return new ArrayList<>();

        Arrays.sort(nums);
        List<List<Integer>> result = new LinkedList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int target = -nums[i];
                int lo = i + 1, hi = nums.length - 1;

                while (lo < hi) {
                    if (nums[lo] + nums[hi] == target) {
                        result.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                        while (lo < nums.length - 1 && nums[lo] == nums[lo + 1]) lo++;
                        while (hi > 0 &&nums[hi] == nums[hi - 1]) hi--;
                        lo++;
                        hi--;
                    }
                    else if (nums[lo] + nums[hi] < target) lo++;
                    else hi--;
                }
            }
        }
        return result;
    }


    // [M] LeetCode#18      4Sum
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) return new ArrayList<>();

        quickSort(nums, 0, nums.length - 1);
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 0; i < nums.length - 3; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                for (int j = i + 1; j < nums.length - 2; j++) {
                    int subTarget = target - nums[i] - nums[j];

                    if (j == i + 1 || nums[j] != nums[j - 1]) {
                        int lo = j + 1, hi = nums.length - 1;
                        while (lo < hi) {
                            if (nums[lo] + nums[hi] == subTarget) {
                                result.add(Arrays.asList(nums[i], nums[j], nums[lo], nums[hi]));
                                while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                                while (hi > lo && nums[hi] == nums[hi--]) hi--;
                                lo++;
                                hi--;
                            }
                            else if (nums[lo] + nums[hi] < subTarget) lo++;
                            else hi--;
                        }
                    }
                }
            }
        }
        return result;
    }
    private void quickSort(int[] a, int hi, int lo) {
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


    // [E] LeetCode#344     Reverse String
    public void reverseString(char[] s) {
        int lo = 0, hi = s.length - 1;
        while (lo < hi) {
            char swap = s[lo];
            s[lo] = s[hi];
            s[hi] = swap;
            lo++;
            hi--;
        }
    }


    // [E] LeetCode#345     Reverse Vowels of a String
    public String reverseVowels(String s) {
        char[] data = s.toCharArray();
        int lo = 0, hi = data.length - 1;
        while (lo < hi) {
            while (lo < hi && (!detectVowel(data, lo))) lo++;
            while (lo < hi && (!detectVowel(data, hi))) hi--;

            char tmp = data[lo];
            data[lo] = data[hi];
            data[hi] = tmp;

            lo++;
            hi--;
        }
        return String.valueOf(data);
    }
    private boolean detectVowel(char[] a, int index) {
        int tmp = (int) a[index];
        return (tmp == 65 || tmp == 69 || tmp == 73 || tmp == 79 || tmp == 85 ||
                tmp == 97 || tmp == 101 || tmp == 105 || tmp == 111 || tmp ==117);
    }


    // [E] LeetCode#541     Reverse String II
    public String reverseStr(String s, int k) {
        if (s.length() <= 1) return s;

        char[] data = s.toCharArray();
        int l1 = 0, l2 = Math.min(s.length() - 1, k - 1);
        while (l1 < s.length()) {
            reverseChar(data, l1, l2);
            if (l2 == s.length()) break;
            l1 += 2 * k;
            l2 = Math.min(l2 + 2 * k, data.length - 1);
        }
        return String.valueOf(data);
    }
    private void reverseChar(char[] data, int lo, int hi) {
        int i = lo, j = hi;
        while (i < j) {
            char tmp = data[i];
            data[i] = data[j];
            data[j] = tmp;
            i++;
            j--;
        }
    }



    public static void main(String[] args) {
        LeetCode0816 leetCode0816 = new LeetCode0816();
        String s = "abcdre";
        System.out.println(s);
        System.out.println(leetCode0816.reverseStr(s, 2));
    }
}
