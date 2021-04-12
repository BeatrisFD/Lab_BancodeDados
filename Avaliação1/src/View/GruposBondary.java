package View;

import java.util.LinkedList;
import java.util.List;

import controller.gruposController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Grupo;
import model.Time;

public class GruposBondary  implements BoundaryContent,EventHandler<ActionEvent>{
	private BorderPane painelPrincipal = new BorderPane();
	private Button btnGerarGrupos = new Button("Gerar Grupos");
	private Pane painelGrupos = new VBox();
	
	private gruposController gc = new gruposController();
	
	
	public GruposBondary() {
		painelPrincipal.setStyle("-fx-padding: 10px");
		painelPrincipal.setTop(btnGerarGrupos);
		
		
		painelGrupos.setStyle("-fx-padding: 10px");
		
		btnGerarGrupos.addEventHandler(ActionEvent.ANY, this);
		
		gc.buscarGrupos();
		painelGrupos=gerarTabelaGrupos();
		painelPrincipal.setCenter(painelGrupos);
		
	}
	
	private Pane gerarTabelaGrupos() {
		Pane painelAux = new VBox();
		
		for (Grupo grupo: gc.getLista()) {
			Label lblGrupo = new Label(grupo.getGrupo()+"");
			painelAux.getChildren().addAll(lblGrupo,gerarTableView(grupo));
		}
		
		return painelAux;
		
	}
	
	private TableView<Time> gerarTableView(Grupo g){
		 TableView table = new TableView();
		 table.setStyle("-fx-padding: 10px");
		 TableColumn<Time, String> columnNomeTime = new TableColumn<>("Time");
		 columnNomeTime.setCellValueFactory(
					new PropertyValueFactory<Time, String>("nomeTime"));
			table.getColumns().addAll(columnNomeTime);
			ObservableList<Time> ol = FXCollections.observableArrayList();
			for (Time iterable_element : g.getTimes()) {
				ol.add(iterable_element);
			}
			table.setItems(ol);
		
		return table;
		
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getTarget()== btnGerarGrupos) {
			gc.gerarGrupos();
			gc.buscarGrupos();
			painelGrupos=gerarTabelaGrupos();
			painelPrincipal.setCenter(painelGrupos);
		}
		
	}

	@Override
	public Pane generateForm() {
		// TODO Auto-generated method stub
		return painelPrincipal;
	}

}
