/*
 * this is the heap data structure for apartment objects
 * it will manager all of the apartment information
 */
import java.util.*;
//import java.lang.*;

public class ApartmentManager {

	private int size = 101;//this seems like a decent starting amount considering this is apartments spanning the U.S.
	private int length=0;
	
	public Apartment[] costHeap;
	public HashMap<String, Integer> costIndex;
	
	public Apartment[] sqftHeap;
	public HashMap<String, Integer> sqftIndex;
	
	public HashMap<String, ApartmentManager> cityCost = new HashMap<String, ApartmentManager>();//cost per city
	public HashMap<String, ApartmentManager> citySqft = new HashMap<String, ApartmentManager>();//sqft per city
	
	
	//constructor
	 public ApartmentManager(){

			costHeap = new Apartment[size];
			sqftHeap = new Apartment[size];
			costIndex = new HashMap<String, Integer>(size);
			sqftIndex = new HashMap<String, Integer>(size);

		}
	
	 
	 //function methods
	 
	 public void insert(Apartment a) {//insert it in the array at the next last spot then insert into hash map
		 
		 costHeap[length] = a;
		 costIndex.put(key(a), length);
		 
		 sqftHeap[length] = a;
		 sqftIndex.put(key(a), length);
		 
		 costSwapUp(length);
		 sqftSwapUp(length);
		 length++;
		 
		 if(length == size) {
			 size *=2;
			 costHeap = Arrays.copyOf(costHeap, size);
	         sqftHeap = Arrays.copyOf(sqftHeap, size);
		 }
		 
		 
	 }
	 
	 public void update(String key, double n){//uses indirection to get array index to change cost
	        if(!costIndex.containsKey(key)) return;
			int i = costIndex.get(key);
			if(i > -1){
				costHeap[i].setCost(n);
				costSwapUp(i);
				costSwapDown(i);
			}
		}
	 
	 public void remove(String key){// remove from everything except the "cost by city" hashmap

			if(costIndex.containsKey(key)) {
				length--;
	            int i;

				i = costIndex.get(key);
				exchange(i, length , costHeap, costIndex);
				costHeap[length] = null;
				costIndex.remove(key);
				
				costSwapUp(i);
				costSwapDown(i);
				

				i = sqftIndex.get(key);
				exchange(i, length, sqftHeap, sqftIndex);
				sqftHeap[length] = null;
				sqftIndex.remove(key);
				
				sqftSwapUp(i);
				sqftSwapDown(i);
				

			}
		}
	 
	 
	 public Apartment getMinCost() {
		 return costHeap[0];
	 }
	 
	 public Apartment getMaxSqft() {
		 return sqftHeap[0];
	 }
	 
	 
	 //end function methods
	 
	 
	 
	 //helper methods
	 public boolean isEmpty(){
	        return length == 0;
	    }
	 
	 public int size(){
	        return length;
	    }
	 public String key(Apartment a){
	        return  a.getZip4() + " " + a.getStreetAddr() + " " + a.getRoom();
	    }
	 
	 
	 private void exchange(int i, int j, Apartment[] a, HashMap<String, Integer> d){

			Apartment t = a[i];
			a[i] = a[j];
			a[j] = t;
			d.replace(key(a[i]), i);
			d.replace(key(a[j]), j);

		}
	 
	 
	 
	 //cost helpers
	 private boolean costIsLess(int i, int j){//since i used this check a lot i made a method

			if(costHeap[i] == null || costHeap[j] == null) return false;
			return costHeap[i].getCost() < costHeap[j].getCost();
		}
	 
	 
	 private void costSwapUp(int i){//swaps toward root
 		 	int parent = (i - 1)/2;
			while(i > 0 && costIsLess(i, parent)){
				exchange(i, parent, costHeap, costIndex);
				i = parent;
			}
	        
		}
	 
	 
	 private void costSwapDown(int i){//swaps away from root
		
		 while(2 * i + 1 < length){		
			 	int child = 2 * i + 1;
			 	
				if (child < length && costIsLess(child + 1, child)) child++;
				if (!costIsLess(child, i)) break;
				
				
				exchange(i, child, costHeap, costIndex);
				i = child;
			}
		}
	 
	 
	 //end cost helpers
	 
	 
	 
	 //sqft helpers
	 private boolean sqftIsMore(int i, int j){//again just for simplicity

			if(sqftHeap[i] == null || sqftHeap[j] == null) return false;
			return sqftHeap[i].getSqft() > sqftHeap[j].getSqft();
		}

	 
	 private void sqftSwapUp(int i){//swaps toward root
		 int parent = (i - 1)/2;
			while(i > 0 && sqftIsMore(i, parent)){
				exchange(i, parent, sqftHeap, sqftIndex);
				i = parent;
			}
	        
		}
	 
	 
	 private void sqftSwapDown(int i){//swaps away from root
			
		 while(2*i+1 < length){		
			 	int child = 2*i+1;
			 	
				if (child < length && sqftIsMore(child+1, child)) child++;
				if (!sqftIsMore(child, i)) break;
				
				
				exchange(i, child, sqftHeap, sqftIndex);
				i = child;
			}
		}
	 
	 
	 //end sqft helpers
	 //end helper methods
	
}
