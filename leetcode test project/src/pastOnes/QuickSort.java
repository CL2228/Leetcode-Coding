package pastOnes;

public class QuickSort {
    // a test for quick of int[]
    int[] result;

    public QuickSort(int[] a) {
        for (int i = 0; i < a.length; i++) a[i] = a[i] * a[i];
        sort(a);
        this.result = a;
    }

    private void sort(int[]a) {
        sort(a, 0, a.length - 1);

    }
    private void sort(int[] a, int lo, int hi) {
        if (lo >= hi) return;

        int j = partition(a, lo, hi);
        exch(a, lo, j);

        sort(a, lo, j - 1);
        sort(a, j + 1, hi);

    }
    private int partition(int[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (a[++i] < a[lo]) {
                if (i >= hi) break;
            }
            while (a[--j] > a[lo]) {
                if (j <= lo) break;
            }
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;
    }
    private void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public int[] getResult() { return result;}


    public static void main(String[] args) {
        int[] a = {-1, -3, -7,4 ,5, 6};
        QuickSort quickSort = new QuickSort(a);
        for (int i : quickSort.getResult()) {
            System.out.print(i + "  ");
        }
    }
}
