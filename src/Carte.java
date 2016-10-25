public enum Direction {
	NORD, SUD, OUEST, EST;
}

public class Carte {

	private int[][] matrice;
	private int tailleCases;

	public Carte(int pNbLignes, int pNbColonnes, int pTaille) {
		if (pTaille < 0) throw new IllegalArgumentException("Argument invalide : taille de case négative.");
		if (pNbLignes < 0 | pNbColonnes < 0) throw new IllegalArgumentException("Argument invalide : dimensions de la carte négatives.");
		this.matrice = new int[pNbLignes][pNbColonnes];
		this.tailleCases = pTaille;
	}

	public void Evenement(long date) {
	}

	public int getNbLignes() {
		return 0;
	}

	public int getNbColonnes() {
		return 0;
	}

	public int getTailleCases() {
		return this.tailleCases;
	}

	public Case getCase(int ligne, int colonne) {
		return new Case();
	}

	public boolean voisinExiste(Case src, Direction dir) {
		return false;
	}

	public Case getVoisin(Case src, Direction dir) {
		return new Case();
	}
}
