import java.util.*;

public class Leetcode0910 {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // [E] 234      Palindrome Linked List
    public boolean isPalindrome(ListNode head) {
        Deque<Integer> deque = new LinkedList<>();
        while (head != null) {
            deque.offerLast(head.val);
            head = head.next;
        }
        if (deque.size() == 1) return true;
        while (!deque.isEmpty()) {
            if (deque.peekFirst() != deque.peekLast()) return false;
            deque.pollFirst();
            if (!deque.isEmpty()) deque.pollLast();
        }
        return true;
    }


    // [M] 491      Increasing Subsequences
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] booleans = new boolean[201];

        for (int i = 0; i < nums.length - 1; i++) {
            if (!booleans[nums[i] + 100]) {
                booleans[nums[i] + 100] = true;
                backtracking491(nums, i, path, res);
            }

        }
        return res;
    }
    private void backtracking491(int[] nums, int idx, List<Integer> path, List<List<Integer>> res) {
        path.add(nums[idx]);
        if (path.size() > 1) res.add(new ArrayList<>(path));

        boolean[] booleans = new boolean[201];

        for (int i = idx + 1; i < nums.length; i++) {
            if (nums[i] >= path.get(path.size() - 1) && !booleans[nums[i] + 100]) {
                booleans[nums[i] + 100] = true;
                backtracking491(nums, i, path, res);
            }
        }
        path.remove(path.size() - 1);
    }


    // [M] 46       Permutations
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] curVisited = new boolean[nums.length];
        for (int i = 0; i < nums.length; i++) {
            backtracking46(curVisited, nums, i, path, res);
        }
        return res;
    }
    private void backtracking46(boolean[] preVisited, int[] nums, int idx, List<Integer> path, List<List<Integer>> res) {
        if (path.size() >= nums.length) return;

        preVisited[idx] = true;
        path.add(nums[idx]);
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
        }
        else {
            for (int i = 0; i < nums.length; i++) {
                if (!preVisited[i]) {
                    preVisited[i] = true;
                    backtracking46(preVisited, nums, i, path, res);
                    preVisited[i] = false;
                }
            }
        }
        path.remove(path.size() - 1);
        preVisited[idx] = false;
    }


    // [M] 47       Permutations II
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        boolean[] visited = new boolean[nums.length];
        boolean[] dupDetector = new boolean[21];
        for (int i = 0; i < nums.length; i++) {
            if (!dupDetector[nums[i] + 10]) {
                dupDetector[nums[i] + 10] = true;
                backtracking47(visited, nums, i, path, res);
            }
        }
        return res;
    }
    private void backtracking47(boolean[] visited, int[] nums, int idx, List<Integer> path, List<List<Integer>> res) {
        if (path.size() >= nums.length) return;
        visited[idx] = true;
        path.add(nums[idx]);

        if (path.size() == nums.length) res.add(new ArrayList<>(path));
        else {
            boolean[] dupDetector = new boolean[21];

            for (int i = 0; i < nums.length; i++) {
                if (!visited[i] && !dupDetector[nums[i] + 10]) {
                    dupDetector[nums[i] + 10] = true;
                    visited[i] = true;
                    backtracking47(visited, nums, i, path, res);
                    visited[i] = false;
                }
            }
        }
        path.remove(path.size() - 1);
        visited[idx] = false;
    }


    // [M] 784      Letter Case Permutation
    public List<String> letterCasePermutation(String s) {
        char[] data = s.toCharArray();
        List<String> res = new ArrayList<>();
        backtracking784(data, 0, res);
        return res;
    }
    private void backtracking784(char[] data, int idx, List<String> res) {
        while (idx < data.length && (data[idx] <= '9' && data[idx] >= '0')) idx++;

        if (idx == data.length) {
            res.add(String.valueOf(data));
            return;
        }

        if (data[idx] >= 'a' && data[idx] <='z') {
            backtracking784(data, idx + 1, res);
            data[idx] = (char)(data[idx] - 32);
            backtracking784(data, idx + 1, res);
            data[idx] = (char)(data[idx] + 32);
        }
        else if (data[idx] >= 'A' && data[idx] <= 'Z') {
            backtracking784(data, idx + 1, res);
            data[idx] = (char)(data[idx] + 32);
            backtracking784(data, idx + 1, res);
            data[idx] = (char)(data[idx] - 32);
        }
    }



    public static void main(String[] args) {

    }
}
