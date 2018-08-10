package software;

import java.util.ArrayList;
import java.util.Random;

import mazeoss.MazeOSS;
import view.Overview;

public class RandomGen implements Runnable {
	

	public static boolean manualOn = false;
	public static int idProcess;
	public static int idApp;
	public static int numInt;
	public static int numSC;
	
	private ArrayList<Integer> exceptionsNumber;
	private ArrayList<Integer> sysCallNumber;
	
	private SharedInfo info;
	
	private int exceptionNb;
	private int sysCallNb;

	private Random rand = new Random();
	

	public RandomGen(SharedInfo info) {
		this.info=info;
		this.initExceptionsNumber();
		this.initSysCallNumber();
	}

	public void initExceptionsNumber() {
		exceptionsNumber = new ArrayList<Integer>();
		exceptionsNumber.add(0);
		exceptionsNumber.add(1);
		exceptionsNumber.add(3);
		exceptionsNumber.add(4);
		exceptionsNumber.add(5);
		exceptionsNumber.add(6);
		exceptionsNumber.add(7);
		exceptionsNumber.add(10);
		exceptionsNumber.add(11);
		exceptionsNumber.add(12);
		exceptionsNumber.add(13);
		exceptionsNumber.add(14);
		exceptionsNumber.add(16);
		exceptionsNumber.add(17);
		exceptionsNumber.add(128);
		exceptionsNumber.add(128);
		exceptionsNumber.add(128);
		exceptionsNumber.add(128);
		exceptionsNumber.add(128);
		exceptionsNumber.add(128);
		
		//********************
		exceptionsNumber.add(32);
		exceptionsNumber.add(33);
		exceptionsNumber.add(34);
		exceptionsNumber.add(35);
		exceptionsNumber.add(36);
		exceptionsNumber.add(38);
		exceptionsNumber.add(40);
		exceptionsNumber.add(43);
		exceptionsNumber.add(44);
		exceptionsNumber.add(45);
		exceptionsNumber.add(46);
		exceptionsNumber.add(47);

	}

	public void initSysCallNumber() {
		sysCallNumber = new ArrayList<Integer>();
		sysCallNumber.add(0);
		//sysCallNumber.add(1);
		sysCallNumber.add(3);
		sysCallNumber.add(4);
		sysCallNumber.add(5);
		sysCallNumber.add(6);
		sysCallNumber.add(7);
		sysCallNumber.add(8);
		sysCallNumber.add(9);
		sysCallNumber.add(10);
		//sysCallNumber.add(11);
		sysCallNumber.add(12);
		sysCallNumber.add(13);
		sysCallNumber.add(14);
		sysCallNumber.add(15);
		sysCallNumber.add(16);
		sysCallNumber.add(17);
		sysCallNumber.add(18);
		sysCallNumber.add(19);
		sysCallNumber.add(20);
		sysCallNumber.add(21);
		sysCallNumber.add(22);
		sysCallNumber.add(23);
		sysCallNumber.add(24);
		sysCallNumber.add(25);
		
	}
	
	public void setManInfo (SharedInfo info) {
		manualOn=false;
		
		try {
			Thread.sleep(40);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
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

	public void setInfo(SharedInfo info) {
		
		if (MazeOS.currentPCB != null) {
			
			Process currentProcess = MazeOS.currentPCB.getProcess();
			int currentInst = MazeOS.currentPCB.getCurrentInstruction();
			if (MazeOS.counterTimer > 10) {
				info.setNumInt(32);
				MazeOS.counterTimer = 0;
				return;
			}
			
			
			if (currentInst < currentProcess.getInstructions().size() - 1) {
				
				
				// Thread.sleep(200);
				exceptionNb = exceptionsNumber.get(rand.nextInt(exceptionsNumber.size()));
				sysCallNb = sysCallNumber.get(rand.nextInt(sysCallNumber.size()));
				
				
				info.setNumInt(exceptionNb);
				info.setNumSC(sysCallNb);
				Overview.refreshProgressBar(currentInst, MazeOS.currentPCB.getProcess().getInstructions().size());
				currentInst++;
				MazeOS.currentPCB.setCurrentInstruction(currentInst);
				MazeOS.counterTimer++;

			} else {
				//MazeOS.currentPCB = null;
				info.setNumInt(128);
				info.setNumSC(1);
				// Terminer Process
				// go to scheduling
				//return;

			}
		} 
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			try {
				Thread.sleep(40);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			try {
				MazeOSS.threadCom.randGenSide(info);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(40);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	

}
