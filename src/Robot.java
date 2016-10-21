public abstract class Robot {

	protected Case position;
	protected double vitesse;
	protected int volumeReservoir;
	protected int volumeDisponibleReservoir;
	protected int tempsRemplissage;
	protected int volumeDeversUnitaire;
	protected int tempsDeversUnitaire;

	public abstract void setPosition(Case pCase);
	public abstract double getVitesse(NatureTerrain nature);
	public abstract void deverserEau(int volume);
	public abstract void remplirReservoir();

	public Case getPosition() {
		return this.position;
	}

	public double getVitesse() {
		return this.vitesse;
	}

	public int getVolumeReservoir() {
		return this.volumeReservoir;
	}

	public int getTempsRemplissage() {
		return this.tempsRemplissage;
	}

	public int getVolumeDeversUnitaire() {
		return this.volumeDeversUnitaire;
	}

	public int getTempsDeversUnitaire() {
		return this.tempsDeversUnitaire;
	}

}
	
