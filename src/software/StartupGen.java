package software;

import java.util.ArrayList;

import hardware.DiskController;
import mazeoss.MazeOSS;

public class StartupGen implements Runnable{
	
	private SharedInfo info;
	private int numApp = -1;
	private ArrayList<FichierApplication> AppOnDisk = new ArrayList<>();
	private ArrayList<Integer> indexesOfApp = new ArrayList<>();
	private ArrayList<Fichier> fichierOnDisk = DiskController.FichierOnDisk;
	

	public StartupGen(SharedInfo info) {
		super();
		this.info = info;
	}


	public void startPC (){
		for (Fichier fichier : fichierOnDisk) {
			//System.out.println(fichier.getClass());
			if (fichier.getClass().getName().equals("software.FichierApplication")) {
				indexesOfApp.add(fichierOnDisk.indexOf(fichier));
				AppOnDisk.add((FichierApplication) fichier);
			}

		}
		
		if(!AppOnDisk.isEmpty()){
			
			
			do {
				
				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					MazeOSS.threadCom.startupGenSide(info);
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
				
				
			} while (numApp <= AppOnDisk.size() + 1);
			
			
			//MazeOSS.threadManuGen.start();
			
			
		}
		

	}
	
	public void setInfo(SharedInfo info){
		numApp++;
		if (numApp < AppOnDisk.size()) {
			info.setNumInt(128);
			info.setNumSC(11);
			info.setNumApp(numApp);
		} else if (numApp == AppOnDisk.size()) {
			info.setNumInt(32);
		} else {
			info.setNumInt(32);
			info.setStartupDone(true);
		}
		
		
		
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		startPC();
	}
	
	

}
