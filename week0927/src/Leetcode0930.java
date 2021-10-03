import java.util.*;

public class Leetcode0930 {


    // [M] 713      Subarray Product Less Than K
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int count = 0;

        double[] prefix = new double[nums.length + 1];
        for (int i = 1; i < nums.length + 1; i++)
            prefix[i] = prefix[i - 1] + Math.log(nums[i - 1]);
        return count;
    }


    // [E] 937      Reorder Data in Log Files
    public String[] reorderLogFiles(String[] logs) {
        Deque<String> dig = new LinkedList<>();
        List<String> let = new LinkedList<>();

        for (String str : logs) {
            String[] tmp = str.split(" ");
            if (tmp[1].charAt(0) <= '9' && tmp[1].charAt(0) >= '0') dig.offerLast(str);
            else let.add(str);
        }

        Collections.sort(let, new myCmp());
//        System.out.println(let);
        String[] res = new String[logs.length];
        int idx = 0;
        for (String l : let) res[idx++] = l;
        while (!dig.isEmpty()) res[idx++] = dig.pollFirst();
        return res;
    }
    class myCmp implements Comparator<String> {
        public int compare(String a, String b) {
            int spaceA = 0, spaceB = 0;
            while (a.charAt(spaceA) != ' ') spaceA++;
            while (b.charAt(spaceB) != ' ') spaceB++;

            int cmpContent = a.substring(spaceA + 1, a.length()).compareTo(b.substring(spaceB + 1, b.length()));
            if (cmpContent > 0) return 1;
            else if (cmpContent < 0) return -1;
            else {
                return a.substring(0, spaceA).compareTo(b.substring(0, spaceB));
            }
        }
    }


    // [M] 31       Next Permutation
    public void nextPermutation(int[] nums) {
        int smallIdx = nums.length - 2, bigIdx = nums.length - 1;
        while (smallIdx >= 0 && nums[smallIdx] >= nums[smallIdx + 1]) smallIdx--;
        if (smallIdx < 0) {
            Arrays.sort(nums);
            return;
        }
        while (nums[bigIdx] <= nums[smallIdx]) bigIdx--;
        int swap = nums[smallIdx];
        nums[smallIdx] = nums[bigIdx];
        nums[bigIdx] = swap;

        sort31(nums, smallIdx + 1, nums.length - 1);
    }
    private void sort31(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition31(a, lo, hi);
        exch31(a, lo, j);
        sort31(a, lo, j - 1);
        sort31(a, j + 1, hi);

    }
    private int partition31(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] <= a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch31(a, i, j);
        }
        return j;
    }
    private void exch31(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    // [M] 29       Divide Two Integers
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        return dividend / divisor;
    }


    // [M] 973      K Closest Points to Origin
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(points.length, new comPare973());
        pq.addAll(Arrays.asList(points));

        int[][] res = new int[k][];
//        sort973(points, 0, points.length - 1);
        for (int i = 0; i < k; i++) res[i] = pq.remove();
        return res;
    }
    class comPare973 implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            int disA = (int) (Math.pow(a[0], 2) + Math.pow(a[1], 2));
            int disB = (int) (Math.pow(b[0], 2) + Math.pow(b[1], 2));
            return Integer.compare(disA, disB);
        }
    }
    private void sort973(int[][] points, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition973(points, lo, hi);
        exch973(points, j, lo);
        sort973(points, lo, j - 1);
        sort973(points, j + 1, hi);
    }
    private void exch973(int[][] points, int i, int j) {
        int[] swap = points[i];
        points[i] = points[j];
        points[j] = swap;
    }
    private int partition973(int[][] points, int lo, int hi) {
        int i = lo, j = hi + 1;
        int disLo = (int) (Math.pow(points[lo][0], 2) + Math.pow(points[lo][1], 2));
        while (true) {
            while ((int)(Math.pow(points[++i][0], 2) + Math.pow(points[i][1], 2)) <= disLo)
                if (i >= hi) break;
            while ((int)(Math.pow(points[--j][0], 2) + Math.pow(points[j][1], 2)) >= disLo)
                if (j <= lo) break;
            if (i >= j) break;
            exch973(points, i, j);
        }
        return j;
    }


    // [E] 680      Valid Palindrome II
    public boolean validPalindrome(String s) {
        char[] data = s.toCharArray();
        int leftIdx = 0, rightIdx = data.length - 1;

        while (leftIdx <= rightIdx) {
            if (data[leftIdx] != data[rightIdx])
                return (isPalindrome(data, leftIdx + 1, rightIdx) || isPalindrome(data, leftIdx, rightIdx - 1));
            leftIdx++;
            rightIdx--;
        }
        return true;
    }
    private boolean isPalindrome(char[] data, int lo, int hi) {
        while (lo <= hi) {
            if (data[lo] != data[hi]) return false;
            lo++;
            hi--;
        }
        return true;
    }


    // [E] 125      Valid Palindrome
    public boolean isPalindrome(String s) {
        int leftIdx = 0, rightIdx = s.length() - 1;
        while (leftIdx <= rightIdx) {
            while (leftIdx < s.length() && !isChar(s.charAt(leftIdx))) leftIdx++;
            if (leftIdx == s.length()) return true;
            while (rightIdx >= 0 && !isChar(s.charAt(rightIdx))) rightIdx--;
            if (rightIdx < 0)return true;

            if (leftIdx > rightIdx) break;
            int left = s.charAt(leftIdx), right = s.charAt(rightIdx);
            if (left <= 90) left += 32;
            if (right <= 90) right += 32;

            if (left != right) return false;
            leftIdx++;
            rightIdx--;
        }
        return true;
    }
    private boolean isChar(char c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || ('0' <= c && c <= '9');
    }
    private boolean isHiLetter(char c) {
        return ('A' <= c && c <= 'Z');
    }


    // [M] 227      Basic Calculator II
    public int calculate(String s) {
        Deque<Integer> numbers = new LinkedList<>();
        Deque<Character> operations = new LinkedList<>();

        int stIdx = 0;
        while (stIdx < s.length()) {
            while (stIdx < s.length() && s.charAt(stIdx) == ' ') stIdx++;
            if (stIdx == s.length()) break;

            char currC = s.charAt(stIdx);
            if (currC == '+' || currC == '-' || currC == '*' || currC == '/') {
                operations.offerLast(currC);
                stIdx++;
            }
            else {
                // this is a number, needs to detect the operations
                int edIdx = stIdx;
                while (edIdx < s.length() && s.charAt(edIdx) >= '0' && s.charAt(edIdx) <= '9') edIdx++;
                int currNum = Integer.parseInt(s.substring(stIdx, edIdx));
                if (operations.isEmpty() || operations.peekLast() == '+' || operations.peekLast() == '-')
                    numbers.offerLast(currNum);
                else if (operations.peekLast() == '*') {
                    operations.pollLast();
                    int numLast = numbers.pollLast();
                    numbers.offerLast(currNum * numLast);
                }
                else if (operations.peekLast() == '/') {
                    operations.pollLast();
                    int numberLast = numbers.pollLast();
                    numbers.offerLast(numberLast / currNum);
                }
                stIdx = edIdx;
            }
        }

        int res = numbers.pollFirst();
        while (!numbers.isEmpty()) {
            char operation = operations.pollFirst();
            if (operation == '+') res += numbers.pollFirst();
            else res -= numbers.pollFirst();
        }
        return res;
    }
    public int calculate1(String s) {
        Deque<String> calculateSteam = new LinkedList<>();
        int stIdx = 0;

        while (stIdx < s.length()) {
            while (stIdx < s.length() && s.charAt(stIdx) == ' ') stIdx++;
            if (stIdx == s.length()) break;

            char curr = s.charAt(stIdx);
            if (curr == '+' || curr == '-' || curr == '/' || curr == '*') {
                calculateSteam.offerLast(s.substring(stIdx, stIdx + 1));
                stIdx++;
            }
            else {
                // we met a number, we need to find the whole number and calculate if needed
                int edIdx = stIdx;
                while (edIdx < s.length() && s.charAt(edIdx) <= '9' && s.charAt(edIdx) >= '0') edIdx++;

                if (calculateSteam.isEmpty() || calculateSteam.peekLast().equals("+") || calculateSteam.peekLast().equals("-"))
                    calculateSteam.offerLast(s.substring(stIdx, edIdx));
                else if (calculateSteam.peekLast().equals("*")) {
                    calculateSteam.pollLast();
                    int a = Integer.parseInt(calculateSteam.pollLast());
                    int subRes = a * Integer.parseInt(s.substring(stIdx, edIdx));
                    calculateSteam.offerLast(String.valueOf(subRes));
                }
                else if (calculateSteam.peekLast().equals("/")) {
                    calculateSteam.pollLast();
                    int a = Integer.parseInt(calculateSteam.pollLast());
                    int subRes = a / Integer.parseInt(s.substring(stIdx, edIdx));
                    calculateSteam.offerLast(String.valueOf(subRes));
                }
                stIdx = edIdx;
            }
        }

        int res = Integer.parseInt(calculateSteam.pollFirst());

        while (!calculateSteam.isEmpty()) {
            String opera = calculateSteam.pollFirst();
            if (opera.equals("+")) res += Integer.parseInt(calculateSteam.pollFirst());
            else res -= Integer.parseInt(calculateSteam.pollFirst());
        }
        return res;
    }


    // [M]  5       Longest Palindromic Substring
    public String longestPalindrome(String s) {
        int[] indices = new int[]{0, 0};
        int maxLen = 1;

        for (int i = 0; i < s.length(); i++) {
            int[] oneCenter = calPalindromic(s, i, i);
            int[] twoCenter = calPalindromic(s, i, i + 1);
            if (oneCenter[1] - oneCenter[0] + 1 > maxLen) {
                maxLen = oneCenter[1] - oneCenter[0] + 1;
                indices = oneCenter;
            }
            if (twoCenter[1] - twoCenter[0] + 1 > maxLen) {
                maxLen = twoCenter[1] - twoCenter[0] + 1;
                indices = twoCenter;
            }
        }
        return s.substring(indices[0], indices[1] + 1);
    }
    private int[] calPalindromic(String s, int left, int right) {
        // find the start and end indices of the palindrome
        if (left >= s.length() || right >= s.length()) return new int[]{0, -1};
        while (s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
            if (left < 0 || right >= s.length()) break;
        }
        return new int[]{left + 1, right - 1};
    }







    public static void main(String[] args) {
        Leetcode0930 lc = new Leetcode0930();
        String a = "  0-4";
        System.out.println(lc.calculate(a));

    }
}
