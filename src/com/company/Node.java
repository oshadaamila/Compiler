package com.company;

/**
 * Created by Oshada on 2020-01-06.
 */
public class Node {
    String val;
    Node right;
    Node left;

    public Node(String val, Node right, Node left) {
        this.val = val;
        this.right = right;
        this.left = left;
    }

    public String getVal() {
        return val;
    }

    public Node getRight() {
        return right;
    }

    public Node getLeft() {
        return left;
    }
}
