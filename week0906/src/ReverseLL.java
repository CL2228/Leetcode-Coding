import java.util.List;
//206

public class ReverseLL {
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode reverseList(ListNode head) {
        return reverseListInt(head);
    }

    private ListNode reverseListInt(ListNode current) {
        if (current == null) {
            return null;
        }
        else {
            ListNode previous = null;

            while (current != null) {
                ListNode currnext = current.next;
                current.next = previous;
                previous = current;
                current = currnext;
            }
            return previous;
//            return reverseListInt(previous);
        }
    }
    public static void main(String[] args) {
        ReverseLL rll = new ReverseLL();
        ListNode a = new ListNode(22);
        ListNode b = new ListNode(33);
        ListNode c = new ListNode(88);
        ListNode d = new ListNode(99);
        a.next = b;
        b.next = c;
        c.next = d;

        ListNode rllresult = rll.reverseList(a);
        ListNode currentnew = rllresult;
//        ListNode currentnew = a;



        while (currentnew != null) {
            System.out.println(currentnew.val);
            currentnew = currentnew.next;
        }
    }
}
