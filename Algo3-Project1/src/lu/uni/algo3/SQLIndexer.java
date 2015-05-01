package lu.uni.algo3;

import lu.uni.algo3.exceptions.OutOfRangeException;

public class SQLIndexer {
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
	public enum SQLType{
		Vehicle,
		Country,
		Camera,
		Photograph,
		Road,
		Bill,
		TollRecord
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
		}
		throw new OutOfRangeException(-1);
	}
}
