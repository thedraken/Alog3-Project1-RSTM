package lu.uni.algo3;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.ExceedMaxOccupationException;
//import lu.uni.algo3.exceptions.MissingTollRecordException;
import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.utils.Utils;

public class Camera implements Comparable<Camera> {
	public Camera(Type type) throws OutOfRangeException{
		_id = SQLIndexer.getInstance().getNewID(SQLType.Camera);
		this._type = type;
		this._photosTaken = new HashSet<Photograph>();
		hashCodeExtra = Utils.returnRandomInt();
	}
	public enum Type{
		RoadEntry,
		RoadLeaving,
		OnRoad
	}
	private int _id;
	public int iD(){
		return this._id;
	}
	private boolean working = true;
	private int hashCodeExtra;
	private RoadSection _location;
	public RoadSection location(){
		return this._location;
	}
	public void setLocation(RoadSection rs){
		this._location = rs;
	}
	//Returns randomanlly if a camera is working. We work on camera's breaking 1% of the time
	public boolean isWorking(){
		if (working){
			int value = Utils.returnRandomInt(0, 100);
			if (value <= 1)
				working = false;
		}
		return working;
	}
	//Attempts to fix a camera, will succeed 90% of the time
	public boolean fixCamera(){
		//90% chance to fix the camera
		int value = Utils.returnRandomInt(0, 100);
		if (value <= 90)
			working = true;
		return working;
	}
	private Type _type;
	public Type type(){
		return this._type;
	}
	//A warning message to be displayed on the road
	private String _warningMessage = null;
	public synchronized String WarningMessage(){
		if (_warningMessage == null)
			return "";
		else
			return _warningMessage;
	}
	//The vehciles within the camera area, this is accquired from the assoicated road section
	public HashSet<Vehicle> vehiclesNowPassing(){
		return this.location().getAllVehiclesInside();
	}
	private HashSet<Photograph> _photosTaken;
	public HashSet<Photograph> photosTaken(){
		return this._photosTaken;
	}
	//Creates a new photo of a car passing through
	public void capturePhoto(Vehicle v) throws OutOfRangeException{
		Calendar cal = Calendar.getInstance();
		Photograph p = new Photograph(this.iD(), cal.getTime(), v);
		this._photosTaken.add(p);
	}
	//A method to identify cars in a photo, not being used in this version
	public void identifyVehicle(Photograph p){
		@SuppressWarnings("unchecked")
		List<Vehicle> listV = (List<Vehicle>)Utils.hashSetToArrayList(_location.getAllVehiclesInside());
		p.setVehicle(listV.get(Utils.returnRandomInt(0, listV.size()-1)));
	}
	//A list of all cars that have stopped inside the current camera's roadsection
	public List<Vehicle> stationaryVehicles(){
		List<Vehicle> stoppedVehicles = new ArrayList<Vehicle>();
		for(Vehicle v: _location.getAllVehiclesInside()){
			if (v.hasStopped()){
				stoppedVehicles.add(v);
			}
		}
		return stoppedVehicles;
	}
	/*public synchronized void updateRoadOccupation(Vehicle v) throws ExceedMaxOccupationException, ObjectExistsInCollectionException{
		if (!this._location.alreadyInside(v))
			this._location.insertVehicle(v);
	}*/
	//Prints the current warning message associated with the camera
	public void displayWarning(String s){
		this._warningMessage = s;
		System.out.println("Camera " + this._id + " has reported a warning: " + s);
	}
	/*public synchronized TollRecord createTollRecord(Vehicle v){
		TollRecord tr =  new TollRecord(v, this.location());
		openTollRecords.add(tr);
		return tr;
	}
	public synchronized TollRecord closeTollRecord(Vehicle v) throws MissingTollRecordException{
		if (openTollRecords.size() > 0){
			List<TollRecord> listOfTolls = Predicates.filterTollRecords(openTollRecords, Predicates.tollRecordForVehicle(v));
			if (listOfTolls.size() > 0){
				TollRecord tr = listOfTolls.get(0);
				tr.setExit(this.location());
				openTollRecords.remove(tr);
				return tr;
			}
			throw new MissingTollRecordException(v, this);
		}
		throw new MissingTollRecordException(v, this);
	}
	private ArrayList<TollRecord> openTollRecords = new ArrayList<TollRecord>();*/
	@Override
	public boolean equals(Object o){
		if (!(o instanceof Camera))
			return false;
		Camera c = (Camera)o;
		if (c.iD() != this._id)
			return false;
		if (c.hashCode() != this.hashCode())
			return false;
		if (c.location() != this._location)
			return false;
		if (c.type() != this._type)
			return false;
		return true;
	}
	@Override
	public int hashCode(){
		return this._id + this.hashCodeExtra;
	}
	@Override
	public int compareTo(Camera c){
		if (c.iD() > this.iD())
			return 1;
		if (c.iD() < this.iD())
			return -1;
		return 0;
	}
	@Override
	public String toString(){
		return "Camera: " + this.iD();
	}
}
