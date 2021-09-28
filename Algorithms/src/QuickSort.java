public class QuickSort <Item extends Comparable<Item>>{


    public void quicksort(Item[] a) {
        quicksort(a, 0, a.length - 1);
    }


    private void quicksort(Item[] a, int lo, int hi) {
        if (lo >= hi) return;
        int j = partition(a, lo, hi);
        exch(a, j, lo);
        quicksort(a, lo, j - 1);
        quicksort(a, j + 1, hi);
    }


    private int partition(Item[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i].compareTo(a[lo]) <= 0)
                if (i >= hi) break;
            while (a[--j].compareTo(a[hi]) >= 0)
                if (j <= lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        return j;
    }

    private void exch(Item[] a, int i, int j) {
        Item swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }




}
