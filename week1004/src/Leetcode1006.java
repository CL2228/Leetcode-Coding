import java.util.*;

public class Leetcode1006 {

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

    static class Node {
        public int val;
        public List<Node> children;


        public Node() {
            children = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<Node>();
        }

        public Node(int _val,ArrayList<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    // [E] 67       Add Binary
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int lastProceed = 0;
        int aIdx = a.length() - 1, bIdx = b.length() - 1;
        while (aIdx >= 0 || bIdx >= 0) {
            int cA = 0, cB = 0;
            if (aIdx >= 0) cA = (int)(a.charAt(aIdx) - '0');
            if (bIdx >= 0) cB = (int)(b.charAt(bIdx) - '0');

            int currBit = (cA + cB + lastProceed) % 2;
            sb.insert(0, (char)(currBit + '0'));
            lastProceed = (cA + cB + lastProceed) / 2;
            aIdx--;
            bIdx--;
        }
        if (lastProceed > 0) sb.insert(0, '1');
        return sb.toString();
    }


    // [H] 269      Alien Dictionary
    public String alienOrder(String[] words) {
        if (words.length == 1) {        // the case of only one word in the dictionary
            boolean[] hasChar = new boolean[26];
            for (int i = 0; i < words[0].length(); i++) hasChar[words[0].charAt(i) - 'a'] = true;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++)
                if (hasChar[i]) sb.append((char)(i + 'a'));
            return sb.toString();
        }

        // use a boolean array to record the existed characters
        boolean[] hasChar = new boolean[26];
        Set<Integer>[] graph = new Set[26];                         // use an array of Set as a graph
        for (int i = 0; i < 26; i++) graph[i] = new HashSet<>();

        for (int i = 1; i < words.length; i++) {
            int[] differ = checkDifferent(words[i - 1], words[i], hasChar);     // find the edges of the graph
            if (differ[0] == -2) return "";                                     // if abnormal detected by the comparison, return
            if (differ[0] == -1) continue;                                      // if no information can be captured, continue
            graph[differ[0]].add(differ[1]);                                    // add the edge
        }

        Stack<Integer> stack = new Stack<>();                                   // a stack used to store postorder traversal
        boolean[] visited = new boolean[26];                                    // used for cycle detection
        boolean[] cleared = new boolean[26];                                    // used for speed up and skip visited nodes

        for (int i = 0; i < 26; i++) {                                          // dfs
            if (hasChar[i]) {
                if (!dfs(i, graph, visited, stack, cleared)) return "";
            }
        }
        StringBuilder sb = new StringBuilder();                                 // reverse the postorder as the result of topological sort
        while (!stack.isEmpty()) sb.append((char)(stack.pop() + 'a'));
        return sb.toString();
    }
    private boolean dfs(int idx, Set<Integer>[] graph, boolean[] visited, Stack<Integer> stack, boolean[] cleared) {
        if (visited[idx]) return false;                     // if already visited, means there is a cycle, return the whole program
        if (cleared[idx]) return true;                      // if this idx is cleared, skip it
        visited[idx] = true;                                // backtrack
        for (int i : graph[idx]) {                          // dfs
            if (!dfs(i, graph, visited, stack, cleared)) return false;
        }
        stack.push(idx);                                    // postorder traversal
        visited[idx] = false;                               // backtrack
        cleared[idx] = true;                                // clear this node
        return true;
    }
    private int[] checkDifferent(String a, String b, boolean[] hasChar) {
        int idx = 0;
        boolean found = false;
        int[] res = {-1, -1};

        while (idx < a.length() && idx < b.length()) {      // detect whether there is a difference
            hasChar[a.charAt(idx) - 'a'] = true;
            hasChar[b.charAt(idx) - 'a'] = true;
            if (a.charAt(idx) != b.charAt(idx) && !found) { // if difference occurs, records it and keeps travel the whole strings
                res = new int[]{a.charAt(idx) - 'a', b.charAt(idx) - 'a'};
                found = true;
            }
            idx++;
        }
        // if no difference for the first part, but the left is greater than the right, it's wrong, mark it and return
        if (!found && a.length() > b.length()) return new int[]{-2, -2};

        if (idx < b.length()) {                 // keep visiting the remaining part
            while (idx < b.length()) {
                hasChar[b.charAt(idx) - 'a'] = true;
                idx++;
            }
        }
        if (idx < a.length()) {
            while (idx < a.length()) {
                hasChar[a.charAt(idx) - 'a'] = true;
                idx++;
            }
         }
        return res;
    }


    // [E] 415      Add Strings
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();

        int lastTerm = 0;
        int idx1 = num1.length() - 1, idx2 = num2.length() - 1;
        while (idx1 >= 0 || idx2 >= 0) {
            int term1 = 0;
            if (idx1 >= 0) {
                term1 = (int)(num1.charAt(idx1) - '0');
                idx1--;
            }
            int term2 = 0;
            if (idx2 >= 0) {
                term2 = (int)(num2.charAt(idx2) - '0');
                idx2--;
            }
            int currTerm = (term1 + term2 + lastTerm) % 10;
            sb.insert(0, (char)(currTerm + '0'));
            lastTerm = (term1 + term2 + lastTerm) / 10;
        }
        if (lastTerm != 0) sb.insert(0, (char)(lastTerm + '0'));
        return sb.toString();
    }


    // [M] 249      Group Shifted Strings
    public List<List<String>> groupStrings(String[] strings) {

        // 1. a map, whose keys are the length, and whose values are a map<String, List<String>>
        // 2. in the subset, the key, which is a string, is the pattern of the string,
        //      which is calculated by deduction of two adjacent characters
        Map<Integer, Map<String, List<String>>> data = new HashMap<>();

        for (String str : strings) {
            int length = str.length();
            String pattern = computePattern(str);
            if (!data.containsKey(length)) {            // if no data of this length has been recorded before
                Map<String, List<String>> subMap = new HashMap<>();     // create a sub map for this length
                subMap.put(pattern, new LinkedList<>(Arrays.asList(str)));      // add the sub map
                data.put(length, subMap);
            }
            else {
                Map<String, List<String>> subMap = data.get(length);        // if some data of this length are recorded before
                                                                                // get the sub map
                if (!subMap.containsKey(pattern))                               // to see whether the pattern is recorded before
                    subMap.put(pattern, new LinkedList<>(Arrays.asList(str)));
                else subMap.get(pattern).add(str);
            }
        }

        List<List<String>> res = new LinkedList<>();                    // adding results
        for (int key : data.keySet()) {
            Map<String, List<String>> subMap = data.get(key);
            res.addAll(subMap.values());
        }
        return res;
    }
    private String computePattern(String s) {
        if (s.length() == 1) return "1";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < s.length(); i++) {
            int difference = s.charAt(i) - s.charAt(i - 1);
            if (difference < 0) difference += 26;
            if (difference < 10) sb.append('0');
            sb.append(String.valueOf(difference));
        }
        return sb.toString();
    }


    // [M] 848      Shifting Letters
    public String shiftingLetters(String s, int[] shifts) {
        shifts[shifts.length - 1] = shifts[shifts.length - 1] % 26;
        for (int i = shifts.length - 2; i >= 0; i--) {
            shifts[i] = shifts[i + 1] + shifts[i];
            shifts[i] = shifts[i] % 26;
        }
        char[] data = s.toCharArray();
        for (int i = 0; i < s.length(); i++)
           data[i] = (char)((shifts[i] + data[i] - 'a') % 26 + 'a');
        return String.valueOf(data);
    }


    // [M] 523      Continuous Subarray Sum
    public boolean checkSubarraySum(int[] nums, int k) {
        // this program uses a hash map, whose keys are the prefix sum,
        // and its values are the frequency of that prefix sum
        Map<Long, Integer> map = new HashMap<>();
        long[] prefixSum = new long[nums.length];       // since the data scale can exceed the maximum value of Integer, use long
        prefixSum[0] = nums[0];     // put the prefix sum[0] to the map
        map.put(prefixSum[0], 1);

        for (int i = 1; i < nums.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];      // calculate the prefix sum, if this value can be divided by k, return true
            if (prefixSum[i] % k == 0) return true;

            long target = 0;                                // the gap between the current prefix sum and the target prefix
            while (target < prefixSum[i]) {                 // the gap must be less than the current prefix sum
                if (map.containsKey(prefixSum[i] - target)) {   // if this key exists
                    if (prefixSum[i - 1] != prefixSum[i] - target || map.get(prefixSum[i] - target) > 1)    // this key cannot be adjacent to the current prefix sum
                        return true;
                }
                target += k;                                // increase the gap
            }
            if (!map.containsKey(prefixSum[i])) map.put(prefixSum[i], 1);       // update the map
            else map.replace(prefixSum[i], map.get(prefixSum[i]) + 1);
        }
        return false;
    }


    // [E] 543      Diameter of Binary Tree
    public int diameterOfBinaryTree(TreeNode root) {
        // uses dynamic programing
        int[] res = postOrderSearch(root);
        return Math.max(res[0], res[1]);
    }
    private int[] postOrderSearch(TreeNode curr) {
        // return [String, Cross]
        if (curr.left == null && curr.right == null) return new int[]{0, 0};

        int[] leftRes = {0, 0};
        if (curr.left != null) leftRes = postOrderSearch(curr.left);
        int[] rightRes = {0, 0};
        if (curr.right != null) rightRes = postOrderSearch(curr.right);

        int currString = 1;
        currString += Math.max(leftRes[0], rightRes[0]);

        int currCross = 0;
        if (curr.left != null) currCross += (1 + leftRes[0]);
        if (curr.right != null) currCross += (1 + rightRes[0]);

        int maxCross = Math.max(currCross, Math.max(leftRes[1], rightRes[1]));

        return new int[]{currString, maxCross};
    }


    // [M] 1522     Diameter of N-Ary Tree
    int maxDia = Integer.MIN_VALUE;
    public int diameter(Node root) {
        dfs1522(root);
        return maxDia;
    }
    private int dfs1522(Node curr) {
        if (curr == null) return 0;
        List<Integer> res = new ArrayList<>();
        for (Node child : curr.children)
            res.add(dfs1522(child));
        if (res.size() == 0) return 1;
        else if (res.size() == 1) {
            maxDia = Math.max(maxDia, res.get(0) + 1);
            return res.get(0) + 1;
        }
        else {
            Collections.sort(res);
            maxDia = Math.max(maxDia, res.get(res.size() - 1) + res.get(res.size() - 2));
            return res.get(res.size() - 1) + 1;
        }
    }


    public static void main(String[] args) {

        Leetcode1006 lc = new Leetcode1006();
        int[] a = {5,0,0,0};
        System.out.println(lc.checkSubarraySum(a, 6));
    }
}
