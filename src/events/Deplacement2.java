package events;
 
import maps.*;
import robots.*;

public class Deplacement2 extends Evenement {

    private Robot robot;
    private Direction direction;
    private Carte carte;
    
    public Deplacement2(long pDate, Robot pRobot, Direction pDirection, Carte pCarte) {
	super(pDate);
	this.robot = pRobot;
	this.direction = pDirection;
	this.carte = pCarte;
    }
    
    @Override
    public void execute() { // Prendre en compte la vitesse de Deplacement 
	if (!carte.voisinExiste(robot.getPosition(), direction)) {
	  throw new IllegalArgumentException("Argument invalide : Déplacement impossible dans cette direction, sortie de carte.");
	}

	Case caseDestination;
	int i = robot.getPosition().getLigne();
	int j = robot.getPosition().getColonne();
	switch (direction) {
	case NORD :
	    i--;
	    break;
	case SUD :
	    i++;
	    break;
	case EST :
	    j++;
	    break;
	case OUEST :
	    j--;
	    break;
	}

	robot.setPosition(carte.getCase(i,j));
    }	
}
    
