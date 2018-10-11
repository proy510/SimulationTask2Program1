
/**
 * @author Parami Roy
 * @StudentID 200205116
 *
 */
import java.util.*;
import java.io.*;
public class Simulation {

	/**
	 * @param args
	 */
	
	
	static void printRow(int mcl,int cla,int cls,int buffer_size,LinkedList<Integer> orbitting_packets)
	{
		System.out.println(+mcl+"\t "+cla+"\t "+cls+"\t "+buffer_size+"\t\t "+orbitting_packets);
		
	}
	
	static void printIntro()
	{
		System.out.println("***In this simulation code, the value 999999 has been used as a HIGH value to initialise the CLS, CLR before the first real calculation of "
				+ "these values.\nThus first few rows may have 999999 printed .\nAlso , in the intial condition of the systeam ( at Master Clock = 0 , CLA(next new arrival) in time stamp 2 ,the"
				+ "buffer size = 0 , CLS = 999999(as per above assumption), CLR = 999999***\n\n");
	}
	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub
		
		/*
		 * Variables
		 */
		
		int cla,cls,clr,buffer_size = 0,max_buffer_size=0,master_clock = 0;
		Scanner time_records = new Scanner(System.in);
		Time time_values = new Time();
		LinkedList<Integer> occ_indices = new LinkedList<Integer>();
		
		PrintStream new_file = new PrintStream(new File("output.txt")); 
		PrintStream console = System.out;
		
		/*
		 * Enter condition of the system at master clock =  0;
		 *
		 */
		cla = 2;
		cls = 999999;
		clr = 999999;
		buffer_size = 0;
		
		time_values.setInitialConditions(cla,cls,clr);
		
		printIntro();
		
		
		System.out.println("Please enter the master clock limit until which you want to execute the simulation:");
		master_clock = time_records.nextInt();
		
		System.out.println("Please enter the maximum buffer size:");
		max_buffer_size = time_records.nextInt();
		
		System.out.println("Enter the inter-arrival time of new packet:");
		cla = time_records.nextInt();
		
		System.out.println("Enter the service time of each packet");
		cls = time_records.nextInt();
		
		System.out.println("Enter the orbitting time units");
		clr = time_records.nextInt();
		System.setOut(new_file);
		
		time_values.setDeltaConditions(cla,cls,clr);
		
		int min_value=time_values.findMinTime();
		occ_indices = time_values.countOccurrencesAndIndices();
		
		/*
		 * Simulation part
		 */
		LinkedList<Integer> temp_clr = new LinkedList<Integer>();
		temp_clr.add(0,(Integer) time_values.getTimeRecord(2));
	    System.out.println("MC\tCLA  \tCLS \tbuff_size\tCLR(s) \t");
	    printRow(0,time_values.getTimeRecord(0),time_values.getTimeRecord(1),buffer_size,temp_clr);

		int flag_main = 0;
		for(int mcl= min_value ; mcl<=master_clock;)
		{
			
			for(int i = occ_indices.size()-1;i>=0;i--)
			{
				if(occ_indices.get(i)>=2)
				{
					//Apply CLR logics
					if(buffer_size>=max_buffer_size)
					{
						int temp = time_values.setNextOrbittingTime(mcl);
						time_values.addToOrbittingPackets(temp);
						time_values.removeFromOrbittingPackets(occ_indices.get(i));
					}
					
					while(buffer_size<max_buffer_size)
					{
						buffer_size++;
						time_values.removeFromOrbittingPackets(occ_indices.get(i));
					}
					
					
					min_value=time_values.findMinTime();
					occ_indices = time_values.countOccurrencesAndIndices();
					printRow(mcl,time_values.getTimeRecord(0),time_values.getTimeRecord(1),buffer_size,time_values.getCLRValues());
					
					mcl = min_value;
					continue;
				}
				
			int index = occ_indices.get(i),flag = 0;
			//if()
			if((index == 1 ) &&(time_values.getNextNewPacketArrivalTime() == time_values.getTimeRecord(index)))
				{
					//Apply CLA logics
					if(buffer_size<max_buffer_size)
					{
						buffer_size++;
						if(time_values.getNextServiceCompletionTime()== 999999)
							time_values.setNextServiceCompletionTime(mcl);
						
					}
					else
					{
						int clr_temp = time_values.setNextOrbittingTime(mcl);
						time_values.addToOrbittingPackets(clr_temp);
					}
					time_values.setNextNewPacketArrivalTime(mcl);
					flag_main = 1;
					printRow(mcl,time_values.getTimeRecord(0),time_values.getTimeRecord(1),buffer_size,time_values.getCLRValues());
					
					min_value=time_values.findMinTime();
					occ_indices = time_values.countOccurrencesAndIndices();
					mcl = min_value;
					continue;
				}
			
				if(occ_indices.get(i)==1 || flag_main == 1)
				{
					//Apply CLS Logics
					if(buffer_size>0)
					{
						buffer_size--;
						
					}
					time_values.setNextServiceCompletionTime(mcl);
					printRow(mcl,time_values.getTimeRecord(0),time_values.getTimeRecord(1),buffer_size,time_values.getCLRValues());
					min_value=time_values.findMinTime();
					occ_indices = time_values.countOccurrencesAndIndices();
					mcl = min_value;
					flag_main = 0;
					flag = 1;
					continue;
					
				}
				
				if(occ_indices.get(i)==0 || flag == 0)
				{
					//Apply CLA logics
					if(buffer_size<max_buffer_size)
					{
						buffer_size++;
						if(time_values.getNextServiceCompletionTime()== 999999)
							time_values.setNextServiceCompletionTime(mcl);
						
					}
					else
					{
						int clr_temp = time_values.setNextOrbittingTime(mcl);
						time_values.addToOrbittingPackets(clr_temp);
					}
					time_values.setNextNewPacketArrivalTime(mcl);
					printRow(mcl,time_values.getTimeRecord(0),time_values.getTimeRecord(1),buffer_size,time_values.getCLRValues());
					min_value=time_values.findMinTime();
					occ_indices = time_values.countOccurrencesAndIndices();
					mcl = min_value;
					
					
				}
			}
			
		}
		System.setOut(console);
		/*
		 * Scanner record closure
		 */
		time_records.close();
	}

}
