package model;

public class ItemTabelaClassificacao {
	private String time;
	private int numJogosDisputados;
	private int numVitorias;
	private int numEmpates;
	private int numDerrotas;
	private int golsMarcados;
	private int golsSofridos;
	private int saldoGols;
	private int pontos;
	
	public ItemTabelaClassificacao() {
		// TODO Auto-generated constructor stub
	}

	public ItemTabelaClassificacao(String time, int numJogosDisputados, int numVitorias, int numEmpates, int numDerrotas,
			int golsMarcados, int golsSofridos, int saldoGols, int pontos) {
		super();
		this.time = time;
		this.numJogosDisputados = numJogosDisputados;
		this.numVitorias = numVitorias;
		this.numEmpates = numEmpates;
		this.numDerrotas = numDerrotas;
		this.golsMarcados = golsMarcados;
		this.golsSofridos = golsSofridos;
		this.saldoGols = saldoGols;
		this.pontos = pontos;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getNumJogosDisputados() {
		return numJogosDisputados;
	}

	public void setNumJogosDisputados(int numJogosDisputados) {
		this.numJogosDisputados = numJogosDisputados;
	}

	public int getNumVitorias() {
		return numVitorias;
	}

	public void setNumVitorias(int numVitorias) {
		this.numVitorias = numVitorias;
	}

	public int getNumEmpates() {
		return numEmpates;
	}

	public void setNumEmpates(int numEmpates) {
		this.numEmpates = numEmpates;
	}

	public int getNumDerrotas() {
		return numDerrotas;
	}

	public void setNumDerrotas(int numDerrotas) {
		this.numDerrotas = numDerrotas;
	}

	public int getGolsMarcados() {
		return golsMarcados;
	}

	public void setGolsMarcados(int golsMarcados) {
		this.golsMarcados = golsMarcados;
	}

	public int getGolsSofridos() {
		return golsSofridos;
	}

	public void setGolsSofridos(int golsSofridos) {
		this.golsSofridos = golsSofridos;
	}

	public int getSaldoGols() {
		return saldoGols;
	}

	public void setSaldoGols(int saldoGols) {
		this.saldoGols = saldoGols;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	
	
}
