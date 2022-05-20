package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Simulatore {
	
	//Coda degli eventi
	private PriorityQueue<Event> queue;
	
	//Parametri di simulazione
	private double k;
	
	//Output della simulazione
	private int nGiorniSbagliati;
	private double C_med;
	
	//Stato del mondo simulato
	private double Q; //metri cubi!!
	private double C; // metri cubi!!
	private double f_med;
	private double f_out;
	private double f_out_min;
	private double f_in;
	private List<Flow> misurazioni;
	List<Double> sommaC;
	int contatoreLista;
	
	public Simulatore(double f_med, List<Flow> misurazioni) {
		this.f_med = f_med;
		this.misurazioni = misurazioni;
	}
	
	public void init(double input) {
		this.k = input;
		
		this.Q = this.k*30*f_med*86400; // metri cubi
		this.C = (this.Q)/(2.0); // metri cubi
		this.f_out_min = 0.8*this.f_med;  //metri cubi al secondo
		this.sommaC = new ArrayList<Double>();
		this.sommaC.add(this.C);
		this.contatoreLista=0;
		
		this.queue = new PriorityQueue<Event>();
		
		this.queue.add(new Event(this.misurazioni.get(0).getDay(),this.misurazioni.get(0).getFlow()));
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
//		System.out.println("\nCapienza: "+this.Q+" metri cubi");
	}

	private void processEvent(Event e) {
		
		this.f_in = e.getF_in();
		
		if(this.f_in*86400+this.C<=Q) {
			
			this.C = this.C + this.f_in*86400;
			this.sommaC.add(C);
			
		} else {
			
			this.C = this.Q;
			this.sommaC.add(this.C);
			
		}
		
		//Ora calcoliamo f_out
		int numeroRandom = (int) (Math.random()*100) +1; //Ora abbiamo un numero da 1 a 100
//		System.out.println("numero estratto: "+numeroRandom);
		
		if(numeroRandom<=95) {
			this.f_out = this.f_out_min;
//			System.out.println("Caso<=95: C vale: "+this.C+" e f_out vale: "+this.f_out);
		} else {
			this.f_out = this.f_out_min*10;
//			System.out.println("Caso>95: C vale: "+this.C+" e f_out vale: "+this.f_out);
		}
		
		if(this.f_out*86400<=C) {
			this.C = this.C - this.f_out*86400;
			this.sommaC.add(this.C);
			
			contatoreLista++;
			if(this.misurazioni.size()!=contatoreLista) {
				this.queue.add(new Event(this.misurazioni.get(contatoreLista).getDay(), this.misurazioni.get(contatoreLista).getFlow()));
			}
			
		} else {
			
			this.C = 0;
			this.sommaC.add(this.C);
			this.nGiorniSbagliati++;
			
			contatoreLista++;
			if(this.misurazioni.size()!=contatoreLista) {
				this.queue.add(new Event(this.misurazioni.get(contatoreLista).getDay(), this.misurazioni.get(contatoreLista).getFlow()));
			}
		}
		
	}
	
	public int getnGiorniSbagliati() {
		return this.nGiorniSbagliati;
	}
	
	public double getOccupazioneMedia() {
		double somma = 0;
		for(double i : this.sommaC) {
			somma += i;
		}
		
		this.C_med = somma/this.sommaC.size();
		
		return C_med;
	}

}
