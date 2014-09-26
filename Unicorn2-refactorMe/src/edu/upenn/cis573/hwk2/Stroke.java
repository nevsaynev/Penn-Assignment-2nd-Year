package edu.upenn.cis573.hwk2;

import java.util.ArrayList;

import android.graphics.Point;

public class Stroke {
	private int lineColor;
	private int lineWidth;
	private ArrayList<Point> points = new ArrayList<Point>();

	public Stroke(int lineColor, int lineWidth) {
		this.lineColor = lineColor;
		this.lineWidth = lineWidth;
	}
	
	public int getLineColor() {
		return lineColor;
	}


	public int getLineWidth() {
		return lineWidth;
	}


	public ArrayList<Point> getPoints() {
		return points;
	}




	
	
}
