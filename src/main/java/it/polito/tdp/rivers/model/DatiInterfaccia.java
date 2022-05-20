package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class DatiInterfaccia {
	
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private int nMisure;
	private double fMed;
	
	public DatiInterfaccia(LocalDate dataInizio, LocalDate dataFine, int nMisure, double fMed) {
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.nMisure = nMisure;
		this.fMed = fMed;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public LocalDate getDataFine() {
		return dataFine;
	}

	public int getnMisure() {
		return nMisure;
	}

	public double getfMed() {
		return fMed;
	}

}
