import java.util.*;

public class Leetcode0913 {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next;}
    }

    // [H] 135      Candy
    public int candy(int[] ratings) {
        if (ratings.length == 1) return 1;
        int[] candies = new int[ratings.length];
        for (int i = 0; i < ratings.length; i++) candies[i] = 1;
        for (int i = 1; i < candies.length; i++)
            if (ratings[i] > ratings[i - 1]) candies[i] = candies[i - 1] + 1;
        for (int i = candies.length - 2; i >=0; i--)
            if (ratings[i] > ratings[i + 1]) candies[i] = Math.max(candies[i], candies[i + 1] + 1);
        int sum = 0;
        for (int i : candies) sum += i;
        return sum;
    }


    // [E] 860      Lemonade Charge
    public boolean lemonadeChange(int[] bills) {
        int[] counter = new int[3];

        for (int money : bills) {
            if (money == 5) counter[0]++;
            else if (money == 10) {
                if (counter[0] == 0) return false;
                else {
                    counter[0]--;
                    counter[1]++;
                }
            }
            else if (money == 20) {
                if (counter[0] == 0) return false;
                if (counter[1] > 0 ) {      // have 10 usd bills
                    counter[1]--;
                    counter[0]--;
                    counter[2]++;
                }
                else {                      // do not have 10 usd bills
                    if (counter[0] < 3) return false;
                    else {
                        counter[0] -= 3;
                        counter[2]++;
                    }
                }
            }
        }
        return true;
    }


    // [M] 611      Valid Triangle Number
    public int triangleNumber(int[] nums) {
        if (nums.length < 3) return 0;
        int count = 0;
        quickSort611(nums, 0, nums.length - 1);
//        for (int i : nums) System.out.print("  " + i);
//        System.out.println();
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                if (nums[j] < nums[i] / 2) break;
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[j] + nums[k] <= nums[i]) break;
                    if (nums[j] + nums[k] > nums[i]) count++;
                }
            }
        }
        return count;
    }
    private void quickSort611(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition611(a, lo, hi);
        exch611(a, lo, j);
        quickSort611(a, lo, j - 1);
        quickSort611(a, j + 1, hi);
    }
    private void exch611(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition611(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] >= a[lo])
                if (i >= hi) break;
            while (a[--j] <= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch611(a, i, j);
        }
        return j;
    }


    // [M] 406      Queue Reconstruction by Height
    public int[][] reconstructQueue(int[][] people) {
        if (people.length == 1) return people;
        int[][] result = new int[people.length][people[0].length];
        quickSort406(people, 0, people.length - 1);
        List<int[]> res = new LinkedList<>();
        for (int[] p : people)
            res.add(p[1], p);
        int idx = 0;
        for (int[] p : res) result[idx++] = p;
        return result;
    }
    private void quickSort406(int[][] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition406(a, lo, hi);
        exch406(a, lo, j);
        quickSort406(a, lo, j - 1);
        quickSort406(a, j + 1, hi);
    }
    private void exch406(int[][] a, int i, int j) {
        int[] swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition406(int[][] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i][0] > a[lo][0] || (a[i][0] == a[lo][0] && a[i][1] <= a[lo][1]))
                if (i >= hi) break;
            while (a[--j][0] < a[lo][0] || (a[j][0] == a[lo][0] && a[j][1] >= a[lo][1]))
                if (j <= lo) break;
            if (i >= j) break;
            exch406(a, i, j);

        }
        return j;
    }


    // [M] 452      Minimum Number of Arrows to Burst Balloons
    public int findMinArrowShots(int[][] points) {
        if (points.length == 1) return 1;
        int count = 1;
        Arrays.sort(points, (o1, o2) -> Integer.compare(o1[0], o2[0]));
//        quickSort452(points, 0, points.length - 1);
        int edIdx = points[0][1];
        for (int[] point : points) {
            if (point[0] <= edIdx) edIdx = Math.min(point[1], edIdx);
            else
            {
                count++;
                edIdx = point[1];
            }
        }
        return count;
    }
    private void quickSort452(int[][] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition452(a, lo, hi);
        exch452(a, lo, j);
        quickSort452(a, lo, j - 1);
        quickSort452(a, j + 1, hi);
    }
    private void exch452(int[][] a, int i, int j) {
        int[] swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition452(int[][] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i][0] < a[lo][0])
                if (i >= hi) break;
            while (a[--j][0] >= a[lo][0])
                if (j <= lo) break;
            if (i >= j) break;
            exch452(a, i, j);
        }
        return j;
    }
    static class Node452 {
        private int left;
        private int right;
        public Node452(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }


    // [M] 435      Non-overlapping Intervals
    public int eraseOverlapIntervals(int[][] intervals) {

        if (intervals.length == 1) return 0;
        int count = 0;
        quickSort435(intervals, 0, intervals.length - 1);


        int edIdx = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < edIdx) count++;
            else edIdx = intervals[i][1];
        }
        return count;
    }
    private void quickSort435(int[][] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition435(a, lo, hi);
        exch435(a, lo, j);
        quickSort435(a, lo, j - 1);
        quickSort435(a, j + 1, hi);
    }
    private void exch435(int[][] a, int lo, int hi) {
        int[] swap = a[lo];
        a[lo] = a[hi];
        a[hi] = swap;
    }
    private int partition435(int[][] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i][1] < a[lo][1] || (a[i][1] == a[lo][1] && a[i][0] <= a[lo][0]))
                if (i >= hi) break;
            while (a[--j][1] > a[lo][1] || (a[j][1] == a[lo][1] && a[j][0] >= a[lo][0]))
                if (j <= lo) break;
            if (i >= j) break;
            exch435(a, i, j);
        }
        return j;
    }


    // [M] 763      Partition Labels
    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();
        int[] group = new int[26];
        int[] count = new int[26];
        int groupNum = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (group[c - 'a'] > 0) {
                count[c - 'a']++;
                groupNum = group[c - 'a'];
                for (int j = 0; j < 26; j++) if (group[j] > groupNum) group[j] = groupNum;
            }
            else {      // new group
                count[c - 'a']++;
                group[c - 'a'] = ++groupNum;
            }
        }
        int[] finalCount = new int[groupNum];
        for (int i = 0; i < 26; i++) {
            if (group[i] == 0) continue;
            finalCount[group[i] - 1] += count[i];
        }
        for (int i : finalCount) res.add(i);
        return res;
    }


    // [M] 56       Merge Intervals
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 1) return intervals;
        quickSort56(intervals, 0, intervals.length - 1);
        int stIdx = intervals[0][0];
        int edIdx = intervals[0][1];

        List<int[]> list = new LinkedList<>();

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > edIdx) {
                int[] subRe = {stIdx, edIdx};
                list.add(subRe);
                stIdx = intervals[i][0];
                edIdx = intervals[i][1];
            }
            else
                edIdx = Math.max(edIdx, intervals[i][1]);
        }
        int[] subRe = {stIdx, edIdx};
        list.add(subRe);
        int[][] res = new int[list.size()][2];
        int idx = 0;
        for (int[] re : list) res[idx++] = re;
        return res;
    }
    private void quickSort56(int[][] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition56(a, lo, hi);
        exch56(a, lo, j);
        quickSort56(a, lo, j - 1);
        quickSort56(a, j + 1, hi);
    }
    private void exch56(int[][] a, int i, int j) {
        int[] swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition56(int[][] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i][0] <= a[lo][0])
                if (i >= hi) break;
            while (a[--j][0] >= a[lo][0])
                if (j <= lo) break;
            if (i >= j) break;
            exch56(a, i, j);
        }
        return j;
    }


    // [M] 86       Partition List
    public ListNode partition(ListNode head, int x) {
        if (head == null) return null;
        ListNode leftHead = new ListNode(1);
        ListNode rightHead = new ListNode(2);
        ListNode leftCurr = leftHead;
        ListNode rightCurr = rightHead;
        ListNode curr = head;
        while (curr != null) {
            ListNode currNext = curr.next;
            if (curr.val < x) {
                leftCurr.next = curr;
                leftCurr = leftCurr.next;
                leftCurr.next = null;
            }
            else {
                rightCurr.next = curr;
                rightCurr = rightCurr.next;
                rightCurr.next = null;
            }
            curr = currNext;
        }
        leftCurr.next = rightHead.next;
        return leftHead.next;
    }


    // [E] 1089     Duplicate Zeros
    public void duplicateZeros(int[] arr) {
        List<Integer> list = new LinkedList<>();
        for (int i : arr) {
            list.add(i);
            if (list.size() == arr.length) break;
            if (i == 0) list.add(0);
            if (list.size() == arr.length) break;
        }
        int idx = 0;
        for (int i : list) arr[idx++] = i;
    }


    // [H] 37       Sudoku Solver
    public void solveSudoku(char[][] board) {
        int[] firstCoordinate = getNext(board, 0, 0);
        boolean res = backtracking37(board, firstCoordinate[0], firstCoordinate[1]);
    }
    private boolean backtracking37(char[][] board, int x, int y) {
        for (int i = 1; i < 10; i++) {
            if (workable(board, x, y, i)) {
                board[x][y] = (char) (i + 48);
                if (x == board.length - 1 && y == board.length - 1) return true;
                int[] nextCoordinate = getNext(board, x, y);
                if (nextCoordinate[0] == -1) return true;
                if (backtracking37(board, nextCoordinate[0], nextCoordinate[1])) return true;
                board[x][y] = '.';
            }
        }
        return false;
    }
    private int[] getNext(char[][] board, int x, int y) {
        int[] res = {-1,-1};
        int r = x, c = y;
        while (c < board.length) {
            while (r < board.length) {
                if (board[r][c] == '.') {
                    res[0] = r;
                    res[1] = c;
                    return res;
                }
                r++;
            }
            r = 0;
            c++;
        }
        return res;
    }
    private boolean workable(char[][] board, int x, int y, int val) {
        int N = board.length;
        char cr = (char)(val + 48);
        for (int i = 0; i < N; i++) if (board[x][i] == cr) return false;
        for (int i = 0; i < N; i++) if (board[i][y] == cr) return false;
        int up = x - x % 3;
        int left = y - y % 3;
        int bottom = 2 + 3 * (x / 3);
        int right = 2 + 3 * (y / 3);
        for (int r = up; r <= bottom; r++) {
            for (int c = left; c <= right; c++) if (board[r][c] == cr) return false;
        }
        return true;
    }


    // [M] 36       Valid Sudoku
    public boolean isValidSudoku(char[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                if (board[r][c] != '.' && !workable36(board, r, c, board[r][c])) return false;
            }
        }
        return true;
    }
    private boolean workable36(char[][] board, int x, int y, char cr) {
        int N = board.length;
        for (int i = 0; i < N; i++) {
            if (i == y) continue;
            if (board[x][i] == cr) return false;
        }
        for (int i = 0; i < N; i++) {
            if (x == i) continue;
            if (board[i][y] == cr) return false;
        }
        int up = x - x % 3;
        int left = y - y % 3;
        int bottom = 2 + 3 * (x / 3);
        int right = 2 + 3 * (y / 3);
        for (int r = up; r <= bottom; r++) {
            for (int c = left; c <= right; c++) {
                if (r == x && c == y) continue;
                if (board[r][c] == cr) return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Leetcode0913 lc = new Leetcode0913();
        int a = 2;
        System.out.println((char)(a + 48));
    }
}
