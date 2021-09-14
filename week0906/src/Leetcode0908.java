import java.util.*;

public class Leetcode0908 {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    // [M] 40       Combination Sum II      // TODO: not solved yet;
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Arrays.sort(candidates);
        Set<String> dupDetector = new HashSet<>();

        for (int i = 0; i < candidates.length; i++) {
            if (i == 0 || candidates[i] != candidates[i - 1])
                if (getSum40(i, 0, target, candidates, path, res, dupDetector)) break;
        }
        return res;
    }
    private boolean getSum40(int currIdx, int preSum, int target, int[] candidates,
                             List<Integer> path, List<List<Integer>> res, Set<String> dupDetector) {
        if (preSum > target) return true;

        path.add(candidates[currIdx]);
        int currSum = preSum + candidates[currIdx];
        if (currSum == target) {
            StringBuilder sb = new StringBuilder();
            for (int i : path) sb.append(i);
            if (!dupDetector.contains(sb.toString())) {
                res.add(new ArrayList<>(path));
                dupDetector.add(sb.toString());
            }
        }
        else if (currSum < target)
            for (int nextIdx = currIdx + 1; nextIdx < candidates.length; nextIdx++) {
                if (nextIdx == currIdx + 1 || candidates[nextIdx] != candidates[nextIdx - 1])
                    if (getSum40(nextIdx, currSum, target, candidates, path, res, dupDetector)) break;

            }
        path.remove(path.size() - 1);
        return false;
    }


    // [M] 216      Combination Sum III
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        // for each search at level L, starting from N_pre,
        // only need to loop to 9 - (k - 1) + (L - 1) = 9 - k + L;
        for (int i = 1; i <= 9 - k + 1; i++)
            if (backtracking216(i, 1, 0, n, k, res, path)) break;
        return res;
    }
    private boolean backtracking216(int currD, int L, int preSum, int n, int k, List<List<Integer>> res, List<Integer> path) {
        if (preSum + currD > n) return true;
        if (L > k) return false;

        path.add(currD);
        int currSum = preSum + currD;
        if (L == k) {
            if (currSum == n)
            res.add(new ArrayList<>(path));
        }
        else if (L < k) {
            L++;
            for (int i = currD + 1; i <= 9 - k + L; i++)
                if (backtracking216(i, L, currSum, n, k, res, path)) break;
        }
        path.remove(path.size() - 1);
        return false;
    }


    // [M] 17       Letter Combinations of a Phone Number
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        backtracking17(0, digits, sb, res);
        return res;
    }
    private void backtracking17(int idx, String digits, StringBuilder sb, List<String> result) {
        if (idx >= digits.length()) return;

        String subCan = decode17(digits.charAt(idx));

        if (idx == digits.length() - 1) {
            for (int i = 0; i < subCan.length(); i++) {
                sb.append(subCan.charAt(i));
                result.add(sb.toString());
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        else {
            for (int i = 0; i < subCan.length(); i++) {
                sb.append(subCan.charAt(i));
                backtracking17(idx + 1, digits, sb, result);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
    private String decode17(char c) {
        switch (c) {
            case '2' : return "abc";
            case '3' : return "def";
            case '4' : return "ghi";
            case '5' : return "jkl";
            case '6' : return "mno";
            case '7' : return "pqrs";
            case '8' : return "tuv";
            case '9' : return "wxyz";
            default: return "";
        }
    }


    // [E] 88       Merge Sorted Array
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int newIdx = m + n - 1;
        int a1Idx = m - 1, a2Idx = n - 1;
        while (newIdx >= 0) {
            if (a2Idx < 0) nums1[newIdx--] = nums1[a1Idx--];
            else if (a1Idx < 0) nums1[newIdx--] = nums2[a2Idx--];
            else {
                if (nums1[a1Idx] >= nums2[a2Idx]) nums1[newIdx--] = nums1[a1Idx--];
                else nums1[newIdx--] = nums2[a2Idx--];
            }
        }
    }
    private void quickSort88(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, lo, j);
        quickSort88(a, lo, j - 1);
        quickSort88(a, j + 1, hi);
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
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;
    }


    // [E] 21       Merge Two Sorted Lists
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode virtualHead = new ListNode(-100);
        ListNode curr = virtualHead;
        while (l1 != null || l2 != null) {
            if (l1 != null && (l2 == null || l1.val <= l2.val)) {
                curr.next = l1;
                l1 = l1.next;
            }
            else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
            curr.next = null;
        }
        return virtualHead.next;
    }


    // [M] 148      Sort List
    public ListNode sortList(ListNode head) {
        if (head == null) return null;
        int N = 0;
        ListNode curr = head;
        while (curr != null) {
            N++;
            curr = curr.next;
        }
        ListNode[] arr = new ListNode[N];
        N = 0;
        curr = head;
        while (curr != null) {
            arr[N++] = curr;
            curr = curr.next;
        }
        quickSort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length - 1; i++)
            arr[i].next = arr[i + 1];
        arr[arr.length - 1].next = null;
        return arr[0];
    }
    private void quickSort(ListNode[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition148(a, lo, hi);
        exch148(a, lo, j);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);
    }
    private void exch148(ListNode[] a, int i, int j) {
        ListNode swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition148(ListNode[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (i < hi && a[++i].val <= a[lo].val)
                if (i >= hi) break;
            while (j > lo && a[--j].val >= a[lo].val)
                if (j <= lo) break;
            if (i >= j) break;
            exch148(a, i, j);
        }
        return j;
    }


    public ListNode removeElements(ListNode head, int val) {
        return head;
    }


    public static void main(String[] args) {
        Leetcode0908 lc = new Leetcode0908();

        // a-b-c
        // 1-2-3
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(3);
        a.next = b;
        b.next = c;

        List<Integer> list = new LinkedList<>();


        ListNode resultHead = lc.removeElements(a, 2);

        ListNode current = a;
        while (current != null) {
            System.out.print(current.val);
            current = current.next;
        }

    }
}
