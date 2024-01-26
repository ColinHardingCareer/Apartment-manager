import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
 * contains the main method and UI
 * 
 */
public class Driver_Apartment {
	
	public static Scanner sc = new Scanner(System.in);
	public static ApartmentManager heap = new ApartmentManager();//the main heap
	
	public static void main(String[] args) {
		
		
		//load in a file if applicable

		System.out.println("Loading...");
        try {
        	File ui = new File("apartments.txt");
        	if(ui.exists()) {//true
        		String street, city, zip4, room;
    	        double cost, sqft;
    	        
    	        
        		System.out.println("Building...");
                Scanner scanner = new Scanner(ui);
                
                scanner.nextLine();
                while(scanner.hasNextLine()) {
                	String w = scanner.nextLine();
                	String[] info = w.split(":");
              
                	zip4 = info[3].toLowerCase();
         	        city = info[2].toLowerCase();	        
         	        street = info[0].toLowerCase();
         	        room = info[1].toLowerCase(); 
         	        cost = Double.parseDouble(info[4]);
         	        sqft = Double.parseDouble(info[5]);
         	        
         	
         	        Apartment a = new Apartment(street, city,zip4,room,cost,sqft);
         	        //Apartment apt = new Apartment(streetAddress, city, zip4, room, cost, sqft);
         	        /*
         	         * helpers un-comment
         	        System.out.println(a.toString());
         	        System.out.println();
         	        */
         	        
         	        
         	        
         	       if(heap.costIndex.containsKey( zip4 + " "+street+ " " + room)) {//if it already exists update else insert
       	        	heap.update(zip4 + " "+street + " " + room, cost);
       	        }else {
       	        	heap.insert(a);
       	        }
         	        
         	        
         	       if (heap.cityCost.get(city)!=null){        	    	   
         	    		   heap.cityCost.get(city).insert(a);     
         	    		  System.out.println("insert: "+street);
         	       }else {
         	    	  heap.cityCost.put(city, new ApartmentManager());
         	    	 heap.cityCost.get(city).insert(a);   
         	    	  System.out.println("put: "+ street);
         	       }
         	        
         	      if (heap.citySqft.get(city)!=null){        	    	   
    	    		   heap.citySqft.get(city).insert(a);        	    	   
    	       }else{
      	    	  heap.citySqft.put(city, new ApartmentManager());
      	    	heap.citySqft.get(city).insert(a);
      	       }

        	}
        	
                scanner.close();	
            	           	
            }
            
       
        	
        } catch(FileNotFoundException e){
            System.out.println("the txt file was not found");
        
		}//end try-catch
		
        //end loading file
		
		
		
		//start the UI
		while(true) {
		
		System.out.print("\nMenu Options\n"
				+ "1.\tAdd an apartment\n"
				+ "2.\tUpdate an apartment\n"
				+ "3.\tRemove an apartment\n"
				+ "4.\tRetrieve the lowest price apartment\n"
				+ "5.\tRetrieve the highest square footage apartment\n"
				+ "6.\tRetrieve the lowest price apartment for a city\n"
				+ "7.\tRetrieve the highest square footage apartment for a city\n"
				+ "0.\tExit the program\n");
        
        System.out.print("\nEnter a number for an option\n");
        int choice = sc.nextInt();
		
        switch(choice){
        case 0: 
            System.exit(0);
            break;
        case 1:  
        	try {
            add();
        }catch(Exception e){
    		System.out.println("\nSomething went wrong try again");        		
    	}
            break;
        case 2: 
        	try {
            update();
        	}catch(Exception e){
        		System.out.println("\nThere are no records for that Apartment.");        		
        	}
            break;
        case 3: 
        	try {
            delete();
        	}catch(Exception e){
        		System.out.println("\nThis Apartment was never here.");        		
        	}
            break;
        case 4: 
        	try {
            System.out.println("The min cost apartment\n===========\n" + heap.getMinCost().toString());
        	}catch(Exception e){
        		System.out.println("\nThere are no records for that city.");        		
        	}
            break;
        case 5: 
        	try {
            System.out.println("The max sqft apartment\n===========\n" + heap.getMaxSqft().toString());
        	}catch(Exception e){
        		System.out.println("\nThere are no records for that city.");        		
        	}
            break;
        case 6: 
            cheapest();
            break;
        case 7: 
            maxSqft();
            break;
            /*
             * this could potentially help see the main heap when grading
        case 8: //tester
            printArr();
            System.out.println("size: "+heap.size());
            break;
            */
        default:
            System.out.println("\nPlease enter a valid number.");
            break;
    }
		}
		
	}
	
	//end UI
	
	
	
	 public static void add(){

	        String streetAddress, city, zip4,room;
	        double cost, sqft;
	        

	        System.out.println("\nfill out the information for the apartment.");

	        sc.nextLine();
	        System.out.print("\nWhat is the zipcode? ");
	        zip4 = sc.nextLine();
	        
	        System.out.print("\nWhat is the city? ");
	        city = sc.nextLine().toLowerCase();	        
	        
	        System.out.print("\nWhat is the street address? ");
	        streetAddress = sc.nextLine().toLowerCase();

	        System.out.print("\nWhat is the apartment number? ");
	        room = sc.nextLine(); 

	        System.out.print("\nWhat is the cost per month? ");
	        cost = sc.nextInt();

	        System.out.print("\nWhat is the square footage? ");
	        sqft = sc.nextInt();
	        
	        
	        Apartment apt = new Apartment(streetAddress, city, zip4, room, cost, sqft);
	        
	        //System.out.println(heap.costIndex.containsKey( zip4 + " "+streetAddress + " " + room));// to see if it is actually working
	        
	        if(heap.costIndex.containsKey( zip4 + " "+streetAddress + " " + room)) {//if it already exists update else insert
	        	System.out.println("\nThis entry already exists\nUpdating Cost...");
	        	heap.update(zip4 + " "+streetAddress + " " + room, cost);
	        	heap.cityCost.get(city).update(zip4 + " "+streetAddress + " " + room, cost);
	        }else {
	        	heap.insert(apt);
	        
	        //System.out.println(apt.toString());//more help
	        
	        
	          
	        // below inserts into the per city heaps
	        	
	        	//insert into cost per city heap
		        if (heap.cityCost.get(city)!=null){        	    	   
		        	heap.cityCost.get(city).insert(apt);     
		    		//System.out.println("insert c: "+streetAddress);//helper
		        }else {
		    	  heap.cityCost.put(city, new ApartmentManager());
		    	  heap.cityCost.get(city).insert(apt);   
		    	 //System.out.println("put c: "+ streetAddress);//helper
		       }
		      //end insert into cost per city heap
		        
		        
		      //insert into sqft per city heap
		      if (heap.citySqft.get(city)!=null){        	    	   
		    	  heap.citySqft.get(city).insert(apt);   
		    	 // System.out.println("insert s : "+streetAddress);//helper
		      }else{
		    	  heap.citySqft.put(city, new ApartmentManager());
		    	  heap.citySqft.get(city).insert(apt);
	      		  //System.out.println("put s : "+ streetAddress);//helper
		      }
		    //end insert into sqft per city heap
	      
	        
	       }  
	        
	    }//end add apartment
	 
	 
	 public static void update(){
	        int cost;
	        String update;
	        	        
	        update=key();
	               
	        if(!heap.costIndex.containsKey(update)) return;
	        
	        System.out.print("\nWhat is the new cost per month? ");
	        cost = sc.nextInt();
	        
	        heap.update(update, cost);
	        
	        String city = heap.costHeap[heap.costIndex.get(update)].getCity();
	        heap.cityCost.get(city).update(update, cost);
	        }
	    
	
public static void delete(){//delete EVERYTHING!!!! wait, just delete the apartment.......
	String key = key();
    
    String city = heap.costHeap[heap.costIndex.get(key)].getCity();
	heap.cityCost.get(city).remove(key);
	heap.citySqft.get(city).remove(key);
   
    
    
    heap.remove(key);
}


private static void cheapest(){
	String city;

    //gets the city 
    System.out.print("Please enter the city: ");
    sc.nextLine();
    city = sc.nextLine().toLowerCase();

    Apartment temp = heap.cityCost.get(city).getMinCost();
    	
    //makes sure it exists and prints the info if so
    if (temp == null) {
    	System.out.println("\nThere are no records for that city.");
    }else {
    	System.out.println("The min cost apartment\n===========\n" + temp.toString()+"\n");
    	/*
    	 * this helps see the hashmap of the per city
    	System.out.println("the hashmap of the city");
    	
    	System.out.println("size: "+heap.cityCost.get(city).size());
    	
    	for(int i = 0; i<heap.cityCost.get(city).size();i++)
		   System.out.println(heap.cityCost.get(city).costHeap[i].toString()+"\n");
    	*/
    }
}
	
private static void maxSqft(){
	
	String city;

    
    System.out.print("Please enter the city: ");
    sc.nextLine();
    city = sc.nextLine().toLowerCase();

    Apartment temp = heap.citySqft.get(city).getMaxSqft();
    if (temp == null) { 
    	System.out.println("\nThere are no records for that city.");
    }else { 
    	System.out.println("The max sqft apartment\n===========\n" + temp.toString()+"\n");
    	/*
    	 * this helps see the hashmap of the per city
System.out.println("the hashmap of the city");
		
		System.out.println("size: "+heap.citySqft.get(city).size());
		
		for(int i = 0; i<heap.citySqft.get(city).size();i++)
			
		   System.out.println(heap.citySqft.get(city).sqftHeap[i].toString()+"\n"+i);
    */
    }
}

public static String key () {//this just makes the key i used it a lot because i didnt wanna write it 
	 String address, zip4, temp, room;

       sc.nextLine();
       System.out.print("\nWhat is the street address? ");
       address = sc.nextLine().toLowerCase();

       System.out.print("\nWhat is the apartment number? ");
       room = sc.nextLine();

       System.out.print("\nWhat is the zipcode? ");
       zip4 = sc.nextLine().toLowerCase();

       temp = zip4 + " "+address + " " + room;
       return temp;
}
/*
 * helper
public static void printArr(){//part of that helper piece in switch 8

    for(int i = 0; i < heap.size(); i++){
        System.out.print(heap.costHeap[i].getCost() + " ");
    }
    System.out.println("\n");
    for(int i = 0; i < heap.size(); i++){
        System.out.print(heap.sqftHeap[i].getSqft() + " ");
    }
    System.out.println("\n");
}
*/



}
