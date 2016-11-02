
import io.LecteurDonnees;
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
	
	int largeur = donnees.getCarte().getNbColonnes() * donnees.getCarte().getTailleCases() / 100;
	
	int hauteur = donnees.getCarte().getNbLignes() * donnees.getCarte().getTailleCases() / 100;


	// crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(largeur, hauteur + 50, Color.WHITE);

	Simulateur simulateur = new Simulateur(gui, donnees);



	Deplacement d1 = new Deplacement(10, donnees.getRobots()[0], donnees.getCarte().getCase(4,3), donnees.getCarte());
	Deplacement d2 = new Deplacement(15, donnees.getRobots()[0], donnees.getCarte().getCase(5,3), donnees.getCarte());
        Deplacement d3 = new Deplacement(20, donnees.getRobots()[0], donnees.getCarte().getCase(5,4), donnees.getCarte());
	Eteindre e1 = new Eteindre(30, donnees.getRobots()[0], donnees.getIncendies()[4], donnees.getCarte());
	
	simulateur.ajouteEvenement(d1);
	simulateur.ajouteEvenement(d2);
	simulateur.ajouteEvenement(d3);
	simulateur.ajouteEvenement(e1);
	

    }

}


