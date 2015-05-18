package lu.uni.algo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.Vehicle.Category;
import lu.uni.algo3.exceptions.OutOfRangeException;

public class PoliceOfficer implements Runnable, RoadSectionObserver{
	
	private int id;
	private Set<RoadSection> roadsToObserve;
	
	private final int BREAKTIME = 5000; 
	
	public PoliceOfficer(HashSet<RoadSection> roadsToObserve){
		//SQLIndexer is responsible to increment and assign unique IDs
		SQLIndexer indexer = SQLIndexer.getInstance();
		try {
			this.id = indexer.getNewID(SQLType.PoliceOfficer);
		} catch (OutOfRangeException e) {
			System.err.println(this.toString() + "\n" + e.getMessage());
		}
		for(RoadSection rs : roadsToObserve){
			rs.registerObserver(this);
		}
		this.roadsToObserve = Collections.synchronizedSet(new HashSet<RoadSection>(roadsToObserve));
	}
	
	public void getSpeedViolations(RoadSection rs){
		for (Vehicle v : rs.getAllVehiclesInside()){
			if (v.getTollRecord().speedViolation()){
				System.out.println(this.toString() + " " + v + " speeding on " + rs + " !");
			}
		}
	}
	
	public RoadSection searchVehicle(Vehicle car){
		for (RoadSection rs : roadsToObserve){
			for (Vehicle v : rs.getAllVehiclesInside()){
				if (v.equals(car))
					return rs;
			}
		}
		return null;
	}
	
	public List<Vehicle> searchVehiclesByCategory(Category category){
		List<Vehicle> vehicleOfCat = new ArrayList<Vehicle>();
		for (RoadSection rs : roadsToObserve){
			for (Vehicle v : rs.getAllVehiclesInside()){
				if (v.getCategory().equals(category))
					vehicleOfCat.add(v);
			}
		}
		return vehicleOfCat;
	}
	
	public List<Photograph> getPhotos(Date dateTime){
		List<Photograph> photos = new ArrayList<Photograph>();
		for (RoadSection rs : roadsToObserve){
			for (Photograph p : rs.getCamera().photosTaken()){
				if (p.dateTime().equals(dateTime)){
					photos.add(p);
				}
			}
		}
		return photos;
	}
	
	public List<Photograph> getPhotosOfCar(Vehicle car){
		List<Photograph> photos = new ArrayList<Photograph>();
		for (RoadSection rs : roadsToObserve){
			for (Photograph p : rs.getCamera().photosTaken()){
				if (p.vehicle().equals(car)){
					photos.add(p);
				}
			}
		}
		return photos;
	}

	@Override
	public void updateRS(RoadSection rs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString(){
		return "Police Officer " + id;
	}

}
