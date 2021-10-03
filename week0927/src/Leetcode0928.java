import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

public class Leetcode0928 {


    // [H] 42       Trapping Rain Water
    public int trap(int[] height) {
        int count = 0;
        int[] leftHeight = new int[height.length];
        int[] rightHeight = new int[height.length];
        leftHeight[0] = height[0];
        rightHeight[height.length - 1] = height[height.length - 1];
        for (int i = 1; i < height.length; i++) {
            leftHeight[i] = Math.max(height[i], leftHeight[i - 1]);
            rightHeight[height.length - 1 - i] = Math.max(height[height.length - 1 - i], rightHeight[height.length - i]);
        }
        for (int i = 1; i < height.length - 1; i++) {
            count += Math.max(0, Math.min(leftHeight[i - 1], rightHeight[i + 1]) - height[i]);
        }
        return count;
    }
    public int trap1(int[] height) {
        Deque<Integer> deque = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < height.length; i++) {
            int Hi = height[i];
            while (deque.size() >= 2) {
                if (height[deque.peekLast()] >= Hi) break;
                while (deque.size() >= 2 && height[deque.peekLast()] < Hi) {
                    int midIdx = deque.pollLast();
                    count += (Math.min(height[deque.peekLast()], Hi) - height[midIdx]) * (i - deque.peekLast() - 1);
                }
            }
            while (!deque.isEmpty() && height[deque.peekLast()] < Hi) deque.pollLast();
            deque.offerLast(i);
//            System.out.println(deque + "  " + count);
        }
        return count;
    }


    // [H] 84       Largest Rectangle in Histogram
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> deque = new LinkedList<>();
        int maxArea = heights[0];
        deque.offerLast(0);

        for (int i = 1; i < heights.length; i++) {
            while (!deque.isEmpty() && heights[deque.peekLast()] > heights[i]) {
                int targetIdx = deque.pollLast();
                int times = 1;
                if (deque.isEmpty()) times += targetIdx;
                else times += (targetIdx - deque.peekLast() - 1);
                times += (i - targetIdx - 1);
                maxArea = Math.max(maxArea, heights[targetIdx] * times);
            }
            maxArea = Math.max(maxArea, heights[i]);

            if (!deque.isEmpty() && heights[deque.peekLast()] == heights[i]) {
                maxArea = Math.max(maxArea, heights[i] * (i - deque.peekLast() + 1));
            }
            else deque.offerLast(i);
        }
        maxArea = Math.max(maxArea, heights[deque.peekFirst()] * (deque.peekLast() + 1));
        return maxArea;
    }


    // [M] 34       Find First and Last Position of Elements in Sorted Array
    public  int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) return new int[]{-1, -1};
        if (nums.length == 1) {
            if (target == nums[0]) return new int[]{0, 0};
            else return new int[]{-1, -1};
        }

        int lo = 0, hi = nums.length;
        int mid = (lo + hi) / 2;
        while (lo < hi) {
            if (nums[mid] == target) break;
            if (nums[mid] < target) lo = mid + 1;
            else hi = mid - 1;
            mid = (lo + hi) / 2;
        }

        if (nums[mid] != target) return new int[]{-1, -1};
        lo = hi = mid;
        while (lo >= 0 && nums[lo] == target) lo--;
        lo++;
        while (hi < nums.length && nums[hi] == target) hi++;
        hi--;
        return new int[]{lo, hi};
    }


    // [M] 3        Longest Substring Without Repeating Characters
    public int lengthOfLongestSubstring(String s) {
        int[] indices = new int[128];
        Arrays.fill(indices, -1);
        if (s.length() == 0) return 0;
        int maxLen = 1;

        int stIdx = 0, edIdx = 0;
        while (edIdx < s.length()) {
            char tail = s.charAt(edIdx);
            if (indices[tail] >= stIdx && indices[tail] <= edIdx) {
                stIdx = indices[tail] + 1;
            }
            indices[tail] = edIdx;
            maxLen = Math.max(maxLen, edIdx - stIdx + 1);

            edIdx++;
        }

        return maxLen;
    }


    // [M] 1035     Uncrossed Lines
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int M = nums1.length, N = nums2.length;
        int[][] dp = new int[M + 1][N + 1];

        for (int i = 1; i < M + 1; i++) {
            int n1 = nums1[i - 1];
            for (int j = 1; j < N + 1; j++) {
                int n2 = nums2[j - 1];

                if (n1 == n2) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[M][N];
    }


    // [H] 214      Shortest Palindrome
    public String shortestPalindrome(String s) {
        StringBuilder sb = new StringBuilder();

        int maxLen = 0;
        for (int j = s.length() - 1; j >= 0; j--) {
            if (j < maxLen - 1) break;
            if (s.charAt(j) != s.charAt(0)) continue;
            int tmpLen = 0;
            int fore = 0, reverse = j;
            while (reverse >= 0 && s.charAt(fore) == s.charAt(reverse)) {
                reverse--;
                fore++;
                tmpLen++;
            }
            if (reverse == -1) maxLen = Math.max(maxLen, tmpLen);
        }

//        System.out.println(maxLen);
        for (int i = s.length() - 1; i > maxLen - 1; i--)
            sb.append(s.charAt(i));
        sb.append(s);

        return sb.toString();
    }





    public static void main(String[] args) {
        Leetcode0928 lc = new Leetcode0928();
        int[] a = {0, 9};
        System.out.println(-4 % 4);
    }
}
