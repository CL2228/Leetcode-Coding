import java.util.*;

public class Leetcode1005 {


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

    static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }


    // [M] 3        Longest Substring Without Repeating Characters
    public int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        int[] recentIndex = new int[128];
        Arrays.fill(recentIndex, -1);
        int maxLen = 1;
        int stIdx = 0;

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            if (recentIndex[curr] >= stIdx) {
                stIdx = recentIndex[curr] + 1;
            }
            recentIndex[curr] = i;
            maxLen = Math.max(maxLen, i - stIdx + 1);
        }
        return maxLen;
    }


    // [M] 1695     Maximum Erasure Value
    public int maximumUniqueSubarray(int[] nums) {
        Set<Integer> set = new HashSet<>();
        Deque<Integer> deque = new LinkedList<>();
        int currSum = 0, maxSum = 0;

        for (int i = 0; i < nums.length; i++) {
            if (!set.contains(nums[i])) {
                set.add(nums[i]);
                deque.offerLast(nums[i]);
                currSum += nums[i];
            }
            else {
                while (deque.peekFirst() != nums[i]) {
                    currSum -= deque.peekFirst();
                    set.remove(deque.pollFirst());
                }
                deque.offerLast(deque.pollFirst());
            }
            maxSum = Math.max(currSum, maxSum);
        }
        return maxSum;
    }
    public int maximumUniqueSubarray1(int[] nums) {
        // this version uses a hash map, because keys in the map are never deleted
        // so the speed sucks
        Map<Integer, Integer> map = new HashMap<>();
        Deque<Integer> deque = new LinkedList<>();
        int maxSum = 0;
        int currSum = 0;
        int stIdx = 0, edIdx = 0;

        for (edIdx = 0; edIdx < nums.length; edIdx++) {
            if (!map.containsKey(nums[edIdx])) {
                currSum += nums[edIdx];
                map.put(nums[edIdx], edIdx);
                deque.offerLast(nums[edIdx]);
            }
            else {
                int preIdx = map.get(nums[edIdx]);

                if (preIdx >= stIdx) {
                    stIdx = preIdx + 1;
                    while (deque.peekFirst() != nums[edIdx]) {
                        currSum -= deque.pollFirst();
                    }
                    deque.pollFirst();
                    deque.offerLast(nums[edIdx]);
                }
                else {
                    deque.offerLast(nums[edIdx]);
                    currSum += nums[edIdx];
                }
                map.put(nums[edIdx], edIdx);
            }
//            System.out.println(deque);
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }


    // [E] 303      Range Sum Query - Immutable
    class NumArray303 {
        private int[] prefixSum;

        public NumArray303(int[] nums) {
            this.prefixSum = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return (prefixSum[right + 1] - prefixSum[left]);
        }
    }


    // [M] 307      Range Sum Query - Mutable
    class NumArray {

        private int[] data;
        public NumArray(int[] nums) {
            this.data = nums;
        }

        public void update(int index, int val) {
            this.data[index] = val;
        }

        public int sumRange(int left, int right) {
            int sum = 0;
            for (int i = left; i <= right; i++) sum += data[i];
            return sum;
        }
    }


    // [M] 311      Sparse Matrix Multiplication
    public int[][] multiply(int[][] mat1, int[][] mat2) {
        int M1 = mat1.length, N1 = mat1[0].length, M2 = mat2.length, N2 = mat2[0].length;
        int[][] res = new int[M1][N2];

        for (int r = 0; r < M1; r++) {
            for (int c = 0; c < N2; c++) {
                int sum = 0;
                for (int i = 0; i < N1; i++) sum += mat1[r][i] * mat2[i][c];
                res[r][c] = sum;
            }
        }
        return res;
    }


    // [M] 988      Smallest String Starting From Leaf
    public String smallestFromLeaf(TreeNode root) {
        String[] res = {""};
        StringBuilder sb = new StringBuilder();
        dfs988(root, sb, res);
        return res[0];
    }
    private void dfs988(TreeNode curr, StringBuilder preStr, String[] res) {
        preStr.insert(0, (char) (curr.val + 'a'));

        if (curr.left == null && curr.right == null) {
            String subRes = preStr.toString();
            if (res[0].equals("")) res[0] = subRes;
            else if (subRes.compareTo(res[0]) < 0) res[0] = subRes;
        }
        else {
            if (curr.left != null) dfs988(curr.left, preStr, res);
            if (curr.right != null) dfs988(curr.right, preStr, res);
        }
        preStr.deleteCharAt(0);
    }


    // [M] 426      Convert Binary Search Tree to Sorted Doubly Linked List
    Node head426;
    Node pre426;
    public Node treeToDoublyList(Node root) {
        if (root == null) return null;
        inorderTravel(root);
        head426.left = pre426;
        pre426.right = head426;
        return head426;
    }
    private void inorderTravel(Node curr) {
        if (curr == null) return;
        if (curr.left != null) inorderTravel(curr.left);
        Node currRight = curr.right;

        if (head426 == null) {
            head426 = curr;
        }
        else {
            pre426.right = curr;
            curr.left = pre426;
        }
        pre426 = curr;
        curr.right = null;

        if (currRight != null) inorderTravel(currRight);
    }


    // [M] 1265     Print Immutable Linked List in Reverse
    interface ImmutableListNode {
        public void printValue(); // print the value of this node.
        public ImmutableListNode getNext(); // return the next node.
    };
    public void printLinkedListInReverse(ImmutableListNode head) {
        Stack<ImmutableListNode> stack = new Stack<>();
        ImmutableListNode curr = head;
        while (curr != null) {
            stack.push(curr);
            curr = curr.getNext();
        }
        while (!stack.isEmpty())
            stack.pop().printValue();
    }



    public static void main(String[] args) {
        Leetcode1005 lc = new Leetcode1005();
        Node root = new Node(1);
        int i = lc.treeToDoublyList(root).val;
        System.out.println(i);
    }
}
