package lu.uni.algo3;

import java.util.ArrayList;
import java.util.HashSet;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.ObjectExistsInCollectionException;
import lu.uni.algo3.exceptions.OutOfRangeException;

public class Road {
	private int _id;
	private String _name;
	private ArrayList<RoadSection> _listOfRoadSections;
	private HashSet<Camera> _listOfCameras;
	public Road(String roadName) throws OutOfRangeException{
		this._id = SQLIndexer.getInstance().getNewID(SQLType.Road);
		this._listOfCameras = new HashSet<Camera>();
		this._listOfRoadSections = new ArrayList<RoadSection>();
		this._name = roadName;
	}
	public int iD(){
		return this._id;
	}
	public synchronized ArrayList<RoadSection> listOfRoadSections(){
		return this._listOfRoadSections;
	}
	public synchronized HashSet<Camera> listOfCameras(){
		return this._listOfCameras;
	}
	public String name(){
		return this._name;
	}
	//A synced method that allows adding of a road section to a road. Throws an exception if it was not done sucessfully
	public synchronized void insertRoadSection(RoadSection rs) throws ObjectExistsInCollectionException{
		if (_listOfRoadSections.contains(rs))
			throw new ObjectExistsInCollectionException();
		_listOfRoadSections.add(rs);
	}
	//A synced method to remove roadsections from a road
	public synchronized boolean removeRoadSection(RoadSection rs){
		if (_listOfRoadSections.contains(rs)){
			_listOfRoadSections.remove(rs);
			return true;
		}
		return false;
	}
}
