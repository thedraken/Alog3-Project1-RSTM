package lu.uni.algo3;

import java.util.ArrayList;
import java.util.List;

import lu.uni.algo3.Camera.Type;
import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.utils.Utils;

public class Simulator {
	
	public static List<Road> roadMap;
	
	//just putting some number until we decide
	public static final int NUMBEROFROADSECTIONSA1 = 4;
	public static final int NUMBEROFROADSECTIONSA3 = 3;
	public static final int NUMBEROFROADSECTIONSA4 = 3;
	public static final int NUMBEROFROADSECTIONSA6 = 3;
	public static final int NUMBEROFROADSECTIONSA7 = 2;
	public static final int NUMBEROFROADSECTIONSA13 = 4;
	public static final int TOTALNUMBEROFROADSECTIONS = 19;
	
	
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
				RoadSection rs = new RoadSection(i, speedLimit, maxOccupation, distanceOfRoadSection, cam);
				try {
					r1.insertRoadSection(rs);
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
				RoadSection rs = new RoadSection(i, speedLimit, maxOccupation, distanceOfRoadSection, cam);
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
				RoadSection rs = new RoadSection(i, speedLimit, maxOccupation, distanceOfRoadSection, cam);
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
				RoadSection rs = new RoadSection(i, speedLimit, maxOccupation, distanceOfRoadSection, cam);
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
				RoadSection rs = new RoadSection(i, speedLimit, maxOccupation, distanceOfRoadSection, cam);
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
				RoadSection rs = new RoadSection(i, speedLimit, maxOccupation, distanceOfRoadSection, cam);
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
		
	}
	
	
}
