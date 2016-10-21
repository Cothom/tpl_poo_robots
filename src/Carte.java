public class Carte {

	private int tailleCases;

	public Carte() {
		this.tailleCases = 0;
	}

	public Carte(int pTaille) {
		if (pTaille < 0) throw new IllegalArgumentException("Argument invalide : taille de case négative.");
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
