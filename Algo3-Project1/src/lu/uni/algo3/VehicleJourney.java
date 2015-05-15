package lu.uni.algo3;

import org.joda.time.DateTime;

public class VehicleJourney {
	private RoadSection _roadSectionTravelled;
	private DateTime _datetimePassedCamera;
	public VehicleJourney(RoadSection rsTravelled, DateTime dateTimePassedCamera){
		this._roadSectionTravelled = rsTravelled;
		this._datetimePassedCamera = dateTimePassedCamera;
	}
	public RoadSection RoadSectionTravelled(){
		return this._roadSectionTravelled;
	}
	public DateTime DateTimePassedCamera(){
		return this._datetimePassedCamera;
	}
}
