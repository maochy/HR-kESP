package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import model.BaseStation;
import model.ConstNum;
import model.EdgeUser;
import model.Location;
import model.Result;

public class TestUtils {

	/**
	 * @param path		Read user data from file path.csv 
	 * @return	The users info
	 */
	public static ArrayList<EdgeUser> readUserData(String path) {
        ArrayList<EdgeUser> users= new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] items = line.split(",");
                EdgeUser u = new EdgeUser();
                u.getLocation().setLat(Double.parseDouble(items[0]));
                u.getLocation().setLng(Double.parseDouble(items[1]));
                users.add(u);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
	
	/**
	 * @param users		The total user info
	 * @return Randomly sampled user info according to @param ConstNum.nUser
	 */
	public static EdgeUser[] generateUserList(ArrayList<EdgeUser> users) {
        EdgeUser[] UserList = new EdgeUser[ConstNum.nUser];
        boolean[] flag = new boolean[ConstNum.nUser];
        for (int i = 0; i < ConstNum.nUser; i++) {
            int rand = (int)(Math.random() * users.size());
            if(flag[i]) {
            	i--;
            	continue;
            }else {
                UserList[i] = users.get(rand);
            	flag[i]=true;
			}
        }
        return UserList;
    }
	
	/**
	 * @param path		Read base station data from file path.csv 
	 * @return	The base stations info
	 */
	public static ArrayList<BaseStation> readBaseStationData(String path) {
        ArrayList<BaseStation> baseStations= new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] items = line.split(",");
                BaseStation bs = new BaseStation();
                bs.getLocation().setLat(Double.parseDouble(items[0]));
                bs.getLocation().setLng(Double.parseDouble(items[1]));
                baseStations.add(bs);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseStations;
    }

	/**
	 * @param baseStations		The total base station info
	 * @return Randomly sampled base station info according to @param ConstNum.nBaseStation
	 */
    public static BaseStation[] generateBSList(ArrayList<BaseStation> baseStations) {
        BaseStation[] BSList = new BaseStation[ConstNum.nBaseStation];
        boolean[] flag = new boolean[ConstNum.nBaseStation];
        for (int i = 0; i < ConstNum.nBaseStation; i++) {
            int rand = (int)(Math.random()*baseStations.size());
            if(flag[i]) {
            	i--;
                continue;
            }else {
            	BSList[i] = baseStations.get(rand);
            	flag[i]=true;
			}
        }
        return BSList;
    }
    
    /**
     * Randomly set the coverage radius of the base station is set randomly
     * @param baseStations		The sampled base station
     * @return	The sampled base station after setting radius
     */
    public static BaseStation[] setRadius(BaseStation[] baseStations) {
    	for(BaseStation bs:baseStations) 
    		bs.setRadius(450 + (int)(Math.random() * 300));
    	return baseStations;
    }

    /**
     * Construct the accessing relationship between base stations
     * @param BSList	The base station info
     * @return	The base station topology network
     */
    public static ArrayList<Integer>[] generateBSGraph(BaseStation[] BSList) {
        @SuppressWarnings("unchecked")
		ArrayList<Integer>[] BSGraph = new ArrayList[ConstNum.nBaseStation];
        for (int i = 0; i < ConstNum.nBaseStation; i++) {
            ArrayList<Integer> nearBSKey = new ArrayList<Integer>();
            for (int j = 0; j < ConstNum.nBaseStation; j++) {
                if (Location.getDistance(BSList[i].getLocation(), BSList[j].getLocation()) < BSList[i].getRadius() && i != j) {
                    nearBSKey.add(j);
                }
            }
            BSGraph[i] = nearBSKey;
        }
        return BSGraph;
    }
    
    /**
     * @param result	The results of the algorithm include the edge server placement scheme
     * @param alpha		The proportion of server failure
     * @param access	The accessing relationship between users and base stations
     * @return	The user survival rate
     */
    public static double robustnessTest(Result result, double alpha, ArrayList<Integer>[] access) {
		ArrayList<Integer> servers = new ArrayList<>();
		ArrayList<Integer> usersBefore = new ArrayList<>();
		
		// 1. Find all servers corresponds to the base station
		int[] decision = result.getDecision();
		for (int i = 0; i < decision.length; i++) {
			if (decision[i] == 1)
				servers.add(i);
		}
		
		// 2. Record the covered users
		for (Integer serverID : servers) {
			for (Integer userID : access[serverID]) {
				if(usersBefore.contains(userID)) continue;
				usersBefore.add(userID);
			}
		}
		
		// 3. Randomly shut down servers according to the set ratio 
		int serverNum = servers.size();
		int falutNum = (int) (serverNum * alpha);
		falutNum = falutNum > 0 ? falutNum : 1;
		ArrayList<Integer> falutServers = new ArrayList<>();
		while (falutServers.size() != falutNum) {
			int randIndex = (int)(Math.random() * servers.size());
			falutServers.add(servers.get(randIndex));
			servers.remove(randIndex);
		}

		// 4. Record the covered users after a random failure
		ArrayList<Integer> usersAfter = new ArrayList<>();
		for (Integer serverID : servers) {
			for (Integer userID : access[serverID]) {
				if(usersAfter.contains(userID)) continue;
				usersAfter.add(userID);
			}
		}
		
		// 5. Calculate the user survival rate
		return 1.0 * usersAfter.size()/usersBefore.size();
	}
}
