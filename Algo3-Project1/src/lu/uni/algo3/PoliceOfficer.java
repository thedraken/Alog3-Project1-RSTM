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
	
	public void addRoadSection(RoadSection r){
		r.registerObserver(this);
		roadsToObserve.add(r);
	}
	
	public void removeRoadSection(RoadSection r){
		r.removeObserver(this);
		roadsToObserve.remove(r);
	}
	
	public void getSpeedViolations(RoadSection rs){
		for (Vehicle v : rs.getAllVehiclesInside()){
			if (v.getTollRecord().speedViolation(rs)){
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
					System.out.println(this.toString() + " Found " + v + " on " + rs + " of category " + category.toString());
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
		//police officers will stop working once there are no more vehicles on the road
		while (!roadIsEmpty()){
			//search for a random car on the run...
			//we select a random road section, then a random vehicle driving on that section based on this:
			//http://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
			int roadSectionItem = Utils.returnRandomInt(0, roadsToObserve.size());
			int i = 0, j = 0;
			RoadSection randomRS = null;

			for (RoadSection rs : roadsToObserve){
				if (i == roadSectionItem){
					randomRS = rs;
					break;
				}
				i++;
			}
			int vehicleItem = Utils.returnRandomInt(0, randomRS.getAllVehiclesInside().size());
			for (Vehicle v : randomRS.getAllVehiclesInside()){
				if (j == vehicleItem){
					searchVehicle(v);
					System.out.println(this.toString() + "Downloading photographs of " + v + ":");
					getPhotosOfCar(v);
					break;
				}
				j++;
			}
			//grab a cup of coffee
			try {
				Thread.sleep(BREAKTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//look for a random category of vehicles
			Category randomCategory;
			if (Utils.returnRandomBoolean(0.5)){
				randomCategory = Category.Car;
			}
			else if (Utils.returnRandomBoolean(0.5)){
				randomCategory = Category.LGV;
			}
			else if (Utils.returnRandomBoolean(0.5)){
				randomCategory = Category.HGV;
			}
			else if (Utils.returnRandomBoolean(0.5)){
				randomCategory = Category.Motorbike;
			}
			else {
				randomCategory = Category.Other;
			}
			searchVehiclesByCategory(randomCategory);
			//go for a smoke
			try {
				Thread.sleep(BREAKTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//look for pictures taken at a given random date/time on random road section???
		}
		
	}
	
	@Override
	public String toString(){
		return "Police Officer " + id;
	}

}
