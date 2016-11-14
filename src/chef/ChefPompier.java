package chef;

import java.util.ArrayList;

import cpcc.*;
import data.*;
import maps.*;
import robots.*;

public class ChefPompier {

	private Carte carte;
	private ArrayList incendies;
	private ArrayList incendiesNonAffectes;
	private ArrayList pointsEau;
	private ArrayList robots;

	public ChefPompier(DonneesSimulation donnees) {
		this.carte = donnees.getCarte();

		this.incendies = new ArrayList();
		this.incendiesNonAffectes = new ArrayList();

		for (int i = 0; i < donnees.getIndiceIncendies(); i++) {
			incendies.add(donnees.getIncendie(i));
			incendiesNonAffectes.add(donnees.getIncendie(i));
		}

		this.pointsEau = new ArrayList();
		for (int i = 0; i < carte.getNbLignes(); i++) {
			for (int j = 0; j < carte.getNbColonnes(); j++) {
				if (carte.getCase(i,j).getNature() == NatureTerrain.EAU) {
					pointsEau.add(carte.getCase(i,j));
				}
			}	    
		}

		this.robots = new ArrayList();
		for (int i = 0; i < donnees.getIndiceRobots(); i++) {
            System.out.println("ChefPompier : Iteration " + i);
			donnees.getRobot(i).setChefPompier(this);
			robots.add(donnees.getRobot(i));
		}

	}

	public Carte getCarte() {
		return this.carte;
	}

	public ArrayList getIncendies() {
		return this.incendies;
	}

    public Incendie getIncendie(int i) {
        return (Incendie) this.incendies.get(i);
    }

	public ArrayList getIncendiesNonAffectes() {
		return this.incendiesNonAffectes;
	}

	public ArrayList getPointsEau() {
		return this.pointsEau;
	}

    public Case getPointEau(int i) {
        return (Case) this.pointsEau.get(i);
    }

	public ArrayList getRobots() {
		return this.robots;
	}

	public void afficheIncendies() {
		System.out.println("Liste incendies non affectes:");
		for (int i=0; i < incendiesNonAffectes.size(); i++) {
			System.out.println("(" + ((Incendie) incendiesNonAffectes.get(i)).getPosition().getLigne() + "," + ((Incendie) incendiesNonAffectes.get(i)).getPosition().getColonne() + ")");
		}
	}

	public boolean proposition(Robot robot, Case caseIncendie, Carte carte) {
		return !robot.estOccupe() && robot.cheminExiste(caseIncendie, carte);
	}

//    public boolean proposition(Robot r, Incendie i) {
//        return !r.estOccupe() && !r.estInaccessible(i);
//    }

	public void strategieElementaire() {
		Incendie incendie;
		Case positionIncendie;
		Case dest;
		Robot robot;
		ArrayList incendiesAffectes = new ArrayList();
		//afficheIncendies();
		System.out.println("Nb incendies a passer : " + incendiesNonAffectes.size());
		System.out.println("Nb robots a passer : " + robots.size());
		for (int i=0; i < incendiesNonAffectes.size(); i++) {
			incendie = (Incendie)incendiesNonAffectes.get(i);
			positionIncendie = incendie.getPosition();
			for (int j=0; j < robots.size(); j++) {
				robot = ((Robot) robots.get(j));
				//if (proposition(robot , positionIncendie, this.carte)) {
				if (robot.toString() == "Drone") {
				    dest = positionIncendie;
				} else {
				    dest = (Case) robot.caseLaPlusProcheAutour(positionIncendie, carte).getObjet();
				}
//				boolean test = proposition(robot , dest, this.carte);
//				if (robot.toString() == "Roues" || robot.toString() == "Pattes") {
//				    if (!robot.estOccupe()) {
//					System.out.println(robot.toString()+" non occupé");
//				    }
//				    System.out.println("chemin vers ("+dest.getLigne() +","+ dest.getColonne()+") existe ?");
//				    if (robot.cheminExiste(dest, carte)) {
//					System.out.println("Chemin existe");
//				    }
//				}
//				if (proposition(robot , dest, this.carte)) { //
//				if (proposition(robot , incendie)) { //
                long date = 0;
//                CalculChemin cc = new CalculChemin(carte, robot);
                Chemin chemin = robot.dijkstra(dest);
				System.out.println("\n\nIncendie : " + i + "\nRobot : " + j + "\nDijkstra termine !\n\n");
                System.out.println("robot "+j+" "+robot.toString()+" "+chemin.getSommet(0).getCase().toString()+" "+chemin.getSommet(chemin.getNbSommets()-1).getCase().toString()+" temps: "+chemin.getTempsParcours());
                if (!robot.estOccupe() && chemin.getTempsParcours() < Double.POSITIVE_INFINITY) {
                    robot.ajouteDeplacementChemin(chemin);

                    for (int k=1; k < chemin.getNbSommets(); k++) {
                        date += (long) chemin.getSommet(k).getTempsTraverse();
                    }

                    robot.eteindreIncendie(date, incendie, carte);	   
                    incendiesAffectes.add(incendie);    
                    break;
                }
			}

		}

		for (int i=0; i < incendiesAffectes.size(); i++) {
			int indice = incendiesNonAffectes.indexOf(incendiesAffectes.get(i));
			incendiesNonAffectes.remove(indice);
		}	
	}

}

