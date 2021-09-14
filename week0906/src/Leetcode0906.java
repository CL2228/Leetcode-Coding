import java.util.*;

public class Leetcode0906 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // [E] 235      Lowest Common Ancestor of a Binary Search Tree
    public TreeNode lowestCommonAncestor236(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return root;
        if (root.left == null && root.right == null) {
            if (root == p) return p;
            else if (root == q) return q;
            else return null;
        }

        boolean leftStat = false, rightStat = false;
        TreeNode leftRes = lowestCommonAncestor(root.left, p, q);
        if (leftRes != null) leftStat = true;
        TreeNode rightRes = lowestCommonAncestor(root.right, p, q);
        if (rightRes != null) rightStat = true;


        if (leftStat && rightStat) return root;
        else if (leftStat) {
            if (root == p || root == q) return root;
            else return leftRes;
        }
        else if (rightStat) {
            if (root == p || root == q) return root;
            else return rightRes;
        }
        else {
            if (root == p || root == q) return root;
            else return null;
        }
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int lo, hi;
        if (p.val > q.val) {
            lo = q.val;
            hi = p.val;
        }
        else {
            lo = p.val;
            hi = q.val;
        }
        return lowAncestor235(root, lo, hi);
    }
    private TreeNode lowAncestor235(TreeNode curr, int lo, int hi) {
        if (curr == null) return null;
        if (curr.val >= lo && curr.val <= hi) return curr;
        else if (curr.val < lo) return lowAncestor235(curr.right, lo, hi);
        else return lowAncestor235(curr.left, lo, hi);
    }


    // [M] 701      Insert into a Binary Search Tree
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val > root.val) root.right = insertIntoBST(root.right, val);
        else if (val < root.val) root.left = insertIntoBST(root.left, val);
        return root;
    }


    // [M] 230      Kth Smallest Element in a BST
    int rank230;
    int result;
    public int kthSmallest(TreeNode root, int k) {
        kthSmall230(root, k);
        return result;
    }
    private void kthSmall230(TreeNode curr, int k) {
        if (rank230 >= k) return;
        if (curr  == null) return;
        kthSmall230(curr.left, k);
        rank230++;
        if (rank230 == k) result = curr.val;
        kthSmall230(curr.right, k);
    }


    // [E] 671      Second Minimum Node In a Binary Tree
    public int findSecondMinimumValue(TreeNode root) {
        Set<Integer> set = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.left != null) queue.offer(curr.left);
            if (curr.right != null) queue.offer(curr.right);
            set.add(curr.val);
        }
        if (set.size() <= 1) return -1;
        int[] res = new int[set.size()];
        int idx = 0;
        for (int i : set) res[idx++] = i;
        quickSort671(res, 0, set.size() - 1);
        return res[1];
    }
    private void quickSort671(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition671(a, lo, hi);
        exch671(a, lo, j);
        quickSort671(a, lo, j - 1);
        quickSort671(a, j + 1, hi);
    }
    private void exch671(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition671(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] <= a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >=j) break;
            exch671(a, i, j);
        }
        return j;
    }


    // [M] 450      Delete Node in a BST
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return root;
        if (key < root.val) root.left = deleteNode(root.left, key);
        else if (key > root.val) root.right = deleteNode(root.right, key);
        else {
            TreeNode successor = findMin(root.right);
            if (successor == null) return root.left;
            successor.right = deleteMin(root.right);
            successor.left = root.left;
            root = successor;
        }
        return root;
    }
    private TreeNode findMin(TreeNode curr) {
        if (curr == null) return null;
        if (curr.left != null) return findMin(curr.left);
        else return curr;
    }
    private TreeNode deleteMin(TreeNode curr) {
        if (curr.left == null) {
            return curr.right;
        }
        else curr.left = deleteMin(curr.left);
        return curr;
    }


    // [M] 669      Trim a Binary Search Tree
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) return null;
        if (root.val < low) return trimBST(root.right, low, high);
        else if (root.val > high) return trimBST(root.left, low, high);

        // the current one is with the range
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        return root;
    }


    // [E] 108      Convert Sorted Array to Binary Search Tree
    public TreeNode sortedArrayToBST(int[] nums) {
        return creatBST108(nums, 0, nums.length - 1);
    }
    private TreeNode creatBST108(int[] nums, int lo, int hi) {
        if (lo > hi) return null;
        if (lo == hi) return new TreeNode(nums[lo]);

        int mid = (lo + hi) / 2;
        TreeNode curr = new TreeNode(nums[mid]);
        curr.left = creatBST108(nums, lo, mid - 1);
        curr.right = creatBST108(nums, mid + 1, hi);
        return curr;
    }


    // [M] 109      Convert Sorted List to Binary Search Tree
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        Queue<Integer> queue = new LinkedList<>();
        while (head != null) {
            queue.add(head.val);
            head = head.next;
        }
        int[] data = new int[queue.size()];
        int idx = 0;
        while (!queue.isEmpty()) data[idx++] = queue.poll();
        return createBST109(data, 0, data.length - 1);
    }
    private TreeNode createBST109(int[] nums, int lo, int hi) {
        if (lo > hi) return null;
        if (lo == hi) return new TreeNode(nums[lo]);
        int mid = (lo + hi) / 2;
        TreeNode curr = new TreeNode(nums[mid]);
        curr.left = createBST109(nums, lo, mid - 1);
        curr.right = createBST109(nums, mid + 1, hi);
        return curr;
    }


    // [M] 538      Convert BST to Greater Tree
    TreeNode pre538;
    public TreeNode convertBST(TreeNode root) {
        convertBST538(root);
        return root;
    }
    private void convertBST538(TreeNode curr) {
        if (curr == null) return;
        convertBST538(curr.right);
        if (pre538 != null)
            curr.val += pre538.val;
        pre538 = curr;
        convertBST538(curr.left);
    }







    public static void intpp(int a) {
        a++;
    }

    public static void intpp(int[] a) {
        a[0]++;
    }


    public static void main(String[] args) {
//        Leetcode90906 lc = new Leetcode90906();
        int a = 3;
        int[] b = {3};
        intpp(a);
        intpp(b);
        System.out.println(a);
        System.out.println(b[0]);

    }
}
