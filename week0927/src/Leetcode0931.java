import java.util.*;

public class Leetcode0931 {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    };

    // [M] 159      Longest Substring with At Most Two Distinct Characters
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        // when we meet a new character, three cases will come up
        // 1. the new character is in the sliding window set,
        //      then update the rightest index of this char to current i
        // 2. the new character is not in thr sliding window set and the distinct number is less than 2:
        //      add the current char to the window set
        // 3. not in the window set and the set is full, after updating, the stIdx = ( leftest char's rightest idx) + 1
        //      remove chars whose rightest index is less than the stIdx;

        Deque<Node159> deque = new LinkedList<>();
        int maxLen = 1;
        deque.offerLast(new Node159(s.charAt(0), 0));
        int stIdx = 0;
        for (int edIdx = 1; edIdx < s.length(); edIdx++) {
            if (deque.peekLast().val == s.charAt(edIdx))            // the new character equals to the tail in the deque
                deque.peekLast().lastIdx = edIdx;
            else if (deque.peekFirst().val == s.charAt(edIdx))      // the new char equals to the head in the deque
                deque.peekFirst().lastIdx = edIdx;
            else if (deque.size() < 2)                              // not equals, but there is only on element in the deque
                deque.offerLast(new Node159(s.charAt(edIdx), edIdx));
            else {                                                  // deque is full, need to delete one from it
                if (deque.peekFirst().lastIdx < deque.peekLast().lastIdx)
                    stIdx = deque.pollFirst().lastIdx + 1;
                else stIdx = deque.pollLast().lastIdx + 1;
                deque.offerLast(new Node159(s.charAt(edIdx), edIdx));
            }
            maxLen = Math.max(maxLen, edIdx - stIdx + 1);
        }
        return maxLen;
    }
    class Node159 {
        private char val;
        private int lastIdx = 0;
        public Node159(char val, int lastIdx) {
            this.val = val;
            this.lastIdx = lastIdx;
        }
    }


    // [M] 340      Longest Substring with At Most K Distinct Characters
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // this implementation uses a hash map to store the rightest of each character

        if (k == 0) return 0;
        int maxLen = 1;

        Set<Character> set = new HashSet<>();
        set.add(s.charAt(0));
        int stIdx = 0;

        for (int edIdx = 1; edIdx < s.length(); edIdx++) {
            char cEd = s.charAt(edIdx);
            if (!set.contains(cEd)) {
                if (set.size() < k) set.add(cEd);
                else {
                    set.clear();
                    int currIdx = edIdx;
                    while (set.contains(s.charAt(currIdx)) ||set.size() < k) {
                        set.add(s.charAt(currIdx));
                        currIdx--;
                    }
                    stIdx = currIdx + 1;
                }
            }
            maxLen = Math.max(maxLen, edIdx - stIdx + 1);
        }
        return maxLen;
    }


    // [H] 224      Basic Calculator
    public int calculate(String s) {
        Deque<String> deque = new LinkedList<>();
        int stIdx = 0;

        while (stIdx < s.length()) {
            while (stIdx < s.length() && s.charAt(stIdx) == ' ') stIdx++;
            if (stIdx == s.length()) break;;

            char cSt = s.charAt(stIdx);
            // if it is an operation, just add it to the deque
            if (isOperation(cSt)) {
                if (deque.isEmpty()) deque.offerFirst("0");
                deque.offerLast(s.substring(stIdx, stIdx + 1));
                stIdx++;
            }
            else if (cSt == '(') {
                deque.offerLast(s.substring(stIdx, stIdx + 1));
                stIdx++;
            }
            else if (cSt == ')') {
                Deque<String> subCal = new LinkedList<>();
                while (!deque.peekLast().equals("(")) {
                    subCal.offerFirst(deque.pollLast());
                }
                deque.pollLast();
//                System.out.print("mark: " + subCal);
                if (subCal.peekFirst().equals("-")) subCal.offerFirst("0");
                int subRes = Integer.parseInt(subCal.pollFirst());
                while (!subCal.isEmpty()) {
                    String opera = subCal.pollFirst();
                    if (opera.equals("+"))
                        subRes += Integer.parseInt(subCal.pollFirst());
                    else subRes -= Integer.parseInt(subCal.pollFirst());
                }
                deque.offerLast(String.valueOf(subRes));
                stIdx++;
            }
            else {
                // this is a number
                int edIdx = stIdx;
                while (edIdx < s.length() && isNumber(s.charAt(edIdx))) edIdx++;
                deque.offerLast(s.substring(stIdx, edIdx));
                stIdx = edIdx;
            }
        }

        int res = Integer.parseInt(deque.pollFirst());
        while (!deque.isEmpty()) {
            if (deque.pollFirst().equals("+"))
                res += Integer.parseInt(deque.pollFirst());
            else res -= Integer.parseInt(deque.pollFirst());
        }
        return res;
    }
    private boolean isOperation(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }
    private boolean isNumber(char c) {
        return ('0' <= c && c <= '9');
    }


    // [E] 1365     How Many Numbers Are Smaller Than the Current Number
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] dup = new int[nums.length];
        System.arraycopy(nums,0, dup, 0, nums.length);
        Arrays.sort(dup);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (i == 0 || dup[i] != dup[i - 1]) map.put(dup[i], i);
        }

        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++)
            res[i] = map.get(nums[i]);
        return res;
    }
    private int binarySearch(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1, mid = (lo + hi) / 2;
        while (lo <= hi && nums[mid] != target) {
            if (nums[mid] < target) lo = mid + 1;
            else hi = mid - 1;
            mid = (lo + hi) / 2;
        }
        while (mid >= 0 && nums[mid] == target) mid--;
        return mid + 1;
    }


    // [E] 941      Valid Mountain Array
    public boolean validMountainArray(int[] arr) {
        if (arr.length < 3) return false;
        int leftIdx, rightIdx;
        for (leftIdx = 1; leftIdx < arr.length; leftIdx++) {
            if (arr[leftIdx] <= arr[leftIdx - 1]) break;
        }
        if (leftIdx == arr.length) return false;

        for (rightIdx = arr.length - 2; rightIdx >= 0; rightIdx--) {
            if (arr[rightIdx] <= arr[rightIdx + 1]) break;
        }
        if (rightIdx < 0) return false;

        return leftIdx == rightIdx + 2;
    }


    // [E] 1207     Unique Number of Occurrences
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : arr) {
            if (!frequency.containsKey(num)) frequency.put(num, 1);
            else frequency.put(num, frequency.get(num) + 1);
        }
        Set<Integer> set = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (set.contains(entry.getValue())) return false;
            else set.add(entry.getValue());
        }
        return true;
    }


    // [E] 283      Move Zeros
    public void moveZeroes(int[] nums) {
        int zeroIdx = 0, nonZeroIdx = 0;
        while (zeroIdx < nums.length && nums[zeroIdx] != 0) zeroIdx++;
        if (zeroIdx >= nums.length - 1) return;

        nonZeroIdx  = zeroIdx + 1;
        while (nonZeroIdx < nums.length && nums[nonZeroIdx] == 0) nonZeroIdx++;

        while (nonZeroIdx < nums.length) {
            exch(nums, zeroIdx, nonZeroIdx);
            zeroIdx++;
            int currIdx = nonZeroIdx + 1;
            while (currIdx < nums.length && nums[currIdx] == 0) currIdx++;
            if (currIdx == nums.length) break;
            else nonZeroIdx = currIdx;
        }
    }
    private void exch(int[] nums, int i, int j) {
        int swap = nums[i];
        nums[i] = nums[j];
        nums[j] = swap;
    }


    // [M] 189      Rotate Array
    public void rotate(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<>();
        for (int num : nums) deque.offerLast(num);

        for (int i = 0; i < k; i++)
            deque.offerFirst(deque.pollLast());

        int idx = 0;
        for (int i : deque) nums[idx++] = i;
    }


    // [E] 235      Lowest Common Ancestor of a Binary Search Tree
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        int lo, hi;
        if (p.val > q.val) {
            lo = q.val;
            hi = p.val;
        }
        else {
            lo = p.val;
            hi = q.val;
        }
        return getAns(root, lo, hi);
    }
    private TreeNode getAns(TreeNode curr, int lo, int hi) {
        if (curr == null) return null;
        if (curr.val >= lo && curr.val <= hi) return curr;
        else if (curr.val < lo) return getAns(curr.right, lo, hi);
        else return getAns(curr.left, lo, hi);
    }
    private TreeNode getAnces(TreeNode curr, TreeNode p, TreeNode q) {
        if (curr == null) return null;
        TreeNode leftRes = getAnces(curr.left, p, q);
        TreeNode rightRes = getAnces(curr.right, p, q);

        if (curr == p || curr == q) return curr;
        else if (leftRes != null && rightRes != null) return curr;
        else if (leftRes != null) return leftRes;
        else if (rightRes != null) return rightRes;
        return null;
    }


    // [M] 1644     Lowest Common Ancestor of a Binary Tree II
    int count1644 = 0;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode res = findCommonAnces1644(root, p, q);
        if (count1644 < 2) return null;
        else return res;
    }
    private TreeNode findCommonAnces1644(TreeNode curr, TreeNode p, TreeNode q) {
        if (curr == null) return null;
        TreeNode leftRes = findCommonAnces1644(curr.left, p, q);
        TreeNode rightRes = findCommonAnces1644(curr.right, p, q);

        if (curr == p || curr == q) {
            count1644++;
            return curr;
        }
        else if (leftRes != null && rightRes != null) return curr;
        else if (leftRes != null) return leftRes;
        else if (rightRes != null) return rightRes;
        else return null;
    }


    // [M] 1650     Lowest Common Ancestor of a Binary Tree III
    public Node lowestCommonAncestor(Node p, Node q) {
        Deque<Node> pathP = new LinkedList<>();
        Deque<Node> pathQ = new LinkedList<>();
        Node curr = p;
        while (curr != null) {
            pathP.offerFirst(curr);
            curr = curr.parent;
        }
        curr = q;
        while (curr != null) {
            pathQ.offerFirst(curr);
            curr = curr.parent;
        }

        Node lca = null;
        while (!pathP.isEmpty() && !pathQ.isEmpty() && pathP.peekFirst() == pathQ.peekFirst()) {
            lca = pathP.pollFirst();
            pathQ.pollFirst();
        }
        return lca;
    }


    // [M] 1676     Lowest Common Ancestor of a Binary Tree IV
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
        Set<TreeNode> set = new HashSet<>();
        for (TreeNode node : nodes) set.add(node);
        return findLCASet(root, set);
    }
    private TreeNode findLCASet(TreeNode curr, Set<TreeNode> set) {
        if (curr == null) return null;
        TreeNode leftRes = findLCASet(curr.left, set);
        TreeNode rightRes = findLCASet(curr.right, set);

        if (set.contains(curr)) return curr;
        else if (rightRes != null && leftRes != null) return curr;
        else if (leftRes != null) return leftRes;
        else if (rightRes != null) return rightRes;
        else return null;
    }


    // [M] 865      Smallest Subtree with all the Deepest Nodes
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        Set<TreeNode> leafSet = null;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offerLast(root);

        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            Set<TreeNode> levelSubSet = new HashSet<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode curr = deque.pollFirst();
                levelSubSet.add(curr);
                if (curr.left != null) deque.offerLast(curr.left);
                if (curr.right != null) deque.offerLast(curr.right);
            }
            leafSet = levelSubSet;
        }
        return findLCA865(root, leafSet);
    }
    private TreeNode findLCA865(TreeNode curr, Set<TreeNode> set) {
        if (curr == null) return null;
        TreeNode leftRes = findLCA865(curr.left, set);
        TreeNode rightRes = findLCA865(curr.right, set);

        if (set.contains(curr)) return curr;
        else if (leftRes != null && rightRes != null) return curr;
        else if (leftRes != null) return leftRes;
        else if (rightRes != null) return rightRes;
        else return null;
    }


    // [M] 1123      Lowest Common Ancestor of Deepest Leaves
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        Set<TreeNode> leafSet = null;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offerLast(root);

        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            Set<TreeNode> levelSubSet = new HashSet<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode curr = deque.pollFirst();
                levelSubSet.add(curr);
                if (curr.left != null) deque.offerLast(curr.left);
                if (curr.right != null) deque.offerLast(curr.right);
            }
            leafSet = levelSubSet;
        }
        return findLCA1123(root, leafSet);
    }
    private TreeNode findLCA1123(TreeNode curr, Set<TreeNode> set) {
        if (curr == null) return null;
        TreeNode leftRes = findLCA1123(curr.left, set);
        TreeNode rightRes = findLCA1123(curr.right, set);

        if (set.contains(curr)) return curr;
        else if (leftRes != null && rightRes != null) return curr;
        else if (leftRes != null) return leftRes;
        else if (rightRes != null) return rightRes;
        else return null;
    }


    // [M] 314      Binary Tree Vertical Order Traversal
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        if (root == null) return res;
        List<Integer>[] verticalList = new List[201];

        Deque<Node314> deque = new LinkedList<>();
        deque.offerLast(new Node314(root, 100));
        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            for (int i = 0; i < levelSize; i++) {
                Node314 curr = deque.pollFirst();
                if (verticalList[curr.verticalIdx] == null) verticalList[curr.verticalIdx] = new LinkedList<>(Arrays.asList(curr.treeNode.val));
                else verticalList[curr.verticalIdx].add(curr.treeNode.val);
                if (curr.treeNode.left != null) deque.offerLast(new Node314(curr.treeNode.left, curr.verticalIdx - 1));
                if (curr.treeNode.right != null) deque.offerLast(new Node314(curr.treeNode.right, curr.verticalIdx + 1));
            }
        }
        for (List<Integer> list : verticalList) {
            if (list != null) res.add(list);
        }

        return res;
    }
    class Node314 {
        private TreeNode treeNode;
        private int verticalIdx;
        public Node314(TreeNode treeNode, int idx) {
            this.treeNode = treeNode;
            this.verticalIdx = idx;
        }
    }






    public static void main(String[] args) {
        Leetcode0931 lc = new Leetcode0931();
        String a = "1-(-2)";
        System.out.println(lc.calculate(a));
    }
}
