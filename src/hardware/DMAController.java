package hardware;

public class DMAController {
	private DiskController diskController;
	private RAMController ramController;
	
	public DMAController(DiskController diskController,RAMController ramController){
		this.diskController = diskController;
		this.ramController = ramController;
	}

}
