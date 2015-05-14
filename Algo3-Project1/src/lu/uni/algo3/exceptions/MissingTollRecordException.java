package lu.uni.algo3.exceptions;

import lu.uni.algo3.Camera;
import lu.uni.algo3.Vehicle;

public class MissingTollRecordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4314416204842204144L;
	public MissingTollRecordException(Vehicle v, Camera c){
		_message = "Cannot find an open toll record for vehicle " + v.getLicencePlate() + " at camera " + c.iD();
	}
	@Override
	public String getMessage(){
		return _message;
	}
	private String _message;
}
