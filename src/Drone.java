public class Drone extends Robot {

	private static double vitesseMaximale = 150;

	public Drone(Case pCase) {
		super.position = pCase;
		super.vitesse = 100;
		super.volumeReservoir = 10000;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 1800;
		super.volumeDeversUnitaire = super.volumeReservoir;
		super.tempsDeversUnitaire = 30;
	}

	public Drone(Case pCase, double pVitesse) {
		super.position = pCase;
		super.vitesse = (pVitesse < vitesseMaximale) ? pVitesse : vitesseMaximale;
		super.volumeReservoir = 10000;
		super.volumeDisponible = super.volumeReservoir;
		super.tempsRemplissage = 1800;
		super.volumeDeversUnitaire = super.volumeReservoir;
		super.tempsDeversUnitaire = 30;
	}

	public void setPosition(Case pCase) {
		if (super.position.estVoisin(pCase))	
			super.position = pCase;
		else
			throw new IllegalArgumentException("Argument invalide : la case spécifiée n'est pas voisine de la case actuelle.");
	}

	public double getVitesse(NatureTerrain nature) {
		return super.vitesse;
	}

	public void deverserEau(int volume) {
		super.volumeDisponible = 0;
	}

	public void remplirReservoir() {
		if (super.position.getNature() == EAU)
			super.volumeDisponible = super.volumeReservoir;
		else 
			throw new IllegalArgumentException("Impossible de remplir le reservoir : la case ne contient pas d'eau.");
	}

}
