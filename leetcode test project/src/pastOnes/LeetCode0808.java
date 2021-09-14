package pastOnes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCode0808 {


    // [M] LeetCode#567     Permutation in String
    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() > s2.length()) return false;
        int[] reference = new int[26];
        int[] inquiry = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            reference[s1.charAt(i) - 'a']++;
            inquiry[s2.charAt(i) - 'a']++;
        }

        int startIndex = 0;
        int endIndex = s1.length();
        if (Arrays.equals(reference, inquiry)) return true;

        while (endIndex < s2.length()) {
            inquiry[s2.charAt(endIndex) - 'a']++;
            inquiry[s2.charAt(startIndex) - 'a']--;

            if (Arrays.equals(reference, inquiry)) return true;

            startIndex++;
            endIndex++;

        }
        return false;
    }


    // [E] LeetCode#1002    Find Common Characters
    public List<String> commonChars(String[] words) {
        int[] reference = new int[26];

        if (words.length == 1) {
            ArrayList<String> result = new ArrayList<String>();
            for (int i = 0; i < words[0].length(); i++)
                result.add(String.valueOf(words[0].charAt(i)));
            return result;
        }

        for (int i = 0; i < words[0].length(); i++)
            reference[words[0].charAt(i) - 'a']++;

        for (int i = 1; i < words.length; i++) {
            int[] inquiry = new int[26];

            for (int c = 0; c < words[i].length(); c++)
                inquiry[words[i].charAt(c) - 'a']++;

            for (int a = 0; a < 26; a++)
                reference[a] = Math.min(reference[a], inquiry[a]);



        }

        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < 26; i++) {
            for (int c = 0; c < reference[i]; c++)
                result.add(String.valueOf((char) ('a' + i)));
        }
        return result;
    }

    public static void arrayVisualization(int[] array) {

    }

    public static void main(String[] args) {
        LeetCode0808 leetCode0808 = new LeetCode0808();
        String[] words = {"bella","label","roller"};
        for (String s : leetCode0808.commonChars(words))
            System.out.print(s + "  ");

    }
}
