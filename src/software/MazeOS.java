package software;

import mazeoss.MazeOSS;

public class MazeOS implements Runnable{
	public static Interrupt IDT[];
	public static InterruptHandler interruptHandler;
	public static SysCall sysCallTable[];
	public static SysCallHandler sysCallHandler;
	
	public static Scheduler scheduler=new Scheduler();
	public static Dispatcher dispatcher=new Dispatcher();
	public static PCB currentPCB;
	public static int counterTimer=0;
	
	private short adrressInit=0;
	private SharedInfo info;
	

	public MazeOS(SharedInfo info) {
		super();
		this.info=info;
		initSysCallTable();
		initIDT();
	}
		
	public Interrupt[] getIDT() {
		return IDT;
	}

	public SysCall[] getSysCallTable() {
		return sysCallTable;
	}

	public void initIDT(){
		IDT=new Interrupt[256];
		
		//Interrupt Descriptor Table pre initialization
		for (int i=0; i<256; i++){
			IDT[i]=new Interrupt("Unknown Interrupt", adrressInit, (byte) 0);
		}
		
		//Exception initialization
		adrressInit+=8;
		IDT[0]= new Interrupt("Divide error", adrressInit, (byte) 3);
		adrressInit+=8;
		IDT[1]= new Interrupt("Debug", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[2]= new Interrupt("NMI", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[3]= new Interrupt("Breakpoint", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[4]= new Interrupt("Overflow", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[5]= new Interrupt("Bounds check", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[6]= new Interrupt("Invalid opcode", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[7]= new Interrupt("Device not available", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[8]= new Interrupt("Double falut", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[9]= new Interrupt("Coprocessor segment overrun", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[10]= new Interrupt("Invalid TSS", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[11]= new Interrupt("Segment not present", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[12]= new Interrupt("Stack exception", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[13]= new Interrupt("General protection", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[14]= new Interrupt("Page fault", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[16]= new Interrupt("Floating point error", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[17]= new Interrupt("Alignement check", adrressInit, (byte) 0);
		
		adrressInit+=8;
		IDT[128]= new Interrupt("System call", adrressInit, (byte) 0);
		
		//Interrupt initialisation
		adrressInit+=8;
		IDT[32]= new Interrupt("Timer", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[33]= new Interrupt("Keyboard", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[34]= new Interrupt("PIC cascading", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[35]= new Interrupt("Second serial port", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[36]= new Interrupt("First serial port", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[38]= new Interrupt("Floppy disk", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[40]= new Interrupt("System clock", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[43]= new Interrupt("Network interface", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[44]= new Interrupt("PS/2 mouse", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[45]= new Interrupt("Mathematical coprocessor", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[46]= new Interrupt("EIDE disk controller's first chain", adrressInit, (byte) 0);
		adrressInit+=8;
		IDT[47]= new Interrupt("EIDE disk controller's second chain", adrressInit, (byte) 0);
		
		interruptHandler=new InterruptHandler(IDT,sysCallHandler);
	}

	public void initSysCallTable(){
		sysCallTable=new SysCall[256];
		
		//System Call Table pre initialization
		adrressInit+=8;
		for (int i=0; i<256; i++){
			sysCallTable[i]=new SysCall("Unknown SysCall", adrressInit);
		}
		
		//System Call Table initialization
		adrressInit+=4;
		sysCallTable[0]= new SysCall("restart syscall", adrressInit);
		adrressInit+=4;
		sysCallTable[1]= new SysCall("exit", adrressInit);
		adrressInit+=4;
		sysCallTable[2]= new SysCall("fork", adrressInit);
		adrressInit+=4;
		sysCallTable[3]= new SysCall("read", adrressInit);
		adrressInit+=4;
		sysCallTable[4]= new SysCall("write", adrressInit);
		adrressInit+=4;
		sysCallTable[5]= new SysCall("open", adrressInit);
		adrressInit+=4;
		sysCallTable[6]= new SysCall("close", adrressInit);
		adrressInit+=4;
		sysCallTable[7]= new SysCall("waitpid", adrressInit);
		adrressInit+=4;
		sysCallTable[8]= new SysCall("create", adrressInit);
		adrressInit+=4;
		sysCallTable[9]= new SysCall("link", adrressInit);
		adrressInit+=4;
		sysCallTable[10]= new SysCall("unlink", adrressInit);
		adrressInit+=4;
		sysCallTable[11]= new SysCall("execve", adrressInit);
		adrressInit+=4;
		sysCallTable[12]= new SysCall("chdir", adrressInit);
		adrressInit+=4;
		sysCallTable[13]= new SysCall("time", adrressInit);
		adrressInit+=4;
		sysCallTable[14]= new SysCall("mknod", adrressInit);
		adrressInit+=4;
		sysCallTable[16]= new SysCall("chmod", adrressInit);
		adrressInit+=4;
		sysCallTable[17]= new SysCall("lchown", adrressInit);
		adrressInit+=4;
		sysCallTable[18]= new SysCall("stat", adrressInit);
		adrressInit+=4;
		sysCallTable[19]= new SysCall("lseek", adrressInit);
		adrressInit+=4;
		sysCallTable[20]= new SysCall("getpid", adrressInit);
		adrressInit+=4;
		sysCallTable[21]= new SysCall("mount", adrressInit);
		adrressInit+=4;
		sysCallTable[22]= new SysCall("unmount", adrressInit);
		adrressInit+=4;
		sysCallTable[23]= new SysCall("setuid", adrressInit);
		adrressInit+=4;
		sysCallTable[24]= new SysCall("getuid", adrressInit);
		adrressInit+=4;
		sysCallTable[25]= new SysCall("stime", adrressInit);
		
		sysCallHandler=new SysCallHandler(sysCallTable);
		}

	@Override
	public void run() {
		while (true) {
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			try {
				MazeOSS.threadCom.osSide(info);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
	}

}
