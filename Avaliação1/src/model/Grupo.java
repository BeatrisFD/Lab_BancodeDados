package model;

import java.util.List;

public class Grupo {
	private char grupo;
	private List<Time> times;
	
	public Grupo(char grupo, List<Time> times) {
		this.grupo = grupo;
		this.times = times;
	}

	public char getGrupo() {
		return grupo;
	}

	public void setGrupo(char grupo) {
		this.grupo = grupo;
	}

	public List<Time> getTimes() {
		return times;
	}

	public void setTimes(List<Time> times) {
		this.times = times;
	}
	
	public void addTimes(Time time){
		this.times.add(time);
	}
}
