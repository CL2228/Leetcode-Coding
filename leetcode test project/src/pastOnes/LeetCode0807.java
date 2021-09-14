package pastOnes;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class LeetCode0807 {

    // [E] LeetCode#383     Ransom Note
    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote == null || magazine == null) return false;
        if (ransomNote.length() > magazine.length()) return false;

        int[] ransomCount = new int[26];
        int[] magazineCount = new int[26];
        for (int i = 0; i < ransomNote.length(); i++)
            ransomCount[ransomNote.charAt(i) - 'a']++;
        for (int i = 0; i < magazine.length(); i++)
            magazineCount[magazine.charAt(i) - 'a']++;

        for (int i = 0; i < 26; i++) {
            if (ransomCount[i] == 0) continue;
            if (ransomCount[i] > magazineCount[i]) return false;
        }
        return true;
    }

    // [M] LeetCode#438     Find All Anagrams in a String
    public List<Integer> findAnagrams(String s, String p) {
        if (s.length() < p.length()) return new ArrayList<Integer>();
        int[] reference = new int[26];
        int[] inquiry = new int[26];
        for (int i = 0; i < p.length(); i++) {
            reference[p.charAt(i) - 'a']++;
            inquiry[s.charAt(i) - 'a']++;
        }

        ArrayList<Integer> result = new ArrayList<Integer>();

        int startIndex = 0;
        int endIndex = p.length();
        if (Arrays.equals(reference, inquiry))
            result.add(startIndex);

        while (endIndex < s.length()) {
            inquiry[s.charAt(startIndex) - 'a']--;
            inquiry[s.charAt(endIndex) - 'a']++;

            if (Arrays.equals(reference, inquiry))
                result.add(++startIndex);
            else
                startIndex++;
            endIndex++;
        }
        return result;

        /*
        Map<String, List<Integer>> map = new HashMap<>();
        char[] reference = p.toCharArray();
        Arrays.sort(reference);
        map.put(String.valueOf(reference), new ArrayList<Integer>());
        int[] pCount = new int[26];
        for (int i = 0; i < p.length(); i++)
            pCount[p.charAt(i) - 'a']++;

        int startIndex = 0, endIndex = p.length() - 1;

        while (endIndex < s.length()) {
            if (pCount[s.charAt(endIndex) - 'a'] == 0) {
                startIndex = endIndex + 1;
                endIndex = startIndex + p.length() - 1;
                continue;
            }

            String sub = s.substring(startIndex, endIndex + 1);
            char[] inquiry = sub.toCharArray();
            Arrays.sort(inquiry);
            if (map.containsKey(String.valueOf(inquiry)))
                map.get(String.valueOf(inquiry)).add(startIndex);
            startIndex++;
            endIndex++;
        }
        return map.get(String.valueOf(reference));
         */
    }




    public static void main(String[] args) {
        LeetCode0807 leetCode0807 = new LeetCode0807();
        String a = "abab";
        String b = "ab";
        for (int i : leetCode0807.findAnagrams(a, b))
            System.out.print(i + "  ");
    }
}
