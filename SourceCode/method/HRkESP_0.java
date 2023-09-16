package method;

import java.util.ArrayList;

import model.ConstNum;
import model.Result;

public class HRkESP_0 {
	public static Result execute(ArrayList<Integer>[] BSGraph, ArrayList<Integer>[] access, int[][] weight) {
		ArrayList<Integer> servers = new ArrayList<>();
		// 1. sort the base station according to the robustness.
		ArrayList<Integer> sortedRobustness = new ArrayList<>();
		ArrayList<Integer> BSWeight = new ArrayList<>();
		for (int i = 0; i < weight.length; i++) {
			int robust = 0;
			for (int j = 0; j < weight[i].length; j++) {
				if (i == j)
					continue;
				robust += weight[i][j];
			}

			int j = 0;
			for (; j < sortedRobustness.size(); j++) {
				if (robust > BSWeight.get(j))
					break;
			}
			sortedRobustness.add(j, (Integer) i);
			BSWeight.add(j, (Integer) robust);
		}

		// Select the most robustness base station as the initial server.
		int initBSID = sortedRobustness.get(0);
		servers.add(initBSID);

		// 2. Record the neighbors of selected base station(server).
		ArrayList<Integer> nearBaseStaions = new ArrayList<>();
		nearBaseStaions.addAll(BSGraph[initBSID]);
		while (servers.size() < ConstNum.nServer) {

			// 3. Sort the neighbors(base station) according to the robustness.
			nearBaseStaions = sortedRobustness(servers, nearBaseStaions, weight, BSGraph);

			// 4. Find two candidate base stations from the neighboring base station set and other base stations respectively.
			int nextServerID = -1;
			if (nearBaseStaions.isEmpty()) {
				// If there is no neighboring base station, the base station with the highest robustness is
				// directly selected as the next server deployment location.
				for (int id : sortedRobustness) {
					if (!servers.contains(id)) {
						nextServerID = id;
						break;
					}
				}
			} else {
				// Select the highest robustness base station as next server deployment loction
				// from the neighboring base stations.
				nextServerID = nearBaseStaions.get(0);
				nearBaseStaions.remove(0);
			}
			// 5. Add a new server into ESP scheme and update the neighboring base station set
			servers.add(nextServerID);
			for (Integer id : BSGraph[nextServerID]) {
				if (!nearBaseStaions.contains(id) && !servers.contains(id)) {
					nearBaseStaions.add(id);
				}
			}
		}

		// 6. Repeat steps 3-5 until the number of servers reaches k
		// calculate the value of objectives
		Result result = new Result();
		for (int i = 0; i < servers.size(); i++) {
			result.getDecision()[servers.get(i)] = 1;
		}
		result.calculateCoverage(access);
		result.calculateRobustness(weight);
		result.calculateTotalCR_CP();

		return result;
	}

	/**
	 * Calculate the robustness value of the neighboring base station with respect to the current server placement scheme
	 * @param servers		The current server placement scheme
	 * @param nearBaseStaions	The index of neighboring base stations
	 * @param weight		The weight matrix of the base station graph
	 * @param BSGraph		The base station topology network
	 * @return
	 */
	private static ArrayList<Integer> sortedRobustness(ArrayList<Integer> servers, ArrayList<Integer> nearBaseStaions,
			int[][] weight, ArrayList<Integer>[] BSGraph) {
		ArrayList<Integer> sortedNearBSs = new ArrayList<>();
		ArrayList<Integer> sortedRobustness = new ArrayList<>();
		for (int i = 0; i < nearBaseStaions.size(); i++) {
			// Calculate the robustness sum of each base station
			int robust = 0;
			for (int j = 0; j < servers.size(); j++) {
				if (BSGraph[nearBaseStaions.get(i)].contains(servers.get(j))) {
					robust += weight[nearBaseStaions.get(i)][servers.get(j)];
				}
			}
			// Insertion sort descending
			int j = 0;
			for (; j < sortedNearBSs.size(); j++) {
				if (robust > sortedRobustness.get(j))
					break;
			}
			sortedRobustness.add(j, (Integer) robust);
			sortedNearBSs.add(j, (Integer) nearBaseStaions.get(i));
		}

		return sortedNearBSs;
	}
	
}
