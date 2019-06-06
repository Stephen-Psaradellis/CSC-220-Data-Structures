package prog04;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
  /** Add an entry to the directory.
      @param name The name of the new person.
      @param number The number of the new person.
  */
 protected void add (String name, String number) {
	    // EXERCISE
// Call find to find where to put the entry.
	 	FindOutput fo = find(name);
	 	DLLEntry entry = new DLLEntry(name, number);
// How does find tell you that the new entry should go at the end?
// Test for this condition and call super.add which handles it.
		 if(tail == null) {
			 super.add(name, number);
		 }
	// Create a new entry to insert.
	// How can you tell if the new entry should go at the front?
	// Test for this condition and write code to handle it.
		 else if(fo.entry == null){
		  	tail.setPrevious(entry);
	    	entry.setPrevious(tail);
	    	tail = entry;
	    	tail.setNext(fo.entry);
	  }		  
	// Set next to the entry that will be next after the new entry is
	// inserted.
		 else if (fo.entry.getPrevious()== null){
			 entry.setPrevious(null);
			 entry.setNext(head);
			 head.setPrevious(entry);
			 head = entry;
	  }
	// Set previous to the entry that will be previous.
		  else {
			 DLLEntry previous = fo.entry.getPrevious();
			 entry.setPrevious(previous);
			 previous.setNext(entry);
			 entry.setNext(fo.entry);
			 fo.entry.setPrevious(entry);
		  }
}

  /** Find an entry in the directory.
      @param name The name to be found.
      @return A FindOutput object describing the result.
  */
  protected FindOutput find (String name) {
	    // EXERCISE
	    // Modify find so it also stops when it gets to an entry after the
	    // one you want.
	  	for (DLLEntry entry = head; entry != null; entry = entry.getNext()) {
	  		int cmp = entry.getName().compareTo(name);
	  		if (cmp == 0)
	  			return new FindOutput(true, entry);
	  		if (cmp < 0)
	  			return new FindOutput(false, entry);
	  	}
	  	return new FindOutput(false,null);
	  	}
}
