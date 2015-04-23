package lu.uni.algo3;

import java.util.Random;

public class Vehicle implements Runnable, Comparable<Vehicle>{
	
	private int id;
	private String licencePlate;
	private String transponder = null;
	private Category category;
	private RoadSection currentPosition;
	private Random random;
	boolean exitRoadMap = false;
	// TODO listOfRecords

	public Vehicle(int id, String licencePlate, Category category){
		this.id = id;
		this.licencePlate = licencePlate;
		this.category = category;
		random = new Random();
		// TODO implement random selection of starting point for vehicle
		// something like:
		//int roadSectionNum = random.nextInt(Simulator.NUMROFROADS);
		//this.currentPosition = ...

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
	
	//only trucks have transponders?
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
			//Thread.sleep(random.nextInt(Simulator.MAXCARWAITTIME));
			
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
	public boolean equals(Object o){
		//TODO
		return false;
	}
	
	@Override
	public int hashCode(){
		//TODO
		return -1;
	}
	
	@Override
	public int compareTo(Vehicle v){
		//TODO
		return -1;
	}
	
	
}
