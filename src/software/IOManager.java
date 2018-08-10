package software;

import java.util.ArrayList;

import mazeoss.MazeOSS;

public class IOManager implements Runnable {
	
	private SharedInfo info;
	private ArrayList<Integer> IONumbers = new ArrayList<>();

	public IOManager(SharedInfo info) {
		super();
		this.info = info;
	}
	
	public void initIONumbers(){
		IONumbers.add(32);
		IONumbers.add(33);
		IONumbers.add(34);
		IONumbers.add(35);
		IONumbers.add(36);
		IONumbers.add(38);
		IONumbers.add(40);
		IONumbers.add(43);
		IONumbers.add(44);
		IONumbers.add(45);
		IONumbers.add(46);
		IONumbers.add(47);
		
	}

	@Override
	public void run() {
		
		while (true){
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			try {
				synchronized (info) {
					MazeOSS.threadCom.ioSide(info);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
		

	}


}
