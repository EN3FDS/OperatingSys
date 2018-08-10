package view;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import mazeoss.MazeOSS;
import software.ManualGen;
import software.PCB;
import software.RandomGen;
import software.Scheduler;
import software.ThreadCom;

public class ProcessesPane extends BorderPane {

	public static TableView<PCB> pcbTable = new TableView<PCB>();
	private static TableColumn<PCB, String> pidColumn = new TableColumn<PCB, String>();
	private static TableColumn<PCB, String> nameColumn = new TableColumn<PCB, String>();

	private ObservableList<PCB> pcbData = FXCollections.observableArrayList();
	private ContextMenu contextMenu = new ContextMenu();

	public ProcessesPane() {
		pidColumn.setText("Pid");
		nameColumn.setText("name");
		pcbTable.getColumns().add(pidColumn);
		pcbTable.getColumns().add(nameColumn);

		pidColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

		pcbTable.setItems(pcbData);

		MenuItem kill = new MenuItem("End Task");
		kill.setOnAction(e -> {
			int id = pcbTable.getSelectionModel().getSelectedItem().getId();
			RandomGen.idProcess = id;
			RandomGen.manualOn = true;
			RandomGen.numInt = 128;
			RandomGen.numSC = 1;
			
			
			
			
			//System.out.println(pcbTable.getSelectionModel().getSelectedItem().getId());
		});
		contextMenu.getItems().add(kill);
		pcbTable.setOnMouseClicked(e -> {
			if (e.isPopupTrigger() && !pcbTable.getItems().isEmpty()) {// manage
																		// of
																		// the
																		// popup
				contextMenu.show(pcbTable, e.getScreenX(), e.getScreenY());
			} else {
				contextMenu.hide();
			}
		});

		pcbTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		pcbTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		this.setCenter(pcbTable);
	}

	public static void refresh() {
			/*ObservableList<PCB> data = FXCollections.observableArrayList();
			Scheduler.ioQueue.forEach(pcb -> {
				data.add(pcb.getPcb());
			});
			Scheduler.readyQueue.forEach(pcb -> {
				data.add(pcb);
			});
			pcbTable.setItems(data);*/
		
		//pcbTable.getItems().clear();
		//pcbTable.setItems(Scheduler.processQueue);
		Platform.runLater(() -> {
			pcbTable.getItems().clear();

			ObservableList<PCB> list = FXCollections.observableArrayList();
			Scheduler.processQueue.forEach(data -> {
				list.add(data);
			});

			pcbTable.setItems(list);
			//ProcessesPane.refresh();
		});
	}
}
