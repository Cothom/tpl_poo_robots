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
	int volume = robot.getVolumeDisponible();	
	System.out.println("Volume avant deversement : " + robot.getVolumeDisponible());
	robot.deverserEau(volume); // A changer
	incendie.eteindre(volume); // IDEM  	
	System.out.println("Volume apres deversement : " + robot.getVolumeDisponible());
    }	
}
    
