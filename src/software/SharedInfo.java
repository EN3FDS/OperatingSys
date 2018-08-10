package software;

public class SharedInfo {	
	
	private int numInt;
	private int numSC;
	private int numApp=0;
	private boolean runningSE;
	private boolean runningGen;
	private boolean runningIO = false;
	private boolean stopSimulation=false;
	private boolean startupDone = false;

	public SharedInfo(int numInt, int numSC, boolean runningSE) {
		this.numInt = numInt;
		this.numSC = numSC;
		this.runningSE = runningSE;
		this.runningGen = !runningSE;
	}
	


	public synchronized boolean isRunningGen() {
		return runningGen;
	}

	public synchronized void setRunningGen(boolean runningGen) {
		this.runningGen = runningGen;
	}

	public SharedInfo() {
		// TODO Auto-generated constructor stub
	}

	public synchronized int getNumInt() {
		return numInt;
	}

	public synchronized void setNumInt(int numInt) {
		this.numInt = numInt;
	}

	public synchronized int getNumSC() {
		return numSC;
	}

	public synchronized void setNumSC(int numSC) {
		this.numSC = numSC;
	}

	public synchronized boolean isRunningSE() {
		return runningSE;
	}

	public synchronized void setRunningSE(boolean runningSE) {
		this.runningSE = runningSE;
	}
	
	public synchronized int getNumApp() {
		return numApp;
	}

	public synchronized void setNumApp(int numApp) {
		this.numApp = numApp;
	}


	public synchronized boolean isStopSimulation() {
		return stopSimulation;
	}


	public synchronized void setStopSimulation(boolean stopSimulation) {
		this.stopSimulation = stopSimulation;
	}

	public synchronized boolean isStartupDone() {
		return startupDone;
	}

	public synchronized void setStartupDone(boolean startupDone) {
		this.startupDone = startupDone;
	}



	public synchronized boolean isRunningIO() {
		return runningIO;
	}



	public synchronized void setRunningIO(boolean runningIO) {
		this.runningIO = runningIO;
	}

	
}
