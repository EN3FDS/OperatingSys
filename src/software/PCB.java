package software;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PCB implements Comparable<PCB> {
	public static final int BLOCKED = 0;
	public static final int RUNNING = 1;
	public static final int TERMINATED = 2;
	public static final int READY = 4;
	private final int id;
	private Date dateCreated;
	
	private int priority;
	private int basePriority;
	
	private int state = -1;
	private Process process;
	private int currentInstruction = 0;
	private ArrayList<Integer> indexesOfPages = new ArrayList<>();

	public PCB(Process process, int priority) {
		this.id = process.getId();
		this.process = process;
		this.priority = priority;
		this.basePriority=priority;
		this.dateCreated = Date.from(Instant.now());
	}
	

	public synchronized int getBasePriority() {
		return basePriority;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ArrayList<Integer> getIndexesOfPages() {
		return indexesOfPages;
	}

	public void setIndexesOfPages(ArrayList<Integer> indexesOfPages) {
		this.indexesOfPages = indexesOfPages;
	}

	public int getId() {
		return id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}
/*
	@Override
	public int compare(PCB pcb1, PCB pcb2) {
		if (pcb1.getPriority() != pcb2.getPriority()) {
			return pcb1.getPriority() - pcb2.getPriority();
		} else {
			return pcb1.getDateCreated().compareTo(pcb2.getDateCreated());
		}
	}*/
	
	
	public void setState(final int state) {
		this.state = state;
	}
	public final int getState() {
		return this.state;
	}
	public String toString() {
		return ""+this.id;
	}

	public Process getProcess() {
		return process;
	}

	public synchronized int getCurrentInstruction() {
		return currentInstruction;
	}

	public synchronized void setCurrentInstruction(int currentInstruction) {
		this.currentInstruction = currentInstruction;
	}


	@Override
	public int compareTo(PCB pcb2) {
		if (this.getPriority() != pcb2.getPriority()) {
			return pcb2.getPriority() - this.getPriority();
		} else {
			return pcb2.getDateCreated().compareTo(this.getDateCreated());
		}
		// TODO Auto-generated method stub
		
	}
	public StringProperty nameProperty(){
		return new SimpleStringProperty(this.getProcess().getName());
	}
	public StringProperty idProperty() {
		return new SimpleStringProperty(""+this.getProcess().getId());
	}
	
	
}
