import java.util.*;

public class Leetcode1004 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
     }
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }


    // [E] 35       Search Insert Position
    public int searchInsert(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1, mid = (lo + hi) / 2;

        while (lo <= hi) {
            if (target == nums[mid]) return mid;
            else if (target < nums[mid]) {
                if (mid == lo) return lo;
                hi = mid - 1;
            }
            else {
                if (mid == hi) return hi + 1;
                lo = mid + 1;
            }
            mid = (lo + hi) / 2;
        }
        return hi;
    }


    // [M] 143      Reorder List
    public void reorderList(ListNode head) {
        int N = 0;
        ListNode curr = head;
        while (curr != null) {
            curr = curr.next;
            N++;
        }
        // get segment the second part
        ListNode firstHead = head;
        ListNode secondHead = null;
        curr = head;
        int idx = 0;
        while (curr != null) {
            idx++;
            if (idx == N / 2) {
                secondHead = curr.next;
                curr.next = null;
                break;
            }
            curr = curr.next;
        }

        // reverse the second part
        ListNode preNode = null;
        curr = secondHead;
        while (curr != null) {
            ListNode currNext = curr.next;
            curr.next = preNode;
            preNode = curr;
            curr = currNext;
        }
        secondHead = preNode;

        // merge two parts
        ListNode virtualHead = new ListNode(-100);
        while (firstHead != null || secondHead != null) {
            if (firstHead != null) {
                virtualHead.next = firstHead;
                firstHead = firstHead.next;
                virtualHead = virtualHead.next;
                virtualHead.next = null;
            }

            if (secondHead != null) {
                virtualHead.next = secondHead;
                secondHead = secondHead.next;
                virtualHead = virtualHead.next;
                virtualHead.next = null;
            }
        }
    }
    public void reorderList1(ListNode head) {
        // this approach uses a deque to store all nodes, which uses a lot of memory
        Deque<ListNode> deque = new LinkedList<>();
        ListNode curr = head;
        while (curr != null) {
            deque.offerLast(curr);
            curr = curr.next;
        }

        ListNode virtualHead = new ListNode(-100);
        curr = virtualHead;

        while (!deque.isEmpty()) {
            ListNode nextLeft = deque.pollFirst();
            curr.next = nextLeft;
            curr = curr.next;
            curr.next = null;
            if (!deque.isEmpty()) {
                curr.next = deque.pollLast();
                curr = curr.next;
                curr.next = null;
            }
        }
    }


    // [E] 2000     Reverse Prefix of Word
    public String reversePrefix(String word, char ch) {
        int idx = -1;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ch) {
                idx = i;
                break;
            }
        }
        if (idx == -1) return word;
        char[] subStr = word.substring(0, idx + 1).toCharArray();
        reverseSubChar(subStr, 0, idx);
        StringBuilder sb = new StringBuilder(String.valueOf(subStr));
        if (idx < word.length() - 1) sb.append(word.substring(idx + 1, word.length()));
        return sb.toString();
    }
    private void reverseSubChar(char[] subStr, int lo, int hi) {
        while (lo < hi) {
            char swap = subStr[lo];
            subStr[lo] = subStr[hi];
            subStr[hi] = swap;
            lo++;
            hi--;
        }
    }


    // [E] 205      Isomorphic Strings
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> map = new HashMap<>();
        Set<Character> used = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                if (used.contains(t.charAt(i))) return false;
                map.put(s.charAt(i), t.charAt(i));
                used.add(t.charAt(i));
            }
            else if (map.get(s.charAt(i)) != t.charAt(i)) return false;
        }
        return true;
    }
    private void quickSort205(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition205(a, lo, hi);
        exch205(a, lo, j);
        quickSort205(a, lo, j - 1);
        quickSort205(a, j + 1, hi);
    }
    private void exch205(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition205(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] <= a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch205(a, i, j);
        }
        return j;
    }


    // [E] 925      Long Pressed Name
    public boolean isLongPressedName(String name, String typed) {
        if (name.length() > typed.length()) return false;
        int spIdx = 0, queryIdx = 0;

        while (spIdx < name.length()) {
//            if (queryIdx == typed.length()) return false;
            if (name.charAt(spIdx) == typed.charAt(queryIdx)) {
                spIdx++;
                queryIdx++;
            }
            else {
                if (queryIdx == 0) return false;
                else if (typed.charAt(queryIdx) == typed.charAt(queryIdx - 1)) queryIdx++;
                else return false;
            }
        }
        while (queryIdx < typed.length()) {
            if (typed.charAt(queryIdx) == typed.charAt(queryIdx - 1)) queryIdx++;
            else return false;
        }
        return true;
    }


    // [M] 1048     Longest String Chain
    public int longestStrChain(String[] words) {
        int maxLen = 1;
        Map<String, Integer>[] chainLen = new Map[17];
        for (int i = 0; i < chainLen.length; i++) chainLen[i] = new HashMap<String, Integer>();
        for (String word : words) {
            chainLen[word.length()].put(word, 1);
        }

        for (int i = 2; i < chainLen.length; i++) {
            for (String word : chainLen[i].keySet()) {
                int subMax = 1;
                StringBuilder sb = new StringBuilder(word);
                for (int idx = 0; idx < sb.length(); idx++) {
                    char deleted = sb.charAt(idx);
                    sb.deleteCharAt(idx);

                    String afterDeleted = sb.toString();
                    if (chainLen[i - 1].containsKey(afterDeleted))
                        subMax = Math.max(subMax, 1 + chainLen[i - 1].get(afterDeleted));
                    sb.insert(idx, deleted);
                }
                chainLen[i].put(word, subMax);
                maxLen = Math.max(maxLen, subMax);
            }
        }
        return maxLen;
    }


    // [M] 912      Sort an Array
    public int[] sortArray(int[] nums) {
        Random rand = new Random();
        for (int i = 0; i < nums.length; i++) {
            int ranIdx = rand.nextInt(nums.length);
            int swap = nums[ranIdx];
            nums[ranIdx] = nums[i];
            nums[i] = swap;
        }
        quickSort912(nums, 0, nums.length - 1);
        return nums;
    }
    private void quickSort912(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition912(a, lo, hi);
        exch912(a, j, lo);
        quickSort912(a, lo, j - 1);
        quickSort912(a, j + 1, hi);
    }
    private void exch912(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition912(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] <= a[lo])
                if (i >= hi) break;
            while (a[--j] >= a[lo])
                if (j <= lo) break;
            if (i >= j) break;
            exch912(a, i, j);
        }
        return j;
    }


    // [E] 844      Backspace String Compare
    public boolean backspaceCompare(String s, String t) {
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '#') {
                if (a.length() > 0) a.deleteCharAt(a.length() - 1);
            }
            else a.append(s.charAt(i));
        }
        for (int i = 0; i < t.length(); i++) {
            if (t.charAt(i) == '#') {
                if (b.length() > 0) b.deleteCharAt(b.length() - 1);
            }
            else b.append(t.charAt(i));
        }
        System.out.println(a.toString());
        System.out.println(b.toString());

        return a.toString().equals(b.toString());
    }


    // [M] 129      Sum Root to Leaf Numbers
    public int sumNumbers(TreeNode root) {
        int[] sum = {0};
        dfs129(root, 0, sum);
        return sum[0];
    }
    private void dfs129(TreeNode curr, int preSum, int[] res) {
        int currSum = preSum * 10 + curr.val;

        if (curr.left == null && curr.right == null) {
            res[0] += currSum;
            return;
        }

        if (curr.left != null) dfs129(curr.left, currSum, res);
        if (curr.right != null) dfs129(curr.right, currSum, res);
    }


    // [E] 100      Same Tree
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q ==null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    private boolean sameSubTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q ==null) return false;
        if (p.val != q.val) return false;
        return sameSubTree(p.left, q.left) && sameSubTree(p.right, q.right);
    }


    // [M] 417      Pacific Atlantic Water Flow
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int M = heights.length, N = heights[0].length;
        List<List<Integer>> res = new ArrayList<>();

        boolean[][] pacific = new boolean[M][N];
        boolean[][] atlantic = new boolean[M][N];

        Deque<int[]> dequePacific = new LinkedList<>();
        for (int c = 0; c < N; c++) {
            pacific[0][c] = true;
            dequePacific.offerLast(new int[]{0, c});
        }
        for (int r = 0; r < M; r++) {
            pacific[r][0] = true;
            dequePacific.offerLast(new int[]{r, 0});
        }

        Deque<int[]> dequeAtlantic = new LinkedList<>();
        for (int c = 0; c < N; c++) {
            atlantic[M - 1][c] = true;
            dequeAtlantic.offerLast(new int[]{M - 1, c});
        }
        for (int r = 0; r < M; r++) {
            atlantic[r][N - 1] = true;
            dequeAtlantic.offerLast(new int[]{r, N - 1});
        }

        visitOcean(pacific, dequePacific, heights);
        visitOcean(atlantic, dequeAtlantic, heights);

        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (pacific[r][c] && atlantic[r][c])
                    res.add(new ArrayList<>(Arrays.asList(r, c)));
            }
        }
        return res;
    }
    private void visitOcean(boolean[][] visited, Deque<int[]> deque, int[][] heights) {
        int M = heights.length, N = heights[0].length;
        while (!deque.isEmpty()) {
            int[] curr = deque.pollFirst();
            int currR = curr[0], currC =curr[1];

            if (currR > 0 && !visited[currR - 1][currC] && heights[currR - 1][currC] >= heights[currR][currC]) {
                visited[currR - 1][currC] = true;
                deque.offerLast(new int[]{currR - 1, currC});
            }
            if (currR < M - 1 && !visited[currR + 1][currC] && heights[currR + 1][currC] >= heights[currR][currC]) {
                visited[currR + 1][currC] = true;
                deque.offerLast(new int[]{currR + 1, currC});
            }
            if (currC > 0 && !visited[currR][currC - 1] && heights[currR][currC - 1] >= heights[currR][currC]) {
                visited[currR][currC - 1] = true;
                deque.offerLast(new int[]{currR, currC - 1});
            }
            if (currC < N - 1 && !visited[currR][currC + 1] && heights[currR][currC + 1] >= heights[currR][currC]) {
                visited[currR][currC + 1] = true;
                deque.offerLast(new int[]{currR, currC + 1});
            }
        }
    }


    // [M] 526      Beautiful Arrangement
    public int countArrangement(int n) {
        int[] res = {0};
        Set<Integer> preSet = new HashSet<>();
        boolean[] visited = new boolean[n + 1];
        for (int i = 1; i <= n; i++) preSet.add(i);
        backtrack526(visited, 1, res, n);
        return res[0];
    }
    private void backtrack526(boolean[] visited, int index, int[] res, int n) {
        if (index > n) {
            res[0]++;
            return;
        }
        for (int i = 1; i < visited.length; i++) {
            if (!visited[i] && (i % index == 0 || index % i == 0)) {
                visited[i] = true;
                backtrack526(visited, index + 1, res, n);

                visited[i] = false;
            }
//            if (key % index == 0 || index % key == 0) {
//                nextSet.remove(key);
//                backtrack526(nextSet, index + 1, res);
//                nextSet.add(key);
//            }
        }
    }




    public static String[] isPrimeNumber(int[] elements) {
        String[] ans = new String[elements.length];

        for (int i = 0; i < elements.length; i++) {
            ans[i] = "Prime";
            for (int k = 2; k <= (elements[i] / 2); k++) {
                if (elements[i] % k == 0) {
                    ans[i] = "Composite";
                    break;
                }

            }
        }
        for (String a: ans) System.out.print(a + "  ");
        return ans;
    }


    public static void main(String[] args) {
        Leetcode1004 lc = new Leetcode1004();
        String a = "xywrrmp", b = "xywrrmu#p";
        System.out.println(lc.backspaceCompare(a, b));
    }
}
