package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import software.MazeOS;
import software.PCB;
import software.Scheduler;

public class Overview extends BorderPane {
	private FlowPane fPane = new FlowPane();

	private static ListView<PCB> listReadyQueue = new ListView<>();
	private static ListView<PCB> listIOQueue = new ListView<>();
	private static GridPane infoCurrentProcess = new GridPane();
	private static Label idProcess = new Label("");
	private static Label nameProcess = new Label("");
	private static Label sizeProcess = new Label("");
	private static ProgressBar progressBar = new ProgressBar();

	public Overview() {
		//// *****Info on current Process********////////
		BorderPane paneInfoCurrentProcess = new BorderPane();
		Label labinfo = new Label("Info Current Process");
		/*infoCurrentProcess.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));*/
		infoCurrentProcess.add(new Label("Pid: "), 0, 0);
		infoCurrentProcess.add(idProcess, 1, 0);
		infoCurrentProcess.add(new Label("Name:"), 0, 1);
		infoCurrentProcess.add(nameProcess, 1, 1);
		infoCurrentProcess.add(new Label("Size: "), 0, 2);
		infoCurrentProcess.add(sizeProcess, 1, 2);

		paneInfoCurrentProcess.setPadding(new Insets(0, 0, 10, 10));
		paneInfoCurrentProcess.setPrefWidth(500);
		paneInfoCurrentProcess.setTop(labinfo);
		paneInfoCurrentProcess.setCenter(infoCurrentProcess);
		/////////// *********End********************//////////

		//// *****Ready QueueList********////////
		BorderPane paneListReadyQueue = new BorderPane();
		listReadyQueue.setFocusTraversable(false);
		listReadyQueue.setMaxHeight(50);
		listReadyQueue.setMinWidth(500);
		listReadyQueue.setDisable(false);
		listReadyQueue.setOrientation(Orientation.HORIZONTAL);

		paneListReadyQueue.setPadding(new Insets(0, 0, 10, 10));
		paneListReadyQueue.setTop(new Label("Ready Queue"));
		paneListReadyQueue.setCenter(listReadyQueue);
		/////////// *********End********************//////////

		//// *****IO Queue********////////
		BorderPane paneIoQueue = new BorderPane();
		listIOQueue.setFocusTraversable(false);
		listIOQueue.setMaxHeight(50);
		listIOQueue.setMinWidth(500);
		listIOQueue.setDisable(false);
		listIOQueue.setOrientation(Orientation.HORIZONTAL);

		paneIoQueue.setPadding(new Insets(0, 0, 10, 10));
		paneIoQueue.setTop(new Label("I\\O Queue"));
		paneIoQueue.setCenter(listIOQueue);
		/////////// *********End********************//////////

		//// *****ProgressBar********////////
		BorderPane paneProgressBar = new BorderPane();
		progressBar.setFocusTraversable(false);

		// paneProgressBar.setTop(new Label("I\\O Queue"));

		paneProgressBar.setCenter(progressBar);
		paneProgressBar.setPadding(new Insets(10, 10, 10, 10));
		/////////// *********End********************//////////

		/// ********Processes Pane********//
		ProcessesPane processesPane = new ProcessesPane();

		//processesPane.setFocusTraversable(false);
		/////////// *********End********************//////////

		fPane.setPadding(new Insets(20));

		fPane.getChildren().add(paneInfoCurrentProcess);
		fPane.getChildren().add(paneProgressBar);
		fPane.getChildren().add(paneListReadyQueue);
		fPane.getChildren().add(paneIoQueue);

		this.setCenter(fPane);
		this.setRight(processesPane);
	}

	public synchronized static void refreshListReadyQueue() {
		Platform.runLater(() -> {
			listReadyQueue.getItems().clear();

			ObservableList<PCB> list = FXCollections.observableArrayList();
			Scheduler.readyQueue.forEach(data -> {
				list.add(data);
			});

			listReadyQueue.setItems(list);
			//ProcessesPane.refresh();
		});
	}

	public synchronized static void refreshListIOQueue() {
		Platform.runLater(() -> {
			listIOQueue.getItems().clear();

			ObservableList<PCB> list = FXCollections.observableArrayList();
			if (Scheduler.ioQueue != null) {
				Scheduler.ioQueue.forEach(data -> {
					if (data!=null) {
						list.add(data.getPcb());
					}
				});
			}
			listIOQueue.setItems(list);
			//ProcessesPane.refresh();
		});
	}

	public synchronized static void refreshInfoCurrentProcess() {
		Platform.runLater(() -> {
			if (MazeOS.currentPCB != null) {
				idProcess.setText("" + MazeOS.currentPCB.getProcess().getId());
				nameProcess.setText(MazeOS.currentPCB.getProcess().getName());
				sizeProcess.setText("" + MazeOS.currentPCB.getProcess().getSize_KB());
			} else {
				idProcess.setText("");
				nameProcess.setText("");
				sizeProcess.setText("");
			}

		});
	}

	public synchronized static void refreshProgressBar(int current, int MAX) {
		Platform.runLater(() -> {
			progressBar.setProgress(((double) current / (double) MAX));
		});
	}
}
