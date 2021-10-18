import java.net.IDN;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Leetcode1016 {


    // [M] 139      Word Break
    public boolean wordBreak(String s, List<String> wordDict) {
        /**
         * this approach uses dynamic and check the whole dictionary every i to see the previous n chars matched
         * time complexity: O(N * M), M is the size of wordDict
         *
         */

        int N = s.length();
        boolean[] dp = new boolean[N + 1];
        dp[0] = true;

        for (int i = 1; i < N + 1; i++) {
            for (String word : wordDict) {
                if (i >= word.length()) {
                    if (dp[i - word.length()] && s.substring(i - word.length(), i).equals(word) ) {
                        dp[i] = true;
                        break;
                    }
                }
            }
        }
        return dp[N];
    }

    public boolean wordBreak1(String s, List<String> wordDict) {
        /**
         * this solution uses dynamic and uses a set as the dictionary
         */
        Set<String> dict = new HashSet<>(wordDict);
        int N = s.length();
        boolean[] dp = new boolean[N + 1];
        dp[0] = true;
        for (int i = 1; i < N + 1; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[N];
    }


    // [H] 4        Median of Two Sorted Arrays
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int Idx1 = 0, Idx2 = 0;
        int N = nums1.length + nums2.length;
        int[] tgIdx = {-1, -1};
        int[] value = new int[2];

        if ((N % 2) != 0) {
            tgIdx[0] = N / 2;
            tgIdx[1] = N / 2;
        }
        else {
            tgIdx[0] = N / 2 - 1;
            tgIdx[1] = N / 2;
        }


        int cnt = 0;
        while (Idx1 < nums1.length || Idx2 < nums2.length) {
            int tmpC = -1;
            if (Idx2 >= nums2.length) {
                tmpC = nums1[Idx1];
                Idx1++;
            }
            else if (Idx1 >= nums1.length) {
                tmpC = nums2[Idx2];
                Idx2++;
            }
            else {
                if (nums1[Idx1] <= nums2[Idx2]) {
                    tmpC = nums1[Idx1];
                    Idx1++;
                }
                else {
                    tmpC = nums2[Idx2];
                    Idx2++;
                }
            }
            if (cnt == tgIdx[0]) value[0] = tmpC;
            if (cnt == tgIdx[1]) {
                value[1] = tmpC;
                break;
            }
            cnt++;
        }
        return ((double)value[0] + (double)value[1]) / 2;
    }



    public static void main(String[] arg) {
        Leetcode1016 lc = new Leetcode1016();
        int[] a = {2};
        int[] b = {};
        System.out.println(lc.findMedianSortedArrays(a, b));

    }
}
