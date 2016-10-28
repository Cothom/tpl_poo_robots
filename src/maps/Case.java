package maps;

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
}
