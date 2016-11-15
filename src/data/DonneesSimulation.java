package data;

import maps.*;
import robots.*;

/**
 * Classe qui permet de stocker l'ensemble des données de la simulation.
 */
public class DonneesSimulation {

	private Carte carte;
	private Robot[] robots;
	private Incendie[] incendies;
	private int indiceRobots;
	private int indiceIncendies;

	public DonneesSimulation () {
		this.indiceRobots = 0;
		this.indiceIncendies = 0;
	}


	public Carte getCarte() {
		return this.carte;
	}

	public Robot[] getRobots() {
		return this.robots;
	}

	public Robot getRobot(int i) {
		return this.robots[i];
	}

	public Incendie[] getIncendies() {
		return this.incendies;
	}

	public Incendie getIncendie(int i) {
		return this.incendies[i];
	}

	public int getIndiceRobots() {
		return this.indiceRobots;
	}

	public int getIndiceIncendies() {
		return this.indiceIncendies;
	}

	/**
	 * Permet de créer la carte de la simulation.
	 * @param pNbLignes
	 * @param pNbColonnes
	 * @param pTaille
	 */
	public void creerCarte(int pNbLignes, int pNbColonnes, int pTaille) {
		this.carte = new Carte(pNbLignes, pNbColonnes, pTaille); 
	}

	/**
	 * Permet de céer une case.
	 * @param ligne
	 * @param colonne
	 * @param nature
	 */
	public void creerCase(int ligne, int colonne, String nature) {
		this.carte.setNatureCase(ligne, colonne, NatureTerrain.valueOf(nature));
	}

	/**
	 * Permet de créer une liste d'incendie.
	 * @param nbIncendies
	 */
	public void creerListeIncendies(int nbIncendies) {
		this.incendies = new Incendie[nbIncendies];
	}

	/**
	 * Permet de créer une liste de robots.
	 * @param nbRobots
	 */
	public void creerListeRobots(int nbRobots) {
		this.robots = new Robot[nbRobots];
	}

	/**
	 * Permet d'ajouter un incendie à la liste des incendies.
	 * @param ligne
	 * @param colonne
	 * @param intensite
	 */
	public void ajouterIncendie(int ligne, int colonne, int intensite) {
		this.incendies[indiceIncendies] = new Incendie(this.carte.getCase(ligne, colonne), intensite);
		indiceIncendies++;
	}

	/**
	 * Permet d'ajouter un robot à la liste des robots.
	 * @param type
	 * @param vitesse
	 * @param ligne
	 * @param colonne
	 */
	public void ajouterRobot(String type, int vitesse, int ligne, int colonne) {
		switch (type) {
			case "DRONE" :
				if (vitesse < 0)
					this.robots[indiceRobots] = new Drone(this.carte.getCase(ligne, colonne));
				else
					this.robots[indiceRobots] = new Drone(this.carte.getCase(ligne, colonne), vitesse);
				break;
			case "CHENILLES" :
				if (vitesse < 0)
					this.robots[indiceRobots] = new RobotChenilles(this.carte.getCase(ligne, colonne));
				else
					this.robots[indiceRobots] = new RobotChenilles(this.carte.getCase(ligne, colonne), vitesse);
				break;
			case "PATTES" :
				this.robots[indiceRobots] = new RobotPattes(this.carte.getCase(ligne, colonne));
				break;
			case "ROUES" :
				if (vitesse < 0)
					this.robots[indiceRobots] = new RobotRoues(this.carte.getCase(ligne, colonne));
				else
					this.robots[indiceRobots] = new RobotRoues(this.carte.getCase(ligne, colonne), vitesse);
				break;
			default :
				throw new IllegalArgumentException("Argument invalide : ce type de robot est inconnu.");
		}
		indiceRobots++;
	}
}
