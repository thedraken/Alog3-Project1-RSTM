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
	private final String SIGNATURE = "RW" + id + ": ";
	
	public RescueWorker(HashSet<RoadSection> roadsToObserve){
		//SQLIndexer is responsible to increment and assign unique IDs
		SQLIndexer indexer = SQLIndexer.getInstance();
		try {
			this.id = indexer.getNewID(SQLType.Vehicle);
		} catch (OutOfRangeException e) {
			System.err.println(SIGNATURE + e.getMessage());
		}
		for(RoadSection rs : roadsToObserve){
			rs.registerObserver(this);
		}
		this.roadsToObserve = Collections.synchronizedSet(new HashSet<RoadSection>(roadsToObserve));
	}
	
	//traffic workers will check the traffic to get to an accident
	//on a given destination road section
	public void checkTrafficTo(RoadSection rs){
		TreeSet<RoadSection> orderedRoadSections = new TreeSet<RoadSection>(roadsToObserve);
		System.out.println(SIGNATURE + "Checking traffic on road from to " + rs);
		boolean clear = true;
		Iterator<RoadSection> iterator = orderedRoadSections.iterator();
		//check for traffic jams from the beginning of the road until the desired road section
		while (iterator.hasNext()){
			RoadSection r = iterator.next();
			if (r.isBusy()){
				System.out.println(SIGNATURE + "Traffic on " + r + " is congested !");
				clear = false;
			}
			if (clear){
				System.out.println(SIGNATURE + "Traffic is flowing !");
			}
			//one we reach the destination, we don't need to continue checking the traffic
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
	

	@Override
	public void updateRS(RoadSection rs) {
		if (rs.isBusy())
			System.out.println(SIGNATURE + "Attention! Traffic on " + rs + " is getting busy !");
		for (RoadSection r : roadsToObserve){
			if (rs.number() == r.number()){
				roadsToObserve.remove(r);
				roadsToObserve.add(rs);
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
