public class Leetcode0920 {



    // [M] 198      House Robber
    public int rob1(int[] nums) {
        if (nums.length == 1) return nums[0];
        else if (nums.length == 2) return Math.max(nums[0], nums[1]);

        int d_0 = 0;
        int d_1 = nums[0];
        int d_2 = nums[1];
        int max = Math.max(d_1, d_2);

        for (int i = 2; i < nums.length; i++) {
            int newTmp = Math.max(d_0 + nums[i], d_1 + nums[i]);
            if (newTmp > max) max = newTmp;
            d_0 = d_1;
            d_1 = d_2;
            d_2 = newTmp;
        }
        return max;
    }


    // [M] 213      House Robber II
    public int rob(int[] nums) {
        if (nums.length == 1) return nums[0];
        else if (nums.length == 2) return Math.max(nums[0], nums[1]);
        int t_3 = 0;
        int t_2 = nums[0];
        int t_1 = nums[1];
        int max = Math.max(t_2, t_1);
        for (int j = 2; j < nums.length - 1; j++) {
            int curr = Math.max(t_3, t_2) + nums[j];
            if (curr > max) max = curr;
            t_3 = t_2;
            t_2 = t_1;
            t_1 = curr;
        }
        t_3 = 0;
        t_2 = nums[1];
        t_1 = nums[2];
        max = Math.max(max, Math.max(t_2, t_1));
        for (int j = 3; j < nums.length; j++) {
            int curr = Math.max(t_2, t_3) + nums[j];
            if (curr > max) max = curr;
            t_3 = t_2;
            t_2 = t_1;
            t_1 = curr;
        }
        return max;
    }


    public static void main(String[] args) {
        Leetcode0920 lc = new Leetcode0920();
        int[] a = {1,2,3,1};
        System.out.println(lc.rob(a));
    }
}
