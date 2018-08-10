package mazeoss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import hardware.CPU;
import hardware.Disk;
import hardware.DiskController;
import hardware.RAM;
import hardware.RAMController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import software.Fichier;
import software.IOManager;
import software.ManualGen;
import software.MazeOS;
import software.RandomGen;
import software.SharedInfo;
import software.StartupGen;
import software.ThreadCom;
import view.GraphOSS;
import view.RamPane;

public class MazeOSS extends Application {
	// fichier root
	public static Fichier fichierRoot = new Fichier(null, "root", true);
	// fichier user
	public static Fichier fichierUser = new Fichier(fichierRoot, "user", true);
	public static Stage primaryStage;
	public static RAM ram = new RAM();
	public static Disk disk = new Disk();
	public static RAMController ramController = new RAMController(ram);
	public static DiskController diskController = new DiskController(disk);
	public static int processCounter = 1;
	public static CPU intel = new CPU();
	
	public static ThreadCom threadCom = new ThreadCom();
	
	
	public static PrintStream out;
	
	public static RandomGen randomGen;
	public static ManualGen manualGen;
	public static StartupGen startupGen;
	public static IOManager ioManager;
	
	public static Thread threadIO;
	public static Thread threadOS;
	public static Thread threadrandGen;
	public static Thread threadManuGen;
	public static Thread threadStartupGen;

	public void start(Stage primaryStage) {
		try {
			MazeOSS.primaryStage = primaryStage;
			MazeOSS.primaryStage.getIcons().add(new Image("file:resources/operating_system1600.png"));
			MazeOSS.primaryStage.setOnCloseRequest(e -> {
				GraphOSS.quitControl();
				e.consume();
			});

			GraphOSS root = new GraphOSS();
			Scene scene = new Scene(root, 800, 450);
			primaryStage.setTitle("Maze Operating System Simulator 1.0");
			primaryStage.setResizable(false);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			/*
			 * ArrayList<Integer> list = DiskController.getIndexesToAllow((int)
			 * (10 * Math.pow(2, 30))); Fichier fichier = new
			 * Fichier("PES17.exe", list, false); diskController.write(fichier);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initPC() {
		GraphOSS.Log("BIOS ...");
		GraphOSS.Log("POST ...");
		// Memoire principale
		ram = new RAM();
		GraphOSS.Log("RAM ... ok!");
		// Memoire secondaire
		disk = new Disk();
		GraphOSS.Log("Disk ... ok!");
		// BIOS
		GraphOSS.Log("MazeLoader Loading Operating System ...");
		GraphOSS.Log("MazeOSS Loading...");
		ramController.allocateMemoryToOS();
		RamPane.setSummary();
		GraphOSS.Log("MazeOSS Setup...");
		GraphOSS.Log("MazeOSS Initialisation...");
		GraphOSS.Log("Start Kernel...");
		GraphOSS.Log("User Authentication ... ok!");
		GraphOSS.Log("User Process started!");
		
		try {
			out = new PrintStream(new File("out.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		outLog("BIOS ...");
		outLog("POST ...");
		// Memoire principale
		outLog("RAM ... ok!");
		// Memoire secondaire
		outLog("Disk ... ok!");
		// BIOS
		outLog("MazeLoader Loading Operating System ...");
		outLog("MazeOSS Loading...");
		outLog("MazeOSS Setup...");
		outLog("MazeOSS Initialisation...");
		outLog("Start Kernel...");
		outLog("User Authentication ... ok!");
		outLog("User Process started!");
		

		SharedInfo info = new SharedInfo(-1, -1, false);

		MazeOS mazeOS = new MazeOS(info);
		IOManager ioManager = new IOManager(info);
		MazeOSS.ioManager = ioManager;
		
		RandomGen randomGen = new RandomGen(info);
		MazeOSS.randomGen = randomGen;
		
		ManualGen manualGen = new ManualGen(info);
		MazeOSS.manualGen = manualGen;
		
		StartupGen startupGen = new StartupGen(info);
		MazeOSS.startupGen = startupGen;

		Thread ManualGenerator = new Thread(manualGen, "Manual");
		threadManuGen = ManualGenerator;
		
		Thread Startup = new Thread(startupGen, "Startup");
		threadStartupGen = Startup;
		
		Thread RandomEvent = new Thread(randomGen, "Random");
		threadrandGen = RandomEvent;
		
		Thread SE = new Thread(mazeOS, "SE");
		Thread IO = new Thread(ioManager, "IO");
		threadIO = IO;
		threadOS = SE;
		
		SE.start();
		Startup.start();
		
		//IO.start();
		
		/*try {
			GEN.join();
			SE.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		outLog("MazeOSS -> Simulation done");
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void outLog(String message){
		out.println(message);
	}
	


}
