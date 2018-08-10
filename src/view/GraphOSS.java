package view;

import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mazeoss.MazeOSS;

public class GraphOSS extends BorderPane {
	private static LogOSS logger = new LogOSS();

	public GraphOSS() {
		Tab overviewTab = new Tab("Overview");
		Tab memoryTab = new Tab("Memory");
		Tab diskTab = new Tab("Disk");
				

		Overview overview = new Overview();
		RamPane memoryPane = new RamPane();
		DiskPane diskPane = new DiskPane();

		overviewTab.setContent(overview);
		memoryTab.setContent(memoryPane);
		diskTab.setContent(diskPane);

		TabPane onglets = new TabPane(overviewTab, memoryTab, diskTab);
		onglets.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		this.setTop(initMenuBar());
		this.setCenter(onglets);
		//this.setBottom(logger);
	}

	public MenuBar initMenuBar() {

		MenuBar menuBar = new MenuBar();
		////////////////////////////////
		Menu file = new Menu("File");
		Menu menuAbout = new Menu("About");
		//////////////////////////////////
		MenuItem simulate = new MenuItem("Simulate");
		
		MenuItem pause = new MenuItem("Pause");
		MenuItem stop = new MenuItem("Stop");
		MenuItem replay = new MenuItem("Replay");
		
		MenuItem shLog = new MenuItem("Show Log");
		MenuItem quit = new MenuItem("Close");

		MenuItem about = new MenuItem("About");
		/////////////////////////////////////

		// onAction
		simulate.setOnAction(e -> {
			MazeOSS.initPC();
			simulate.setDisable(true);
		});
		shLog.setOnAction(e->{
			String command = "\"C:\\Users\\Public\\Desktop\\Notepad++.lnk\" out.txt";
			try {
				Process rProcess = Runtime.getRuntime().exec(command);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		quit.setOnAction(e -> {
			quitControl();
		});
		about.setOnAction(e -> {
			about();
		});
		// addMenuitem to menu
		file.getItems().add(simulate);
		
		pause.setDisable(true);
		replay.setDisable(true);
		stop.setDisable(true);
		
		file.getItems().add(pause);
		file.getItems().add(replay);
		file.getItems().add(stop);
		
		file.getItems().add(shLog);
		file.getItems().add(quit);

		menuAbout.getItems().add(about);

		// add menu to menubar
		menuBar.getMenus().add(file);
		menuBar.getMenus().add(menuAbout);

		return menuBar;
	}

	public static void quitControl() {
		BorderPane page = new BorderPane();

		// Create the dialog Stage.
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Quit?");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(MazeOSS.primaryStage);
		Scene scene = new Scene(page);
		page.setMinSize(200, 100);
		dialogStage.setScene(scene);

		// SET THE DIALOG
		Label label = new Label("Are you sure?");
		ButtonBar control = new ButtonBar();
		Button yes = new Button("Yes");
		Button no = new Button("no");

		yes.setOnMouseReleased(e -> {
			System.exit(0);
		});
		no.setOnMouseReleased(f -> {
			dialogStage.close();
		});

		control.getButtons().add(yes);
		control.getButtons().add(no);

		page.setCenter(label);
		page.setBottom(control);
		BorderPane.setAlignment(label, Pos.CENTER);

		dialogStage.showAndWait();
	}

	public static void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(MazeOSS.primaryStage.getTitle());
		alert.setHeaderText("About");
		alert.setContentText("Author:\n\tRalph JEAN\n\tJean Mary BELOTTE\n\tDavessenn J. M. LOUIS-JEUNE\n\nProf.: Ing. Carl DARBOUZE");
		alert.showAndWait();
	}

	public static void Log(String text) {
		//logger.appendText(text + "\n");
	}
}
