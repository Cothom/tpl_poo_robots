package cpcc;

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
		this.sommets = new Sommet[pCarte.getNbLignes()][pCarte.getNbColonnes];
		for (int i = 0; i < pCarte.getNbLignes(); i++) {
			for (int j = 0; j < pCarte.getNbColonnes(); j++) {
				this.sommets[i][j] = new Sommet(pCarte.getCase(i, j), CalculChemin.tempsTraverse(pCarte.getCase(i, j), pRobot));
			}
		}
	}

	public static double tempsTraverse(Case c, Robot r) {
		return (double) this.carte.getTailleCases() / r.getVitesse(c.getNature());
	}

	public static int getDeltaX(Direction d) {
		switch (d) {
			case NORD :
			case SUD :
				return 0;
				break;
			case OUEST :
				return -1;
				break;
			case EST :
				return 1;
				break;
		}
	}

	public static int getDeltaY(Direction d) {
		switch (d) {
			case OUEST :
			case EST :
				return 0;
				break;
			case NORD :
				return -1;
				break;
			case SUD :
				return 1;
				break;
		}
	}

	private boolean tousMarques() {
		for (int i = 0; i < this.nbLignes(); i++) {
			for (int j = 0; j < this.nbColonnes(); j++) {
				if (!this.sommets[i][j].getEstMarque()) {
					return false;
				}
			}
		}
		return true;
	}

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
		for (Sommet v : s.getVoisins()) {
			if (s.getPoids() + s.getDistanceSource() < v.getDistanceSource()) {
				v.setDistanceSource(s.getPoids() + s.getDistanceSource());
				v.setVoisinVersSource(s);
			}
		}
	}

	private Chemin renverserChemin(Chemin c) {
		Chemin r = new Chemin(this.robot);
		len_chemin = c.getNbSommets();
		for (int i = 0; i < c.getNbSommets(); i++) {
			r.ajouterSommet(c.getSommet(c.getNbSommets() - i));
		}
		return r;
	}

	private Chemin cheminComplet(Sommet src, Sommet dest) {
		Chemin chemin = new Chemin(this.robot);
		Sommet tmp = dest;
		while (!tmp.getVoisinVersSource().equals(src)) {
			chemin.ajouterSommet(tmp);
			tmp = tmp.getVoisinVersSource();
		}
		return renverserChemin(chemin);
	}

	public Chemin dijkstra(Case src, Case dst) {
		Sommet source = this.sommets[src.getLigne()][src.getColonne()];
		Sommet destin = this.sommets[dst.getLigne()][dst.getColonne()];
		Sommet courant;
		source.setDistanceSource(0);
		
		while (!this.tousMarques()) {
			courant = this.sommetMin();
			courant.setEstMarque(true);
			this.majDistances(courant);
		}
		return cheminComplet(source, destin);
	}
}
