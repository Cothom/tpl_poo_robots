package cpcc;

import java.util.Vector;

import robots.*;
import maps.*;

public class Chemin {

	private Carte carte;
	private Robot robot;
	private double tempsParcours;
	private Vector tabSommets;
	private Vector tabTemps;

	public Chemin(Carte pCarte, Robot pRobot) {
		this.carte = pCarte;
		this.robot = pRobot;
		this.tempsParcours = 0;
		this.tabSommets = new Vector();
		this.tabTemps = new Vector();
	}

	public void ajouterSommet(Sommet s) {
		this.tabSommets.add(s);
		double temps = CalculChemin.calculPoidsArc(this.carte, ((Sommet) this.tabSommets.get(this.tabSommets.size()-1)).getCase(), s.getCase(), this.robot);
		this.tabTemps.add(temps);
		this.tempsParcours += temps;
	}

	public double getTempsParcours() {
		return this.tempsParcours;
	}

    public void setTempsParcours(double t) {
	    this.tempsParcours = t;
	}

	public int getNbSommets() {
		return this.tabSommets.size();
	}

	public Sommet getSommet(int i) {
		return (Sommet) this.tabSommets.get(i);
	}

	public Vector getTabSommets() {
		return this.tabSommets;
	}

	public boolean estVide() {
	    return (this.tabSommets.size() > 0) ? false : true; // Tu as inverse et j'ai corrige (Rana) ? OK ? ok
	}
}
