package controller;


import java.util.LinkedList;
import java.util.List;

import dao.GruposDao;
import dao.IGruposDao;
import dao.IItemTabelaClassificacaoDao;
import dao.IJogosDao;
import dao.ITimesDao;
import dao.ItemTabelaClassificacaoDao;
import dao.TimesDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Grupo;
import model.ItemTabelaClassificacao;
import model.Time;

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
		gdao.gerarGrupos();
	}
	
	public List<String> buscarRebaixados(){
		IItemTabelaClassificacaoDao iiDao= new ItemTabelaClassificacaoDao();
		List <ItemTabelaClassificacao> l= iiDao.buscarTabelaClassificacaoGeral();
		while(l.size()>4) {
			l.remove(0);
		}
		
		List<String> lista= new LinkedList<String>();
		for (ItemTabelaClassificacao item : l) {
			lista.add(item.getTime());
			//System.out.println(item.getTime());
		}
	
		return lista;
	}
}
