import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tusimple {

    public static int maxEvents(List<Integer> arrival, List<Integer> duration) {
        if (arrival.size() == 1) return 1;
        int[] ars = new int[arrival.size()];
        int[] ends = new int[duration.size()];
        int idx = 0;
        for (int i : arrival) ars[idx++] = i;
        idx = 0;
        for (int i : duration) {
            ends[idx] = i + ars[idx];
            idx++;
        }
        for (int i : ars) System.out.print(i + " ");
        System.out.println();
        for (int i : ends) System.out.print(i + " ");
        System.out.println();
        
        
        quicksort(ars, ends, 0, ars.length - 1);

        int edTime = ends[0];
        int count = 1;

        for (int i = 1; i < ars.length; i++) {
            if (ars[i] >= edTime) {
                edTime = ends[i];
                count++;
            }
        }


        return count;
    }
    private static void quicksort(int[] arrival, int[] end, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(arrival, end, lo, hi);
        exch(arrival, end, j, lo);
        quicksort(arrival, end, lo, j - 1);
        quicksort(arrival, end, j + 1, hi);
    }
    private static void exch(int[] arrival, int[] end, int i, int j) {
        int swap = arrival[i];
        arrival[i] = arrival[j];
        arrival[j] = swap;

        swap = end[i];
        end[i] = end[j];
        end[j] = swap;
    }
    private static int partition(int[] arr, int[] end, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (end[++i] < end[lo] || (end[i] == end[lo] && arr[i] >= arr[lo]))
                if (i >= hi) break;
            while (end[--j] > end[lo] || (end[j] == end[lo] && arr[j] <= arr[lo]))
                if (j <= lo) break;
            if (i >= j) break;
            exch(arr, end, i, j);
        }
        return j;
    }




    public static int minMoves(int n, int startRow, int startCol, int endRow, int endCol) {
        int[][] board = new int[n][n];
        board[startRow][startRow] = 1;
        board[endRow][endCol] = 2;          //  end point set as 2
        int[] mini = new int[2];
        mini[0] = Integer.MAX_VALUE;
        backtracking(board, startRow, startCol, endRow, endCol, 0, mini);
        return mini[0] - 1;
    }
    public static void backtracking(int[][] board, int r, int c, int endRow, int endCol, int s, int[] mini) {
        int step = s + 1;
        if (step > mini[0]) return;

        if (board[r][c] == 2) {
            if (step < mini[0]) mini[0] = step;
        }
        else {
            board[r][c] = 1;
            List<List<Integer>> WL = getWorkableList(board, r, c, endRow, endCol);
            for (List<Integer> candidate : WL)
                backtracking(board, candidate.get(0), candidate.get(1), endRow, endCol, step, mini);
            board[r][c] = 0;
        }
    }


    public static List<List<Integer>> getWorkableList(int[][] board, int r, int c, int endR, int endC) {
        List<List<Integer>> list = new ArrayList<>();
        int[][] WLOrder = getOrder(r, c, endR, endC);
        for (int[] coordinate : WLOrder) {
            if (workable(board, coordinate[0], coordinate[1])) {
                if (board[coordinate[0]][coordinate[1]] == 2) {
                    List<List<Integer>> res = new ArrayList<>();
                    res.add(new ArrayList<>(Arrays.asList(coordinate[0], coordinate[1])));
                    return res;
                }
                else {
                    list.add(new ArrayList<>(Arrays.asList(coordinate[0], coordinate[1])));
                }
            }
        }
        return list;
    }

    public static boolean workable(int[][] board, int r, int c) {
        int N = board.length;
        if (r < 0 || c < 0 || r >= N || c >= N) return false;
        if (board[r][c] != 1) return true;
        else return false;
    }

    public static int[][] getOrder(int r, int c, int endR, int endC) {
        if (endR <= r && endC >= c) {
            int[][] a = {{r - 2, c + 1}, {r - 1, c + 2}, {r + 1, c + 2}, {r + 2, c + 1}, {r + 2, c - 1}, {r + 1, c - 2}, {r - 2, c - 1}, {r - 1, c - 2}};
            return a;
        }
        else if (endR >= r && endC >= c) {
            int[][] a = {{r + 1, c + 2}, {r + 2, c + 1}, {r - 1, c + 2}, {r + 2, c - 1}, {r - 2, c + 1}, {r + 1, c - 2}, {r - 1, c - 2}, {r - 2, c - 1}};
            return a;
        }
        else if (endR >= r && endC <= c) {
            int[][] a = {{r + 2, c - 1}, {r + 1, c- 2}, {r - 1, c - 2}, {r + 2, c + 1}, {r - 2, c - 1}, {r - 2, c + 1}, {r - 1, c + 2}, {r + 1, c + 2}};
            return a;
        }
        else {
            int[][] a = {{r - 1, c - 2}, {r - 2, c - 1}, {r + 1, c - 2}, {r - 2, c + 1}, {r + 2, c - 1}, {r - 1, c + 2}, {r + 1, c + 2}, {r + 2, c + 1}};
            return a;
        }
    }




    public static void main(String[] args) {
        int[] arr = {1,3,3,5,7};
        int[] dur = {2,2,1,2,1};

        System.out.println(minMoves(10, 9, 9, 5, 3));
    }
}
