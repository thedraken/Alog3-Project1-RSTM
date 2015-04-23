package lu.uni.algo3;

import java.util.HashSet;

public class Biller implements TollRecordObserver, Runnable{
	
	private TollRecord tRecord;
	private HashSet<Vehicle> listOfVehicles;
	
	public Biller(TollRecord tRecord){
		listOfVehicles = new HashSet<Vehicle>();
		this.tRecord = tRecord;
		//tRecord.register(this);
	}
	
	@Override
	public void update(){
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//tRecord.remove(this);
	}
	
	public void monthlyBill(){
		
	}

}
