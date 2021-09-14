import edu.princeton.cs.algs4.In;

public class Leetcode0823 {
    private class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // [E] 27       Remove Element
    public int removeElement(int[] nums, int val) {
        int newIndex = 0;
        for (int oldIndex = 0; oldIndex < nums.length; oldIndex++) {
            if (val != nums[oldIndex])
                nums[newIndex++] = nums[oldIndex];
        }
        return newIndex;
    }


    // [E] 344      Reverse String
    public void reverseString(char[] s) {
        int lo = 0, hi = s.length - 1;
        while (lo < hi) {
            char tmp = s[lo];
            s[lo++] = s[hi];
            s[hi--] = tmp;
        }
    }


    // [E] 剑指5      Replace Space
    public String replaceSpace(String s) {
        char[] data = s.toCharArray();
        int count = 0;
        for (char i : data)
            if (i == ' ') count++;
        char[] newData = new char[data.length + 2 * count];
        int newIndex = 0;
        for (char datum : data) {
            if (datum == ' ') {
                newData[newIndex++] = '%';
                newData[newIndex++] = '2';
                newData[newIndex++] = '0';
            } else newData[newIndex++] = datum;
        }
        return String.valueOf(newData);
    }


    // [M] 151      Reverse Words in a String
    public String reverseWords(String s) {
        char[] data = s.toCharArray();
        int newIndex = 0;
        for (int i = 0; i < data.length; i++) {
            if (newIndex == 0 && data[i] == ' ') continue;
            if (data[i] != ' ' || data[i - 1] != ' ')
                data[newIndex++] = data[i];
        }
        if (data[newIndex - 1] == ' ') newIndex--;

        char[] newData = new char[newIndex];
        System.arraycopy(data, 0, newData, 0, newIndex);
        reverseSubChar(newData, 0, newIndex - 1);
        data = newData;

        int stIdx = 0, edIdx;
        while (stIdx < data.length) {
            edIdx = stIdx;
            while (edIdx < data.length && data[edIdx] != ' ') edIdx++;
            reverseSubChar(data, stIdx, edIdx - 1);
            stIdx = edIdx + 1;
        }
        return String.valueOf(data);
    }
    private void reverseSubChar(char[] data, int lo, int hi) {
        if (lo >= hi) return;
        while (lo < hi) {
            char tmp = data[lo];
            data[lo++] = data[hi];
            data[hi--] = tmp;
        }
    }


    // [E] 206      Reverse Linked List
    public ListNode reverseList(ListNode head) {
        return reverseNode(head, null);
    }
    private ListNode reverseNode(ListNode cur, ListNode pre) {
        if (cur == null) return pre;
        ListNode next = cur.next;
        cur.next = pre;
        return reverseNode(next, cur);
    }









    public static void main(String[] args) {
        Leetcode0823 leetcode0823 = new Leetcode0823();
    }
}