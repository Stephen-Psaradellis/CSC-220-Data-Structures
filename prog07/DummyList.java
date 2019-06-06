package prog07;
import java.util.*;

public class DummyList <K extends Comparable<K>, V> 
  extends AbstractMap<K, V> {

  protected static class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {

    K key;
    V value;
    Node<K, V> next;
    
    Node (K key, V value, Node next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }
    
    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  int size = 0;
  protected Node<K, V> dummy = new Node<K, V>(null, null, null);
  
  protected class Iter implements Iterator<Map.Entry<K, V>> {
    Node<K, V> previous = dummy;
    
    public boolean hasNext () { 
      if(previous.next!=null)
    	  return true;
      else
    	  return false;
    }
    
    public Map.Entry<K, V> next () {
    	if(hasNext()) {
    		previous = previous.next;
    		return previous;
    	}
    	else {
    		return null;
    	}
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  protected Node findPrevious (K key, Node start) {
	  System.out.print(start.key + " ");
	  Node node = start;
    while((node.next!=null) && node.next.key.compareTo(key) < 0) {
    	node = node.next;
    }
    return node;
  }
  
  protected boolean keyIsNext (K key, Node node) {
    return node.next != null && node.next.key.equals(key);
  }

  public boolean containsKey (Object keyAsObject) {
    K key = (K) keyAsObject;
    Node previous = findPrevious(key, dummy);
    return keyIsNext(key, previous);
  }
  
  public V get (Object keyAsObject) {
    K key = (K) keyAsObject;
    // STEP 7:  Implement, using findPrevious.
    // You will need to cast the value to a V using (V).
    Node node = findPrevious(key,dummy);
    if(node.next!=null)
    	return (V)node.next.getValue();
    else
    	return null;
  }
	public V remove (Object keyAsObject) {
		K key = (K) keyAsObject;
		Node previous = findPrevious(key,dummy);
		if(!keyIsNext(key,previous))
			return null;
		Node remove = previous.next;
		if(remove.key.equals(keyAsObject))
			previous.next=remove.next;
		return (V)remove.value;
	}
	public V put (K key, V value) {
		Node add = new Node(key,value,null);
		Node previous = findPrevious(key,dummy);
		if(!keyIsNext(key,previous)) {
			Node next = previous.next;
			previous.next = add;
			add.next = next;
			return null;
		}
		else {
			Node oldNode = previous.next;
			previous.next = new Node(key,value,oldNode.next);
			return (V)oldNode.value;
		}
	}

  public boolean isEmpty () { return size == 0; }
  
  protected class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter();
    }
    
    public int size () { return size; }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  protected void makeTestList () {
    for (int i = 24; i > 0; i -= 2) {
      dummy.next = new Node("" + (char)('A' + i), i, dummy.next);
      size++;
    }
  }

  public static void main (String[] args) {
    DummyList<String, Integer> map = new DummyList<String, Integer>();
    map.makeTestList();
    System.out.println(map);
    System.out.println("containsKey(A) = " + map.containsKey("A"));
    System.out.println("containsKey(C) = " + map.containsKey("C"));
    System.out.println("containsKey(L) = " + map.containsKey("L"));
    System.out.println("containsKey(M) = " + map.containsKey("M"));
    System.out.println("containsKey(Y) = " + map.containsKey("Y"));
    System.out.println("containsKey(Z) = " + map.containsKey("Z"));

    System.out.println("get(A) = " + map.get("A"));
    System.out.println("get(C) = " + map.get("C"));
    System.out.println("get(L) = " + map.get("L"));
    System.out.println("get(M) = " + map.get("M"));
    System.out.println("get(Y) = " + map.get("Y"));
    System.out.println("get(Z) = " + map.get("Z"));

    System.out.println("remove(A) = " + map.remove("A"));
    System.out.println("remove(C) = " + map.remove("C"));
    System.out.println("remove(L) = " + map.remove("L"));
    System.out.println("remove(M) = " + map.remove("M"));
    System.out.println("remove(Y) = " + map.remove("Y"));
    System.out.println("remove(Z) = " + map.remove("Z"));

    System.out.println(map);

    System.out.println("put(A,7) = " + map.put("A", 7));
    System.out.println("put(A,9) = " + map.put("A", 9));
    System.out.println("put(M,17) = " + map.put("M",17));
    System.out.println("put(M,19) = " + map.put("M",19));
    System.out.println("put(Z,3) = " + map.put("Z",3));
    System.out.println("put(Z,20) = " + map.put("Z",20));

    System.out.println(map);
  }
}
