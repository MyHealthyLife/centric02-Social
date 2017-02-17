package myhealthylife.centric2.rest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="rank")
public class Rank {
	
	private final Integer NUM_ELEMENTS_FILTERED_LIST = 10;

	private ArrayList<String> compactRanking;
	
	public ArrayList<String> getCompactRanking() {
		return compactRanking;
	}

	public void setCompactRanking(ArrayList<String> compactRanking) {
		this.compactRanking = compactRanking;
	}
	
	
	public ArrayList<String> getFinalRanking(Map<String, Double> sortedRanking) {

		Iterator<String> iKeys = sortedRanking.keySet().iterator();
		this.compactRanking = new ArrayList<>();
		
		for(int i=0;i<sortedRanking.size();i++) {
			
			String currentKey = iKeys.next();
			Double currentValue = sortedRanking.get(currentKey);
			
			String recordToInsert = "[" + i + 1 + "] " + currentKey + ": " + currentValue;
			
			this.compactRanking.add(recordToInsert);
			
		}
		
		return this.compactRanking;
	}
	
	
	
	public ArrayList<String> getFinalRankingFiltered(String username, Map<String, Double> sortedRanking) {

		this.compactRanking = new ArrayList<>();
		
		Iterator<String> iKeys = sortedRanking.keySet().iterator();
		
		boolean elementFound = false;
		int elementCounter = 0;
		int upperBound = sortedRanking.size();
		
		for(int i=0;i<upperBound;i++) {
			
			String currentKey = iKeys.next();
			Double currentValue = sortedRanking.get(currentKey);
			
			String recordToInsert = "[" + (i + 1) + "] " + currentKey + ": " + currentValue;

			this.compactRanking.add(recordToInsert);
			
			if(!elementFound && currentKey.equals(username)) {
				elementFound = true;
				elementCounter = i;
			}
			
		}
		
		Integer lowerElement = elementCounter - this.NUM_ELEMENTS_FILTERED_LIST/2;
		Integer upperElement = elementCounter + this.NUM_ELEMENTS_FILTERED_LIST/2;
		
		// Check the bounds
		if(lowerElement < 0) {
			lowerElement = 0;
		}
		if(upperElement >= upperBound) {
			upperElement = upperBound;
		}
		else {
			upperElement +=1;
		}
		
		return new ArrayList<>(compactRanking.subList(lowerElement, upperElement));
	}
	
}
