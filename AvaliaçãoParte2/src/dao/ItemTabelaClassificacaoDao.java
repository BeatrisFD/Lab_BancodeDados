package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import model.ItemTabelaClassificacao;

public class ItemTabelaClassificacaoDao implements IItemTabelaClassificacaoDao {

	@Override
	public List<ItemTabelaClassificacao> buscarTabelaClassificacaoPorGrupo(char grupo) {
		List<ItemTabelaClassificacao> lista = new LinkedList<ItemTabelaClassificacao>();
		String sql="SELECT * FROM fn_mostrar_tabela_grupo(?) ORDER BY pontos DESC ,num_vitorias DESC ,gols_marcadados DESC,saldo_gols DESC";
		try {
			PreparedStatement ps= GenericDao.getInstancia().getConnection().prepareStatement(sql);
			ps.setString(1, grupo+"");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ItemTabelaClassificacao item = new ItemTabelaClassificacao(rs.getString("nome_time"), rs.getInt("num_jogos_disputados"),
						rs.getInt("num_vitorias"), rs.getInt("num_empates"), rs.getInt("num_derrotas"), rs.getInt("gols_marcadados"),
						rs.getInt("gols_sofridos"), rs.getInt("saldo_gols"), rs.getInt("pontos"));
				lista.add(item);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;
	}

	@Override
	public List<ItemTabelaClassificacao> buscarTabelaClassificacaoGeral() {
		List<ItemTabelaClassificacao> lista = new LinkedList<ItemTabelaClassificacao>();
		String sql="SELECT * FROM fn_mostrar_tabela_geral() ORDER BY pontos DESC ,num_vitorias DESC ,gols_marcadados DESC,saldo_gols DESC";
		try {
			PreparedStatement ps= GenericDao.getInstancia().getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ItemTabelaClassificacao item = new ItemTabelaClassificacao(rs.getString("nome_time"), rs.getInt("num_jogos_disputados"),
						rs.getInt("num_vitorias"), rs.getInt("num_empates"), rs.getInt("num_derrotas"), rs.getInt("gols_marcadados"),
						rs.getInt("gols_sofridos"), rs.getInt("saldo_gols"), rs.getInt("pontos"));
				lista.add(item);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;
	}

}
