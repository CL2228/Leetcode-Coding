import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class Leetcode0901 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
     }

    // Code Signal Test Practice
    public int[] mutateTheArray(int n, int[] a) {
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            int left, right;
            if (i == 0)
                left = 0;
            else left = a[i - 1];
            if (i == n - 1)
                right = 0;
            else right = a[i + 1];
            b[i] = left + a[i] + right;
        }
        return b;
    }


    // Code Signal Test Practice
    int[] mostFrequentDigits(int[] a) {
        int[] count = new int[10];
        for (int num : a) {
            while (num > 0) {
                count[num % 10]++;
                num /= 10;
            }
        }
        int max = count[0];
        for (int c : count) {
            if (c > max) max = c;
        }

        int ct = 0;
        for (int c: count)
            if (c == max) ct++;
        int[] result = new int[ct];
        int index = 0;
        for (int i = 0; i < 10; i++)
            if (count[i] == max) result[index++] = i;
        return result;
    }


    // Code Signal Test Practice
    int[][] minesweeperClick(boolean[][] field, int x, int y) {
        int R = field.length;
        int C = field[0].length;
        int[][] result = new int[R][C];
        for (int r = 0; r < R; r++)
            for (int c = 0; c < C; c++) result[r][c] = -1;

        if (!field[x][y]) {
             reveal(result, field, x, y);
        }
        return result;
    }
    private int getCount(boolean[][] filed, int x, int y) {
        int R = filed.length;
        int C = filed[0].length;
        int count = 0;
        if (x != 0) {
            if (filed[x - 1][y]) count++;
            if (y != 0 && filed[x - 1][y - 1]) count++;
            if (y != C - 1 && filed[x - 1][y + 1]) count++;
        }
        if (x != R - 1) {
            if (filed[x + 1][y]) count++;
            if (y != 0 && filed[x + 1][y - 1]) count++;
            if (y != C - 1 && filed[x + 1][y + 1]) count++;
        }
        if (y != 0 && filed[x][y - 1]) count++;
        if (y != C - 1 && filed[x][y + 1]) count++;
        return count;
    }
    private void reveal(int[][] result, boolean[][] field, int x, int y) {
        if (result[x][y] != -1) return;
        if (!field[x][y]) result[x][y] = getCount(field, x, y);
        if (result[x][y] == 0) {
            int R = field.length, C = field[0].length;
            if (x != 0) {
                if (!field[x - 1][y]) reveal(result, field, x - 1, y);
                if (y != 0 && !field[x - 1][y - 1]) reveal(result, field, x - 1, y - 1);
                if (y != C - 1 && !field[x - 1][y + 1]) reveal(result, field, x - 1, y + 1);
            }
            if (x != R - 1) {
                if (!field[x + 1][y]) reveal(result, field, x + 1, y);
                if (y != 0 && !field[x + 1][y - 1]) reveal(result, field, x + 1, y - 1);
                if (y != C - 1 && !field[x + 1][y + 1]) reveal(result, field, x + 1, y + 1);
            }
            if (y != 0 && !field[x][y - 1]) reveal(result, field, x, y - 1);
            if (y != C - 1 && !field[x][y + 1]) reveal(result,field, x, y + 1);
        }
    }


    // [M] 236      Lowest Common Ancestor of a Binary Tree
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Deque<TreeNode> forP = new LinkedList<>();
        Deque<TreeNode> forQ = new LinkedList<>();
        findNode(root, p, forP);
        findNode(root, q, forQ);
        TreeNode ans = forP.peekFirst();
        while (!forP.isEmpty() && !forQ.isEmpty()) {
            if (forP.peekFirst() != forQ.peekFirst()) break;
            else {
                ans = forP.pollFirst();
                forQ.pollFirst();
            }
        }
        return ans;
    }
    private boolean findNode(TreeNode curr, TreeNode target, Deque<TreeNode> path) {
        if (curr == target) {
            path.offerLast(curr);
            return true;
        }
        path.offerLast(curr);
        boolean result = false;
        if (curr.left != null)
            result = result || findNode(curr.left, target, path);
        if (result) return true;
        if (curr.right != null)
            result = result || findNode(curr.right, target, path);
        if (!result) path.pollLast();
        return result;
    }






    public static void main(String[] args) {
        Leetcode0901 lc = new Leetcode0901();
        int[] a = {25, 2, 3, 57, 38, 41};
        int[] re = lc.mostFrequentDigits(a);
        for (int i : re) System.out.println(i);
    }

}
