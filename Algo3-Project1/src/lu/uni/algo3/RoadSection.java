package lu.uni.algo3;

import java.util.HashSet;
import java.util.List;

import lu.uni.algo3.Predicates;
import lu.uni.algo3.Vehicle.Category;
import lu.uni.algo3.exceptions.ExceedMaxOccupation;
import lu.uni.algo3.exceptions.ObjectExistsInHashSet;
import lu.uni.algo3.utils.Utils;

public class RoadSection implements Comparable<RoadSection> {
	private int _number;
	private int _speedLimit;
	private int _maxOccupation;
	private int _tollRate;
	private HashSet<Vehicle> _listOfVehiclesInside;
	private boolean _roadContinutesAfterSection;
	private Road _connectionToOtherRoad;
	private HashSet<Observer> _listOfObservers;
	private int hashCodeExtra;
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
		hashCodeExtra = Utils.returnRandomInt();
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
	public synchronized  HashSet<Vehicle> getAllVehiclesInside(){
		return this._listOfVehiclesInside;
	}
	public boolean roadContinutesAfterSection(){
		return this._roadContinutesAfterSection;
	}
	public Road connectionToOtherRoad(){
		return this._connectionToOtherRoad;
	}
	public synchronized HashSet<Observer> listOfObservers(){
		return this._listOfObservers;
	}
	public int getOccupation(){
		return this._listOfVehiclesInside.size();
	}
	public synchronized void insertVehicle(Vehicle v) throws ExceedMaxOccupation, ObjectExistsInHashSet{
		if (_listOfVehiclesInside.size() +1 > _maxOccupation)
			throw new ExceedMaxOccupation(_listOfVehiclesInside.size(), maxOccupation(), 1);
		if (_listOfVehiclesInside.contains(v))
			throw new ObjectExistsInHashSet();
		_listOfVehiclesInside.add(v);
	}
	public synchronized boolean removeVehicle(Vehicle v){
		if (_listOfVehiclesInside.contains(v)){
			_listOfVehiclesInside.remove(v);
			return true;
		}
		return false;
	}
	public synchronized List<Vehicle> getVehiclesByCategory(Category c){
		return Predicates.filterVehicles(_listOfVehiclesInside, Predicates.isCategory(c));
	}
	public boolean isBusy(){
		//TODO What do we need to do here?
		return false;
	}
	public boolean alreadyInside(Vehicle v){
		return _listOfVehiclesInside.contains(v);
	}
	//TODO Register observers
	//public void registerObserver(RoadSectionOb)
	//public void removeObserver(RoadSectionObserver rso)
	public void notifyObservers(){
		for(Observer obs : _listOfObservers)
			obs.update();
	}
	@Override
	public boolean equals(Object o){
		if (!(o instanceof RoadSection))
			return false;
		RoadSection rs = (RoadSection)o;
		if (rs.number() != this._number)
			return false;
		if (rs.roadContinutesAfterSection() != this.roadContinutesAfterSection())
			return false;
		return true;
	}
	@Override
	public int compareTo(RoadSection o) {
		if (_number < o.number())
			return -1;
		if (_number > o.number())
			return 1;
		
		return 0;
	}
	@Override
	public int hashCode(){
		return _number + hashCodeExtra;
	}
}