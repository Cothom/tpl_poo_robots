package robots;

import java.util.Vector;

import cpcc.*;
import maps.*;

public class Drone extends Robot {

	private static double vitesseMaximale = 150;

	public Drone(Case pPosition) {
		this(pPosition, 100);
	}

	public Drone(Case pPosition, double pVitesse) {
		this.position = pPosition;
		this.vitesse = (pVitesse < vitesseMaximale) ? pVitesse : vitesseMaximale;
		this.volumeReservoir = 10000;
		this.volumeDisponible = this.volumeReservoir;
		this.tempsRemplissage = 1800;
		this.volumeDeversUnitaire = this.volumeReservoir;
		this.tempsDeversUnitaire = 30;

        this.cc = new CalculChemin(this);
        this.cheminsIncendies = new Vector();
        this.cheminsRecharge = new Vector();
        this.incendiesInaccessibles = new Vector();
        this.rechargesInaccessibles = new Vector();
	}

	public void setPosition(Case pPosition) {
		this.position = pPosition;
	}

	public double getVitesse(NatureTerrain nature) {
		return this.vitesse;
	}

	public void deverserEau() {
		if (this.volumeDisponible == 0) 
			throw new IllegalArgumentException("Impossible de deverser de l'eau : réservoir vide.");
		else  
			this.volumeDisponible -= this.volumeDeversUnitaire;	    
	}

	public void remplirReservoir() {
		if (this.position.getNature() == NatureTerrain.EAU)
			this.volumeDisponible = this.volumeReservoir;
		else 
			throw new IllegalArgumentException("Impossible de remplir le reservoir : la case ne contient pas d'eau.");
	}

	@Override
	public String toString() {
		return "Drone";
	}

}
