package lu.uni.algo3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
//Uses jar file from Joda Time
//Found at: https://github.com/JodaOrg/joda-time/releases
//Java 1.8 doesn't have an equivalence of timespan at the moment
import org.joda.time.Interval;

public class Bill {
	//For a bill, I would assume we calculate the money on distance travelled
	private double _chargeAmmount = 0.5;
	private int _id;
	public int ID(){
		return this._id;
	}
	//http://stackoverflow.com/questions/1359817/using-bigdecimal-to-work-with-currencies
	public BigDecimal AmountDue(){
		BigDecimal actualVal = new BigDecimal(_chargeAmmount * _distanceTravelled);
		return actualVal.setScale(2, RoundingMode.HALF_EVEN);
	}
	private Calendar _dateOfBill;
	public Calendar DateOfBill(){
		return this._dateOfBill;
	}
	private Interval _timeSpentOnRoad;
	public Interval TimeSpentOnRoad(){
		return this._timeSpentOnRoad;
	}
	private double _distanceTravelled;
	public double DistanceTravelled(){
		return this._distanceTravelled;
	}
	public Bill(double distanceTravelled, Interval timeSpentOnRoad){
		this._timeSpentOnRoad = timeSpentOnRoad;
		this._dateOfBill = Calendar.getInstance();
		this._distanceTravelled = distanceTravelled;
	}
}
