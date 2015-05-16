package lu.uni.algo3;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.ExceedMaxOccupationException;
import lu.uni.algo3.exceptions.MissingTollRecordException;
import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.utils.Utils;

public class Camera implements Comparable<Camera> {
	public Camera(RoadSection location, Type type) throws OutOfRangeException{
		_id = SQLIndexer.getInstance().getNewID(SQLType.Camera);
		this._type = type;
		this._location = location;
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
	private int hashCodeExtra;
	private RoadSection _location;
	public RoadSection location(){
		return this._location;
	}
	public boolean isWorking(){
		double value = Utils.returnRandomDouble(0, 100);
		//We assume cameras will be broken 1% of the time
		return (value > 0.01);
	}
	private Type _type;
	public Type type(){
		return this._type;
	}
	private String _warningMessage = null;
	public synchronized String WarningMessage(){
		if (_warningMessage == null)
			return "";
		else
			return _warningMessage;
	}
	public HashSet<Vehicle> vehiclesNowPassing(){
		return this.location().getAllVehiclesInside();
	}
	private HashSet<Photograph> _photosTaken;
	public HashSet<Photograph> photosTaken(){
		return this._photosTaken;
	}
	public void capturePhoto() throws OutOfRangeException{
		Calendar cal = Calendar.getInstance();
		Photograph p = new Photograph(this.iD(), cal.getTime());
		this._photosTaken.add(p);
	}
	public void identifyVehicle(Photograph p){
		@SuppressWarnings("unchecked")
		List<Vehicle> listV = (List<Vehicle>)Utils.hashSetToArrayList(_location.getAllVehiclesInside());
		p.setVehicle(listV.get(Utils.returnRandomInt(0, listV.size()-1)));
	}
	public synchronized boolean stationaryVehicle(){
		//TODO we should add a thing to the vehicle that tells it randomly to stop
		for(Vehicle v: _location.getAllVehiclesInside()){
			//if (v.stopped)
		}
		return false;
	}
	public synchronized void updateRoadOccupation(Vehicle v) throws ExceedMaxOccupationException, ObjectExistsInCollectionException{
		if (!this._location.alreadyInside(v))
			this._location.insertVehicle(v);
	}
	public void displayWarning(String s){
		this._warningMessage = s;
		System.out.println("Camera " + this._id + " has reported a warning: " + s);
	}
	public synchronized TollRecord createTollRecord(Vehicle v){
		TollRecord tr =  new TollRecord(v, this, new DateTime());
		openTollRecords.add(tr);
		return tr;
	}
	public synchronized TollRecord closeTollRecord(Vehicle v) throws MissingTollRecordException{
		//TODO should we store the list of open toll records against the vehicle or camera
		if (openTollRecords.size() > 0){
			List<TollRecord> listOfTolls = Predicates.filterTollRecords(openTollRecords, Predicates.tollRecordForVehicle(v));
			if (listOfTolls.size() > 0){
				TollRecord tr = listOfTolls.get(0);
				tr.setExit(this, new DateTime());
				openTollRecords.remove(tr);
				return tr;
			}
			throw new MissingTollRecordException(v, this);
		}
		throw new MissingTollRecordException(v, this);
	}
	private ArrayList<TollRecord> openTollRecords = new ArrayList<TollRecord>();
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
}
