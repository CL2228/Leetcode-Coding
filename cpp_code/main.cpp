#include <iostream>
#include <vector>
#include <map>
#include <set>
#include <queue>
#include <stack>
#include <deque>


using namespace std;

class Solution {
    struct TreeNode {
        int val;
        TreeNode *left;
        TreeNode *right;
        TreeNode() : val(0), left(nullptr), right(nullptr) {}
        TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
        TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
    };
private:

public:
    int evalRPN(vector<string>& tokens) {
        stack<int> stack;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens[i] == "+" || tokens[i] == "-" || tokens[i] == "*" || tokens[i] == "/") {
                int b = stack.top();
                stack.pop();
                int a = stack.top();
                stack.pop();
                if (tokens[i] == "+") stack.push(a + b);
                if (tokens[i] == "-") stack.push(a - b);
                if (tokens[i] == "*") stack.push(a * b);
                if (tokens[i] == "/") stack.push(a / b);


            } else stack.push(stoi(tokens[i]));
        }
        return stack.top();
    }


};

int main() {
    Solution sl;
    vector<string> a = {"4", "13", "5", "/", "+"};
    std::cout << sl.evalRPN(a) << std::endl;
    std::cout << "Hello, World!" << std::endl;
    return 0;
}
