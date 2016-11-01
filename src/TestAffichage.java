
import io.LecteurDonnees;
import data.*;
import maps.*;

//import java.io.FileNotFoundException;
//import java.util.zip.DataFormatException;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
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
		GUISimulator gui = new GUISimulator(largeur, hauteur, Color.BLACK);

		Simulateur simulateur = new Simulateur(gui, donnees);
	}

}

class Simulateur implements Simulable {
	/** L'interface graphique associée */
	private GUISimulator gui;

	private DonneesSimulation donnees;

	public Simulateur(GUISimulator pGui, DonneesSimulation pDonnees) {
		this.gui = pGui;
		gui.setSimulable(this);

		this.donnees = pDonnees;

		dessineCarte(donnees.getCarte());
	}

	private void dessineCarte(Carte pCarte) {
		int tailleCases = pCarte.getTailleCases() / 100; // Changement d'Echelle
		for (int i = 0; i < pCarte.getNbLignes(); i++) {
			for (int j = 0; j < pCarte.getNbColonnes(); j++) {
				dessineCase(tailleCases * j + 50, tailleCases * i + 50, pCarte.getCase(i,j), tailleCases);
			}
		}
	}

	private void dessineCase(int x, int y, Case pCase, int tailleCase) {
		NatureTerrain nature = pCase.getNature();
		Color couleur;
		switch (nature) {
			case EAU :
				couleur = Color.BLUE;
				break;	    
			case ROCHE :
				couleur = Color.RED;
				break;	    
			case FORET :
				couleur = Color.GREEN;
				break;	    
			case HABITAT :
				couleur = Color.CYAN;
			default :
				couleur = Color.WHITE;
				break;
		}
		gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, couleur, tailleCase));
		System.out.println("("+x+","+y+")");

	}

	@Override
	public void next() {
	}

	@Override
	public void restart() {
		gui.reset();
	}
}


