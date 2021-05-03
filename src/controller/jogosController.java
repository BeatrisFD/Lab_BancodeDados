package controller;

import java.time.LocalDate;

import java.util.LinkedList;
import java.util.List;

import dao.GruposDao;
import dao.IJogosDao;
import dao.ITimesDao;
import dao.JogosDao;
import dao.TimesDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Grupo;
import model.Jogo;
import model.JogoQuartas;
import model.Time;

public class jogosController {
	private ObservableList<Jogo> lista = 
			FXCollections.observableArrayList();
	
	public ObservableList<Jogo> getLista() {
		return lista;
	}
	
	public void buscarTodosJogos() {
		IJogosDao jDao = new JogosDao();
		List<Jogo> listaTemp = jDao.buscarTodosJogos();
		lista.clear();
		for (Jogo grupo : listaTemp) {
			lista.add(grupo);
		}
	}
	
	public void buscarJogosNaData(LocalDate dia) {
		IJogosDao jDao = new JogosDao();
		List<Jogo> listaTemp = jDao.buscarJogosPorData(dia);
		lista.clear();
		for (Jogo grupo : listaTemp) {
			lista.add(grupo);
		}
	}
	
	public void gerarRodadas() {
			GruposDao gdao = new GruposDao();
			if (!gdao.buscarGrupos().isEmpty()) {
				LocalDate dias[] = { LocalDate.of(2019, 01, 19), LocalDate.of(2019, 01, 23), LocalDate.of(2019, 01, 26),
						LocalDate.of(2019, 01, 30), LocalDate.of(2019, 02, 02), LocalDate.of(2019, 02, 06),
						LocalDate.of(2019, 02, 9), LocalDate.of(2019, 02, 13), LocalDate.of(2019, 02, 16),
						LocalDate.of(2019, 02, 20), LocalDate.of(2019, 02, 23), LocalDate.of(2019, 02, 27) };
				LocalDate jogos[][] = new LocalDate[16][16];
				int grupos[][] = new int[16][2];
				List<Grupo> lg = gdao.buscarGrupos();
				int aux = 0, aux1 = 0;
				for (Grupo grupo : lg) {
					for (Time time : grupo.getTimes()) {
						grupos[aux1][0] = time.getCodTime();
						grupos[aux1][1] = aux;
						aux1++;
					}
					aux++;
				}
				//RODADA 1
				jogos[0][4]=dias[0];jogos[3][8]=dias[0];jogos[1][9]=dias[0];jogos[6][11]=dias[0];jogos[5][12]=dias[0];
				jogos[10][13]=dias[0];jogos[2][14]=dias[0];jogos[7][15]=dias[0];
				//RODADA 2
				jogos[1][7]=dias[1];jogos[6][8]=dias[1];jogos[2][9]=dias[1];jogos[0][10]=dias[1];jogos[3][12]=dias[1];
				jogos[5][13]=dias[1];jogos[11][14]=dias[1];jogos[4][15]=dias[1];
				//RODADA 3
				jogos[3][4]=dias[2];jogos[2][8]=dias[2];jogos[5][10]=dias[2];jogos[0][11]=dias[2];jogos[7][12]=dias[2];
				jogos[1][13]=dias[2];jogos[6][14]=dias[2];jogos[9][15]=dias[2];
				//RODADA 4
				jogos[3][7]=dias[3];jogos[0][8]=dias[3];jogos[2][10]=dias[3];jogos[1][11]=dias[3];jogos[9][12]=dias[3];
				jogos[6][13]=dias[3];jogos[4][14]=dias[3];jogos[5][15]=dias[3];
				//RODADA 5
				jogos[4][8]=dias[4];jogos[0][9]=dias[4];jogos[6][10]=dias[4];jogos[2][11]=dias[4];jogos[1][12]=dias[4];
				jogos[7][13]=dias[4];jogos[5][14]=dias[4];jogos[3][15]=dias[4];
				//RODADA 6
				jogos[5][8]=dias[5];jogos[7][9]=dias[5];jogos[1][10]=dias[5];jogos[4][11]=dias[5];jogos[0][12]=dias[5];
				jogos[2][13]=dias[5];jogos[3][14]=dias[5];jogos[6][15]=dias[5];
				//RODADA 7
				jogos[2][5]=dias[6];jogos[1][6]=dias[6];jogos[4][9]=dias[6];jogos[3][11]=dias[6];jogos[10][12]=dias[6];
				jogos[8][13]=dias[6];jogos[7][14]=dias[6];jogos[0][15]=dias[6];
				//RODADA 8
				jogos[1][4]=dias[7];jogos[3][6]=dias[7];jogos[7][10]=dias[7];jogos[5][11]=dias[7];jogos[2][12]=dias[7];
				jogos[0][13]=dias[7];jogos[9][14]=dias[7];jogos[8][15]=dias[7];
				//RODADA 9
				jogos[0][5]=dias[8];jogos[7][8]=dias[8];jogos[6][9]=dias[8];jogos[3][10]=dias[8];
				jogos[4][12]=dias[8];jogos[11][13]=dias[8];jogos[1][14]=dias[8];jogos[2][15]=dias[8];
				//RODADA 10
				jogos[3][5]=dias[9];jogos[2][7]=dias[9];jogos[1][8]=dias[9];jogos[4][10]=dias[9];jogos[6][12]=dias[9];
				jogos[9][13]=dias[9];jogos[0][14]=dias[9];jogos[11][15]=dias[9];
				//RODADA 11
				jogos[1][5]=dias[10];jogos[2][6]=dias[10];jogos[0][7]=dias[10];jogos[3][9]=dias[10];jogos[11][12]=dias[10];
				jogos[4][13]=dias[10];jogos[8][14]=dias[10];jogos[10][15]=dias[10];
				//RODADA 12
				jogos[2][4]=dias[11];jogos[0][6]=dias[11];jogos[5][9]=dias[11];jogos[7][11]=dias[11];jogos[8][12]=dias[11];
				jogos[3][13]=dias[11];jogos[10][14]=dias[11];jogos[1][15]=dias[11];
				
				for (int i = 0; i < grupos.length; i++) {
					for (int j = i+1; j < grupos.length; j++) {
						if(jogos[i][j]!=null) {
							InserirJogo(new Jogo(new Time(grupos[i][0]),new Time(grupos[j][0]),jogos[i][j]));
							System.out.println(i+"-"+grupos[i][0]+" X "+j+" - "+grupos[j][0]+" - "+jogos[i][j]);
						}
					}
				}
			}
			buscarTodosJogos();
	}
	
	public void InserirJogo(Jogo jogo) {
		IJogosDao jdao = new JogosDao();
		jdao.adicionarJogo(jogo);
	}
	private void RemoverJogos() {
		IJogosDao jdao = new JogosDao();
		jdao.limparJogos();
	}
	
	public List<JogoQuartas> buscarQuartas(){
		IJogosDao jdao = new JogosDao();
		return jdao.buscarQuartas();
	}
	
	public void atualizaJogo(Jogo jogo) {
		IJogosDao jdao = new JogosDao();
		jdao.atualizarJogo(jogo);
	}
}
