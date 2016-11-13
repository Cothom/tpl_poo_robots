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



	public void ajouteDeplacementsVersDest(Case dest, Carte carte) {
		/*
		   - requiert Etat du robot Libre
		   */
		CalculChemin cc = new CalculChemin(carte, this);
		Chemin chemin = cc.dijkstra(this.getPosition(), dest);
		if (chemin.getTempsParcours() == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException("Ce robot (" + this.toString() + ") ne peut pas se rendre sur cette case.");
		}
		cc.afficherChemin(chemin);
		this.ajouteDeplacementChemin(chemin);
	}

	public void eteindreIncendie(long date, Incendie incendie, Carte carte) {
		simulateur.ajouteEvenement(new Etat(simulateur.getDateSimulation() + date, this, EtatRobot.ETEINDRE));
		simulateur.ajouteEvenement(new Etat(simulateur.getDateSimulation() + date + tempsDeversUnitaire, this, EtatRobot.LIBRE));
		simulateur.ajouteEvenement(new Eteindre(simulateur.getDateSimulation() + date + tempsDeversUnitaire, this, incendie, carte));
	}

	public ajouteDeplacementChemin(Chemin chemin) {
		long dateEvenement = simulateur.getDateSimulation();
		for (int i=1; i < chemin.getNbSommets(); i++) {
			dateEvenement += (long) chemin.getSommet(i).getTempsTraverse();
			simulateur.ajouteEvenement(new Deplacement(dateEvenement, this, chemin.getSommet(i).getCase(), carte));		    
		}

		this.etatRobot = EtatRobot.DEPLACEMENT;
		simulateur.ajouteEvenement(new Etat(dateEvenement, this, EtatRobot.LIBRE));
	}

	public void seRecharger() {
		this.ajouteDeplacementChemin(this.trouverCheminRechargement());
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

	public boolean estOccupe() {
		return (this.etatRobot == EtatRobot.LIBRE) ? false : true; // A Modifier
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

