package utils;

import java.util.ArrayList;

import model.BaseStation;
import model.EdgeUser;
import model.Location;

public class AlgorithmUtils {
	
	/**
	 * @param BSList	The info of base stations
	 * @param UserList	The info of users
	 * @return	The access relationship between base stations and users
	 */
	public static ArrayList<Integer>[] calculateAccessibility(BaseStation[] BSList, EdgeUser[] UserList) {
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] access = new ArrayList[BSList.length];
		for (int i = 0; i < BSList.length; i++) {
			ArrayList<Integer> nearUserKey = new ArrayList<Integer>();
			for (int j = 0; j < UserList.length; j++) {
				if (Location.getDistance(BSList[i].getLocation(), UserList[j].getLocation()) < BSList[i].getRadius()) {
					nearUserKey.add(j);
				}
			}
			access[i] = nearUserKey;
		}
		return access;
	}

	/**
	 * @param access	The info of access relationship
	 * @return	The weight matrix of the base station graph
	 */
	public static int[][] calculateWeightMatrix(ArrayList<Integer>[] access) {
		int BSNum = access.length;
		int[][] weight = new int[BSNum][BSNum];
		for (int i = 0; i < BSNum; i++) {
			for (int j = i; j < BSNum; j++) {
				int count = 0;
				if (i != j) {
					for (Integer id : access[i]) {
						if (access[j].contains(id)) {
							count++;
						}
					}
					weight[i][j] = count;
					weight[j][i] = count;
				}else {
					weight[i][j] = access[i].size();
				}
			}
		}
		return weight;
	}
}
