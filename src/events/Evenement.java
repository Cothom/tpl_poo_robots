package events;

/**
 * Classe abstraite qui permet d'implémenter un événement.
 */
public abstract class Evenement {
	private long date;

	public Evenement(long pDate) {
		if (pDate < 0)
			throw new IllegalArgumentException("Argument invalide : date négative.");
		this.date = pDate;
	}

	public long getDate() {
		return this.date;
	}

	/**
	 * Méthode abstraite qui effectuera une action différente en fonction du type de l'événement.
	 */
	public abstract void execute();
}

