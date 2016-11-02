package events;
 
import maps.*;
import robots.*;

public class Deplacement extends Evenement {

    private Robot robot;

    private Case caseDestination;

    public Deplacement(long pDate, Robot pRobot, Case pCaseDestination) {
	super(pDate);
	this.robot = pRobot;
	this.caseDestination = pCaseDestination;
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
	    direction = Direction.SUD;
	} else if (d_y == 1) {
	    direction = Direction.NORD;
	}
	
	//if (!donnees.getCarte().voisinExiste(robot.getPosition(), direction)) {
	//  throw new IllegalArgumentException("Argument invalide : Déplacement impossible dans cette direction, sortie de carte.");
	//}

	robot.setPosition(caseDestination);
    }	
}
    
