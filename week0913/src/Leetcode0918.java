import java.util.*;

public class Leetcode0918 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    // [E] 1290     Convert Binary Number in a Linked List to Integer
    public int getDecimalValue(ListNode head) {
        int num = 0;
        while (head != null) {
            num *= 2;
            num += head.val;
            head = head.next;
        }
        return num;
    }


    // [M] 474      Ones and Zeros
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        for (String str : strs) {
            int zeroNum = 0, oneNum = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '1') oneNum++;
                else zeroNum++;
            }
            for (int r = m; r >= zeroNum; r--) {
                for (int c = n; c >= oneNum; c--) {
                    dp[r][c] = Math.max(dp[r][c], dp[r - zeroNum][c - oneNum] + 1);
                }
            }
        }





        return dp[m][n];
    }


    // [M] 416      Partition Equal Subset Sum
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i : nums) sum += i;
        if (sum % 2 != 0) return false;
        sum /= 2;
        int[] dp = new int[sum + 1];
        for (int n : nums) {
            for (int j = sum; j >= n; j--)
                dp[j] = Math.max(dp[j], dp[j - n] + n);
        }
        return dp[sum] == sum;
    }


    // [M] 698      Partition to K Equal Sum Subsets
    int N;
    int sum;
    int count;
    public boolean canPartitionKSubsets(int[] nums, int k) {
        N = nums.length;
        count = 0;
        sum = 0;
        Arrays.sort(nums);
        for (int n : nums) sum += n;
        if (sum % k != 0) return false;
        sum /= k;
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        boolean[] visited = new boolean[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (!visited[i]) {
                backtracking698(nums, visited, i, 0, path, ans);
            }
        }

        System.out.println(ans);
        if (count == k) return true;
        else return false;
    }
    // 1: exceed;   2: answer found;    0: keep going
    private boolean backtracking698(int[] nums, boolean[] visited, int idx, int preSum, List<Integer> path, List<List<Integer>> ans) {
        int curSum = preSum + nums[idx];
        if (curSum > sum) return false;

        visited[idx] = true;
        N--;
        path.add(nums[idx]);

        if (curSum == sum) {
            count++;
            ans.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return true;
        }
        else {
            for (int i = idx + 1; i < nums.length; i++) {
                if (!visited[i]) {
                    if (backtracking698(nums, visited, i, curSum, path, ans)) {
                        path.remove(path.size() - 1);
                        return true;
                    }
                }
            }
        }

        path.remove(path.size() - 1);

        visited[idx] = false;
        N++;
        return false;
    }


    // [M] 518      Coin Change 2
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int c : coins) {
            for (int j = c; j < amount + 1; j++) dp[j] = dp[j] + dp[j - c];
        }
        return dp[amount];
    }


    // [E] 1346     Check If N and Its Double Exist
    public boolean checkIfExist(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int i : arr) {
            if (set.contains(i * 2)) return true;
            else if (i % 2 == 0 && set.contains(i / 2)) return true;
            else set.add(i);
        }
        return false;
    }


    // [M] 377      Combination Sum IV
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int j = 0; j < target + 1; j++) {
            for (int num : nums) if (j >= num) dp[j] += dp[j - num];
        }
        return dp[target];
    }


    // [E] 70       Climbing Stairs
    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int j = 0; j < n + 1; j++) {
            for (int i = 1; i <= 2; i++) if (j >= i) dp[j] += dp[j - i];
        }
        return dp[n];
    }


    // [M] 322      Coin Change
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        for (int j = 0; j < amount + 1; j++) {
            for (int i = 0; i < coins.length; i++) {
                if (j == coins[i]) dp[j] = 1;
                if (j > coins[i] && dp[j - coins[i]] != 0) {
                    if (dp[j] == 0) dp[j] = dp[j - coins[i]] + 1;
                    else dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                }
            }
        }
        if (dp[amount] == 0) return -1;
        else return dp[amount];
    }


    // [E] 1137     N-th Tribonacci Number
    public int tribonacci(int n) {
        if (n == 0) return 0;
        else if (n == 1) return 1;
        else if (n == 2) return 1;

        int t_3 = 0;
        int t_2 = 1;
        int t_1 = 1;
        int curr= -1;
        for (int i = 3; i <= n; i++) {
            curr = t_3 + t_2 + t_1;
            t_3 = t_2;
            t_2 = t_1;
            t_1 = curr;
        }
       return curr;
    }


    // [M] 279      Perfect Squares
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int j = 0; j < n + 1; j++) {
            for (int i = 1; i * i <= n; i++) {
                if (j == i * i) dp[j] = 1;
                else if (j > i * i && dp[j - i * i] != 0) {
                    if (dp[j] == 0) dp[j] = dp[j - i * i] + 1;
                    else dp[j] = Math.min(dp[j], dp[j - i * i] + 1);
                }
            }
        }
        return dp[n];
    }



    public static void main(String[] args) {
        Leetcode0918 lc = new Leetcode0918();
        int[] n = {1};
        System.out.println(lc.numSquares(13));
    }
}
