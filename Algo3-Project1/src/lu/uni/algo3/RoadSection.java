package lu.uni.algo3;

import java.util.HashSet;
import java.util.List;

import lu.uni.algo3.Predicates;
import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.Vehicle.Category;
import lu.uni.algo3.exceptions.ExceedMaxOccupationException;
import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.exceptions.OutOfRangeException;
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
	private Camera _camera;
	private Road _road;
	public RoadSection(int speedLimit, int maxOccupation, HashSet<RoadSection> connectionToOtherRoadSections, double distanceOfRoadSection, Camera cam, Road roadIsWith) throws OutOfRangeException{
		baseRoadSection(speedLimit, maxOccupation, distanceOfRoadSection, cam, roadIsWith);
		this._connectionToOtherRoadSections = connectionToOtherRoadSections;
		this._roadContinutesAfterSection = true;
	}
	public RoadSection(int speedLimit, int maxOccupation, double distanceOfRoadSection, Camera cam, Road roadIsWith) throws OutOfRangeException{
		baseRoadSection(speedLimit, maxOccupation, distanceOfRoadSection, cam, roadIsWith);
		this._connectionToOtherRoadSections = new HashSet<RoadSection>();
		this._roadContinutesAfterSection = false;
	}
	//The base constructor called by both constructors, this will set the standard values needed in both
	private void baseRoadSection(int speedLimit, int maxOccupation, double distanceOfRoadSection, Camera cam, Road roadIsWith) throws OutOfRangeException{
		this._number = SQLIndexer.getInstance().getNewID(SQLType.RoadSection);
		this._speedLimit = speedLimit;
		this._maxOccupation = maxOccupation;
		this._listOfObservers = new HashSet<RoadSectionObserver>();
		this._listOfVehiclesInside = new HashSet<Vehicle>();
		hashCodeExtra = Utils.returnRandomInt();
		this._distance = distanceOfRoadSection;
		this._camera = cam;
		cam.setLocation(this);
		this._road = roadIsWith;
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
	public Road road(){
		return this._road;
	}
	//A synced hashset of vehicles, has to be synced as this can change at any time
	public synchronized  HashSet<Vehicle> getAllVehiclesInside(){
		return this._listOfVehiclesInside;
	}
	//Returns a boolean confirming if the road has more motorway left or not to run on
	public boolean roadContinutesAfterSection(){
		return this._roadContinutesAfterSection;
	}
	//Returns a hashset of road sections this roadsection is connected to
	public synchronized HashSet<RoadSection> connectionToOtherRoadSections(){
		return this._connectionToOtherRoadSections;
	}
	public synchronized HashSet<RoadSectionObserver> listOfObservers(){
		return this._listOfObservers;
	}
	//Gets the current number of vehicles inside the road section
	public int getOccupation(){
		return this.getAllVehiclesInside().size();
	}
	public Camera getCamera(){
		return this._camera;
	}
	//A method to insert a vehicle inside a road section
	public synchronized void insertVehicle(Vehicle v) throws ExceedMaxOccupationException, ObjectExistsInCollectionException{
		//First checks to make sure there is room in the roadsection
		if (_listOfVehiclesInside.size() +1 > _maxOccupation)
			throw new ExceedMaxOccupationException(_listOfVehiclesInside.size(), maxOccupation(), 1);
		//Then makes sure the vehicle is not already in the road section
		if (_listOfVehiclesInside.contains(v))
			throw new ObjectExistsInCollectionException();
		_listOfVehiclesInside.add(v);
		try {
			//Captures a photo of the car, since it is now in the road section
			this._camera.capturePhoto(v);
		} catch (OutOfRangeException e) {
			System.out.println("Camera " + this._camera.iD() + " on the roadsection " + this.number() + " was unable to take a photograph");
			e.printStackTrace();
		}
		//If the car is new to the system, it was generate a new toll record, else it will update the current one
		if (v.getTollRecord() == null)
			v.setTollRecord(new TollRecord(v, this));
		else
			v.getTollRecord().addRoadSection(this);
		//Runs a method to update observers, if they need to know about the vehicles
		notifyObservers();
	}
	//A synced method to remove a vehicle from this road section
	public synchronized boolean removeVehicle(Vehicle v){
		//Make sure the vehicle is actually in the road section
		if (_listOfVehiclesInside.contains(v)){
			_listOfVehiclesInside.remove(v);
			notifyObservers();
			//If the car is leaving the toll system, this will close the toll record
			if (v.hasExitedRoadMap())
				v.getTollRecord().setExit(this);
			return true;
		}
		return false;
	}
	public List<Vehicle> getVehiclesByCategory(Category c){
		return Predicates.filterVehicles(this.getAllVehiclesInside(), Predicates.isCategory(c));
	}
	//Returns true if a road is considered congested
	public boolean isBusy(){
		//Road is considered busy if over half occupation
		int currentCarsOnRoad = this.getAllVehiclesInside().size();
		double percentage = (double)currentCarsOnRoad / (double)_maxOccupation;
		return percentage > 0.5;

	}
	public synchronized boolean alreadyInside(Vehicle v){
		return this.getAllVehiclesInside().contains(v);
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
	//Adds a connection of a road section to another. This can include from other motorways, so acting like a junction
	public synchronized void addRoadSectionConnection(RoadSection rs){
		if (this._connectionToOtherRoadSections.contains(rs))
			return;
		this._connectionToOtherRoadSections.add(rs);
		this._roadContinutesAfterSection = true;
	}
	@Override
	public boolean equals(Object o){
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
	public int compareTo(RoadSection o) {
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
