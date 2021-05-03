package View;

import java.util.List;

import controller.TabelaController;
import controller.gruposController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.ItemTabelaClassificacao;
import model.Time;

public class TabelaGruposBondary implements BoundaryContent,EventHandler<ActionEvent>{
	private BorderPane painelPrincipal = new BorderPane();
	private Pane painelGrupos = new VBox();
	
	private char[] grupos = {'A','B','C','D'};
	
	private TabelaController tController = new TabelaController();
	private gruposController gc = new gruposController();
	
	private Button btnAtt = new Button("Atualizar");
	
	public TabelaGruposBondary() {
		painelPrincipal.setStyle("-fx-padding: 10px");
		painelGrupos=gerarTabelaGrupos();
		painelPrincipal.setCenter(painelGrupos);
		painelPrincipal.setBottom(btnAtt);
		
		btnAtt.addEventHandler(ActionEvent.ANY, this);
	}
	
	private Pane gerarTabelaGrupos() {
		Pane painelAux = new VBox();
		for (char c : grupos) {
			Label lblGrupo = new Label("Grupo "+c);
			painelAux.getChildren().addAll(lblGrupo,gerarTableView(tController.buscarTabelaPorGrupo(c)));
		}
		return painelAux;
	}
	
	private Node gerarTableView(List<ItemTabelaClassificacao> lista) {
		TableView table = new TableView();
		 table.setStyle("-fx-padding: 10px");
		 TableColumn<ItemTabelaClassificacao, String> columnNomeTime = new TableColumn<>("Time");
		 columnNomeTime.setCellValueFactory(
					new PropertyValueFactory<ItemTabelaClassificacao, String>("time"));
		 TableColumn<ItemTabelaClassificacao, Integer> columnNumJogos = new TableColumn<>("Jogos Disputados");
		 columnNumJogos.setCellValueFactory(
					new PropertyValueFactory<ItemTabelaClassificacao, Integer>("numJogosDisputados"));
		 TableColumn<ItemTabelaClassificacao, Integer> columnNumVitorias = new TableColumn<>("Vitorias");
		 columnNumVitorias.setCellValueFactory(
					new PropertyValueFactory<ItemTabelaClassificacao, Integer>("numVitorias"));
		 TableColumn<ItemTabelaClassificacao, Integer> columnNumEmpates = new TableColumn<>("Empates");
		 columnNumEmpates.setCellValueFactory(
					new PropertyValueFactory<ItemTabelaClassificacao, Integer>("numEmpates"));
		 TableColumn<ItemTabelaClassificacao, Integer> columnNumDerrotas = new TableColumn<>("Derrotas");
		 columnNumDerrotas.setCellValueFactory(
					new PropertyValueFactory<ItemTabelaClassificacao, Integer>("numDerrotas"));
		 TableColumn<ItemTabelaClassificacao, Integer> columnGolsMarcados = new TableColumn<>("Gols Marcados");
		 columnGolsMarcados.setCellValueFactory(
					new PropertyValueFactory<ItemTabelaClassificacao, Integer>("golsMarcados"));
		 TableColumn<ItemTabelaClassificacao, Integer> columnGolsSofridos = new TableColumn<>("Gols Sofridos");
		 columnGolsSofridos.setCellValueFactory(
					new PropertyValueFactory<ItemTabelaClassificacao, Integer>("golsSofridos"));
		 TableColumn<ItemTabelaClassificacao, Integer> columnSaldoGols = new TableColumn<>("Saldo de Gols");
		 columnSaldoGols.setCellValueFactory(
					new PropertyValueFactory<ItemTabelaClassificacao, Integer>("saldoGols"));
		 TableColumn<ItemTabelaClassificacao, Integer> columnPontos = new TableColumn<>("Pontos");
		 columnPontos.setCellValueFactory(
					new PropertyValueFactory<ItemTabelaClassificacao, Integer>("pontos"));
		 
		 table.getColumns().addAll(columnNomeTime,columnNumJogos,columnNumVitorias,columnNumEmpates,columnNumDerrotas,
				 columnGolsMarcados,columnGolsSofridos,columnSaldoGols,columnPontos);
		 
		 ObservableList<ItemTabelaClassificacao> ol = FXCollections.observableArrayList();
		 
		 for (ItemTabelaClassificacao itemTabelaClassificacao : lista) {
			ol.add(itemTabelaClassificacao);
		}
		 table.setItems(ol);
			
		 List<String> rebaixados = gc.buscarRebaixados();
		 
		 columnNomeTime.setCellFactory(column -> {
			    return new TableCell<ItemTabelaClassificacao, String>() {
			        @Override
			        protected void updateItem(String item, boolean empty) {
			            super.updateItem(item, empty); //This is mandatory

			            if (item == null || empty) { //If the cell is empty
			                setText(null);
			                setStyle("");
			            } else { //If the cell is not empty

			                setText(item); //Put the String data in the cell

			               
			                ItemTabelaClassificacao auxTime= getTableView().getItems().get(getIndex());

			                
			                if (rebaixados.contains(auxTime.getTime())) {
			                    //setTextFill(Color.RED); //The text in red
			                    setStyle("-fx-background-color: red"); //The background of the cell in yellow
			                } 
			            }
			        }
			    };
			});
		 
			return table;
		 
	}

	public void attTabela() {
		painelGrupos=gerarTabelaGrupos();
		painelPrincipal.setCenter(painelGrupos);
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(event.getTarget()==btnAtt) {
			this.attTabela();
		}
		
	}

	@Override
	public Pane generateForm() {
		return painelPrincipal;
	}

}
