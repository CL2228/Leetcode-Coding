public class LeetCode0828 {


    // [E] 13       Roman to Integer
    public int romanToInt(String s) {
        int sum = 0;
        int idx = 0;
        while (idx < s.length()) {
            int intData = convertToInt(s.charAt(idx));
            if (idx + 1 < s.length()) {
                int intDataPlus = convertToInt(s.charAt(idx + 1));
                if (intDataPlus > intData) {
                    sum += (intDataPlus - intData);
                    idx += 2;
                }
                else {
                    sum += intData;
                    idx++;
                }
            }
            else {
                sum += intData;
                break;
            }
        }
        return sum;
    }
    private int convertToInt(char x) {
        switch (x) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
        }
        return -1;
    }






    public static void main(String[] args) {
        LeetCode0828 leetCode0828 = new LeetCode0828();
        String a = "MCMXCIV";
        System.out.print(leetCode0828.romanToInt(a));
    }
}
