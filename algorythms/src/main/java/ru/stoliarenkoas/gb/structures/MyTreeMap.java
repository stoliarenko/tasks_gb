package ru.stoliarenkoas.gb.structures;

import ru.stoliarenkoas.gb.etc.Pow;

import java.util.Random;

public class MyTreeMap<K extends Comparable<K>, V> {

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private int size;
        private int depth;

        Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
            updateDepth();
        }

        private void updateDepth() {
            depth = 1;
            if (left != null) depth += left.depth;
            if (right != null) {
                int newDepth = 1 + right.depth;
                if (newDepth > depth) depth = newDepth;
            }
        }
    }

    private Node root = null;

    public boolean isEmpty() {
        return root == null;
    }

    public int getSize() {
        if (isEmpty()) return 0;
        return root.size;
    }

    public int getDepth() {
        if (isEmpty()) return 0;
        return root.depth;
    }

    public V get(K key) {
        Node result = get(root, key);
        if (result == null) return null;
        return result.value;
    }

    private Node get(Node current, K key) {
        if (current == null) return null;
        if (key == null) throw new NullPointerException("Null key");

        int difference = key.compareTo(current.key);
        if (difference == 0) return current;
        if (difference < 0) return get(current.left, key);
        return get(current.right, key);
    }

    public V put(K key, V value) {
        if (key == null || value == null) throw new NullPointerException("Null key or value");

        if (root == null) {
            root = new Node(key, value, 1);
            return null;
        }
        return put(root, key, value);
    }

    private V put(Node current, K key, V value) {

        V result = null;
        int difference = key.compareTo(current.key);
        if (difference == 0) {
            V oldValue = current.value;
            current.value = value;
            return oldValue;
        }
        if (difference < 0) {
            if (current.left == null) current.left = new Node(key, value, 1);
            else result = put(current.left, key, value);
        }
        if (difference > 0) {
            if (current.right == null) current.right = new Node(key, value, 1);
            else result = put(current.right, key, value);
        }
        if (result == null) current.size++;
        current.updateDepth();
        return result;

    }

    private Node removeMinNode(Node current) {
        if (current.left == null) return current;
        Node result = removeMinNode(current.left);
        if (result == current.left) current.left = current.left.right;
        current.size--;
        current.updateDepth();
        return result;
    }

    private Node removeMaxNode(Node current) {
        if (current.right == null) return current;
        Node result = removeMaxNode(current.right);
        if (result == current.right) current.right = current.right.left;
        current.size--;
        current.updateDepth();
        return result;
    }

    public V remove(K key) {
        Node result = remove(root, key);
        if (result == null) return null;
        return result.value;
    }

    private Node remove(Node current, K key) {
        if (current == null) return null;
        if (key == null) throw new NullPointerException("Null key");

        Node result = null;
        int difference = key.compareTo(current.key);
        if (difference == 0 && current != root) return current;
        if (difference < 0) result = remove(current.left, key);
        if (difference > 0) result = remove(current.right, key);
        if (current == root && difference == 0) result = root;

        if (result == null) return null;

        if (result == current.left || result == current.right || result == root) {
            Node replacement = null;
            if (result == current.left) {
                if (result.right == null) current.left = result.left;
                else replacement = removeMaxNode(result);
            }
            if (result == current.right) {
                if (result.left == null) current.right = result.right;
                else replacement = removeMinNode(result);
            }
            if (result == root) {
                if (root.left == null) root = root.right;
                else replacement = removeMaxNode(root.left);
            }
            if (replacement != null) {
                replacement.left = result.left;
                replacement.right = result.right;
                replacement.size = result.size;
            }
        }

        current.size--;
        current.updateDepth();
        return result;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node current) {
        if (current == null) return true;
        if (!isBalanced(current.left) || !isBalanced(current.right)) return false;
        int left = 0, right = 0;
        if (current.left != null) left = current.left.size;
        if (current.right != null) right = current.right.size;
        return Pow.pow(left-right, 2) < 2;
    }

    public boolean isBalancedByDepth() {
        return isBalancedByDepth(root);
    }

    private boolean isBalancedByDepth(Node current) {
        if(current == null) return true;
        if (!isBalancedByDepth(current.left) || !isBalancedByDepth(current.right)) return false;
        int left = 0, right = 0;
        if (current.left != null) left = current.left.depth;
        if (current.right != null) right = current.right.depth;
        return Pow.pow(left-right, 2) < 2;
    }

    public boolean isBalancedByDepthChecked() {
        return isBalancedByDepthChecked(root) != -1;
    }

    private int isBalancedByDepthChecked(Node current) {
        if (current == null) return 0;
        int left = isBalancedByDepthChecked(current.left);
        int right = isBalancedByDepthChecked(current.right);
        if (left == -1 || right == -1) return -1;
        if (Pow.pow(left-right, 2) > 1) return -1;
        return Math.max(left, right) + 1;
    }

    public static void printBalancedPercent() {
        printBalancedPercent(1000000, 6, false);
    }

    public static void printBalancedPercent(int numberOfTrees, int depthOfTree, boolean doubleCheckDepth) {
        final long startTime = System.currentTimeMillis();
        int balancedCounter = 0;
        Random random = new Random();
        for (int i = 0; i < numberOfTrees; i++) {
            MyTreeMap<Integer, Integer> map = new MyTreeMap<Integer, Integer>();
            Integer number = 300;
            while (map.getDepth() < depthOfTree + 1) {
                number = random.nextInt( 201) - 100;
                map.put(number, number);
            }
            map.remove(number);
            if (doubleCheckDepth) {
                if (map.isBalancedByDepth()) balancedCounter++;
            } else if (map.isBalancedByDepthChecked()) balancedCounter++;
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + ((endTime-startTime)/1000) + "s.");

        double balancedPercent = (double) balancedCounter / numberOfTrees * 100;

        System.out.printf("Balanced percent in %d maps is %.2f%%(%d total).%n", numberOfTrees, balancedPercent, balancedCounter);
    }

}
