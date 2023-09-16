package model;

import java.util.ArrayList;

public class Result {
	private double totalCR_CP = 0;
	private int totalCoverage = 0;
	private int totalRobustness = 0;
	private int[] decision = new int[ConstNum.nBaseStation];


	public Result() {}

	public Result(Result result) {
		this.decision = result.decision;
		this.totalCoverage = result.totalCoverage;
		this.totalRobustness = result.totalRobustness;
		this.totalCR_CP = result.totalCR_CP;
	}

	/**
	 * @return the totalCoverage
	 */
	public int getTotalCoverage() {
		return totalCoverage;
	}

	/**
	 * @return the totalRobustness
	 */
	public int getTotalRobustness() {
		return totalRobustness;
	}

	/**
	 * @return totalCR_CP
	 */
	public double getTotalCR_CP() {
		return totalCR_CP;
	}

	public void setTotalCR_CP(int intValue) {
		this.totalCR_CP = intValue;
	}

	/**
	 * @return the edge server placement scheme
	 */
	public int[] getDecision() {
		return decision;
	}

	public void setDecision(int[] decision) {
		this.decision = decision;
	}

	public void calculateCoverage(ArrayList<Integer>[] access) {
		ArrayList<Integer> users = new ArrayList<>();
		for (int i = 0; i < ConstNum.nBaseStation; i++) {
			if (this.decision[i] == 0)
				continue;
			for (int j = 0; j < access[i].size(); j++) {
				if (users.contains(access[i].get(j)))
					continue;
				users.add(access[i].get(j));
			}
		}
		this.totalCoverage = users.size();
	}

	public void calculateRobustness(int[][] weight) {
		int r = 0;
		for (int i = 0; i < ConstNum.nBaseStation; i++) {
			if (this.decision[i] == 0)
				continue;
			for (int j = i + 1; j < ConstNum.nBaseStation; j++) {
				r += weight[i][j] * this.decision[j];
			}
		}
		this.totalRobustness = r;
	}

	public void calculateTotalCR_CP() {
		double tmp = (totalCoverage * totalCoverage) + (totalRobustness * totalRobustness);
		this.totalCR_CP = Math.sqrt(tmp);
	}

	@Override
	public String toString() {
		return "Obj:"+ totalCR_CP + "\tCoverage:" + totalCoverage + "\tRobustness:" + totalRobustness;
	}

}
