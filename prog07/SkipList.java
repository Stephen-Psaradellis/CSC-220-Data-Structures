package prog07;
import java.util.*;

import prog07.DummyList.Node;

public class SkipList <K extends Comparable<K>, V> 
  extends DummyList<K, V> {

  int size = 0;
  protected Node<K, Object> topDummy = (Node<K, Object>) dummy;
  
  boolean isSkip (Node node) {
    return node.value != null && node.value instanceof Node;
  }

  Random random = new Random(1);

  boolean heads () {
    return random.nextInt() % 2 == 0;
  }

  public String toString () {
    String s = "";
    for (Node start = topDummy; isSkip(start); start = (Node) start.value) {
      for (Node node = start.next; node != null; node = node.next)
        s += node.key + " ";
      s += "\n";
    }
    s += super.toString();
    return s;
  }

  void makeTestLists () {
    while (topDummy.next != null && topDummy.next.next != null) {
      Node lo = topDummy;
      topDummy = new Node(null, topDummy, null);
      Node hi = topDummy;
      while (lo.next != null && lo.next.next != null) {
        lo = lo.next.next;
        hi.next = new Node(lo.key, lo, null);
        hi = hi.next;
      }
    }
  }
  public boolean containsKey (Object keyAsObject) {
	    K key = (K) keyAsObject;
	    Node start = topDummy;
	    while(isSkip(start)) {
	    	Node previous = findPrevious(key,start);
	    	start = (Node) previous.value;
	    }
	    Node node = findPrevious(key,start);
	    return keyIsNext(key, node);
	  }
  public V get(Object keyAsObject) {
	  K key = (K) keyAsObject;
	  Node start = topDummy;
	  while (isSkip(start)) {
		  Node previous = findPrevious(key, start);
		  start = (Node) previous.value;
	  }
	  Node previous = findPrevious(key,start);
	  if (!keyIsNext(key,previous)) {
		  return null;
	  }
	  else 
		  return (V) previous.next.value;
  }
  public V remove(Object keyAsObject) {
	K key = (K) keyAsObject;
	Node start = topDummy;
	while(isSkip(start)) {
		Node previous = findPrevious(key,start);
		if(previous.equals(topDummy) && previous.next.key.equals(keyAsObject)) {
			start = (Node) start.next;
		System.out.print("This loop");
		}
		else {
			Node current = previous.next;
			if(current != null && current.key.equals(keyAsObject)){
				previous.next = current.next;
			}
			System.out.print("That loop");
		}
		start = (Node) previous.value;
	}
	Node previous = findPrevious(key,start);
	if(!keyIsNext(key,previous)) {
		return null;
	} else {
		Node current = previous.next;
		if(current.key.equals(keyAsObject))
			previous.next = current.next;
		return (V)current.value;
	}	
  }
  public V put(K key, V value){
	  Deque<Node> stack = new LinkedList<Node>();
	  Node start = topDummy;
	  Node previous;
	  V returnValue = null;
	  while(isSkip(start)){
		  previous = findPrevious(key,start);
		  stack.push(previous);
		  start = (Node)previous.value;
	  }
	  previous = findPrevious(key, start);
	  if(keyIsNext(key,previous)){
		  returnValue = (V)previous.next.value;
		  previous.next = previous.next.next;
	  }
	  previous.next = new Node(key,value,previous.next);
	  while(heads()){
		  Node keyNode = previous.next;
		  if(stack.isEmpty()){
			  Node add = new Node(key,keyNode,null);
			  topDummy = new Node (null,topDummy,add);
			  previous = topDummy;
		  } else {
			  previous = stack.pop();
			  previous.next = new Node(key,keyNode,previous.next);
		  }
	  }
	  size++;
	  return returnValue;
  }
  

  public static void main (String[] args) {
    SkipList<String, Integer> map = new SkipList<String, Integer>();
    for (int i = 1; i < 26; i++) {
      String key = "" + (char) ('A' + i);
      Integer val = i;
      System.out.println("put(" + key + "," + val + ")=" + map.put(key, val));
    }
    map.makeTestLists();
    System.out.println(map);
    // map.checkLists();
    
    System.out.println("containsKey(A)=" + map.containsKey("A"));
    System.out.println("containsKey(C)=" + map.containsKey("C"));
    System.out.println("containsKey(L)=" + map.containsKey("L"));
    System.out.println("containsKey(M)=" + map.containsKey("M"));
    System.out.println("containsKey(Z)=" + map.containsKey("Z"));
    System.out.println("containsKey(Zoe)=" + map.containsKey("Zoe"));
    
    System.out.println("get(A)=" + map.get("A"));
    System.out.println("get(C)=" + map.get("C"));
    System.out.println("get(L)=" + map.get("L"));
    System.out.println("get(M)=" + map.get("M"));
    System.out.println("get(Z)=" + map.get("Z"));
    System.out.println("get(Zoe)=" + map.get("Zoe"));
    
    System.out.println(map);
    
    System.out.println("remove(A)=" + map.remove("A"));
    System.out.println("remove(C)=" + map.remove("C"));
    System.out.println("remove(L)=" + map.remove("L"));
    System.out.println("remove(Q)=" + map.remove("Q"));
    System.out.println("remove(Z)=" + map.remove("Z"));
    System.out.println("remove(Zoe)=" + map.remove("Zoe"));
    
    System.out.println(map);
    
    System.out.println("put(A,10)=" + map.put("A",10));
    System.out.println("put(A,11)=" + map.put("A",11));
    System.out.println("put(L,20)=" + map.put("L",20));
    System.out.println("put(L,21)=" + map.put("L",21));
    System.out.println("put(Z,30)=" + map.put("Z",30));
    System.out.println("put(Z,31)=" + map.put("Z",31));

    System.out.println(map);
  }
}
