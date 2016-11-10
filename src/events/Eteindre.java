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
	System.out.println("Intensite : " + incendie.getIntensite());
	System.out.println(robot.toString() + " Volume avant deversement  : " + robot.getVolumeDisponible());
	robot.deverserEau(); // A changer
	incendie.eteindre(robot.getVolumeDeversUnitaire()); // IDEM  	
	System.out.println(robot.toString() + "Volume apres deversement : " + robot.getVolumeDisponible());
	System.out.println("Intensite : " + incendie.getIntensite());
    }	
}
    
