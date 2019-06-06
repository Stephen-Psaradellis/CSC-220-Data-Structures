package prog06;

import java.util.*;

/**
 * Implements the Queue interface using a circular array.
 **/
public class ArrayQueue<E> extends AbstractQueue<E>
  implements Queue<E> {

  // Data Fields
  /** Index of the first item in the queue. */
  private int first;
  /** Index of the last item in the queue. */
  private int last;
  /** Current size of the queue. */
  private int size;
  /** Default capacity of the queue. */
  private static final int DEFAULT_CAPACITY = 10;
  /** Array to hold the items. */
  private E[] theItems;

  // Constructors
  /**
   * Construct a queue with the default initial capacity.
   */
  public ArrayQueue () {
    this(DEFAULT_CAPACITY);
  }

  /**
   * Construct a queue with the specified initial capacity.
   * @param initCapacity The initial capacity
   */
  @SuppressWarnings("unchecked")
  public ArrayQueue (int initCapacity) {
    theItems = (E[]) new Object[initCapacity];
    first = 0;
    last = theItems.length - 1;
    size = 0;
  }

  // Public Methods
  /**
   * Inserts an item at the last item in the queue.
   * @post item is added to the last item in the queue.
   * @param item The element to add
   * @return true (always successful)
   */
  @Override
  public boolean offer (E item) {
	  if (size == theItems.length)
		  reallocate();
    // Move last forward one, but if it goes past the end of the
    // array, go last to zero.
    last = (last + 1) % theItems.length;
    // Store the new item at last.
    theItems[last] = item;
    size++;
    return true;
  }

  /**
   * Returns the item at the first item in the queue without removing it.
   * @return The item at the first item in the queue if successful;
   * return null if the queue is empty
   */
  @Override
  public E peek () {
    if (size == 0)
      return null;
    return theItems[first];
  }

  /**
   * Removes the entry at the first item in the queue and returns it
   * if the queue is not empty.
   * @post first references item that was second in the queue.
   * @return The item removed if successful or null if not
   */
  @Override
  public E poll () {
      if (size == 0) {
          return null;
      }
      E result = theItems[first];
      first = (first + 1) % theItems.length;
      --size;
      return result;
  }

  /**
   * Return the size of the queue
   * @return The number of items in the queue
   */
  @Override
    public int size () {
    return size;
  }

  /**
   * Returns an iterator to the elements in the queue
   * @return an iterator to the elements in the queue
   */
  @Override
    public Iterator<E> iterator () {
    return new Iter();
  }
    
  private boolean labReallocate = true;

  // Private Methods
  /**
   * Double the capacity and reallocate the items.
   * @pre The array is filled to capacity.
   * @post The capacity is doubled and the first half of the
   *       expanded array is filled with items.
   */
  @SuppressWarnings("unchecked")
  private void reallocate () {
      int newCapacity = 2 * this.theItems.length;
      Object[] newItems = new Object[newCapacity];
      if (this.labReallocate) {
          int j = this.first;
          int i = 0;
          while (i < this.size) {
              newItems[i] = this.theItems[j];
              j = (j + 1) % this.theItems.length;
              ++i;
          }
      } else if (this.first <= this.last) {
          System.arraycopy(this.theItems, this.first, newItems, 0, this.last - this.first + 1);
      } else {
          System.arraycopy(this.theItems, this.first, newItems, 0, this.theItems.length - this.first);
          System.arraycopy(this.theItems, 0, newItems, this.theItems.length - this.first, this.last + 1);
      }
      this.first = 0;
      this.last = this.size - 1;
      this.theItems = (E[])newItems;
  }

  private boolean labIterator = true;

  /** Inner class to implement the Iterator<E> interface. */
  private class Iter implements Iterator<E> {
    // Data Fields

    // Index of next element */
    private int index;

    // Count of elements accessed so far */
    private int count = 0;

    // Methods
    // Constructor
    /**
     * Initializes the Iter object to reference the
     * first queue element.
     */
    public Iter () {
    	this.count = 0;
        this.index = ArrayQueue.this.labIterator ? ArrayQueue.this.first : (ArrayQueue.this.size > 0 ? ArrayQueue.this.first : -1);
    }

    /**
     * Returns true if there are more elements in the queue to access.
     * @return true if there are more elements in the queue to access.
     */
    @Override
      public boolean hasNext () {
        if (ArrayQueue.this.labIterator) {
            if (this.count < ArrayQueue.this.size) {
                return true;
            }
            return false;
        } else if (this.index == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns the next element in the queue.
     * @pre index references the next element to access.
     * @post index and count are incremented.
     * @return The element with subscript index
     */
    @Override
      public E next () {
        if (hasNext()) {
            E returnValue = ArrayQueue.this.theItems[this.index];
            if (ArrayQueue.this.labIterator) {
                this.index = (this.index + 1) % ArrayQueue.this.theItems.length;
                this.count++;
            } else if (this.index != ArrayQueue.this.last) {
                this.index = (this.index + 1) % ArrayQueue.this.theItems.length;
            } else {
                this.index = -1;
            }
            return returnValue;
        }
        throw new NoSuchElementException();
    }

    /**
     * Remove the item accessed by the Iter object -- not implemented.
     * @throws UnsupportedOperationException when called
     */
    @Override
      public void remove () {
      throw new UnsupportedOperationException();
    }
  }
}
