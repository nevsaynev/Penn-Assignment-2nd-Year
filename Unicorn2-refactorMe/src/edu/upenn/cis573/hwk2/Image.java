package edu.upenn.cis573.hwk2;

import android.graphics.Bitmap;
import android.graphics.Point;

public class Image {
	private Bitmap image;
	private Point imagePoint = new Point(-150, 100);

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public Point getImagePoint() {
		return imagePoint;
	}

	public void setImagePoint(Point imagePoint) {
		this.imagePoint = imagePoint;
	}

	public boolean isWithinBounds(float x, float y) {
		int width = image.getWidth();
		int height = image.getHeight();
		if (x > imagePoint.x && x < imagePoint.x + width && y > imagePoint.y
				&& y < imagePoint.y + height) {
			return true;
		}
		return false;
	}
}
