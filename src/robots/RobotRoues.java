package robots;

import java.util.Vector;

import cpcc.*;
import maps.*;

public class RobotRoues extends Robot {

	public RobotRoues(Case pPosition) {
	    this(pPosition, 80);
	}

	public RobotRoues(Case pPosition, double pVitesse) {
		this.position = pPosition;
		this.vitesse = pVitesse;
		this.volumeReservoir = 5000;
		this.volumeDisponible = this.volumeReservoir;
		this.tempsRemplissage = 600;
		this.volumeDeversUnitaire = 100;
		this.tempsDeversUnitaire = 5;

        this.cc = new CalculChemin(this);
        this.cheminsIncendies = new Vector();
        this.cheminsRecharge = new Vector();
        this.incendiesInaccessibles = new Vector();
        this.rechargesInaccessibles = new Vector();
	}

	public void setPosition(Case pPosition) {
		if (pPosition.getNature() == NatureTerrain.TERRAIN_LIBRE || pPosition.getNature() == NatureTerrain.HABITAT)	
			this.position = pPosition;
		else
			throw new IllegalArgumentException("Argument invalide : la case spécifiée n'est pas voisine de la case actuelle ou le terrain n'est pas adapté à ce robot.");
	}

	public double getVitesse(NatureTerrain nature) {
		if (nature != NatureTerrain.TERRAIN_LIBRE && nature != NatureTerrain.HABITAT)
			return 0;
		else
			return this.vitesse;
	}

	public void deverserEau() {
	    if (this.volumeDisponible == 0) 
		throw new IllegalArgumentException("Impossible de deverser de l'eau : réservoir vide.");
	    else  
		this.volumeDisponible -= this.volumeDeversUnitaire;
	    
	}

	public void remplirReservoir() {
	    Carte carte = chefPompier.getCarte();
	    Case voisin;
	    boolean aCoteCaseEau = false;
	    CalculChemin cc = new CalculChemin(carte, this);

	    for (Direction d : Direction.values()) {	    
		if (carte.voisinExiste(this.position, d)) {
		    voisin = carte.getCase(this.position.getLigne() + cc.getDeltaL(d), this.position.getColonne() + cc.getDeltaC(d));
		    if (voisin.getNature() == NatureTerrain.EAU) {
			aCoteCaseEau = true;
			break;
		    }
		}
	    }
	    if (aCoteCaseEau)
		this.volumeDisponible = this.volumeReservoir;
	    else 
		throw new IllegalArgumentException("Impossible de remplir le reservoir : le robot n'est pas a coté d'une case contenant de l'eau.");
	}
    
    @Override
    public String toString() {
	return "Roues";
    }
}
