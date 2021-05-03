package View;

import java.time.LocalDate;
import java.util.Date;

import controller.jogosController;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Jogo;
import model.Time;

public class AtualizaJogoBoundary implements BoundaryContent,EventHandler<ActionEvent> {
	private BorderPane painelPrincipal = new BorderPane();
	private FlowPane painelPesquisa = new FlowPane();
	private Label lblData = new Label("Pesquisar por Data : ");
	private TextField txtData = new TextField();
	private Button btnPesquisar = new Button("Pesquisar");
	private TableView table = new TableView<>();
	
	jogosController jc = new jogosController();
	
	private Label lblJogo = new Label("Jogo");
	private Label lblTimeA = new Label("");
	private Label lblTimeB = new Label("");
	private TextField txtGolsTimeA = new TextField();
	private TextField txtGolsTimeB = new TextField();
	
	private Button btnAtt = new Button("Atualizar");
	
	private GridPane painelCampos = new GridPane();
	
	private Jogo jogoSelecionado;
	
	public AtualizaJogoBoundary() {
		painelPrincipal.setStyle("-fx-padding: 10px");
		painelPesquisa.getChildren().addAll(lblData,txtData,btnPesquisar);
		painelPesquisa.setStyle("-fx-padding: 10px");
		painelPrincipal.setTop(painelPesquisa);
		
		
		painelCampos.add(lblJogo, 0, 0);
		painelCampos.add(lblTimeA, 0, 1);
		painelCampos.add(txtGolsTimeA, 1, 1);
		painelCampos.add(new Label(" X "), 2, 1);
		painelCampos.add(txtGolsTimeB, 3, 1);
		painelCampos.add(lblTimeB, 4, 1);
		painelCampos.add(btnAtt, 0, 2);
		
		btnPesquisar.addEventHandler(ActionEvent.ANY, this);
		btnAtt.addEventHandler(ActionEvent.ANY, this);
		
		painelPrincipal.setCenter(painelCampos);
		
		addTableColumns();
		
		painelPrincipal.setBottom(table);
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
		}
		if(event.getTarget()==btnAtt) {
			Jogo jogo = bondaryParaEntidade();
			if(jogo!=null) {
				jc.atualizaJogo(jogo);
				String dia[] = txtData.getText().split("-");
				LocalDate ld =LocalDate.of(Integer.parseInt(dia[0]),Integer.parseInt(dia[1]),Integer.parseInt(dia[2]));
				jc.buscarJogosNaData(ld);
				jogoSelecionado=null;
				lblTimeA.setText("");
				lblTimeB.setText("");
				txtGolsTimeA.clear();
				txtGolsTimeB.clear();
			}
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
			
			table.getSelectionModel().selectedItemProperty().addListener(
						new ChangeListener<Jogo>() {
							public void changed(javafx.beans.value.ObservableValue<? extends Jogo> observable, Jogo oldValue, Jogo newValue) {
								entidadeParaBondary(newValue);
							}
						}
					);
			
			
			table.getColumns().addAll(columnTimeA,columnTimeB,columnGolsA,columnGolsB,columnData);
			table.setItems(jc.getLista());
		}
		
		private void entidadeParaBondary(Jogo jogo) {
			if(jogo!=null) {
				jogoSelecionado=jogo;
				lblTimeA.setText(jogo.getTimeA().getNomeTime());
				lblTimeB.setText(jogo.getTimeB().getNomeTime());
				txtGolsTimeA.setText(jogo.getGolsTimeA()+"");
				txtGolsTimeB.setText(jogo.getGolsTimeB()+"");
			}
		}
		
		private Jogo bondaryParaEntidade() {
			if(!txtGolsTimeA.getText().isEmpty() || !txtGolsTimeB.getText().isEmpty()) {
				Jogo j = new Jogo(jogoSelecionado.getTimeA(), jogoSelecionado.getTimeB(), 
						Integer.parseInt(txtGolsTimeA.getText()), Integer.parseInt(txtGolsTimeB.getText()), jogoSelecionado.getData());
				return j;
			}
			return null;
		}

		
	@Override
	public Pane generateForm() {
		return painelPrincipal;
	}

}
