package cpcc;

import java.util.Vector;

import robots.*;
import maps.*;

public class Sommet {

	private CalculChemin cc;
	private Case position;
	private double poids;
	private Vector tabVoisins;
	private boolean estMarque;
	private double distanceSource;
	private Sommet voisinVersSource;

	public Sommet(CalculChemin pCc, Case pCase, double pPoids) {
		this.cc = pCc;
		this.position = pCase;
		this.poids = pPoids;
		this.tabVoisins = new Vector();
		this.estMarque = false;
		this.distanceSource = Double.POSITIVE_INFINITY;
	}

	public void ajouterVoisins() {
		for (Direction d : Direction.values()) {
			if (this.cc.getCarte().voisinExiste(this.position, d)) {
				this.tabVoisins.add(this.cc.getSommet(this.position.getLigne() + CalculChemin.getDeltaL(d), this.position.getColonne() + CalculChemin.getDeltaC(d)));
			}
		}
	}

	public Case getCase() {
		return this.position;
	}

	public boolean getEstMarque() {
		return this.estMarque;
	}

	public double getDistanceSource() {
		return this.distanceSource;
	}
	
	public Vector<Sommet> getVoisins() {
		return this.tabVoisins;
	}
	
	public Sommet getVoisin(int i) {
		return (Sommet) this.tabVoisins.get(i);
	}

	public int getNbVoisins() {
		return this.tabVoisins.size();
	}

	public double getPoids() {
		return this.poids;
	}

	public void setDistanceSource(double d) {
		this.distanceSource = d;
	}

	public Sommet getVoisinVersSource() {
		return this.voisinVersSource;
	}

	public void setVoisinVersSource(Sommet v) {
		this.voisinVersSource = v;
	}

	public void setEstMarque(boolean b) {
		this.estMarque = b;
	}
}
