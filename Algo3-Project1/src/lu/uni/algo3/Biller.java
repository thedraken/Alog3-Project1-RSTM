package lu.uni.algo3;

import java.util.HashSet;
import java.util.Set;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.exceptions.TollIsNotCompleteException;

public class Biller implements Runnable{
	
	private int id;
	private Set<Vehicle> billedCars;
	
	private final int BREAKTIME = 5000; 
	
	public Biller(){
		//SQLIndexer is responsible to increment and assign unique IDs
		SQLIndexer indexer = SQLIndexer.getInstance();
		try {
			this.id = indexer.getNewID(SQLType.Biller);
		} catch (OutOfRangeException e) {
			System.err.println(this.toString() + "\n" + e.getMessage());
		}
		this.billedCars = new HashSet<Vehicle>();
	}
	
	public void printBill(){
		for (Vehicle v : Simulator.listOfVehicles){
			if (v.getTollRecord() != null && v.getTollRecord().ExitTime() != null && !billedCars.contains(v)){
				try {
					Bill finalBill = v.getTollRecord().generateBill();
					System.out.println(this.toString() + " Bill for vehicle with ID " + v.getID() + ": " + "amount: " + finalBill.AmountDue());
					billedCars.add(v);
				} catch (TollIsNotCompleteException e) {
					System.err.println(this.toString() + "\n" + e.getMessage());
				}
			}
		}
	}
	
	//checks if all the vehicles have left the road map
	public boolean isRoadMapEmpty(){
		return Predicates.filterThreads(Simulator.listOfCarThreads, Predicates.runningThreads(Simulator.listOfCarThreads)).size() == 0;
	}
	
	@Override
	public void run() {
		// a biller will do his job until there are no more vehicles on the road map
		while (!isRoadMapEmpty()){
			printBill();
			try {
				Thread.sleep(BREAKTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//bill the last car that has exited
		try {
			Thread.sleep(BREAKTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		printBill();
	}
	
	@Override
	public String toString(){
		return "Biller " + id;
	}

}
