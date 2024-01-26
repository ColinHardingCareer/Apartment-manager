/*
 * Apartment Object
 * used to hold information for a single apartment
 */
public class Apartment {
	
	private String streetAddr,city,zip4,room;
	private double cost,sqft;
	
	
	//full constructor
	public Apartment(String a,String c,String z,String r, double m, double s) {
		streetAddr = a;
		city = c;
		zip4 = z;
		room = r;
		cost = m;
		sqft = s;
		
	}
	
	
	  public String toString(){

	        StringBuilder s = new StringBuilder();
	        s.append("Street Address: " + streetAddr + "\n");
	        s.append("Apartment Number: " + room + "\n");
	        s.append("City: " + city + "\n");
	        s.append("Zipcode: " + zip4 + "\n");
	        s.append("Cost Per Month: $" + cost + "\n");
	        s.append("Square Footage: " + sqft);

	        return s.toString();
	    }
	
	
	//getters and setters
	public String getStreetAddr() {
		return streetAddr;
	}
	public void setStreetAddr(String a) {
		streetAddr = a;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String c) {
		city = c ;
	}
	
	public String getZip4() {
		return zip4;
	}
	public void setZip4 (String z) {
		zip4 = z;
	}
	
	public String getRoom() {
		return room;
	}
	public void setRoom (String r) {
		room = r;
	}
	
	public double getCost() {
		return cost;
	}
	public void setCost (double m) {
		cost = m ;
	}
	
	public double getSqft() {
		return sqft;
	}
	public void setSqft (double s) {
		sqft = s ;
	}
	
	//end getters and setters

}
