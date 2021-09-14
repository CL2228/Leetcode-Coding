import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Leetcode0909 {


    // [M] 131      Palindrome Partitioning
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> path = new ArrayList<>();
        backtracking131(0, s, path, res);
        return res;
    }
    private boolean isPalindrome131(int lo, int hi, String s) {
        while (lo <= hi)
            if (s.charAt(lo++) != s.charAt(hi--)) return false;
        return true;
    }
    private void backtracking131(int lo, String s, List<String> path, List<List<String>> res) {
        if (lo >= s.length()) return;
        int hi = lo;
        while (hi < s.length()) {
            while (hi < s.length() && s.charAt((hi)) != s.charAt(lo)) hi++;
            if (hi >= s.length()) break;

            if (isPalindrome131(lo, hi, s)) {
                path.add(s.substring(lo, hi + 1));
                if (hi == s.length() - 1)
                    res.add(new ArrayList<>(path));
                else
                    backtracking131(hi + 1, s, path,res);
                path.remove(path.size() - 1);
            }
            hi++;
        }
    }


    // [M] 93       Restore IP Addresses
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        Deque<Integer> path = new LinkedList<>();
        backtracking93(0, s, res, path);
        return res;
    }
    private void backtracking93(int lo, String s, List<String> res, Deque<Integer> path) {
        if (lo >= s.length()) return;
        if (path.size() >= 4) return;
        int hi = lo;
        while (hi < s.length() && hi - lo < 3) {
            int tmpByte = Integer.parseInt(s.substring(lo, hi + 1));
            if (tmpByte > 255) break;

            path.offerLast(tmpByte);
            if (hi == s.length() - 1 && path.size() == 4) {
                StringBuilder sb = new StringBuilder();
                for (int i : path) sb.append(i).append(".");
                sb.deleteCharAt(sb.length() - 1);
                res.add(sb.toString());
            }
            else if (path.size() < 4)
                backtracking93(hi + 1, s, res, path);

            path.pollLast();
            if (tmpByte == 0) break;
            else hi++;
        }
    }


    // [M] 78       Subsets
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        res.add(new ArrayList<>(path));
        for (int i = 0; i < nums.length; i++)
            backtracking78(i, nums, path, res);
        return res;
    }
    private void backtracking78(int lo, int[] nums, List<Integer> path, List<List<Integer>> res) {
        path.add(nums[lo]);
        res.add(new ArrayList<>(path));
        for (int i = lo + 1; i < nums.length; i++) {
            backtracking78(i, nums, path, res);
        }
        path.remove(path.size() - 1);
    }


    // [M] 90       Subsets II
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        res.add(new ArrayList<>(path));
        quickSort90(nums, 0, nums.length - 1);
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || nums[i] != nums[i - 1])
                backtracking90(i, nums, path, res);
        }
        return res;
    }
    private void backtracking90(int lo, int[] nums, List<Integer> path, List<List<Integer>> res) {
        if (lo >= nums.length) return;

        path.add(nums[lo]);
        res.add(new ArrayList<>(path));
        for (int i = lo + 1; i < nums.length; i++) {
            if (i == lo + 1 || nums[i] != nums[i - 1])
                backtracking90(i, nums, path, res);
        }
        path.remove(path.size() - 1);
    }
    private void quickSort90(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch90(a, lo, j);
        quickSort90(a, lo, j - 1);
        quickSort90(a, j + 1, hi);
    }
    private void exch90(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (i < hi && a[++i] <= a[lo])
                if (i >= hi) break;
            while (j > lo && a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch90(a, i, j);
        }
        return j;
    }




    public static void main(String[] args) {
        Leetcode0909 lc = new Leetcode0909();
        String s = "aab";
        List<List<String>> res = lc.partition(s);
        for (List<String> list : res) {
            for (String st : list) System.out.print(st + "  ");
            System.out.println();
        }
        System.out.println(res.size());
    }
}
