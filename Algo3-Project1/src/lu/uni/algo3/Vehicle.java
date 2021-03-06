package lu.uni.algo3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.ExceedMaxOccupationException;
import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.utils.Utils;

public class Vehicle implements Runnable, Comparable<Vehicle>{
	
	public enum Category{
		HGV,
		Car,
		LGV,
		Motorbike,
		Other
	}
	public enum Direction{
		ascending,
		descending
	}
	private int id;
	private String licencePlate;
	private String transponder;
	private Category category;
	private RoadSection currentPosition;
	private Direction direction;
	private boolean stopped;
	private boolean exitRoadMap;
	private TollRecord tollR;
	private boolean changedRoadLastTime = false;
	
	//minimum (1 sec) and maximum time (5 sec) a vehicle takes to go from one roadSection to the next
	private static final int MINCARWAITTIME = 1000;
	private static final int MAXCARWAITTIME = 5000;
	//the time a car stops due to malfunction/accident
	private static final int CARSTOPTIME = 15000;
	
	/*suggestion for SQLIndexer singleton:
	 * since we have multiple threads accessing this method we should probably use
	 * double checked locking to make sure only one instance is created:
	 
	public static SQLIndexer getInstance(){
		if (instance == null)
			syncDoubleCheck();
		return instance;
	}
	private static synchronized void syncDoubleCheck(){
		if (instance == null)
			instance = new SQLIndexer();
	}
	 */

	public Vehicle(String licencePlate, Category cat) {
		//SQLIndexer is responsible to increment and assign unique IDs
		SQLIndexer indexer = SQLIndexer.getInstance();
		try {
			this.id = indexer.getNewID(SQLType.Vehicle);
		} catch (OutOfRangeException e) {
			System.err.println(e.getMessage());
		}
		this.licencePlate = licencePlate;
		this.category = cat;
		this.exitRoadMap = false;
		this.stopped = false;
		this.tollR = null;
		this.transponder = null;
		this.currentPosition = null;
	}
	
	public int getID(){
		return id;
	}
	
	public String getLicencePlate(){
		return licencePlate;
	}
	
	public Category getCategory(){
		return category;
	}
	
	//only trucks have transponders
	public void setTransponder(String transponder){
		if(category.equals(Category.HGV) || category.equals(Category.LGV)){
			this.transponder = transponder; 
		}
	}
	
	public String getTransponder(){
		return transponder;
	}
	
	public boolean hasStopped(){
		return stopped;
	}
	
	public TollRecord getTollRecord(){
		return tollR;
	}
	
	public void setTollRecord(TollRecord tollR){
		this.tollR = tollR;
	}
	
	public boolean hasExitedRoadMap(){
		return exitRoadMap;
	}
	//when moving to another road section, a vehicle will first try to enter that road section
	//and only then will it be removed from the current road section.
	//in case it doesn't succeed, it still keeps the current position
	public void changePosition(RoadSection rs){
		try {
			rs.insertVehicle(this);
			currentPosition.removeVehicle(this);
			currentPosition = rs;
			System.out.println(this.toString() +" changing to section "+ rs.number() + " on road " + rs.road().name() );
		} catch (ExceedMaxOccupationException e) {
			System.err.println(this.toString() + "\n" + e.getMessage());
		} catch (ObjectExistsInCollectionException e) {
			System.err.println(this.toString() + "\n" + e.getMessage());
		}
	}

	@Override
	public void run() {
		//enter roadMap
		// random selection of starting point for vehicle
		int roadSectionNum = Utils.returnRandomInt(1, SQLIndexer.getInstance().getNumberOfRoadSections());
		while(currentPosition == null){
			for (Road r : Simulator.roadMap){
				List<RoadSection> list = Predicates.filterRoadSections(r.listOfRoadSections(), Predicates.roadSectionByNumber(roadSectionNum));
				if (list.size() > 0){
					try {
						RoadSection rs = list.get(0);
						rs.insertVehicle(this);
						this.currentPosition = rs;
						System.out.println(this.toString() + " Entering road " + r.name() +" at section "+ rs.number());
					} catch (ExceedMaxOccupationException e) {
						System.err.println(this.toString() + "\n" + e.getMessage());
					} catch (ObjectExistsInCollectionException e) {
						System.err.println(this.toString() + "\n" + e.getMessage());
					}
				}
			}
		}
		//random selection of the direction the vehicle will take
		if (Utils.returnRandomBoolean(0.5)){
			this.direction = Direction.ascending;
		}
		else{
			this.direction = Direction.descending;
		}
		
		while(!exitRoadMap){
			
			//5% of the cars will stop on the road due to malfunctioning/accident
			boolean stop = Utils.returnRandomBoolean(0.05);
			if(stop){
				stopped = true;
				try {
					Thread.sleep(CARSTOPTIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				stopped = false;
			}
			
			//move and simulate different random speeds
			try {
				Thread.sleep(Utils.returnRandomInt(MINCARWAITTIME, MAXCARWAITTIME));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//leave roadSection 
			//to next roadSection on same road or leave Road (to other Road or out of the map)
			endOfRoadSectionBehavior();
		}	
	}
	
	public void endOfRoadSectionBehavior(){
		int behavior = 2;
		//the behavior of the vehicle is randomly generated
		//the vehicle has 60% probability of staying on the same road, if possible
		if (Utils.returnRandomBoolean(0.6)){
			behavior = 0;
		}
		else {
			//if leaving the current road, he will have 50% probability of changing to another road, if there is one
			//Unless the car has previously done a turn in the last move, prevents the car looping too much. This has been set to 30% instead
			if (Utils.returnRandomBoolean(0.5) && !changedRoadLastTime){
				behavior = 1;
			}
			else if (changedRoadLastTime && Utils.returnRandomBoolean(0.3))
				behavior = 1;
		}
		
		RoadSection currentRS;
		boolean success = false;
		
		switch (behavior) {
			//case 0: the vehicle continues to the next road section of this road, if there exists one
			//if not try next case
			case 0:
				for (Road r : Simulator.roadMap){
					if (direction.equals(Direction.ascending)){
						Iterator<RoadSection> it = r.listOfRoadSections().iterator();
						while(it.hasNext()){
							if (it.next().equals(currentPosition) && it.hasNext()){
								changePosition(it.next());
								success = true;
								changedRoadLastTime = false;
							}
							if (success)
								break;
						}
						if (success){
							break;
						} 
					}
					else{
						ListIterator<RoadSection> it = r.listOfRoadSections().listIterator(r.listOfRoadSections().size());
						while(it.hasPrevious()){
							if (it.previous().equals(currentPosition) && it.hasPrevious()){
								changePosition(it.previous());
								success = true;
								changedRoadLastTime = false;
							}
						}
						if (success){
							break;
						} 
					}
				}	
				if (success){
					break;
				}		
			//case 1: the vehicle changes road, updating its position to the roadSection of the other road
			//that has a connection to this one
			//if no connection to other road exits, we try the next case
			case 1:
				for (Road r : Simulator.roadMap){
					Iterator<RoadSection> it = r.listOfRoadSections().iterator();
					while(it.hasNext()){
						currentRS = it.next();
						if (currentRS.equals(currentPosition) && !currentRS.connectionToOtherRoadSections().isEmpty()){
							ArrayList<RoadSection> connections = new ArrayList<RoadSection>(currentRS.connectionToOtherRoadSections());
							changePosition(connections.get(Utils.returnRandomInt(0, connections.size()-1)));
							success = true;
							changedRoadLastTime = true;
							//if a vehicle changes road it gets the possibility to change direction
							if (Utils.returnRandomBoolean(0.5)){
								this.direction = Direction.ascending;
							}
							else{
								this.direction = Direction.descending;
							}
						}
					}
					if (success){
						break;
					}
				}
				if (success){
					break;
				}
			//case 2 and default: the vehicle exits the road map - the thread finishes its execution
			case 2: default:
				exitRoadMap = true;
				currentPosition.removeVehicle(this);
				System.out.println(this.toString() + " leaving road map !");
				break;
		}
	}
	
	@Override
	//two vehicles will be considered equal if they have the same id
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Vehicle)){
			return false;
		}
		if(this == obj){
			return true;
		}
		
		Vehicle vehicle = (Vehicle)obj;
		if(this.id == vehicle.id){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return id;
	}
	
	@Override
	public int compareTo(Vehicle v){
		if (id < v.getID())
			return -1;
		if (id > v.getID())
			return 1;
		if (this.hashCode() > v.hashCode())
			return 1;
		if (this.hashCode() < v.hashCode())
			return -1;
		return 0;
	}
	
	@Override
	public String toString(){
		return "Vehicle " + id;
	}
}
