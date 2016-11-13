package robots;

import chef.*;
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
    protected EtatRobot etatRobot = EtatRobot.LIBRE; // ? Rana
    protected ChefPompier chefPompier;
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
    
    public EtatRobot getEtatRobot() {
	return this.etatRobot;
    }
    
    public void setEtatRobot(EtatRobot etat) {
	this.etatRobot = etat;
    }

    public void setChefPompier(ChefPompier chefPompier) {
	this.chefPompier = chefPompier;
    }

    public void ajouteIncendiesNonAffectes(Incendie incendie) {
	chefPompier.getIncendiesNonAffectes().add(incendie);
    }

    public Case caseLaPlusProcheAutour(Case dest, Carte carte) {
	Case voisin;
	Case casePlusProche = dest;
	CalculChemin cc;//c = new CalculChemin(carte, this);
	Chemin chemin;
	double temps = Double.POSITIVE_INFINITY;
	
	for (Direction d : Direction.values()) {
	    
	    if (carte.voisinExiste(dest, d)) {
		cc = new CalculChemin(carte, this);
		voisin = carte.getCase(dest.getLigne() + cc.getDeltaL(d), dest.getColonne() + cc.getDeltaC(d));
		chemin = cc.dijkstra(this.position, voisin);
		
		if (chemin.getTempsParcours() < temps) {		  
		    temps = chemin.getTempsParcours();
		    casePlusProche = voisin;
		}			    
	    }
	}

	return casePlusProche;
	
    }
    
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

    public void eteindreIncendie(long date, Incendie incendie, Carte carte) {
	
	simulateur.ajouteEvenement(new Etat(simulateur.getDateSimulation() + date, this, EtatRobot.ETEINDRE));
	simulateur.ajouteEvenement(new Etat(simulateur.getDateSimulation() + date + tempsDeversUnitaire, this, EtatRobot.LIBRE));
	simulateur.ajouteEvenement(new Eteindre(simulateur.getDateSimulation() + date + tempsDeversUnitaire, this, incendie, carte));
	
    }

    public void seRecharger(Carte carte) {
	
	Case caseRechargement = (this.toString() == "Drone") ? (Case) chefPompier.getPointsEau().get(0) : this.caseLaPlusProcheAutour((Case) chefPompier.getPointsEau().get(0), carte);
	Case caseDest;
	CalculChemin cc = new CalculChemin(carte, this);
	Chemin chemin;
	Chemin cheminVersDest = cc.dijkstra(this.position, (Case) chefPompier.getPointsEau().get(0));
	double temps = Double.POSITIVE_INFINITY;

	for (int i=1; i < chefPompier.getPointsEau().size(); i++) {
	    cc = new CalculChemin(carte, this);
	    caseDest = (this.toString() == "Drone") ? (Case) chefPompier.getPointsEau().get(i) : this.caseLaPlusProcheAutour((Case) chefPompier.getPointsEau().get(i), carte);
	    chemin = cc.dijkstra(this.position, caseDest);

	    if (chemin.getTempsParcours() < temps) {	  
		temps = chemin.getTempsParcours();
		caseRechargement = caseDest;
		cheminVersDest = chemin;
	    }
	}

	this.ajouteDeplacementsVersDest(caseRechargement, carte);
	//cc.afficherChemin(cheminVersDest);

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


    public boolean cheminExiste(Case dest, Carte carte) {
	CalculChemin cc = new CalculChemin(carte, this);
	Chemin chemin = cc.dijkstra(this.getPosition(), dest);
	if (chemin.getTempsParcours() == Double.POSITIVE_INFINITY) {
	    return false;
	}
	return true;
    }

    /*
    public void ajouteDeplacementChemin(Chemin chemin) {
	long dateEvenement = simulateur.getDateSimulation();
	for (int i=1; i < chemin.getNbSommets(); i++) {
	    dateEvenement += (long) chemin.getSommet(i).getTempsTraverse();
	    simulateur.ajouteEvenement(new Deplacement(dateEvenement, this, chemin.getSommet(i).getCase(), carte));		    
	}

	this.etatRobot = EtatRobot.DEPLACEMENT;
	simulateur.ajouteEvenement(new Etat(dateEvenement, this, EtatRobot.LIBRE));
    }

    public Chemin trouverCheminRechargement() {
	Vector caseEau = new Vector();
	Carte carte = this.simulateur.getCarte();
	CalculChemin cc = new CalculChemin(carte, this);
	Chemin chemin;
	double tempsMin = Double.POSITIVE_INFINITY;
	Case caseCourante;
	Chemin meilleurChemin;

	for (int i = 0; i < carte.getNbLignes(); i++) {
	    for (int j = 0; j < carte.getNbColonnes(); j++) {
		if (carte.getCase(i, j).getNature() == NatureTerrain.EAU) {
		    caseEau.add(carte.getCase(i, j));
		}
	    }
	}
	for (Case cr : casesEau) {
	    for (Direction d : Direction.values()) {
		if (carte.voisinExiste(cr, d) && carte.getVoisin(cr, d).estAccessible(this)) {
		    caseCourante = carte.getCase(cr.getLigne() + CalculChemin.getDeltaL(d), cr.getColonne() + CalculChemin.getDeltaC(d));
		    chemin = cc.dijsktra(this.position, caseCourante);
		    if (chemin.getTempsParcours() < tempsMin) {
			tempsMin = chemin.getTempsParcours();
			meilleurChemin = chemin;
		    }
		    //					this.casesRechargement.add(carte.getCase(cr.getLigne() + CalculChemin.getDeltaL(d), cr.getColonne() + CalculChemin.getDeltaC(d)));
		}
	    }
	}
	return meilleurChemin;
    }

    */
}

