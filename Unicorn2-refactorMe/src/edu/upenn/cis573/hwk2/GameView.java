package edu.upenn.cis573.hwk2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class GameView extends View {

	private Stroke stroke = new Stroke(Color.RED, 10);
	private Image image = new Image();

	private boolean killed = false;
	private boolean newUnicorn = true;
	private int score = 0;
  

	private int yChange = 0;
	
    private BackgroundDrawingTask backGroundDrawingTask = new BackgroundDrawingTask(image, this);

	public GameView(Context context) {
		super(context);
		setBackgroundResource(R.drawable.space);
		setImage(R.drawable.unicorn);

	}

	public GameView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setBackgroundResource(R.drawable.space);
		setImage(R.drawable.unicorn);
	}

	private void setImage(int pic) {
		image.setImage(BitmapFactory.decodeResource(getResources(), pic));
		image.setImage(Bitmap.createScaledBitmap(image.getImage(), 150, 150,
				false));
	}
	
	public void backGroundDraw(){
		backGroundDrawingTask.execute();
	}

	/*
	 * This method is automatically invoked when the View is displayed. It is
	 * also called after you call "invalidate" on this object.
	 */
	protected void onDraw(Canvas canvas) {

		// resets the position of the unicorn if one is killed or reaches the
		// right edge
		if (newUnicorn || image.getImagePoint().x >= this.getWidth()) {
			image.getImagePoint().set(-150, (int) (Math.random() * 200 + 200));
			yChange = (int) (10 - Math.random() * 20);
			newUnicorn = false;
			killed = false;
		}

		// show the exploding image when the unicorn is killed
		if (killed) {
			setImage(R.drawable.explosion);
			drawOnCanvas(canvas);
			newUnicorn = true;
			setImage(R.drawable.unicorn);
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
			invalidate();
			return;
		}

		drawOnCanvas(canvas);

		// draws the stroke
		if (stroke.getPoints().size() > 1) {
			for (int i = 0; i < stroke.getPoints().size() - 1; i++) {
				Point start = stroke.getPoints().get(i);
				Point stop = stroke.getPoints().get(i + 1);
				Paint paint = new Paint();
				paint.setColor(stroke.getLineColor());
				paint.setStrokeWidth(stroke.getLineWidth());
				canvas.drawLine(start.x, start.y, stop.x, stop.y, paint);
			}
		}

	}

	

	/*
	 * This method is automatically called when the user touches the screen.
	 */
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			addNewPoint(event);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			addNewPoint(event);

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			stroke.getPoints().clear();
		} else {
			return false;
		}

		// see if the point is within the boundary of the image

		float x = event.getX();
		float y = event.getY();
		// the !killed thing here is to prevent a "double-kill" that could occur
		// while the "explosion" image is being shown
		if (!killed && image.isWithinBounds(x, y)) {
			killed = true;
			score++;
			((TextView) (GameActivity.instance.getScoreboard())).setText(""
					+ score);
		}

		// forces a redraw of the View
		invalidate();

		return true;
	}

	private void drawOnCanvas(Canvas canvas) {
		canvas.drawBitmap(image.getImage(), image.getImagePoint().x,
				image.getImagePoint().y, null);
	}
	
	private void addNewPoint(MotionEvent event) {
		stroke.getPoints().add(
				new Point((int) event.getX(), (int) event.getY()));
	}
  
	
	  public int getScore() {
			return score;
		}

		public int getyChange() {
			return yChange;
		}
	

}
