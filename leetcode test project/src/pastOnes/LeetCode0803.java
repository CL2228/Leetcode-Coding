package pastOnes;

import java.util.*;

public class LeetCode0803 {

    // [E] LeetCode#242     Valid Anagram
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int mode = 6;
        hashTable242 tableS = new hashTable242(mode);
        hashTable242 tableT = new hashTable242(mode);

        for (int i = 0; i < s.length(); i++) {
            tableS.put(s.charAt(i));
            tableT.put(t.charAt(i));
        }
//        tableS.visualization();
//        tableT.visualization();

        for (int i = 0; i < mode; i++) {
            hashTable242.Node headS = tableS.getHeadNode(i);
            hashTable242.Node headT = tableT.getHeadNode(i);


            if ((headS == null && headT != null) || (headT == null && headS != null)) return false;

            while (headS != null && headT !=null) {
                if (headS.count != tableT.inquiry(headS.key)) return false;
                if (headT.count != tableS.inquiry(headT.key)) return false;
                headS = headS.next;
                headT = headT.next;
                if ((headS == null && headT != null) || (headS != null && headT == null)) return false;
            }
        }

        return true;
    }
    public static class hashTable242 {
        private int mode;
        private Node[] st;

        private static class Node {
            char key;
            int count;
            Node pre;
            Node next;
            public Node(char key) {
                this.key = key;
                this.count = 1;
            }
        }

        public Node[] getSt() {
            return this.st;
        }

        public Node getHeadNode(int index) {
            return st[index];
        }

        public hashTable242(int mode) {
            this.mode = mode;
            this.st = (Node[]) new Node[mode];
        }

        public int hash(char c) {
            return c % mode;
        }

        public void put(char key) {
            int hashIndex = hash(key);
            if (st[hashIndex] == null)
                st[hashIndex] = new Node(key);
            else {
                Node current = st[hashIndex];
                while (current != null) {
                    if (current.key == key) {
                        current.count++;
                        return;
                    }
                    current = current.next;
                }

                // now the current is null
                Node newKey = new Node(key);
                if (st[hashIndex].next != null) {
                    st[hashIndex].pre.next = newKey;
                    newKey.pre = st[hashIndex].pre;
                }
                else {
                    st[hashIndex].next = newKey;
                    newKey.pre = st[hashIndex];
                }
                st[hashIndex].pre = newKey;
            }
        }

        public int inquiry(char key) {
            int hashIndex = hash(key);
            if (st[hashIndex] == null) return 0;
            else {
                Node current = st[hashIndex];
                while (current != null) {
                    if (current.key == key) return current.count;
                    current = current.next;
                }
            }
            return 0;
        }

        public void visualization() {
            for (int i = 0; i < mode; i++) {
                Node current = st[i];
                if (current == null) {
                    System.out.println("null");
                    continue;
                }
                while (current != null) {
                    System.out.print(current.key + ":" + current.count + "  ");
                    current = current.next;
                }
                System.out.println();
            }
            System.out.println();
        }

    }


    // [M] LeetCode#49      Group Anagrams
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();

        hashTable49 table = new hashTable49(31);
        for (String i : strs)
            table.add(i);


        return table.getResult();
    }
    private static class hashTable49 {
        private class Node49 {
            private List<String> llstring;
            private Node49 next;
            private Node49 pre;
            private int hash;
            public Node49(int hash, String str) {
                this.llstring = new LinkedList<String>();
                this.hash = hash;
                llstring.add(str);
            }

        }
        private Node49[] sc;
        private int mode;


        public hashTable49(int mode) {
            this.mode = mode;
            sc = new Node49[mode];
        }

        public int hashOfString(String str) {
            if (str.equals("")) return 0;

            int[] count = new int[26];
            for (int i = 0; i < str.length(); i++)
                count[str.charAt(i) - 'a']++;
            int hash = 0;
            for (int i = 0; i < 26; i++)
                hash = (i + 1) * count[i] + 19 * (hash % 11);

            return hash;
        }

        public void add(String str) {
            int hash = hashOfString(str);
            int hashMode = hash % mode;

            if (sc[hashMode] == null) {
                sc[hashMode] = new Node49(hash, str);
                return;
            }

            Node49 current = sc[hashMode];
            while (current != null) {
                if (current.hash == hash) {
                    current.llstring.add(str);
                    return;
                }
                current = current.next;
            }

            Node49 newNode = new Node49(hash, str);
            if (sc[hashMode].next == null) {
                sc[hashMode].next = newNode;
                sc[hashMode].pre = newNode;
                newNode.pre = sc[hashMode];
            }
            else {
                newNode.pre = sc[hashMode].pre;
                sc[hashMode].pre.next = newNode;
                sc[hashMode].pre = newNode;
            }
        }

        public LinkedList<List<String>> getResult() {
            LinkedList<List<String>> results = new LinkedList<List<String>>();

            for (int i = 0; i < mode; i++) {
                Node49 current = sc[i];
                while (current != null) {
                    results.add(current.llstring);
                    current = current.next;
                }
            }
            return results;
        }
    }
    /*
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();
        Map<String, List<String >> map = new HashMap<>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String keyStr = String.valueOf(ca);
            if (!map.containsKey(keyStr)) map.put(keyStr, new ArrayList<>());
            map.get(keyStr).add(s);
        }
        return new ArrayList<>(map.values());
    }
     */





    public static void main(String[] args) {
        LeetCode0803 leetCode0803 = new LeetCode0803();

        String[] strs = {"eat","tea","tan","ate","nat","bat"};
        String aaa = "serenere";

        System.out.println(String.valueOf(aaa));
    }
}
