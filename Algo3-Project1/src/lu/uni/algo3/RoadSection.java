package lu.uni.algo3;

import java.util.HashSet;

public class RoadSection {
	private int _number;
	private int _speedLimit;
	private int _maxOccupation;
	private int _tollRate;
	private HashSet<Vehicle> _listOfVehiclesInside;
	private boolean _roadContinutesAfterSection;
	private Road _connectionToOtherRoad;
	
}
