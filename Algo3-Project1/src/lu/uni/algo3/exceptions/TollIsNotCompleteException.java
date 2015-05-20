package lu.uni.algo3.exceptions;

import lu.uni.algo3.Vehicle;

public class TollIsNotCompleteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -76015324339175821L;
	//This message is thrown if a bill is attempted to be generate and the toll record hasn't been closed
	public TollIsNotCompleteException(int id, Vehicle v){
		_message = "The toll record " + id + " for vehicle " + v.getLicencePlate() + " cannot be have a bill generated, as it is not complete";
	}
	@Override
	public String getMessage(){
		return _message;
	}
	private String _message;
}
