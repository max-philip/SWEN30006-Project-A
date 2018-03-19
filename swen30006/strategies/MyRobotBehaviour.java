package strategies;
import automail.Clock;
import automail.PriorityMailItem;
import automail.StorageTube;

public class MyRobotBehaviour implements IRobotBehaviour {
	
	private boolean newPriority; // Used if we are notified that a priority item has arrived.
	private int maxWeight;
	public static final int MAX_WEAK = 2000;
	public static final int MAX_STRONG = Integer.MAX_VALUE;
	public static final int HIGH_PRIORITY = 100;
		
	public MyRobotBehaviour(boolean strong) {
		newPriority = false;
		
		maxWeight = strong ? MAX_STRONG : MAX_WEAK;
	}
	
	public void startDelivery() {
		newPriority = false;
	}
	
	@Override
    public void priorityArrival(int priority, int weight) {
    	// Oh! A new priority item has arrived.
		// (Why's it telling me the weight?)
		
		newPriority = ((weight < maxWeight) && (priority == HIGH_PRIORITY));
		
		//newPriority = true;
    }
 
	@Override
	public boolean returnToMailRoom(StorageTube tube) {
		if (tube.isEmpty()) {
			return false; // Empty tube means we are returning anyway
		} else {
			// Return if we don't have a high priority item and a new one came in
			Boolean priority = (tube.peek() instanceof PriorityMailItem);
			return !priority && newPriority;
		}
	}
	
}
