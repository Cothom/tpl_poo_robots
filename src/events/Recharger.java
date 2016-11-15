package events;

import chef.EtatRobot;

import maps.*;
import robots.*;
/**
 * Classe qui permet a un robot de recharger son reservoir.
 */
public class Recharger extends Evenement {

	private Robot robot;
	private Carte carte;

	public Recharger(long pDate, Robot pRobot, Carte pCarte) {
		super(pDate);
		this.robot = pRobot;
		this.carte = pCarte;
	}
	
	/**
	 * Fonction qui remplit le reservoir du robot a une date donnee.
	 * Elle renvoie une erreur si le robot n'est pas a coté d'une case d'eau.
	 */	
	@Override
	public void execute() {
		robot.remplirReservoir();
		robot.setEtatRobot(EtatRobot.LIBRE);
	}	
}

