package lu.uni.algo3;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.ExceedMaxOccupation;
import lu.uni.algo3.exceptions.MissingTollRecord;
import lu.uni.algo3.exceptions.ObjectExistsInCollection;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.utils.Utils;

public class Camera {
	public Camera(RoadSection location, Type type) throws OutOfRangeException{
		_id = SQLIndexer.getInstance().getNewID(SQLType.Camera);
		this._type = type;
		this._location = location;
		this._photosTaken = new HashSet<Photograph>();
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
	public synchronized void updateRoadOccupation(Vehicle v){
		if (!this._location.alreadyInside(v))
			try {
				this._location.insertVehicle(v);
			} catch (ExceedMaxOccupation | ObjectExistsInCollection e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void displayWarning(String s){
		this._warningMessage = s;
	}
	public synchronized TollRecord createTollRecord(Vehicle v){
		TollRecord tr =  new TollRecord(v, this._location);
		openTollRecords.add(tr);
		return tr;
	}
	public synchronized TollRecord closeTollRecord(Vehicle v) throws MissingTollRecord{
		//TODO should we store the list of open toll records against the vehicle or camera
		if (openTollRecords.size() > 0){
			List<TollRecord> listOfTolls = Predicates.filterTollRecords(openTollRecords, Predicates.tollRecordForVehilce(v));
			if (listOfTolls.size() > 0){
				TollRecord tr = listOfTolls.get(0);
				tr.closeRecord(this._location);
				openTollRecords.remove(tr);
				return tr;
			}
			throw new MissingTollRecord(v, this);
		}
		throw new MissingTollRecord(v, this);
	}
	private ArrayList<TollRecord> openTollRecords = new ArrayList<TollRecord>();
}
