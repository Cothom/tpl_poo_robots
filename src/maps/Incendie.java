package maps;

public class Incendie {

	private Case position;
	private int intensite;
	private int intensite_initiale;

	public Incendie(Case pPosition, int pIntensite) {
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

	public Case getPosition() {
		return this.position;
	}
}
