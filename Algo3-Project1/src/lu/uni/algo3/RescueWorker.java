package lu.uni.algo3;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class RescueWorker implements Runnable, RoadSectionObserver{
	
	private HashSet<RoadSection> roadsToObserve;
	
	public RescueWorker(HashSet<RoadSection> roadsToObserve){
		for(RoadSection rs : roadsToObserve){
			rs.registerObserver(this);
		}
		this.roadsToObserve = roadsToObserve;
	}
	//traffic workers will check the traffic to get to an accident
	//from a given direction, depending on the direction the vehicle was driving
	public void checkTrafficFromTo(RoadSection rs1, RoadSection rs2){
		TreeSet<RoadSection> orderedRoadSections = new TreeSet<RoadSection>(roadsToObserve);
		if (rs1.compareTo(rs2) < 0){
			Iterator<RoadSection> ascIterator = orderedRoadSections.iterator();
		}
		else{
			Iterator<RoadSection> descIterator = orderedRoadSections.descendingIterator();
		}
	}
	
	public RoadSection locateVehicle(Vehicle v){
		return null;
	}
	

	@Override
	public void updateRS(RoadSection rs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
