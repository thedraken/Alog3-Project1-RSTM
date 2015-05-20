package lu.uni.algo3.exceptions;

public class ExceedMaxOccupationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3659096395793882200L;
	//If a roadsection exceeds it's maxiumum occupancy, this is custom error message is thrown
	public ExceedMaxOccupationException(int currentNumber, int maximum, int numberToAdd){
		_message = "The current number of items is " + currentNumber + " and the maximum is " + maximum + ", so cannot add " + numberToAdd;
	}
	@Override
	public String getMessage(){
		return _message;
	}
	private String _message;
}
