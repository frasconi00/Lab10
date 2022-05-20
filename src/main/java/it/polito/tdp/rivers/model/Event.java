package it.polito.tdp.rivers.model;
//flusso in ingresso

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	
	private LocalDate day;
	private double f_in;
	
	public Event(LocalDate day, double f_in) {
		this.day = day;
		this.f_in = f_in;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public double getF_in() {
		return f_in;
	}

	public void setF_in(double f_in) {
		this.f_in = f_in;
	}

	@Override
	public String toString() {
		return "Event [day=" + day + ", f_in=" + f_in + "]";
	}
	
	@Override
	public int compareTo(Event other) {
		return this.day.compareTo(other.day);
	}

}
