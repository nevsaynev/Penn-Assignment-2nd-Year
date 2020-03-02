/*
package com.example.danceforhealth;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

public class Workout implements Parcelable {
	private String type;
	private int strain;
	private int heartrate;
	private int steps;
	private int weight;
	private String date;
	private String workoutTime;
	private int time;
	private int likedIdx;
	private int funIdx;
	private int tiredIdx;
	private boolean update = false;
	

	public Workout() {
		type = "";
		strain = 0;
		heartrate = 0;
		steps = 0;
		weight = 0;
		time = 0;
		
		Date date = new Date();

		SimpleDateFormat ft = new SimpleDateFormat ("E M dd yyyy");
		this.date = ft.format(date);
	}

	public Workout(String type, int strain, int hr, int st, int wt) {
		this.type = type;
		this.strain = strain;
		this.heartrate = hr;
		this.steps = st;
		this.weight = wt;
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("E M dd yyyy");
		this.date = ft.format(date);
	}

	public Workout(Parcel p) {
		setType(p.readString());
		setStrain(p.readInt());
		setHeartrate(p.readInt());
		setSteps(p.readInt());
		setWeight(p.readInt());
		setDate(p.readString());
		setWorkoutTime(p.readString());
		setTime(p.readInt());
		setLikedIndex(p.readInt());
		setFunIndex(p.readInt());
		setTiredIndex(p.readInt());
	}

	public String getDate() {
		return date;
	}
	
	public String getWorkoutTime() {
		return workoutTime;	
	}
	
	public boolean getUpdate() {
		return update;
	}
	
	public void setUpdate(boolean b) {
		update = b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + heartrate;
		result = prime * result + steps;
		result = prime * result + strain;
		result = prime * result + time;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + weight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workout other = (Workout) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (heartrate != other.heartrate)
			return false;
		if (steps != other.steps)
			return false;
		if (strain != other.strain)
			return false;
		if (time != other.time)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	public String getType() {
		return type;
	}

	public int getStrain() {
		return strain;
	}

	public int getHR() {
		return heartrate;
	}

	public int getSteps() {
		return steps;
	}

	public int getWeight() {
		return weight;
	}

	public int getHeartrate() {
		return heartrate;
	}
	
	public int getTime() {
		return time;
	}

	public int getLikedIndex() {
		return likedIdx;
	}

	public void setLikedIndex(int r1) {
		this.likedIdx = r1;
	}

	public int getFunIndex() {
		return funIdx;
	}

	public void setFunIndex(int r2) {
		this.funIdx = r2;
	}

	public int getTiredIndex() {
		return tiredIdx;
	}

	public void setTiredIndex(int r3) {
		this.tiredIdx = r3;
	}

	public void setHeartrate(int heartrate) {
		this.heartrate = heartrate;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStrain(int strain) {
		this.strain = strain;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	

	public void setTime(int minutes) {
		this.time = minutes;
	}
	
	public void setWorkoutTime(String workoutTime) {
		this.workoutTime = workoutTime;	
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return (this.getType() + " on \n" + this.getDate());
	}

	public static final Parcelable.Creator<Workout> CREATOR = new Creator<Workout>() {
		@Override
		public Workout createFromParcel(Parcel source) {
			return new Workout(source);
		}

		@Override
		public Workout[] newArray(int size) {
			return new Workout[size];
		}
	};


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(getType());
		parcel.writeInt(getStrain());
		parcel.writeInt(getHR());
		parcel.writeInt(getSteps());
		parcel.writeInt(getWeight());
		parcel.writeString(getDate());
		parcel.writeString(getWorkoutTime());
		parcel.writeInt(getTime());
		parcel.writeInt(getLikedIndex());
		parcel.writeInt(getFunIndex());
		parcel.writeInt(getTiredIndex());
	}

	
}
*/

package com.example.danceforhealth;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

public class Workout implements Parcelable {
	private String databaseObjectID;
	private String type;
	private int strain;
	private int heartrate;
	private int steps;
	private int weight;
	private String workoutdate;
	private String workoutTime;
	//different format of date
	private int week;
	private int day;

	
	private int workingTime;
	private int likedIdx;
	private int funIdx;
	private int tiredIdx;
	private boolean update = false;
	

	public Workout() {
		type = "";
		strain = 0;
		heartrate = 0;
		steps = 0;
		weight = 0;
		workingTime = 0;
		databaseObjectID = "null";
		
		Date date = new Date();

		SimpleDateFormat ft = new SimpleDateFormat ("E M dd yyyy");
		this.workoutdate = ft.format(date);
	}

	public Workout(String type, int strain, int hr, int st, int wt) {
		this.type = type;
		this.strain = strain;
		this.heartrate = hr;
		this.steps = st;
		this.weight = wt;
		
	}

	public Workout(Parcel p) {
		setType(p.readString());
		setStrain(p.readInt());
		setHeartrate(p.readInt());
		setSteps(p.readInt());
		setWeight(p.readInt());
		setWorkoutDate(p.readString());
		setWorkoutTime(p.readString());
		setTime(p.readInt());
		setLikedIndex(p.readInt());
		setFunIndex(p.readInt());
		setTiredIndex(p.readInt());
		setDay(p.readInt());
		setWeek(p.readInt());
		setDatabaseObjectID(p.readString());
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((workoutdate == null) ? 0 : workoutdate.hashCode());
		result = prime * result + heartrate;
		result = prime * result + steps;
		result = prime * result + strain;
		result = prime * result + workingTime;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + weight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workout other = (Workout) obj;
		if (workoutdate == null) {
			if (other.workoutdate != null)
				return false;
		} else if (!workoutdate.equals(other.workoutdate))
			return false;
		if (databaseObjectID != other.databaseObjectID)
			return false;
		if (heartrate != other.heartrate)
			return false;
		if (steps != other.steps)
			return false;
		if (strain != other.strain)
			return false;
		if (workingTime != other.workingTime)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	public String getDatabaseObjectID() {
		return databaseObjectID;
	}

	public void setDatabaseObjectID(String objectID) {
		this.databaseObjectID = objectID;
	}
	
	public String getType() {
		return type;
	}

	public int getStrain() {
		return strain;
	}

	public int getHR() {
		return heartrate;
	}

	public int getSteps() {
		return steps;
	}

	public int getWeight() {
		return weight;
	}

	public int getHeartrate() {
		return heartrate;
	}
	
	public int getTime() {
		return workingTime;
	}

	public int getLikedIndex() {
		return likedIdx;
	}

	public void setLikedIndex(int r1) {
		this.likedIdx = r1;
	}

	public int getFunIndex() {
		return funIdx;
	}

	public void setFunIndex(int r2) {
		this.funIdx = r2;
	}

	public int getTiredIndex() {
		return tiredIdx;
	}

	public void setTiredIndex(int r3) {
		this.tiredIdx = r3;
	}

	public void setHeartrate(int heartrate) {
		this.heartrate = heartrate;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStrain(int strain) {
		this.strain = strain;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	

	public void setTime(int minutes) {
		this.workingTime = minutes;
	}

	public void setWorkoutDate(String date) {
		this.workoutdate = date;
	}
	
	public int getWeek() {
		return week;
	}
		
	public void setWeek(int week) {
		this.week = week;
	}
		
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	public String getDate() {
		return workoutdate;
	}
	
	public boolean getUpdate() {
		return update;
	}
	
	public void setUpdate(boolean b) {
		update = b;
	}

	public String getWorkoutTime() {
		return workoutTime;
	}

	public void setWorkoutTime(String workoutTime) {
		this.workoutTime = workoutTime;
	}

	@Override
	public String toString() {
		return (this.getType() + " on \n" + this.getDate());
	}

	public static final Parcelable.Creator<Workout> CREATOR = new Creator<Workout>() {
		@Override
		public Workout createFromParcel(Parcel source) {
			return new Workout(source);
		}

		@Override
		public Workout[] newArray(int size) {
			return new Workout[size];
		}
	};


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(getType());
		parcel.writeInt(getStrain());
		parcel.writeInt(getHR());
		parcel.writeInt(getSteps());
		parcel.writeInt(getWeight());
		parcel.writeString(getDate());
		parcel.writeString(getWorkoutTime());
		parcel.writeInt(getTime());
		parcel.writeInt(getLikedIndex());
		parcel.writeInt(getFunIndex());
		parcel.writeInt(getTiredIndex());
		parcel.writeInt(getDay());
		parcel.writeInt(getWeek());
		parcel.writeString(getDatabaseObjectID());
	}
}






