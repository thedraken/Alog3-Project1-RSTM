package lu.uni.algo3;

import java.util.ArrayList;
import java.util.HashMap;
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
	private HashMap<RoadSection, Boolean> _speedViolation;
	public TollRecord(Vehicle vehicle, RoadSection entry){
		this._vehicle = vehicle;
		_entryTime = new DateTime();
		_exitTime = null;
		_hashExtra = Utils.returnRandomInt();
		this._listOfRoadSectionsTravelled = new ArrayList<RoadSection>();
		_listOfRoadSectionsTravelled.add(entry);
		_speedViolation = new HashMap<RoadSection, Boolean>();
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
	//Adds a road section to the toll record, the car has travelled further
	public synchronized void addRoadSection(RoadSection rs){
		//We can't add it if the car has already left the toll system
		if (!hasExited){
			_listOfRoadSectionsTravelled.add(rs);
			//5% of the time a car will be speeding and we will catch them
			_speedViolation.put(rs, Utils.returnRandomInt(0, 100) < 5);
		}
	}
	//Total list of roadsections the car has travelled over during his entire joruney
	public synchronized ArrayList<RoadSection> ListOfRoadSectionsTranversed(){
		return this._listOfRoadSectionsTravelled;
	}
	//The car is now leaving the motorway
	public synchronized void setExit(RoadSection exit){
		this._exitTime = new DateTime();
		//Just make sure the last road section was added to the list
		if (_listOfRoadSectionsTravelled.get(_listOfRoadSectionsTravelled.size()-1) != exit)
			addRoadSection(exit);
		hasExited = true;
	}
	//Generate the bill for the biller
	public Bill generateBill() throws TollIsNotCompleteException{
		//We can only generate it when the driver has left the toll system
		if (!hasExited)
			throw new TollIsNotCompleteException(this._id, this._vehicle);
		if (_bill == null){
			double totalDistanceTravelled = 0;
			//Get the total distance travelled
			for(RoadSection rs : ListOfRoadSectionsTranversed())
				totalDistanceTravelled+= rs.distance();
			//Not used any more, was going to work out if car had sped, but working on real time isn't practical
			Interval i = new Interval(this.EntryTime(), this.ExitTime());
			_bill = new Bill(totalDistanceTravelled, i);
		}
		return _bill;
	}
	//Checks the hashmap if any key values matches the roadsectioned queried and the value is a boolean of true. If so, they sped on that roadsection
	public synchronized boolean speedViolation(RoadSection rs){
		return _speedViolation.entrySet().stream().filter(p -> p.getValue() && p.getKey() == rs).count() > 0;
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
