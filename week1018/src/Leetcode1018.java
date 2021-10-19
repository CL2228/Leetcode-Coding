import java.util.*;

public class Leetcode1018 {


    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

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

    // [M] 328      Odd Even LinkedList
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) return head;
        boolean oddState = true;

        ListNode lastOdd = head, lastEven = head.next, curr = lastEven.next;

        while (curr != null) {

            if (oddState) {         // this is an odd node, it should move to the first part
                ListNode evenHead = lastOdd.next;
                ListNode currNext = curr.next;

                lastEven.next = currNext;       // let the lastEven -> curr.next
                curr.next = evenHead;           // curr.next should be the evenHead
                lastOdd.next = curr;            // curr should be concatenated to the lastOdd, and it should be the lastOdd
                lastOdd = curr;                 // make it the lastOdd

                curr = currNext;                // update the current node
            }
            else {
                lastEven = curr;                // if this is an even node, no operation needed
                curr = curr.next;
            }
            oddState = !oddState;               // flip the state
        }

        return head;
    }


    // [M] 355      Design Twitter
    class Twitter {

        private Map<Integer, Deque<Tweet>> userPostData;
        private Map<Integer, Set<Integer>> followData;

        private int timeStamp;

        private class Tweet {
            private int timeStamp;
            private int tweetId;
            public Tweet(int timeStamp, int tweetId) {
                this.timeStamp = timeStamp;
                this.tweetId = tweetId;
            }
        }

        public Twitter() {
            timeStamp = 0;
            this.userPostData = new HashMap<>();
            this.followData = new HashMap<>();
        }


        public void postTweet(int userId, int tweetId) {
            if (!userPostData.containsKey(userId)) {            // this user doesn't exist
                Deque<Tweet> subDeque = new LinkedList<>();
                subDeque.offerFirst(new Tweet(timeStamp, tweetId));
                userPostData.put(userId, subDeque);
                followData.put(userId, new HashSet<>());
            }
            else {                     // this user exists
                Deque<Tweet> userDeque = userPostData.get(userId);
                userDeque.offerFirst(new Tweet(timeStamp, tweetId));
                if (userDeque.size() > 10) userDeque.pollLast();

            }
            timeStamp++;
        }


        public List<Integer> getNewsFeed(int userId) {
            if (!userPostData.containsKey(userId)) return new LinkedList<>();
            PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> -Integer.compare(a.timeStamp, b.timeStamp));
            Set<Integer> followList = followData.get(userId);
            List<Integer> res = new LinkedList<>();

            for (int followee : followList)
                pq.addAll(userPostData.get(followee));
            pq.addAll(userPostData.get(userId));

            int idx = 0;
            while (!pq.isEmpty() && idx < 10) {
                Tweet t = pq.remove();
                res.add(t.tweetId);
                idx++;
            }
            return res;
        }

        public void follow(int followerId, int followeeId) {
            if (!userPostData.containsKey(followerId)) {
                userPostData.put(followerId, new LinkedList<>());
                followData.put(followerId, new HashSet<>());
            }
            if (!userPostData.containsKey(followeeId)) {
                userPostData.put(followeeId, new LinkedList<>());
                followData.put(followeeId, new HashSet<>());
            }
            followData.get(followerId).add(followeeId);
        }

        public void unfollow(int followerId, int followeeId) {
            followData.get(followerId).remove(followeeId);
        }
    }


    // [M] 80       Remove Duplicates from Sorted Array II
    public int removeDuplicates(int[] nums) {
        int REPEATING_MAXIMUM = 2;

        int stIdx = 1;
        int repeatFreq = 1;

        for (int edIdx = 1; edIdx < nums.length; edIdx++) {
            if (nums[edIdx] != nums[edIdx - 1]) {
                repeatFreq = 1;
                nums[stIdx++] = nums[edIdx];
            }
            else {
                if (repeatFreq < REPEATING_MAXIMUM) {
                    nums[stIdx++] = nums[edIdx];
                    repeatFreq++;
                }
            }
        }

        return stIdx;
    }


    // [H] 32       Longest Valid Parentheses
    public int longestValidParentheses(String s) {

        Stack<Node> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') stack.push(new Node('(', i));
            else {
                if (stack.isEmpty() || stack.peek().val == ')') stack.push(new Node(')', i));
                else stack.pop();
            }
        }

        int curIdx = s.length();
        int maxLen = 0;

        while (!stack.isEmpty()) {
            Node peekN = stack.pop();
            int peekIdx = peekN.index;
            maxLen = Math.max(maxLen, curIdx - peekIdx - 1);
            curIdx = peekIdx;
        }
        maxLen = Math.max(maxLen, curIdx);


        return maxLen;
    }
    private class Node {
        private char val;
        private int index;
        public Node(char val, int index) {
            this.val = val;
            this.index = index;
        }
    }


    // [E] 66       Plus One
    public int[] plusOne(int[] digits) {
        int remainder = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int currSum = digits[i] + remainder;
            digits[i] = currSum % 10;
            remainder = currSum / 10;
        }
        if (remainder == 0) {
            return digits;
        }
        else {
            int[] res = new int[digits.length + 1];
            res[0] = remainder;
            for (int i = 0; i < digits.length; i++) res[i + 1] = digits[i];
            return res;
        }


    }


    // [M] 369      Plus One Linked List
    public ListNode plusOne(ListNode head) {
        int remainder = 1;
        Stack<ListNode> stack = new Stack<>();
        ListNode curr = head;
        while (curr != null) {
            stack.push(curr);
            curr = curr.next;
        }

        while (!stack.isEmpty()) {
            curr = stack.pop();
            int currSum = curr.val + remainder;
            curr.val = currSum % 10;
            remainder = currSum / 10;
        }

        if (remainder == 0) return head;
        else {
            ListNode newHead = new ListNode(remainder);
            newHead.next = head;
            return newHead;
        }
    }


    // [H] 60       Permutation Sequence
    public String getPermutation(int n, int k) {
        int[] a = new int[n];
        for (int i = 1; i <= n; i++) a[i - 1] = i;
        for (int i = 0; i < k - 1; i++) permutateOnce(a);

        StringBuilder sb = new StringBuilder();
        for (int i : a) sb.append(i);
        return sb.toString();
    }
    private void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, lo, j);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);
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
    private void permutateOnce(int[] a) {
        int firstIdx = a.length - 2;
        while (firstIdx >= 0 && a[firstIdx] > a[firstIdx + 1]) firstIdx--;
        int secondIdx = a.length - 1;
        while (secondIdx >= 0 && a[secondIdx] < a[firstIdx]) secondIdx--;
        exch(a, firstIdx, secondIdx);
        quickSort(a, firstIdx + 1, a.length - 1);
    }


    // [M] 73       Set Matrix Zeros
    public void setZeroes(int[][] matrix) {
        int M = matrix.length, N = matrix[0].length;


        boolean rowMarked = false, colMarked = false;
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (matrix[r][c] == 0) {
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;

                    if (r == 0) rowMarked = true;
                    if (c == 0) colMarked = true;
                }
            }
        }

//        for (int[] row : matrix) {
//            for (int e : row) System.out.print(e + " ");
//            System.out.println();
//        }



        for (int r = 1; r < M; r++) {
            if (matrix[r][0] == 0) {
                for (int c = 1; c < N; c++) matrix[r][c] = 0;
            }
        }
        for (int c = 1; c < N; c++) {
            if (matrix[0][c] == 0) {
                for (int r = 1; r < M; r++) matrix[r][c] = 0;
            }
        }
        if (matrix[0][0] == 0) {
            if (colMarked)
                for (int r = 0; r < M; r++) matrix[r][0] = 0;
            if (rowMarked)
                for (int c = 0; c < N; c++) matrix[0][c] = 0;
        }
    }


    // [M] 289      Game of Life
    public void gameOfLife(int[][] board) {
        int M = board.length, N = board[0].length;

        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (isAlive(board, r, c)) board[r][c] += 10;
            }
        }

        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                board[r][c] /= 10;
            }
        }

    }
    private boolean isAlive(int[][] board, int r, int c) {
        int neiNum = neighbour(board, r, c);
        if (neiNum == 3) return true;
        if (neiNum < 2 || neiNum > 3) return false;
        if (neiNum == 2 && board[r][c] % 10 == 1) return true;
        return false;
    }
    private int neighbour(int[][] board, int r, int c) {
        int num = 0;

        if (r > 0) {
            if (board[r - 1][c] % 10 == 1) num++;
            if (c > 0 && board[r - 1][c - 1] % 10 == 1) num++;
            if (c < board[0].length - 1 && board[r - 1][c + 1] % 10 == 1) num++;
        }
        if (r < board.length - 1) {
            if (board[r + 1][c] % 10 == 1) num++;
            if (c > 0 && board[r + 1][c - 1] % 10 == 1) num++;
            if (c < board[0].length - 1 && board[r + 1][c + 1] % 10 == 1) num++;
        }
        if (c > 0 && board[r][c - 1] % 10== 1) num++;
        if (c < board[0].length - 1 && board[r][c + 1] % 10 == 1) num++;
        return num;
    }


    // [M] 82       Remove Duplicates from Sorted List II
    public ListNode deleteDuplicates1(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode virtualHead = new ListNode(-100);
        ListNode resCurr = virtualHead;
        int preNum = -110;

        ListNode curr = head;
        while (curr != null) {
            if (curr.val != preNum && (curr.next == null || curr.val != curr.next.val)) {
                resCurr.next = curr;
                resCurr = curr;
                preNum = curr.val;

                ListNode currNext = curr.next;
                curr.next = null;
                curr = currNext;
            }
            else {
                preNum = curr.val;
                curr = curr.next;
            }
        }
        return virtualHead.next;
    }


    // [M] 1836     Remove Duplicates From an Unsorted Linked List
    public ListNode deleteDuplicatesUnsorted(ListNode head) {
        Map<Integer, Integer> freq = new HashMap<>();

        ListNode curr = head;
        while (curr != null) {
            if (freq.containsKey(curr.val)) freq.replace(curr.val, freq.get(curr.val) + 1);
            else freq.put(curr.val, 1);
            curr = curr.next;
        }
        Set<Integer> chosen = new HashSet<>();
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            if (e.getValue() == 1) chosen.add(e.getKey());
        }

        ListNode virtualHead = new ListNode(-100);
        ListNode tail = virtualHead;
        curr = head;

        while (curr != null) {
            if (chosen.contains(curr.val)) {
                ListNode currNext = curr.next;

                tail.next = curr;
                tail = curr;
                curr.next = null;
                curr = currNext;
            }
            else curr = curr.next;
        }

        return virtualHead.next;

    }
    public void visualList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + " ");
            curr = curr.next;
        }
        System.out.println();
    }


    // [E] 83       Remove Duplicates from Sorted List
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        ListNode curr = head.next, tail = head;
        int preVal = head.val;
        tail.next = null;

        while (curr != null) {
            if (curr.val == preVal) {
                curr = curr.next;
            }
            else {
                ListNode currNext = curr.next;
                preVal = curr.val;
                tail.next = curr;
                tail = curr;
                curr.next = null;
                curr = currNext;
            }
        }

        return head;
    }


    // [E] 993      Cousins in Binary Tree
    boolean flag993 = true;
    public boolean isCousins(TreeNode root, int x, int y) {
        Map<Integer, Integer> data = new HashMap<>();
        postOrderDFS(root, x, y, 0, data);
        return flag993 && data.get(x) == data.get(y);
    }
    private void postOrderDFS(TreeNode curr, int x, int y, int level, Map<Integer, Integer> data) {
        if (curr == null) return;
        if (curr.val == 1) System.out.println(curr.left.val + "  " + curr.right.val);
        if (!flag993) return;
        if (curr.left != null && curr.right != null && (curr.left.val == x || curr.left.val == y) &&
                (curr.right.val == x || curr.right.val == y)) {
            flag993 = false;
            return;
        }
        data.put(curr.val, level);
        postOrderDFS(curr.left, x, y, level + 1, data);
        postOrderDFS(curr.right, x, y, level + 1, data);
    }


    // [M] 622      Design Circular Queue
    class MyCircularQueue {
        private int[] data;
        private int stIdx;
        private int edIdx;
        private int N;

        public MyCircularQueue(int k) {
            this.data = new int[k];
            this.N = 0;
            stIdx = edIdx = 0;
        }

        public boolean enQueue(int value) {
            if (isFull()) return false;

            data[edIdx++] = value;
            if (edIdx >= data.length) edIdx = 0;
            N++;
            return true;
        }

        public boolean deQueue() {
            if (isEmpty()) return false;
            data[stIdx++] = 0;
            if (stIdx >= data.length) stIdx = 0;
            N--;
            return true;
        }

        public int Front() {
            if (isEmpty()) return -1;
            else return data[stIdx];
        }

        public int Rear() {
            if (isEmpty()) return -1;
            if (edIdx == 0) return data[data.length - 1];
            else return data[edIdx - 1];
        }

        public boolean isEmpty() {
            return N == 0;
        }

        public boolean isFull() {
            return N == data.length;
        }
    }


    // [M] 641      Design Circular Deque
    class MyCircularDeque {

        private int[] data;
        private int N;
        private int stIdx;
        private int edIdx;
        public MyCircularDeque(int k) {
            this.data = new int[k];
            N = 0;
            if (k == 0)
                stIdx = edIdx = 0;
            else {
                stIdx = 0;
                edIdx = 1;
            }
        }

        public boolean insertFront(int value) {
            if (isFull()) return false;
            data[stIdx--] = value;
            if (stIdx < 0) stIdx = data.length - 1;

            N++;
            return true;
        }

        public boolean insertLast(int value) {
            if (isFull()) return false;
            data[edIdx++] = value;
            if (edIdx >= data.length) edIdx = 0;
            N++;
            return true;
        }

        public boolean deleteFront() {
            if (isEmpty()) return false;
            stIdx++;
            if (stIdx >= data.length) stIdx = 0;
            N--;
            return true;
        }

        public boolean deleteLast() {
            if (isEmpty()) return false;
            edIdx--;
            if (edIdx < 0) edIdx = data.length - 1;
            N--;
            return true;
        }

        public int getFront() {
            if (isEmpty()) return -1;
            if (stIdx == data.length - 1) return data[0];
            else return data[stIdx + 1];
        }

        public int getRear() {
            if (isEmpty()) return -1;

            if (edIdx == 0) return data[data.length - 1];
            else return data[edIdx - 1];

        }

        public boolean isEmpty() {
            return N == 0;
        }

        public boolean isFull() {
            return N == data.length;
        }
    }


    // [M] 73       Search a 2D Matrix
    public boolean searchMatrix(int[][] matrix, int target) {
        /*
        this solution uses two binary searches
         */
        int row = binarySearchRow(matrix, target);
        if (row < 0) return false;
        else return binarySearchElement(matrix[row], target);
    }
    private int binarySearchRow(int[][] matrix, int target) {       // binary search for rows
        int lo = 0, hi = matrix.length - 1, mid = (lo + hi) / 2;
        while (lo <= hi) {
            if (target < matrix[lo][0] || target > matrix[hi][matrix[hi].length - 1]) return -1;
            if (target >= matrix[mid][0] && target <= matrix[mid][matrix[mid].length - 1]) return mid;

            if (target < matrix[mid][0]) hi = mid - 1;
            else if (target > matrix[mid][matrix[mid].length - 1]) lo = mid + 1;
            mid = (lo + hi) / 2;
        }
        return -1;
    }
    private boolean binarySearchElement(int[] a, int target) {      // binary search for element
        int lo = 0, hi = a.length - 1, mid = (lo + hi) / 2;
        while (lo <= hi) {
            if (a[lo] == target) return true;
            if (a[hi] == target) return true;
            if (a[mid] == target) return true;

            if (target < a[mid]) hi = mid - 1;
            else lo = mid + 1;
            mid = (lo + hi) / 2;
        }
        return false;
    }






    public static void main(String[] args) {
        Leetcode1018 lc = new Leetcode1018();
        ListNode q = new ListNode(3);
        ListNode w = new ListNode(2);
        ListNode e = new ListNode(2);
        ListNode r = new ListNode(1);
        ListNode t = new ListNode(3);
        ListNode y = new ListNode(2);
        ListNode u = new ListNode(4);

        q.next = w;
        w.next = e;
        e.next = r;
        r.next = t;
        t.next = y;
        y.next = u;

        ListNode res = lc.deleteDuplicatesUnsorted(q);
        lc.visualList(res);

    }
}
