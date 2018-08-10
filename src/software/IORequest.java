package software;

public class IORequest implements Comparable<IORequest>{
	
	private PCB pcb;
	private int numInt;
	
	public IORequest(PCB pcb, int numInt) {
		super();
		this.pcb = pcb;
		this.numInt = numInt;
	}

	public synchronized PCB getPcb() {
		return pcb;
	}

	public synchronized int getNumInt() {
		return numInt;
	}

	@Override
	public int compareTo(IORequest arg0) {
		// TODO Auto-generated method stub
		return pcb.compareTo(arg0.getPcb());
	}
	
	

}
