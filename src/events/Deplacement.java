package events;

import chef.EtatRobot;
import maps.*;
import robots.*;

public class Deplacement extends Evenement {

	private Robot robot;
	private Case caseDestination;
	private Carte carte;

    
	public Deplacement(long pDate, Robot pRobot, Case pCaseDestination, Carte pCarte) {
		super(pDate);
		this.robot = pRobot;
		this.caseDestination = pCaseDestination;
		this.carte = pCarte;

		String typeRobot = this.robot.toString();
		switch (this.caseDestination.getNature()) {
			case EAU :
				if (!typeRobot.equals("Drone")) {
					throw new IllegalArgumentException("Argument invalide : ce type de case n'est pas accessible par ce robot.");
				}
				break;
			case ROCHE :
				if (typeRobot.equals("Chenille") || typeRobot.equals("Roues")) {
					throw new IllegalArgumentException("Argument invalide : ce type de case n'est pas accessible par ce robot.");
				}
				break;
			case FORET :
				if (typeRobot.equals("Roues")) {
					throw new IllegalArgumentException("Argument invalide : ce type de case n'est pas accessible par ce robot.");
				}
				break;
			default :
				break;
		}		
	}
    
	@Override
	public void execute() {
	    int d_x = caseDestination.getColonne() - robot.getPosition().getColonne();
	    int d_y = caseDestination.getLigne() - robot.getPosition().getLigne();
	    
	    if (Math.abs(d_x) > 1 || Math.abs(d_y) > 1 || (Math.abs(d_x) == 1 && Math.abs(d_y) == 1) || (Math.abs(d_x) == 0 && Math.abs(d_y) == 0)) {
		throw new IllegalArgumentException("Argument invalide : Direction de déplacement interdite.");
	    }
	    Direction direction;
	    if (d_x == -1) {
		direction = Direction.OUEST;
	    } else if (d_x == 1) {
		direction = Direction.EST;
	    } else if (d_y == -1) {
		direction = Direction.NORD;
	    } else { // (d_y == 1)
		direction = Direction.SUD;
	    }

	    if (!carte.voisinExiste(robot.getPosition(), direction)) {
		System.out.println(robot.toString()+" ("+d_x+", "+d_y+")");
		throw new IllegalArgumentException("Argument invalide : Déplacement impossible dans cette direction, sortie de carte.");
		}
	    robot.setPosition(caseDestination);

	}      
}

