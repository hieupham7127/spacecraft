package library;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class IProvider {

	public final Coordinate CELL; // 150 cells in total
	private final Coordinate defaultScreen = new Coordinate(640, 960); // 10 : 15 | 2 : 3
	public Coordinate targetScreen;
	
	private Context context;
	
	public IProvider(Context context) {
		this.context = context;
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		targetScreen = new Coordinate(metrics.widthPixels, metrics.heightPixels);
		CELL = displayProportion(defaultScreen.x / 10, defaultScreen.x / 10);
	}
	
	public Coordinate displayProportion(Coordinate size) { return displayProportion(size.x, size.y); }
	public Coordinate displayProportion(float x, float y) { return Coordinate.multiply(new Coordinate(x, y), Coordinate.divide(targetScreen,defaultScreen)); }
	public float scaleWidth(float x) { return targetScreen.x / defaultScreen.x * x; }
	public float scaleHeight(float y) { return targetScreen.y / defaultScreen.y * y; }
	
	// [Data]
	private static final long MEGABYTE = 1024L * 1024L;
	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}
	public static void DataUsage() {
		  // Get the Java runtime
	    Runtime runtime = Runtime.getRuntime();
	    // Run the garbage collector
	    runtime.gc();
	    // Calculate the used memory
	    long memory = runtime.totalMemory() - runtime.freeMemory();
	    System.out.println("Used memory is bytes: " + memory);
	    System.out.println("Used memory is megabytes: "
	        + bytesToMegabytes(memory));
	}
	
}
