package software;

import mazeoss.MazeOSS;

public class ManualGen implements Runnable{
	
	private SharedInfo info;
	
	public static boolean manualOn = false;
	public static int idProcess;
	public static int idApp;
	public static int numInt;
	public static int numSC;
	
	/*
	 * for the type of operation
	 * 1 = terminate process
	 * 2 = run app
	 * 3 = delete app
	 */
	public static int type;

	public ManualGen(SharedInfo info) {
		super();
		this.info = info;
	}
	
	public void setInfo (SharedInfo info) {
		MazeOS.scheduler.addPCBToReadyQueue(MazeOS.currentPCB);
		Scheduler.readyQueue.forEach(el -> {
			if (el.getId() == idProcess){
				MazeOS.currentPCB = el;
				Scheduler.readyQueue.remove(el);
			}
		});
		
		Scheduler.ioQueue.forEach(el -> {
			if (el.getPcb().getId() == idProcess){
				MazeOS.currentPCB = el.getPcb();
				Scheduler.ioQueue.remove(el);
			}
		});
		
		info.setNumInt(numInt);
		info.setNumSC(numSC);
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
while(true){
			
	if (manualOn){
		
		
		
		try {
			MazeOSS.threadCom.manualGenSide(info);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		manualOn = false;
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
			
		}
		
	}
	
	

}
