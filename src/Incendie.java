public class Incendie {

    private Case position;
	private int intensite;
	private int intensite_initiale;

	public Incendie(Case pPosition, int pIntensite) {
		if (pLigne < 0 || pColonne < 0) throw new IllegalArgumentException("Argument invalide : coordonnée négative.");
		if (pIntensite < 0) throw new IllegalArgumentException("Argument invalide : intensité négative.");
		this.position = pPosition;
		this.intensite_initiale = pIntensite;
		this.intensite = pIntensite;
	}

	public void eteindre(int nbLitres) {
		this.intensite = (nbLitres > this.intensite) ? 0 : this.intensite - nbLitres;
	}

	public boolean estEteint() {
		return (this.intensite == 0) ? true : false;
	}

	public int getIntensite() {
		return this.intensite;
	}

	public int getIntensiteInitiale() {
		return this.intensite_initiale;
	}

	public int getPosition() {
		return this.position;
	}
}
