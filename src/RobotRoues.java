public class RobotRoues extends Robot {

	public RobotRoues() {
		super.position = new Case();
		super.vitesse = 80;
		super.volumeReservoir = 5000;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 600;
		super.volumeDeversUnitaire = 100;
		super.tempsDeversUnitaire = 5;
	}

	public RobotRoues(Case pCase) {
		super.position = pCase;
		super.vitesse = 80;
		super.volumeReservoir = 5000;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 600;
		super.volumeDeversUnitaire = 100;
		super.tempsDeversUnitaire = 5;
	}

	public RobotRoues(Case pCase, double pVitesse) {
		super.position = pCase;;
		super.vitesse = pVitesse;
		super.volumeReservoir = 5000;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 600;
		super.volumeDeversUnitaire = 100;
		super.tempsDeversUnitaire = 5;
	}

	public void setPosition(Case pCase) {
		if (super.position.estVoisin(pCase) && (pCase.getNature() == TERRAIN_LIBRE || pCase.getNature() == HABITAT))	
			super.position = pCase;
		else
			throw new IllegalArgumentException("Argument invalide : la case spécifiée n'est pas voisine de la case actuelle ou le terrain n'est pas adapté à ce robot.");
	}

	public double getVitesse(NatureTerrain nature) {
		if (nature != TERRAIN_LIBRE && nature != HABITAT)
			return 0;
		else
			return super.vitesse;
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
