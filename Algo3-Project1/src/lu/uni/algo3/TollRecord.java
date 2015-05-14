package lu.uni.algo3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import lu.uni.algo3.exceptions.TollIsNotCompleteException;

public class TollRecord {
	private Bill _bill;
	private int _id;
	private Vehicle _vehicle;
	private ArrayList<RoadSection> _listOfRoadSectionsTravelled;
	private Calendar _entryTime;
	private Calendar _exitTime;
	private boolean hasExited = false;
	public TollRecord(Vehicle vehicle, Camera entry){
		this._vehicle = vehicle;
		_entryTime = Calendar.getInstance();
		_listOfRoadSectionsTravelled = new ArrayList<RoadSection>();
		_listOfRoadSectionsTravelled.add(entry.location());
		_exitTime = null;
	}
	public int ID(){
		return _id;
	}
	public Vehicle Vehicle(){
		return _vehicle;
	}	
	public Calendar EntryTime(){
		return _entryTime;
	}
	public Calendar ExitTime(){
		return _exitTime;
	}
	public synchronized ArrayList<RoadSection> ListOfRoadSectionsTravelled(){
		return this._listOfRoadSectionsTravelled;
	}
	
	public void setExit(Camera exit){
		this._exitTime = Calendar.getInstance();
		this._listOfRoadSectionsTravelled.add(exit.location());
		hasExited = true;
	}
	public void generateBill() throws TollIsNotCompleteException{
		if (!hasExited)
			throw new TollIsNotCompleteException(this._id, this._vehicle);
		
		//_bill = new Bill(, timeSpentOnRoad)
	}
	@Override
	public synchronized boolean equals(Object o){
		if (!(o instanceof TollRecord))
			return false;
		TollRecord tr = (TollRecord)o;
		if (tr.ID() != this._id)
			return false;
		//TODO finish off equal implementation
		return true;
	}
}
