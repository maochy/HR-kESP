package method;

import java.util.ArrayList;

import model.BaseStation;
import model.ConstNum;
import model.EdgeUser;
import model.Result;
import utils.AlgorithmUtils;
import utils.TestUtils;

public class Main {

	public static void main(String[] args) {
		// read data set
		String metroBaseStationFile = "data/CBD/melb_cbd_station.csv";
		String metroUserFile = "data/CBD/melb_cbd_user.csv";
		ArrayList<BaseStation> metroBaseStations = new ArrayList<>();
		metroBaseStations = TestUtils.readBaseStationData(metroBaseStationFile);
		ArrayList<EdgeUser> metroUsers = new ArrayList<>();
		metroUsers = TestUtils.readUserData(metroUserFile);

		ConstNum.nBaseStation = 20;
		ConstNum.nServer = 4;
		ConstNum.nUser = 80;
		ConstNum.faultRate = 0.5;

		// randomly generate data set
		BaseStation[] subBaseStations = TestUtils.generateBSList(metroBaseStations);
		subBaseStations = TestUtils.setRadius(subBaseStations);
		EdgeUser[] subUsers = TestUtils.generateUserList(metroUsers);
		ArrayList<Integer>[] BSGraph = TestUtils.generateBSGraph(subBaseStations);
		ArrayList<Integer>[] access = AlgorithmUtils.calculateAccessibility(subBaseStations, subUsers);
		int[][] weight = AlgorithmUtils.calculateWeightMatrix(access);

		// solve the edge server placement scheme
		Result result_0 = HRkESP_0.execute(BSGraph, access, weight);
		Result result_1 = HRkESP_1.execute(BSGraph, access, weight);

		// randomly robustness test
		double USRate_0 = TestUtils.robustnessTest(result_0, ConstNum.faultRate, access);
		double USRate_1 = TestUtils.robustnessTest(result_1, ConstNum.faultRate, access);
		
		System.out.println("HR-kESP_0\t" + result_0.toString() + "\t" + USRate_0);
		System.out.println("HR-kESP_1\t" + result_1.toString() + "\t" + USRate_1);

	}
}
