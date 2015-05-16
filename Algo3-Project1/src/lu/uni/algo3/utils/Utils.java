package lu.uni.algo3.utils;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.math.BigInteger;
import java.security.SecureRandom;

import lu.uni.algo3.Vehicle.Category;

public final class Utils {
	private Utils(){
		
	}
	private static SecureRandom scrRnd = new SecureRandom();
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
	public static boolean returnRandomBoolean(double probability){
		return rnd.nextDouble() <= probability;
	}
	public static List<?> hashSetToArrayList(HashSet<?> hshSt){
		return Arrays.asList(hshSt.toArray());
	}
	//http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
	public static String returnRandomLicensePlate(){
		return new BigInteger(50, scrRnd).toString(32);
	}
	private static final List<Category> VALUES = Collections.unmodifiableList(Arrays.asList(Category.values()));
	public static Category randomCategory(){
		return VALUES.get(rnd.nextInt(VALUES.size()));
	}
}
