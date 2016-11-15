package maps;

/**
 * Classe qui permet de socker les informations relatives a un incendie.
 */
public class Incendie {

	private Case position;
	private int intensite;
	private int intensite_initiale;

	public Incendie(Case pPosition, int pIntensite) {
		if (pPosition.getLigne() < 0 || pPosition.getColonne() < 0) throw new IllegalArgumentException("Argument invalide : coordonnée négative.");
		if (pIntensite < 0) throw new IllegalArgumentException("Argument invalide : intensité négative.");

		this.position = pPosition;
		this.intensite_initiale = pIntensite;
		this.intensite = pIntensite;
	}

	/**
	 * Permet de décrémenter l'intensite de l'incendie.
	 * @param nbLitres
	 */
	public void eteindre(int nbLitres) {
		this.intensite = (nbLitres > this.intensite) ? 0 : this.intensite - nbLitres;
	}

	/**
	 * Permet de savoir si un incendie est éteint ou non.
	 */
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
