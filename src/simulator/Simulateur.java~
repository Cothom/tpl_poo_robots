
import io.LecteurDonnees;
import data.*;
import events.*;
import maps.*;
import robots.*;
//import java.io.FileNotFoundException;
//import java.util.zip.DataFormatException;

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

	// Ajout de Tests de déplacement
	/*
	Deplacement d1 = new Deplacement(3, donnees.getRobots()[0], donnees.getCarte().getCase(2,3), donnees.getCarte());
	Deplacement d2 = new Deplacement(30, donnees.getRobots()[0], donnees.getCarte().getCase(1,3), donnees.getCarte());
        Deplacement d3 = new Deplacement(60, donnees.getRobots()[0], donnees.getCarte().getCase(0,3), donnees.getCarte());
	Deplacement d4 = new Deplacement(90, donnees.getRobots()[0], donnees.getCarte().getCase(0,2), donnees.getCarte());


	
	simulateur.ajouteEvenement(d1);
	simulateur.ajouteEvenement(d2);
	simulateur.ajouteEvenement(d3);
	simulateur.ajouteEvenement(d4); */



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


class Simulateur implements Simulable {
    /** L'interface graphique associée */
    private GUISimulator gui;

    private DonneesSimulation donnees;

    private long dateSimulation;
    private ArrayList Evenements;

    public Simulateur(GUISimulator pGui, DonneesSimulation pDonnees) {
	this.gui = pGui;
	gui.setSimulable(this);

	this.donnees = pDonnees;

	dessineCarte(donnees.getCarte());
	dessineIncendies(donnees.getIncendies());
	dessineRobots(donnees.getRobots());

	dateSimulation = 0;
	Evenements = new ArrayList();
    }

    private void dessineCarte(Carte pCarte) {
	int tailleCases = pCarte.getTailleCases() / 100; // Changement d'Echelle
	for (int i = 0; i < pCarte.getNbLignes(); i++) {
	    for (int j = 0; j < pCarte.getNbColonnes(); j++) {
		dessineCase(tailleCases * j + 75, tailleCases * i + 75, pCarte.getCase(i,j), tailleCases);
	    }
	}
    }

    private void dessineCase(int x, int y, Case pCase, int tailleCase) {
	NatureTerrain nature = pCase.getNature();
	Color couleur;
	switch (nature) {
	case EAU :
	    couleur = Color.decode("#0099ff");
	    break;	    
	case ROCHE :
	    couleur = Color.decode("#91541e");
	    break;	    
	case FORET :
	    couleur = Color.decode("#006600");
	    break;
	case TERRAIN_LIBRE :
	    couleur = Color.decode("#ffffcc");
	    break;
	case HABITAT :
	    couleur = Color.RED;
	    break;
	default :
	    couleur = Color.BLACK;
	    break;
	}
	gui.addGraphicalElement(new Rectangle(x, y, couleur, couleur, tailleCase));

    }

    private void dessineIncendies(Incendie[] pIncendies) {
	int nb_incendies = donnees.getIndiceIncendies(); // Voir si on garde cet attribut ou non avec le groupe.
	int x;
	int y;
	int tailleCases = donnees.getCarte().getTailleCases() / 100; // Changement d'Echelle
	for (int i = 0; i < nb_incendies; i++) {
	    if (!pIncendies[i].estEteint()) {
		x = tailleCases * pIncendies[i].getPosition().getColonne() + 75; //+ tailleCases / 8 ?;
		y = tailleCases * pIncendies[i].getPosition().getLigne() + 75; // + tailleCases / 8 ?;
		gui.addGraphicalElement(new Rectangle(x, y, Color.YELLOW, Color.YELLOW, tailleCases / 2));
	    }
	}
    }

    private void dessineRobots(Robot[] pRobots) {
	int nb_robots = donnees.getIndiceRobots(); // Voir si on garde cet attribut ou non avec le groupe.
	int x;
	int y;
	int tailleCases = donnees.getCarte().getTailleCases() / 100; // Changement d'Echelle
	for (int i = 0; i < nb_robots; i++) {
	    x = tailleCases * pRobots[i].getPosition().getColonne() + 75; //+ tailleCases / 8 ?;
	    y = tailleCases * pRobots[i].getPosition().getLigne() + 75; // + tailleCases / 8 ?;
	    gui.addGraphicalElement(new Rectangle(x, y, Color.BLACK, Color.BLACK, tailleCases / 4));
	}
    }

    public void ajouteEvenement(Evenement e) {
	long date = e.getDate();
	if (date < this.dateSimulation)
	    throw new IllegalArgumentException("Impossible d'ajouter un évènement antérieur à la date courante de simulation.");
	System.out.println(Evenements.size());
	if (Evenements.size() == 0) {
	    Evenements.add(e);
	    return;
	}

	int i = 0;
	while (i != Evenements.size() && ((Evenement) Evenements.get(i)).getDate() < date) {
	    i++;
	}
	Evenements.add(i, e);
    }
    
    private void incrementeDate() {
	dateSimulation++;
    }

    private Boolean simulationTerminee() {
	if (Evenements.size() != 0) 
	    return false;
	else
	    return true;
    }
    
    @Override
    public void next() {
	
	incrementeDate();
	System.out.println(dateSimulation);
	
	while (!simulationTerminee() && ((Evenement)  Evenements.get(0)).getDate() <= dateSimulation) {
	    ((Evenement)  Evenements.get(0)).execute();
	    Evenements.remove(0);
	}
	gui.reset();
	dessineCarte(donnees.getCarte());
	dessineIncendies(donnees.getIncendies());
	dessineRobots(donnees.getRobots());
		      	
    }

    @Override
    public void restart() {
	gui.reset();
    }
}


