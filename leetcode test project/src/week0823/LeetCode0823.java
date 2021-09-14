package week0823;



public class LeetCode0823 {

    // [E] 27       Remove Element
    public int removeElement(int[] nums, int val) {
        int count = 0;
        int newIndex = 0;
        for (int oldIndex = 0; oldIndex < nums.length; oldIndex++) {
            if (val != nums[oldIndex]) {
                nums[newIndex++] = nums[oldIndex];
                count++;
            }
        }
        return count;
    }


    // [M] 459      Repeated Substring Pattern

    public static void main(String[] args) {
        LeetCode0823 leetCode0823 = new LeetCode0823();
        int[] a = {2,3,3,2};
        System.out.println(leetCode0823.removeElement(a, 3));
    }
}
