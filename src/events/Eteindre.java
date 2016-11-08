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
	robot.deverserEau(8000); // A changer
	incendie.eteindre(8000); // IDEM
    }	
}
    
