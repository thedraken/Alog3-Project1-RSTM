package lu.uni.algo3;

import java.util.ArrayList;
import java.util.List;

import lu.uni.algo3.Camera.Type;
import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.utils.Utils;

public class Simulator {
	
	public static List<Road> roadMap;
	public static List<Vehicle> listOfVehicles;
	public static Biller theBiller;
	public static List<RescueWorker> listOfRescueWorkers;
	public static List<PoliceOfficer> listOfPoliceOfficers;
	public static List <TrafficPlanner> listOfTrafficPlanners;
	
	//just putting some number until we decide
	public static final int NUMBEROFROADSECTIONSA1 = 4;
	public static final int NUMBEROFROADSECTIONSA3 = 3;
	public static final int NUMBEROFROADSECTIONSA4 = 3;
	public static final int NUMBEROFROADSECTIONSA6 = 3;
	public static final int NUMBEROFROADSECTIONSA7 = 2;
	public static final int NUMBEROFROADSECTIONSA13 = 4;
	
	
	public static void main(String[] args){
		
		uploadLuxRoadMap();
		
		generateCars();
		recruteWorkers();
		
		for (Vehicle v : listOfVehicles){
			(new Thread(v)).start();
		}
		
		for (PoliceOfficer po : listOfPoliceOfficers){
			(new Thread(po)).start();
		}
		
		for (RescueWorker rw : listOfRescueWorkers){
			(new Thread(rw)).start();
		}
		
		for (TrafficPlanner tp : listOfTrafficPlanners){
			(new Thread(tp)).start();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(theBiller).start();
	}
	
	public static void uploadLuxRoadMap(){
		roadMap = new ArrayList<Road>();
		try {
			Road r1 = new Road("A1");
			int numberOfRoadSectionsToCreate = NUMBEROFROADSECTIONSA1;
			for(int i = 0; i < numberOfRoadSectionsToCreate; i++){
				//int number, int speedLimit, int maxOccupation, double distanceOfRoadSection
				int speedLimit = Utils.returnRandomInt(90, 130);
				int maxOccupation = Utils.returnRandomInt(5, 15);
				double distanceOfRoadSection = Utils.returnRandomDouble(2, 30);
				Camera cam = new Camera(Type.OnRoad);
				RoadSection rs = new RoadSection(speedLimit, maxOccupation, distanceOfRoadSection, cam, r1);
				try {
					r1.insertRoadSection(rs);
					System.out.println("Creating road section with id: " + rs.number() + ", belonging to road: " + r1.name());
				} catch (ObjectExistsInCollectionException e) {
					System.out.println("Unable to insert road section into road "+ r1.name() +", please see error message below");
					e.printStackTrace();
				}
			}
			roadMap.add(r1);
		} catch (OutOfRangeException e) {
			System.out.println("Unable to create road, please see error message below");
			e.printStackTrace();
		}
		try {
			Road r2 = new Road("A3");
			int numberOfRoadSectionsToCreate = NUMBEROFROADSECTIONSA3;
			for(int i = 0; i < numberOfRoadSectionsToCreate; i++){
				//int number, int speedLimit, int maxOccupation, double distanceOfRoadSection
				int speedLimit = Utils.returnRandomInt(90, 130);
				int maxOccupation = Utils.returnRandomInt(5, 15);
				double distanceOfRoadSection = Utils.returnRandomDouble(2, 30);
				Camera cam = new Camera(Type.OnRoad);
				RoadSection rs = new RoadSection(speedLimit, maxOccupation, distanceOfRoadSection, cam, r2);
				try {
					r2.insertRoadSection(rs);
				} catch (ObjectExistsInCollectionException e) {
					System.out.println("Unable to insert road section into road "+ r2.name() +", please see error message below");
					e.printStackTrace();
				}
			}
			roadMap.add(r2);
		} catch (OutOfRangeException e) {
			System.out.println("Unable to create road, please see error message below");
			e.printStackTrace();
		}
		try {
			Road r3 = new Road("A4");
			int numberOfRoadSectionsToCreate = NUMBEROFROADSECTIONSA4;
			for(int i = 0; i < numberOfRoadSectionsToCreate; i++){
				//int number, int speedLimit, int maxOccupation, double distanceOfRoadSection
				int speedLimit = Utils.returnRandomInt(90, 130);
				int maxOccupation = Utils.returnRandomInt(5, 15);
				double distanceOfRoadSection = Utils.returnRandomDouble(2, 30);
				Camera cam = new Camera(Type.OnRoad);
				RoadSection rs = new RoadSection(speedLimit, maxOccupation, distanceOfRoadSection, cam, r3);
				try {
					r3.insertRoadSection(rs);
				} catch (ObjectExistsInCollectionException e) {
					System.out.println("Unable to insert road section into road "+ r3.name() +", please see error message below");
					e.printStackTrace();
				}
			}
			roadMap.add(r3);
		} catch (OutOfRangeException e) {
			System.out.println("Unable to create road, please see error message below");
			e.printStackTrace();
		}
		try {
			Road r4 = new Road("A6");
			int numberOfRoadSectionsToCreate = NUMBEROFROADSECTIONSA6;
			for(int i = 0; i < numberOfRoadSectionsToCreate; i++){
				//int number, int speedLimit, int maxOccupation, double distanceOfRoadSection
				int speedLimit = Utils.returnRandomInt(90, 130);
				int maxOccupation = Utils.returnRandomInt(5, 15);
				double distanceOfRoadSection = Utils.returnRandomDouble(2, 30);
				Camera cam = new Camera(Type.OnRoad);
				RoadSection rs = new RoadSection(speedLimit, maxOccupation, distanceOfRoadSection, cam, r4);
				try {
					r4.insertRoadSection(rs);
				} catch (ObjectExistsInCollectionException e) {
					System.out.println("Unable to insert road section into road "+ r4.name() +", please see error message below");
					e.printStackTrace();
				}
			}
			roadMap.add(r4);
		} catch (OutOfRangeException e) {
			System.out.println("Unable to create road, please see error message below");
			e.printStackTrace();
		}
		try {
			Road r5 = new Road("A7");
			int numberOfRoadSectionsToCreate = NUMBEROFROADSECTIONSA7;
			for(int i = 0; i < numberOfRoadSectionsToCreate; i++){
				//int number, int speedLimit, int maxOccupation, double distanceOfRoadSection
				int speedLimit = Utils.returnRandomInt(90, 130);
				int maxOccupation = Utils.returnRandomInt(5, 15);
				double distanceOfRoadSection = Utils.returnRandomDouble(2, 30);
				Camera cam = new Camera(Type.OnRoad);
				RoadSection rs = new RoadSection(speedLimit, maxOccupation, distanceOfRoadSection, cam, r5);
				try {
					r5.insertRoadSection(rs);
				} catch (ObjectExistsInCollectionException e) {
					System.out.println("Unable to insert road section into road "+ r5.name() +", please see error message below");
					e.printStackTrace();
				}
			}
			roadMap.add(r5);
		} catch (OutOfRangeException e) {
			System.out.println("Unable to create road, please see error message below");
			e.printStackTrace();
		}
		try {
			Road r6 = new Road("A13");
			int numberOfRoadSectionsToCreate = NUMBEROFROADSECTIONSA13;
			for(int i = 0; i < numberOfRoadSectionsToCreate; i++){
				//int number, int speedLimit, int maxOccupation, double distanceOfRoadSection
				int speedLimit = Utils.returnRandomInt(90, 130);
				int maxOccupation = Utils.returnRandomInt(5, 15);
				double distanceOfRoadSection = Utils.returnRandomDouble(2, 30);
				Camera cam = new Camera(Type.OnRoad);
				RoadSection rs = new RoadSection(speedLimit, maxOccupation, distanceOfRoadSection, cam, r6);
				try {
					r6.insertRoadSection(rs);
				} catch (ObjectExistsInCollectionException e) {
					System.out.println("Unable to insert road section into road "+ r6.name() +", please see error message below");
					e.printStackTrace();
				}
			}
			roadMap.add(r6);
		} catch (OutOfRangeException e) {
			System.out.println("Unable to create road, please see error message below");
			e.printStackTrace();
		}
		//setting up connections between road sections
		//A1 has connections to A3 and A6 from it's 4th road section
		RoadSection a1RS4 = roadMap.get(0).listOfRoadSections().get(3);
		RoadSection a3RS5 = roadMap.get(1).listOfRoadSections().get(0);
		RoadSection a6RS11 = roadMap.get(3).listOfRoadSections().get(0);
		a1RS4.addRoadSectionConnection(a3RS5);
		a1RS4.addRoadSectionConnection(a6RS11);
		a3RS5.addRoadSectionConnection(a1RS4);
		a3RS5.addRoadSectionConnection(a6RS11);
		a6RS11.addRoadSectionConnection(a1RS4);
		a6RS11.addRoadSectionConnection(a3RS5);
		//A3 has also a connection to A13
		RoadSection a3RS6 = roadMap.get(1).listOfRoadSections().get(1);
		RoadSection a13RS18 = roadMap.get(5).listOfRoadSections().get(2);
		a3RS6.addRoadSectionConnection(a13RS18);
		a13RS18.addRoadSectionConnection(a3RS6);
		//A4 has connections to A6 and A13
		RoadSection a4RS8 = roadMap.get(2).listOfRoadSections().get(0);
		RoadSection a4RS10 = roadMap.get(2).listOfRoadSections().get(2);
		RoadSection a6RS12 = roadMap.get(3).listOfRoadSections().get(1);
		RoadSection a13RS19 = roadMap.get(5).listOfRoadSections().get(3);
		a4RS8.addRoadSectionConnection(a6RS12);
		a6RS12.addRoadSectionConnection(a4RS8);
		a4RS10.addRoadSectionConnection(a13RS19);
		a13RS19.addRoadSectionConnection(a4RS10);
	}
	
	public static void generateCars(){
		listOfVehicles = new ArrayList<Vehicle>();
		int numberOfCarsToGenerate = Utils.returnRandomInt(50, 100);
		for(int i = 0; i < numberOfCarsToGenerate; i++){
			Vehicle v = new Vehicle(Utils.returnRandomLicensePlate(), Utils.randomCategory());
			listOfVehicles.add(v);
			System.out.println("Created the vehicle " + v.getID() + " with a license plate of " + v.getLicencePlate() + " and a category of " + v.getCategory());
		}
		System.out.println("Created  a total of " + numberOfCarsToGenerate + " cars");
	}
	
	public static void recruteWorkers(){
		for (Road r : roadMap){
			listOfRescueWorkers = new ArrayList<RescueWorker>();
			RescueWorker rw = new RescueWorker(r.listOfRoadSections());
			listOfRescueWorkers.add(rw);
			listOfPoliceOfficers = new ArrayList<PoliceOfficer>();
			PoliceOfficer po = new PoliceOfficer(r.listOfRoadSections());
			listOfPoliceOfficers.add(po);
			listOfTrafficPlanners = new ArrayList<TrafficPlanner>();
			TrafficPlanner tp = new TrafficPlanner(r.listOfRoadSections());
			listOfTrafficPlanners.add(tp);
		}
		theBiller = new Biller();
	}
	
}
