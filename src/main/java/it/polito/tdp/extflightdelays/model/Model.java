package it.polito.tdp.extflightdelays.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph<Airport,DefaultEdge> grafo;
	
	public void creaGrafo(int distance) {
		
		this.grafo = new SimpleWeightedGraph<Airport,DefaultEdge>(DefaultEdge.class);
		
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		
		List<Airport> airports = dao.loadAllAirports();
		Map<Integer,Airport> airportsMap = new HashMap<Integer,Airport>();
		
		for(Airport a : airports) {
			airportsMap.put(a.getId(), a);
		}
		
		Graphs.addAllVertices(this.grafo, airports);
		
		List<Flight> flights = dao.getAllFlights();
		
		for(Flight f : flights) {
			if(f.getDistance()>distance) {
				
				this.grafo.addEdge(airportsMap.get(f.getOriginAirportId()), airportsMap.get(f.getDestinationAirportId()));
		
			}
			
		}
		
	}
	
	public int getVertici() {
		
		return this.grafo.vertexSet().size();
	}
	
	public int getArchi() {
		return this.grafo.edgeSet().size();
	}

}
