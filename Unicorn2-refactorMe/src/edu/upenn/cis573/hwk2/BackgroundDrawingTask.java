package edu.upenn.cis573.hwk2;

import android.os.AsyncTask;

/*
 * This  class is responsible for making the unicorn appear to move.
 * When "exec" is called on an object of this class, "doInBackground" gets
 * called in a background thread. It just waits 10ms and then updates the
 * image's position. Then "onPostExecute" is called.
 */
class BackgroundDrawingTask extends AsyncTask<Integer, Void, Integer> {
	Image image;

	long endTime;
	GameView view;

	public BackgroundDrawingTask(Image image, GameView view) {
		super();
		this.image = image;

		this.view = view;
	}

	// this method gets run in the background
	protected Integer doInBackground(Integer... args) {
		try {
			// note: you can change these values to make the unicorn go
			// faster/slower
			Thread.sleep(10);
			image.getImagePoint().x += 10;
			image.getImagePoint().y += view.getyChange();
		} catch (Exception e) {
		}
		// the return value is passed to "onPostExecute" but isn't actually
		// used here
		return 1;
	}

	// this method gets run in the UI thread
	protected void onPostExecute(Integer result) {
		// redraw the View
		view.invalidate();
		if (view.getScore() < 10) {
			// need to start a new thread to make the unicorn keep moving
			BackgroundDrawingTask task = new BackgroundDrawingTask(image, view);
			task.execute();
		} else {
			// game over, man!
			endTime = System.currentTimeMillis();
			// these methods are deprecated but it's okay to use them...
			// probably.
			GameActivity.instance.removeDialog(1);
			GameActivity.instance.showDialog(1);
		}
	}
}
