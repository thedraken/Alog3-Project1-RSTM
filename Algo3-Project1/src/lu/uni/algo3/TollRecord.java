package lu.uni.algo3;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import lu.uni.algo3.exceptions.TollIsNotCompleteException;
import lu.uni.algo3.utils.Utils;

public class TollRecord implements Comparable<TollRecord> {
	private Bill _bill;
	private int _id;
	private Vehicle _vehicle;
	private DateTime _entryTime;
	private DateTime _exitTime;
	private boolean hasExited = false;
	private int _hashExtra;
	private ArrayList<RoadSection> _listOfRoadSectionsTravelled;
	public TollRecord(Vehicle vehicle, RoadSection entry, DateTime dateTimePassedCamera){
		this._vehicle = vehicle;
		_entryTime = new DateTime();
		_exitTime = null;
		_hashExtra = Utils.returnRandomInt();
		this._listOfRoadSectionsTravelled = new ArrayList<RoadSection>();
		_listOfRoadSectionsTravelled.add(entry);
	}
	public int ID(){
		return _id;
	}
	public Vehicle Vehicle(){
		return _vehicle;
	}	
	public DateTime EntryTime(){
		return _entryTime;
	}
	public DateTime ExitTime(){
		return _exitTime;
	}
	public synchronized void addRoadSection(RoadSection rs){
		if (!hasExited)
			_listOfRoadSectionsTravelled.add(rs);
	}
	public synchronized ArrayList<RoadSection> ListOfRoadSectionsTranversed(){
		return this._listOfRoadSectionsTravelled;
	}
	public synchronized void setExit(RoadSection exit){
		this._exitTime = new DateTime();
		this._listOfRoadSectionsTravelled.add(exit);
		hasExited = true;
	}
	public Bill generateBill() throws TollIsNotCompleteException{
		if (!hasExited)
			throw new TollIsNotCompleteException(this._id, this._vehicle);
		if (_bill == null){
			double totalDistanceTravelled = 0;
			for(RoadSection rs : ListOfRoadSectionsTranversed())
				totalDistanceTravelled+= rs.distance();
			Interval i = new Interval(this.EntryTime(), this.ExitTime());
			_bill = new Bill(totalDistanceTravelled, i);
		}
		return _bill;
	}
	public boolean speedViolation(){
		//We work on a 5% chance of a speed violation
		return (Utils.returnRandomInt(0, 100) <= 5);
	}
	@Override
	public boolean equals(Object o){
		if (!(o instanceof TollRecord))
			return false;
		TollRecord tr = (TollRecord)o;
		if (tr.ID() != this._id)
			return false;
		if (tr.hashCode() != this.hashCode())
			return false;
		if (this.Vehicle() != tr.Vehicle())
			return false;
		if (this.EntryTime() != tr.EntryTime())
			return false;
		if (this.ExitTime() != tr.ExitTime())
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		return this.ID() + this._hashExtra;
	}
	@Override
	public int compareTo(TollRecord o) {
		if (this.ID() > o.ID())
			return -1;
		if (this.ID() < o.ID())
			return 1;
		return 0;
	}
}
