// Total: 2
package pastOnes;

public class LeetCode0801 {




    // [M] LeetCode#260     Single Number III
    public int[] singleNumber(int[] nums) {
        if (nums.length == 2) return nums;
        quickSort(nums, 0, nums.length - 1);
        int[] result = new int[2];
        int count = 0;
        for (int i = 0; i < nums.length; i += 2) {
            if (i == nums.length - 1) {
                result[count++] = nums[i];
                if (count >= 2) break;
            }
            else if (i < nums.length - 2) {
                if (nums[i] != nums[i + 1]) {
                    result[count++] = nums[i];
                    if (count >= 2) break;

                    if (nums[i + 1] != nums[i + 2]) {
                        result[count++] = nums[i + 1];
                        if (count >= 2) break;
                    }
                    i++;
                }
            }
            else {
                if (nums[i] != nums[i + 1]) {
                    result[count++] = nums[i];
                    result[count] = nums[i + 1];
                    break;
                }
            }
        }
        return result;
    }
    private void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
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
            if (i >= j)
                break;
            exch(a, i, j);
        }
        return j;
    }
    private void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    // [M] LeetCode#707     Design Linked List
    public static class MyLinkedList {
        private class ListNode {
            private int val;
            private ListNode pre;
            private ListNode next;
            public ListNode(int val) { this.val = val; }
            public ListNode(int val, ListNode pre, ListNode next) {
                this.val = val;
                this.pre = pre;
                this.next = next;
            }
        }

        private int count;
        private ListNode head;
        private ListNode tail;

        /** Initialize your data structure here. */
        public MyLinkedList() {
            this.count = 0;
        }

        /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
        public int get(int index) {
            if (this.count == 0) return -1;
            else if (index < 0 || index >= this.count) return -1;
            else return getByIndex(index).val;
        }

        /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
        public void addAtHead(int val) {
            ListNode newHead = new ListNode(val);
            if (count == 0) {
                this.head = newHead;
                this.tail = newHead;
            }
            else {
                newHead.next = this.head;
                this.head.pre = newHead;
                this.head = newHead;
            }
            this.count++;
        }

        /** Append a node of value val to the last element of the linked list. */
        public void addAtTail(int val) {
            ListNode newTail = new ListNode(val);
            if (count == 0) {
                this.tail = newTail;
                this.head = newTail;
            }
            else {
                this.tail.next = newTail;
                newTail.pre = this.tail;
                this.tail = newTail;
            }
            this.count++;
        }


        /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
        public void addAtIndex(int index, int val) {
            if (index == 0)
                addAtHead(val);
            else if (index == this.count) {
                addAtTail(val);
            }
            else {
                ListNode previousNode = getByIndex(index);
                ListNode newNode = new ListNode(val);
                newNode.next = previousNode;
                newNode.pre = previousNode.pre;
                previousNode.pre.next = newNode;
                previousNode.pre = newNode;
                count++;
            }
        }

        /** Delete the index-th node in the linked list, if the index is valid. */
        public void deleteAtIndex(int index) {
            if (index < 0 || index >= this.count) return;
            if (index == 0) {
                if (this.count == 1)
                    this.head = this.tail = null;
                else {
                    this.head = this.head.next;
                    this.head.pre = null;
                }
            }
            else if (index == this.count - 1) {
                if (this.count == 1)
                    this.tail = this.head = null;
                else {
                    this.tail = this.tail.pre;
                    this.tail.next = null;
                }
            }
            else {
                ListNode previousNode = getByIndex(index);
                previousNode.pre.next = previousNode.next;
                previousNode.next.pre = previousNode.pre;
            }
            count--;
        }

        private ListNode getByIndex(int index) {
            int i = 0;
            ListNode current = this.head;
            while (i < index) {
                i++;
                current = current.next;
            }
            return current;
        }

        public void visualization() {
            ListNode current = this.head;
            while (current != null) {
                System.out.print(current.val + " ");
                current = current.next;
            }
            System.out.println();
        }

    }


    public static void main(String[] args) {
        String[] commands = {"MyLinkedList","addAtHead","deleteAtIndex","addAtHead","addAtHead","addAtHead","addAtHead",
                "addAtHead","addAtTail","get","deleteAtIndex","deleteAtIndex"};
        int[] parameters = {0,2,1,2,7,3,2,5,5,5,6,4};

        MyLinkedList myLinkedList = new MyLinkedList();


        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals("MyLinkedList")) continue;

            if (commands[i].equals("addAtHead")) {
                myLinkedList.addAtHead(parameters[i]);
                System.out.println("addAtHead: " + parameters[i]);
            }

            if (commands[i].equals("deleteAtIndex")) {
                myLinkedList.deleteAtIndex(parameters[i]);
                System.out.println("deleteAtIndex: " + parameters[i]);
            }

            if (commands[i].equals("addAtTail")) {
                myLinkedList.addAtTail(parameters[i]);
                System.out.println("addAtTail: " + parameters[i]);
            }

            if (commands[i].equals("get")) {
                System.out.println("get at (" + parameters[i] + ") is:" + myLinkedList.getByIndex(parameters[i]).val) ;
            }
        }
        myLinkedList.visualization();










    }



}
