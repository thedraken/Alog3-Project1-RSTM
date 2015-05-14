package lu.uni.algo3;

import java.util.HashSet;
import java.util.List;
import lu.uni.algo3.Predicates;
import lu.uni.algo3.Vehicle.Category;
import lu.uni.algo3.exceptions.ExceedMaxOccupationException;
import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.utils.Utils;

public class RoadSection implements Comparable<RoadSection> {
	private int _number;
	private int _speedLimit;
	private int _maxOccupation;
	private int _tollRate;
	private HashSet<Vehicle> _listOfVehiclesInside;
	private boolean _roadContinutesAfterSection;
	private HashSet<RoadSection> _connectionToOtherRoadSections;
	private HashSet<RoadSectionObserver> _listOfObservers;
	private int hashCodeExtra;
	private double _distance;
	public RoadSection(int number, int speedLimit, int maxOccupation, HashSet<RoadSection> connectionToOtherRoadSections){
		baseRoadSection(number, speedLimit, maxOccupation);
		this._connectionToOtherRoadSections = connectionToOtherRoadSections;
		this._roadContinutesAfterSection = true;
		
	}
	public RoadSection(int number, int speedLimit, int maxOccupation){
		baseRoadSection(number, speedLimit, maxOccupation);
		this._connectionToOtherRoadSections = new HashSet<RoadSection>();
		this._roadContinutesAfterSection = false;
	}
	private void baseRoadSection(int number, int speedLimit, int maxOccupation){
		this._number = number;
		this._speedLimit = speedLimit;
		this._maxOccupation = maxOccupation;
		this._listOfObservers = new HashSet<RoadSectionObserver>();
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
	public double distance(){
		return this._distance;
	}
	public synchronized  HashSet<Vehicle> getAllVehiclesInside(){
		return this._listOfVehiclesInside;
	}
	public boolean roadContinutesAfterSection(){
		return this._roadContinutesAfterSection;
	}
	public synchronized HashSet<RoadSection> connectionToOtherRoadSections(){
		return this._connectionToOtherRoadSections;
	}
	public synchronized HashSet<RoadSectionObserver> listOfObservers(){
		return this._listOfObservers;
	}
	public int getOccupation(){
		return this._listOfVehiclesInside.size();
	}
	public synchronized void insertVehicle(Vehicle v) throws ExceedMaxOccupationException, ObjectExistsInCollectionException{
		if (_listOfVehiclesInside.size() +1 > _maxOccupation)
			throw new ExceedMaxOccupationException(_listOfVehiclesInside.size(), maxOccupation(), 1);
		if (_listOfVehiclesInside.contains(v))
			throw new ObjectExistsInCollectionException();
		_listOfVehiclesInside.add(v);
		notifyObservers();
	}
	public synchronized boolean removeVehicle(Vehicle v){
		if (_listOfVehiclesInside.contains(v)){
			_listOfVehiclesInside.remove(v);
			notifyObservers();
			return true;
		}
		return false;
	}
	public synchronized List<Vehicle> getVehiclesByCategory(Category c){
		return Predicates.filterVehicles(_listOfVehiclesInside, Predicates.isCategory(c));
	}
	public synchronized boolean isBusy(){
		//Road is considered busy if over half occupation
		int currentCarsOnRoad = _listOfVehiclesInside.size();
		double percentage = (double)currentCarsOnRoad / (double)_maxOccupation;
		return percentage > 0.5;
		
	}
	public synchronized boolean alreadyInside(Vehicle v){
		return _listOfVehiclesInside.contains(v);
	}
	public synchronized void registerObserver(RoadSectionObserver obs){
		_listOfObservers.add(obs);
	}
	public synchronized void removeObserver(RoadSectionObserver obs){
		_listOfObservers.remove(obs);
	}
	public void notifyObservers(){
		for(RoadSectionObserver obs : _listOfObservers)
			obs.updateRS(this);
	}
	@Override
	public synchronized boolean equals(Object o){
		if (!(o instanceof RoadSection))
			return false;
		RoadSection rs = (RoadSection)o;
		if (rs.number() != this._number)
			return false;
		if (rs.hashCode() != this.hashCode())
			return false;
		if (rs.roadContinutesAfterSection() != this._roadContinutesAfterSection)
			return false;
		if (rs.maxOccupation() != this._maxOccupation)
			return false;
		if (rs.speedLimit() != this._speedLimit)
			return false;
		if (rs.connectionToOtherRoadSections() != this._connectionToOtherRoadSections)
			return false;
		return true;
	}
	@Override
	public synchronized int compareTo(RoadSection o) {
		if (_number < o.number())
			return -1;
		if (_number > o.number())
			return 1;
		if (this.hashCode() > o.hashCode())
			return 1;
		if (this.hashCode() < o.hashCode())
			return -1;
		return 0;
	}
	@Override
	public int hashCode(){
		return _number + hashCodeExtra;
	}
	@Override
	public String toString(){
		return "Road section number: " + _number;
	}
}