package prog05;

import java.util.EmptyStackException;
import prog05.StackInt;


/** Class to implement interface StackInt<E> as a linked list.
*   @author vjm
* */

public class LinkedStack<E>
implements StackInt<E> {
	  // Data Fields
	  /** The reference to the top stack node. */
    private Node<E> top = null;
    /** Pushes an item onto the top of the stack and returns the item
    pushed.
    @param obj The object to be inserted.
    @return The object inserted.
 */
    @Override
    public E push(E obj) {
        top = new Node(obj, top);
        return obj;
    }

    @Override
    public E pop() {
        if (empty()) {
            throw new EmptyStackException();
        }
        Object obj = top.data;
        top = top.next;
        return (E)obj;
    }

    @Override
    public E peek() {
        if (empty()) {
            throw new EmptyStackException();
        }
        return (E)top.data;
    }

    @Override
    public boolean empty() {
        if (top == null) {
            return true;
        }
        return false;
    }
    /** A Node is the building block for a single-linked list. */
    private static class Node<E> {
        // Data Fields
        /** The reference to the data. */
    	private E data;
        /** The reference to the next node. */
        private Node next;
        // Constructors
        /** Creates a new node with a null next field.
            @param data The data stored
         */
        private Node(E data) {
            data = data;
            next = null;
        }
        /** Creates a new node that references another node.
        @param data The data stored
        @param next The next node referenced by new node.
     */
        private Node(E data, Node<E> next) {
            data = data;
            next = next;
        }
    }
}
