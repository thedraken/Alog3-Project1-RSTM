package lu.uni.algo3.exceptions;

public class ObjectExistsInCollectionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3810730536053223762L;

	@Override
	public String getMessage(){
		return "Cannot add object to collection as already within collection";
	}
}
