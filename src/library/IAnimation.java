package library;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.os.SystemClock;

public class IAnimation {
	
	//private static ArrayList<SynchronizedQueue> lists = new ArrayList<SynchronizedQueue>();
	private static SynchronizedQueue queue = new SynchronizedQueue(1);
	
	public static void moving(final ISprite motion, final String id, Coordinate destination, final float durationOfAnimation) {
		if (queue.size() > 0) return;
		final Coordinate pos = motion.pos;
	    final Coordinate distance = Coordinate.substract(destination, motion.pos);
	    IThread run = new IThread(queue) {
			@Override
			protected void tasks() throws InterruptedException {
				long startTime = SystemClock.elapsedRealtime();
			    long currentTime;
			    float elapsedRatio = 0;
			    while (elapsedRatio < 1) {
			    	motion.setPosition(Coordinate.add(pos, Coordinate.multiply(distance, elapsedRatio)));
			        currentTime = SystemClock.elapsedRealtime();
			        elapsedRatio = (currentTime - startTime) / durationOfAnimation;
			    }
		    	motion.setPosition(Coordinate.add(pos, distance));
		    	motion.toString();
		        Stop();
			}
	    	
	    };
	}
	
	public static void rotation(final ISprite motion, final String id, final int degrees, final float durationOfAnimation) {
		if (queue.size() > 0) return;
	    final Bitmap bitmap = motion.bitmap;
	    
	    IThread run = new IThread(queue) {
			@Override
			protected void tasks() throws InterruptedException {
				long startTime = SystemClock.elapsedRealtime();
			    long currentTime;
			    float elapsedRatio = 0;
			    
			    while (elapsedRatio < 1) {
			    	motion.bitmap = IBitmap.rotate(bitmap, elapsedRatio * degrees);
			        currentTime = SystemClock.elapsedRealtime();
			        elapsedRatio = (currentTime - startTime) / durationOfAnimation;
			    }
		        motion.bitmap = IBitmap.rotate(bitmap, degrees);		
		        Stop();
			}
	    	
	    };
        
	    // As elapsed ratio will never exactly equal 1, you have to manually draw the last frame
	    // draw the canvas again here as before
	    // And you can now set whatever other notification or action you wanted to do at the end of your animation
	}
	
	public static void fadeInOut(final ISprite motion, final String id, final float durationOfAnimation) {
	    /*SynchronizedQueue new_queue = null;
		for (SynchronizedQueue queue : lists)
			if (queue.getName().equals(id)) new_queue = queue;
		if (new_queue == null) {
			new_queue = new SynchronizedQueue(id, 1);
		    lists.add(new_queue);
		}*/
		if (queue.size() > 0) return;
		final Bitmap bitmap = motion.getBitmap();
	    final Bitmap fade_bitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_4444);
	    for (int i = 0; i < bitmap.getWidth(); i++) 
			for (int j = 0; j < bitmap.getHeight(); j++) 
				fade_bitmap.setPixel(i, j, Color.TRANSPARENT);
	    
		IThread run = new IThread(queue) {
			@Override
			protected void tasks() throws InterruptedException {
				queue.add(id);
			    long startTime = SystemClock.elapsedRealtime();
			    long currentTime;
			    float elapsedRatio = 0;
			    int frequency = 0;
			    
			    while (elapsedRatio < 1) {
			    	motion.bitmap = (frequency++ % 2 == 0) ? bitmap : fade_bitmap;
			    	currentTime = SystemClock.elapsedRealtime();
			    	elapsedRatio = (currentTime - startTime) / durationOfAnimation;
			    }
		        motion.bitmap = bitmap;
			    Stop();
				queue.remove();
			}
		};

		// put a stop to the next thread if current thread still works or else ConcurrentModificationException will happen
	}

}
