package robots;

import chef.EtatRobot;
import cpcc.*;
import events.*;
import maps.*;
import simulator.*;

public abstract class Robot {

	protected Simulateur simulateur;
	protected Case position;
	protected double vitesse;
	protected int volumeReservoir;
	protected int volumeDisponible;
	protected int tempsRemplissage;
	protected int volumeDeversUnitaire;
	protected int tempsDeversUnitaire;

        protected EtatRobot etat = EtatRobot.LIBRE; // ? Rana

	public abstract void setPosition(Case pCase);
	public abstract double getVitesse(NatureTerrain nature);
	public abstract void deverserEau();
	public abstract void remplirReservoir();

	public Case getPosition() {
		return this.position;
	}

	public double getVitesse() {
		return this.vitesse;
	}

	public int getVolumeReservoir() {
		return this.volumeReservoir;
	}

	public int getVolumeDisponible() {
		return this.volumeDisponible;
	}

	public int getTempsRemplissage() {
		return this.tempsRemplissage;
	}

	public int getVolumeDeversUnitaire() {
		return this.volumeDeversUnitaire;
	}

	public int getTempsDeversUnitaire() {
		return this.tempsDeversUnitaire;
	}

	public void setSimulateur(Simulateur s) {
		this.simulateur = s;
	}

    public void ajouteDeplacementsVersDest(Case dest, Carte carte) {
	    CalculChemin cc = new CalculChemin(carte, this);
	    Chemin chemin = cc.dijkstra(this.getPosition(), dest);
	    if (chemin.getTempsParcours() == Double.POSITIVE_INFINITY) {
	        throw new IllegalArgumentException("Ce robot (" + this.toString() + ") ne peut pas se rendre sur cette case.");
	    }
	    cc.afficherChemin(chemin);
	    long dateEvenement = simulateur.getDateSimulation();
	    for (int i=1; i < chemin.getNbSommets(); i++) {
		dateEvenement += (long) chemin.getSommet(i).getPoids();
		simulateur.ajouteEvenement(new Deplacement(dateEvenement, this, chemin.getSommet(i).getCase(), carte));		    
	    }	    
        }

        public boolean estOccupe() {
	    return (this.etat == EtatRobot.LIBRE) ? false : true; // A Modifier
	}


    public boolean cheminExiste(Case dest, Carte carte) {
	CalculChemin cc = new CalculChemin(carte, this);
	Chemin chemin = cc.dijkstra(this.getPosition(), dest);
	if (chemin.getTempsParcours() == Double.POSITIVE_INFINITY) {
	    return false;
	}
	return true;
    }
}

