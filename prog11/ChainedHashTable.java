package prog11;

import java.util.*;

public class ChainedHashTable<K, V> extends AbstractMap<K, V> {
  public static class Entry<K, V> implements Map.Entry<K, V> {
	K key;
	V value;
	Entry<K,V> next;

        public K getKey () { return key; }
        public V getValue () { return value; }
        public V setValue (V value) { return this.value = value; }

	Entry (K key, V value, Entry<K,V> next) {
	    this.key = key;
	    this.value = value;
	    this.next = next;
	}
    }

    private final static int DEFAULT_CAPACITY = 5;
    private Entry<K,V>[] table = new Entry[DEFAULT_CAPACITY];
    private int size;

    private int hashIndex (Object key) {
	int index = key.hashCode() % table.length;
	if (index < 0)
	    index += table.length;
	return index;
    }

    private Entry<K,V> find (Object key) {
	int index = hashIndex(key);
	for (Entry<K,V> node = table[index]; node != null; node = node.next)
	    if (key.equals(node.key))
		return node;
	return null;
    }

    public boolean containsKey (Object key) {
	return find(key) != null;
    }

    public V get (Object key) {
	Entry<K,V> node = find(key);
	if (node == null)
	    return null;
	return node.value;
    }

    public V put (K key, V value) {
	Entry<K,V> node = find(key);
	if (node != null) {
	    V old = node.value;
	    node.value = value;
	    return old;
	}
	if (size == table.length)
	    rehash(2 * table.length);
	int index = hashIndex(key);
	table[index] = new Entry<K,V>(key, value, table[index]);
	size++;
	return null;
    }
	
    public V remove (Object key) {
	// Get the index for the key.
    	int index = hashIndex(key); 
	// Deal with the case that the linked list at that index is empty.
    	if(table[index] == null)
    		return null;
	// Deal with the case that the key belongs to the first
	// element in that list.
    	if(table[index].getKey() == key) {
    		Entry entry = table[index];
    		table[index] = entry.next;
    		size--;
    		return (V)entry.value;  		
    	}
	// Deal with the case that the key belongs to some other
	// element in that list.	
    	else {
    		Entry entry = table[index];
    		while (entry.next!=null) {
    			if(entry.next.key.equals(key)){
    				V value = (V)entry.next.value;
    				entry.next = entry.next.next;
    				size--;
    				return value;
    			}
    				
    		}
    	}
	// Return null otherwise.
	return null;
    }

    
    private void rehash (int newCapacity) {
      // IMPLEMENT
    	Entry<K,V>[] newTable = table;
    	size = 0;
    	table = new Entry[newCapacity]; 
    	for(int i = 0; i < newTable.length; i++) {
    		for (Entry<K,V> entry = newTable[i]; entry != null; entry = entry.next) {
    		put(entry.key,entry.value);
    		}
    	}
    }

    private Iterator<Map.Entry<K, V>> entryIterator () {
      return new EntryIterator();
    }

    private class EntryIterator implements Iterator<Map.Entry<K, V>> {
      // EXERCISE
    	Entry<K,V> entry = null;
    	int counter = 0;

      public EntryIterator() {
    	  for(int i = counter; i <table.length; i++) {
    		  if(table[i] != null){
    			  entry = table[i];
    			  return;
    		  }
    	  }
      }

      public boolean hasNext () {
        // EXERCISE
    	  if(entry!=null)
    		  return true;
    		  
        return false;
      }

      public Map.Entry<K, V> next () {
        // EXERCISE
    	  Entry<K,V> entry1 = entry;
    	  entry = entry.next;
    	  if(entry == null) {
    		  counter++;
    		  while(counter<table.length && table[counter] == null ) {  
    		  	counter++;
    		  }
    	  
    	  	if(counter <table.length) 
    			entry = table[counter];
    	  }
    	  return entry1;
      }

      public void remove () {}
    }

    public Set<Map.Entry<K,V>> entrySet() { return new EntrySet(); }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
      public int size() { return size; }

      public Iterator<Map.Entry<K, V>> iterator () {
        return entryIterator();
      }

      public void remove () {}
    }

    public String toString () {
	String ret = "------------------------------\n";
	for (int i = 0; i < table.length; i++) {
	    ret = ret + i + ":";
	    for (Entry<K,V> node = table[i]; node != null; node = node.next)
		ret = ret + " " + node.key + " " + node.value;
	    ret = ret + "\n";
	}
	return ret;
    }

    public static void main (String[] args) {
	ChainedHashTable<String, Integer> table =
	    new ChainedHashTable<String, Integer>();

	table.put("Brad", 46);
	System.out.println(table);
	table.put("Hal", 10);
	System.out.println(table);
	table.put("Kyle", 6);
	System.out.println(table);
	table.put("Lisa", 43);
	System.out.println(table);
	table.put("Lynne", 43);
	System.out.println(table);
	table.put("Victor", 46);
	System.out.println(table);
	table.put("Zoe", 6);
	System.out.println(table);
	table.put("Zoran", 76);
	System.out.println(table);

        for (String key : table.keySet())
          System.out.print(key + " ");
        System.out.println();

	table.remove("Zoe");
	System.out.println(table);
	table.remove("Kyle");
	System.out.println(table);
	table.remove("Brad");
	System.out.println(table);
	table.remove("Zoran");
	System.out.println(table);
	table.remove("Lisa");
	System.out.println(table);
    }
}
			     
