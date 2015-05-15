package lu.uni.algo3;

import java.util.ArrayList;
import java.util.HashSet;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.exceptions.TollIsNotCompleteException;
import lu.uni.algo3.utils.Utils;

public class TollRecord implements Comparable<TollRecord> {
	private Bill _bill;
	private int _id;
	private Vehicle _vehicle;
	private ArrayList<VehicleJourney> _listOfJourneys;
	private DateTime _entryTime;
	private DateTime _exitTime;
	private boolean hasExited = false;
	private int _hashExtra;
	private HashSet<TollRecordObserver> hshstTollRecordObserver;
	public TollRecord(Vehicle vehicle, Camera entry, DateTime dateTimePassedCamera){
		this._vehicle = vehicle;
		_entryTime = new DateTime();
		_listOfJourneys = new ArrayList<VehicleJourney>();
		_listOfJourneys.add(new VehicleJourney(entry.location(), dateTimePassedCamera));
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
	public synchronized void addRoadSection(Camera c, DateTime dateTimePassedCamera){
		if (!hasExited)
			_listOfJourneys.add(new VehicleJourney(c.location(), dateTimePassedCamera));
	}
	public synchronized ArrayList<VehicleJourney> ListOfVehicleJourneys(){
		return this._listOfJourneys;
	}
	public synchronized void setExit(Camera exit, DateTime dateTimePassedCamera){
		this._exitTime = new DateTime();
		this._listOfJourneys.add(new VehicleJourney(exit.location(), dateTimePassedCamera));
		hasExited = true;
	}
	public Bill generateBill() throws TollIsNotCompleteException{
		if (!hasExited)
			throw new TollIsNotCompleteException(this._id, this._vehicle);
		if (_bill == null){
			double totalDistanceTravelled = 0;
			for(VehicleJourney vj : ListOfVehicleJourneys())
				totalDistanceTravelled+= vj.RoadSectionTravelled().distance();
			Interval i = new Interval(this.EntryTime(), this.ExitTime());
			_bill = new Bill(totalDistanceTravelled, i);
		}
		return _bill;
	}
	public boolean speedViolation(){
		DateTime firstDate = null;
		DateTime secondDate;
		for(VehicleJourney vj: ListOfVehicleJourneys()){
			secondDate = vj.DateTimePassedCamera();
			if (firstDate != null){
				Interval i = new Interval(firstDate, secondDate);
				long milliseconds = i.toDurationMillis();
				double hours = milliseconds / (1000.0 * 60.0 * 60.0);
				if (vj.RoadSectionTravelled().distance() / hours > vj.RoadSectionTravelled().speedLimit())
					return true;
			}
			firstDate = vj.DateTimePassedCamera();
		}
		return false;
		
	}
	public synchronized void registerObserver(TollRecordObserver tro) throws ObjectExistsInCollectionException{
		if (hshstTollRecordObserver == null)
			hshstTollRecordObserver = new HashSet<TollRecordObserver>();
		if (hshstTollRecordObserver.contains(tro))
			throw new ObjectExistsInCollectionException();
		hshstTollRecordObserver.add(tro);
	}
	public synchronized void removeObserver(TollRecordObserver tro){
		if (hshstTollRecordObserver.contains(tro))
			hshstTollRecordObserver.remove(tro);
	}
	public synchronized void notifyObservers(){
		for(TollRecordObserver tro: hshstTollRecordObserver){
			tro.notify();
		}
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
