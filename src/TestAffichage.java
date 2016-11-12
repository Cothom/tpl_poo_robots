
import io.LecteurDonnees;
import cpcc.*;
import data.*;
import events.*;
import maps.*;
import robots.*;
import simulator.*;

import java.awt.Color;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;




public class TestAffichage {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
			System.exit(1);
		}

		/* Version initiale Sujet :
		   On peut intercepter les exceptions ici si on le souhaite comme dans le fichier TestLecture.java et non dans creeDonnees : choix de conception a voir avec le reste du groupe. */
		// chargement des donnees 
		DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);


		int largeur = 800;
		int hauteur = 600;

		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(largeur, hauteur, Color.WHITE);

		Simulateur simulateur = new Simulateur(gui, donnees);

		/* Deplacement TYPE 1 
		   Deplacement d1 = new Deplacement(10, donnees.getRobots()[0], donnees.getCarte().getCase(4,3) , donnees.getCarte());
		   Deplacement d2 = new Deplacement(15, donnees.getRobots()[0], donnees.getCarte().getCase(5,3) , donnees.getCarte());
		   Deplacement d3 = new Deplacement(20, donnees.getRobots()[0], donnees.getCarte().getCase(5,4), donnees.getCarte());
		   */

		/*

		Deplacement2 d1 = new Deplacement2(10, donnees.getRobots()[0], Direction.SUD, donnees.getCarte());
		Deplacement2 d2 = new Deplacement2(15, donnees.getRobots()[0], Direction.SUD, donnees.getCarte());
		Deplacement2 d3 = new Deplacement2(20, donnees.getRobots()[0], Direction.SUD, donnees.getCarte());

		Deplacement2 d4 = new Deplacement2(25, donnees.getRobots()[0], Direction.SUD, donnees.getCarte());
		Deplacement2 d5 = new Deplacement2(27, donnees.getRobots()[0], Direction.OUEST, donnees.getCarte());


		Eteindre e1 = new Eteindre(30, donnees.getRobots()[0], donnees.getIncendies()[1], donnees.getCarte()); */
		//Recharger r1 = new Recharger(40, donnees.getRobots()[0], donnees.getCarte());
		/*
		CalculChemin cc = new CalculChemin(donnees.getCarte(), donnees.getRobots()[1]);
		Chemin c1 = cc.dijkstra(donnees.getRobots()[1].getPosition(), donnees.getCarte().getCase(0, 0));
		cc.afficherChemin(c1);
	      	 
		for (int i=1; i < c1.getNbSommets(); i++) {
		    simulateur.ajouteEvenement(new Deplacement(i*10, donnees.getRobots()[1], c1.getSommet(i).getCase(), donnees.getCarte()));
		    
		    }*/
		// donnees.getRobots()[1].ajouteDeplacementsVersDest(donnees.getCarte().getCase(3,4), donnees.getCarte());
		/*
		simulateur.ajouteEvenement(d1);
		simulateur.ajouteEvenement(d2);
		simulateur.ajouteEvenement(d3);
		simulateur.ajouteEvenement(d4);
		simulateur.ajouteEvenement(d5);
		simulateur.ajouteEvenement(e1); */
		//simulateur.ajouteEvenement(r1);


		/* Test Robot 2 */
		/*	Deplacement2 d6 = new Deplacement2(10, donnees.getRobots()[1], Direction.SUD, donnees.getCarte());
		Deplacement2 d7 = new Deplacement2(15, donnees.getRobots()[1], Direction.EST, donnees.getCarte());
		Deplacement2 d8 = new Deplacement2(20, donnees.getRobots()[1], Direction.EST, donnees.getCarte());	
		Eteindre e2 = new Eteindre(30, donnees.getRobots()[1], donnees.getIncendies()[5], donnees.getCarte());	
		simulateur.ajouteEvenement(d6);
		simulateur.ajouteEvenement(d7);
		simulateur.ajouteEvenement(d8);
		simulateur.ajouteEvenement(e2); */

	}
//	/* Version initiale Sujet :
//	   On peut intercepter les exceptions ici si on le souhaite comme dans le fichier TestLecture.java et non dans creeDonnees : choix de conception a voir avec le reste du groupe. */
//	// chargement des donnees 
//	DonneesSimulation donnees = LecteurDonnees.creeDonnees(args[0]);
//
//
//	int largeur = 800;
//	int hauteur = 600;
//
//	// crée la fenêtre graphique dans laquelle dessiner
//	GUISimulator gui = new GUISimulator(largeur, hauteur, Color.WHITE);
//
//	Simulateur simulateur = new Simulateur(gui, donnees);
//
//	/* Deplacement TYPE 1 
//	   Deplacement d1 = new Deplacement(10, donnees.getRobots()[0], donnees.getCarte().getCase(4,3) , donnees.getCarte());
//	   Deplacement d2 = new Deplacement(15, donnees.getRobots()[0], donnees.getCarte().getCase(5,3) , donnees.getCarte());
//	   Deplacement d3 = new Deplacement(20, donnees.getRobots()[0], donnees.getCarte().getCase(5,4), donnees.getCarte());
//	   */
//
//	Deplacement2 d1 = new Deplacement2(10, donnees.getRobots()[0], Direction.SUD, donnees.getCarte());
//	Deplacement2 d2 = new Deplacement2(15, donnees.getRobots()[0], Direction.SUD, donnees.getCarte());
//	Deplacement2 d3 = new Deplacement2(20, donnees.getRobots()[0], Direction.SUD, donnees.getCarte());
//
//	Deplacement2 d4 = new Deplacement2(25, donnees.getRobots()[0], Direction.SUD, donnees.getCarte());
//	Deplacement2 d5 = new Deplacement2(27, donnees.getRobots()[0], Direction.OUEST, donnees.getCarte());
//
//
//	Eteindre e1 = new Eteindre(30, donnees.getRobots()[0], donnees.getIncendies()[1], donnees.getCarte());
//	Recharger r1 = new Recharger(40, donnees.getRobots()[0], donnees.getCarte());
//
//
//	simulateur.ajouteEvenement(d1);
//	simulateur.ajouteEvenement(d2);
//	simulateur.ajouteEvenement(d3);
//	simulateur.ajouteEvenement(d4);
//	simulateur.ajouteEvenement(d5);
//	simulateur.ajouteEvenement(e1);
//	//simulateur.ajouteEvenement(r1);
//
//
//	/* Test Robot 2 */
//	Deplacement2 d6 = new Deplacement2(10, donnees.getRobots()[1], Direction.SUD, donnees.getCarte());
//	//Deplacement2 d7 = new Deplacement2(15, donnees.getRobots()[1], Direction.EST, donnees.getCarte());
//	Deplacement2 d8 = new Deplacement2(20, donnees.getRobots()[1], Direction.EST, donnees.getCarte());	
//	Eteindre e2 = new Eteindre(30, donnees.getRobots()[1], donnees.getIncendies()[5], donnees.getCarte());	
//	simulateur.ajouteEvenement(d6);
//	//simulateur.ajouteEvenement(d7);
//	simulateur.ajouteEvenement(d8);
//	simulateur.ajouteEvenement(e2);

}


