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
	private ArrayList<RoadSection> _listOfRoadSectionsTravelled;
	private DateTime _entryTime;
	private DateTime _exitTime;
	private boolean hasExited = false;
	private int _hashExtra;
	public TollRecord(Vehicle vehicle, Camera entry){
		this._vehicle = vehicle;
		_entryTime = new DateTime();
		_listOfRoadSectionsTravelled = new ArrayList<RoadSection>();
		_listOfRoadSectionsTravelled.add(entry.location());
		_exitTime = null;
		_hashExtra = Utils.returnRandomInt(); 
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
	public synchronized void addRoadSection(Camera c){
		if (!hasExited)
			_listOfRoadSectionsTravelled.add(c.location());
	}
	public synchronized ArrayList<RoadSection> ListOfRoadSectionsTravelled(){
		return this._listOfRoadSectionsTravelled;
	}
	public synchronized void setExit(Camera exit){
		this._exitTime = new DateTime();
		this._listOfRoadSectionsTravelled.add(exit.location());
		hasExited = true;
	}
	public void generateBill() throws TollIsNotCompleteException{
		if (!hasExited)
			throw new TollIsNotCompleteException(this._id, this._vehicle);
		double totalDistanceTravelled = 0;
		for(RoadSection rs : ListOfRoadSectionsTravelled())
			totalDistanceTravelled+= rs.distance();
		Interval i = new Interval(this.EntryTime(), this.ExitTime());
		_bill = new Bill(totalDistanceTravelled, i);
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
		//TODO finish implementation
		return true;
	}
	@Override
	public int hashCode() {
		return this.ID() + this._hashExtra;
	}
	@Override
	public int compareTo(TollRecord o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
