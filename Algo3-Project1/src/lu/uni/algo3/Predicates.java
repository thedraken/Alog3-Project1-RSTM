package lu.uni.algo3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lu.uni.algo3.Vehicle.Category;

public class Predicates {
	public static Predicate<Vehicle> isCategory(Category c){
		return p -> p.getCategory() == c;
	}
	public static List<Vehicle> filterVehicles(HashSet<Vehicle> vehicles, Predicate<Vehicle> predicate){
		return vehicles.stream().filter(predicate).collect(Collectors.<Vehicle>toList());
	}
	public static Predicate<TollRecord> tollRecordForVehicle(Vehicle v){
		return p -> p.Vehicle() == v;
	}
	public static List<TollRecord> filterTollRecords(ArrayList<TollRecord> trs, Predicate<TollRecord> predicate){
		return trs.stream().filter(predicate).collect(Collectors.<TollRecord>toList());
	}
	public static Predicate<RoadSection> roadSectionByNumber(int number){
		return p -> p.number() == number;
	}
	public static ArrayList<RoadSection> filterRoadSections(ArrayList<RoadSection> arrayList, Predicate<RoadSection> predicate){
		return (ArrayList<RoadSection>) arrayList.stream().filter(predicate).collect(Collectors.<RoadSection>toList());
	}
	public static Predicate<RoadSection> ignoreTheseRoadSections(ArrayList<RoadSection> previousTravelled){
		return p -> !previousTravelled.contains(p);
	}
	public static ArrayList<Thread> filterThreads(List<Thread> listOfCarThreads, Predicate<Thread> predicate){
		return (ArrayList<Thread>) listOfCarThreads.stream().filter(predicate).collect(Collectors.<Thread>toList());
	}
	public static Predicate<Thread> runningThreads(List<Thread> listOfCarThreads){
		return p -> p.isAlive();
	}
}
