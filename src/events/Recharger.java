package events;
 
import maps.*;
import robots.*;

public class Recharger extends Evenement {

    private Robot robot;
    private Carte carte;
    /* Faut-il rajouter un attribut case si on recharge sur une case ? */
    
    public Recharger(long pDate, Robot pRobot, Carte pCarte) {
	super(pDate);
	this.robot = pRobot;
	this.carte = pCarte;
    }
    
    @Override
    public void execute() {
	robot.remplirReservoir();
	System.out.println("Volume apres Rechargement : " + robot.getVolumeDisponible());
    }	
}
    
