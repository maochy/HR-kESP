package model;

public class Location {
	private double lat = 0;
	private double lng = 0;

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public Location() {
		
	}
	public Location(double lat,double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
	public Location(Location l) {
		this.lat = l.getLat();
		this.lng = l.getLng();
	}
	
	public Location(double[] l) {
		this.lat = l[0];
		this.lng = l[1];
	}
	
	public static double rad(double d){
	    return d * Math.PI / 180.0;
	}
	
	public static double getDistance(Location l1,Location l2) {
		double radLat1 = rad(l1.getLat());
	    double radLat2 = rad(l2.getLat());
	    double a = radLat1 - radLat2;
	    double b = rad(l1.getLng()) - rad(l2.getLng());
	    double s = 2 *Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2))); 
	    s = s * ConstNum.EARTH_RADIUS;    
		return s; //meter
	}

}
