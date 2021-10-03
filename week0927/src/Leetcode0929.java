import java.util.*;

public class Leetcode0929 {


    // [M] 1041     Robot Bounded In Circle
    public boolean isRobotBounded(String instructions) {
        /*
        -4-up
        -3-right
        -2-down
        -1-left
        0-up
        1-right
        2-down
        3-left
        4-up
         */

        int direction = 0;
        int up = 0, left = 0;

        for (int i = 0; i < instructions.length(); i++) {
            char command = instructions.charAt(i);
            if (command == 'L') {
                direction--;
                if (direction < 0) direction += 4;
            } else if (command == 'R') {
                direction++;
                if (direction >= 4) direction -= 4;
            }
            else {
                if (direction == 0) up++;
                else if (direction == 1) left--;
                else if (direction == 2) up--;
                else if (direction == 3) left++;
            }
        }
        if (left == 0 && up == 0) return true;
        return direction != 0;
    }


    // [M] 621      Task Schedule
    public int leastInterval(char[] tasks, int n) {
        // greedy algorithms
        int[] count = new int[26];
        for (char c : tasks) count[c - 'A']++;
        Arrays.sort(count);
        int idleTime = (count[25] - 1) * n;

        for (int i = 24; i >= 0 && idleTime > 0; i--) {
            idleTime -= Math.min(count[25] - 1, count[i]);
        }
        idleTime = Math.max(0, idleTime);
        return tasks.length + idleTime;
    }


    // [M] 1249     Minimum Remove to Make Valid Parentheses
    public String minRemoveToMakeValid(String s) {
        char[] data = s.toCharArray();
        StringBuilder sb = new StringBuilder();

        Stack<Node1249> stack = new Stack<>();
        for (int i = 0; i < data.length; i++) {
            char c = data[i];
            if (c == '(') stack.push(new Node1249('(', i));
            else if (c == ')') {
                if (!stack.isEmpty() && stack.peek().val == '(') stack.pop();
                else stack.push(new Node1249(')', i));
            }
        }
        Set<Integer> filter = new HashSet<>();
        while (!stack.isEmpty()) filter.add(stack.pop().idx);

        for (int i = 0; i < data.length; i++) {
            if (!filter.contains(i)) sb.append(data[i]);
        }
        return sb.toString();
    }
    class Node1249 {
        char val;
        int idx;
        public Node1249(char val, int idx) {
            this.val = val;
            this.idx = idx;
        }
    }


    // [M] 560      Subarray Sum Equals K
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        for (int i = 0; i < nums.length; i++) {
            if (i > 0) nums[i] += nums[i - 1];
            int subTarget = nums[i] - k;
            if (map.containsKey(subTarget)) count += map.get(subTarget);

            if (!map.containsKey(nums[i])) map.put(nums[i], 1);
            else map.replace(nums[i], map.get(nums[i]) + 1);
        }
        return count;
    }





    public static void main(String[] args) {
        Leetcode0929 lc = new Leetcode0929();
        int[] a = {1,2,3};
        System.out.println(lc.subarraySum(a, 3));
    }
}
