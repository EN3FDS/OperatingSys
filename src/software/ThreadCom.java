package software;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import mazeoss.MazeOSS;
import view.Overview;

public class ThreadCom {
	

	public static Lock lock = new ReentrantLock();
	public static Condition genCondition = lock.newCondition();
	
	public static Condition osCondition = lock.newCondition();
	
	
	
	public ThreadCom() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void randGenSide(SharedInfo info) throws InterruptedException{
		lock.lock();
		try {
			while(!info.isRunningGen()){
				genCondition.await();
			}
			
			if(RandomGen.manualOn){
				RandomGen.manualOn = false;
				MazeOSS.randomGen.setManInfo(info);
			}else {
				do{
					MazeOSS.randomGen.setInfo(info);
				} while (info.getNumInt() == -1);
				
			}
			
			info.setRunningSE(true);
			info.setRunningGen(false);
			osCondition.signal();
			
		
			
		} finally {
			// TODO: handle finally clause
			lock.unlock();
		}
	}
	
	
	public synchronized void ioSide(SharedInfo info) throws InterruptedException{
		//lock.lock();
		try {
			
			
			if(!Scheduler.ioQueue.isEmpty()){
				
				IORequest ioRequest=MazeOS.scheduler.pickRequestFromIOQueue();
				if (ioRequest != null){
					//System.out.println("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
					//GraphOSS.Log("Interrupt Handler -> Interrupt "+info.getNumInt()+":"+IDT[info.getNumInt()].getName()+ " not treated || No routine available!");
					//System.out.println("I/O Manager -> Interrupt "+ioRequest.getNumInt()+":"+MazeOS.IDT[ioRequest.getNumInt()].getName()+ " treated!");
					//System.out.println("Process "+ioRequest.getPcb().getId()+ " READY!");
					MazeOSS.outLog("I/O Manager -> Interrupt "+ioRequest.getNumInt()+":"+MazeOS.IDT[ioRequest.getNumInt()].getName()+ " treated!");
					MazeOSS.outLog("I/O Manager -> Process "+ioRequest.getPcb().getId()+ " READY!");
					
					MazeOS.scheduler.addPCBToReadyQueue(ioRequest.getPcb());
					
					if (MazeOS.currentPCB == null){
						MazeOS.currentPCB = MazeOS.scheduler.pickPCBFromReadyQueue();
						
					}
				}
				
				
				Overview.refreshListIOQueue();
				Overview.refreshListReadyQueue();
				Overview.refreshInfoCurrentProcess();


			}

			
		} finally {
			// TODO: handle finally clause
			//lock.unlock();
		}
	}
	
	
	public void manualGenSide(SharedInfo info) throws InterruptedException{
		lock.lock();
		try {
			while(!info.isRunningGen()){
				genCondition.await();
			}
			
			MazeOSS.manualGen.setInfo(info);
			info.setRunningSE(true);
			info.setRunningGen(false);
			osCondition.signal();
			
		} finally {
			// TODO: handle finally clause
			lock.unlock();
		}
	}
	
	
	public void startupGenSide(SharedInfo info) throws InterruptedException{
		lock.lock();
		try {
			while(!info.isRunningGen()){
				genCondition.await();
			}
			
			
			if(info.isStartupDone()){
				try {
					//MazeOSS.threadManuGen.start();
					MazeOSS.threadrandGen.start();
					MazeOSS.threadIO.start();
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			} else {
				MazeOSS.startupGen.setInfo(info);
				info.setRunningSE(true);
				osCondition.signal();
			}
			
			
			
		} finally {
			// TODO: handle finally clause
			lock.unlock();
		}
	}
	
	
	public void osSide(SharedInfo info) throws InterruptedException{
		lock.lock();
		try {
			while(!info.isRunningSE()){
				osCondition.await();
			}
			
			MazeOSS.outLog("MazeOS -> Interrupt " + info.getNumInt() + " Received");
			MazeOS.interruptHandler.handleInterrupt(info);
			
			
			info.setNumInt(-1);
            info.setNumSC(-1);
            info.setRunningSE(false);
            info.setRunningGen(true);
            
            genCondition.signalAll();
			
			
		} finally {
			// TODO: handle finally clause
			lock.unlock();
		}
	}


}
