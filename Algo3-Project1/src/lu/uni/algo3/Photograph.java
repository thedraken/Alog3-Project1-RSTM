package lu.uni.algo3;

import java.util.Date;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.OutOfRangeException;

public class Photograph {
	public Photograph(int cameraID, Date dateTimeCaptured, Vehicle vehicle) throws OutOfRangeException{
		_iD = SQLIndexer.getInstance().getNewID(SQLType.Photograph);
		this._dateTimeCaptured = dateTimeCaptured;
		this._locationOnDisk = "/photograph/Camera" + cameraID + "/Photo" + _iD + ".jpg";
		this._vehicle = vehicle;
	}
	private int _iD;
	public int iD(){
		return this._iD;
	}
	private Date _dateTimeCaptured;
	public Date dateTime(){
		return this._dateTimeCaptured;
	}
	private String _locationOnDisk;
	public String locationOnDisk(){
		return this._locationOnDisk;
	}
	private Vehicle _vehicle;
	public Vehicle vehicle(){
		return this._vehicle;
	}
	public void setVehicle(Vehicle v){
		this._vehicle = v;
	}
}
