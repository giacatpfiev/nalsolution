package gc.garcol.nalsolution.cache.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Using this class instead up using {@link LinkedList} because:
 * <br> * {@link LinkedList#unlink(LinkedList.Node)} is not public.
 * <br> * {@link LinkedList.Node} is not public.
 * <br> * {@link LinkedList#remove(Object)} is O(n) time complexity.
 * <br> All most of code is based on {@link LinkedList}
 *
 * @author thai-van
 **/
public class CacheLinkedList<ID, E> {

    protected Node<ID, E> first;
    protected Node<ID, E> last;

    protected int size;

    public CacheLinkedList() {
    }

    /**
     * Inserts the specified element at the beginning of this list.
     *
     * @param e the element to add
     * @return Node containing added element
     */
    public Node<ID, E> addFirst(ID id, E e) {
        return linkFirst(id,e);
    }

    /**
     * Inserts the specified node at the beginning of this list.
     *
     * @param e the element to add
     */
    public void addFirst(Node<ID, E> e) {
        final Node<ID, E> f = first;
        e.prev = null;
        e.next = f;

        first = e;
        if (f == null)
            last = e;
        else
            f.prev = e;
        size++;
    }

    /**
     * Returns the first node in this list.
     *
     * @return the first node in this list
     */
    public Node<ID,E> getFirst() {
        return first;
    }

    /**
     * Appends the specified element to the end of this list.
     */
    public Node<ID, E> addLast(ID id, E e) {
        return linkLast(id, e);
    }

    /**
     *
     * @param e
     */
    public void addLast(Node<ID, E> e) {
        final Node<ID, E> l = last;
        e.prev = l;
        e.next = null;

        last = e;
        if (l == null) {
            first = e;
        } else {
            l.next = e;
        }
        size++;
    }

    /**
     * Returns the last node in this list.
     *
     * @return the last node in this list
     */
    public Node<ID, E> getLast() {
        return last;
    }

    /**
     * Removes a specific node in linkedList.
     * @return the element of removed node
     */
    public E remove(Node<ID, E> node) {
        return unlink(node);
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last node from this list
     * @throws NoSuchElementException if this list is empty
     */
    public Node<ID, E> removeLast() {
        final Node<ID, E> l = last;
        if (l == null)
            throw new NoSuchElementException();
        unlinkLast(l);
        return l;
    }

    /**
     * Unlinks non-null last node l.
     */
    private E unlinkLast(Node<ID, E> l) {
        // assert l == last && l != null;
        final E element = l.item;
        final Node<ID, E> prev = l.prev;
//        l.item = null; //@since: still using node
        l.prev = null; // help GC
        last = prev;
        if (prev == null)
            first = null;
        else
            prev.next = null;
        size--;
        return element;
    }

    private Node<ID, E> linkFirst(ID id, E e) {
        final Node<ID, E> f = first;
        final Node<ID, E> newNode = new Node(null, id, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
        return newNode;
    }

    private Node<ID, E> linkLast(ID id, E e) {
        final Node<ID, E> l = last;
        final Node<ID, E> newNode = new Node(l, id, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;

        return newNode;
    }

    private E unlink(Node<ID, E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<ID, E> next = x.next;
        final Node<ID, E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        // todo help GC
        // ... see LinkedList code

        size--;
        return element;
    }

    public int getSize() {
        return size;
    }

    public CacheLinkedList<ID, E> setSize(int size) {
        this.size = size;
        return this;
    }

    public List<E> toList() {
        List<E> list = new ArrayList<>(size);
        Node<ID, E> iter = first;
        while (iter != null) {
            list.add(iter.value());
            iter = iter.getNext();
        }

        return list;
    }
}
