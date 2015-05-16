package lu.uni.algo3;

import java.util.ArrayList;
import java.util.List;

import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.utils.Utils;

public class Simulator {
	
	public static List<Road> roadMap;
	
	//just putting some number until we decide
	public static final int NUMBEROFROADSECTIONS = 50;
	
	
	public static void uploadLuxRoadMap(){
		roadMap = new ArrayList<Road>();
		try {
			Road r = new Road("A1");
			int numberOfRoadSectionsToCreate = NUMBEROFROADSECTIONS;
			for(int i = 0; i < numberOfRoadSectionsToCreate; i++){
				//int number, int speedLimit, int maxOccupation, double distanceOfRoadSection
				int speedLimit = Utils.returnRandomInt(90, 130);
				int maxOccupation = Utils.returnRandomInt(5, 15);
				double distanceOfRoadSection = Utils.returnRandomDouble(2, 30);
				RoadSection rs = new RoadSection(i, speedLimit, maxOccupation, distanceOfRoadSection);
				try {
					r.insertRoadSection(rs);
				} catch (ObjectExistsInCollectionException e) {
					System.out.println("Unable to insert road section into road "+ r.name() +", please see error message below");
					e.printStackTrace();
				}
			}
			roadMap.add(r);
		} catch (OutOfRangeException e) {
			System.out.println("Unable to create road, please see error message below");
			e.printStackTrace();
		}
	}
}
