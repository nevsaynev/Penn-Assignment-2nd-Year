package com.example.danceforhealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;


public class Util {
	// calculates the level that the person is currently at by their time
		public int calculateTotalWorkingTime() {
			PrevWorkout workouts = PrevWorkout.getInstance();
			if(workouts.size() == 0) {
				return 0;
			}
			int sum = 0;
			for (Workout w : workouts.getPrevious()) {
				Log.v("fixbug12", w.toString());
				sum += w.getTime();
			}
			return sum;
		}

		// calculates the level the person is currently at by their weight loss
		public int calculateWeightLoss() {
			PrevWorkout workouts = PrevWorkout.getInstance();
			if(workouts.size()==0) {
				return 0;
			}
			int startWeight = workouts.getPrevious().get(0).getWeight();
			int finishWeight = workouts.getPrevious().get(workouts.size() - 1).getWeight();
			int weightLoss = finishWeight - startWeight;
			return weightLoss;
		}
		
		//calculates what achievement the person has got based on how long he/she has been keeping working out
		public int calculateContinuousWorkingWeeks() {
			PrevWorkout workouts = PrevWorkout.getInstance();
			if (workouts.size() == 0) {
				return 0;
			}		
			Collections.sort(workouts.getPrevious(), new CustomComparator());

			
			int count = 1;
			for(int i = 0; i<workouts.size()-1;i++ ){
				//descending order
				Workout curr = workouts.getPrevious().get(i);
				Workout next = workouts.getPrevious().get(i+1);
				Log.v("workingweeks", ""+curr.getWeek());
				
				if(curr.getWeek() == next.getWeek()+1) count++;
				else if(curr.getWeek()==next.getWeek()) continue;
				else if(curr.getWeek()==1 &&(next.getWeek()==52||next.getWeek()==53)) count++;
				else{
					break;
				}
				
			}
		
		
			return count;
		}
		 		
		
		public class CustomComparator implements Comparator<Workout> {
			@Override
			public int compare(Workout o1, Workout o2) {
				String str1 = o1.getDate();
				String str2 = o2.getDate();
				Date date1 = new Date();
				Date date2 = new Date();
				
				try {
					Log.v("dateParse", "enter");
					date1 = new SimpleDateFormat("E  MMM/dd/yyyy",Locale.ENGLISH).parse(str1);
					Log.v("dateParse", date1.toString()+"only null");
					date2 = new SimpleDateFormat("E  MMM/dd/yyyy",Locale.ENGLISH).parse(str2);
					Log.v("dateParse", date2.toString()+"only null");
				
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return date1.compareTo(date2) > 0 ? -1 : 1;
			}
		}
		
		
	
}
