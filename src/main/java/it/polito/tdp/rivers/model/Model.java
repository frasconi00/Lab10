package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private Map<Integer,River> idMap;
	
	private int giorniInsoddisfatti;
	private double occupazioneMedia;
	
	public Model() {
		this.dao = new RiversDAO();
		this.idMap = new HashMap<Integer, River>();
		this.dao.loadAllRivers(idMap);
	}
	
	public DatiInterfaccia getDati(River r) {
		return this.dao.getDati(r.getId());
	}
	
	public List<River> getRivers() {
		List<River> lista = new ArrayList<River>(this.idMap.values());
		
		return lista;
	}
	
	public int simula( int idRiver, double f_med, double input) {
		
		this.giorniInsoddisfatti=-1;
		this.occupazioneMedia=-1;
		
		List<Flow> misurazioni = this.dao.getMisurazioni(idRiver, idMap);
		Simulatore sim = new Simulatore(f_med,misurazioni);
		
		sim.init(input);
		sim.run();
		
//		System.out.println("\nGiorni insoddisfatti: "+sim.getnGiorniSbagliati());
//		System.out.println("\nOccupazione media in metri cubi: "+sim.getOccupazioneMedia());
		
		this.giorniInsoddisfatti = sim.getnGiorniSbagliati();
		this.occupazioneMedia = sim.getOccupazioneMedia();
				
		return 0;
	}

	public int getGiorniInsoddisfatti() {
		return giorniInsoddisfatti;
	}

	public double getOccupazioneMedia() {
		return occupazioneMedia;
	}

}
