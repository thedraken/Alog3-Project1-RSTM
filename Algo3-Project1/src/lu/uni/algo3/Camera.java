package lu.uni.algo3;

import java.awt.Point;
import java.util.Calendar;
import java.util.HashSet;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.MissingVehicleException;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.utils.Utils;

public class Camera {
	public Camera(Point location, Type type) throws OutOfRangeException{
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
		double value = Utils.returnRandomDouble(0, 100);
		//We assume cameras will be broken 1% of the time
		return (value > 0.01);
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
		//We'll take a photo for each and every car in the hashset of cars
		if (_vehiclesNowPassing.size() <= 0)
			return;
		Calendar cal = Calendar.getInstance();
		for(Vehicle  v: _vehiclesNowPassing){
			try {
				Photograph p = new Photograph(this.iD(), cal.getTime(), v);
				this._photosTaken.add(p);
			} catch (OutOfRangeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
