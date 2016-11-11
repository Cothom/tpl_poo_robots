package cpcc;

import java.util.Vector;

import robots.*;
import maps.*;

public class Chemin {

	private Robot robot;
	private double tempsParcours;
	private int nombreCases;
	private Vector tabCases;

	public Chemin(Robot pRobot) {
		this.robot = pRobot;
		this.tempsParcours = 0;
		this.nombreCases = 0;
		this.tabCases = new Vector();
	}

	public void ajouterSommet(Sommet s) {
		this.tabCases.add(s);
		this.nombreCases++;
		this.tempsParcours += CalculChemin.tempsTraverse(s.getCase(), this.robot);
	}

	public double getTempsParcours() {
		return this.tempsParcours;
	}
}
