package software;

import java.util.ArrayList;

public class Process {
	private int id;
	private String name;
	private ArrayList<Instruction> instructions = new ArrayList<>();
	private int SIZE = 0;
	
	
	public Process(int id,String name, ArrayList<Instruction> instructions){
		this.id = id;
		this.name = name;
		this.instructions = instructions;
		
		
		SIZE += 16 * (instructions.size() / 10 ); //represent 
		for (Instruction instruction : this.instructions) {
			SIZE += instruction.getSize_B();
		}
	}

	public int getId() {
		return id;
	}
	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}
	public int getSize_KB() {
		return SIZE / 1024; //size en KB
	}
	
	public String getName() {
		return name;
	}

	public String toString(){
		return "(ID: "+id+", nbr.instr: "+instructions.size()+", Size: "+SIZE+" B)";
	}
	
}
