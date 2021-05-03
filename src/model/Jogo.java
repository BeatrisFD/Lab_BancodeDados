package model;

import java.time.LocalDate;
import java.util.Date;

public class Jogo {
	private Time timeA;
	private Time timeB;
	private int golsTimeA;
	private int golsTimeB;
	private LocalDate data;
	
	public Jogo(Time codTimeA, Time codTimeB, int golsTimeA, int golsTimeB, LocalDate data) {
		super();
		this.timeA = codTimeA;
		this.timeB = codTimeB;
		this.golsTimeA = golsTimeA;
		this.golsTimeB = golsTimeB;
		this.data = data;
	}
	
	

	public Jogo(Time time, Time time2, LocalDate localDate) {
		super();
		this.timeA = time;
		this.timeB = time2;
		this.data = localDate;
	}



	public Time getTimeA() {
		return timeA;
	}

	public void setTimeA(Time timeA) {
		this.timeA = timeA;
	}

	public Time getTimeB() {
		return timeB;
	}
	public void setTimeB(Time timeB) {
		this.timeB = timeB;
	}

	public int getGolsTimeA() {
		return golsTimeA;
	}
	public void setGolsTimeA(int golsTimeA) {
		this.golsTimeA = golsTimeA;
	}
	public int getGolsTimeB() {
		return golsTimeB;
	}
	public void setGolsTimeB(int golsTimeB) {
		this.golsTimeB = golsTimeB;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	
}
