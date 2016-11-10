package events;

import maps.*;
import robots.*;

public class Eteindre extends Evenement {

	private Robot robot;
	private Incendie incendie;
	private Carte carte;

	public Eteindre(long pDate, Robot pRobot, Incendie pIncendie, Carte pCarte) {
		super(pDate);
		this.robot = pRobot;
		this.incendie = pIncendie; // Il faut rajouter un controle sur la position de l'incendie par rapport au robot
		this.carte = pCarte;
	}

	@Override
	public void execute() {
		int d_x = incendie.getPosition().getColonne() - robot.getPosition().getColonne();
		int d_y = incendie.getPosition().getLigne() - robot.getPosition().getLigne();
		if (Math.abs(d_x) > 1 || Math.abs(d_y) > 1 || (Math.abs(d_x) == 1 && Math.abs(d_y) == 1) || (Math.abs(d_x) == 0 && Math.abs(d_y) == 0)) {
			throw new IllegalArgumentException("Impossible d'éteindre l'incendie : le robot n'est pas a coté de celui-ci.");
		} else { 
			System.out.println("Intensite : " + incendie.getIntensite());
			System.out.println(robot.toString() + " Volume avant deversement  : " + robot.getVolumeDisponible());
			robot.deverserEau(); // A changer
			incendie.eteindre(robot.getVolumeDeversUnitaire()); // IDEM  	
			System.out.println(robot.toString() + "Volume apres deversement : " + robot.getVolumeDisponible());
			System.out.println("Intensite : " + incendie.getIntensite());
		}
	}	
}

