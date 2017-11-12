import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {

        final T value;
        Node<T> left = null;
        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;
    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }

        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison > 0) {
            assert closest.right == null;
            closest.right = newNode;
        } else {
            assert closest.left == null;
            closest.left = newNode;
        }
        size++;
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        Node<T> parent = root;
        Node<T> elem;

        @SuppressWarnings("unchecked")
        T t = (T) o;

        if (root == null) throw new NoSuchElementException();
        if (parent.value == t) {

            //three variants for deleting a vertex
            if (parent.left == null && parent.right == null) root = null;
            if (parent.left != null && parent.right == null) root = parent.left;
            if (parent.left == null && parent.right != null) root = parent.right;
            else {

                //if vertex have two subtree
                Node<T> target = null;
                if ((parent.right != null ? parent.right.left : null) != null) {
                    target = parent.right;
                    while (target.left.left != null) target = target.left;
                }
                if (target != null) {
                    Node<T> min = target.left;
                    target.left = min.right;
                    min.right = parent.right;
                    min.left = parent.left;
                    root = min;
                } else {
                    if (parent.right != null) {
                        parent.right.left = parent.left;
                    }
                    root = parent.right;
                }
            }
        } else {

            //find root with the removed node
            while ((parent.value.compareTo(t) > 0 && parent.left.value != t) ||
                    (parent.value.compareTo(t) < 0 && parent.right.value != t)) {

                if (parent.value.compareTo(t) < 0 && parent.right != o)
                    parent = parent.right;

                else if (parent.value.compareTo(t) > 0 && parent.left != o)
                    parent = parent.left;

                else throw new NoSuchElementException();
            }

            //tree situation to refer to deleted nodes
            if (parent.left != null && parent.left.value == t)
                elem = parent.left;

            else if (parent.right != null && parent.right.value == t)
                elem = parent.right;

            else throw new NoSuchElementException();

            if (elem.left == null && elem.right == null) { //if no child nodes
                if (parent.left == elem) parent.left = null;
                else if (parent.right == elem) parent.right = null;
            }

            //if one node child
            if ((elem.left != null && elem.right == null) ||
                    (elem.left == null && elem.right != null)) {

                if (parent.left == elem && elem.left != null)
                    parent.left = elem.left;

                else if (parent.right == elem && elem.right != null)
                    parent.right = elem.right;
            }

            //if two node child
            if (elem.left != null && elem.right != null) {
                Node<T> target = null;
                if (elem.right.left != null) {
                    target = elem.right;
                    while (target.left.left != null) target = target.left;
                }
                if (target != null) {
                    Node<T> min = target.left;
                    target.left = min.right;
                    min.left = elem.left;
                    min.right = elem.right;
                    if (parent.left == elem) parent.left = min;

                    else if (parent.right == elem) parent.right = min;
                }
                else {
                    elem.right.left = elem.left;
                    if (parent.left == elem) parent.left = elem.right;
                    else if (parent.right == elem) parent.right = elem.right;
                }
            }
        }
        size--;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        BinaryTreeIterator() {
        }

        private Node<T> findNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}