package software;

public class Instruction {
	private final boolean interrupted;
	private final byte cycles;// represente le nombre de cycle d'horloge
								// necessaire à l'execution: 1-4

	public Instruction(boolean inter, byte cycles) {
		this.interrupted = inter;
		this.cycles = cycles;
	}

	public boolean isInterrupted() {
		return interrupted;
	}

	public byte getCycles() {
		return cycles;
	}

	public int getSize_B() {
		return 128 * cycles; // size en byte
	}

	public String toString() {
		return "(Interrupted: " + interrupted + ", cCycle: " + cycles + ")";
	}
}
