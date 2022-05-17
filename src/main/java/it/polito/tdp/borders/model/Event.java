package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{
	
	private int time; // T=0, T=1, ...
	private Country nazione;
	private int persone;
	
	public Event(int time, Country nazione, int persone) {
		super();
		this.time = time;
		this.nazione = nazione;
		this.persone = persone;
	}

	public int getTime() {
		return time;
	}

	public Country getNazione() {
		return nazione;
	}

	public int getPersone() {
		return persone;
	}

	@Override
	public int compareTo(Event o) {
		return this.time - o.time;
	}
	
	public String toString() {
		return time + " " + nazione + " " + persone +"\n";
	}

}
