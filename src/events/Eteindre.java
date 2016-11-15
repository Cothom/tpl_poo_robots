package events;

import chef.EtatRobot;
import maps.*;
import robots.*;

public class Eteindre extends Evenement {

    private Robot robot;
    private Incendie incendie;
    private Carte carte;

    public Eteindre(long pDate, Robot pRobot, Incendie pIncendie, Carte pCarte) {
        super(pDate);
        this.robot = pRobot;
        this.incendie = pIncendie;
        this.carte = pCarte;
    }

    @Override
    public void execute() {
        int d_x = incendie.getPosition().getColonne() - robot.getPosition().getColonne();
        int d_y = incendie.getPosition().getLigne() - robot.getPosition().getLigne();

        if (d_x != 0 || d_y != 0) {
            throw new IllegalArgumentException("Impossible d'éteindre l'incendie : le robot n'est pas au-dessus celui-ci.");
        } else { 
            robot.deverserEau();
            incendie.eteindre(robot.getVolumeDeversUnitaire());

            if (robot.getVolumeDisponible() == 0) {
                robot.setEtatRobot(EtatRobot.RESERVOIR_VIDE);
                robot.seRecharger(carte);

                if (!incendie.estEteint()) {
                    robot.ajouteIncendiesNonAffectes(incendie);
                }
            }
        }
    }	
}

