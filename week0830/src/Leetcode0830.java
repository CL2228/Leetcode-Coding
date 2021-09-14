import java.util.*;
import java.util.Stack;


public class Leetcode0830 {

    private class TreeNode {
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

    static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };


    // [E] 144      Binary Tree Preorder Traversal
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;
        travelPreorder(root, result);
        return result;
    }
    private void travelPreorder(TreeNode curr, List<Integer> result) {
        if (curr == null) return;
        result.add(curr.val);
        travelPreorder(curr.left, result);
        travelPreorder(curr.right, result);
    }
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            result.add(curr.val);
            if (curr.right != null)
                stack.push(curr.right);
            if (curr.left != null)
                stack.push(curr.left);
        }
        return result;
    }


    // [E] 94       Binary Tree Inorder Traversal
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;
        travelInorder(root, result);
        return result;
    }
    private void travelInorder(TreeNode curr, List<Integer> result) {
        if (curr == null) return;
        travelInorder(curr.left, result);
        result.add(curr.val);
        travelInorder(curr.right, result);
    }


    // [E] 145      Binary Tree Postorder Traversal
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        travelPostorder(root, result);
        return result;
    }
    private void travelPostorder(TreeNode curr, List<Integer> result) {
        if (curr == null) return;
        travelPostorder(curr.left, result);
        travelPostorder(curr.right, result);
        result.add(curr.val);
    }


    // [E] 590      N-ary Tree Postorder Traversal
    public List<Integer> postorder(Node root) {
        List<Integer> result = new LinkedList<>();
        NAryTravelPostorder(root, result);
        return result;
    }
    private void NAryTravelPostorder(Node curr, List<Integer> result) {
        if (curr == null) return;
        for (Node child : curr.children)
            NAryTravelPostorder(child, result);
        result.add(curr.val);
    }


    // [E] 589      N-ary Tree Preorder Traversal
    public List<Integer> preorder(Node root) {
        List<Integer> result = new LinkedList<>();
        NaryTravelPreorder(root, result);
        return result;
    }
    private void NaryTravelPreorder(Node curr, List<Integer> result) {
        if (curr == null) return;
        result.add(curr.val);
        for (Node child : curr.children)
            NaryTravelPreorder(child, result);
    }


    // [H] 321      Create Maximum Number
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        PQ pq = new PQ(nums1.length + nums2.length);
        for (int i = 0; i < nums1.length; i++)
            pq.insert(new NumNode(nums1[i], i));
        for (int i = 0; i < nums2.length; i++)
            pq.insert(new NumNode(nums2[i], i + nums1.length));

        NumNode[] resultNode = new NumNode[k];
        int[] result = new int[k];

        for (int i = 0; i < k; i++)
            resultNode[i] = pq.delMax();
        quickSort(resultNode, 0, resultNode.length - 1);
        for (int i = 0; i < k; i++)
            result[i] = resultNode[i].val;
        return result;
    }
    public void quickSort(NumNode[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, lo, j);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);
    }
    private int partition(NumNode[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i].index <= a[lo].index)
                if (i >= hi) break;
            while (a[--j].index >= a[lo].index)
                if (j <= lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;
    }
    private void exch(NumNode[] a, int i, int j) {
        NumNode swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private static class NumNode implements Comparable<NumNode> {
        private int val;
        private int index;
        public NumNode(int val, int index) {
            this.val = val;
            this.index = index;
        }
        public int compareTo(NumNode that) {
            if (this.val > that.val) return 1;
            else if (this.val < that.val) return -1;
            else {
                if (this.index < that.index) return 1;
                else if (this.index > that.index) return -1;
                else return 0;
            }
        }
    }
    private static class PQ {
        private NumNode[] data;
        private int n;
        public PQ(int N) { this.data = new NumNode[N + 1];}

        public boolean isEmpty() { return  n == 0; }
        public int size() { return n; }
        public NumNode max() {
            if (!isEmpty()) return data[0];
            else return null;
        }
        public NumNode delMax() {
            if (isEmpty()) return null;
            NumNode max = data[1];
            exch(1, n--);
            sink(1);
            data[n + 1] = null;
            return max;
        }

        public void insert(NumNode x) {
            data[++n] = x;
            swim(n);
        }

        private void swim(int k) {
            while (k > 1 && less(k / 2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        }
        private void sink(int k) {
            int N = size();
            while (2 * k <= N) {
                int j = 2 * k;
                if (j < n && less(j, j + 1)) j++;
                if (!less(k, j)) break;
                exch(k, j);
                k = j;
            }
        }
        private void exch(int i, int j) {
            NumNode swap = data[i];
            data[i] = data[j];
            data[j] = swap;
        }
        private boolean less(int i, int j) {
            return data[i].compareTo(data[j]) < 0;
        }
    }


    // [M] 347      Top K Frequent Elements
    public int[] topKFrequent(int[] nums, int k) {
        int[] result = new int[k];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            if (!map.containsKey(i)) map.put(i, 1);
            else map.replace(i, map.get(i) + 1);
        }

        PriorityQueue<Node347> pq = new PriorityQueue<>(map.size());
        for (int key : map.keySet())
            pq.offer(new Node347(key, map.get(key)));
        for (int i = 0; i < map.size() - k; i++)
            pq.poll();
        int index = 0;
        for (Node347 n : pq)
            result[index++] = n.val;
        return result;
    }
    private static class Node347 implements Comparable<Node347>{
        private int val;
        private int freq;
        public Node347(int val, int freq) { this.val = val; this.freq = freq; }
        public int compareTo(Node347 that) {
            if (this.freq < that.freq) return -1;
            else if (this.freq > that.freq) return 1;
            else return 0;
        }
    }


    // [M] 215      Kth Largest Element in an Array;
    public int findKthLargest(int[] nums, int k) {
        int target = nums.length - k;
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int re = partition(nums, lo, hi);
            exch(nums, lo, re);
            if (re > target) hi = re - 1;
            else if (re < target) lo = re + 1;
            else return nums[re];
        }
        return nums[lo];
    }
    public int partition(int[] a, int lo, int hi) {
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
    public void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private void quickSort215(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition215(a, lo, hi);
        exch215(a, j, lo);
        quickSort215(a, lo, j - 1);
        quickSort215(a, j + 1, hi);
    }
    private void exch215(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition215(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] >= a[lo])
                if (i >= hi) break;
            while (a[--j] <= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch215(a, i, j);
        }
        return j;
    }


    // [M] 451      Sort Characters By Frequency
    public String frequencySort(String s) {
        int N = s.length();
        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);
            if (!map.containsKey(c))
                map.put(c, 1);
            else
                map.replace(c, map.get(c) + 1);
        }

        Node451[] nodeArr = new Node451[map.size()];
        int i = 0;
        for (Character key : map.keySet())
            nodeArr[i++] = new Node451(key, map.get(key));

        sort451(nodeArr, 0, nodeArr.length - 1);
        for (Node451 node : nodeArr) {
            for (i = 0; i < node.freq; i++)
                sb.append(node.val);
        }
        return sb.toString();
    }
    private class Node451 {
        private char val;
        private int freq;
        public Node451(char val, int freq) {
            this.val = val;
            this.freq = freq;
        }
    }
    private void sort451(Node451[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition451(a, lo, hi);
        exch451(a, lo, j);
        sort451(a, lo, j - 1);
        sort451(a, j + 1, hi);
    }
    private void exch451(Node451[] a, int i, int j) {
        Node451 swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition451(Node451[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i].freq >= a[lo].freq)
                if (i >= hi) break;
            while (a[--j].freq <= a[lo].freq)
                if (j <= lo) break;
            if (i >= j) break;
            exch451(a, i, j);
        }
        return j;
    }


    // [E] 387      First Unique Character in a String
    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) map.replace(c, Integer.MAX_VALUE);
            else map.put(c, i);
        }
        int index = Integer.MAX_VALUE;
        for (char key : map.keySet()) {
            int tmpIdx = map.get(key);
            if (tmpIdx < index) index = tmpIdx;
        }

        if (index < Integer.MAX_VALUE) return index;
        else return -1;
    }


    public static void main(String[] args) {
        Leetcode0830 leetcode0830 = new Leetcode0830();
        String a = "aadaada";
        System.out.println(leetcode0830.firstUniqChar(a));
    }

}


