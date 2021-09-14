// Total: 4
package pastOnes;

public class LeetCode0729 {

    // TODO: 707, 1206

    public static class ListNode{
        int val;
        ListNode next;
        public ListNode() {}
        public ListNode(int val) { this.val = val; }
        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    // [E] LeetCode#203     Remove LInked List Elements
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) return null;

        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null) return null;
        ListNode current = head;
        while (current != null) {
            if (current.next == null) break;
            if (current.next.val == val) {
                ListNode nextValid = current.next.next;
                while (nextValid != null && nextValid.val == val) nextValid = nextValid.next;
                current.next = nextValid;
            }

            current = current.next;
        }
        return head;
    }



    // [M] LeetCode#707     Design Linked List
    public static class MyLinkedList{
        private class Node707 {
            Node707 next;
            Node707 prev;
            int val;
            public Node707(int val) { this.val = val; }
        }

        private Node707 head;
        private Node707 tail;
        private int count;

        /** Initialize your data structure here. */
        public MyLinkedList() {
            this.count = 0;
        }

        /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
        public int get(int index) {
            if (index > this.count) return -1;
            Node707 current = head;
            int cnt = 0;
            while (current != null) {
                if (cnt == index) return current.val;
                cnt++;
                current = current.next;
            }
            return -1;
        }

        /** Add a node of value val before the first element of the linked list.
         * After the insertion, the new node will be the first node of the linked list. */
        public void addAtHead(int val) {
            if (this.head == null) {
                this.head = new Node707(val);
                this.tail = this.head;
                this.count++;
            }
            else {
                Node707 newHead = new Node707(val);
                newHead.next = this.head;
                this.head.prev = newHead;
                this.head = newHead;
                this.count++;
            }


        }

        /** Append a node of value val to the last element of the linked list. */
        public void addAtTail(int val) {
            if (this.tail == null) {
                this.tail = new Node707(val);
                this.head = this.tail;
                this.count++;
            }
            else {
                this.tail.next = new Node707(val);
                this.tail.next.prev = this.tail;
                this.tail = tail.next;
                this.count++;
            }
        }

        /** Add a node of value val before the index-th node in the linked list.
         * If index equals to the length of linked list, the node will be appended to the end of linked list.
         * If index is greater than the length, the node will not be inserted. */
        public void addAtIndex(int index, int val) {
            if (index > this.count) return;
            else if (index == this.count) {
                addAtTail(val);
            }
            else if (index == 0) {
                addAtHead(val);
            }
            else {
                int cnt = 0;
                Node707 current = this.head;

                while (cnt < index) {
                    current = current.next;
                    cnt++;
                }

                Node707 newInserter = new Node707(val);
                newInserter.prev = current.prev;
                newInserter.next = current;
                current.prev.next = newInserter;
                current.prev = newInserter;
                this.count++;
            }
        }



        /** Delete the index-th node in the linked list, if the index is valid. */
        public void deleteAtIndex(int index) {
            if (index < this.count) {

                int cnt = 0;
                Node707 current = this.head;
                while (cnt < index) {
                    current = current.next;
                    cnt++;
                }


                if (this.count == 1 && index == 0) {
                    this.head = null;
                    this.tail = null;
                }
                else if (index == 0) {
                    this.head = this.head.next;
                    if (this.count > 2) {
                        this.head.next.prev = this.head;
                    }
                }
                else if (cnt == this.count - 1) {
                    current.prev.next = null;
                    this.tail = current.prev;
                }
                else
                    current.prev.next = current.next;

                this.count--;
            }
        }


        public void visualization() {
            Node707 curr = head;
            while (curr != null) {
                System.out.print(curr.val + " ");
                curr = curr.next;
            }
            System.out.println();

        }
    }



    // [E] LeetCode#206     Reverse Linked List
    public ListNode reverseList(ListNode head) {
        return reverse(head, null);
    }
    private ListNode reverse(ListNode curr, ListNode pre) {
        if (curr == null) return pre;
        ListNode nextCurr = curr.next;
        curr.next = pre;
        return reverse(nextCurr, curr);
    }



    // [M] LeetCode#92      Reverse Linked List II
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) return head;
        ListNode virtualHead = new ListNode(0, head);
        ListNode leftBorder = getByIndex(virtualHead, left - 1);
        ListNode leftNode = leftBorder.next;
        ListNode rightBorder = getByIndex(virtualHead, right).next;

        ListNode current = leftNode;
        ListNode tmpNext = leftNode.next;
        while (tmpNext != rightBorder) {
            ListNode anotherNext = tmpNext.next;
            tmpNext.next = current;
            current = tmpNext;
            tmpNext = anotherNext;
        }

        leftBorder.next = current;
        leftNode.next = rightBorder;
        return virtualHead.next;
    }
    private ListNode getByIndex(ListNode head, int index) {
        int i = 0;
        while (i < index) {
            head = head.next;
            i++;
        }
        return head;
    }




    public static void main(String[] args) {
        LeetCode0729 leetCode0729 = new LeetCode0729();

        ListNode C = new ListNode(5);
        ListNode B = new ListNode(4, C);
        ListNode A = new ListNode(3, B);
        System.out.println(leetCode0729.reverseList(A).val);

    }
}
