package prog04;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPDStudy extends DLLBasedPD {
  /** Add an entry to the directory.
      @param name The name of the new person.
      @param number The number of the new person.
  */
  protected void add (String name, String number) {
    // EXERCISE
    // Call find to find where to put the entry.
	  FindOutput fo = find(name);
	  
    // How does find tell you that the new entry should go at the end?
    // Test for this condition and call super.add which handles it.
	  if(fo.entry==tail) {
		  super.add(name, number);
	  }

    // Create a new entry to insert.
	  DLLEntry entry = new DLLEntry(name,number);

    // How can you tell if the new entry should go at the front?
    // Test for this condition and write code to handle it.
	  if(fo.entry==head) {
		 DLLEntry next = fo.entry.getNext();
		 entry.setNext(next);
		 entry.setPrevious(null);
		 next.setPrevious(entry);
		 fo.entry.setNext(null);
		 head = entry;
	  }
		  
	  else {
		  DLLEntry next = fo.entry.getNext();
		  DLLEntry previous = fo.entry.getPrevious();
		  entry.setNext(next);
		  entry.setPrevious(previous);
		  next.setPrevious(entry);
		  previous.setNext(entry);
	  }
    // Set next to the entry that will be next after the new entry is
    // inserted.

    
    // Set previous to the entry that will be previous.


    // Insert the new DLLEntry between next and previous.


  }

  /** Find an entry in the directory.
      @param name The name to be found.
      @return A FindOutput object describing the result.
  */
  protected FindOutput find (String name) {
    // EXERCISE
    // Modify find so it also stops when it gets to an entry after the
    // one you want.

    return new FindOutput(false, null);
  }
}
