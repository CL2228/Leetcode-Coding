// Total number: 15
// TODO: Worth attention: 78,
package pastOnes;


import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Chapter2Array {
    /****************************************************
     *  二分法     July/26/2021
     ****************************************************/
    // [E] LeetCode#709     Binary Search
    public int search(int[] nums, int target) {
        if (nums.length == 0) return -1;
        if (nums.length == 1 && nums[0] == target) return 0;

        int left = 0, right = nums.length - 1, middle = right /2;

        while (left < right) {
            if (nums[left] == target) return left;
            if (nums[right] == target) return right;

            if (nums[middle] >= target)     // on the left side
                right = middle;
            else
                left = middle + 1;
            middle = (left + right) / 2;
        }
        return -1;
    }


    // [E] LeetCode#35
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int middle = (left + right) / 2;

        while (right > left) {
            if (nums[right] == target) return right;
            if (nums[left] == target) return left;

            if (nums[middle] >= target) {    // explore left side
                right = middle;
            }

            if (nums[middle] < target)      // explore right side
                left = middle + 1;

            middle = (left + right ) / 2;

            System.out.println("left: " + left + "  middle: " + middle + "  right: " + right);
        }

        // index not found, now right = left
        if (nums[left] < target) return left + 1;
        else return left;
    }


    // [M] LeetCode#34
    public int[] searchRange(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        int middle = (left + right) / 2;
        int[] result = new int[2];

        if (nums.length == 0) {
            result[0] = -1;
            result[1] = -1;
            return result;

        }

        if (nums.length == 1 && nums[0] == target)
            return result;

        while (right > left) {
            if (nums[middle] >= target)
                right = middle;
            else left = middle + 1;
            middle = (left + right) / 2;
        }

        if (nums[middle] == target) {       // search ends
            left = middle;
            right = middle;

            while (left >= 0 && nums[left] == target)
                left--;
            left++;

            while (right <= nums.length - 1 && nums[right] == target)
                right++;
            right--;

            result[0] = left;
            result[1] = right;
            return result;
        } else {
            result[0] = -1;
            result[1] = -1;
            return result;
        }
    }


    // [E] LeetCode#69      Sqrt
    public int mySqrt(int x) {
        if (x == 0) return 0;
        if (x == 1) return 1;
        int left = 0, right = x, middle = x / 2;
        if (middle > 46340)
            right = 46341;
            middle = right / 2;

        while (left < right - 1) {
            if (middle * middle == x) return middle;
            if (middle * middle < x)
                left = middle;
            else
                right = middle;
            middle = (left + right) / 2;

//            System.out.println("left:" + left + "  middle:" + middle + "right:" + right);
        }

        return left;
    }


    // [M] LeetCode#367
    public boolean isPerfectSquare(int num) {
        if (num == 0 || num == 1) return true;

        int left = 0, right = num, middle = right / 2;
        if (middle > 46340){
            right = 46341;
            middle = right / 2;
        }

        while (left < right - 1) {
            if (left * left == num) return true;
            if (right * right == num) return true;
            if (middle * middle == num) return true;

            if (middle * middle < num)
                left = middle;
            else
                right = middle;
            middle = (left + right) / 2;
//            System.out.println("left:" + left + "  middle:" + middle + "right:" + right);
        }

        return false;
    }



    /****************************************************
     *  移除数组元素 （双指针）    July/26/2021
     ****************************************************/
    // [E] LeetCode#27      Remove Element
    public int removeElement(int[] nums, int val) {
        int newIndex = 0, oldIndex = 0;

        for (oldIndex = 0; oldIndex < nums.length; oldIndex++) {
            if (nums[oldIndex] != val) {
                nums[newIndex] = nums[oldIndex];
                newIndex++;
            }
        }
        return newIndex;
    }


    // [E] LeetCode#26      Remove Duplicates from Sorted Array
    public int removeDuplicates(int[] nums) {
        int newIndex = 0, oldIndex;
        for (oldIndex = 1; oldIndex < nums.length; oldIndex++) {
            if (nums[newIndex] != nums[oldIndex])
                nums[++newIndex] = nums[oldIndex];
        }
        return newIndex + 1;
    }


    // [E] LeetCode#283     Move Zeros
    public void moveZeroes(int[] nums) {

        // the faster version, using two pointers
        int firstZero = 0, lastZero = 0;
        while (lastZero < nums.length - 1) {
            if (nums[firstZero] != 0) {     // no zeros found yet
                firstZero++;
                lastZero++;
            } else {                        // one/some zero(s) found, now nums[firstZero] = 0;

                if (firstZero == lastZero) {    // only one zero found now, firstZero = lastZero
                    if (nums[lastZero + 1] == 0)   // the next one is also zero
                        lastZero++;
                    else {                          // the next one is not zero
                        nums[lastZero] = nums[lastZero + 1];
                        nums[++lastZero] = 0;
                        ++firstZero;
                    }
                } else {                // some zeros have been found, firstZero != lastZero
                    if (nums[lastZero + 1] == 0)    // the one next to lastZero is still zero
                        lastZero++;
                    else {              // the one next to lastZero is not zero, exchange it with firstZero
                        nums[firstZero] = nums[lastZero + 1];
                        nums[++lastZero] = 0;
                        firstZero++;
                    }
                }
            }
        }



        /*
        the slower version, using double-loop

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                int nonZeroIndex = i + 1;


                while (nonZeroIndex < nums.length) {
                    if (nums[nonZeroIndex] != 0) {
                         // swap two positions
                        nums[i] = nums[nonZeroIndex];
                        nums[nonZeroIndex] = 0;
                        break;
                    } else nonZeroIndex++;
                }
                if (nonZeroIndex >= nums.length - 1) break;
            }
        }
         */
    }


    // [E] LeetCode#844     Backspace String Compare
    public boolean backspaceCompare(String s, String t) {
        Stack<Character> sStack = new Stack<Character>();
        Stack<Character> tStack = new Stack<Character>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '#') {
                if (sStack.size() > 0)
                    sStack.pop();
            }
            else
                sStack.push(s.charAt(i));
        }

        for (int i = 0; i < t.length(); i++) {
            if (t.charAt(i) == '#') {
                if (tStack.size() > 0)
                    tStack.pop();
            }
            else
                tStack.push(t.charAt(i));
        }

        if (tStack.size() != sStack.size()) return false;
        else {
            while (!tStack.isEmpty()) {
                if (sStack.pop() != tStack.pop()) return false;
            }
        }

        return true;
    }


    // [E] LeetCode#977     Squares of a Sorted Array
    public int[] sortedSquares(int[] nums) {
        int[] result = new int[nums.length];
        int i = 0, j = nums.length - 1, k = nums.length - 1;

        while (i <= j) {
            if (nums[i] * nums[i] < nums[j] * nums[j]) {
                result[k--] = nums[j] * nums[j];
                System.out.println(nums[j] * nums[j]);
                j--;
            }
            else {
                result[k--] = nums[i] * nums[i];
                System.out.println(nums[i] * nums[i]);

                i++;
            }
        }

        /* the version that uses quick sort
        for (int i = 0; i < nums.length; i++) nums[i] = nums[i] * nums[i];
        quickSortFor977(nums);
         */

        return result;
    }
    private void quickSortFor977(int[] nums) {
        quickSortFor977(nums, 0, nums.length - 1);
    }
    private void quickSortFor977(int[] nums, int lo, int hi) {
        if (hi <= lo) return;

        int j = partition(nums, lo, hi);
        exch(nums, lo, j);

        quickSortFor977(nums, lo, j - 1);
        quickSortFor977(nums, j + 1, hi);

    }
    private int partition(int[] nums, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (nums[++i] < nums[lo]) {
                if (i == hi) break;
            }
            while (nums[--j] > nums[lo]) {
                if (j == lo) break;
            }
            if (i >= j) break;
            exch(nums, i, j);
        }
        return j;
    }
    private void exch(int[] nums, int i, int j) {
        int swap = nums[i];
        nums[i] = nums[j];
        nums[j] = swap;
    }


    /****************************************************
     *  Sliding windows in Arrays    July/27/2021
     ****************************************************/
    // [M] LeetCode#209      Minimum Size Subarray Sum
    public int minSubArrayLen(int target, int[] nums) {
        int startIndex = 0, endIndex = 0;
        int minLength = Integer.MAX_VALUE;
        int currLength = 0;
        int sum = 0;


        while (!(endIndex > nums.length - 1 && sum < target)) {
            if (sum < target) {
                sum += nums[endIndex++];
                currLength++;
            } else {
                sum -= nums[startIndex++];
                currLength--;

            }
            if (sum >= target)
                minLength = Math.min(currLength, minLength);

            System.out.println("curr: " + currLength + "  minimum: " + minLength);
        }
        if (minLength < Integer.MAX_VALUE)
            return minLength;
        else return 0;
    }


    // [M] LeetCode#904     Fruit Into Baskets
    public int totalFruit(int[] fruits) {
        if (fruits.length == 1) return 1;

        int cat1StartIndex = 0;
        int cat2StartIndex = -1;
        int cat1 = fruits[0];
        int cat2 = -1;
        int cat1Num = 1;
        int cat2Num = 0;
        int maximumTotal = 1;

        for (int endIndex = 1; endIndex < fruits.length; endIndex++) {
            if (fruits[endIndex] != cat1 && fruits[endIndex] != cat2) {     // new category shows up
                if (cat1StartIndex > cat2StartIndex) {                      // cat 2 should be replaced
                    cat2 = fruits[endIndex];
                    cat2Num = 1;
                    cat2StartIndex = endIndex;
                    cat1Num = endIndex - cat1StartIndex;
                    maximumTotal = Math.max(maximumTotal, cat1Num + cat2Num);
                }
                else {                                                      // cat 1 should be replaced
                    cat1 = fruits[endIndex];
                    cat1Num = 1;
                    cat1StartIndex = endIndex;
                    cat2Num = endIndex - cat2StartIndex;
                    maximumTotal = Math.max(maximumTotal, cat1Num + cat2Num);
                }
            }
            else {          // still old category
                if (fruits[endIndex] == cat1) {         // cat 1
                    cat1Num++;
                    if (cat2StartIndex > cat1StartIndex) cat1StartIndex = endIndex;
                    maximumTotal = Math.max(maximumTotal, cat1Num + cat2Num);
                } else {                                // cat 2
                    cat2Num++;
                    if (cat1StartIndex > cat2StartIndex) cat2StartIndex = endIndex;
                    maximumTotal = Math.max(maximumTotal, cat1Num + cat2Num);
                }
            }
//            System.out.println("cat1:" + cat1 + "  Starter:" + cat1StartIndex +
//                    "  cat2: " + cat2 + "  Starter:" + cat2StartIndex
//                    + "  cat1Num:" + cat1Num + "  cat2Num:" + cat2Num +
//                    "  total:" + (cat1Num + cat2Num) + "  maximum:" + maximumTotal);
        }
        return maximumTotal;
    }


    // [H] LeetCode#76      Minimum Window Substring
    public String minWindow(String s, String t) {
        if (s.length() == 1 && t.length() == 1 && s.charAt(0) == t.charAt(0)) return s;

        BSTFor76<Character> baseline = new BSTFor76<Character>();
        BSTFor76<Character> waitList = new BSTFor76<Character>();
        BSTFor76<Character> window = new BSTFor76<Character>();

        // initiate wait list
        for (int i = 0; i < t.length(); i++) {
            waitList.put(t.charAt(i));
            baseline.put(t.charAt(i));
        }

        int startIndex = -1;
        int miniLength = Integer.MAX_VALUE;
        int miniStartIndex = -1, miniEndIndex = -1;

        for (int endIndex = 0; endIndex < s.length(); endIndex++) {
            char c = s.charAt(endIndex);

            // initiation case
            if (startIndex < 0 && waitList.get(c) == -1) continue;
            if (startIndex < 0 && waitList.get(c) > 0) {
                startIndex = endIndex;
                window.put(c);
                waitList.delete(c);
                if (waitList.size() < 1) return t;
                continue;
            }

            window.put(c);

            if (waitList.get(c) > 0)
                waitList.delete(c);

            if (waitList.size() < 1) {      // trim the window to find the minimum
                int tmpStart = startIndex;
                while (tmpStart <= endIndex && window.get(s.charAt(tmpStart)) > baseline.get(s.charAt(tmpStart)))
                    window.delete(s.charAt(tmpStart++));

                // now we have reached the sub-minimum case
                if (endIndex - tmpStart < miniLength) {
                    miniStartIndex = tmpStart;
                    miniEndIndex = endIndex;
                    miniLength = endIndex - tmpStart;
                }

                window.delete(s.charAt(tmpStart));
                waitList.put(s.charAt(tmpStart));
                tmpStart++;

                while (tmpStart <= endIndex && window.get(s.charAt(tmpStart)) > baseline.get(s.charAt(tmpStart))) {
                    window.delete(s.charAt(tmpStart));
                    tmpStart++;
                }
                startIndex = tmpStart;
            }
        }

        if (miniEndIndex < 0 || miniStartIndex < 0) return "";
        else
            return s.substring(miniStartIndex, miniEndIndex + 1);

    }
    private class BSTFor76 <Key extends Comparable<Key>>{
        private class Node {
            private Node left;
            private Node right;
            private int val;
            private Key key;
            private int count;
            public Node(Key key, int val, int count) {
                this.key = key;
                this.val = val;
                this.count = count;
            }
        }

        private Node root;

        public int size() {
            return size(this.root);
        }
        private int size(Node x) {
            if (x == null) return 0;
            else return x.count;
        }

        // add one element to the BST
        public void put(Key key) {
            this.root = put(root, key);
        }
        private Node put(Node curr, Key key) {
            if (curr == null) return new Node(key, 1, 1);
            else if (key.compareTo(curr.key) < 0)
                curr.left = put(curr.left, key);
            else if (key.compareTo(curr.key) > 0)
                curr.right = put(curr.right, key);
            else
                curr.val++;
            curr.count = 1 + size(curr.left) + size(curr.right);
            return curr;
        }

        // return the key value, -1 if the key is not in the BST
        public int get(Key key) {
            Node x = this.root;
            while (x != null) {
                if (key.compareTo(x.key) < 0)
                    x = x.left;
                else if (key.compareTo(x.key) > 0)
                    x = x.right;
                else
                    return x.val;
            }
            return -1;
        }

        public Node min(Node x) {
            if (x.left == null) return x;
            else return min(x.left);
        }


        public Node deleteMin(Node x) {
            if (x.left == null) return x.right;
            x.left = deleteMin(x.left);
            x.count = 1 + size(x.left) + size(x.right);
            return x;
        }
        // delete a Key, when the val of the key is greater than 1, then only decrease that value but not delete
        public void delete(Key key) {
            this.root = delete(root, key);
        }
        private Node delete(Node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x.left = delete(x.left, key);
            else if (cmp > 0) x.right = delete(x.right, key);
            else {          // find the key
                if (x.val > 1) x.val--;
                else {
                    if (x.left == null) return x.right;
                    if (x.right == null) return x.left;

                    Node t = x;
                    x = min(t.right);
                    x.right = deleteMin(t.right);
                    x.left = t.left;
                }
            }
            x.count = 1 + size(x.left) + size(x.right);
            return x;
        }
    }


    // [M] LeetCode#59      Spiral Matrix II
    public int[][] generateMatrix(int n) {
        if (n == 1)
            return new int[][] {{1}};

        int[][] matrix = new int[n][n];
        int[] borders = {0, n - 1, 0, n -1};
        int mode = 1;
        int count = 1;

        while (borders[1] >= borders[0] && borders[3] >= borders[2]) {
            count = generatingSingleSequence(matrix, mode, count, borders);
            mode++;
            if (mode >= 5)
                mode = 1;
        }

        return matrix;
    }
    // borders = [left, right, top, bottom]
    private int generatingSingleSequence(int[][] matrix, int mode, int count, int[] borders) {
        switch (mode) {
            case 1:
                for (int i = borders[0]; i <= borders[1]; i++)
                    matrix[borders[2]][i] = count++;
                borders[2]++;
                break;
            case 2:
                for (int i = borders[2]; i <= borders[3]; i++)
                    matrix[i][borders[1]] = count++;
                borders[1]--;
                break;
            case 3:
                for (int i = borders[1]; i >= borders[0]; i--)
                    matrix[borders[3]][i] = count++;
                borders[3]--;
                break;
            case  4:
                for (int i = borders[3]; i >= borders[2]; i--)
                    matrix[i][borders[0]] = count++;
                borders[0]++;
        }
        return count;
    }


    // [M] LeetCode#54      Spiral Matrix
    public List<Integer> spiralOrder(int[][] matrix) {
        LinkedList<Integer> result = new LinkedList<>();

        int[] borders = {0, matrix[0].length - 1, 0, matrix.length - 1};
        int mode = 1;

        while (borders[1] >= borders[0] && borders[3] >= borders[2]) {
            spiralOrder(matrix, mode, borders, result);
            mode++;
            if (mode >= 5)
                mode = 1;
        }
        return result;
    }
    private void spiralOrder(int[][] matrix, int mode, int[] borders, List<Integer> result) {
        switch (mode) {
            case 1:
                for (int i = borders[0]; i <= borders[1]; i++)
                    result.add(matrix[borders[2]][i]);
                borders[2]++;
                break;

            case 2:
                for (int i = borders[2]; i <= borders[3]; i++) {
                    result.add(matrix[i][borders[1]]);
                }
                borders[1]--;
                break;

            case 3:
                for (int i = borders[1]; i >= borders[0]; i--)
                    result.add(matrix[borders[3]][i]);
                borders[3]--;
                break;

            case 4:
                for (int i = borders[3]; i >= borders[2]; i--)
                    result.add(matrix[i][borders[0]]);
                borders[0]++;
                break;
        }
    }



    public static void matrixPrint(int[][] matrix) {
        for (int[] row : matrix) {
            for (int element : row)
                System.out.print("  " + element + "  ");
            System.out.println();
        }
    }



    public static void main(String[] args) {

        Chapter2Array chapter2Array = new Chapter2Array();
        String a = "ADOBECODEBANC";
        String b = "ABC";

        String c = "qwerty";
        String d = "ye";

        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};

        List<Integer> result = chapter2Array.spiralOrder(matrix);
        System.out.println(result);

    }
}
