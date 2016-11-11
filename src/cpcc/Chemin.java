package cpcc;

import java.util.Vector;

import robots.*;
import maps.*;

public class Chemin {

	private Robot robot;
	private double tempsParcours;
	private int nbSommets;
	private Vector tabSommets;

	public Chemin(Robot pRobot) {
		this.robot = pRobot;
		this.tempsParcours = 0;
		this.nombreSommets = 0;
		this.tabSommets = new Vector();
	}

	public void ajouterSommet(Sommet s) {
		this.tabSommets.add(s);
		this.nbSommets++;
		this.tempsParcours += CalculChemin.tempsTraverse(s.getCase(), this.robot);
	}

	public double getTempsParcours() {
		return this.tempsParcours;
	}

	public int getNbSommets() {
		return this.nbSommets;
	}

	public Sommet getSommet(int i) {
		return this.tabSommets;
	}
}
