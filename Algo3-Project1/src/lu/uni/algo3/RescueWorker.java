package lu.uni.algo3;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.OutOfRangeException;

public class RescueWorker implements Runnable, RoadSectionObserver{
	
	private int id;
	private Set<RoadSection> roadsToObserve;
	
	private final int BREAKTIME = 5000; 
	
	public RescueWorker(HashSet<RoadSection> roadsToObserve){
		//SQLIndexer is responsible to increment and assign unique IDs
		SQLIndexer indexer = SQLIndexer.getInstance();
		try {
			this.id = indexer.getNewID(SQLType.RescueWorker);
		} catch (OutOfRangeException e) {
			System.err.println(this.toString() + "\n" + e.getMessage());
		}
		for(RoadSection rs : roadsToObserve){
			rs.registerObserver(this);
		}
		this.roadsToObserve = Collections.synchronizedSet(new HashSet<RoadSection>(roadsToObserve));
	}
	
	public void addRoadSection(RoadSection r){
		r.registerObserver(this);
		roadsToObserve.add(r);
	}
	
	public void removeRoadSection(RoadSection r){
		r.removeObserver(this);
		roadsToObserve.remove(r);
	}
	
	//traffic workers will check the traffic to get to an accident
	//on a given destination road section
	public void checkTrafficTo(RoadSection rs){
		TreeSet<RoadSection> orderedRoadSections = new TreeSet<RoadSection>(roadsToObserve);
		System.out.println(this.toString() + " Checking traffic to get to " + rs);
		boolean clear = true;
		Iterator<RoadSection> iterator = orderedRoadSections.iterator();
		//check for traffic jams from the beginning of the road until the desired road section
		while (iterator.hasNext()){
			RoadSection r = iterator.next();
			if (r.isBusy()){
				System.out.println(this.toString() + " Traffic on " + r + " is congested !");
				clear = false;
			}
			if (clear){
				System.out.println(this.toString() + " Traffic is flowing !");
			}
			//once we reach the destination, we don't need to continue checking the traffic
			//on the rest of the road
			if(r.equals(rs)){
				break;
			}
		}
	}
	//allows the rescue worker to locate a vehicle
	//possibly in need of assistance
	public RoadSection locateVehicle(Vehicle v){
		for (RoadSection rs : roadsToObserve){
			if (rs.alreadyInside(v))
				return rs;
		}
		return null;
	}
	
	public void getPossibleAccident(RoadSection rs){
		if (!rs.getCamera().stationaryVehicles().isEmpty()){
			for (Vehicle v : rs.getCamera().stationaryVehicles()){
				System.out.println(this.toString() + " " + v + " stopped on " + rs);
				checkTrafficTo(rs);
			}
		}
	}
	
	//checks if all the road sections have no vehicles inside
	public boolean roadIsEmpty(){
		for (RoadSection rs : roadsToObserve){
			if (!rs.getAllVehiclesInside().isEmpty()){
				return false;
			}
		}
		return true;
	}

	@Override
	public void updateRS(RoadSection rs) {
		if (rs.isBusy())
			System.out.println(this.toString() + " Attention! Traffic on " + rs + " is getting busy !");
		getPossibleAccident(rs);
	}
	
	@Override
	public void run() {
		//a rescue worker will do his job until there are no more cars on the road
		while (!roadIsEmpty()){
			for (RoadSection rs : roadsToObserve){
				getPossibleAccident(rs);
			}
			try {
				Thread.sleep(BREAKTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public String toString(){
		return "Road Worker " + id;
	}

}
