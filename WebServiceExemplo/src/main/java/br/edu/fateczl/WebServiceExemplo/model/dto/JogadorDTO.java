package br.edu.fateczl.WebServiceExemplo.model.dto;
import br.edu.fateczl.WebServiceExemplo.model.entity.Times;

public class JogadorDTO {

	private int codigo;
	private String nomeJogador;
	private String sexo;
	private float altura;
	private String dt_nasc;
	private int idade;
	private TimesDTO times;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNomeJogador() {
		return nomeJogador;
	}
	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public float getAltura() {
		return altura;
	}
	public void setAltura(float altura) {
		this.altura = altura;
	}
	public String getDt_nasc() {
		return dt_nasc;
	}
	public void setDt_nasc(String dt_nasc) {
		this.dt_nasc = dt_nasc;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}
	public TimesDTO getTimes() {
		return times;
	}
	public void setTimes(TimesDTO times) {
		this.times = times;
	}
	
	@Override
	public String toString() {
		return "JogadorDTO [codigo=" + codigo + ", nomeJogador=" + nomeJogador + ", sexo=" + sexo + ", altura=" + altura
				+ ", dt_nasc=" + dt_nasc + ", idade=" + idade + ", times=" + times + "]";
	}
	
	
}
