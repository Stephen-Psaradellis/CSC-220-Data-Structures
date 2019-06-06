package prog02;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {
	/** Add an entry to the directory.
    @param name The name of the new person
    @param number The number of the new person
	 */
	protected void add(String name, String number) {
		FindOutput fo = find(name);
		//int insertionIndex = fo.index;
		int insertionIndex = 0;
		if(!fo.found){
			size++;
			for(int i = size - 1; i > insertionIndex; i--){
				theDirectory[i] = theDirectory[i-1]; 
			}
		}
		if (size >= theDirectory.length) {
			reallocate();
		}
		theDirectory[insertionIndex] = new DirectoryEntry(name, number);
	}
	/** Find an entry in the directory.
    @param name The name to be found
    @return A FindOutput object containing the result of the search.
	 */

	protected FindOutput find (String name) {
		int first = 0; 
		int last = size-1; 
		int middle = ((first+last)/2);
		while(first<=last && !name.equals(theDirectory[middle].getName())){
			middle = ((first+last)/2);
			int cmp = name.compareTo(theDirectory[middle].getName());
			if(cmp == 0)
				break;
			if(cmp < 0){
				last = middle-1;
			}
			if(cmp > 0){
				first = middle+1;
			}
		}
		if(first>last && last!=-1)
			return new FindOutput(false,size);
		return new FindOutput(true,middle);
	}
	/** Remove an entry from the directory.
    @param name The name of the person to be removed
    @return The current number. If not in directory, null is
            returned
	 */
	public String removeEntry (String name) {
		FindOutput fo = find(name);
		if (!fo.found)
			return null;
		DirectoryEntry entry = theDirectory[fo.index];
		for(int i = fo.index; i<size-1; i++){
			theDirectory[i] = theDirectory[i+1];
		}
		size--;
		modified = true;
		return entry.getNumber();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
