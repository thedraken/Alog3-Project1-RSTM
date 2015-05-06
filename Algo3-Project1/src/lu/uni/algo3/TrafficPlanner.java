package lu.uni.algo3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lu.uni.algo3.Vehicle.Category;

public class TrafficPlanner implements RoadSectionObserver{
	
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

	@Override
	public void update(RoadSection rs) {
		// TODO Auto-generated method stub
		
	}

}
