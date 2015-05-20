package lu.uni.algo3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lu.uni.algo3.Vehicle.Category;

public class Predicates {
	//Returns a vehicle depending on it's category
	public static Predicate<Vehicle> isCategory(Category c){
		return p -> p.getCategory() == c;
	}
	public static List<Vehicle> filterVehicles(HashSet<Vehicle> vehicles, Predicate<Vehicle> predicate){
		return vehicles.stream().filter(predicate).collect(Collectors.<Vehicle>toList());
	}
	//Returns a tollrecord that is for a certain vehicle
	public static Predicate<TollRecord> tollRecordForVehicle(Vehicle v){
		return p -> p.Vehicle() == v;
	}
	public static List<TollRecord> filterTollRecords(ArrayList<TollRecord> trs, Predicate<TollRecord> predicate){
		return trs.stream().filter(predicate).collect(Collectors.<TollRecord>toList());
	}
	//Select a roadsection by number predicate
	public static Predicate<RoadSection> roadSectionByNumber(int number){
		return p -> p.number() == number;
	}
	public static ArrayList<RoadSection> filterRoadSections(ArrayList<RoadSection> arrayList, Predicate<RoadSection> predicate){
		return (ArrayList<RoadSection>) arrayList.stream().filter(predicate).collect(Collectors.<RoadSection>toList());
	}
	//A predicate to filter roadsections already used by a vehicle, a method to prevent the vehicle looping we had
	//It was not used in the end
	public static Predicate<RoadSection> ignoreTheseRoadSections(ArrayList<RoadSection> previousTravelled){
		return p -> !previousTravelled.contains(p);
	}
	public static ArrayList<Thread> filterThreads(List<Thread> listOfCarThreads, Predicate<Thread> predicate){
		return (ArrayList<Thread>) listOfCarThreads.stream().filter(predicate).collect(Collectors.<Thread>toList());
	}
	//A predicate to get a list of all threads that are still running
	public static Predicate<Thread> runningThreads(List<Thread> listOfCarThreads){
		return p -> p.isAlive();
	}
	public static ArrayList<Photograph> filterPhotos(HashSet<Photograph> hshst, Predicate<Photograph>predicate){
		return (ArrayList<Photograph>)hshst.stream().filter(predicate).collect(Collectors.<Photograph>toList());
	}
	//A predicate to get all photographs associated to a vehicle
	public static Predicate<Photograph> photographByVehicle(Vehicle v){
		return p -> p.vehicle() == v;
	}
}
