package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /* Returns true if the map contains key K.
     *  Otherwise return false.**/
    @Override
    public boolean containsKey(K key) {
        if (get(key) == null)
            return false;
        return true;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     *  This method provides one more argument than get method, so it can be used in the get method.
     */
    private V getHelper(K key, Node p) {
        if (p == null)
            return null;
        if (key.compareTo(p.key) < 0) {
            //key < p.key
            return getHelper(key, p.left);
        } else if (key.compareTo(p.key) > 0 ) {
            //key > p.key
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            //The base case of the recursion, and we need some methods to connect the
            //new generated node to the existed tree.
            return new Node(key, value);
        }
        if (key.compareTo(p.key) > 0) {
            // key > p.key Moves to the right branch.
            p.right = putHelper(key, value, p.right);
        } else if (key.compareTo(p.key) < 0) {
            // key < p.key Moves to the left branch.
            p.left = putHelper(key, value, p.left);
        } else {
            // key == p.key
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key,value);
        } else {
            putHelper(key, value, root);
        }
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /** Returns the number of child for a given node n*/
    private int numberOfChild(Node n) {
        if (n == null)
            return 0;
        int children = 0;
        if (n.left != null)
            children += 1;
        if (n.right != null)
            children += 1;
        return children;
    }


    /** The return result is an array with length 2.
     *  array[0]: The structured tree after deleting the item.
     *  array[1]: The deleted item, otherwise set it to null.*/
    private Node[] removeHelper(Node n, K key) {
        Node[] result = (Node[]) new Object[2];
        if (n == null) {
            // The base case of the recursion.
            // The item is not in the tree.
            result[0] = null;
            result[1] = null;
            return result;
        }
        if (n.key.compareTo(key) == 0) {
            // Another base case of the recursion.
            // The item is deleted.
            int kids = numberOfChild(n);
            result[1] = n;
            if (kids == 0) {
                result[0] = null;
                //result[1] = n;
            } else if (kids == 1) {
                if (n.left == null)
                    result[0] = n.right;
                else
                    result[0] = n.left;
                //result[1] = n;
            } else {
                Node replaceNode = findReplaceNode(n.left)[1];
                if (replaceNode != n.left) {
                    replaceNode.left = n.left;
                } else
                    replaceNode.left = null;
                replaceNode.right = n.right;
                result[0] = replaceNode;
                //result[1] = n;
            }
            return result;
        } else if (n.key.compareTo(key) < 0) {
            // n.key < key Move to the right branch.
            result = removeHelper(n.right, key);
            n.right = result[0];
            return result;
        } else {
            result = removeHelper(n.left, key);
            n.left = result[0];
            return result;
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node[] temp = removeHelper(root, key);
        if (root.key.compareTo(key) == 0) {
            root = temp[0];
        }
        size -= 1;
        return temp[1].value;
    }

    /** Find the node to replace the deleted node.
     *  n should start be at the left branch.
     *  array[1]: The node that can be used to replacement.
     *  array[0]: Keep the tree structure.*/
    private Node[] findReplaceNode(Node n) {
        Node[] result = (Node[]) new Object[2];
        if (n.right == null) {
            result[0] = null;
            result[1] = n;
            return result;
        }
        result[0] = n;
        Node[] temp = findReplaceNode(n.right);
        n.right = temp[0];
        result[1] = temp[1];
        return result;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
    }
}