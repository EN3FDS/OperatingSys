package software;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Scheduler {
	public static PriorityQueue<PCB> readyQueue = new PriorityQueue<PCB>();
	
	public static ArrayList<PCB> processQueue = new ArrayList<>();
	
	
	public static PriorityQueue<IORequest> ioQueue = new PriorityQueue<IORequest>();
	
	
	public synchronized void addPCBToReadyQueue(PCB pcb){
		readyQueue.add(pcb);
	}
	
	public synchronized void addPCBToProcessQueue(PCB pcb){
		processQueue.add(pcb);
	}
	
	public synchronized void removePCBFromProcessQueue(PCB pcb){
		ArrayList<PCB> list = new ArrayList<>();
		processQueue.forEach(data->{
			if (data.getId() != pcb.getId()){
				list.add(data);
			}
		});
		processQueue = new ArrayList<>();
		processQueue.addAll(list);
	}
	
	public synchronized void addRequestToIOQueue(IORequest ioRequest){
		ioQueue.add(ioRequest);
	}
	public synchronized PCB pickPCBFromReadyQueue() {
		//System.out.println("Scheduler -> Scheduling executed!");
		//GraphOSS.Log("Scheduler -> Scheduling executed!");
		return readyQueue.poll();
	}
	public synchronized IORequest pickRequestFromIOQueue() {
		return ioQueue.poll();
	}
}
