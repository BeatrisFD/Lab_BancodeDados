package controller;


import java.util.List;

import dao.GruposDao;
import dao.IGruposDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Grupo;

public class gruposController {
	private ObservableList<Grupo> lista = 
			FXCollections.observableArrayList();
	
	public ObservableList<Grupo> getLista(){
		return lista;
	}
	
	public void buscarGrupos() {
		IGruposDao gdao = new GruposDao();
		lista.clear();
		List<Grupo> listaTemp = gdao.buscarGrupos();
		for (Grupo grupo : listaTemp) {
			lista.add(grupo);
		}
	}
	
	public void gerarGrupos() {
		IGruposDao gdao = new GruposDao();
		lista.clear();
		gdao.deletarGrupos();
		gdao.gerarGrupos();
	}
}
