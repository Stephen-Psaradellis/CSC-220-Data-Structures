package prog11;

import java.util.*;

import prog11.ChainedHashTable.Entry;


public class OpenHashTable<K, V> extends AbstractMap<K, V> {
    private static class Node<K, V> implements Map.Entry<K, V> {
	K key;
	V value;

        public K getKey () { return key; }
        public V getValue () { return value; }
        public V setValue (V value) { return this.value = value; }

	Node (K key, V value) {
	    this.key = key;
	    this.value = value;
	}
    }

    private final static int DEFAULT_CAPACITY = 5;
    private Node<K,V>[] table = new Node[DEFAULT_CAPACITY];
    private Node<K,V> DELETED = new Node<K,V>(null, null);
    private int size;
	private int nonNull=0;
  
	
	private int hashIndex (Object key) {
	int index = key.hashCode() % table.length;
	if (index < 0)
	    index += table.length;
	return index;
    }

    // Linear probe sequence: start at hashIndex(key) and increment,
    // but roll around to zero at the end of the table.

    // Return the index of the Node with key if it is in the probe
    // sequence.

    // If it is not there, return the index where the Node with key
    // should be inserted.  If there is a deleted Node in the probe
    // sequence, return the index of the *first* deleted Node in the
    // sequence.

    // Otherwise return the index of the first null in the probe
    // sequence.
    private int find (Object key) {
      int i = hashIndex(key);
      int j = -1;
      while(table[i]!=null && (table[i] == DELETED || !table[i].key.equals(key))) {
    	  if(table[i] == DELETED && j==-1)
    	  	j = i;	  
    	  i = (i+1)%table.length;
      }
      if(j!= -1 && table[i]==null) 
    	  return j;    	      
      return i;
    }

    public boolean containsKey (Object key) {
	Node<K,V> node = table[find(key)];
	return node != null && node != DELETED;
    }

    public V get (Object key) {
	Node<K,V> node = table[find(key)];
	if (node == null || node == DELETED)
	    return null;
	return node.value;
    }

    public V put (K key, V value) {
	System.out.println("put " + key + " " + value);
	int index = find(key);
	Node<K,V> node = table[index];
	if (node == null || node == DELETED) {
		  if(node==null)
			 nonNull++;
          table[index] = new Node<K,V>(key, value);
          size++;
          if (nonNull>table.length/2)
            rehash(4 * size);
          return null;
	}
	V old = node.value;
	node.value = value;
	return old;
    }

    public V remove (Object key) {
	System.out.println("remove " + key);
	int index = find(key);
	Node<K,V> node = table[index];
	if (node == null || null == DELETED)
	    return null;
	table[index] = DELETED;
	size--;
	return node.value;
    }

    private void rehash (int newCapacity) {
    	Node<K,V>[] newTable = table;
    	size = 0;
    	nonNull = 0;
    	table = new Node[newCapacity]; 
    	for(int i = 0; i < newTable.length; i++) {
    		Node<K,V> entry = newTable[i];
    		if(entry!=DELETED && entry!=null)
    			put(entry.key,entry.value);
    		}
    	}
 	public class Name implements Comparable<Name> {
    private String first, last;

        public Name (String first, String last) {
	this.first = first;
	this.last = last;
    }	@Override
	
        public int compareTo(OpenHashTable<K, V>.Name that) {
    	int firstCmpFirst = this.first.compareTo(that.first);
		int lastCmpLast = this.last.compareTo(that.last);
        if ( lastCmpLast == 0)
            return  firstCmpFirst;
        else
            return  lastCmpLast;
	}
 	}

    private Iterator<Map.Entry<K, V>> entryIterator () {
      return new EntryIterator();
    }

    private class EntryIterator implements Iterator<Map.Entry<K, V>> {
      // EXERCISE
        int counter;

     EntryIterator() {
      	  for(counter = 0; counter <table.length; counter++) {
      		  if(table[counter] != null && table[counter] != DELETED){
      			  return;
      		  }
      	  }
        }

      public boolean hasNext () {
        // EXERCISE
    	  if(counter < table.length)
    		  return true;
        return false;
      }

      public Map.Entry<K, V> next () {
      	  Entry<K,V> entry1 = table[counter];
  		  counter++;
  		  while(counter<table.length && (table[counter] == null || table[counter] == DELETED)) {  
  		  	counter++;
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
	String ret = "-------------------------------\n";
	for (int i = 0; i < table.length; i++) {
	    ret = ret + i + ": ";
	    Node<K,V> node = table[i];
	    if (node == null)
		ret = ret + "null\n";
	    else if (node == DELETED)
		ret = ret + "DELETED\n";
	    else
		ret = ret + node.key + " " + node.value + "\n";
	}
	return ret;
    }

    public static void main (String[] args) {
	OpenHashTable<String, Integer> table =
	    new OpenHashTable<String, Integer>();

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
	table.remove("Hal");
	System.out.println(table);
	table.remove("Lynne");
	System.out.println(table);

	table.put("Ant", 3);
	System.out.println(table);
	table.remove("Ant");
	System.out.println(table);
	table.put("Bug", 1);
	System.out.println(table);
	table.remove("Bug");
	System.out.println(table);
	table.put("Cat", 4);
	System.out.println(table);
	table.remove("Cat");
	System.out.println(table);
	table.put("Dog", 1);
	System.out.println(table);
	table.remove("Dog");
	System.out.println(table);
	table.put("Eel", 5);
	System.out.println(table);
	table.remove("Eel");
	System.out.println(table);
	table.put("Fox", 9);
	System.out.println(table);
	table.remove("Fox");
	System.out.println(table);
	table.put("Gnu", 2);
	System.out.println(table);
	table.remove("Gnu");
	System.out.println(table);

	table.put("Hen", 2);
	System.out.println(table);
	table.remove("Hen");
	System.out.println(table);
	table.put("Jay", 2);
	System.out.println(table);
	table.remove("Jay");
	System.out.println(table);
	table.put("Owl", 2);
	System.out.println(table);
	table.remove("Owl");
	System.out.println(table);
	table.put("Pig", 2);
	System.out.println(table);
	table.remove("Pig");
	System.out.println(table);
	table.put("Rat", 2);
	System.out.println(table);
	table.remove("Rat");
	System.out.println(table);
	table.put("Yak", 2);
	System.out.println(table);
	table.remove("Yak");
	System.out.println(table);
    }
}





     
