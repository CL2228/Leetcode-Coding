import java.util.*;

public class Leetcode1014 {

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
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    };

    // [M] 413      Arithmetic Slices
    public int numberOfArithmeticSlices1(int[] nums) {
        if (nums.length < 3) return 0;
        int totalNum = 0;
        int stIdx = 0, preGap = nums[1] - nums[0], edIdx = 1;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] != preGap) {      // need to start over and calculate the previous length
                totalNum += calNum(edIdx - stIdx + 1);
                stIdx = i - 1;
                edIdx = i;
                preGap = nums[i] - nums[i - 1];
            }
            else {
                edIdx = i;
            }
        }
        totalNum += calNum(edIdx - stIdx + 1);

        return totalNum;
    }
    private int calNum(int length) {
        int factor = 3, num = 0;
        while (factor <= length) {
            num += (length - factor + 1);
            factor++;
        }
        return num;
    }


    // [H] 446      Arithmetic Slices II - Subsequence
    public int numberOfArithmeticSlices(int[] nums) {
        int N = nums.length, totalNum = 0;
        Map<Long, Map<Integer, Integer>>[] dp = new Map[N];
        for (int i = 0; i < N; i++) dp[i] = new HashMap<>();

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++)
                totalNum += calculatePerNum(dp, nums, i, j);
        }

        return totalNum;
    }
    private int calculatePerNum(Map<Long, Map<Integer, Integer>>[] dp, int[] nums, int curr, int j) {
        long gap = (long)nums[curr] - (long)nums[j];
        Map<Long, Map<Integer, Integer>> currMap = dp[curr];
        Map<Long, Map<Integer, Integer>> jMap = dp[j];

        if (!currMap.containsKey(gap)) {                                    // if the gap does not exist in the curr ele's map
            Map<Integer, Integer> gapMapCurrent = new HashMap<>();          // a sub map for that gap
            gapMapCurrent.put(1, 1);                                        // the frequency of length 1 should be 1
            currMap.put(gap, gapMapCurrent);                                // add this sub map
        }
        else currMap.get(gap).replace(1, currMap.get(gap).get(1) + 1);      // the value of 1 length key should increase by 1


        int num = 0;
        Map<Integer, Integer> currGapMap = currMap.get(gap);                // the sub gap-map of curr and j
        Map<Integer, Integer> jGapMap = jMap.get(gap);
        if (jGapMap == null) return num;                                    // if does not exist, return 0
        else {
            for (Map.Entry<Integer, Integer> entry : jGapMap.entrySet()) {  // update
                int len = entry.getKey(), freq = entry.getValue();
                num += freq;
                if (currGapMap.containsKey(len + 1)) currGapMap.replace(len + 1, currGapMap.get(len + 1) + freq);
                else currGapMap.put(len + 1, freq);
            }
        }
        return num;
    }


    // [E] 961      N-Repeated Element in Size 2N Array
    public int repeatedNTimes(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) {
            if (set.contains(n)) return n;
            set.add(n);
        }
        return -1;
    }


    // [M] 1094     Car Pooling
    public boolean carPooling(int[][] trips, int capacity) {
        int[] occupation = new int[1001];
        for (int[] t : trips) {
            int people = t[0];
            if (people > capacity) return false;
            int stIdx = t[1], edIdx = t[2];
            occupation[stIdx] += people;
            occupation[edIdx] -= people;
        }
        for (int i = 1; i <= 1000; i++) {
            occupation[i] += occupation[i - 1];
            if (occupation[i] > capacity) return false;
        }
//        for (int i = 0; i < 20; i++) System.out.print(occupation[i] + " ");
        return true;
    }


    // [E] 1        Two Sum
    public int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) return new int[]{map.get(target - nums[i]), i};
            map.put(nums[i], i);
        }
        return null;
    }


    // [E] 167      Two Sum II - Input array is sorted
    public int[] twoSum(int[] numbers, int target) {
        int stIdx = 0, edIdx = numbers.length - 1;
        while (stIdx < edIdx) {
            int subSum = numbers[stIdx] + numbers[edIdx];
            if (subSum == target) return new int[]{stIdx + 1, edIdx + 1};
            else if (subSum < target) stIdx++;
            else edIdx--;
        }
        return null;
    }


    // [E] 1099     Two Sum Less Than K
    public int twoSumLessThanK(int[] nums, int k) {
        int maxSum = -1;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] < k) maxSum = Math.max(maxSum, nums[i] + nums[j]);
            }
        }
        return maxSum;
    }


    // [E] 653      Two Sum IV - Input is a BST
    public boolean findTarget(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return dfs(root, set, k);
    }
    private boolean dfs(TreeNode curr, Set<Integer> set, int k) {
        if (curr == null) return false;
        int target = k - curr.val;
        if (set.contains(target)) return true;
        set.add(curr.val);
        if (dfs(curr.left, set, k)) return true;
        if (dfs(curr.right, set, k)) return true;
        return false;
    }


    // [E] 170      Two Sum III - Data structure design
    class TwoSum {

        Map<Integer, Integer> map;
        public TwoSum() {
            this.map = new HashMap<>();
        }

        public void add(int number) {
            if (!map.containsKey(number)) map.put(number, 1);
            else map.put(number, map.get(number) + 1);
        }

        public boolean find(int value) {
            for (int key : map.keySet()) {
                int target = value - key;
                if (map.containsKey(target)) {
                    if (target != key) return true;
                    else if (map.get(key) > 1) return true;
                }
            }
            return false;
        }
    }


    // [M] 1214     Two Sum BSTs
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        Set<Integer> set1 = new HashSet<>();
        dfs1(root1, set1);
        return dfs2(root2, set1, target);
    }
    private void dfs1(TreeNode curr, Set<Integer> set) {
        if (curr == null) return;
        set.add(curr.val);
        dfs1(curr.left, set);
        dfs1(curr.right, set);
    }
    private boolean dfs2(TreeNode curr, Set<Integer> set, int k) {
        if (curr == null) return false;
        int target = k - curr.val;
        if (set.contains(target)) return true;
        if (dfs2(curr.left, set, k)) return true;
        return dfs2(curr.right, set, k);
    }


    // [M] 15       3Sum
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                int target = - nums[i];
                int stIdx = i + 1, edIdx = nums.length - 1;

                while (stIdx < edIdx) {
                    if (nums[stIdx] + nums[edIdx] < target) stIdx++;
                    else if (nums[stIdx] + nums[edIdx] > target) edIdx--;
                    else {
                        res.add(new LinkedList<>(Arrays.asList(nums[i], nums[stIdx], nums[edIdx])));
                        while (stIdx < edIdx && nums[stIdx + 1] == nums[stIdx]) stIdx++;
                        while (edIdx > stIdx && nums[edIdx] == nums[edIdx - 1]) edIdx--;
                        stIdx++;
                        edIdx--;
                    }
                }
            }
        }
        return res;
    }


    // [M] 259      3Sum Smaller
    public int threeSumSmaller(int[] nums, int target) {
        if (nums.length < 3) return 0;
        quickSort(nums);

        int num = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int stIdx = i + 1, edIdx = nums.length - 1;
            int k = target - nums[i];
            while(stIdx < edIdx) {
                if (nums[stIdx] + nums[edIdx] >= k) edIdx--;
                else {
                    num += edIdx - stIdx;
                    stIdx++;
                }
            }
        }
        return num;
    }
    private void quickSort(int[] a) {
        Random rand = new Random();
        for (int i = 0; i < a.length; i++)
            exch(a, i, rand.nextInt(a.length));
        quickSort(a, 0, a.length - 1);
    }
    private void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, j, lo);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);
    }
    private void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] <= a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;
    }


    // [M] 16       3Sum Closest
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestNum = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length - 2; i++) {
            int k = target - nums[i];
            if (i == 0 || nums[i] != nums[i - 1]) {
                int stIdx = i + 1, edIdx = nums.length - 1;

                while (stIdx < edIdx) {
                    int twoSum = nums[stIdx] + nums[edIdx];
//                    System.out.println(stIdx + "  " + edIdx + "  ");
                    if (nums[i] + twoSum == target) return target;
                    if (Math.abs(target - closestNum) > Math.abs(target - (nums[i] + twoSum)))
                        closestNum = nums[i] + twoSum;

                    if (twoSum > k) edIdx--;
                    else if (twoSum < k) stIdx++;
                }
            }
        }
        return closestNum;
    }


    // [M] 909      Snakes and Ladders
    public int snakesAndLadders(int[][] board) {
        int N = board.length;
        boolean[] visited = new boolean[N * N + 1];
        Map<Integer, Integer> shortcuts = new HashMap<>();
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                if (board[r][c] != -1) shortcuts.put(getIdx(r, c, N), board[r][c]);
            }
        }
        System.out.println(shortcuts);

        Queue<Integer> queue = new LinkedList<>();
        visited[1] = true;
        queue.offer(1);
        int distance = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.println(queue);

            for (int i = 0; i < levelSize; i++){
                int curr = queue.poll();                        // remove from the queue
                if (curr == N * N) return distance;             // reach the destination

                for (int nextIdx = curr + 1; nextIdx <= Math.min(N * N, curr + 6); nextIdx++) {                  // next steps, from one to six
                    if (visited[nextIdx]) continue;

                    // only operate when this one hasn't been visited
                    visited[nextIdx] = true;
                    if (shortcuts.containsKey(nextIdx)) {       // this path has a shortcut, only add the end of the shortcut to the queue
                        int destSC = shortcuts.get(nextIdx);

                        // the shortcut destination is not a start of another shortcut
                        if (!visited[destSC]) queue.offer(destSC);
                        if (!shortcuts.containsKey(destSC)) visited[destSC] = true;
                    }
                    else queue.offer(nextIdx);              // if it is not a shortcut, add it to the queue
                }
            }
            distance++;
        }
        return -1;
    }
    private int getIdx(int r, int c, int N) {
        int based = (N - 1 - r) * N;
        boolean even = (N - 1 - r) % 2 == 0;
        if (even) based += (c + 1);
        else based += (N - c);
        return based;
    }


    public static void main(String[] args) {
        Leetcode1014 lc = new Leetcode1014();
        int[][] a = {{-1,1,1,1},{-1,7,1,1},{16,1,1,1},{-1,1,9,1}};
        System.out.println(lc.snakesAndLadders(a));


    }
}
