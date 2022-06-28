package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	GenesDao dao;
	Graph<String, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao= new GenesDao();
	}
	
	public void creaGrafo() {
		this.grafo= new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//VERTICI
		Graphs.addAllVertices(this.grafo, this.dao.getLocalization());
		System.out.println("#vertici: "+this.grafo.vertexSet().size());
		//ARCHI
		for(Adiacenza a: dao.getArchi()) {
			Graphs.addEdge(this.grafo,  a.getV1(), a.getV2(), a.getPeso());
		}
		System.out.println("#archi: "+this.grafo.edgeSet().size());
		
	}

	public Integer getNVertici() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet().size();
	}

	public Integer getNArchi() {
		// TODO Auto-generated method stub
		return this.grafo.edgeSet().size();
	}

	public List<String> getLocalizzazioni() {
		// TODO Auto-generated method stub
		return dao.getLocalization();
	}
	
	public List<Adiacenza> getAdiacenze(String localizzazione){
		Set<DefaultWeightedEdge> setArchi=this.grafo.incomingEdgesOf(localizzazione);
		List<Adiacenza> adiacenze= new ArrayList<>();
		for(DefaultWeightedEdge e: setArchi) {
			adiacenze.add(new Adiacenza(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), (int)this.grafo.getEdgeWeight(e)));
		}
		return adiacenze;
		
	}

}