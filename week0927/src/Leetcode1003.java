import java.util.LinkedList;
import java.util.List;

public class Leetcode1003 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    // [H] 124      Binary Tree Maximum Path Sum
    public int maxPathSum(TreeNode root) {
        int[] resRoot = postOrderFind(root);
        return Math.max(resRoot[0], Math.max(resRoot[1], resRoot[2]));
    }
    private int[] postOrderFind(TreeNode curr) {
        // return: [Maximum historical bi-directional path, current maximum unidirectional, historical maximum unidirectional]

        if (curr == null) return new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        int[] leftRes = postOrderFind(curr.left);
        int[] rightRes = postOrderFind(curr.right);

        // First, calculate the maximum historical bi-directional path
        int currBiDirection = curr.val;
        if (leftRes[1] > 0) currBiDirection += leftRes[1];
        if (rightRes[1] > 0) currBiDirection += rightRes[1];
        currBiDirection = Math.max(currBiDirection, Math.max(leftRes[0], rightRes[0]));

        // Second, calculate the current unidirectional path
        int currUniDirection = curr.val;
        currUniDirection += Math.max(0, Math.max(leftRes[1], rightRes[1]));

        // Third, calculate the maximum historical unidirectional path
        int hisUniDirection = Math.max(currUniDirection, Math.max(leftRes[2], rightRes[2]));
        return new int[] {currBiDirection, currUniDirection, hisUniDirection};
    }


    // [M] 19       Remove Nth Node From End of List
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode virtualHead = new ListNode(-100, head);
        ListNode left = virtualHead, right = virtualHead;
        for (int i = 0; i < n + 1; i++) right = right.next;

        while (right != null) {
            right = right.next;
            left = left.next;
        }
        left.next = left.next.next;
        return virtualHead.next;
    }


    // [M] 22       Generate Parentheses
    public List<String> generateParenthesis(int n) {
        List<String> res = new LinkedList<>();
        int[] remain = new int[]{n, n};
        StringBuilder sb = new StringBuilder();
        backtrack22(remain, sb, res);
        return res;
    }
    private void backtrack22(int[] remain, StringBuilder sb, List<String> res) {
        if (remain[0] == 0 && remain[1] == 0) {
            res.add(sb.toString());
            return;
        }
        if (remain[0] == remain[1]) {
            sb.append('(');
            remain[0]--;
            backtrack22(remain, sb, res);
            remain[0]++;
            sb.deleteCharAt(sb.length() - 1);
        }
        else if (remain[0] == 0) {
            sb.append(')');
            remain[1]--;
            backtrack22(remain, sb, res);
            remain[1]++;
            sb.deleteCharAt(sb.length() - 1);
        }
        else {
            sb.append('(');
            remain[0]--;
            backtrack22(remain, sb, res);
            remain[0]++;
            sb.deleteCharAt(sb.length() - 1);

            sb.append(')');
            remain[1]--;
            backtrack22(remain, sb, res);
            remain[1]++;
            sb.deleteCharAt(sb.length() - 1);
        }
    }


    // [H] 25       Reverse Nodes in k-Group
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode virtualHead = new ListNode(-100, head);
        ListNode lastTail = virtualHead;
        ListNode left = head, right = head;
        for (int i = 0; i < k - 1; i++) right = right.next;

        while (right != null) {
            lastTail.next = reverseList(left, right);
            lastTail = left;
            left = right = lastTail.next;
            for (int i = 0; i < k - 1; i++) {
                if (right == null) break;
                right = right.next;
            }
            if (right == null) break;
        }
        return virtualHead.next;
    }
    private ListNode reverseList(ListNode left, ListNode right) {
        // reverse a linked list given the head and the tail of that linked list
        // notice that the right.next is not null;
        // let pre = right.next, so the head will point to it
        ListNode rightNext = right.next;
        ListNode pre = null, curr = left;
//        System.out.println("test: " + rightNext.val);

        while (curr != rightNext) {
            ListNode currNext = curr.next;
            curr.next = pre;
            pre = curr;
            curr = currNext;
        }
        left.next = rightNext;
        return pre;
    }


    // [M] 33       Search in Rotated Sorted Array
    public int search1(int[] nums, int target) {
        if (nums.length == 1 && nums[0] == target) return 0;

        int lo = 0, hi = nums.length - 1, mid = (lo + hi) / 2;
        while (lo < hi) {
//            System.out.println(lo + " " + hi);
            if (nums[lo] == target) return lo;
            if (nums[hi] == target) return hi;
            if (nums[mid] == target) return mid;

            if (nums[lo] > nums[hi]) {
                if (nums[mid] < nums[hi]) {
                    if (target < nums[mid] || target > nums[lo]) hi = mid - 1;
                    else lo = mid + 1;
                }
                else {
                    if (target > nums[mid] || target < nums[hi]) lo = mid + 1;
                    else hi = mid - 1;
                }
            }
            else {
                if (nums[mid] < target) lo = mid + 1;
                else hi = mid - 1;
            }
            mid = (hi + lo) / 2;
        }
        return -1;
    }


    // [M] 81       Search in Rotated Sorted Array II
    public boolean search(int[] nums, int target) {
        return subSearch(nums, 0, nums.length - 1, target);
    }
    private boolean subSearch(int[] nums, int lo, int hi, int target) {
        if (nums.length == 1 && target == nums[0]) return true;     // if only one element, easy to find
        int mid = (lo + hi) / 2;

        if (lo >= hi) return false;                                 // if lo == hi, it must be false
        if (nums[lo] == target || nums[hi] == target || nums[mid] == target) return true;   // one of the true cases

//        System.out.println(lo + ":" + nums[lo] + " " +
//                mid + ":" + nums[mid] + " "
//                        + hi + ":" + nums[hi]);

        if (nums[lo] < nums[hi]) {                              // if nums[lo] < nums[hi], normal case
            if (nums[mid] < target) return subSearch(nums, mid + 1, hi, target);
            else return subSearch(nums, lo, mid - 1, target);
        }
        else {
            if (nums[lo] == nums[hi] && nums[hi] == nums[mid])  // if lo, mid, hi are the same , need to exam both sides
                return subSearch(nums, lo, mid, target) || subSearch(nums, mid + 1, hi, target);
            else if (nums[mid] <= nums[hi]) {                   // if mid < hi,
                if (target < nums[mid] || target > nums[lo]) return subSearch(nums, lo, mid - 1, target);
                else return subSearch(nums, mid + 1, hi, target);
            }
            else {                                              // if mid > hi
                if (target > nums[mid] || target < nums[lo]) return subSearch(nums, mid + 1, hi, target);
                else return subSearch(nums, lo, mid - 1, target);
            }
        }
    }









    public static void main(String[] args) {
        Leetcode1003 lc = new Leetcode1003();
        int[] a = {2,5,6,0,0,1,2};

        System.out.println(lc.search(a, 3));

    }
}
