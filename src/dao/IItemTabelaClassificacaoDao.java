package dao;

import java.util.List;

import model.ItemTabelaClassificacao;

public interface IItemTabelaClassificacaoDao {
	public List<ItemTabelaClassificacao> buscarTabelaClassificacaoPorGrupo(char grupo);
	public List<ItemTabelaClassificacao> buscarTabelaClassificacaoGeral();
}
