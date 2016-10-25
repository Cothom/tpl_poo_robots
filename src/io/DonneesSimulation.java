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

	public void creerCarte(int pNbLignes, int pNbColonnes, int pTaille) {
		this.carte = new Carte(pNbLignes, pNbColonnes, pTaille); 
	}

	public void creerCase(int ligne, int colonne, String nature) {
		this.carte[i][j] = new Case(ligne, colonne, NatureTerrain.valueOf(nature));
	}

	public void creerListeIncendies(int nbIncendies) {
		this.incendies = new Incendies[nbIncendies];
	}

	public void creerListeRobots(int nbRobots) {
		this.robots = new Robot[nbRobots];
	}

	public void ajouterIncendie(int ligne, int colonne, int intensite) {
		this.incendies[indiceIncendies] = new Incendies(this.carte[ligne][colonne], intensite);
		indiceIncendies++;
	}

	public void ajouterRobot(String type, int vitesse, int ligne, int colonne) {
		switch (type) {
			case "DRONE" :
				if (vitesse < 0)
					this.robots[indiceRobots] = new Drone(this.carte[ligne][colonne]);
				else
					this.robots[indiceRobots] = new Drone(this.carte[ligne][colonne], vitesse);
				break;
			case "CHENILLES" :
				if (vitesse < 0)
					this.robots[indiceRobots] = new RobotChenilles(this.carte[ligne][colonne]);
				else
					this.robots[indiceRobots] = new RobotChenilles(this.carte[ligne][colonne], vitesse);
				break;
			case "PATTES" :
				this.robots[indiceRobots] = new RobotPattes(this.carte[ligne][colonne]);
				break;
			case "ROUES" :
				if (vitesse < 0)
					this.robots[indiceRobots] = new RobotRoues(this.carte[ligne][colonne]);
				else
					this.robots[indiceRobots] = new RobotRoues(this.carte[ligne][colonne], vitesse);
				break;
			default :
				throw new IllegalArgumentException("Argument invalide : ce type de robot est inconnu.");
				break;
		}
		indiceRobots++;
	}
}
