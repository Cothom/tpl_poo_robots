package robots;

import java.util.Vector;

import chef.*;
import cpcc.*;
import events.*;
import maps.*;
import simulator.*;

/**
 * Classe abstraite représentant un robot
 * Un robot est capable de trouver son chemin vers une case de la carte
 * et programme lui-même les évènements lui permettant d'y accéder
 * Il est aussi capable de détecter qu'il doit aller se recharger
 * et il le fait de manière autonome.
 */
public abstract class Robot {

    protected CalculChemin cc;
    protected Vector cheminsIncendies;
    protected Vector cheminsRecharge;
    protected Vector incendiesInaccessibles;
    protected Vector rechargesInaccessibles;
	protected Simulateur simulateur;
	protected Case position;
	protected double vitesse;
	protected int volumeReservoir;
	protected int volumeDisponible;
	protected int tempsRemplissage;
	protected int volumeDeversUnitaire;
	protected int tempsDeversUnitaire;
	protected EtatRobot etatRobot = EtatRobot.LIBRE;
	protected ChefPompier chefPompier;
	/**
	 * Change la position du robot vers la case pCase
	 * @param pCase case sur laquelle on met le robot
	 */
	public abstract void setPosition(Case pCase);
	/**
	 * Retourne la vitesse du robot en fonction de la nature du terrain
	 * @param nature
	 * @return
	 */
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
        this.cc.setCarte(s.getCarte());
	}

	public EtatRobot getEtatRobot() {
		return this.etatRobot;
	}

	public void setEtatRobot(EtatRobot etat) {
		this.etatRobot = etat;
	}
	/**
	 * Cete fonction est utile car elle comprend la réinitialisation
	 * des sommets de la classe CalculChemin.
	 * @param dst
	 * @return c C'est le chemin le plus court entre la position du robot et dst.
	 */
    public Chemin dijkstra(Case dst) {
        Chemin c = this.cc.dijkstra(this.position, dst);
        this.cc.reinitSommets(this.simulateur.getCarte());
        return c;
    }

	public void setChefPompier(ChefPompier chefPompier) {
		this.chefPompier = chefPompier;
	}

	public void ajouteIncendiesNonAffectes(Incendie incendie) {
		chefPompier.getIncendiesNonAffectes().add(incendie);
	}

	/**
	 * Cherche la carte adjacente à la carte dest qui est la plus proche du robot
	 * au sens du plus court chemin de la méthode CalculChemin.dijkstra
	 * @param dest case dont le voisin le plus proche est recherché
	 * @param carte permet d'accéder directement à la carte et donc aux cases
	 * @return le voisin de dest le plus proche de robot ainsi que le chemin pour y arriver
	 */
	public PlusProcheObjet caseLaPlusProcheAutour(Case dest, Carte carte) {
		Case voisin;
		Case casePlusProche = dest;
		Chemin chemin;
        Chemin pcChemin = new Chemin(carte, this);
		double temps = Double.POSITIVE_INFINITY;

		for (Direction d : Direction.values()) {

			if (carte.voisinExiste(dest, d)) {
				voisin = carte.getCase(dest.getLigne() + this.cc.getDeltaL(d), dest.getColonne() + this.cc.getDeltaC(d));
				chemin = this.dijkstra(voisin);

				if (chemin.getTempsParcours() < temps) {		  
					temps = chemin.getTempsParcours();
                    pcChemin = chemin;
					casePlusProche = voisin;
				}			    
			}
		}

		return new PlusProcheObjet(casePlusProche, pcChemin);

	}
	/**
	 * Calcule le plus cours chemin jusqu'à dest et programme
	 * la suite des évènements Deplacements permettant au robot d'y arriver
	 * @param dest case vers laquelle le robot doit se déplacer
	 * @param carte permet d'accéder directement à la carte et donc aux cases
	 */
	public void ajouteDeplacementsVersDest(Case dest, Carte carte) {
		/*
		   - requiert Etat du robot Libre ou Reservoir vide
		   */

		if (this.getEtatRobot() != EtatRobot.LIBRE && this.getEtatRobot() != EtatRobot.RESERVOIR_VIDE) { return; }
		CalculChemin cc = new CalculChemin(carte, this);
		Chemin chemin = cc.dijkstra(this.getPosition(), dest);

		if (chemin.getTempsParcours() == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException("Ce robot (" + this.toString() + ") ne peut pas se rendre sur cette case.");
		}

		long dateEvenement = simulateur.getDateSimulation();
		for (int i=1; i < chemin.getNbSommets(); i++) {
			dateEvenement += (long) chemin.getSommet(i).getTempsTraverse();
			simulateur.ajouteEvenement(new Deplacement(dateEvenement, this, chemin.getSommet(i).getCase(), carte));		    
		}

		this.etatRobot = EtatRobot.DEPLACEMENT;
		simulateur.ajouteEvenement(new Etat(dateEvenement, this, EtatRobot.LIBRE));
	}

	/**
	 * Programme la suite des évènements nécessaire aux robots pour
	 * éteindre l'incendie
	 * @param date date à laquelle le robot commence à éteindre l'incendie
	 * @param incendie l'incendie à éteindre
	 * @param carte permet d'accéder directement à la carte et donc aux cases
	 */
	public void eteindreIncendie(long date, Incendie incendie, Carte carte) {

		simulateur.ajouteEvenement(new Etat(simulateur.getDateSimulation() + date, this, EtatRobot.ETEINDRE));
		int intensite = incendie.getIntensite();
		int nbDeversUnitaire;
		if (this.toString() == "Drone") {
			nbDeversUnitaire = 1;
		} else if (this.toString() == "Pattes") {
			nbDeversUnitaire = intensite / volumeDeversUnitaire;
		} else {
			nbDeversUnitaire = (intensite > this.volumeDisponible) ? (volumeDisponible / volumeDeversUnitaire) : (intensite / volumeDeversUnitaire);
		}

		simulateur.ajouteEvenement(new Etat(simulateur.getDateSimulation() + date + (nbDeversUnitaire)*tempsDeversUnitaire, this, EtatRobot.LIBRE));
		for (int i=1; i <= nbDeversUnitaire; i++) {
			simulateur.ajouteEvenement(new Eteindre(simulateur.getDateSimulation() + date + i*tempsDeversUnitaire, this, incendie, carte));
		}


	}
	/**
	 * Trouve la case de rechargement la plus proche et programme la série d'évènements
	 * pour y accéder
	 * @param carte permet d'accéder directement à la carte et donc aux cases
	 */
    public void seRecharger(Carte carte) {

        Case caseRechargement = (this.toString() == "Drone") ? (Case) chefPompier.getPointsEau().get(0) : (Case) this.caseLaPlusProcheAutour((Case) chefPompier.getPointsEau().get(0), carte).getObjet();
        Case caseDest;
        Chemin chemin;
        Chemin cheminVersDest = this.dijkstra((Case) chefPompier.getPointsEau().get(0));
        double temps = Double.POSITIVE_INFINITY;


        for (int i=1; i < chefPompier.getPointsEau().size(); i++) {
            caseDest = (this.toString() == "Drone") ? (Case) chefPompier.getPointsEau().get(i) : (Case) this.caseLaPlusProcheAutour((Case) chefPompier.getPointsEau().get(i), carte).getObjet();
            chemin = this.dijkstra(caseDest);

            if (chemin.getTempsParcours() < temps) {	  
                temps = chemin.getTempsParcours();
                caseRechargement = caseDest;
                cheminVersDest = chemin;
            }
        }

        this.ajouteDeplacementsVersDest(caseRechargement, carte);
        long dateRechargement = simulateur.getDateSimulation();

        for (int i=1; i < cheminVersDest.getNbSommets(); i++) {
            dateRechargement += (long) cheminVersDest.getSommet(i).getTempsTraverse();
        }
        simulateur.ajouteEvenement(new Etat(dateRechargement, this, EtatRobot.RECHARGEMENT));
        simulateur.ajouteEvenement(new Recharger(dateRechargement + tempsRemplissage, this, carte));
    }

	public boolean estOccupe() {
		return (this.etatRobot == EtatRobot.LIBRE) ? false : true; 
	}
	
	/**
	 * Ajoute les évènements nécessaires au déplacement à partir du chemin
	 * @param chemin permet de déduire les micro-déplacements à effectuer
	 */
    public void ajouteDeplacementChemin(Chemin chemin) {
        long dateEvenement = simulateur.getDateSimulation();
        for (int i=1; i < chemin.getNbSommets(); i++) {
            dateEvenement += (long) chemin.getSommet(i).getTempsTraverse();
            simulateur.ajouteEvenement(new Deplacement(dateEvenement, this, chemin.getSommet(i).getCase(), this.chefPompier.getCarte()));		    
        }
        this.etatRobot = EtatRobot.DEPLACEMENT;
        simulateur.ajouteEvenement(new Etat(dateEvenement, this, EtatRobot.LIBRE));
    }

}

