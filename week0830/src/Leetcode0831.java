import java.util.*;

public class Leetcode0831 {

    private static class TreeNode {
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
    private class Node1 {
        public int val;
        public List<Node1> children;

        public Node1() {}

        public Node1(int _val) {
            val = _val;
        }

        public Node1(int _val, List<Node1> _children) {
            val = _val;
            children = _children;
        }
    }

    // [E] 94       Binary Tree Inorder Traversal
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;
        Stack<TreeNode> stack = new Stack<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            if (stack.peek() == null) {
                stack.pop();
                result.add(stack.pop().val);
            }
            else {
                TreeNode curr = stack.pop();
                if (curr.right != null)
                    stack.push(curr.right);
                stack.push(curr);
                stack.push(null);
                if (curr.left != null)
                    stack.push(curr.left);
            }
        }
        return result;
    }
    private void visul(Stack<TreeNode> stack) {
        for (TreeNode s : stack) {
            if (s == null) System.out.print("null  ");
            else System.out.print(s.val + "  ");
        }
        System.out.println();
    }


    // [E] 144      Binary Tree Preorder Traversal
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            if (stack.peek() == null) {
                stack.pop();
                result.add(stack.pop().val);
            }
            else {
                TreeNode curr = stack.pop();
                if (curr.right != null)
                    stack.push(curr.right);
                if (curr.left != null)
                    stack.push(curr.left);
                stack.push(curr);
                stack.push(null);
            }
        }
        return result;
    }


    // [E] 145      Binary Tree Postorder Traversal
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            if (stack.peek() == null) {
                stack.pop();
                result.add(stack.pop().val);
            }
            else {
                TreeNode curr = stack.peek();
                stack.push(null);
                if (curr.right != null)
                    stack.push(curr.right);
                if (curr.left != null)
                    stack.push(curr.left);
            }
        }
        return result;
    }


    // [M] 102      Binary Tree Level Order Traversal
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfs102(root, 1, result);
        return result;
//        return bfs102(root);
    }
    private void dfs102(TreeNode curr, int level, List<List<Integer>> result) {
        if (curr == null) return;
        if (result.size() < level)
            result.add(new ArrayList<Integer>());
        result.get(level - 1).add(curr.val);
        if (curr.left != null) dfs102(curr.left, level + 1, result);
        if (curr.right != null) dfs102(curr.right, level + 1, result);
    }
    private List<List<Integer>> bfs102(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if (root == null) return result;
        Queue<TreeNode> WL = new LinkedList<>();
        WL.offer(root);
        while (!WL.isEmpty())  {
            int len = WL.size();
            List<Integer> list = new LinkedList<>();
            while (len > 0) {
                TreeNode curr = WL.poll();
                if (curr.left != null) WL.offer(curr.left);
                if (curr.right != null) WL.offer(curr.right);
                list.add(curr.val);
                len--;
            }
            result.add(list);
        }
        return result;
    }


    // [M] 107      Binary Tree Level Order Traversal II
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> data = new ArrayList<>();
        if (root == null) return data;
        dfs107(root, 1, data);
        List<List<Integer>> result = new LinkedList<>();
        Stack<List<Integer>> stack = new Stack<>();
        for (List<Integer> list : data) stack.push(list);
        while (!stack.isEmpty()) result.add(stack.pop());
        return result;
    }
    private void dfs107(TreeNode curr, int level, List<List<Integer>> data) {
        if (curr == null) return;
        if (data.size() < level)
            data.add(new ArrayList<Integer>());
        data.get(level - 1).add(curr.val);
        if (curr.left != null) dfs107(curr.left, level + 1, data);
        if (curr.right != null) dfs107(curr.right, level + 1, data);
    }


    // [M] 199      Binary Tree Right Side View
    public List<Integer> rightSideView(TreeNode root) {
        List<List<Integer>> data = new ArrayList<>();
        List<Integer> result = new LinkedList<>();
        dfs199(root, 1, data);
        for (List<Integer> list : data)
            result.add(list.get(list.size() - 1));
        return result;
    }
    private void dfs199(TreeNode curr, int level, List<List<Integer>> data) {
        if (curr == null) return;
        if (data.size() < level)
            data.add(new ArrayList<Integer>());
        data.get(level - 1).add(curr.val);
        if (curr.left != null) dfs199(curr.left, level + 1, data);
        if (curr.right != null) dfs199(curr.right, level + 1, data);
    }


    // [E] 637      Average of Levels in Binary Tree
    public List<Double> averageOfLevels(TreeNode root) {
        List<List<Double>> data = new ArrayList<>();
        List<Double> result = new ArrayList<>();
        if (root == null) return result;
        dfs637(root, 1, data);
        for (List<Double> list : data) {
            double sum = 0.0;
            for (double i : list) sum += i;
            result.add(sum / list.size());
        }
        return result;
    }
    private void dfs637(TreeNode curr, int level, List<List<Double>> data) {
        if (curr == null) return;
        if (data.size() < level)
            data.add(new ArrayList<Double>());
        data.get(level - 1).add((double) curr.val);
        if (curr.left != null) dfs637(curr.left, level + 1, data);
        if (curr.right != null) dfs637(curr.right, level + 1, data);
    }


    // [E] 104      Maximum Depth of Binary Tree
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        List<Integer> data = new ArrayList<>();
        data.add(0);
        dfs104(root, 1, data);
        return data.get(0);
    }
    private void dfs104(TreeNode curr, int level, List<Integer> data) {
        if (curr == null) return;
        if (level > data.get(0)) {
            data.remove(0);
            data.add(level);
        }
        if (curr.left != null) dfs104(curr.left, level + 1, data);
        if (curr.right != null) dfs104(curr.right, level + 1, data);
    }


    // [M] 429      N-ary Tree Level Order Traversal
    public List<List<Integer>> levelOrder(Node1 root) {
        List<List<Integer>> data = new ArrayList<>();
        dfs429(root, 1, data);
        return data;
    }
    private void dfs429(Node1 curr, int level, List<List<Integer>> data) {
        if (curr == null) return;
        if (data.size() < level)
            data.add(new ArrayList<Integer>());
        data.get(level - 1).add(curr.val);
        for (Node1 child : curr.children)
            dfs429(child, level + 1, data);
    }


    // [M] 515      Find Largest Value in Each Tree Row
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        List<List<Integer>> data = new ArrayList<>();
        dfs515(root, 1, data);
        for (List<Integer> list : data)
            result.add(list.get(0));
        return result;
    }
    private void dfs515(TreeNode curr, int level, List<List<Integer>> data) {
        if (curr == null) return;
        if (data.size() < level) {
            data.add(new ArrayList<Integer>());
            data.get(level - 1).add(curr.val);
        }
        else {
            if (curr.val > data.get(level - 1).get(0)) {
                data.get(level - 1).remove(0);
                data.get(level - 1).add(curr.val);
            }
        }
        if (curr.left != null) dfs515(curr.left, level + 1, data);
        if (curr.right != null) dfs515(curr.right, level + 1, data);
    }


    // [M] 117      Populating Next Right Pointers in Each Node II
    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
    public Node connect1(Node root) {
        if (root == null) return null;
        List<List<Node>> data = new ArrayList<>();
        dfs116(root, 1, data);
        for (List<Node> list : data) {
            for (int i = 0; i < list.size() - 1; i++)
                list.get(i).next = list.get(i + 1);
        }
        return root;
    }
    private void dfs116(Node curr, int level, List<List<Node>> data) {
        if (data.size() < level)
            data.add(new ArrayList<Node>());
        data.get(level - 1).add(curr);
        if (curr.left != null) dfs116(curr.left, level + 1, data);
        if (curr.right != null) dfs116(curr.right, level + 1, data);
    }


    // [M] 116      Populating Next Right Pointers in Each Node
    public Node connect(Node root) {
        if (root == null) return null;
        connect116(root.left, root.right);
        return root;
    }
    private void connect116(Node left, Node right) {
        if (left == null || right == null) return;
        left.next = right;
        connect116(left.left, left.right);
        connect116(left.right, right.left);
        connect116(right.left, right.right);
    }


    // [E] 111      Minimum Depth of Binary Tree
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        List<Integer> record = new ArrayList<>();
        record.add(Integer.MAX_VALUE);
        dfs111(root, 1, record);
        return record.get(0);
    }
    private void dfs111(TreeNode curr, int level, List<Integer> record) {
        if (curr == null) return;
        if (curr.left == null && curr.right == null) {
            if (level < record.get(0)) {
                record.remove(0);
                record.add(level);
            }
        }
        else {
            if (curr.left != null) dfs111(curr.left, level + 1, record);
            if (curr.right != null) dfs111(curr.right, level + 1, record);
        }
    }





    public static void main(String[] args) {
        Leetcode0831 lc = new Leetcode0831();

    }
}
