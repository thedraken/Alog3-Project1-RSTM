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
import lu.uni.algo3.utils.Utils;

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
				if (v.equals(car)){
					System.out.println(this.toString() + " " + v + " found on " + rs + ". Sending unit to pursuit.");
					return rs;
				}
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
					System.out.println(this.toString() + " Date and time of photograph: " + p.dateTime() + " taken on " + rs + ", downloading from: " + p.locationOnDisk());
					photos.add(p);
				}
			}
		}
		return photos;
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
		getSpeedViolations(rs);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//police officers will stop working once there are no more vehicles on the road
		while (!roadIsEmpty()){
			//search for a random car on the run...
			//since there are no guarantees on the iteration order of the set
			//this will give us a (kind of...) random vehicle on a (kind of...) random road section...
			for (RoadSection rs : roadsToObserve){
				for (Vehicle v : rs.getAllVehiclesInside()){
					searchVehicle(v);
					System.out.println(this.toString() + "Downloading photographs of " + v + ":");
					getPhotosOfCar(v);
					break;
				}
				break;
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
		return "Police Officer " + id;
	}

}
