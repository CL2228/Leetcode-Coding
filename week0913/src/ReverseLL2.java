// 0913 92 Reverse Linked List II

import java.util.List;

public class ReverseLL2 {
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        return reverseListInt(head, left, right);
    }
    private ListNode reverseListInt(ListNode current, int l, int r) {
        ListNode previous = null;

        ListNode head = current;
        ListNode leftBorder = null;
        ListNode rightBorder;
        ListNode currNext = null;
        int count = 0;
        while (current.next != null) {
            while(count < (l - 1)) {
                if (count == l - 2) leftBorder = current;
                current = current.next;
                count++;
            }

            ListNode right = current;

            while (count >= (l - 1) && count < r) {
                currNext = current.next;
                current.next = previous;
                previous = current;
                current = currNext;
                count++;
            }


            leftBorder.next = previous;
            right.next = currNext;


            while(current.next != null && count >= r) {
                current = current.next;
                count++;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        ReverseLL2 rll2 = new ReverseLL2();
        ListNode a = new ListNode(22);
        ListNode b = new ListNode(33);
        ListNode c = new ListNode(88);
        ListNode d = new ListNode(99);
        ListNode e = new ListNode(54);
        a.next = b;
        b.next = c;
        c.next = d;
        d.next = e;

        ListNode rllresult = rll2.reverseBetween(a, 2, 4);
        ListNode currentNew = rllresult;
        while (currentNew != null) {
            System.out.println(currentNew.val);
            currentNew = currentNew.next;
        }
    }
}
