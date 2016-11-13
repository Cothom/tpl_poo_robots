package events;

import chef.EtatRobot;
import robots.*;

public class Etat extends Evenement {
    private Robot robot;
    private EtatRobot etat;
    

    public Etat(long pDate, Robot pRobot, EtatRobot pEtat) {
	super(pDate);
	this.robot = pRobot;
	this.etat = pEtat;
    }

    @Override
    public void execute() {
	robot.setEtatRobot(etat);
    }	
}

