package lu.uni.algo3;

import java.util.ArrayList;
import lu.uni.algo3.utils.Utils;

public class Main {
	private static ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
	public static void main(String[] args) {
		generateCars();
		for(Vehicle v: listOfVehicles)
			v.run();
	}
	public static void generateCars(){
		int numberOfCarsToGenerate = Utils.returnRandomInt(50, 100);
		for(int i = 0; i < numberOfCarsToGenerate; i++){
			Vehicle v = new Vehicle(Utils.returnRandomLicensePlate(), Utils.randomCategory());
			listOfVehicles.add(v);
			System.out.println("Created the vehicle " + v.getID() + " with a license plate of " + v.getLicencePlate() + " and a category of " + v.getCategory());
		}
		System.out.println("Created  a total of " + numberOfCarsToGenerate + " cars");
	}

}
