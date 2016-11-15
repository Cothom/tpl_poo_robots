package events;

import chef.EtatRobot;
import maps.*;
import robots.*;

public class Recharger extends Evenement {

	private Robot robot;
	private Carte carte;

	public Recharger(long pDate, Robot pRobot, Carte pCarte) {
		super(pDate);
		this.robot = pRobot;
		this.carte = pCarte;
	}

	@Override
	public void execute() {
		robot.remplirReservoir();
		System.out.println("Volume apres Rechargement : " + robot.getVolumeDisponible());
		robot.setEtatRobot(EtatRobot.LIBRE);
	}	
}

