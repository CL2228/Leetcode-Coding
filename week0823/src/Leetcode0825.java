import java.util.*;

public class Leetcode0825 {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
      }
    }

    // [H] 1172     Dinner Plate Stacks
    private static class DinnerPlates {
        private class stackNode {
            stackNode next;
            stackNode pre;
            Stack<Integer> stackItem;
            public stackNode() {
                stackItem = new Stack<>();
            }
            public stackNode(int item) {
                stackItem = new Stack<>();
                stackItem.push(item);
            }
            public int stackSize() { return stackItem.size(); }
            public void push(int val) { this.stackItem.push(val); }
            public int pop() { return this.stackItem.pop(); }
            public boolean isEmpty() { return stackItem.empty(); }
            public boolean isFull() { return stackItem.size()>= capacity; }
        }

        private final int capacity;
        private stackNode head;
        private stackNode tail;
        private int count;


        public DinnerPlates(int capacity) {
            this.capacity = capacity;
            count = 0;

        }

        public void push(int val) {
            if (head == null) {
                this.head = new stackNode();
                tail = head;
                count++;
            }

            stackNode curr = this.head;
            while (curr.isFull()) {
                if (curr.next == null) {
                    stackNode newTail = new stackNode(val);
                    newTail.pre = curr;
                    curr.next = newTail;
                    this.tail = newTail;
                    count++;
                    return;
                }
                curr = curr.next;
            }
            curr.stackItem.push(val);
        }

        public int pop() {
            if (this.head == null || this.tail == null) return -1;

            stackNode curr = this.tail;
            while (curr.isEmpty()) {
                curr = curr.pre;
                curr.next = null;
                tail = curr;
                count--;
                if (tail == null) {
                    head = tail;
                    return -1;
                }
            }
            int result = curr.pop();
            if (curr == head && curr.isEmpty()) {
                head = tail = null;
                count = 0;
            }
            return result;
        }

        public int popAtStack(int index) {
            if (index >= this.count) return -1;

            stackNode curr = this.head;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            if (curr.isEmpty()) return -1;
            else return curr.pop();
        }

        public void visualization() {
            stackNode curr = this.head;
            while (curr != null) {
                if (curr.isEmpty()) System.out.print("empty");
                else
                    for (int i : curr.stackItem) System.out.print(i + " ");
                System.out.println();
                curr = curr.next;
            }
            System.out.println();
        }
    }


    // [E] 232      Implement Queue using Stacks
    private class MyQueue {
        private Stack<Integer> pushStack;
        private Stack<Integer> popStack;

        /** Initialize your data structure here. */
        public MyQueue() {
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            if (pushStack.isEmpty()) {
                while (!popStack.isEmpty())
                    pushStack.push(popStack.pop());
            }
            pushStack.push(x);
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            if (popStack.isEmpty()) {
                while (!pushStack.isEmpty())
                    popStack.push(pushStack.pop());
            }
            return popStack.pop();
        }

        /** Get the front element. */
        public int peek() {
            if (popStack.isEmpty()) {
                while (!pushStack.isEmpty())
                    popStack.push(pushStack.pop());
            }
            return popStack.peek();

        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return (pushStack.isEmpty() && popStack.isEmpty());
        }
    }


    // [E] 225      Implement Stack using Queues
    private class MyStack {
        private Queue<Integer> data;

        /** Initialize your data structure here. */
        public MyStack() { data = new LinkedList<>(); }

        /** Push element x onto stack. */
        public void push(int x) {
            int size = data.size();
            data.offer(x);
            for (int i = 0; i < size; i++)
                data.offer(data.poll());
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            if (data.size() == 0) return -1;
            else return data.poll();
        }

        /** Get the top element. */
        public int top() {
            if (data.isEmpty()) return -1;
            return data.peek();
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return data.isEmpty();
        }
    }


    // [E] 20       Valid Parentheses
    public boolean isValid(String s) {
        if (s.length() < 2) return false;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++)  {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') stack.push(c);
            else if (stack.isEmpty()) return false;
            else {
                if (c == ')' && stack.pop() != '(') return false;
                if (c == '}' && stack.pop() != '{') return false;
                if (c == ']' && stack.pop() != '[') return false;
            }
        }
        return stack.isEmpty();
    }


    // [E] 1047     Remove All Adjacent Duplicates In String
    public String removeDuplicates1(String s) {
        Stack<Character> data = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (data.isEmpty()) data.push(c);
            else {
                if (data.peek() == c) data.pop();
                else data.push(c);
            }
        }
        char[] result = new char[data.size()];
        int count = 0;
        for (char c : data)
            result[count++] = c;
        return String.valueOf(result);
    }


    // [M] 157      Evaluate Reverse Polish Notation
    public int evalRPN(String[] tokens) {
        Stack<Integer> data = new Stack<>();
        for (String s : tokens) {
            if (s.equals("+")) {
                int a = data.pop();
                int b = data.pop();
                data.push(a + b);
            }
            else if (s.equals("-")) {
                int a = data.pop();
                int b = data.pop();
                data.push(b - a);
            }
            else if (s.equals("*")) {
                int a = data.pop();
                int b = data.pop();
                data.push(a * b);
            }
            else if (s.equals("/")) {
                int a = data.pop();
                int b = data.pop();
                data.push(b / a);
            }
            else data.push(Integer.parseInt(s));
        }
        return data.pop();
    }




    public static void main(String[] args) {
        Leetcode0825 leetcode0825 = new Leetcode0825();
        String[] a = {"10","6","9","3","+","-11","*","/","*","17","+","5","+"};
        System.out.println(leetcode0825.evalRPN(a));

    }

}
