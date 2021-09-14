import edu.princeton.cs.algs4.In;
import org.w3c.dom.Node;

import java.util.*;

public class Leetcode0829 {
    // [M] 71       Simplify Path
    public String simplifyPath1(String path) {
        Deque<int[]> deque = new LinkedList<>();
        int stIdx = 0, edIdx;
        while (stIdx < path.length()) {
            edIdx = stIdx;
            int[] idx = new int[2];

            if (path.charAt(stIdx) == '/') stIdx++;
            else if (path.charAt(stIdx) == '.') {       //  the case starting with a '.'
                while (edIdx < path.length() && path.charAt(edIdx) == '.') edIdx++;

                if (edIdx - stIdx == 1) { // the case of /.xxx
                    if (edIdx < path.length() && path.charAt(edIdx) != '/') {
                        while (edIdx < path.length() && path.charAt(edIdx) != '/') edIdx++;
                        if (edIdx - stIdx > 1) {
                            idx[0] = stIdx;
                            idx[1] = edIdx;
                            deque.offerLast(idx);
                        }
                    }
                    stIdx = edIdx + 1;
                }
                else if (edIdx - stIdx == 2) {

                    if (edIdx < path.length() && path.charAt(edIdx) != '/') {       // the case of  /..xxx
                        while (edIdx < path.length() && path.charAt(edIdx) != '/') edIdx++;
                        idx[0] = stIdx;
                        idx[1] = edIdx;
                        deque.offerLast(idx);
                        stIdx = edIdx + 1;
                    }
                    else {          // the case of return:  /../
                        if (!deque.isEmpty()) deque.pollLast();
                        stIdx = edIdx;
                    }
                }
                else {
                    System.out.println("signal:  " + path.substring(stIdx, edIdx) +
                            "  stIdx:" + stIdx +
                            "  edIdx:" + edIdx +
                            "  ed - st:" + (edIdx - stIdx) +
                            "  st:" + path.charAt(stIdx) +
                            "  ed:" + path.charAt(edIdx - 1));

                    while (edIdx < path.length() && path.charAt(edIdx) != '/') edIdx++;


                    idx[0] = stIdx;
                    idx[1] = edIdx;
                    deque.offerLast(idx);
                    stIdx = edIdx + 1;
                }
            }
            else {
                while (edIdx != path.length() && path.charAt(edIdx) != '/') edIdx++;

                idx[0] = stIdx;
                idx[1] = edIdx;
                deque.offerLast(idx);
                stIdx = edIdx + 1;
            }
        }

        if (deque.isEmpty()) return "/";
        else {
            StringBuilder sb = new StringBuilder();
            while (!deque.isEmpty()) {
                int[] idx = deque.pollFirst();
                sb.append("/").append(path.substring(idx[0],idx[1]));
            }
            return sb.toString();
        }
    }
    public String simplifyPath(String path) {
        Deque<String> deque = new LinkedList<>();
        Set<String> skip = new HashSet<>(Arrays.asList(".", "..", ""));
        for (String s : path.split("/")) {
            if (s.equals("..") && !deque.isEmpty()) deque.pollLast();
            else if (!skip.contains(s))
                deque.offerLast(s);
        }
        if (deque.isEmpty()) return "/";
        else {
            StringBuilder sb = new StringBuilder();
            while (!deque.isEmpty()) sb.append("/").append(deque.pollFirst());
            return sb.toString();
        }
    }


    // [M] 91       Decode Ways
    // TODO: this problem is not solved!
    public int numDecodings(String s) {
        if (s.length() == 0) return 0;
        else return decodeNum(0, s);
    }
    private int decodeNum(int pos, String s) {
        int N = s.length();
        if (pos == N) return 1;
        if (s.charAt(pos) == '0') return 0;
        int count = decodeNum(pos + 1, s);
        if (pos < N - 1 && (s.charAt(pos) == '1' || s.charAt(pos) == '2') && Integer.parseInt(s.substring(pos + 1, pos + 2)) < 7)
            count += decodeNum(pos + 2, s);
        return count;
    }








    public static void main(String[] args) {
        Leetcode0829 leetcode0829 = new Leetcode0829();
        String a = "06";
        System.out.print(leetcode0829.numDecodings(a));
    }
}
