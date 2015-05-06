package lu.uni.algo3;

import java.util.HashSet;

import lu.uni.algo3.exceptions.ExceedMaxOccupation;
import lu.uni.algo3.exceptions.ObjectExistsInHashSet;

public class RoadSection {
	private int _number;
	private int _speedLimit;
	private int _maxOccupation;
	private int _tollRate;
	private HashSet<Vehicle> _listOfVehiclesInside;
	private boolean _roadContinutesAfterSection;
	private Road _connectionToOtherRoad;
	private HashSet<Observer> _listOfObservers;
	public RoadSection(int number, int speedLimit, int maxOccupation, Road connectionToOtherRoad){
		baseRoadSection(number, speedLimit, maxOccupation);
		this._connectionToOtherRoad = connectionToOtherRoad;
		this._roadContinutesAfterSection = true;
		
	}
	public RoadSection(int number, int speedLimit, int maxOccupation){
		baseRoadSection(number, speedLimit, maxOccupation);
		this._connectionToOtherRoad = null;
		this._roadContinutesAfterSection = false;
	}
	private void baseRoadSection(int number, int speedLimit, int maxOccupation){
		this._number = number;
		this._speedLimit = speedLimit;
		this._maxOccupation = maxOccupation;
		this._listOfObservers = new HashSet<Observer>();
		this._listOfVehiclesInside = new HashSet<Vehicle>();
	}
	public int number(){
		return this._number;
	}
	public int speedLimit(){
		return this._speedLimit;
	}
	public int maxOccupation(){
		return this._maxOccupation;
	}
	public int tollRate(){
		return this._tollRate;
	}
	public HashSet<Vehicle> listOfVehiclesInside(){
		return this._listOfVehiclesInside;
	}
	public boolean roadContinutesAfterSection(){
		return this._roadContinutesAfterSection;
	}
	public Road connectionToOtherRoad(){
		return this._connectionToOtherRoad;
	}
	public HashSet<Observer> listOfObservers(){
		return this._listOfObservers;
	}
	public int getOccupation(){
		return this.listOfVehiclesInside().size();
	}
	public void insertVehicle(Vehicle v) throws ExceedMaxOccupation, ObjectExistsInHashSet{
		if (listOfVehiclesInside().size() +1 > maxOccupation())
			throw new ExceedMaxOccupation(listOfVehiclesInside().size(), maxOccupation(), 1);
		if (_listOfVehiclesInside.contains(v))
			throw new ObjectExistsInHashSet();
		_listOfVehiclesInside.add(v);
	}
}
