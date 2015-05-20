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
	//Get the next random double
	public static double returnRandomDouble(){
		return rnd.nextDouble();
	}
	//Get the next random double within a range
	public static double returnRandomDouble(double min, double max){
		return min + (max-min)*rnd.nextDouble();
	}
	//Get the next random int
	public static int returnRandomInt(){
		return rnd.nextInt();
	}
	//Get the next random int within a range
	public static int returnRandomInt(int min, int max){
		 return rnd.nextInt((max - min) + 1) + min;
	}
	//Return a boolean, depending on the probability passed
	public static boolean returnRandomBoolean(double probability){
		return rnd.nextDouble() <= probability;
	}
	//Convert a hashset to an arraylist. Sometimes easier for random access
	public static List<?> hashSetToArrayList(HashSet<?> hshSt){
		return Arrays.asList(hshSt.toArray());
	}
	//Generates a car number plate, just take random alpha numeric characters
	//http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
	public static String returnRandomLicensePlate(){
		return new BigInteger(50, scrRnd).toString(32);
	}
	//Returns a random vehicle category, can be used when creating vehicles
	private static final List<Category> VALUES = Collections.unmodifiableList(Arrays.asList(Category.values()));
	public static Category randomCategory(){
		return VALUES.get(rnd.nextInt(VALUES.size()));
	}
}
