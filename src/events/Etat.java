package events;

import chef.EtatRobot;
import robots.*;

/**
 * Classe qui permet a un robot de changer d'état à une date donnée.
 */
public class Etat extends Evenement {
    private Robot robot;
    private EtatRobot etat;
    

    public Etat(long pDate, Robot pRobot, EtatRobot pEtat) {
	super(pDate);
	this.robot = pRobot;
	this.etat = pEtat;
    }

   /**
    * Fonction qui effectue l'acion d'un changement d'état pour le robot.
    */
    @Override
    public void execute() {
	robot.setEtatRobot(etat);
    }	
}

