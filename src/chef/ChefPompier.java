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

    public ArrayList getRobots() {
	return this.robots;
    }

    public void afficheIncendies() {
	System.out.println("Liste incendies :");
	for (int i=0; i < incendies.size(); i++) {
	    System.out.println("(" + ((Incendie) incendies.get(i)).getPosition().getLigne() + "," + ((Incendie) incendies.get(i)).getPosition().getColonne() + ")");
	}
    }

    public boolean proposition(Robot robot, Case caseIncendie, Carte carte) {
	return !robot.estOccupe() && robot.cheminExiste(caseIncendie, carte);
    }
    
    public void strategieElementaire() {
	Incendie incendie;
	Case positionIncendie;
	Robot robot;
	ArrayList indicesIncendiesAffectes = new ArrayList();
	for (int i=0; i < incendiesNonAffectes.size(); i++) {
	    incendie = (Incendie)incendiesNonAffectes.get(i);
	    positionIncendie = incendie.getPosition();
	   for (int j=0; j < robots.size(); j++) {
	       robot = ((Robot) robots.get(j));
	       if (proposition(robot , positionIncendie, this.carte)) { // Proposition acceptee
		   robot.ajouteDeplacementsVersDest(positionIncendie, carte); // Ou venir a cote pour autres que drones, a gérer
		   long date = 0;
		   CalculChemin cc = new CalculChemin(carte, robot);
		   Chemin chemin = cc.dijkstra(robot.getPosition(), positionIncendie);
		   for (int k=1; k < chemin.getNbSommets(); k++) {
		       date += (long) chemin.getSommet(k).getPoids();
		   }
		   robot.eteindreIncendie(date, incendie, carte);
		   
		   indicesIncendiesAffectes.add(i);    
		   break;
	       }
	   }
	    
	}
	for (int i=0; i < indicesIncendiesAffectes.size(); i++) {
	    incendiesNonAffectes.remove((int) indicesIncendiesAffectes.get(i));
	}
	
    }
    
  
}
	
