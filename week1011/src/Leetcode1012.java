import java.util.*;

public class Leetcode1012 {


    // [M] 721      Accounts Merge
    int ID = 0;
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<Integer, String> idToName = new HashMap<>();            // map from the ID to the username
        Map<Integer, Set<String>> idToEmails = new HashMap<>();     // map from the ID the accounts belong to that ID
        Map<String, Set<Integer>> nameToIDs = new HashMap<>();      // map from same name but different users to their IDs


        for (List<String> acc : accounts) {
            String currName = acc.get(0);

            if (!nameToIDs.containsKey(currName)) {                                 // this user doesn't exist
                Set<String> currEmails = new HashSet<>();
                for (int i = 1; i < acc.size(); i++) currEmails.add(acc.get(i));    //
                idToName.put(ID, currName);
                idToEmails.put(ID, currEmails);
                nameToIDs.put(currName, new HashSet<>(Arrays.asList(ID)));
                ID++;
            }
            else {
                subMerge(acc, idToName, idToEmails, nameToIDs);
            }
        }


        List<List<String>> res = new LinkedList<>();

        for (int id : idToName.keySet()) {
            List<String> subRes = new LinkedList<>(idToEmails.get(id));
            Collections.sort(subRes);
            subRes.add(0, idToName.get(id));
            res.add(subRes);
        }
        return res;
    }
    private void subMerge(List<String> currAccount, Map<Integer, String> idToName, Map<Integer,
            Set<String>> idToEmails, Map<String, Set<Integer>> nameToIDs) {

        String currName = currAccount.get(0);
        Set<Integer> sameNameIDs = new HashSet<>(nameToIDs.get(currName));
        List<Integer> targetIDs = new ArrayList<>();

        for (int i = 1; i < currAccount.size(); i++) {
            if (sameNameIDs.isEmpty()) break;

            for (int ID : sameNameIDs) {
                if (idToEmails.get(ID).contains(currAccount.get(i))) {
                    sameNameIDs.remove(ID);
                    targetIDs.add(ID);
                    break;
                }
            }
        }


        if (targetIDs.size() == 0) {                // if no account need to merge, just create a new one
            Set<String> emails = new HashSet<>();
            for (int i = 1; i < currAccount.size(); i++) emails.add(currAccount.get(i));

            idToName.put(ID, currName);
            idToEmails.put(ID, emails);
            nameToIDs.get(currName).add(ID);
            ID++;
        }
        else {                                      // 1 or more accounts need to be merged

            Set<String> targetEmails = idToEmails.get(targetIDs.get(0));
            for (int i = 1; i < targetIDs.size(); i++) {
                int currTgID = targetIDs.get(i);

                targetEmails.addAll(idToEmails.get(currTgID));
                idToName.remove(currTgID);
                idToEmails.remove(currTgID);
                nameToIDs.get(currName).remove(currTgID);
            }

            for (int i = 1; i < currAccount.size(); i++)
                targetEmails.add(currAccount.get(i));
        }
    }


    // [H] 363      Max Sum of Rectangle No larger Than K
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int M = matrix.length, N = matrix[0].length;
        int[][] prefixSum = new int[M][N];
        prefixSum[0][0] = matrix[0][0];

        int maxRes;
        if (prefixSum[0][0] <= k) maxRes = prefixSum[0][0];
        else maxRes = Integer.MIN_VALUE;

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(0);
        for (int c = 1; c < N; c++) {
            prefixSum[0][c] = matrix[0][c] + prefixSum[0][c - 1];
            int subRes = getSubRe(prefixSum[0][c], pq, k);
            maxRes = Math.max(maxRes, subRes);
            System.out.println("r:" + 0 + " c:" + c + "  subRe:" + subRes + "  prefixSum:" + prefixSum[0][c]);
            System.out.println(pq);
            System.out.println();
//            if (maxRes == k) {
//                System.out.println("r:" + 0 + " c:" + c);
//                return k;
//            }
            pq.add(prefixSum[0][c]);
        }
        for (int r = 1; r < M; r++) {
            prefixSum[r][0] = matrix[r][0] + prefixSum[r - 1][0];
            int subRes = getSubRe(prefixSum[r][0], pq, k);
            maxRes = Math.max(maxRes, subRes);
            System.out.println("r:" + r + " c:" + 0 + "  subRe:" + subRes + "  prefixSum:" + prefixSum[r][0]);
            System.out.println(pq);
            System.out.println();
//            if (maxRes == k) {
//                System.out.println("r:" + r + " c:" + 0);
//                return k;
//            }
            pq.add(prefixSum[r][0]);
        }

        for (int r = 1; r < M; r++) {
            for (int c = 1; c < N; c++) {
                prefixSum[r][c] = matrix[r][c] + prefixSum[r - 1][c] + prefixSum[r][c - 1] - prefixSum[r - 1][c - 1];
                int subRes = getSubRe(prefixSum[r][c], pq, k);
                maxRes = Math.max(maxRes, subRes);
                System.out.println("r:" + r + " c:" + c + "  subRe:" + subRes + "  prefixSum:" + prefixSum[r][c]);
                System.out.println(pq);
                System.out.println();
//                if (maxRes == k) {
//                    System.out.println("r:" + r + " c:" + c);
//                    return k;
//                }
                pq.add(prefixSum[r][c]);
            }
        }

        return maxRes;
    }
    private int getSubRe(int currSum, PriorityQueue<Integer> pq, int k) {
        int lowerBound = currSum - k;
        int subRes = Integer.MIN_VALUE;
        List<Integer> needAdded = new LinkedList<>();

        while (!pq.isEmpty() && pq.peek() < lowerBound) needAdded.add(pq.remove());

        if (!pq.isEmpty()) subRes = currSum - pq.peek();
        for (int i : needAdded) pq.add(i);
        return subRes;
    }



    public static void main(String[] args) {
        Leetcode1012 lc = new Leetcode1012();
        int[][] a = {{5,-4,-3,4},{-3,-4,4,5},{5,1,5,-4}};
        System.out.println(lc.maxSumSubmatrix(a, 3));
    }
}
