package view;

import java.util.stream.Collectors;

import com.sun.glass.events.MouseEvent;

import hardware.DiskController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import mazeoss.MazeOSS;
import software.Fichier;

public class DiskPane extends BorderPane {

	private static Data usedDisk = new Data("Used", 0);
	private static Data freeDisk = new Data("Free", 0);
	
	private static Label sizeDisk = new Label();
	private static Label sizeBlockDisk = new Label();
	private static Label nbreBlockDisk = new Label();

	
	public DiskPane() {
		this.setLeft(initDiskDescription());
		setSummary();
		this.setRight(initTreeView());
		this.setCenter(initPieChart());
	}

	public TreeView<Fichier> initTreeView() {
		TreeItem<Fichier> treeRoot = new TreeItem<>(MazeOSS.fichierRoot);
		TreeItem<Fichier> treeUser = new TreeItem<>(MazeOSS.fichierUser);

		treeRoot.setExpanded(true);
		/*
		 * Hashtable<String, TreeItem<Fichier>> table = new Hashtable<>();
		 * table.put(MazeOSS.fichierRoot.getName(), treeRoot);
		 */

		DiskController.FichierOnDisk.forEach(fichier -> {
			/*
			 * if (fichier.isDirectory()) { table.put(fichier.getName(), new
			 * TreeItem<Fichier>(fichier)); }else { TreeItem<Fichier> tmp =
			 * table.get((String)fichier.getFichierParent().getName());
			 * 
			 * if(tmp == null){ tmp = new TreeItem<Fichier>(fichier);
			 * table.put(fichier.getName(), tmp); tmp.setValue(fichier); }else {
			 * tmp.setValue(fichier); } }
			 */
			TreeItem<Fichier> tItem = new TreeItem<Fichier>(fichier);
			/*tItem.addEventHandler(MouseEvent.BUTTON_RIGHT, e->{
				
			});*/
			treeUser.getChildren().add(tItem);
			
		});
		treeRoot.getChildren().add(treeUser);
		TreeView<Fichier> retour = new TreeView<>(treeRoot);
		return retour;
	}

	public PieChart initPieChart() {
		ObservableList<Data> arg0 = FXCollections.observableArrayList(usedDisk, freeDisk);
		PieChart pieChart = new PieChart(arg0);

		/*usedDisk.getNode().setStyle("-fx-background-color:red");
		freeDisk.getNode().setStyle("-fx-background-color:green");*/

		usedDisk.pieValueProperty().bind(DiskController.usedBlocksProperty);
		freeDisk.pieValueProperty()
				.bind(Bindings.subtract(MazeOSS.diskController.getNUMBER_BLOCKS(), usedDisk.pieValueProperty()));
		DoubleBinding total = Bindings.createDoubleBinding(
				() -> pieChart.getData().stream().collect(Collectors.summingDouble(PieChart.Data::getPieValue)),
				pieChart.getData());
		pieChart.getData().forEach(data -> {
			data.nameProperty().bind(Bindings.concat(data.getName(), "\n", Bindings.format("%.1f%%",
					Bindings.divide(Bindings.multiply(100, data.pieValueProperty()), total))));
		});

		pieChart.setTitle("Disk Chart");
		return pieChart;
	}
	public BorderPane initDiskDescription(){
		BorderPane retour = new BorderPane();
		GridPane grid = new GridPane();
		TitledPane titledPane = new TitledPane("Summary", grid);
		
		grid.add(new Label("Size :\t"), 0, 0);
		grid.add(sizeDisk, 1, 0);
		grid.add(new Label("Size Block :\t"), 0, 1);
		grid.add(sizeBlockDisk, 1, 1);
		grid.add(new Label("Pages :\t"), 0, 2);
		grid.add(nbreBlockDisk, 1, 2);
		
		retour.setCenter(titledPane);
		return retour;
	}
	public static void setSummary(){
		sizeDisk.setText(""+MazeOSS.disk.getSIZE_GB()+" GB");
		sizeBlockDisk.setText(""+DiskController.getSIZE_BLOCK_KB()+" KB");
		nbreBlockDisk.setText(""+MazeOSS.diskController.getNUMBER_BLOCKS());
	}
}