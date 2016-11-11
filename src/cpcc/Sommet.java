package cpcc;

import java.util.Vector;

import robots.*;
import maps.*;

public class Sommet {

	private Case case;
	private double poids;
	private Vector tabVoisins;
	private boolean estMarque;
	private double distanceSource;
	private Sommet voisinVersSource;

	public Sommet(Case pCase, double pPoids) {
		this.case = pCase;
		this.poids = pPoids;
		this.tabVoisins = new Vector();
		this.estMarque = false;
		this.distanceSource = Double.POSITIVE_INFINITY;
		this.voisinVersSource = new Sommet(pCase, pPoids);
		this.ajouterVoisins();
	}

	private void ajouterVoisins() {
		for (Direction d : Direction.values()) {
			if (this.carte.voisinExiste(this.case, d)) {
				this.tabVoisins.add(this.carte.getVoisin(this.Case, d));
			}
		}
	}

	public Case getCase() {
		return this.case;
	}

	public boolean getEstMarque() {
		return this.estMarque;
	}

	public double getDistanceSource() {
		return this.distanceSource;
	}
	
	public Vector getVoisins() {
		return this.tabVoisins;
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
}
