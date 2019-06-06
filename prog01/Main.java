package prog01;

import java.util.*;

public class Main {
  public static void main (String[] args) {
	  int[] array1 = {2,4,6,8,10};
	  int[] array2 = {12,14,16,18,20};
	  int[] array3 = new int[10];
	  for(int i = 0; i < 5; i++) {
		  array3[i] = array1[i];
	  }
	  for(int j = 5; j < 10; j++) {
		  array3[j] = array2[j-5];
	  }
	  for(int k = 0; k < 10; k++){
		  System.out.println(array3[k]);
	  }

	  }
  }
    
    