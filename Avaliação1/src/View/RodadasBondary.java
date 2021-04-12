package View;

import java.time.LocalDate;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import controller.jogosController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import model.Jogo;
import model.Time;

public class RodadasBondary  implements BoundaryContent,EventHandler<ActionEvent>{
	private BorderPane painelPrincipal = new BorderPane();
	private FlowPane painelPesquisa = new FlowPane();
	private Label lblData = new Label("Pesquisar por Data : ");
	private TextField txtData = new TextField();
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnGerarRodadas = new Button("Gerar Rodadas");
	private TableView table = new TableView<>();
	
	jogosController jc = new jogosController();
	
	public RodadasBondary() {
		painelPrincipal.setStyle("-fx-padding: 10px");
		painelPesquisa.getChildren().addAll(lblData,txtData,btnPesquisar);
		painelPesquisa.setStyle("-fx-padding: 10px");
		painelPrincipal.setBottom(painelPesquisa);
		painelPrincipal.setTop(btnGerarRodadas);
		
		btnPesquisar.addEventHandler(ActionEvent.ANY, this);
		btnGerarRodadas.addEventHandler(ActionEvent.ANY, this);
		addTableColumns();
		
		jc.buscarTodosJogos();
		painelPrincipal.setCenter(table);
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(event.getTarget()==btnPesquisar) {
			if(txtData.getText()=="") {
				jc.buscarTodosJogos();
			}else {
				String dia[] = txtData.getText().split("-");
				LocalDate ld =LocalDate.of(Integer.parseInt(dia[0]),Integer.parseInt(dia[1]),Integer.parseInt(dia[2]));
				System.out.println(ld);
				jc.buscarJogosNaData(ld);
			}
			
		}else if(event.getTarget()==btnGerarRodadas) {
			jc.getLista().clear();
			jc.gerarRodadas();
			jc.buscarTodosJogos();
			painelPrincipal.setCenter(table);
		}
	}
	
	private void addTableColumns() {
		TableColumn<Jogo, Time> columnTimeA = new TableColumn<>("Time A");
		columnTimeA.setCellValueFactory(
				new PropertyValueFactory<Jogo, Time>("timeA"));
		TableColumn<Jogo, Time> columnTimeB = new TableColumn<>("Time B");
		columnTimeB.setCellValueFactory(
				new PropertyValueFactory<Jogo, Time>("timeB"));
		TableColumn<Jogo, String> columnGolsA = new TableColumn<>("Gols A");
		columnGolsA.setCellValueFactory(
				new PropertyValueFactory<Jogo, String>("golsTimeA"));
		TableColumn<Jogo, String> columnGolsB = new TableColumn<>("Gols B");
		columnGolsB.setCellValueFactory(
				new PropertyValueFactory<Jogo, String>("golsTimeB"));
		TableColumn<Jogo, Date> columnData = new TableColumn<>("Data");
		columnData.setCellValueFactory(
				new PropertyValueFactory<Jogo, Date>("data"));
		
		table.getColumns().addAll(columnTimeA,columnTimeB,columnGolsA,columnGolsB,columnData);
		table.setItems(jc.getLista());
	}

	@Override
	public Pane generateForm() {
		// TODO Auto-generated method stub
		return painelPrincipal;
	}

}
