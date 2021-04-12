package View;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import controller.jogosController;
import dao.TimesDao;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent>{

	private MenuBar barraMenu  = new MenuBar();
	
	private Menu mnuGrupos = new Menu("Grupos");
	private Menu mnuRodadas = new Menu("Rodadas");
	private MenuItem mnuGrupo = new Menu("Grupos");
	private MenuItem mnuRodada = new Menu("Rodadas");
	
	private BorderPane panPrincipal = new BorderPane();
	
	private Map<MenuItem, BoundaryContent> telas = new HashMap<>();

	@Override
	public void handle(ActionEvent event) {
		BoundaryContent tela = telas.get(event.getTarget());
		if (tela != null) { 
			panPrincipal.setCenter(tela.generateForm());
		}		
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		panPrincipal.setTop(barraMenu);
		gerarMenus();
		Scene scn = new Scene(panPrincipal, 800, 600);
		
		stage.setScene(scn);
		stage.setTitle("Menu Principal");
		stage.show();
	}
	
	private void gerarMenus() {
		barraMenu.getMenus().addAll(mnuGrupos,mnuRodadas);
		mnuGrupos.getItems().addAll(mnuGrupo);
		mnuRodadas.getItems().add(mnuRodada);
		
		telas.put(mnuGrupo, new GruposBondary());
		telas.put(mnuRodada, new RodadasBondary());
		
		Set<MenuItem> keys = telas.keySet();
		for(MenuItem menu : keys) { 
			menu.addEventHandler(ActionEvent.ANY, this);
		}
	}
	
	public static void main(String[] args) {
		Main.launch(args);
		
	}
}
