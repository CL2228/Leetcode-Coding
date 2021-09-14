package Algorithms_review;

import edu.princeton.cs.algs4.Queue;

public class BST <Key extends Comparable<Key>, Value>{

    private class Node {
        private Node left;
        private Node right;
        private Value val;
        private Key key;
        private int count;
        public Node(Key key, Value val, int count) {
            this.key = key;
            this.val = val;
            this.count = count;
        }
    }

    private Node root;

    public void put(Key key, Value val) {
        this.root = put(this.root, key, val);
    }
    private Node put(Node curr, Key key, Value val) {
        if (curr == null) return new Node(key, val, 1);
        else if (key.compareTo(curr.key) < 0)
            curr.left = put(curr.left, key, val);
        else if (key.compareTo(curr.key) > 0)
            curr.right = put(curr.right, key, val);
        else
            curr.val = val;
        curr.count = 1 + size(curr.left) + size(curr.right);
        return curr;
    }


    // find the minimum and maximum node of a tree(sub-tree)
    public Node min(Node x) {
       if (x.left == null) return x;
       else return min(x.left);
    }
    public Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    // find the floor Node of the given key(the largest Node that <= Key)
    public Node floor(Key key) {
        return floor(this.root, key);
    }
    private Node floor(Node x, Key key) {
        if (x == null) return null;
        if (x.key.compareTo(key) == 0) return x;

        if (x.key.compareTo(key) > 0) return floor(x.left, key);
        else {
            Node result = floor(x.right, key);
            if (result == null) return x;
            else return result;
        }
    }

    // find the ceiling node of the given key (the smallest node >= key)
    public Node ceiling(Key key) {
        return ceiling(this.root, key);
    }
    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        if (x.key.compareTo(key) == 0) return x;
        if (x.key.compareTo(key) < 0) return ceiling(x.right, key);
        else {
            Node result = ceiling(x.left, key);
            if (result == null) return x;
            else return result;
        }
    }

    // get the size of the whole tree
    public int size() {
        return size(this.root);
    }
    private int size(Node x) {
        if (x == null) return 0;
        else return x.count;
    }

    // get the rank of a Key k,  (how many keys < k)
    public int rank(Key key) {
        return rank(this.root, key);
    }
    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = x.key.compareTo(key);
        if (cmp == 0) return size(x.left);
        else if (cmp > 0) return rank(x.left, key);
        else return 1 + size(x.left) + rank(x.right, key);

    }


    // return all keys in-order
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        inorder(this.root, queue);
        return queue;
    }
    private void inorder(Node x, Queue<Key> queue) {
        if (x == null) return;
        inorder(x.left, queue);
        queue.enqueue(x.key);
        inorder(x.right, queue);
    }





    public void delete(Key key) {
        this.root = delete(root, key);
    }
    public void deleteMin() {
        this.root = deleteMin(root);
    }
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {          // the exact node to delete
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }


    public Value get(Key key) {
        Node x = this.root;
        while (x != null) {
            if (key.compareTo(x.key) < 0)
                x = x.left;
            else if (key.compareTo(x.key) > 0)
                x = x.right;
            else
                return x.val;
        }
        return null;
    }
}
