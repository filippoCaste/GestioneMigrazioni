package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	// Coda degli eventi
	private Queue<Event> queue;
	
	// Parametri di simulazione
	
	private int nInizialeMigranti;
	private Country nazioneIniziale;
	
	// Output della simulazione (numero passi simulazione T (dal testo))
	private int nPassi;
	private Map<Country, Integer> persone; // per ogni Country fornisce il numero di persone immigrate sono 'stanziali'
										   // oppure List<CountryAndNumber>
	
	// STATO DEL MONDO simulato
	private Graph<Country, DefaultEdge> grafo;
	// Map persone che caratterizza lo stato in quanto fornisce il numero di persone in un determinato momento
	// Country --> numero persone
	
	public Simulatore(Graph<Country, DefaultEdge> grafo) {
		super();
		this.queue = new PriorityQueue<Event>();
		this.grafo = grafo;
	}
	
	public void inizializza(Country partenza, int migranti) {
		this.nazioneIniziale = partenza;
		this.nInizialeMigranti = migranti;
		
		this.persone = new HashMap<Country, Integer>(); // riempita ogni volta che inizia la simulazione
		for(Country c : grafo.vertexSet()) {
			persone.put(c, 0);
		}
		
		// setting del primo elemento per far partire il run()
		this.queue.add(new Event(1, nazioneIniziale, nInizialeMigranti)); 		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) { // non ci sono altri elementi che fanno concludere la simulazione se non l'esaurimento dei migranti
			Event e = this.queue.poll();
//			System.out.println(e);
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		int stanziali = e.getPersone() / 2;
		int migranti = e.getPersone() - stanziali;
		
		int confinanti = this.grafo.degreeOf(e.getNazione());
		
		int gruppiMigranti = migranti / confinanti;
		stanziali += migranti % confinanti ; 
		
		// Aggiornamento stato del mondo
		this.persone.put(e.getNazione(), persone.get(e.getNazione())+stanziali);
		this.nPassi = e.getTime();
		
		// Eventi futuri
		if(gruppiMigranti!=0) 
			for(Country vicino : Graphs.neighborListOf(grafo, e.getNazione())) {
				this.queue.add(new Event(e.getTime()+1, vicino, gruppiMigranti));
			}
	}

	public int getnPassi() {
		return nPassi;
	}

	public Map<Country, Integer> getPersone() {
		return persone;
	}
}
