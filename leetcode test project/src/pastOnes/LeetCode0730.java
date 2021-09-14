package pastOnes;

// Total: 6
import java.util.Stack;

public class LeetCode0730 {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    // [M]LeetCode#24       Swap Nodes in Pairs
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode virtualHead = new ListNode(-100, head);

        ListNode left = virtualHead;
        ListNode medium = head;
        ListNode right = head.next;
        while (medium != null && right!= null) {
            medium.next = right.next;
            left.next = right;
            right.next = medium;

            ListNode tmp = right;
            right = medium;
            medium = tmp;

            left = medium.next;
            medium = right.next;
            if (medium == null) right = null;
            else right = medium.next;
         }
        return virtualHead.next;
    }


    // [M]LeetCode#1721     Swapping Nodes in a Linked List
    public ListNode swapNodes(ListNode head, int k) {
        if (head == null || head.next == null) return head;
        int size = size(head);
        if (size - k + 1 == k) return head;     // no changes needed

        ListNode virtualHead = new ListNode(-100, head);

        if (size - k + 1 == k + 1 || size - k + 1 == k - 1) {
            ListNode leftBorder;
            ListNode right;
            if (k < size - k + 1) {
                leftBorder = getByIndex(virtualHead, k - 1);
                right = getByIndex(virtualHead, size - k + 1);

            }
            else {
                leftBorder = getByIndex(virtualHead, size - k);
                right = getByIndex(virtualHead, k);
            }
            ListNode left = leftBorder.next;
            left.next = right.next;
            right.next = left;
            leftBorder.next = right;
        }
        else {
            ListNode leftBorder = getByIndex(virtualHead, k - 1);
            ListNode rightMinus1 = getByIndex(virtualHead, size - k + 1 - 1);
            ListNode left = leftBorder.next;
            ListNode right = rightMinus1.next;
            ListNode leftNext = left.next;

            left.next = right.next;
            rightMinus1.next = left;

            right.next = leftNext;
            leftBorder.next = right;
        }
        return virtualHead.next;
    }
    private int size(ListNode head) {
        int size = 0;
        while (head != null) {
            size++;
            head = head.next;
        }
        return size;
    }
    private ListNode getByIndex(ListNode head, int index) {
        int i = 0;
        while (i < index) {
            head = head.next;
            i++;
        }
        return head;
    }


    // [M]LeetCode#19       Remove Nth Node From End of List
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) return head;
        if (head.next == null && n == 1) return null;

        ListNode virtualHead = new ListNode(-100, head);

        ListNode slow = virtualHead;
        ListNode fast = virtualHead;

        for (int i = 0; i < n + 1; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return virtualHead.next;
    }


    // [E]LeetCode#InterView 02.07    Get Intersection Node
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode currentA = headA;
        ListNode currentB = headB;

        while (currentA != currentB) {
            currentA = currentA == null? headB : currentA.next;
            currentB = currentB == null? headA : currentB.next;
        }
        return currentA;
    }


    // [M]LeetCode#142      Linked List Cycle II
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;
        if (head.next.next == head) return head;
        ListNode slow = head;
        ListNode fast = head;
        while (slow == head || slow != fast) {
            if (slow.next == null || fast.next ==null || fast.next.next == null) return null;
            slow = slow.next;
            fast = fast.next.next;
        }
//        System.out.println("slow:" + slow.val + "  fast:" + fast.val);

        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast =fast.next;
        }
        return slow;
    }


    // [M]LeetCode#287      Find the Duplicate Number
    public int findDuplicate(int[] nums) {
        int[] toSort = new int[nums.length];
        System.arraycopy(nums, 0, toSort, 0, nums.length);
        quickSort(toSort, 0, toSort.length - 1);
        visualizeInts(nums);
        visualizeInts(toSort);

        for (int i = 0; i < toSort.length - 1; i++) {
            if (toSort[i] == toSort[i + 1]) return toSort[i];
        }

        return -1;
    }
    private void quickSort(int[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        exch(a, lo, j);

        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);

    }
    private int partition(int[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
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



    public static void visualizeInts(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    public static void visualization(ListNode root) {
        while (root != null) {
            System.out.print(root.val + " ");
            root = root.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LeetCode0730 leetCode0730 = new LeetCode0730();

        int[] a = {3,1,3,4,2};


        System.out.println(leetCode0730.findDuplicate(a));
    }
}
