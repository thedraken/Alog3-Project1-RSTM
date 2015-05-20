package lu.uni.algo3;

import java.util.ArrayList;
import java.util.List;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.OutOfRangeException;
import lu.uni.algo3.exceptions.TollIsNotCompleteException;

public class Biller implements RoadSectionObserver, Runnable{
	
	private int id;
	private List<RoadSection> roadsToObserve;
	
	private final int BREAKTIME = 5000; 
	
	public Biller(ArrayList<RoadSection> roadsToObserve){
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
	
	//checks if all the vehicles have left the road map
	public boolean isRoadMapEmpty(){
		for (Road r: Simulator.roadMap){
			for (RoadSection rs : r.listOfRoadSections()){
				if (!rs.getAllVehiclesInside().isEmpty())
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
		// a biller will do his job until there are no more vehicles on the road map
		while (!isRoadMapEmpty()){
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
