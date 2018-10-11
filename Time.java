import java.util.*;
/**
 * The index 0 in the Linked List for time records, will always indicate the CLA , the index 1 will indicate
 * CLS and any index beyond would indicate CLRs.
 * 
 */

/**
 * @author Parami Roy
 * @studentID 200205116
 *
 */
public class Time {
	
	
	private int delta_new_arrival= 0, delta_service_completion=0, delta_orbitting_time =0,min_time=0;
	
	
	LinkedList<Integer> time_record = new LinkedList<Integer>();
	LinkedList<Integer> time_record_clone = new LinkedList<Integer>();
	LinkedList<Integer> orbitting_packets = new LinkedList<Integer>();
	LinkedList<Integer> clr_print = new LinkedList<Integer>();
	
	void setInitialConditions(int cla, int cls,int clr)
	{
		time_record.add((Integer) cla);
		time_record.add((Integer) cls);
		time_record.add((Integer) clr);
		
	}
	
	void setDeltaConditions(int cla,int cls,int clr)
	{
		delta_new_arrival = cla;
		delta_service_completion = cls;
		delta_orbitting_time = clr;
		
	}
	
	LinkedList<Integer> getCLRValues()
	{
		clr_print.clear();
		for(int i = 2; i<time_record.size();i++)
			clr_print.add((Integer) time_record.get(i));
		
		return clr_print;
			
	}
	/*
	 * Getters and Setters for CLA
	 */
	
	int getTimeRecord(int index)
	{
		return time_record.get(index);
	}
	
	Integer getNextNewPacketArrivalTime()
	{
		return time_record.get(0);
	}
	
	void setNextNewPacketArrivalTime(int master_clock)
	{
		int temp =0;
		temp = master_clock +delta_new_arrival;
		time_record.add(0,(Integer) temp);
		time_record.remove(1);
	}
	
	/*
	 * Getters and Setters for CLS
	 */
	
	Integer getNextServiceCompletionTime()
	{
		return time_record.get(1);
	}
	
	void setNextServiceCompletionTime(int master_clock)
	{
		int temp = 0;
		temp = master_clock +delta_service_completion;
		time_record.add(1,(Integer) temp);
		time_record.remove(2);
	}
	
	/*
	 * Getters and Setters for CLR
	 */
	int setNextOrbittingTime(int mcl)
	{
		int temp=0;
		temp = mcl + delta_orbitting_time;
		time_record.add((Integer) temp);
		if(time_record.get(2) == 999999)
		{
			time_record.remove(2);
		}
		return temp;
	}
	
	/*
	 * Methods for orbitting packets
	 */
	
	void addToOrbittingPackets(int clr_temp)
	{
		orbitting_packets.add((Integer) clr_temp);
	}
	
	LinkedList<Integer> getListOfOrbittingPackets()
	{
		return orbitting_packets;
	}
	void removeFromOrbittingPackets(int index_to_remove)
	{
		time_record.remove(index_to_remove);
	}
	@SuppressWarnings("unchecked")
	int findMinTime()
	{
		time_record_clone = (LinkedList<Integer>) time_record.clone();
		Collections.sort(time_record_clone);
		min_time = time_record_clone.get(0);
		return min_time;
	}
	
	LinkedList<Integer> countOccurrencesAndIndices()
	{
		LinkedList<Integer> indices = new LinkedList<Integer>();
		
		for(int i = 0;i<time_record.size();i++)
		{
			if(time_record.get(i) == min_time)
				indices.add((Integer) i);
		}
		return indices;
	}
	
	void printLL()
	{
		System.out.println(time_record);
	}
	
	
	
	
	

}
