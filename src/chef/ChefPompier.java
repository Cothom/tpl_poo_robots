package chef;

import java.util.ArrayList;

import cpcc.*;
import data.*;
import maps.*;
import robots.*;

/*
 * La classe chefPompier représente l'entité qui gère les robots
 * et leur répartition sur les différents incendies.
 */

public class ChefPompier {

	private Carte carte;
	private ArrayList incendiesNonAffectes;
	private ArrayList pointsEau;
	private ArrayList robots;

	public ChefPompier(DonneesSimulation donnees) {
		this.carte = donnees.getCarte();

		this.incendiesNonAffectes = new ArrayList();

		for (int i = 0; i < donnees.getIndiceIncendies(); i++) {
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
			donnees.getRobot(i).setChefPompier(this);
			robots.add(donnees.getRobot(i));
		}

	}

	public Carte getCarte() {
		return this.carte;
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

	public void strategieElementaire() {
		Incendie incendie;
		Case positionIncendie;
		Case dest;
		Robot robot;
		ArrayList incendiesAffectes = new ArrayList();
		for (int i=0; i < incendiesNonAffectes.size(); i++) {
			incendie = (Incendie)incendiesNonAffectes.get(i);
			positionIncendie = incendie.getPosition();
			for (int j=0; j < robots.size(); j++) {
				robot = ((Robot) robots.get(j));
                dest = positionIncendie;
				if (!robot.estOccupe() && robot.getVitesse(dest.getNature()) > 0) {
                    long date = 0;
                    Chemin chemin = robot.dijkstra(dest);
                    if (chemin.getTempsParcours() < Double.POSITIVE_INFINITY && chemin.getNbSommets() > 0) {
                        //System.out.println("robot "+j+" "+robot.toString()+" "+chemin.getSommet(0).getCase().toString()+" "+chemin.getSommet(chemin.getNbSommets()-1).getCase().toString()+" temps: "+chemin.getTempsParcours());
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

		}

		for (int i=0; i < incendiesAffectes.size(); i++) {
			int indice = incendiesNonAffectes.indexOf(incendiesAffectes.get(i));
			incendiesNonAffectes.remove(indice);
		}	
	}

}

