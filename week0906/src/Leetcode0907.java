import java.util.*;

public class Leetcode0907 {


    // [M] 77       Combinations
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        // from level to N - (k - 1) + (level - 1) , level starting from 1 to k
        for (int i = 1; i <= n - (k - 1); i++)
            getCombine77(i, 1, k, n, path, res);
        return res;
    }
    private void getCombine77(int currN, int level, int k, int N,List<Integer> path, List<List<Integer>> res) {
        path.add(currN);

        if (level == k) res.add(new ArrayList<>(path));
        else if (level < k) {
            level++;
            for (int i = currN + 1; i <= N - (k - 1) + (level - 1); i++)
                getCombine77(i, level, k, N, path, res);
        }
        path.remove(path.size() - 1);
    }


    // [M] 39       Combination Sum
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Arrays.sort(candidates);

        for (int i = 0; i < candidates.length; i++)
            if (getSum39(i, 0, target, candidates, path, res)) break;
        return res;
    }
    private boolean getSum39(int currIdx, int preSum, int target, int[] candidates, List<Integer> path, List<List<Integer>> res) {
        if (preSum > target) return true;

        path.add(candidates[currIdx]);
        int currSum = preSum + candidates[currIdx];
        if (currSum == target) {
            res.add(new ArrayList<>(path));
        }
        else if (currSum < target)
            for (int i = currIdx; i < candidates.length; i++)
                if (getSum39(i, currSum, target, candidates, path, res)) break;

        path.remove(path.size() - 1);
        return false;
    }






    public static void main(String[] args) {
        Leetcode0907 lc = new Leetcode0907();
        int[] a = {2,3,6,7};
        lc.combinationSum(a, 7);
    }
}
