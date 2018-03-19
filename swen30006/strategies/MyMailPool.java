package strategies;

import java.util.*;

import automail.MailItem;
import automail.PriorityMailItem;
import automail.StorageTube;
import exceptions.TubeFullException;

public class MyMailPool implements IMailPool{
	// My first job with Robotic Mailing Solutions Inc.!
	// 2 kinds of items so two structures
	// Remember stacks from 1st year - easy to use, not sure if good choice
	private PriorityQueue<MailItem> nonPriorityPoolWeak;
	private PriorityQueue<MailItem> priorityPoolWeak;
	private PriorityQueue<MailItem> nonPriorityPoolStrong;
	private PriorityQueue<MailItem> priorityPoolStrong;
	private static final int MAX_TAKE = 4;
	private static final int WEAK_MAX = 2000;
	
	
//	private PriorityQueue<MailItem> nonPriorityPool;
//	private PriorityQueue<MailItem> priorityPool;
	
	Comparator<MailItem> comparator = new Comparator<MailItem>() {
		
//		private int scoreMail(MailItem mail) {
//			
//			int priority = 1;
//			
//			if (mail instanceof PriorityMailItem) {
//				priority += Math.sqrt(((PriorityMailItem)mail).getPriorityLevel());
//			}
//			
//			return (int) (Math.pow(mail.getDestFloor(), priority));
//			
//		}
		
		@Override
		public int compare(MailItem m1, MailItem m2) {
			
//			return scoreMail(m1) - scoreMail(m2);
			
			
			int priority;
			int arrival  = m1.getArrivalTime() - m2.getArrivalTime();
			int dest = m1.getDestFloor() - m2.getDestFloor();
			
			ArrayList<Integer> order = new ArrayList<>();
			
			if ((m1 instanceof PriorityMailItem) && (m2 instanceof PriorityMailItem)) {
				priority = ((PriorityMailItem)m1).getPriorityLevel() - ((PriorityMailItem)m2).getPriorityLevel();
				order.add(priority);
			}
			order.add(dest);
			order.add(arrival);
			
			
			for (int value : order) {
				if (value != 0) {
					return value;
				}
			}
			System.out.print("yeet");
			return order.remove(0);
		}
		
	};

	
	
	public MyMailPool(){
		// Start empty
		nonPriorityPoolWeak = new PriorityQueue<>(comparator);
		priorityPoolWeak = new PriorityQueue<>(comparator);
		
		nonPriorityPoolStrong = new PriorityQueue<>(comparator);
		priorityPoolStrong = new PriorityQueue<>(comparator);
		
//		nonPriorityPool = new PriorityQueue<MailItem>(comparatorArrival);
//		priorityPool = new PriorityQueue<MailItem>(comparatorPriority);
		
	}

	public void addToPool(MailItem mailItem) {
		
		boolean strongIsBiggerPriority = (priorityPoolStrong.size() > priorityPoolWeak.size());
		boolean strongIsBiggerNon = (nonPriorityPoolStrong.size() > nonPriorityPoolWeak.size());
		
		// Check whether it has a priority or not
		if(mailItem instanceof PriorityMailItem){
			// Add to priority items
			// Kinda feel like I should be sorting or something
			
			if (mailItem.getWeight() <= WEAK_MAX) {
				if (strongIsBiggerPriority) {
					priorityPoolWeak.add(mailItem);
				} else {
					priorityPoolStrong.add(mailItem);
				}
			}
			
			if (mailItem.getWeight() > WEAK_MAX) {
				priorityPoolStrong.add(mailItem);
			}
		}
		else {
			// Add to nonpriority items
			// Maybe I need to sort here as well? Bit confused now
			if (mailItem.getWeight() <= WEAK_MAX) {
				if (strongIsBiggerNon) {
					nonPriorityPoolWeak.add(mailItem);
				} else {
					nonPriorityPoolStrong.add(mailItem);
				}
			}
			
			if (mailItem.getWeight() > WEAK_MAX) {
				nonPriorityPoolStrong.add(mailItem);
			}
		}
	}
		
		
//		if (mailItem instanceof PriorityMailItem) {
//			priorityPool.add(mailItem);
//		} else {
//			nonPriorityPool.add(mailItem);
//		}
	
	private int getNonPriorityPoolSize(int weightLimit) {
		// This was easy until we got the weak robot
		// Oh well, there's not that many heavy mail items -- this should be close enough
		
		if (weightLimit > WEAK_MAX) {
			return nonPriorityPoolStrong.size();
		} else {
			return nonPriorityPoolWeak.size();
		}
		
//		return nonPriorityPool.size();
	}
	
	private int getPriorityPoolSize(int weightLimit){
		// Same as above, but even less heavy priority items -- hope this works too
		if (weightLimit > WEAK_MAX) {
			return priorityPoolStrong.size();
		} else {
			return priorityPoolWeak.size();
		}
		
//		return priorityPool.size();
	}

	private MailItem getNonPriorityMail(int weightLimit){
//		if(getNonPriorityPoolSize(weightLimit) > 0){
//			// Should I be getting the earliest one? 
//			// Surely the risk of the weak robot getting a heavy item is small!
//			return nonPriorityPool.poll();
//		}
//		else{
//			return null;
//		}
		
		
		
		if (weightLimit > WEAK_MAX) {
			if (getNonPriorityPoolSize(weightLimit) > 0) {
				return nonPriorityPoolStrong.poll();
			}
		} else {
			if (getNonPriorityPoolSize(weightLimit) > 0) {
				return nonPriorityPoolWeak.poll();
			}
		}
		return null;
		
//		MailItem[] overweight = new MailItem[nonPriorityPool.size()];
//		int index = 0;
//		MailItem highest = null;
//		boolean iteratedFlag = false;
//		
//		if(getNonPriorityPoolSize(weightLimit) > 0){
//			while (nonPriorityPool.peek().getWeight() > weightLimit) {
//				overweight[index] = nonPriorityPool.poll();
//				index++;
//			}
//			
//			if (nonPriorityPool.peek().getWeight() <= weightLimit) {
//				highest = nonPriorityPool.poll();
//			}
//			
//			if (iteratedFlag) { 
//				for (MailItem mail : overweight) {
//					nonPriorityPool.add(mail);
//				}
//			}
//		}			
//		
//		return highest;
		
	}
	
	
	
	private MailItem getHighestPriorityMail(int weightLimit){
		
//		if(getPriorityPoolSize(weightLimit) > 0){
//			// How am I supposed to know if this is the highest/earliest?
//			return priorityPool.poll();
//		}
//		else{
//			return null;
//		}
		
		
		if (weightLimit > WEAK_MAX) {
			if (getPriorityPoolSize(weightLimit) > 0) {
				return priorityPoolStrong.poll();
			}
		} else {
			if (getPriorityPoolSize(weightLimit) > 0) {
				return priorityPoolWeak.poll();
			}
		}
		return null;
		
//		ArrayList<MailItem> overweight = new ArrayList<>();
//		MailItem highest = null;
//		boolean iteratedFlag = false;
//		
//		if(getPriorityPoolSize(weightLimit) > 0){
//			while (priorityPool.peek().getWeight() > weightLimit) {
//				overweight.add(priorityPool.poll());
//				iteratedFlag = true;
//			}
//			
//			if (priorityPool.peek().getWeight() <= weightLimit) {
//				highest = priorityPool.poll();
//			}
//			
//			if (iteratedFlag) { 
//				for (MailItem mail : overweight) {
//					priorityPool.add(mail);
//				}
//			}
//		}
//		
//		return highest;
		
	}
	
	// Never really wanted to be a programmer any way ...

	@Override
	public void fillStorageTube(StorageTube tube, boolean strong) {
		int max = strong ? Integer.MAX_VALUE : WEAK_MAX; // max weight
		// Priority items are important;
		// if there are some, grab one and go, otherwise take as many items as we can and go
		try{
			// Start afresh by emptying undelivered items back in the pool
			while(!tube.isEmpty() && !(tube.peek() instanceof PriorityMailItem)) {
				addToPool(tube.pop());
			}
			// Check for a top priority item
			
//			if (getPriorityPoolSize(max) > 0) {
//				// Add priority mail item
//				tube.addItem(getHighestPriorityMail(max));
//				// Won't add any more - want it delivered ASAP
//			}
//			else{
//				// Get as many nonpriority items as available or as fit
//				while(tube.getSize() < MAX_TAKE && getNonPriorityPoolSize(max) > 0) {
//					tube.addItem(getNonPriorityMail(max));
//				}
//			}
			
			while ((getPriorityPoolSize(max) > 0) && tube.getSize() < MAX_TAKE) {
				// Add priority mail item
				tube.addItem(getHighestPriorityMail(max));
				// Won't add any more - want it delivered ASAP
			}
			
				// Get as many nonpriority items as available or as fit
			while(tube.getSize() < MAX_TAKE && getNonPriorityPoolSize(max) > 0) {
				tube.addItem(getNonPriorityMail(max));
			}
		}
		catch(TubeFullException e){
			e.printStackTrace();
		}
	}

}
