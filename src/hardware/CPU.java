package hardware;

import java.util.Random;

import software.Instruction;

public class CPU {
	// We use the 8086 format
	
	private int frequency;
	private int AX;
	private int BX;
	private int CX;
	private int DX;

	private int SI;
	private int DI;
	private int SP;
	private int BP;

	private int IP;
	private int CS;
	private int DS;
	private int SS;
	private int ES;

	private Random rand = new Random();

	private int flagsRegister;

	public CPU() {
		this.randomValueRegisters();
	}


	
	public void randomValueRegisters() {
		AX = rand.nextInt();
		BX = rand.nextInt();
		CX = rand.nextInt();
		DX = rand.nextInt();

		SI = rand.nextInt();
		DI = rand.nextInt();
		SP = rand.nextInt();
		BP = rand.nextInt();

		IP = rand.nextInt();
		CS = rand.nextInt();
		DS = rand.nextInt();
		SS = rand.nextInt();
		ES = rand.nextInt();

		flagsRegister = rand.nextInt();
	}

	public void execute(Instruction inst) {
		// Execute and return the interrupt number
		this.randomValueRegisters();
		
	}

}
