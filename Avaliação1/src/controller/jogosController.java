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
			System.out.println(grupo.getData());
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
		RemoverJogos();
		GruposDao gdao = new GruposDao();
		if(!gdao.buscarGrupos().isEmpty()) {
			LocalDate dias [] = {LocalDate.of(2019,01,19),LocalDate.of(2019, 01,23),LocalDate.of(2019,01,26),LocalDate.of(2019,01,30),
					LocalDate.of(2019,02,02),LocalDate.of(2019,02,06),LocalDate.of(2019,02,9),LocalDate.of(2019,02,13),LocalDate.of(2019,02,16),
					LocalDate.of(2019,02,20), LocalDate.of(2019,02,23),LocalDate.of(2019,02,27)};
			LocalDate jogos [][]= new LocalDate[16][16];
			int grupos [][] = new int [16][2];
			List<Grupo> lg = gdao.buscarGrupos();
			int aux=0,aux1=0;
			for (Grupo grupo : lg) {
				for (Time time : grupo.getTimes()) {
					System.out.println(time.getCodTime());
					grupos[aux1][0]=time.getCodTime();
					grupos[aux1][1]=aux;
					aux1++;
				}
				aux++;
			}
			List<LocalDate> ldate= new LinkedList<LocalDate>();
			for (LocalDate date : dias) {
				ldate.add(date);
			}

			for (int i = 0; i < grupos.length; i++) {
				List<LocalDate>ldateAux= new LinkedList<LocalDate>();
				ldateAux.addAll(ldate);
				for (int j = i+1; j < grupos.length; j++) {
					Boolean diajogado = false;
					if(i!=j && (i/4 != j/4)) {
						if(jogos[i][j]==null) {
							int rng = (int)(Math.random()*ldateAux.size());
							for (int k = 0; k < grupos.length; k++) {
								if(jogos[i][k]!=null && jogos[j][k]!=null && jogos[k][i]!=null && jogos[k][j]!=null ) {
									if(jogos[i][k].equals(ldateAux.get(rng)) || jogos[j][k].equals(ldateAux.get(rng)) ||(jogos[k][j].equals(ldateAux.get(rng)) || jogos[k][j].equals(ldateAux.get(rng))) ) {
										ldateAux.remove(rng);
										k=grupos.length;
										diajogado=true;
									}
								}
							}
							if(!diajogado) {
								jogos[i][j]=ldateAux.get(rng);
								InserirJogo(new Jogo(new Time(grupos[i][0]),new Time(grupos[j][0]),0,0,ldateAux.get(rng)));
								System.out.println(i+"-"+grupos[i][0]+" X "+j+" - "+grupos[j][0]+" - "+ldateAux.get(rng));
								ldateAux.remove(rng);
							}
						}
					}
				}
			}
			
		}
		
		
	}
	
	public void InserirJogo(Jogo jogo) {
		IJogosDao jdao = new JogosDao();
		jdao.adicionarJogo(jogo);
	}
	private void RemoverJogos() {
		IJogosDao jdao = new JogosDao();
		jdao.limparJogos();
	}
}
