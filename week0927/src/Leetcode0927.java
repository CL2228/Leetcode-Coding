import java.util.*;

public class Leetcode0927 {

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


    // [H] 296      Best Meeting Point
    public int minTotalDistance(int[][] grid) {
        List<Integer> xCoor = new ArrayList<>();
        List<Integer> yCoor = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    xCoor.add(i);
                    yCoor.add(j);
                }
            }
        }
        int xMid = xCoor.get(xCoor.size() / 2);
        Collections.sort(yCoor);
        int yMid = yCoor.get(yCoor.size() / 2);
        return distOneDimension(xCoor, xMid) + distOneDimension(yCoor, yMid);
    }
    private int distOneDimension(List<Integer> list, int pos) {
        int dist = 0;
        for (int c : list)
            dist += Math.abs(c - pos);
        return dist;
    }


    // [E] 28       Implement strStr()
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;

        int[][] dfa = computeDFA(needle);
        int state = 0;
        for (int i = 0; i < haystack.length(); i++) {
            state = dfa[haystack.charAt(i) - 'a'][state];
            if (state == needle.length()) return i - needle.length() + 1;
        }
        return -1;
    }
    private int[][] computeDFA(String pattern) {
        int R = 26, M = pattern.length();
        int[][] dfa = new int[R][M];
        dfa[pattern.charAt(0) - 'a'][0] = 1;

        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];
            dfa[pattern.charAt(j) - 'a'][j] = j + 1;
            X = dfa[pattern.charAt(j) - 'a'][X];
        }
        return dfa;
    }


    // [M] 739      Daily Temperatures
    public int[] dailyTemperatures(int[] temperatures) {
        int[] res = new int[temperatures.length];

        Deque<Integer> deque = new LinkedList<>();
        deque.offerLast(0);

        for (int i = 1; i < temperatures.length; i++) {
            while (!deque.isEmpty() && temperatures[deque.peekLast()] < temperatures[i]){
                res[deque.peekLast()] = i - deque.peekLast();
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        return res;
    }


    // [E] 496      Next Greater Element I
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Deque<Integer> deque = new LinkedList<>();
        Map<Integer, Integer> map = new HashMap<>();
        deque.offerLast(nums2[0]);

        for (int i = 1; i < nums2.length; i++) {
            int n2i = nums2[i];
            while (!deque.isEmpty() && deque.peekLast() < n2i) {
                map.put(deque.peekLast(), n2i);
                deque.pollLast();
            }
            deque.offerLast(n2i);
        }
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.getOrDefault(nums1[i], -1);
        }
        return res;
    }


    // [M] 503      Next Greater Element II
    public int[] nextGreaterElements(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);

        Deque<Integer> deque = new LinkedList<>();
        deque.offerLast(0);

        for (int i = 1; i < nums.length; i++) {
            int numI = nums[i];
            while (!deque.isEmpty() && nums[deque.peekLast()] < numI) {
                res[deque.peekLast()] = numI;
                deque.pollLast();
            }
            deque.offerLast(i);
        }

        for (int i = 0; i < nums.length; i++) {
            int numI = nums[i];
            while (!deque.isEmpty() && nums[deque.peekLast()] < numI) {
                if (res[deque.peekLast()] == -1) res[deque.peekLast()] = numI;
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        return res;
    }


    // [M] 901      Online Stock Span
    class StockSpanner {
        class Node {
            private int value;
            private int idx;
            public Node(int val, int idx) {
                this.value = val;
                this.idx = idx;
            }
        }

        private Stack<Node> stack;
        private int N;

        public StockSpanner() {
            this.stack = new Stack<>();
            this.N = 0;
            stack.push(new Node(Integer.MAX_VALUE, -1));
        }

        public int next(int price) {
            int ans = 1;
            Node curr = null;
            while (!stack.isEmpty() && stack.peek().value <= price) {
                curr = stack.pop();
            }
            ans = N - stack.peek().idx;
            stack.push(new Node(price, N++));
            return ans;
        }
    }


    // [M] 251      Flatten 2D Vector
    class Vector2D {

        private int[][] data;
        private int N;
        private int cR, cC;

        public Vector2D(int[][] vec) {
            this.data = vec;
            for (int[] d : vec) this.N += d.length;

            this.cC = 0;
            this.cR = 0;
            while (cR < data.length && data[cR].length == 0) cR++;
        }

        public int next() {
            int rtn = data[cR][cC];

            if (cC < data[cR].length - 1) cC++;
            else {
                cC = 0;
                if (cR < data.length - 1) {
                    cR++;
                    while (cR < data.length && data[cR].length == 0) cR++;
                }
            }
            N--;
            return rtn;
        }

        public boolean hasNext() {
            return N != 0;
        }
    }


    // [M] 173      Binary Search Tree Iterator
    class BSTIterator {
        Deque<Integer> deque;

        public BSTIterator(TreeNode root) {
            deque = new LinkedList<>();
            dfs(root);
            System.out.println(deque.size());
        }

        private void dfs(TreeNode curr) {
            if (curr == null) return;
            dfs(curr.left);
            deque.offerLast(curr.val);
            dfs(curr.right);
        }

        public int next() {
            return deque.pollFirst();
        }

        public boolean hasNext() {
            return !deque.isEmpty();
        }
    }


    // [M] 285      Inorder Successor in BST
    TreeNode preNode;
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        return dfs285(root, p);
    }
    private TreeNode dfs285(TreeNode curr, TreeNode p) {
        if (curr == null) return null;
        TreeNode leftRes = dfs285(curr.left, p);
        if (leftRes != null) return leftRes;
        if (preNode == p) return curr;
        preNode = curr;
        return dfs285(curr.right, p);
    }



    // [M] 281      Zigzag Iterator
    public class ZigzagIterator {
        List<Integer> data1;
        List<Integer> data2;

        int idx1, idx2;
        int mode;

        public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
            data1 = v1;
            data2 = v2;

            idx1 = idx2 = 0;
            if (v1.isEmpty()) mode = 2;
            else mode = 1;
        }

        public int next() {
            int res = -1;

            if (mode == 1) {
                res = data1.get(idx1++);
                if (idx2 < data2.size()) mode = 2;
            }
            else if (mode == 2) {
                res = data2.get(idx2++);
                if (idx1 < data1.size()) mode = 1;
            }
            return res;
        }

        public boolean hasNext() {
            return (!(idx1 == data1.size() && idx2 == data2.size()));
        }
    }

    public static void main(String[] args) {
        Leetcode0927 lc = new Leetcode0927();
    }
}
