package dao;

import java.util.List;

import model.Grupo;

public interface IGruposDao {

	public void deletarGrupos();
	public void gerarGrupos();
	public List<Grupo> buscarGrupos();
}
