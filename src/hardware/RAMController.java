package hardware;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import software.Instruction;
import software.PCB;
import software.Process;
import view.RamPane;

public class RAMController {
	@SuppressWarnings("unused")
	private RAM ram;
	private final int SIZE_FRAME_KB = 16; // 16KB
	private final int NUMBER_PAGES;
	public static DoubleProperty usedPagesProperty = new SimpleDoubleProperty(0);
	private Process[] pages;
	public static int OS_NB_PAGES = 2000;

	public RAMController(RAM ram) {
		this.ram = ram;
		NUMBER_PAGES = (int) ((ram.getSIZE_GB() * (Math.pow(2, 20))) / (SIZE_FRAME_KB));
		pages = new Process[NUMBER_PAGES];
	}
	
	public int getSIZE_FRAME_KB() {
		return SIZE_FRAME_KB;
	}

	public static int getOS_NB_PAGES() {
		return OS_NB_PAGES;
	}

	public int getNumberPages() {
		return NUMBER_PAGES;
	}

	public ArrayList<Integer> allocateMemoryTo(Process process) {
		float nbPage = (float)process.getSize_KB() / (float)SIZE_FRAME_KB ;
		int nombrePageToUse;
		if((nbPage - (int) nbPage ) > 0){
			nombrePageToUse = (int) nbPage + 1;
		}else {
			nombrePageToUse = (int) nbPage;
		}
		int n = 0;
		ArrayList<Integer> indexes = new ArrayList<>();
		// empty pages ?
		for (Process proc : pages) {
			if (proc == null) {
				indexes.add(n);
				if (indexes.size() == nombrePageToUse) {
					break;
				}
			}
			n++;
		}
		/////////////////////////////////
		if (!indexes.isEmpty()) {
			for (int i : indexes) {
				pages[i] = process;
			}
			
			Platform.runLater(()->{
				double val = usedPagesProperty.doubleValue() + indexes.size();
				usedPagesProperty.set(val);
			});
		}
		
		return indexes;
	}

	public void deallocateMemoryFrom(PCB pcb) {
		ArrayList<Integer> indexesOfProcess = pcb.getIndexesOfPages();
		indexesOfProcess.forEach(i ->{
			pages[i] = null;
		});
		
		Platform.runLater(()->{
			double fo = usedPagesProperty.get() - indexesOfProcess.size();
			usedPagesProperty.set(fo);
		});
			
	}

	public ArrayList<Integer> indexesOfPagesFor(Process process) {
		int k = 0;
		ArrayList<Integer> retour = new ArrayList<>();
		while (k < NUMBER_PAGES) {
			if (pages[k] == process) {
				retour.add(k);
			}
			k++;
		}
		return retour;
	}

	public boolean isFull() {
		for (Process process : pages) {
			if (process == null) {
				return false;
			}
		}
		return true;
	}
	public void allocateMemoryToOS() {
		ArrayList<Instruction> instructions = new ArrayList<>();
		instructions.add(new Instruction(false, (byte)1));
		Process osProcess = new Process(1,"MazeOS", instructions );
		int n = OS_NB_PAGES;
		for(int i=0 ;i < n;i++){
			pages[i] = osProcess;
		}

		usedPagesProperty.set(usedPagesProperty.get() + n);
		RamPane.usedOSRAM();
	}

}
