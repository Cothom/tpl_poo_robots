package simulator;

import io.LecteurDonnees;
import chef.*;
import data.*;
import events.*;
import maps.*;
import robots.*;


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


public class Simulateur implements Simulable {
	private GUISimulator gui;

	private DonneesSimulation donnees;
	private DonneesSimulation donneesInitiales;

	private long dateSimulation;
	private ArrayList Evenements;

        private ChefPompier chefPompier;

	public Simulateur(GUISimulator pGui, DonneesSimulation pDonnees) {
		this.gui = pGui;
		gui.setSimulable(this);

		this.donnees = pDonnees;
		this.donneesInitiales = pDonnees;

		for (int i = 0; i < this.donnees.getIndiceRobots(); i++) {
			this.donnees.getRobot(i).setSimulateur(this);
		}

		draw();
		dateSimulation = 0;
		Evenements = new ArrayList();

		chefPompier = new ChefPompier(donnees);
	}

        public long getDateSimulation() {
	    return this.dateSimulation;
	}

	private void draw() {
		dessineCarte();
		dessineIncendies();
		dessineRobots();
	}

	private void dessineCarte() {
		Carte carte = donnees.getCarte();

		/* Changement d'échelle */
		int a = gui.getPanelWidth() / carte.getNbColonnes();
		int b = gui.getPanelHeight() / carte.getNbLignes();
		int tailleCases = (a < b) ? a : b;

		/* Calcul du décalage */
		int dx = (a < b) ? 0 : (gui.getPanelWidth() - carte.getNbColonnes() * tailleCases) / 2;
		int dy = (a < b) ? (gui.getPanelHeight() - carte.getNbLignes() * tailleCases) / 2 : 0;


		for (int i = 0; i < carte.getNbLignes(); i++) {
			for (int j = 0; j < carte.getNbColonnes(); j++) {
				dessineCase(tailleCases * j + dx,  tailleCases * i + dy, carte.getCase(i,j), tailleCases);
			}
		}
	}

	private void dessineCase(int x, int y, Case pCase, int tailleCase) {

		NatureTerrain nature = pCase.getNature();	
		String cheminImage = System.getProperty("user.dir") + "/src/images/";	

		switch (nature) {
			case EAU :
				cheminImage += "water.png";
				break;	    
			case ROCHE :
				cheminImage += "rock.png";
				break;	    
			case FORET :
				cheminImage += "woods.png";
				break;
			case TERRAIN_LIBRE :
				cheminImage += "free.png";
				break;
			case HABITAT :
				cheminImage += "home.png";
				break;
			default :
				break;
		}

		gui.addGraphicalElement(new ImageElement(x, y, cheminImage, tailleCase, tailleCase, null)); // Ajout controle chargement image ?


	}

	private void dessineIncendies() {
		Incendie[] incendies = donnees.getIncendies();
		int nb_incendies = donnees.getIndiceIncendies(); // Voir si on garde cet attribut ou non avec le groupe.

		/* Changement d'échelle */	
		int a = gui.getPanelWidth() / donnees.getCarte().getNbColonnes();
		int b = gui.getPanelHeight() / donnees.getCarte().getNbLignes();
		int tailleCases = (a < b) ? a : b;	

		int x;
		int y;

		/* Calcul du décalage */
		int dx = (a < b) ? 0 : (gui.getPanelWidth() - donnees.getCarte().getNbColonnes() * tailleCases) / 2;
		int dy = (a < b) ? (gui.getPanelHeight() - donnees.getCarte().getNbLignes() * tailleCases) / 2 : 0;

		String cheminImage = System.getProperty("user.dir") + "/src/images/fire.png";

		for (int i = 0; i < nb_incendies; i++) {
			if (!incendies[i].estEteint()) {
				x = tailleCases * incendies[i].getPosition().getColonne();
				y = tailleCases * incendies[i].getPosition().getLigne();
				gui.addGraphicalElement(new ImageElement(x + dx, y + dy, cheminImage, tailleCases, tailleCases, null));
				//gui.addGraphicalElement(new Rectangle(x, y, Color.YELLOW, Color.YELLOW, tailleCases / 2));
			}
		}
	}

//	String cheminImage = System.getProperty("user.dir") + "/src/images/fire.png";
//	//int numero_image = ((int) dateSimulation % 8) + 1;
//	//String cheminImage = System.getProperty("user.dir") + "/src/images/fire/fire" + String.valueOf(numero_image) + ".png";




    private void dessineRobots() {
	Robot[] robots = donnees.getRobots();

	int nb_robots = donnees.getIndiceRobots(); // Voir si on garde cet attribut ou non avec le groupe.

	/* Changement d'échelle */
	int a = gui.getPanelWidth() / donnees.getCarte().getNbColonnes();
	int b = gui.getPanelHeight() / donnees.getCarte().getNbLignes();
	int tailleCases = (a < b) ? a : b;

	int x;
	int y;	
	/* Calcul du décalage */
	int dx = (a < b) ? 0 : (gui.getPanelWidth() - donnees.getCarte().getNbColonnes() * tailleCases) / 2;
	int dy = (a < b) ? (gui.getPanelHeight() - donnees.getCarte().getNbLignes() * tailleCases) / 2 : 0;

	String cheminDossierImages = System.getProperty("user.dir") + "/src/images/";
	String cheminImage;
	String typeRobot;

	for (int i = 0; i < nb_robots; i++) {
	    x = tailleCases * robots[i].getPosition().getColonne() + tailleCases / 4;
	    y = tailleCases * robots[i].getPosition().getLigne() + tailleCases / 4;

	    typeRobot = robots[i].toString();
	    switch (typeRobot) { // Autres robots a Ajouter
	    case "Drone" :
		cheminImage = cheminDossierImages + "drone.png";
		break;
	    case "Roues" :
		cheminImage = cheminDossierImages + "roues.png";
		break;
	    default :
		cheminImage = cheminDossierImages + "roues.png";
		break;
	    }
	    gui.addGraphicalElement(new ImageElement(x + dx, y + dy, cheminImage, tailleCases / 2, tailleCases / 2, null));
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

		if (dateSimulation%10 == 0) {
		    chefPompier.afficheIncendies();
		}
		while (!simulationTerminee() && ((Evenement)  Evenements.get(0)).getDate() <= dateSimulation) {
			((Evenement)  Evenements.get(0)).execute();
			Evenements.remove(0);
		}
		gui.reset();
		draw();	      	
	}

	@Override
	public void restart() {
	}

	public DonneesSimulation getDonnees() {
		return this.donnees;
	}
}


