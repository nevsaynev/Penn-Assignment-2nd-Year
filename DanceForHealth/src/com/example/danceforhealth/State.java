package com.example.danceforhealth;


//singleton 
public class State  {
	private int weightLoss;
	private int level;
	private int workingTime;
	private int workingWeeks;

	private static State singletonInstance = null;

	private State() {
		this.weightLoss = 0;
		this.level = 1;
		this.workingTime = 0;
		this.workingWeeks = 0;
	}

	public int getLostWeight() {
		return weightLoss;
	}

	public void setLostWeight(int lostWeight) {
		this.weightLoss = lostWeight;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	

	public int getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(int workingTime) {
		this.workingTime = workingTime;
	}

	public int getWorkingWeeks() {
		return workingWeeks;
	}

	public void setWorkingWeeks(int workingWeeks) {
		this.workingWeeks = workingWeeks;
	}

	public static State getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new State();
		}
		return singletonInstance;
	}

	
}