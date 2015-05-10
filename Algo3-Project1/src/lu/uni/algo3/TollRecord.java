package lu.uni.algo3;

import java.util.Calendar;
import java.util.Date;

public class TollRecord {
	
	private int _id;
	private Vehicle _vehicle;
	private RoadSection _entry;
	private Date _entryTime;
	private RoadSection _exit;
	private Date _exitTime;
	
	public TollRecord(Vehicle vehicle, RoadSection entry){
		this._vehicle = vehicle;
		_entryTime = Calendar.getInstance().getTime();
		_exit = null;
		_exitTime = null;
	}
	public int ID(){
		return _id;
	}
	public Vehicle Vehicle(){
		return _vehicle;
	}	
	public RoadSection Entry(){
		return _entry;
	}
	public Date EntryTime(){
		return _entryTime;
	}
	public RoadSection Exit(){
		return _exit;
	}
	public Date ExitTime(){
		return _exitTime;
	}
	public void closeRecord(RoadSection exit){
		this._exitTime = Calendar.getInstance().getTime();
		this._exit = exit;
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
