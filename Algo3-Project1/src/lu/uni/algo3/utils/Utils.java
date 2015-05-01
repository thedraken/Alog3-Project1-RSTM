package lu.uni.algo3.utils;
import java.util.Random;

public final class Utils {
	private Utils(){
		
	}
	private static Random rnd = new Random();
	public static double returnRandomDouble(){
		return rnd.nextDouble();
	}
	public static double returnRandomDouble(double min, double max){
		return min + (max-min)*rnd.nextDouble();
	}
	public static int returnRandomInt(){
		return rnd.nextInt();
	}
	public static int returnRandomInt(int min, int max){
		 return rnd.nextInt((max - min) + 1) + min;
	}
}
