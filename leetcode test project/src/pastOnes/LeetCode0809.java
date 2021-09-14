// TODO: 691
package pastOnes;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class LeetCode0809 {

    // [E] LeetCode#349     Intersection of Two Arrays
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) return null;

        HashSet<Integer> set1 = new HashSet<Integer>();
        HashSet<Integer> set2 = new HashSet<Integer>();

        if (nums1.length < nums2.length) {
            for (int i = 0; i < nums1.length; i++) {
                set1.add(nums1[i]);
                set2.add(nums2[i]);
            }
            for (int i = nums1.length; i < nums2.length; i++)
                set2.add(nums2[i]);
        } else {
            for (int i = 0; i < nums2.length; i++) {
                set1.add(nums1[i]);
                set2.add(nums2[i]);
            }
            for (int i = nums2.length; i < nums1.length; i++)
                set1.add(nums1[i]);
        }


        HashSet<Integer> toDelete = new HashSet<Integer>();
        for (int i : set1) {
            if (!set2.contains(i))
                toDelete.add(i);
        }

        for (int i : toDelete)
            set1.remove(i);

        int[] result = new int[set1.size()];
        int idx = 0;
        for (int i : set1) {
            result[idx] = i;
            idx++;
        }
        return result;
    }


    // [E] LeetCode#350     Intersection of Two Arrays
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) return null;

        HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> map2 = new HashMap<Integer, Integer>();

        for (int i : nums1) {
            if (map1.containsKey(i))
                map1.replace(i, map1.get(i) + 1);
            else
                map1.put(i, 1);
        }
        for (int i : nums2) {
            if (map2.containsKey(i))
                map2.replace(i, map2.get(i) + 1);
            else
                map2.put(i, 1);
        }


        ArrayList<Integer> toDelete = new ArrayList<>();
        for (int i : map1.keySet()) {
            if (!map2.containsKey(i))
                toDelete.add(i);
            else
                map1.replace(i, Math.min(map1.get(i), map2.get(i)));
        }
        for (int i : toDelete)
            map1.remove(i);

        ArrayList<Integer> resultList = new ArrayList<>();
        for (int i : map1.keySet()) {
            for (int t = 0; t < map1.get(i); t++)
                resultList.add(i);
        }

        int[] result = new int[resultList.size()];
        int idx = 0;
        for (int i : resultList) {
            result[idx] = i;
            idx++;
        }

        return result;
    }


    public static void main(String[] args) {
        HashSet<Integer> test = new HashSet<Integer>();
        LeetCode0809 leetCode0809 = new LeetCode0809();
        int[] a = {1,2,2,1};
        int[] b = {2,2};

        for (int i : leetCode0809.intersect(a, b))
            System.out.print(i + "  ");
    }
}
