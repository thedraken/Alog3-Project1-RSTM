package lu.uni.algo3;
import java.util.Random;
import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.OutOfRangeException;

public class Vehicle implements Runnable, Comparable<Vehicle>{
	
	public enum Category{
		HGV,
		Car,
		LGV,
		Motorbike,
		Other
	}
	private int id;
	private String licencePlate;
	private String transponder = null;
	private Category category;
	private RoadSection currentPosition;
	private Random random;
	boolean exitRoadMap = false;
	// TODO listOfRecords
	
	//maximum time a vehicle takes to one roadSection to the next
	//should this be here on in the simulator?
	private static final int MAXCARWAITTIME = 10000;
	
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

	public Vehicle(String licencePlate, Category category){
		//SQLIndexer is responsible to increment and assign unique IDs
		SQLIndexer indexer = SQLIndexer.getInstance();
		try {
			this.id = indexer.getNewID(SQLType.Vehicle);
		} catch (OutOfRangeException e) {
			System.err.println(e.getMessage());
		}
		this.licencePlate = licencePlate;
		this.category = category;
		random = new Random();
		// TODO implement random selection of starting point for vehicle
		// something like:
		//int roadSectionNum = random.nextInt(Simulator.NUMROFROADS);
		//this.currentPosition = ...
		//also random direction (ex. increasing/decreasing, in respect to roadSection number...)

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
	
	//only trucks have transponders(?)
	public void setTransponder(String transponder){
		if(category.equals(Category.HGV) || category.equals(Category.LGV)){
			this.transponder = transponder; 
		}
	}
	
	public String getTransponder(){
		return transponder;
	}

	@Override
	public void run() {
		// TODO implement run()
		
		while(!exitRoadMap){
			//enter roadSection
			//...
			
			//move and simulate different random speeds
			try {
				Thread.sleep(random.nextInt(MAXCARWAITTIME));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//leave roadSection 
			//to next roadSection or leave Road (to other Road or not)
			endOfRoadSectionBehavior();
			//...
		}	
	}
	
	public void endOfRoadSectionBehavior(){
		// TODO
		//the behavior of the vehicle is randomly generated
		int behavior = random.nextInt(3);
		
		switch (behavior) {
			//case 0: the vehicle continues to the next road section of this road, if there exists one
			//if not try next case
			case 0:
//				if(currentPosition.RoadContinuesAfterSection){
//					currentPosition = currentPosition.nextRoadSection;
//					break;
//				}
			//case 1: the vehicle changes road, updating its position to the roadSection of the other road
			//that has a connection to this one
			//if no connection to other road exits, we try the next case
			case 1:
//				if(currentPosition.hasConectionToOtherRoad){
//					currentPosition = currentPosition.otherRoad;
//					break;
//				}
			//case 2 and default: the vehicle exits the road map - the thread finishes its execution
			case 2: default:
				exitRoadMap = true;
				break;
		}
	}
	
	@Override
	//two vehicles will be considered equal if they have the same id
	//(should licencePlate be included?)
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
		//TODO
		return -1;
	}
}
