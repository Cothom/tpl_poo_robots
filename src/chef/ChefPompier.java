package chef;

import java.util.ArrayList;

import data.*;
import maps.*;
import robots.*;

public class ChefPompier {

    private Carte carte;
    private ArrayList positionsIncendies;
    private ArrayList incendiesNonAffectes;
    private ArrayList robots;
    
    public ChefPompier(DonneesSimulation donnees) {
	this.carte = donnees.getCarte();

	this.positionsIncendies = new ArrayList();
	this.incendiesNonAffectes = new ArrayList();
	
	for (int i = 0; i < donnees.getIndiceIncendies(); i++) {
	    positionsIncendies.add(donnees.getIncendie(i).getPosition());
	    incendiesNonAffectes.add(donnees.getIncendie(i).getPosition());
	}
	
	this.robots = new ArrayList();
	for (int i = 0; i < donnees.getIndiceRobots(); i++) {
	    robots.add(donnees.getRobot(i));
	}
	
    }

    public Carte getCarte() {
	return this.carte;
    }

    public ArrayList getPositionsIncendies() {
	return this.positionsIncendies;
    }

    public ArrayList getRobots() {
	return this.robots;
    }

    public void afficheIncendies() {
	System.out.println("Liste incendies :");
	for (int i=0; i < positionsIncendies.size(); i++) {
	    System.out.println("(" + ((Case) positionsIncendies.get(i)).getLigne() + "," + ((Case) positionsIncendies.get(i)).getColonne() + ")");
	}
    }

    public boolean proposition(Robot robot, Case caseIncendie, Carte carte) {
	return !robot.estOccupe() && robot.cheminExiste(caseIncendie, carte);
    }
    
    public void strategieElementaire() {
	Robot robot;
	for (int i=0; i < incendiesNonAffectes.size(); i++) {
	   for (int j=0; j < robots.size(); i++) {
	       robot = ((Robot) robots.get(j));
	       if (proposition(robot , (Case)incendiesNonAffectes.get(i), this.carte)) { // Proposition acceptee
		   incendiesNonAffectes.remove(i);
		   break;
	       }
	   }
	    
	}
    }
    
  
}
	
