package cpcc;

import java.util.Vector;

import robots.*;
import maps.*;

public class Chemin {

	private Carte carte;
	private Robot robot;
	private double tempsParcours;
	private Vector tabSommets;

	public Chemin(Carte pCarte, Robot pRobot) {
		this.carte = pCarte;
		this.robot = pRobot;
		this.tempsParcours = 0;
		this.tabSommets = new Vector();
	}

	public void ajouterSommet(Sommet s) {
		this.tabSommets.add(s);
		this.tempsParcours += CalculChemin.tempsTraverse(this.carte, s.getCase(), this.robot);
	}

	public double getTempsParcours() {
		return this.tempsParcours;
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
}
