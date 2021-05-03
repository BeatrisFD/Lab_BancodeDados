package controller;

import java.util.List;

import dao.IItemTabelaClassificacaoDao;
import dao.ItemTabelaClassificacaoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ItemTabelaClassificacao;

public class TabelaController {
	private ObservableList<ItemTabelaClassificacao> lista = 
			FXCollections.observableArrayList();
	
	public ObservableList<ItemTabelaClassificacao> getLista(){
		return lista;
	}
	
	public List<ItemTabelaClassificacao> buscarTabelaPorGrupo(char grupo) {
		IItemTabelaClassificacaoDao itemDao = new ItemTabelaClassificacaoDao();
		List<ItemTabelaClassificacao> l = itemDao.buscarTabelaClassificacaoPorGrupo(grupo);
		return l;
	}
	
	public List<ItemTabelaClassificacao> buscarTabelaGeral() {
		IItemTabelaClassificacaoDao itemDao = new ItemTabelaClassificacaoDao();
		List<ItemTabelaClassificacao> l = itemDao.buscarTabelaClassificacaoGeral();
		return l;
	}
}
