package model;

public class ConstNum {
	public static int nBaseStation = 100;
	public static int nServer = 3;
	public static int nUser = 2048;
	public static double faultRate = 0.20; //(0.2-0.9)
	
	public static BaseStation[] BSList;
	public static EdgeUser[] UserList;
	

	public static int initSolutionNum = 3;
	public static int partSolutionNum = 3;
	
	public static final double EARTH_RADIUS = 6378137;
	
}
