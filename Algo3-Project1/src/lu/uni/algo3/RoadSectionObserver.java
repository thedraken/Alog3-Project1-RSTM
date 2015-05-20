package lu.uni.algo3;


public interface RoadSectionObserver {
	
	public void updateRS(RoadSection rs);
	//checks if all the vehicles have left the road map
		public default boolean isRoadMapEmpty(){
			return Predicates.filterThreads(Simulator.listOfCarThreads, Predicates.runningThreads(Simulator.listOfCarThreads)).size() == 0;
		}
}
