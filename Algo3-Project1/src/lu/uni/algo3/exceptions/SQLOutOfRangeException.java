package lu.uni.algo3.exceptions;

public class SQLOutOfRangeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1402668225814795581L;
	public SQLOutOfRangeException(int id){
		_message = "The index of " + id + " is out of range";
	}
	@Override
	public String getMessage(){
		return _message;
	}
	private String _message;
	
}
