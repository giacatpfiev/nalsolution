package gc.garcol.nalsolution.cache.collection;

import java.util.Objects;

/**
 * @author thai-van
 **/
public class Node<ID, E> {

    ID id;
    E item;

    Node<ID, E> next;
    Node<ID, E> prev;

    long expiredTime;

    public Node(Node<ID, E> prev, ID id, E element, Node<ID, E> next) {
        this.id = id;
        this.item = element;
        this.next = next;
        this.prev = prev;
        expiredTime = System.currentTimeMillis();
    }

    public ID id() {
        return id;
    }

    public E value() {
        return item;
    }

    public void value(E value) {
        this.item = value;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Node<ID, E> getNext() {
        return next;
    }

    public Node<ID, E> setNext(Node<ID, E> next) {
        this.next = next;
        return this;
    }

    public Node<ID, E> getPrev() {
        return prev;
    }

    public Node<ID, E> setPrev(Node<ID, E> prev) {
        this.prev = prev;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?, ?> node = (Node<?, ?>) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", item=" + item +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
