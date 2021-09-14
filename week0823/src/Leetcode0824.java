import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Leetcode0824 {

    private class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    // [M] 19       Remove Nth Node From End of List
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) return null;
        ListNode virtualHead = new ListNode(-100, head);
        ListNode slow = virtualHead;
        ListNode fast = virtualHead;
        for (int i =0; i < n + 1; i++) fast = fast.next;

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;

        return virtualHead.next;
    }


    // [E] Int0207  Intersection Node
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        int modeA = 1;
        int modeB = 1;
        ListNode curA = headA;
        ListNode curB = headB;
        while (modeA >= 0 && modeB >= 0) {
            if (curA == curB) return curA;

            if (curA.next == null) {
                curA = headB;
                modeA--;
            }
            else curA = curA.next;

            if (curB.next == null) {
                curB = headA;
                modeB--;
            }
            else curB = curB.next;
        }
        return null;
    }


    // [M] 142      Linked List Cycle II
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) return null;
        ListNode slow = head.next;
        ListNode fast = slow.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) return null;
            slow = slow.next;
            fast = fast.next.next;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }


    // [M] 15       3Sum
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums.length < 3) return new LinkedList<>();
        List<List<Integer>> result = new LinkedList<>();
        quickSort(nums, 0, nums.length - 1);

        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int lo = i + 1, hi = nums.length - 1;
                int target = - nums[i];
                while (lo < hi) {
                    if (nums[lo] + nums[hi] == target) {
                        result.add(Arrays.asList(nums[lo], nums[hi], nums[i]));
                        while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                        while (hi > lo && nums[hi] == nums[hi - 1]) hi--;
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



    // [M] 18       4Sum
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums.length < 4) return new LinkedList<>();
        List<List<Integer>> result = new LinkedList<>();
        quickSort(nums, 0, nums.length - 1);

        for (int i = 0; i < nums.length - 3; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                for (int j = i + 1; j < nums.length - 2; j++) {
                    if (j == i + 1 || nums[j] != nums[j - 1]) {
                        int subTarget = target - nums[i] - nums[j];
                        int lo = j + 1, hi = nums.length - 1;
                        while (lo < hi) {
                            if (nums[lo] + nums[hi] == subTarget) {
                                result.add(Arrays.asList(nums[i], nums[j], nums[lo], nums[hi]));
                                while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                                while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
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
    private void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo , hi);
        exch(a, j, lo);
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

}


