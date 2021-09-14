import java.util.*;

public class Leetcode0902 {


    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
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
    }


    // [E] 226      Invert Binary Tree
    public TreeNode invertTree(TreeNode root) {
        invert226(root);
        return root;
    }
    private void invert226(TreeNode curr) {
        if (curr == null) return;
        if (curr.left == null && curr.right == null) return;
        TreeNode swap = curr.left;
        curr.left = curr.right;
        curr.right = swap;
        if (curr.left != null) invert226(curr.left);
        if (curr.right != null) invert226(curr.right);
    }


    // [M] 654      Maximum Binary Tree
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructSubTree654(nums, 0, nums.length - 1);
    }
    private TreeNode constructSubTree654(int[] nums, int lo, int hi) {
        if (lo > hi) return null;
        int max = nums[lo];
        int idx = lo;
        for (int i = lo; i <= hi; i++) {
            if (nums[i] > max) {
                max = nums[i];
                idx = i;
            }
        }
        TreeNode son = new TreeNode(max);
        son.left = constructSubTree654(nums, lo, idx - 1);
        son.right = constructSubTree654(nums, idx + 1, hi);
        return son;
    }


    // [E] 101      Symmetric Tree
    public boolean isSymmetric(TreeNode root) {
        return checkSymmetric101(root.left, root.right);
    }
    private boolean checkSymmetric101(TreeNode curr, TreeNode mirror) {
        if (curr == null && mirror == null) return true;
        if (curr == null || mirror == null) return false;
        if (curr.val != mirror.val) return false;
        inverseNode(mirror);
        boolean result = checkSymmetric101(curr.left, mirror.left);
        result = result && checkSymmetric101(curr.right, mirror.right);
        return result;
    }
    private void inverseNode(TreeNode curr) {
        if (curr == null) return;
        TreeNode swap = curr.left;
        curr.left = curr.right;
        curr.right = swap;
    }


    // [E] 100      Same Tree
    public boolean isSameTree(TreeNode p, TreeNode q) {
        return checkSame100(p, q);
    }
    private boolean checkSame100(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        else if (p == null || q == null) return false;
        else if (p.val != q.val) return false;
        boolean result = checkSame100(p.left, q.left);
        result = result && checkSame100(p.right, q.right);
        return result;
    }


    // [E] 572      Subtree of Another Tree
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        return checkSub(root, subRoot);
    }
    private boolean checkSub(TreeNode inquiry, TreeNode support) {
        boolean result = checkSame572(inquiry, support);
        if (result) return true;
        if (inquiry.left != null && checkSub(inquiry.left, support)) return true;
        if (inquiry.right != null && checkSub(inquiry.right, support)) return true;
        return false;
    }
    private boolean checkSame572(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        else if (p == null || q == null) return false;
        else if (p.val != q.val) return false;
        boolean result = checkSame572(p.left, q.left);
        result = result && checkSame572(p.right, q.right);
        return result;
    }


    // [M] 508      Most Frequent Subtree Sum
    public int[] findFrequentTreeSum(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
        calSum508(root, map);
        int max = 0;
        for (int key : map.keySet()) {
            int count = map.get(key);
            if (count > max)
                max = count;
        }
        Deque<Integer> deque = new LinkedList<>();
        for (int key : map.keySet())
            if (map.get(key) == max) deque.offerLast(key);
        int[] result = new int[deque.size()];
        max = 0;
        while (!deque.isEmpty())
            result[max++] = deque.removeLast();
        return result;

    }
    private void calSum508(TreeNode curr, Map<Integer, Integer> map) {
        int sum = 0;
        if (curr.left != null) calSum508(curr.left, map);
        if (curr.right != null) calSum508(curr.right, map);
        if (curr.left != null) sum += curr.left.val;
        if (curr.right != null) sum += curr.right.val;
        curr.val += sum;
        if (map.containsKey(curr.val)) map.replace(curr.val, map.get(curr.val) + 1);
        else map.put(curr.val, 1);
    }


    // [E] 104      Maximum Depth of Binary Tree
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }


    // [E] 559      Maximum Depth of N-ary Tree
    public int maxDepth(Node root) {
        if (root == null) return 0;
        int max = 0;
        for (Node child : root.children) {
            int depthChild = maxDepth(child);
            if (depthChild > max) max = depthChild;
        }
        return max + 1;
    }



    public static void main(String[] args) {
        Leetcode0902 lc = new Leetcode0902();
        TreeNode c = new TreeNode(2);
        TreeNode b = new TreeNode(-3);

        TreeNode a = new TreeNode(5); a.left = b; a.right = c;

        lc.findFrequentTreeSum(a);

    }
}
