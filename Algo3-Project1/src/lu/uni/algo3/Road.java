package lu.uni.algo3;

import java.util.HashSet;

import lu.uni.algo3.SQLIndexer.SQLType;
import lu.uni.algo3.exceptions.OutOfRangeException;

public class Road {
	private int _id;
	private String _name;
	private HashSet<RoadSection> _listOfRoadSections;
	private HashSet<Camera> _listOfCameras;
	public Road(String roadName) throws OutOfRangeException{
		this._id = SQLIndexer.getInstance().getNewID(SQLType.Road);
		this._listOfCameras = new HashSet<Camera>();
		this._listOfRoadSections = new HashSet<RoadSection>();
		this._name = roadName;
	}
	public int iD(){
		return this._id;
	}
	public HashSet<RoadSection> listOfRoadSections(){
		return this._listOfRoadSections;
	}
	public HashSet<Camera> listOfCameras(){
		return this._listOfCameras;
	}
	public String name(){
		return this._name;
	}
	public void insertRoadSection(RoadSection rs){
		_listOfRoadSections.add(rs);
	}
	public boolean removeRoadSection(RoadSection rs){
		if (_listOfRoadSections.contains(rs)){
			_listOfRoadSections.remove(rs);
			return true;
		}
		return false;
	}
}
