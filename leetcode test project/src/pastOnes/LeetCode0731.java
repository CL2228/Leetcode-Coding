// Total: 2
package pastOnes;

public class LeetCode0731 {

    // [E] LeetCode#136     Single Number
    public int singleNumber1(int[] nums) {
        int result = 0;
        for (int i : nums)
            result ^= i;
        return result;

        /* a solution using QuickSort: run time: O(nlogn), memory: O(1)
        if (nums.length == 1) return nums[0];

        quickSort(nums, 0, nums.length - 1);

        for (int i = 0; i < nums.length - 1; i = i + 2) {
            if (nums[i] != nums[i + 1]) return nums[i];
        }
        return nums[nums.length - 1];

         */
    }
    private void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, lo, j);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);

    }
    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] <= a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j)
                break;
            exch(a, i, j);
        }
        return j;
    }
    private void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    // [M] LeetCode#137     Single Number II
    public int singleNumber(int[] nums) {
        quickSort(nums, 0, nums.length - 1);

        for (int i = 1; i < nums.length - 1; i += 3) {
            if (nums[i] != nums[i - 1]) {
                return nums[i - 1];
            }
        }
        return nums[nums.length - 1];
    }


    public static void main(String[] args) {
        LeetCode0731 leetCode0731 = new LeetCode0731();

        int[] a = {0,1,0,1,0,1,99};
        System.out.println(leetCode0731.singleNumber(a));

    }
}
