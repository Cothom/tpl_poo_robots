public class RobotChenilles extends Robot {

	private static double vitesseMaximale = 80;

	private double vitesseNormale;
	private double vitesseForet;

	public RobotChenilles() {
		super.position = new Case();
		super.vitesse = 60;
		super.volumeReservoir = 2000;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 300;
		super.volumeDeversUnitaire = 100;
		super.tempsDeversUnitaire = 8;

		this.vitesseNormale = super.vitesse;
		this.vitesseForet = super.vitesse / 2;
	}

	public RobotChenilles(Case pPosition) {
		super.position = pPosition;
		super.vitesse = 60;
		super.volumeReservoir = 2000;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 300;
		super.volumeDeversUnitaire = 100;
		super.tempsDeversUnitaire = 8;

		this.vitesseNormale = super.vitesse;
		this.vitesseForet = super.vitesse / 2;
	}

	public RobotChenilles(Case pPosition, double pVitesse) {
		super.position = pPosition;
		super.vitesse = (pVitesse < vitesseMaximale) ? pVitesse : vitesseMaximale;
		super.volumeReservoir = 2000;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 300;
		super.volumeDeversUnitaire = 100;
		super.tempsDeversUnitaire = 8;

		this.vitesseNormale = super.vitesse;
		this.vitesseForet = super.vitesse / 2;
	}

	public void setPosition(Case pCase) {
		if (super.position.estVoisin(pCase) && (pCase.getNature() != EAU && pCase.getNature() != ROCHER)) {
			super.position = pCase;
			if (pCase.getNature == FORET) super.vitesse = this.vitesseForet;
			else super.vitesse = this.vitesseNormale;
		} else
		throw new IllegalArgumentException("Argument invalide : la case spécifiée n'est pas voisine de la case actuelle ou le terrain n'est pas adapté à ce robot.");
	}

	public double getVitesse(NatureTerrain nature) {
		double vitesse;
		switch (nature) {
			case EAU :
			case ROCHER :
				vitesse = 0;
				break;
			case FORET :
				vitesse = this.vitesseForet;
				break;
			default :
				vitesse = this.vitesseNormale;
				break;
		}
		return vitesse;
	}

	public void deverserEau(int volume) {
		if (volume >= super.volumeDisponible)
			super.volumeDisponible = 0;
		else 
			super.volumeDisponible -= super.volumeDeversUnitaire;
	}

	public void remplirReservoir() {
		if (super.position.getNature() == EAU)
			super.volumeDisponible = super.volumeReservoir;
		else 
			throw new IllegalArgumentException("Impossible de remplir le reservoir : la case ne contient pas d'eau.");
	}

}
