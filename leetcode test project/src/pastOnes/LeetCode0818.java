package pastOnes;

import java.util.Arrays;
import java.util.Stack;

public class LeetCode0818 {

    // [E] 剑指Offer58        左旋转字符串
    public String reverseLeftWords(String s, int n) {
        char[] data = s.toCharArray();

        reverseChar(data, 0, n - 1);
        reverseChar(data, n, s.length() - 1);
        reverseChar(data, 0, s.length() - 1);
        return String.valueOf(data);
    }
    private void reverseChar(char[] data, int lo, int hi) {
        int i = lo, j = hi;
        while (i < j) {
            char swap = data[i];
            data[i] = data[j];
            data[j] = swap;
            i++;
            j--;
        }
    }


    // [M] LeetCode#151     Reverse Words in a String
    public String reverseWords(String s) {
        char[] data = s.toCharArray();
        reverseSubChar(data, 0, s.length() - 1);

        int newIndex = 0;
        for (int i = 0; i < data.length; i++) {
            if (newIndex == 0 && data[i] != ' ') {
                data[newIndex] = data[i];
                newIndex++;

            }
            else if (newIndex > 0 && (data[i] != ' ' || data[i - 1] != ' ')) {
                data[newIndex] = data[i];
                newIndex++;
            }
        }
        if (data[newIndex - 1] == ' ') newIndex--;
        for (int i = newIndex; i < s.length(); i++) data[i] = ' ';

        int wordSt = 0, wordEd = 0;
        while (wordSt < data.length && wordEd < data.length) {
            while (data[wordSt] == ' ') {
                wordSt++;
                if (wordSt >= s.length()) {
                    char[] result = Arrays.copyOf(data, newIndex);
                    return String.valueOf(result);
                }
            }
            wordEd = wordSt;
            while (wordEd < data.length && data[wordEd] != ' ') wordEd++;

            reverseSubChar(data, wordSt, wordEd - 1);
            wordSt = wordEd + 1;
        }

        char[] result = Arrays.copyOf(data, newIndex);
        return String.valueOf(result);
    }
    public void reverseSubChar(char[] data, int lo, int hi) {
        while (lo < hi) {
            char swap = data[lo];
            data[lo] = data[hi];
            data[hi] = swap;
            lo++;
            hi--;
        }
    }


    // [E] LeetCode#28      Implement strStr()
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        int stIdx = 0;
        while (stIdx <= haystack.length() - needle.length()) {
            if (needle.charAt(0) != haystack.charAt(stIdx)) stIdx++;
            else {
                for (int i = 0; i < needle.length(); i++) {
                    if (needle.charAt(i) != haystack.charAt(stIdx + i)) {
                        stIdx++;
                        break;
                    }
                    if (i == needle.length() - 1 && needle.charAt(i) == haystack.charAt(stIdx + i)) return stIdx;
                }
            }
        }
        return -1;
    }



    public static void main(String[] args) {
        LeetCode0818 leetCode0818 = new LeetCode0818();
        String haystack = "mississippi";
        String needle = "issip";

        System.out.println(leetCode0818.strStr(haystack, needle));
    }


}
