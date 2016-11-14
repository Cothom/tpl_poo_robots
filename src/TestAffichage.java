
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

		// création du simulateur
		Simulateur simulateur = new Simulateur(gui, donnees, args[0]);
		
	}
}


