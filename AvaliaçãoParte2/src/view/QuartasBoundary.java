package View;

import java.util.List;

import dao.IJogosDao;
import dao.JogosDao;
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
import model.Grupo;
import model.JogoQuartas;
import model.Time;

public class QuartasBoundary implements BoundaryContent,EventHandler<ActionEvent> {
	private BorderPane painelPrincipal = new BorderPane();
	private Button btnAtualizar = new Button("Atualizar");
	private Label lblTitulo = new Label("Quartas de Final Parciais"); 
	
	private IJogosDao jDao = new JogosDao();
	
	public QuartasBoundary() {
		painelPrincipal.setStyle("-fx-padding: 10px");
		painelPrincipal.setTop(lblTitulo);
		painelPrincipal.setBottom(btnAtualizar);
		
		painelPrincipal.setCenter(gerarTableView(jDao.buscarQuartas()));
	}
	
	private TableView<JogoQuartas> gerarTableView(List<JogoQuartas> list){
		 TableView table = new TableView();
		 table.setStyle("-fx-padding: 10px");
		 TableColumn<JogoQuartas, String> columnNomeTimeA = new TableColumn<>("Time A");
		 columnNomeTimeA.setCellValueFactory(
					new PropertyValueFactory<JogoQuartas, String>("timeA"));
		 TableColumn<JogoQuartas, String> columnNomeTimeB = new TableColumn<>("Time B");
		 columnNomeTimeB.setCellValueFactory(
					new PropertyValueFactory<JogoQuartas, String>("timeB"));
			table.getColumns().addAll(columnNomeTimeA,columnNomeTimeB);
			ObservableList<JogoQuartas> ol = FXCollections.observableArrayList();
			for (JogoQuartas iterable_element : list) {
				ol.add(iterable_element);
			}
			table.setItems(ol);
		
		return table;
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(event.getTarget()==btnAtualizar) {
			painelPrincipal.setCenter(gerarTableView(jDao.buscarQuartas()));
		}
	}

	@Override
	public Pane generateForm() {
		return painelPrincipal;
	}
	
}
