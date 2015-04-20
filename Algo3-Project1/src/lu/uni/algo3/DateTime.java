package lu.uni.algo3;
import java.time.LocalDate;
import java.time.LocalTime;
public class DateTime {
	public DateTime(LocalDate date, LocalTime time){
		this._time = time;
		this._date = date;
	}
	private LocalTime _time;
	public LocalTime time(){
		return _time;
	}
	private LocalDate _date;
	public LocalDate date(){
		return _date;
	}
}
