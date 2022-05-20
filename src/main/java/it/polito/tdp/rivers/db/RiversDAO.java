package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.DatiInterfaccia;
import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public void loadAllRivers(Map<Integer,River> idMap) {
		
		final String sql = "SELECT id, name FROM river";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(!idMap.containsKey(res.getInt("id"))) {
					idMap.put(res.getInt("id"), new River(res.getInt("id"), res.getString("name")));
				}
			}

			conn.close();
			return;
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL Error");
		}

	}
	
	public DatiInterfaccia getDati(int id) {
		String sql="SELECT MIN(DAY) AS inizio, MAX(DAY) AS fine, COUNT(*) AS nMisure, AVG(flow) AS fMed "
				+ "FROM flow "
				+ "WHERE river=?";
		
		DatiInterfaccia di = null;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				di = new DatiInterfaccia(res.getDate("inizio").toLocalDate(),
						res.getDate("fine").toLocalDate(),
						res.getInt("nMisure"),
						res.getDouble("fMed")); 
			}

			conn.close();
			return di;
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL Error");
		}
		
	}
	
	public List<Flow> getMisurazioni(int id, Map<Integer,River> idMap) {
		String sql="SELECT * "
				+ "FROM flow "
				+ "WHERE river=?";
		
		List<Flow> lista = new ArrayList<Flow>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				 lista.add(new Flow(res.getDate("day").toLocalDate(),
						 res.getDouble("flow"),
						 idMap.get(res.getInt("river")) ) );
			}

			conn.close();
			return lista;
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL Error");
		}
	}
}
