package cpcc;

import java.util.Vector;

import robots.*;
import maps.*;

public class Sommet {

	private CalculChemin cc;
	private Case position;
	private double tempsTraverseFinal;
	private Vector tabVoisins;
	private Vector poidsVoisins;
	private boolean estMarque;
	private double distanceSource;
	private Sommet voisinVersSource;

	public Sommet(CalculChemin pCc, Case pCase) {
		this.cc = pCc;
		this.position = pCase;
		this.tabVoisins = new Vector();
		this.poidsVoisins = new Vector();
		this.estMarque = false;
		this.distanceSource = Double.POSITIVE_INFINITY;
	}

	public void ajouterVoisins() {
		int lVoisin = 0;
		int cVoisin = 0;
		for (Direction d : Direction.values()) {
			if (this.cc.getCarte().voisinExiste(this.position, d)) {
				lVoisin = this.position.getLigne() + CalculChemin.getDeltaL(d);
				cVoisin = this.position.getColonne() + CalculChemin.getDeltaC(d);
				this.tabVoisins.add(this.cc.getSommet(lVoisin, cVoisin));
				this.poidsVoisins.add(this.cc.calculPoidsArc(this.cc.getCarte(), this.position, this.cc.getCarte().getCase(lVoisin, cVoisin), this.cc.getRobot()));
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

	public double getPoids(int i) {
		return (double) this.poidsVoisins.get(i);
	}

	public double getTempsTraverse() {
		return this.tempsTraverseFinal;
	}

	public void setDistanceSource(double d) {
		this.distanceSource = d;
	}

	public Sommet getVoisinVersSource() {
		return this.voisinVersSource;
	}

	public void setVoisinVersSource(Sommet v) {
		this.voisinVersSource = v;
		this.tempsTraverseFinal = CalculChemin.calculPoidsArc(this.cc.getCarte(), this.position, v.getCase(), this.cc.getRobot());
	}

	public void setEstMarque(boolean b) {
		this.estMarque = b;
	}
}
