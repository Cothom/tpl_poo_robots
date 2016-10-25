public enum NatureTerrain {
	EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT;
}

public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;

	public Case(int pLigne, int pColonne, NatureTerrain pNature) {
		this.ligne = pLigne;
		this.colonne = pColonne;
		this.nature = pNature;
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
}
