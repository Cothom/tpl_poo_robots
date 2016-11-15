package maps;

import robots.Robot;

/**
 * Classe qui permet de stocker les informations relatives a une case.
 */
public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;

	public Case(int pLigne, int pColonne, NatureTerrain pNature) {
		this.ligne = pLigne;
		this.colonne = pColonne;
		this.nature = pNature;
	}

	public Case(Case pCase) {
		this.ligne = pCase.getLigne();
		this.colonne = pCase.getColonne();
		this.nature = pCase.getNature();
	}

	public int getLigne() {
		return this.ligne;
	}

	public int getColonne() {
		return this.colonne;
	}

	public NatureTerrain getNature() {
		return this.nature;
	}

	public void setNature(NatureTerrain pNature) {
		this.nature = pNature;
	}

	/**
	 * Permet de savoir si une case est accessible par un robot.
	 * @param r
	 * @return
	 */
	public boolean estAccessible(Robot r) {
		return (r.getVitesse(this.nature) > 0) ? true : false;
	}

    public String toString() {
        return "(" + this.ligne + ", " + this.colonne + ")";
    }
}
