package lu.uni.algo3.exceptions;

public class OutOfRangeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1402668225814795581L;
	//An exception that is thrown if the SQL indexer trys to process an enum it is not aware of
	public OutOfRangeException(int id){
		_message = "The index of " + id + " is out of range";
	}
	@Override
	public String getMessage(){
		return _message;
	}
	private String _message;
	
}
