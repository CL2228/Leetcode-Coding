
package pastOnes;
import java.util.Arrays;
import java.util.Stack;

public class LeetCode0817 {

    // [E] LeetCode#577     Reverse Words in a String III
    public String reverseWords1(String s) {
        if (s.length() == 1) return s;

        char[] data = s.toCharArray();

        int startIndex = 0, endIndex = 0;
        while (endIndex < s.length()) {
            while (endIndex < s.length() && data[endIndex] != ' ') endIndex++;
            reverseChar(data, startIndex, endIndex - 1);
            endIndex++;
            startIndex = endIndex;
        }
        return String.valueOf(data);
    }
    private void reverseChar(char[] data, int lo, int hi) {
        int i = lo, j = hi;
        while (i < j) {
            char swap = data[i];
            data[i] = data[j];
            data[j] = swap;
            i++;
            j--;
        }
    }


    // [M] LeetCode#306     Additive Number
    public boolean isAdditiveNumber(String num) {
        if (num.length() < 3) return false;

        Stack<int[]> stage1 = getFirstAdd(num);
        if (stage1.empty()) return false;
        for (int[] i : stage1)
            System.out.println(i[0] + " " + i[1] + " " + i[2]);

        for (int[] indexes :  stage1) {
            if (indexes[2] == num.length()) return true;
            while (indexes[0] != -1) {
                indexes = updateAdd(num, indexes);
                if (indexes[2] ==num.length()) return true;
            }
        }
        return false;
    }
    private int[] updateAdd(String num, int[] indexes) {

        int[] result = new int[3];
        if (indexes[2] < num.length() - 1 && num.charAt(indexes[2]) == '0' && num.charAt(indexes[2] + 1) != '0') {
            result[0] = -1;
            return result;
        }

        int l1 = indexes[0], l2 = indexes[1], l3 = indexes[2], l4 = l3 + 1;
        int num1 = Integer.parseInt(num.substring(l1, l2));
        int num2 = Integer.parseInt(num.substring(l2, l3));
        while (l4 <= num.length()) {
            int num3 = Integer.parseInt(num.substring(l3, l4));



            if (num1 + num2 == num3) {
                result[0] = l2;
                result[1] = l3;
                result[2] = l4;
                return result;
            }
            else if (num1 + num2 > num3) l4++;
            else break;
        }
        result[0] = -1;
        return result;
    }
    private int[] getFirstAdd(String num, int l2) {
        int[] result = new int[3];

//        if (l2 != 1 && num.charAt(l2) == '0') {
//            result[0] = -1;
//            return result;
//        }

        int l3 = l2 + 1, l4 = l3 + 1;
        while (num.charAt(l2) != '0' && num.charAt(l3) == '0') {
            l3++;
            l4++;
            if (l4 > num.length()) {
                result[0] = -1;
                return result;
            }
        }

        while (l4 <= num.length()) {
            int num1 = Integer.parseInt(num.substring(0, l2));
            int num2 = Integer.parseInt(num.substring(l2, l3));
            int num3 = Integer.parseInt(num.substring(l3, l4));
            System.out.println("l2:" + l2 + " l3:" + l3 + " l4:" + l4);
            System.out.println("num1:" + num1 + " num2:" + num2 + " num3:" + num3);
            System.out.println();
            if (num1 + num2 == num3) {
                result[0] = l2;
                result[1] = l3;
                result[2] = l4;
                return result;
            }
            else if (num1 + num2 > num3) l4++;
            else {
                if (num.charAt(l2) == '0') {
                    result[0] = -1;
                    return result;
                } else {
                    l3++;
                    if (l3 >= l4) l4++;
                    if (l3 >= num.length()) break;
                    while (num.charAt(l3) == '0') {
                        l3++;
                        if (l3 >= l4) l4++;
                    }
                }
            }
        }

        result[0] = -1;
        return result;
    }
    private Stack<int[]> getFirstAdd(String num) {
        Stack<int[]> result = new Stack<>();
        int l2Destination = num.charAt(0) == '0' ? 1 : num.length() - 2;
        l2Destination = Math.min(l2Destination, 9);
        for (int l2 = 1; l2 <= l2Destination; l2++) {
            int[] subRe = getFirstAdd(num, l2);
            if (subRe[0] != -1)
                result.add(subRe);
        }
        return result;
    }
    private int[] updateAdd(String num) {
        Stack<int[]> indexData = new Stack<int[]>();


        int[] result = new int[3];
        int l2, l3, l4;
        int l2Destination = num.charAt(0) == '0' ? 1 : num.length() - 2;

        for (l2 = 1; l2 <= l2Destination; l2++) {
            if (l2 != 1 && num.charAt(l2) == '0') continue;
            l3 = l2 + 1;
            l4 = l3 + 1;
            while (num.charAt(l2) != '0' && num.charAt(l3) == '0') {
                l3++;
                l4++;
                if (l4 > num.length()) {
                    result[0] = -1;
                    return result;
                }
            }
//            System.out.println("mark:" + l2 + "  " + l3 + "  " + l4);


            while (l4 <= num.length()) {
                //System.out.println(l2 + "  " + l3 + "  " + l4);
                int num1 = Integer.parseInt(num.substring(0, l2));
                int num2 = Integer.parseInt(num.substring(l2, l3));
                int num3 = Integer.parseInt(num.substring(l3, l4));

                if (num1 + num2 == num3) {
//                    System.out.println("numValue:" + num1 + " " + num2 + " " + num3);
//                    System.out.println(l2 + "  " +  l3 + "  " + l4);
                    result[0] = l2;
                    result[1] = l3;
                    result[2] = l4;
                    return result;
                }
                else if (num1 + num2 > num3) l4++;
                else {
                    if (num.charAt(l2) == '0') {
                        result[0] = -1;
                        return result;
                    }
                    else {
                        l3++;
                        if (l3 >= l4) l4++;
                        if (l3 >= num.length()) break;
                        while (num.charAt(l3) == '0') {
                            l3++;
                            if (l3 >= l4) l4++;
                        }
                    }
                }
            }
        }
        result[0] = -1;
        return result;
    }


    // [E] 剑指offer05        替换空格
    public String replaceSpace(String s) {
        char[] data = s.toCharArray();
        int count = 0;
        for (char datum : data) {
            if (datum == ' ') count++;
        }
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


    // [M] LeetCode#151     Reverse Words in a String
    public String reverseWords(String s) {
        Stack<String> dataStr = new Stack<>();
        int startIndex = 0;
        int endIndex = 0;
        while (endIndex < s.length()) {
            while (startIndex < s.length() && s.charAt(startIndex) == ' ')
                startIndex++;
            if (startIndex >= s.length()) break;
            endIndex = startIndex;
            while (endIndex < s.length() && s.charAt(endIndex) != ' ')
                endIndex++;
            dataStr.add(s.substring(startIndex, endIndex));
            startIndex = endIndex;
        }

        StringBuilder result = new StringBuilder();
        result.append(dataStr.pop());
        while (!dataStr.empty()) {
            result.append(" ");
            result.append(dataStr.pop());
        }
        return result.toString();
    }







    public static void main(String[] args) {
        LeetCode0817 leetCode0817 = new LeetCode0817();
        String a = "Alice does not even like bob";
        String b = "zzx";
        String c = b + a;
        System.out.println(a);
        System.out.println(leetCode0817.reverseWords(a));
    }
}
