package software;

import mazeoss.MazeOSS;
import view.Overview;

public class InterruptHandler {
	
	// Variable Vecteur d'interruption
	private Interrupt IDT [];
	private SysCallHandler sysCallHandler;
	
	public InterruptHandler(Interrupt IDT [], SysCallHandler sysCallHandler) {
		// TODO Auto-generated constructor stub
		this.IDT = IDT;
		this.sysCallHandler=sysCallHandler;
	}

	public InterruptHandler(Interrupt IDT []) {
		// TODO Auto-generated constructor stub
		this.IDT = IDT;
	}
	
	public void saveRegister() {
		// First task of the interrupt handler
	
	}
	
	public void schedule() {
		
		//Timer Interrupt
		//Scheduling operation
		
		try {
			if(MazeOS.currentPCB != null){
				int currentPriority = MazeOS.currentPCB.getPriority();
				currentPriority--;
				if(currentPriority<=MazeOS.currentPCB.getBasePriority()){
					MazeOS.currentPCB.setPriority(MazeOS.currentPCB.getBasePriority());
				} else{
					MazeOS.currentPCB.setPriority(currentPriority);
				}
				
				for (PCB pcb : Scheduler.readyQueue) {
					int pcbPriority = pcb.getPriority();
					pcbPriority++;
					if(pcbPriority<=99){
						pcb.setPriority(pcbPriority);
					}
					
				}
				
				MazeOS.scheduler.addPCBToReadyQueue(MazeOS.currentPCB);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		MazeOS.dispatcher.contextSwitch();
		//MazeOS.currentPCB=MazeOS.scheduler.pickPCBFromReadyQueue();
		Overview.refreshListReadyQueue();
		Overview.refreshInfoCurrentProcess();
		////System.out.println("***************"+Scheduler.readyQueue.size()+" Element(s) in ready queue ****************");
		//System.out.println("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
		//GraphOSS.Log("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
		MazeOSS.outLog("Scheduler -> Scheduling executed");
		
		
	}
	
	public void executeISR(SharedInfo info) {
		switch (info.getNumInt()) {
			/*case 0:{
							
			}
	
			case 1:{
						
			}
	
			case 2:{
		
			}
	
			case 3:{
						
			}
	
			case 4:{
				
			}
	
			case 5:{
			
			}
	
			case 6:{
			
			}
	
			case 7:{
		
			}
	
			case 8:{
	
			}
	
			case 9:{
		
			}
	
			case 10:{
			
			}
	
			case 11:{
		
			}
	
			case 12:{
			
			}
	
			case 13:{
				
			}
	
			case 14:{
			
			}*/
	
		/*case 15:{
	
				//System.out.println("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
				//GraphOSS.Log("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
				break;			
			}
	
			case 16:{
		
			}
	
			case 17:{
	
				GraphOSS.Log("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
				break;			
			}*/
	
			case 128:{
				sysCallHandler.executeSCSR(info);
				MazeOSS.outLog("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " treated");
				
				break;			
			}
	
			case 32:{
				schedule();
				MazeOSS.outLog("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " treated");
				break;			
			}
	
			case 33:{
			
			}
	
			case 34:{
			
			}
	
			case 35:{
				
			}
	
			case 36:{
		
			}
	
			case 38:{
			
			}
	
			case 40:{
		
			}
	
			case 43:{
			
			}
	
			case 44:{
	
			}
	
			case 45:{
			
			}
	
			case 46:{
			
			}
	
			case 47:{
				
				//IO Interrupt
				//IO operation
				if(MazeOS.currentPCB != null){
										
					IORequest ioRequest = new IORequest(MazeOS.currentPCB, info.getNumInt());
					MazeOS.scheduler.addRequestToIOQueue(ioRequest);
				}
				
				Overview.refreshListIOQueue();
				MazeOS.currentPCB = null;
				MazeOS.dispatcher.contextSwitch();
				Overview.refreshListReadyQueue();
				Overview.refreshInfoCurrentProcess();
				
				
				////System.out.println("***************"+Scheduler.readyQueue.size()+" Element(s) in ready queue ****************");
				//System.out.println("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
				//GraphOSS.Log("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
				
				//MazeOSS.outLog("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " treated");
				break;
			
			}
	
			default:{
				
				MazeOSS.outLog("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
				//GraphOSS.Log("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
				break;
			}
		}
		
		
		// Execute Interrupt Routine
	
	}
	
	public void returnFromInt () {
		// Return From Interrupt
		//Switch mode Kernel::User
		//
	}
	
	public void restoreRegister() {
		// Last task of the interrupt handler
	
	}
	
	public void handleInterrupt(SharedInfo info) {
		this.saveRegister();
		this.executeISR(info);
		this.returnFromInt();
		this.restoreRegister();
	
	}

}
