package model;

/**
 * @author gcui
 *
 */
public class BaseStation {
	private Location location = new Location();
	private double radius = 0;
	
	public BaseStation(){
		
	}
	
	public BaseStation(BaseStation s) {		
		this.location = s.getLocation();
		this.radius = s.getRadius();
	}
	
	public BaseStation(Location location,double radius) {
		this.location = location;
		this.radius = radius;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

}
