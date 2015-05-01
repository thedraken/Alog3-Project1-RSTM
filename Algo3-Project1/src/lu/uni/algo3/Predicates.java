package lu.uni.algo3;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lu.uni.algo3.Vehicle.Category;

public class Predicates {
	public static Predicate<Vehicle> isCategory(Category c){
		return p -> p.getCategory() == c;
	}
	public static List<Vehicle> filterVehicles(HashSet<Vehicle> vehilces, Predicate<Vehicle> predicate){
		return vehilces.stream().filter(predicate).collect(Collectors.<Vehicle>toList());
	}
}
