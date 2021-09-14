import java.util.*;

public class Leetcode0826 {


    // [H] 239      Sliding Window Maximum
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 1) return nums;
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < k - 1; i++)
            pushDeque(deque, nums[i]);

        for (int i = k - 1; i < nums.length; i++ ) {
            pushDeque(deque, nums[i]);
            result[i - k + 1] = getPop(deque, nums[i - k + 1]);
        }
        return result;
    }
    private void pushDeque(Deque<Integer> deque, int ele) {
        while (!deque.isEmpty() && deque.peekLast() < ele) deque.removeLast();
        deque.offerLast(ele);
    }
    private int getPop(Deque<Integer> deque, int ele) {
        assert !deque.isEmpty();
        if (deque.peek() != ele) return deque.peek();
        else return deque.pollFirst();
    }
    private void visualizeDeque(Deque<Integer> deque) {
        for (int i : deque) System.out.print(i + " ");
        System.out.println();
    }


    // [M] 347      Top K Frequent Elements
    public int[] topKFrequentQS(int[] nums, int k) {
        // this a version that first uses hashMap to calculate the frequency of each element
        // than use quick sort to find out the top K elements
        int[] result = new int[k];
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            if (map.containsKey(n)) map.replace(n, map.get(n) + 1);
            else map.put(n, 1);
        }
        freqNode[] data = new freqNode[map.size()];
        int i = 0;
        for (int key : map.keySet())
            data[i++] = new freqNode(key, map.get(key));
        quickSort(data, 0, data.length - 1);
        for (i = 0; i < k; i++)
            result[i] = data[i].key();
        return result;
    }
    private static class freqNode implements Comparable <freqNode>{
        private final int key;
        private final int freq;
        public freqNode(int key, int freq) {
            this.key = key;
            this.freq = freq;
        }
        public int compareTo(freqNode that) {
            return Integer.compare(this.freq, that.freq);
        }
        public int key() { return this.key; }
    }
    private void quickSort(freqNode[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, lo, j);
        quickSort(a, lo, j - 1);
        quickSort(a, j + 1, hi);
    }
    private void exch(freqNode[] a, int i, int j) {
        freqNode swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    private int partition(freqNode[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i].compareTo(a[lo]) >= 0)
                if (i >= hi) break;
            while (a[--j].compareTo(a[lo]) <= 0)
                if (j <= lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;
    }
    public int[] topKFrequent(int[] nums, int k) {
        // this version uses priority queue, which is much faster and saves more memory
        int[] result = new int[k];
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            if (map.containsKey(n)) map.replace(n, map.get(n) + 1);
            else map.put(n, 1);
        }
        PriorityQueue<freqNode> pq = new PriorityQueue<>(map.size());



        return result;
    }



    // [M] 71       Simplify Path
    public String simplifyPath(String path) {
        if (path.equals("/...")) return path;
        if (path.equals("/.../")) return "/...";
        char[] data = path.toCharArray();
        Deque<int[]> deque = new LinkedList<>();

        int stIdx = 0, edIdx;
        while (stIdx < data.length) {
            edIdx = stIdx;
            if (data[stIdx] == '/') stIdx++;
            else if (data[stIdx] == '.') {
                while (edIdx < data.length && data[edIdx] == '.') edIdx++;

                if (edIdx - stIdx == 2){
                    if ((edIdx == data.length || data[edIdx] == '/')) {
                        if (!deque.isEmpty())
                            deque.pollLast();

                    }
                    else {
                        while (edIdx < data.length && data[edIdx] != '/') edIdx++;
                        int[] idx = new int[2];
                        idx[0] = stIdx;
                        idx[1] = edIdx - 1;
                        deque.offerLast(idx);
                    }
                }
                else {
                    while (edIdx < data.length && data[edIdx] != '/') edIdx++;
                    int[] idx = new int[2];
                    idx[0] = stIdx;
                    idx[1] = edIdx - 1;
                    deque.offerLast(idx);
                }
                stIdx = edIdx;
            }
            else {
                while (edIdx < data.length && data[edIdx] != '/') edIdx++;
                int[] idx = new int[2];
                idx[0] = stIdx;
                idx[1] = edIdx - 1;
                deque.offerLast(idx);
                stIdx = edIdx;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (deque.isEmpty()) stringBuilder.append("/");
        else {
            while (!deque.isEmpty()) {
                int[] idx = deque.pollFirst();
                char[] sub = new char[idx[1] - idx[0] + 1];
                System.arraycopy(data, idx[0], sub, 0, idx[1] - idx[0] + 1);
                stringBuilder.append("/").append(String.valueOf(sub));
            }
        }
        return stringBuilder.toString();
    }



    public static void main(String[] args) {
        Leetcode0826 leetcode0826 = new Leetcode0826();
        String a = "/.hidden";
        System.out.print(leetcode0826.simplifyPath(a));
    }
}
