package dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import model.Jogo;

public interface IJogosDao {
	public List<Jogo> buscarTodosJogos();
	public List<Jogo> buscarJogosPorData(LocalDate dia);
	public void adicionarJogo(Jogo jogo);
	public void limparJogos();
}
