package lu.uni.algo3;

import java.util.Date;

public class TollRecord {
	
	private int id;
	private Vehicle vehicle;
	private RoadSection entry;
	private Date entryTime;
	private RoadSection exit;
	private Date exitTime;
	
	public TollRecord(Vehicle vehicle, RoadSection entry, Date entryTime){
		this.vehicle = vehicle;
		this.entry = entry;
		this.entryTime = entryTime;
	}
	
	public Vehicle getVehicle(){
		return vehicle;
	}
	
	public RoadSection getEntry(){
		return entry;
	}
	
	public Date getEntryTime(){
		return entryTime;
	}
	
	public RoadSection getExit(){
		return exit;
	}
	
	public void setExit(RoadSection exit){
		this.exit = exit;
	}
	
	public Date getExitTime(){
		return exitTime;
	}
	
	public void setExitTime(Date exitTime){
		this.exitTime = exitTime;
	}

}
