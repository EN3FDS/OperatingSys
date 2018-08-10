package software;

import java.util.ArrayList;

public class FichierApplication extends Fichier {
	private ArrayList<Instruction> instructions = new ArrayList<>();
	private final int priority; 
	
	public FichierApplication(Fichier parent,String name,int priority,ArrayList<Instruction> instructions){
		super(parent,name, false);
		this.instructions = instructions;
		this.priority = priority;
	}

	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}
	public void setInstructions(ArrayList<Instruction> instructions) {
		this.instructions = instructions;
	}

	public int getPriority() {
		return priority;
	}
	public int getSIZE_B() {
		int size = 0;
		for(Instruction instruction : instructions){
			size+= instruction.getSize_B();
		}
		return size;
	}
	public String toString() {
		return this.name+"\t"+this.getSIZE_B()+" B";
	}
}
