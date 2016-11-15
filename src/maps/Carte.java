package maps;

import cpcc.*;

public class Carte {

	private Case[][] matrice;
	private int nbLignes;
	private int nbColonnes;
	private int tailleCases;

	public Carte(int pNbLignes, int pNbColonnes, int pTaille) {
		if (pNbLignes < 1 || pNbColonnes < 1)
			throw new IllegalArgumentException("Argument invalide : taille de carte négative ou nulle.");
		if (pTaille < 0)
			throw new IllegalArgumentException("Argument invalide : taille de case négative.");
		this.tailleCases = pTaille;
		this.nbLignes = pNbLignes;
		this.nbColonnes = pNbColonnes;
		this.matrice = new Case[pNbLignes][pNbColonnes];
		for (int i = 0; i < pNbLignes; i++) {
			for (int j = 0; j < pNbColonnes; j++) {
				this.matrice[i][j] = new Case(i, j, NatureTerrain.valueOf("TERRAIN_LIBRE"));
			}
		}
	}

	public void Evenement(long date) {
	}

	public int getNbLignes() {
		return this.nbLignes;
	}

	public int getNbColonnes() {
		return this.nbColonnes;
	}

	public int getTailleCases() {
		return this.tailleCases;
	}

	public Case getCase(int ligne, int colonne) {
		return this.matrice[ligne][colonne];
	}

	public boolean voisinExiste(Case src, Direction dir) {
		int dl = CalculChemin.getDeltaL(dir);
		int dc = CalculChemin.getDeltaC(dir);
		int l = src.getLigne();
		int c = src.getColonne();
		//System.out.println("(" + c + ", " + l + ") , Direction : " + dir.toString() + "  (" + dx + ", " + dy + ")");
		boolean r = true;
		if (l + dl < 0 || l + dl >= this.nbLignes)   r = false;
		if (c + dc < 0 || c + dc >= this.nbColonnes) r = false;
		//System.out.println(" " + ((r) ? "true\n\n" : "false\n\n"));
		return r;
	}

	public Case getVoisin(Case src, Direction dir) {
		Case r;
		if (!voisinExiste(src, dir)) throw new IllegalArgumentException("Argument invalide : cette case n'a pas de voisin dans cette direction.");
		switch (dir) {
			case NORD :
				r = this.matrice[src.getLigne() - 1][src.getColonne()];
				break;
			case SUD :
				r = this.matrice[src.getLigne() + 1][src.getColonne()];
				break;
			case OUEST :
				r = this.matrice[src.getLigne()][src.getColonne() - 1];
				break;
			case EST :
				r = this.matrice[src.getLigne()][src.getColonne() + 1];
				break;
			default :
				throw new IllegalArgumentException("Argument invalide : cette direction n'existe pas.");
		}
		return r;
	}

	public void setNatureCase(int ligne, int colonne, NatureTerrain nature) {
		this.matrice[ligne][colonne].setNature(nature);
	}
}
