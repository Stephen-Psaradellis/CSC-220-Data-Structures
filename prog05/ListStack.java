package prog05;

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/** Implementation of the interface StackInt<E> using a List.
*   @author vjm
*/

public class ListStack<E> implements StackInt<E> {
    List<E> theData = new ArrayList();

    @Override
    public E push(E obj) {
        theData.add(obj);
        return obj;
    }

    @Override
    public E pop() {
        if (empty()) {
            throw new EmptyStackException();
        }
        return theData.remove(theData.size() - 1);
    }

    @Override
    public E peek() {
        if (empty()) {
            throw new EmptyStackException();
        }
        return theData.get(theData.size() - 1);
    }

    @Override
    public boolean empty() {
        if (theData.size() == 0) {
            return true;
        }
        return false;
    }
}
