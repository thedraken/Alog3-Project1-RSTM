package lu.uni.algo3.exceptions;

public class MissingVehicleException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5154622817227222650L;
	//If attempting to access a list and it has no vehicles in it, this is thrown
	@Override
	public String getMessage(){
		return "No vehicles were found in the list";
	}
}
