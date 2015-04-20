package lu.uni.algo3;

import java.awt.Point;
import java.util.Calendar;
import java.util.HashSet;
import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.MissingVehicleException;
import lu.uni.algo3.exceptions.SQLOutOfRangeException;

public class Camera {
	public Camera(Point location, Type type) throws SQLOutOfRangeException{
		_id = SQLIndexer.getInstance().getNewID(SQLType.Camera);
		this._type = type;
		this._location = location;
		this._vehiclesNowPassing = new HashSet<Vehicle>();
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
	private Point _location;
	public Point location(){
		return this._location;
	}
	public boolean isWorking(){
		//TODO implement method!
		return true;
	}
	private Type _type;
	public Type type(){
		return this._type;
	}
	public boolean hasCarStopped(){
		//TODO implement method!
		return false;
	}
	private HashSet<Vehicle> _vehiclesNowPassing;
	public HashSet<Vehicle> vehiclesNowPassing(){
		return this._vehiclesNowPassing;
	}
	private HashSet<Photograph> _photosTaken;
	public HashSet<Photograph> photosTaken(){
		return this._photosTaken;
	}
	public void capturePhoto() throws MissingVehicleException{
		Vehicle vehicle;
		if (_vehiclesNowPassing.size() <= 0)
			throw new MissingVehicleException();
		else{
			//_vehiclesNowPassing.
		}
		Calendar cal = Calendar.getInstance();
		try {
			//TODO Change method to use get random vehicle photographed
			Photograph photo = new Photograph(this._id, cal.getTime(), new Vehicle());
			_photosTaken.add(photo);
		} catch (SQLOutOfRangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
