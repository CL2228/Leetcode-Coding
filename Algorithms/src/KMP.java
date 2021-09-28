public class KMP {


    public static int[][] getDFA(String pattern) {

        // suppose the pattern only consists of ASCII
        int R = 128, M = pattern.length();
        int[][] dfa = new int[R][M];
        dfa[pattern.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int i = 0; i < R; i++)
                dfa[i][j] = dfa[i][X];
            dfa[pattern.charAt(j)][j] = j + 1;
            X = dfa[pattern.charAt(j)][X];
        }
        return dfa;
    }



    public static boolean contains(String query, String pattern) {
        // using the above KMP DFA

        int[][] dfa = getDFA(pattern);

        int state = 0;
        for (int i = 0; i < query.length(); i++) {
            state = dfa[query.charAt(i)][state];
            if (state == pattern.length()) return true;
        }
        return false;
    }



    public static void main(String[] args) {
        String q = "qqwwchenghuilioop";
        String p = "chenghuili";
        System.out.println(contains(q, p));
//        int[][] dfa = getDFA(p);
//        for (int[] d : dfa) {
//            for (int i : d) System.out.print(i + " ");
//            System.out.println();
//        }
    }
}
