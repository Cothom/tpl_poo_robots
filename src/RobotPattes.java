public class RobotPattes extends Robot {

	private double vitesseNormale;
	private double vitesseRocher;

	public RobotPattes() {
		super.position = new Case();
		super.vitesse = 30;
		super.volumeReservoir = 0;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 300;
		super.volumeDeversUnitaire = 10;
		super.tempsDeversUnitaire = 1;

		this.vitesseNormale = 30;
		this.vitesseRocher = 10;
	}

	public RobotPattes(Case pPosition) {
		super.position = pPosition;
		super.vitesse = 30;
		super.volumeReservoir = 0;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 300;
		super.volumeDeversUnitaire = 10;
		super.tempsDeversUnitaire = 1;

		this.vitesseNormale = 30;
		this.vitesseRocher = 10;

	}

	public void setPosition(Case pCase) {
		if (super.position.estVoisin(pCase) && (pCase.getNature() != EAU)) {
			super.position = pCase;
			if (pCase.getNature == ROCHER) super.vitesse = this.vitesseRocher;
			else super.vitesse = this.vitesseNormale;
		} else
		throw new IllegalArgumentException("Argument invalide : la case spécifiée n'est pas voisine de la case actuelle ou le terrain n'est pas adapté à ce robot.");
	}

	public double getVitesse(NatureTerrain nature) {
		double vitesse;
		switch (nature) {
			case EAU :
				vitesse = 0;
				break;
			case ROCHER :
				vitesse = this.vitesseRocher;
				break;
			default :
				vitesse = this.vitesseNormale;
				break;
		}
		return vitesse;
	}

	public void deverserEau(int volume) {
	}

	public void remplirReservoir() {
	}

}

