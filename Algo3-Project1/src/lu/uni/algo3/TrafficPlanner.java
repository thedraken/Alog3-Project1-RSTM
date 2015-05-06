package lu.uni.algo3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lu.uni.algo3.Vehicle.Category;

public class TrafficPlanner implements Runnable, RoadSectionObserver{
	
	private HashSet<RoadSection> roadsToObserve;
	
	public TrafficPlanner(HashSet<RoadSection> roadsToObserve){
		for(RoadSection rs : roadsToObserve){
			rs.registerObserver(this);
		}
		this.roadsToObserve = roadsToObserve;
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
			System.out.println("Traffic on " + rs + " is congested !");
			System.out.println("Traffic by category of vehicles:");
			for (Category c : Category.values()){
				System.out.println(c.toString() + ": " + getVehiclesPercentage(c, rs) + "%");
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
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
