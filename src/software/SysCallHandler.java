package software;

import java.util.ArrayList;

import hardware.DiskController;
import mazeoss.MazeOSS;
import view.Overview;
import view.ProcessesPane;

public class SysCallHandler {

	// System call table
	private SysCall sysCallTable[];

	public SysCallHandler(SysCall sysCallTable[]) {
		// TODO Auto-generated constructor stub
		this.sysCallTable = sysCallTable;
	}

	public void saveRegister() {
		// First task of the System Call handler

	}

	public void executeSCSR(SharedInfo info) {
		// System.out.println("---Syscall
		// "+info.getNumSC()+":"+sysCallTable[info.getNumSC()].getName()+ "
		// received and treated");
		// Execute Sys Call Routine

		switch (info.getNumSC()) {
		/*case 0: {

			
					
			GraphOSS.Log("System Call Handler -> System Call " + info.getNumSC() + ":"
					+ sysCallTable[info.getNumSC()].getName() + " not treated || No routine available!");
			break;
		}*/

		case 1: {

			MazeOS.scheduler.removePCBFromProcessQueue(MazeOS.currentPCB);
			ProcessesPane.refresh();
			
			MazeOSS.ramController.deallocateMemoryFrom(MazeOS.currentPCB);
			MazeOS.currentPCB = MazeOS.scheduler.pickPCBFromReadyQueue();
			Overview.refreshListReadyQueue();
			Overview.refreshInfoCurrentProcess();
			
			if(Scheduler.readyQueue.isEmpty() ){
				if (Scheduler.ioQueue.isEmpty()){
					if (MazeOS.currentPCB == null){
						info.setStopSimulation(true);
						
					}
					
				}
						
			}
			
			//ProcessesPane.refresh();
			MazeOSS.outLog("System Call Handler -> System Call " + info.getNumSC() + ":"+ sysCallTable[info.getNumSC()].getName() + " treated!");

			break;
		}

		/*case 2: {

		}

		case 3: {

		}

		case 4: {

		}

		case 5: {

		}

		case 6: {

		}

		case 7: {

		}

		case 8: {

		}

		case 9: {

		}

		case 10: {

			GraphOSS.Log("System Call Handler -> System Call " + info.getNumSC() + ":"
					+ sysCallTable[info.getNumSC()].getName() + " not treated || No routine available!");
			break;
		}*/

		case 11: {
			// Routine executer programme
			try {
				FichierApplication fichierApplication = (FichierApplication) DiskController.FichierOnDisk
						.get(info.getNumApp());
				Process process = new Process(MazeOSS.processCounter, fichierApplication.name,
						fichierApplication.getInstructions());
				ArrayList<Integer> indexes = MazeOSS.ramController.allocateMemoryTo(process);
				PCB pcb = new PCB(process, fichierApplication.getPriority());
				pcb.setIndexesOfPages(indexes);
				MazeOS.scheduler.addPCBToReadyQueue(pcb);
				MazeOSS.processCounter++;
				Overview.refreshListReadyQueue();
				MazeOS.scheduler.addPCBToProcessQueue(pcb);
				ProcessesPane.refresh();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			
			MazeOSS.outLog("System Call Handler -> System Call " + info.getNumSC() + ":"+ sysCallTable[info.getNumSC()].getName() + " treated!");			break;
		}

		/*case 12: {

		}

		case 13: {

		}

		case 14: {

		}

		case 15: {

		}

		case 16: {

		}

		case 17: {

		}

		case 18: {

		}

		case 19: {

		}

		case 20: {

		}

		case 21: {

		}

		case 22: {

		}

		case 23: {

		}

		case 24: {

		}

		case 25: {

		}*/

		default: {
			
			MazeOSS.outLog("System Call Handler -> System Call " + info.getNumSC() + ":"+ sysCallTable[info.getNumSC()].getName() + " not treated || No routine available!");
			break;
		}
		}

	}

	public void restoreRegister() {
		// Last task of the System Call handler

	}

	public void returnFromSysCall() {
		// Return From System Call
		// Switch mode Kernel::User
		//
	}

	public void handleSysCall(SharedInfo info) {
		this.saveRegister();
		this.executeSCSR(info);
		this.returnFromSysCall();
		this.restoreRegister();

	}

}
