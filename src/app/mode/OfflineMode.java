package app.mode;

import java.util.ArrayList;

import library.Coordinate;
import library.IAnimation;
import library.IBitmap;
import library.ISprite;
import library.IThread;
import library.SynchronizedQueue;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import app.activities.GameActivity;
import app.sprite.BackGround;
import app.sprite.Bonus;
import app.sprite.Bullet;
import app.sprite.Meteorite;
import app.sprite.Spaceship;
import app.view.MapView;

public class OfflineMode {
	private MapView mapview;
	private Context context;
	private boolean isPaused = false;

	private ArrayList<ISprite> motions = new ArrayList<ISprite>();
	private ArrayList<IThread> threads = new ArrayList<IThread>();
	private SynchronizedQueue<Object> queue = new SynchronizedQueue(100);

	public long score = 0;
	int level;

	public OfflineMode(MapView mapview, int level, Coordinate panel) {
		this.mapview = mapview;
		this.context = mapview.getContext();
		this.level = level;

		motions.add(new BackGround(context));
		motions.add(new Spaceship(context, new Coordinate(panel.x / 2, panel.y * 3 / 4), "normal", 3));
		
		threads.add(new FrameThread(queue, mapview.getHolder()));
		threads.add(new BulletThread(queue, 200));
		threads.add(new MeteoriteThread(queue, 2000));
		threads.add(new BonusThread(queue, 2000));
	}
	
	public void onPause() { for (IThread thread : threads) thread.Pause(); }
	public void onResume() { for (IThread thread : threads) thread.Resume(); }
	public void onStop() { for (IThread thread : threads) thread.Stop(); }
	public boolean isOnPause() { return threads.get(0).isPaused(); }

	public void setLevel(int level) { this.level = level; }
	public Spaceship getSpaceship() { return (Spaceship) motions.get(1); }
	public long getScore() { return score; }
		

	// ------- FrameThread -------
	class FrameThread extends IThread {
		private static final long FPS = 60;
		private static final long MAX_SKIPPED = 5;
		private static final long FRAME_PERIOD = 1000 / FPS;
		private SurfaceHolder holder; 	
		private Canvas canvas = null;
		
		public FrameThread(SynchronizedQueue<Object> queue, SurfaceHolder holder) { 
			super(queue);
			this.holder = holder;
		}

		void physics() { 
			// moving 
			for (ISprite motion : motions) motion.moving();
			
			// checking collisions
			for (int i = 1; i < motions.size() - 1; i++)
				for (int j = i + 1; j < motions.size(); j++) 
					if (motions.get(i).isAlive() && motions.get(j).isAlive() && motions.get(i).isOverlapped(motions.get(j))) {
						score += motions.get(i).Collide(motions.get(j));
						score += motions.get(j).Collide(motions.get(i));
						break;
					}
			// removing all invalid and dead motions 
			for (int i = motions.size() - 1; i > 1; i--) 
				if (!motions.get(i).isDestinationValid() || !motions.get(i).isAlive()) {
					//IBitmap.recyclingBitmap(motions.get(i).getBitmap());
					motions.remove(i);
				}
		}
		
		void draw() {
			//canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			for (ISprite motion : motions) motion.draw(canvas);
			mapview.updateScore();
			if (((Spaceship) motions.get(1)).getLife() < 1) { 
				Paint paint = new Paint();
				paint.setColor(Color.RED);
				paint.setTextSize(50);
				for (int i = 0; i < 20; i++) 
					canvas.drawText("Game Over", (int) (Math.random() * canvas.getWidth()), (int) (Math.random() * canvas.getHeight()), paint);
				onPause();
			}
		}
		
		@Override
		protected void tasks() throws InterruptedException {
			long startTime = System.currentTimeMillis();
			long framesSkipped = 0;
			
			canvas = holder.lockCanvas();
			final int count = queue.size();
			for (int i = 0; i < count; i++) motions.add((ISprite) queue.remove());
			if (canvas != null) {
				//this.animation();
				synchronized(holder) { this.draw(); }
				holder.unlockCanvasAndPost(canvas);
			}
			
			time = FRAME_PERIOD - (System.currentTimeMillis() - startTime);
			while (time < 0 && framesSkipped < MAX_SKIPPED) {
				this.physics();
				time += FRAME_PERIOD;
				framesSkipped++;
			}
				
		}
	}
	
	// ------- BulletThread -------	
	class BulletThread extends IThread {
		BulletThread(SynchronizedQueue<Object> queue, long millis) { super(queue, millis); }
		@Override
		protected void tasks() throws InterruptedException {
			for (Bullet bullet : getSpaceship().getBullets()) 
				if (bullet.valid()) {
					Bullet new_bullet = new Bullet(context, "A");
					new_bullet.setBitmap(bullet.getBitmap());
					new_bullet.setPosition(bullet.getPosition());
					new_bullet.setVelocity(bullet.getVelocity());
					queue.add(new_bullet);
				}
		}
	}
	
	// ------- MeteoriteThread ------- 
	class MeteoriteThread extends IThread {
		final int BIG_METEORITE = 1;
		private int count = 0;
		MeteoriteThread(SynchronizedQueue<Object> queue, long millis) { super(queue, millis); }
		@Override protected void tasks() throws InterruptedException { 
			queue.add(new Meteorite(context, "small"));
			count = (count + 1) % BIG_METEORITE;
			if (count == 0) queue.add(new Meteorite(context, "big"));
		}
	}	

	// ------- Bonus -------
	class BonusThread extends IThread {
		BonusThread(SynchronizedQueue<Object> queue, long millis) { super(queue, millis); }
		@Override protected void tasks() throws InterruptedException { queue.add(new Bonus(context)); }
	}	
}
