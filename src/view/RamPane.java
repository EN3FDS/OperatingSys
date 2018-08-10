package view;

import java.util.stream.Collectors;

import hardware.RAMController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import mazeoss.MazeOSS;

public class RamPane extends BorderPane {
	private static Data usedOSRAM = new Data("MazeOS", 0);
	private static Data usedRAM = new Data("Process", 0);
	private static Data freeRAM = new Data("Free", MazeOSS.ramController.getNumberPages());
	
	private static Label sizeRAM = new Label();
	private static Label sizePagesRAM = new Label();
	private static Label nbrePagesRAM = new Label();
	private static Label nbrePagesRAM_OS = new Label();
	

	public RamPane() {
		ObservableList<Data> arg0 = FXCollections.observableArrayList(usedOSRAM, usedRAM, freeRAM);
		PieChart pieChart = new PieChart(arg0);

		
		DoubleBinding total = Bindings.createDoubleBinding(
				() -> pieChart.getData().stream().collect(Collectors.summingDouble(PieChart.Data::getPieValue)),
				pieChart.getData());
		pieChart.getData().forEach(data -> {
			data.nameProperty().bind(Bindings.concat(data.getName(), "\n", Bindings.format("%.2f%%",
					Bindings.divide(Bindings.multiply(100, data.pieValueProperty()), total))));
		});
		this.setRight(initRamDescription());
		this.setCenter(pieChart);
	}
	public BorderPane initRamDescription(){
		BorderPane retour = new BorderPane();
		GridPane grid = new GridPane();
		TitledPane titledPane = new TitledPane("Summary", grid);
		
		grid.add(new Label("Size :\t"), 0, 0);
		grid.add(sizeRAM, 1, 0);
		grid.add(new Label("Size Frame :\t"), 0, 1);
		grid.add(sizePagesRAM, 1, 1);
		grid.add(new Label("Pages :\t"), 0, 2);
		grid.add(nbrePagesRAM, 1, 2);
		grid.add(new Label("OS Pages :\t"), 0, 3);
		grid.add(nbrePagesRAM_OS, 1, 3);
		
		retour.setCenter(titledPane);
		return retour;
	}

	public static void usedOSRAM() {
		freeRAM.pieValueProperty().bind(Bindings.subtract(MazeOSS.ramController.getNumberPages()-RAMController.OS_NB_PAGES,
				usedRAM.pieValueProperty()));
		usedRAM.pieValueProperty().bind(Bindings.subtract( RAMController.usedPagesProperty,RAMController.OS_NB_PAGES));
		usedOSRAM.setPieValue(RAMController.OS_NB_PAGES);
	}
	public static void setSummary(){
		sizeRAM.setText(""+MazeOSS.ram.getSIZE_GB()+" GB");
		sizePagesRAM.setText(""+MazeOSS.ramController.getSIZE_FRAME_KB()+" KB");
		nbrePagesRAM.setText(""+MazeOSS.ramController.getNumberPages());
		nbrePagesRAM_OS.setText(""+RAMController.getOS_NB_PAGES());
	}
}
