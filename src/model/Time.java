package model;

public class Time {
	private int codTime;
	private String nomeTime;
	private String cidade;
	private String estadio;
	
	public Time(int codTime, String nomeTime, String cidade, String estadio) {
		this.codTime = codTime;
		this.nomeTime = nomeTime;
		this.cidade = cidade;
		this.estadio = estadio;
	}
	public Time(int codTime) {
		this.codTime = codTime;
	}

	public int getCodTime() {
		return codTime;
	}

	public void setCodTime(int codTime) {
		this.codTime = codTime;
	}

	public String getNomeTime() {
		return nomeTime;
	}

	public void setNomeTime(String nomeTime) {
		this.nomeTime = nomeTime;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstadio() {
		return estadio;
	}

	public void setEstadio(String estadio) {
		this.estadio = estadio;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNomeTime();
	}
	
}
