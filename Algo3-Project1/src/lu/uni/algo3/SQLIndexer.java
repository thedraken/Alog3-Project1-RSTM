package lu.uni.algo3;

import lu.uni.algo3.exceptions.OutOfRangeException;

public class SQLIndexer {
	//A generic class used to create dummy SQL index numbers. Gives the first item a 1 and the next a 2 and so on.
	private static SQLIndexer instance = null;
	private SQLIndexer(){
		
	}
	public static SQLIndexer getInstance(){
		if (instance == null)
			instance = new SQLIndexer();
		return instance;
	}
	private int _vehicleID = 0;
	private int _countryID = 0;
	private int _cameraID = 0;
	private int _photographID = 0;
	private int _roadID = 0;
	private int _billID = 0;
	private int _tollRecordID = 0;
	private int _trafficPlannerID = 0;
	private int _rescueWorkerID = 0;
	private int _policeOfficerID = 0;
	private int _billerID = 0;
	private int _roadSectionID = 0;
	public enum SQLType{
		Vehicle,
		Country,
		Camera,
		Photograph,
		Road,
		Bill,
		TollRecord,
		TrafficPlanner,
		RescueWorker,
		PoliceOfficer,
		Biller,
		RoadSection
	}
	public int getNewID(SQLType type) throws OutOfRangeException{
		switch(type){
		case Bill:
			return ++_billID;
		case Camera:
			return ++_cameraID;
		case Country:
			return ++_countryID;
		case Photograph:
			return ++_photographID;
		case Road:
			return ++_roadID;
		case TollRecord:
			return ++_tollRecordID;
		case Vehicle:
			return ++_vehicleID;
		case TrafficPlanner:
			return ++_trafficPlannerID;
		case RescueWorker:
			return ++_rescueWorkerID;
		case PoliceOfficer:
			return ++_policeOfficerID;
		case Biller:
			return ++_billerID;
		case RoadSection:
			return ++_roadSectionID;
		}
		//If we've reached here, we're missing an enum value. Throw an exception so we can fix it
		throw new OutOfRangeException(-1);
	}
	//Used to get the total number of road sections in the entire system. Need for the simulator
	public int getNumberOfRoadSections(){
		return _roadSectionID;
	}
}
