package robots;

import maps.*;
 
public abstract class Robot {

    protected Case position;
    protected double vitesse;
    protected int volumeReservoir;
    protected int volumeDisponible;
    protected int tempsRemplissage;
    protected int volumeDeversUnitaire;
    protected int tempsDeversUnitaire;

    public abstract void setPosition(Case pCase);
    public abstract double getVitesse(NatureTerrain nature);
    public abstract void deverserEau();
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

    public int getVolumeDisponible() {
	return this.volumeDisponible;
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
	
