package hardware;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import mazeoss.MazeOSS;
import software.Fichier;
import software.FichierApplication;
import software.Instruction;

public class DiskController {
	private Disk disk;
	private static final int SIZE_BLOCK_KB = 2; // KB
	private final int NUMBER_BLOCKS;
	public static IntegerProperty usedBlocksProperty = new SimpleIntegerProperty(0);
	private static Fichier[] blocks;
	public static ArrayList<Fichier> FichierOnDisk = new ArrayList<>();

	public DiskController(Disk disk) {
		this.disk = disk;
		NUMBER_BLOCKS = (int) ((disk.getSIZE_GB() * (Math.pow(2, 20))) / (SIZE_BLOCK_KB));
		blocks = new Fichier[NUMBER_BLOCKS];

		// init OS on disk
		ArrayList<Integer> indexesOfBlocks = getIndexesToAllow((int) (6 * (Math.pow(2, 20))));
		FichierApplication os = new FichierApplication(MazeOSS.fichierRoot, "OS", 1, null);
		os.setListBlock(indexesOfBlocks);
		this.write(os);

		// Application on disk
		loadAppOnDisk();
		/*
		  ArrayList<Integer> indexesOfBlocks1 = getIndexesToAllow((int) (10 *
		  (Math.pow(2, 20)))); FichierApplication pes = new
		  FichierApplication(MazeOSS.fichierRoot, "PES", 1, null);
		  pes.setListBlock(indexesOfBlocks1); this.write(pes);*/
		 
	}

	public static int getSIZE_BLOCK_KB() {
		return SIZE_BLOCK_KB;
	}

	public boolean write(Fichier fichier) {
		ArrayList<Integer> indexes = fichier.getListBlock();
		/////////////////////////////////
		if (!indexes.isEmpty()) {
			for (int i : indexes) {
				blocks[i] = fichier;
			}
			usedBlocksProperty.set(usedBlocksProperty.get() + indexes.size());
			return true;
		}
		return false;
	}

	public static ArrayList<Integer> getIndexesToAllow(long sizeOfFichier_KB) {
		int nombreBlockToUse = (int) (sizeOfFichier_KB / (SIZE_BLOCK_KB));
		int n = 0;
		ArrayList<Integer> indexes = new ArrayList<>();
		// empty pages ?
		for (Fichier fic : blocks) {
			if (fic == null) {
				indexes.add(n);
				if (indexes.size() == nombreBlockToUse) {
					break;
				}
			}
			n++;
		}
		return indexes;
	}

	public int getNUMBER_BLOCKS() {
		return NUMBER_BLOCKS;
	}

	public void loadAppOnDisk() {
		ArrayList<Instruction> instructions = new ArrayList<>();
		Random random = new Random();

		// Appliction mazeEditor
		for (int i = 0; i < 800; i++) {
			Instruction instruction = new Instruction(random.nextBoolean(), (byte) ((i % 3) + 1));
			instructions.add(instruction);
		}
		FichierApplication mazeEditor = new FichierApplication(MazeOSS.fichierUser, "Maze Editor.exe", 10,
				instructions);
		ArrayList<Integer> indexesOfBlocks1 = getIndexesToAllow(mazeEditor.getSIZE_B());
		mazeEditor.setListBlock(indexesOfBlocks1);
		this.write(mazeEditor);
		FichierOnDisk.add(mazeEditor);

		instructions = new ArrayList<>();
		// Appliction mazeSoccer
		for (int i = 0; i < 1500; i++) {
			Instruction instruction = new Instruction(random.nextBoolean(), (byte) ((i % 3) + 1));
			instructions.add(instruction);
		}
		FichierApplication mazeSoccer = new FichierApplication(MazeOSS.fichierUser, "Maze Soccer.exe", 50,
				instructions);
		ArrayList<Integer> indexesOfBlocks2 = getIndexesToAllow(mazeSoccer.getSIZE_B());
		mazeSoccer.setListBlock(indexesOfBlocks2);
		this.write(mazeSoccer);
		FichierOnDisk.add(mazeSoccer);

		instructions = new ArrayList<>();
		// Appliction mazeReader
		for (int i = 0; i < 1000; i++) {
			Instruction instruction = new Instruction(random.nextBoolean(), (byte) ((i % 3) + 1));
			instructions.add(instruction);
		}
		FichierApplication mazeReader = new FichierApplication(MazeOSS.fichierUser, "Maze Reader.exe", 16,
				instructions);
		ArrayList<Integer> indexesOfBlocks3 = getIndexesToAllow(mazeReader.getSIZE_B());
		mazeReader.setListBlock(indexesOfBlocks3);
		this.write(mazeReader);
		FichierOnDisk.add(mazeReader);

		instructions = new ArrayList<>();
		// Appliction mazeBrowser
		for (int i = 0; i < 2000; i++) {
			Instruction instruction = new Instruction(random.nextBoolean(), (byte) ((i % 3) + 1));
			instructions.add(instruction);
		}
		FichierApplication mazeBrowser = new FichierApplication(MazeOSS.fichierUser, "Maze Browser.exe", 10,
				instructions);
		ArrayList<Integer> indexesOfBlocks4 = getIndexesToAllow(mazeBrowser.getSIZE_B());
		mazeBrowser.setListBlock(indexesOfBlocks4);
		this.write(mazeBrowser);
		FichierOnDisk.add(mazeBrowser);
	}
}
