package lu.uni.algo3;

import java.util.ArrayList;
import java.util.List;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.Vehicle.Category;
import lu.uni.algo3.exceptions.OutOfRangeException;

public class TrafficPlanner implements Runnable, RoadSectionObserver{
	
	private int id;
	private List<RoadSection> roadsToObserve;
	
	private final int BREAKTIME = 5000;
	
	public TrafficPlanner(ArrayList<RoadSection> roadsToObserve){
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
		this.roadsToObserve = new ArrayList<RoadSection>(roadsToObserve);
	}
	
	public void addRoadSection(RoadSection r){
		r.registerObserver(this);
		roadsToObserve.add(r);
	}
	
	public void removeRoadSection(RoadSection r){
		r.removeObserver(this);
		roadsToObserve.remove(r);
	}
	//traffic planners will keep a record of the road sections where the traffic is problematic
	public List<RoadSection> getCongestedSections(){
		List<RoadSection> busySections = new ArrayList<RoadSection>();
		for (RoadSection rs: roadsToObserve){
			if(rs.isBusy())
				busySections.add(rs);
		}
		return busySections;
	}
	//traffic planners will run statistics on the vehicles running on the road
	public double getVehiclesPercentage(Category cat, RoadSection rs){
		List <Vehicle> vehiclesOfCategory = rs.getVehiclesByCategory(cat);
		return ((vehiclesOfCategory.size() * 100) / rs.getOccupation());
	}
	//warnings will be sent to the public when a given road section is congested and the traffic on that road analysed
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
		//a traffic planner will do his job until all cars have left the road map
		while(!isRoadMapEmpty()){
			for (RoadSection rs : roadsToObserve){
				sendCongestionWarning(rs);
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
		return "Traffic Planner " + id;
	}
	

}
