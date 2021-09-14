import java.util.*;


public class Leetcode0904 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }


    // [M] 113      Path Sum II
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        List<Integer> path = new LinkedList<>();
        findPath113(root, result, path, 0, targetSum);
        return result;
    }
    private void findPath113(TreeNode curr, List<List<Integer>> result, List<Integer> path, int preSum, int target) {
        path.add(curr.val);
        if (curr.left == null && curr.right == null) {
            if (preSum + curr.val == target) result.add(new ArrayList<>(path));
            return;
        }
        if (curr.left != null) {
            findPath113(curr.left, result, path, preSum + curr.val, target);
            path.remove(path.size() - 1);
        }
        if (curr.right != null) {
            findPath113(curr.right, result,path, preSum + curr.val, target);
            path.remove(path.size() - 1);
        }
    }


    // [M] 106      Construct Binary Tree from Inorder and Postorder Traversal
    public TreeNode buildTree106(int[] inorder, int[] postorder) {
        return buildNode106(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }
    private TreeNode buildNode106(int[] inorder, int inLo, int inHi, int[] postorder, int postLo, int postHi) {
        if (inLo == inHi) return new TreeNode(inorder[inLo]);
        TreeNode center = new TreeNode(postorder[postHi]);
        int inCenterIdx = partition106(inorder, inLo, inHi, postorder[postHi]);
        if (inCenterIdx != inLo)
            center.left = buildNode106(inorder, inLo, inCenterIdx - 1, postorder, postLo, postLo + inCenterIdx - inLo - 1);
        if (inCenterIdx != inHi)
            center.right = buildNode106(inorder, inCenterIdx + 1, inHi, postorder, postLo + inCenterIdx - inLo, postHi - 1);
        return center;
    }
    private int partition106(int[] inorder, int lo, int hi, int val) {
        for (int i = lo; i <= hi; i++)
            if (inorder[i] == val) return i;
        return -1;
    }


    // [M] 105      Construct Binary Tree from Preorder and Inorder Traversal
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildNode105(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }
    private TreeNode buildNode105(int[] preorder, int preLo, int preHi, int[] inorder, int inLo, int inHi) {
        if (inLo == inHi) return new TreeNode(inorder[inLo]);
        TreeNode curr = new TreeNode(preorder[preLo]);
        int inCenterIdx = partition105(inorder, inLo, inHi, curr.val);
        if (inCenterIdx != inLo)
            curr.left = buildNode105(preorder, preLo + 1, preLo + inCenterIdx - inLo, inorder, inLo, inCenterIdx - 1);
        if (inCenterIdx != inHi)
            curr.right = buildNode105(preorder, preLo + inCenterIdx - inLo + 1, preHi, inorder, inCenterIdx + 1, inHi);
        return curr;
    }
    private int partition105(int[] inorder, int lo, int hi, int val) {
        for (int i = lo; i <= hi; i++)
            if (inorder[i] == val) return i;
        return -1;
    }


    // [E] 219      Contains Duplicate II
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i <= Math.min(nums.length - 1, k - 1) ;i ++) {
            if (set.contains(nums[i])) return true;
            set.add(nums[i]);
        }
        for (int i = k; i < nums.length; i++) {
            if (set.contains(nums[i])) return true;
            set.add(nums[i]);
            set.remove(nums[i - k]);
        }
        return false;
    }


    // [E] 217      Contains Duplicate
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) return true;
            set.add(num);
        }
        return false;
    }


    // [M] 220      Contains Duplicate III
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        Set<Long> set = new HashSet<>();
        for (int i = 0; i < Math.min(k, nums.length); i++) {
            if (set.contains((long)nums[i])) return true;
            for (long key : set) {
                if (Math.abs(key - nums[i]) <= t) return true;
            }
            set.add((long)nums[i]);
        }
        for (int i = k; i < nums.length; i++) {
            if (set.contains((long)nums[i])) return true;
            for (long key : set) {
                if (Math.abs(key - nums[i]) <= t) return true;
            }
            set.add((long)nums[i]);
            set.remove((long)nums[i - k]);
        }
        return false;
    }


    // [M] 654      Maximum Binary Tree
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructNode654(nums, 0, nums.length - 1);
    }
    public TreeNode constructNode654(int[] nums, int lo, int hi) {
        if (lo > hi) return null;
        int max = Integer.MIN_VALUE;
        int maxIdx = lo;
        for (int i = lo; i <= hi; i++) {
            if (nums[i] > max) {
                maxIdx = i;
                max = nums[i];
            }
        }
        TreeNode curr = new TreeNode(max);
        curr.left = constructNode654(nums, lo, maxIdx - 1);
        curr.right = constructNode654(nums, maxIdx + 1, hi);
        return curr;
    }


    // [E] 404      Sum of Left Leaves
    public int sumOfLeftLeaves(TreeNode root) {
        int[] result = new int[1];
        sumLeft404(root, result);
        return result[0];
    }
    private void sumLeft404(TreeNode curr, int[] res) {
        if (curr.left == null && curr.right == null) return;
        if (curr.left != null) {
            if (curr.left.left == null && curr.left.right == null) res[0] += curr.left.val;
            else sumLeft404(curr.left, res);
        }
        if (curr.right != null) sumLeft404(curr.right, res);
    }


    // [M] 513      Find Bottom Left Tree Value
    public int findBottomLeftValue(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        layerOrderSearch513(root, 1, list);
        return list.get(list.size() - 1);
    }
    public void layerOrderSearch513(TreeNode curr, int level, List<Integer> result) {
        if (level > result.size()) result.add(curr.val);
        if (curr.left != null) layerOrderSearch513(curr.left, level + 1, result);
        if (curr.right != null) layerOrderSearch513(curr.right, level + 1, result);
    }


    // [E] 617      Merge Two Binary Trees
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        return merge617(root1, root2);
    }
    private TreeNode merge617(TreeNode curr1, TreeNode curr2) {
        if (curr1 == null && curr2 == null) return null;
        if (curr1 == null) return curr2;
        if (curr2 == null) return curr1;
        curr1.val = curr1.val + curr2.val;
        curr1.left = merge617(curr1.left, curr2.left);
        curr1.right = merge617(curr1.right, curr2.right);
        return curr1;
    }


    // [E] 700      Search in a Binary Search Tree
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) return null;
        if (val == root.val) return root;
        else if (val < root.val) return searchBST(root.left, val);
        else return searchBST(root.right, val);
    }


    // [M] 98       Validate Binary Search Tree
    public boolean isValidBST(TreeNode root) {
        return validateBST98(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    private boolean validateBST98(TreeNode curr, long lo, long hi) {
        if (curr.left != null && !(curr.left.val < curr.val && curr.left.val > lo)) return false;
        if (curr.right != null && !(curr.right.val > curr.val && curr.right.val < hi)) return false;
        if (curr.left != null && !validateBST98(curr.left, lo, curr.val)) return false;
        if (curr.right != null && !validateBST98(curr.right, curr.val, hi)) return false;
        return true;
    }



    public static void main(String[] args) {
        Leetcode0904 lc = new Leetcode0904();
        TreeNode a = new TreeNode(2);
        TreeNode b = new TreeNode(1);
        TreeNode c = new TreeNode(3);
        a.left = b;
        a.right = c;
        System.out.println(lc.isValidBST(a));
    }
}
