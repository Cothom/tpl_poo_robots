package cpcc;

import java.util.Collections;

import robots.*;
import maps.*;

public class CalculChemin {

	private int nbLignes;
	private int nbColonnes;
	private Robot robot;
	private Carte carte;
	private Sommet[][] sommets;

	public CalculChemin(Carte pCarte, Robot pRobot) {
		this.robot = pRobot;
		this.carte = pCarte;
		this.nbLignes = pCarte.getNbLignes();
		this.nbColonnes = pCarte.getNbColonnes();
		this.sommets = new Sommet[pCarte.getNbLignes()][pCarte.getNbColonnes()];
		for (int i = 0; i < pCarte.getNbLignes(); i++) {
			for (int j = 0; j < pCarte.getNbColonnes(); j++) {
				this.sommets[i][j] = new Sommet(this, pCarte.getCase(i, j), CalculChemin.tempsTraverse(pCarte, pCarte.getCase(i, j), pRobot));
			}
		}
		for (int i = 0; i < pCarte.getNbLignes(); i++) {
			for (int j = 0; j < pCarte.getNbColonnes(); j++) {
				this.sommets[i][j].ajouterVoisins();
			}
		}
	}

	public static double tempsTraverse(Carte pCarte, Case c, Robot r) {
		return (double) pCarte.getTailleCases() / r.getVitesse(c.getNature());
	}

	public static int getDeltaC(Direction d) {
		if (d == Direction.NORD || d == Direction.SUD) return 0;
		if (d == Direction.OUEST) return -1;
		if (d == Direction.EST) return 1;
		return 0;
	}

	public static int getDeltaL(Direction d) {
		if (d == Direction.OUEST || d == Direction.EST) return 0;
		if (d == Direction.NORD) return -1;
		if (d == Direction.SUD) return 1;
		return 0;
	}

	public Sommet getSommet(int i, int j) {
		if (i < 0 || j < 0)
			throw new IllegalArgumentException("Argument invalide: coordonnée négative.");
		if (i > this.carte.getNbLignes() || j > this.carte.getNbColonnes())
			throw new IllegalArgumentException("Argument invalide: coordonnée hors de la carte.");
		return this.sommets[i][j];
	}

	public Carte getCarte() {
		return this.carte;
	}

//	private boolean tousMarques() {
//		for (int i = 0; i < this.nbLignes; i++) {
//			for (int j = 0; j < this.nbColonnes; j++) {
//				if (!this.sommets[i][j].getEstMarque()) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}

	private Sommet sommetMin() {
		int x = 0;
		int y = 0;
		double min = Double.POSITIVE_INFINITY;
		for (int i = 0; i < this.nbLignes; i++) {
			for (int j = 0; j < this.nbColonnes; j++) {
				if (this.sommets[i][j].getDistanceSource() < min && !this.sommets[i][j].getEstMarque()) {
					min = this.sommets[i][j].getDistanceSource();
					x = i;
					y = j;
				}
			}
		}
		return this.sommets[x][y];
	}

	private void majDistances(Sommet s) {
//		Sommet v;
//		for (int i = 0; i < s.getNbVoisins(); i++) {
//			v = s.getVoisin(i);
		for(Sommet v : s.getVoisins()) {
			if (s.getPoids() + s.getDistanceSource() < v.getDistanceSource()) {
				v.setDistanceSource(s.getPoids() + s.getDistanceSource());
				v.setVoisinVersSource(s);
			}
		}
	}

	private Chemin renverserChemin(Chemin c) {
		Chemin r = new Chemin(this.carte, this.robot);
		for (int i = c.getNbSommets()-1; i >= 0; i--) {
			r.ajouterSommet(c.getSommet(i));
		}
		return r;
	}

	private Chemin cheminComplet(Sommet src, Sommet dest) {
		Chemin chemin = new Chemin(this.carte, this.robot);
		Sommet tmp = dest;
		while (!tmp.getVoisinVersSource().equals(src)) {
			chemin.ajouterSommet(tmp);
			tmp = tmp.getVoisinVersSource();
		}
		Collections.reverse(chemin.getTabSommets());
		return chemin;
	}

	public Chemin dijkstra(Case src, Case dst) {
		Sommet source = this.sommets[src.getLigne()][src.getColonne()];
		Sommet destin = this.sommets[dst.getLigne()][dst.getColonne()];
		int xDestin = destin.getCase().getColonne();
		int yDestin = destin.getCase().getLigne();
		Sommet courant = source;
		source.setDistanceSource(0);
		
//		while (!this.tousMarques()) {
		while (!(courant.getCase().getColonne() == xDestin &&
				 courant.getCase().getLigne()   == yDestin)) {
			courant = this.sommetMin();
			courant.setEstMarque(true);
			this.majDistances(courant);
		}
		return cheminComplet(source, destin);
	}

	public void afficherChemin(Chemin c) {
		System.out.println("Chemin le plus court entre "+ c.getSommet(0).getCase().getLigne() + ", " + c.getSommet(0).getCase().getColonne() + " et " + c.getSommet(c.getNbSommets()-1).getCase().getLigne() + ", " + c.getSommet(c.getNbSommets()-1).getCase().getColonne() + " pour le robot " + this.robot.toString());
		for (int i = 0; i < c.getNbSommets(); i++) {
			System.out.print("(" + c.getSommet(i).getCase().getLigne() + ", " + c.getSommet(i).getCase().getColonne() + ") ");
		}
		System.out.println();
	}
}
