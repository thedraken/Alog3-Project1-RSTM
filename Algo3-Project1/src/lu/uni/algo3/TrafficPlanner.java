package lu.uni.algo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.Vehicle.Category;
import lu.uni.algo3.exceptions.OutOfRangeException;

public class TrafficPlanner implements Runnable, RoadSectionObserver{
	
	private int id;
	private Set<RoadSection> roadsToObserve;
	
	private final int BREAKTIME = 5000;
	
	public TrafficPlanner(HashSet<RoadSection> roadsToObserve){
		//SQLIndexer is responsible to increment and assign unique IDs
		SQLIndexer indexer = SQLIndexer.getInstance();
		try {
			this.id = indexer.getNewID(SQLType.TrafficPlanner);
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
	
	public List<RoadSection> getCongestedSections(){
		List<RoadSection> busySections = new ArrayList<RoadSection>();
		for (RoadSection rs: roadsToObserve){
			if(rs.isBusy())
				busySections.add(rs);
		}
		return busySections;
	}
	
	public double getVehiclesPercentage(Category cat, RoadSection rs){
		List <Vehicle> vehiclesOfCategory = rs.getVehiclesByCategory(cat);
		return ((vehiclesOfCategory.size() * 100) / rs.getOccupation());
	}
	
	public void sendCongestionWarning(RoadSection rs){
		if (rs.isBusy()){
			System.out.println(this.toString() + " Traffic on " + rs + " is congested !");
			System.out.println(this.toString() + " Traffic by category of vehicles:");
			for (Category c : Category.values()){
				System.out.println(this.toString() + " " + c.toString() + ": " + getVehiclesPercentage(c, rs) + "%");
			}
		}
	}

	@Override
	public void updateRS(RoadSection rs) {
		sendCongestionWarning(rs);
	}

	@Override
	public void run() {
		for (RoadSection rs : roadsToObserve){
			sendCongestionWarning(rs);
		}
		try {
			Thread.sleep(BREAKTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		return "Traffic Planner " + id;
	}
	

}
