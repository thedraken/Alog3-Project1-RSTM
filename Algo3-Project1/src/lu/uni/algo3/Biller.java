package lu.uni.algo3;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.exceptions.TollIsNotCompleteException;

public class Biller implements RoadSectionObserver, Runnable{
	
	private int id;
	private Set<RoadSection> roadsToObserve;
	
	private final int BREAKTIME = 5000; 
	
	public Biller(HashSet<RoadSection> roadsToObserve){
		//SQLIndexer is responsible to increment and assign unique IDs
		SQLIndexer indexer = SQLIndexer.getInstance();
		try {
			this.id = indexer.getNewID(SQLType.Biller);
		} catch (OutOfRangeException e) {
			System.err.println(this.toString() + "\n" + e.getMessage());
		}
		for(RoadSection rs : roadsToObserve){
			rs.registerObserver(this);
		}
		this.roadsToObserve = Collections.synchronizedSet(new HashSet<RoadSection>(roadsToObserve));
	}
	
	public void printBill(RoadSection rs){
		for (Vehicle v : rs.getAllVehiclesInside()){
			if (v.getTollRecord().ExitTime() != null){
				try {
					Bill finalBill = v.getTollRecord().generateBill();
					System.out.println(this.toString() + " Bill for vehicle with ID " + v.getID() + ": " + "amount: " + finalBill.AmountDue());
				} catch (TollIsNotCompleteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
	public void updateRS(RoadSection rs){
		printBill(rs);
	}

	@Override
	public void run() {
		// a biller will do his job until there are no more vehicles on the road
		while (!roadIsEmpty()){
			for (RoadSection rs : roadsToObserve){
				printBill(rs);
			}
			try {
				Thread.sleep(BREAKTIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//tRecord.remove(this);
	}
	
	@Override
	public String toString(){
		return "Biller " + id;
	}

}
