package lu.uni.algo3.exceptions;

public class ObjectExistsInHashSet extends Exception {
	@Override
	public String getMessage(){
		return "Cannot add object to HashSet as already within HashSet";
	}
}
