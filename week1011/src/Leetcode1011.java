import java.util.*;

public class Leetcode1011 {

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

    interface BinaryMatrix {
        public int get(int row, int col);
        public List<Integer> dimensions();
    };


    // [H] 174      Dungeon Game
    public int calculateMinimumHP(int[][] dungeon) {
        /**
         * this solution uses dynamic programming
         *
         * the dp[i][j] represents the minimum HP needed to reach the end from this tile
         *
         * for each tile, it can choose to go rightward or downward,
         * choose the optimal case based on the HP need on the right tile or the bottom tile
         */
        int M = dungeon.length, N = dungeon[0].length;
        int[] dp = new int[N];
        if (dungeon[M - 1][N - 1] > 0) dp[N - 1] = 1;       // initialization of the corner
        else dp[N - 1] = -dungeon[M - 1][N - 1] + 1;

        // initialization of the dp array (the bottom border)
        for (int c = N - 2; c >= 0; c--) {
            int tmp = dp[c + 1] - dungeon[M - 1][c];
            if (tmp <= 0) tmp = 1;
            dp[c] = tmp;
        }


        // dy computing
        for (int r = M - 2; r >= 0; r--) {
            int[] tmpDP = new int[N];
            for (int c = N - 1; c >= 0; c--) {
                if (c == N - 1) {                       // if it is at the right border, can only move downward
                    int tmp = dp[c] - dungeon[r][c];
                    if (tmp <= 0) tmp = 1;
                    tmpDP[c] = tmp;
                }
                else {                                  // else, choose the better one
                    int tmp = Math.min(tmpDP[c + 1], dp[c]) - dungeon[r][c];
                    if (tmp <= 0) tmp = 1;
                    tmpDP[c] = tmp;
                }
            }
            dp = tmpDP;                                 // update the dp array
        }
//        for (int[] row : dp) {
//            for (int ele : row) System.out.print(ele + " ");
//            System.out.println();
//        }
        return dp[0];
    }


    // [H] 72       Edit Distance
    public int minDistance(String word1, String word2) {
        int M = word1.length(), N = word2.length();
        int[][] dp = new int[M + 1][N + 1];

        for (int r = 0; r < M + 1; r++) dp[r][0] = r;
        for (int c = 0; c < N + 1; c++) dp[0][c] = c;

        for (int r = 1; r < M + 1; r++) {
            char sourceChar = word1.charAt(r - 1);
            for (int c = 1; c < N + 1; c++) {
                char destChar = word2.charAt(c - 1);
                if (sourceChar == destChar) dp[r][c] = dp[r - 1][c - 1];
                else dp[r][c] = Math.min(dp[r - 1][c], Math.min(dp[r][c - 1], dp[r - 1][c - 1])) + 1;
            }
        }
        return dp[M][N];
    }


    // [M] 50       Pow(x, n)
    public double myPow(double x, int n) {
        return Math.pow(x, (double)n);
    }


    // [M] 921      Minimum Add to Make Parentheses Valid
    public int minAddToMakeValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') stack.push('(');
            else {
                if (stack.isEmpty() || stack.peek() == ')') stack.push(')');
                else stack.pop();
            }
        }

        return stack.size();
    }
    public int minAddToMakeValid1(String s) {
        /**
         * this solution uses the method:
         *  1. if the curr one is a '(', leftNum++
         *  2. if the curr one is a ')', if leftNum > 0, leftNum--; else numAdd++;
         *
         *  3. when the loop over, add the leftNum
         */
        int numAdd = 0;

        int leftNum = 0, rightNum = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') leftNum++;
            else if (s.charAt(i) == ')') {
                if (leftNum > 0) leftNum--;
                else numAdd++;
            }
        }
        numAdd += leftNum;
        return numAdd;
    }


    // [M] 1344     Angle Between Hands of a Clock
    public double angleClock(int hour, int minutes) {
        double hourBased = 30 * (double)(hour % 12);
        double minuteAngle = 360 * ((double) minutes) / 60;
        double hourAngle = hourBased + minuteAngle / 12;
        double distance = Math.abs(minuteAngle - hourAngle);
        if (distance > 180) return 360 - distance;
        else return distance;
    }


    // [M] 1382     Balance a Binary Search Tree
    public TreeNode balanceBST(TreeNode root) {
        /**
         *  this solution uses inorder traversal to get an array of nodes
         *  and divide and conquer to construct the node
         */
        List<TreeNode> data = new ArrayList<>();
        inorderTraversal(root, data);
        return constructTree(data, 0, data.size() - 1);

    }
    private void inorderTraversal(TreeNode curr, List<TreeNode> data) {
        if (curr == null) return;
        inorderTraversal(curr.left, data);
        data.add(curr);
        inorderTraversal(curr.right, data);
    }
    private TreeNode constructTree(List<TreeNode> data, int lo, int hi) {
        if (lo > hi) return null;
        int mid = (lo + hi) / 2;
        TreeNode curr = data.get(mid);
        curr.left = constructTree(data, lo, mid - 1);
        curr.right = constructTree(data, mid + 1, hi);
        return curr;
    }


    // [M] 1428     Leftmost Column with at Least a One
    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        /**
         * this solution scans the rightmost element of each row, if it's 1, add the row to the WL
         * start by scanning the first row in the WL, if it meets a 0 or has reached the left border, scan the next row
         */
        List<Integer> size = binaryMatrix.dimensions();
        int M = size.get(0), N = size.get(1);
        Queue<Integer> rowWL = new LinkedList<>();
        for (int r = 0; r < M; r++)
            if (binaryMatrix.get(r, N - 1) == 1) rowWL.offer(r);

        if (rowWL.isEmpty()) return -1;

        int columnIdx = N - 1;
        while (columnIdx >= 0 && !rowWL.isEmpty()) {
            int currRow = rowWL.poll();
            while (columnIdx >= 0 && binaryMatrix.get(currRow, columnIdx) == 1) columnIdx--;
        }

        return columnIdx + 1;
    }


    // [M] 1570     Dot Product of Two S
    class SparseVector {
        Set<Integer> set;
        private int[] data;
        SparseVector(int[] nums) {
            set = new HashSet<>();
            for (int i = 0; i < nums.length; i++)
                if (nums[i] != 0) set.add(i);
            data = nums;
        }

        // Return the dotProduct of two sparse vectors
        public int dotProduct(SparseVector vec) {
            int sum = 0;
            for (int key : set) {
                if (vec.set.contains(key)) sum += this.data[key] * vec.data[key];
            }
            return sum;
        }
    }


    // [M] 1763     Buildings With an Ocean View
    public int[] findBuildings(int[] heights) {
        Stack<Integer> indices = new Stack<>();
        int maxHeight = -1;
        for (int i = heights.length - 1; i >= 0; i--) {
            if (heights[i] > maxHeight) {
                indices.push(i);
                maxHeight = heights[i];
            }
        }
        int[]res = new int[indices.size()];
        int idx = 0;
        while(!indices.isEmpty()) res[idx++] = indices.pop();
        return res;
    }


    // [M] 636      Exclusive Time of Functions
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] timeInterval = new int[n];

        int stackTopTimeStamp = 0;
        boolean stackTopStart = false;
        Stack<Integer> stack = new Stack<>();

        // 1. if the curr log is a start type:
        //      a. if the stack is empty, just add it and do nothing
        //      b. if the previous log is start, for that process, its time increase by t - TS
        //      c. if the previous log is end, for the peek process, its time increase by t - TS - 1
        // 2. if the curr log is an end type:
        //      a. if the previous state is start for the peek process, its duration increases by t - TS + 1
        //      b. if the previous state is an end, for the peek process, its duration increases by t - TS

        for (String s : logs) {
            int[] log = getLog(s);

            if (log[1] == 0) {              // it's a start event
                if (stack.isEmpty()) stack.push(log[0]);
                else if (stackTopStart) {
                    timeInterval[stack.peek()] += log[2] - stackTopTimeStamp;
                    stack.push(log[0]);
                }
                else if (!stackTopStart) {
                    timeInterval[stack.peek()] += log[2] - stackTopTimeStamp - 1;
                    stack.push(log[0]);
                }

                stackTopStart = true;
            }
            else {
                int idx = stack.pop();
                if (stackTopStart) timeInterval[idx] += log[2] - stackTopTimeStamp + 1;
                else timeInterval[idx] += log[2] - stackTopTimeStamp;
                stackTopStart = false;
            }
            stackTopTimeStamp = log[2];
        }
        return timeInterval;
    }
    private int[] getLog(String logStr) {
        /**
         * return : [ID,  start:0/end:1, timestamp]
         */
        int[] log = new int[3];
        String[] logs = logStr.split(":");
        log[0] = Integer.parseInt(logs[0]);
        if (logs[1].equals("end")) log[1] = 1;
        log[2] = Integer.parseInt(logs[2]);
        return log;
    }


    // [M] 670      Maximum Swap
    public int maximumSwap(int num) {
        char[] numStr = String.valueOf(num).toCharArray();

        for (int i = 0; i < numStr.length; i++) {
            char currMax = numStr[i];
            int maxIdx = -1;

            for (int j = i + 1; j < numStr.length; j++) {       // loop to find the answer
                if (numStr[j] > numStr[i]) {
                    if (numStr[j] >= currMax) {
                        currMax = numStr[j];
                        maxIdx = j;
                    }
                }
            }
            if (currMax > numStr[i]) {      // swap if the answer is found
                char swap = numStr[i];
                numStr[i] = numStr[maxIdx];
                numStr[maxIdx] = swap;
                break;
            }
        }
        return Integer.parseInt(String.valueOf(numStr));
    }





    public static void main(String[] args) {
        Leetcode1011 lc = new Leetcode1011();
        int a = 27367;
        System.out.println(lc.maximumSwap(a));
    }
}
