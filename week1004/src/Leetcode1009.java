import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leetcode1009 {



    // [M] 146      LRU Cache
    static class LRUCache {
        /**
         *  1. this solution uses a doubly linked list,
         *      the head of which is the most recent visited Node, the tail is the least recent visited Node
         *  2. the value of the map is the linked list node
         *
         *  1. When an element is queried, if it's not in the list, return -1, else, promote this node
         *  2. When inserting, add it to both the map and the linked list
         */

        private class Node {
            private Node pre;
            private Node next;
            private int key;
            private int value;
            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }

        }
        private Node head;
        private Node tail;
        private Map<Integer, Node> map;
        private int CAPACITY;

        public LRUCache(int capacity) {
            this.CAPACITY = capacity;
            this.map = new HashMap<>();
            this.head = this.tail = null;
        }

        public int get(int key) {
            Node curr = map.get(key);
            if (curr == null) return -1;
            else promote(curr);
            return curr.value;
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                Node curr = map.get(key);
                curr.value = value;
                promote(curr);
            }
            else {
                Node curr = new Node(key, value);
                if (map.size() == CAPACITY) {
                    map.remove(tail.key);
                    removeTail();
                }
                map.put(key, curr);
                addHead(curr);
            }
        }

        private void removeTail() {
            if (CAPACITY == 1) this.head = this.tail = null;
            else {
                this.tail = tail.pre;
                tail.next = null;
            }
        }
        private void addHead(Node curr) {
            curr.next = head;
            if (head != null) head.pre = curr;
            else tail = curr;
            head = curr;
        }
        private void promote(Node curr) {
            if (head == curr) return;
            Node currPre = curr.pre, currNext = curr.next;

            // deal with the tail
            currPre.next = currNext;
            if (currNext == null) tail = currPre;
            else currNext.pre = currPre;

            // deal with the head
            curr.next = head;
            head.pre = curr;
            head = curr;
        }

    }


    // [M] 253      Meeting Rooms II
    public int minMeetingRooms(int[][] intervals) {
        /**
         * this solution uses an array list to store to the most recent end time of that meeting room
         *
         * 1. first sort the intervals by their end time
         * 2. for each interval, find the best meeting in that list that
         *      the end time is closest to the beginning time of this interval,
         *      then update this meeting's end time to the interval's end time
         *   if this meeting does not exist, create a new meeting
         */
        List<Integer> list = new ArrayList<>();
        quickSort253(intervals, 0, intervals.length - 1);

        for (int[] interval : intervals) {
            int idx = -1;
            int minGap = Integer.MAX_VALUE;
            for (int i = 0; i < list.size(); i++) {
                int tmpEdIdx = list.get(i);
                if (interval[0] >= tmpEdIdx) {
                    if (interval[0] - tmpEdIdx < minGap) {
                        minGap = interval[0] - tmpEdIdx;
                        idx = i;
                    }
                }
            }
            if (idx < 0) list.add(interval[1]);
            else list.set(idx, interval[1]);
        }
        System.out.println(list);
        return list.size();
    }
    private void quickSort253(int[][] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition253(a, lo, hi);
        exch253(a, lo, j);
        quickSort253(a, lo, j - 1);
        quickSort253(a, j + 1, hi);
    }
    private void exch253(int[][] a, int i, int j) {
        int[] swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition253(int[][] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i][1] < a[lo][1] || (a[i][1] == a[lo][1] && a[i][0] <= a[lo][0]))
                if (i >= hi) break;
            while (a[--j][1] > a[lo][1] || (a[j][1] == a[lo][1] && a[j][0] >= a[lo][0]))
                if (j <= lo) break;
            if (i >= j) break;
            exch253(a, i, j);
        }
        return j;
    }




    public static void main(String[] args) {
        Leetcode1009 lc = new Leetcode1009();

        int[][] a = {{0,30},{5,10},{15,20}};
        System.out.println(lc.minMeetingRooms(a));



    }
}
