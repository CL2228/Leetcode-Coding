import java.util.Arrays;

public class Leetcode0912 {


    // [E] 53       Maximum Subarray
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int subSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (subSum <= 0) subSum = nums[i];
            else subSum += nums[i];
            if (subSum > max) max = subSum;
        }
        return max;
    }


    // [M] 122      Best Time to Buy and Sell Stock II
    public int maxProfit1(int[] prices) {
        int[] profit = new int[prices.length];
        int sum = 0;
        for (int i = 1; i < prices.length; i++) profit[i] = (prices[i] - prices[i - 1]);
        for (int i = 1; i < profit.length; i++) if (profit[i] > 0) sum += profit[i];
        return sum;
    }


    // [M] 55       Jump Game
    public boolean canJump(int[] nums) {
        int cover = 0;
        if (nums.length == 1) return true;
        for (int i = 0; i <= cover; i++) {
            cover = Math.max(cover, nums[i] + i);
            if (cover >= nums.length - 1) return true;
        }
        return false;
//        boolean[] visited = new boolean[nums.length];
//        return backtracking(nums, 0, visited);
    }
    private boolean backtracking(int[] nums, int idx, boolean[] visited) {
        if (idx >= nums.length - 1) return true;
        if (visited[idx]) return false;
        visited[idx] = true;
        for (int i = nums[idx]; i > 0; i--) {
            if (backtracking(nums, idx + i, visited)) return true;
        }
        return false;
    }


    // [M] 45       Jump Game II
    public int jump(int[] nums) {
        if (nums.length == 1) return 0;
        int step = 1;
        int currCover = nums[0];
        int nextCover = nums[0];
        int idx = 0;
        while (currCover < nums.length - 1) {
            for (int i = idx; i <= currCover; i++) {
                nextCover = Math.max(nextCover, i + nums[i]);
            }

            step++;
            idx = currCover + 1;
            currCover = nextCover;
        }
        return step;
//        if (nums.length == 1) return 0;
//        int[] miniStep = new int[1];
//        miniStep[0] = nums.length + 1;
//        int[] visited = new int[nums.length];
//        Arrays.fill(visited, nums.length + 1);
//        backtracking45(nums, 0, visited, 0, miniStep);
//        return miniStep[0];
    }
    private void backtracking45(int[] nums, int idx, int[] visited, int currStep, int[] res) {
        if (currStep >= res[0]) return;
        if (idx >= nums.length - 1) {
            res[0] = currStep;
            return;
        }
        if (currStep >= visited[idx]) return;

        visited[idx] = currStep;
        for (int i = nums[idx]; i > 0; i--)
            backtracking45(nums, idx + i, visited, currStep + 1, res);
    }


    // [E] 1005     Maximize Sum Of Array After K Negations
    public int largestSumAfterKNegations(int[] nums, int k) {
        int sum = 0;
        Arrays.sort(nums);
        for (int i : nums) sum += i;
        int idx;
        for (idx = 0; idx < nums.length; idx++) {
            if (k == 0) break;
            if (nums[idx] >= 0) break;
            nums[idx] = -nums[idx];
            sum += 2 * nums[idx];
            k--;
        }
        if (k > 0) {
            if (idx == 0) {
                if (k % 2 == 0) return sum;
                else return sum - 2 * nums[idx];
            }
            else if (idx >= nums.length) {
                if (k % 2 == 0) return sum;
                else return sum - 2 * nums[idx - 1];
            }
            else if (nums[idx] == 0) return sum;
            else {
                int factor = Math.min(nums[idx - 1], nums[idx]);
                if (k % 2 == 0) return sum;
                else return sum - 2 * factor;
            }
        }
        else return sum;
    }


    // [M] 134      Gas Station
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int sum = 0;
        for (int i = 0; i < gas.length; i++) sum += gas[i] - cost[i];
        if (sum < 0) return -1;

        int startIdx = 0, currSum;
        while (startIdx < gas.length) {
            currSum = 0;
            int j = startIdx;
            while (j < gas.length) {
                currSum += gas[j] - cost[j];
                if (currSum < 0) {
                    startIdx = j + 1;
                    break;
                }
                else j++;
            }
            if (j == gas.length) break;
        }
        return startIdx;
    }
    private boolean workable(int idx, int[] gas, int[] cost) {
        int currIdx = idx;
        int L = gas[currIdx];
        if (L < cost[currIdx]) return false;
        L -= cost[currIdx];
        currIdx++;
        if (currIdx >= gas.length) currIdx = 0;
        L += gas[currIdx];
        while (currIdx != idx) {
            if (L < cost[currIdx]) return false;
            L -= cost[currIdx];
            currIdx++;
            if (currIdx >= gas.length) currIdx = 0;
            L += gas[currIdx];
        }
        return true;
    }
    static class Node134 {
        private final int cost;
        private final int index;
        public Node134(int cost, int index) {
            this.cost = cost;
            this.index = index;
        }
    }
    private void quickSort134(Node134[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition134(a, lo, hi);
        exch134(a, lo, j);
        quickSort134(a, lo, j - 1);
        quickSort134(a, j + 1, hi);
    }
    private void exch134(Node134[] a, int i, int j) {
        Node134 swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition134(Node134[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i].cost <= a[lo].cost)
                if (i >= hi) break;
            while (a[--j].cost >= a[lo].cost)
                if (j <= hi) break;
            if (i >= j) break;
            exch134(a, i, j);
        }
        return j;
    }


    // [E] 121      Best Time to Buy and Sell Stock
    public int maxProfit(int[] prices) {
        if (prices.length == 1) return 0;
        int currProfit = prices[1] - prices[0];
        int maxProfit = Math.max(0, currProfit);
        int idx = 1;
        while (idx < prices.length - 1) {
            if (currProfit < 0) currProfit = prices[idx + 1] - prices[idx];
            else currProfit += prices[idx + 1] - prices[idx];
            if (currProfit > maxProfit) maxProfit = currProfit;
            idx++;
        }
        return maxProfit;
    }


    // [H] 135      Candy
    public int candy(int[] ratings) {
        if (ratings.length == 1) return 1;
        int[] candies = new int[ratings.length];
        candies[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] <= ratings[i - 1]) {
                candies[i] = 1;
                for (int idx = i; idx > 0; idx--) {
                    if (ratings[idx - 1] > ratings[idx] && candies[idx - 1] <= candies[idx]) candies[idx - 1] = candies[idx] + 1;
                    else break;
                }
            }
            else candies[i] = candies[i - 1] + 1;
        }
        int sum = 0;
        for (int c : candies) sum += c;
        return sum;
    }



    public static void main(String[] args) {
        Leetcode0912 lc = new Leetcode0912();
        int[] rating = {1,2,87,87,87,2,1};
        System.out.println(lc.candy(rating));
    }
}
