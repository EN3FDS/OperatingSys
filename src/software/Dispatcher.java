package software;

import mazeoss.MazeOSS;

public class Dispatcher {
	
	public synchronized void contextSwitch(){
		try {
			PCB newPCB = MazeOS.scheduler.pickPCBFromReadyQueue();
			if (MazeOS.currentPCB!=newPCB){
				//GraphOSS.Log("Dispatcher -> Context Switch executed! || New Process Selected!");
				MazeOSS.outLog("Dispatcher -> Context Switch executed! || New Process Selected!");
			}
			MazeOS.currentPCB = newPCB;
			
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
