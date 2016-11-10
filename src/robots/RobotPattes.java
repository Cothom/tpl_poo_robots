package robots;
 
import maps.*;

public class RobotPattes extends Robot {

	private double vitesseNormale;
	private double vitesseRocher;

	public RobotPattes(Case pPosition) {
		this.vitesse = 30;
		this.volumeReservoir = 0;
		this.volumeDisponible = this.volumeReservoir;
		this.tempsRemplissage = 300;
		this.volumeDeversUnitaire = 10;
		this.tempsDeversUnitaire = 1;

		this.vitesseNormale = 30;
		this.vitesseRocher = 10;

		this.setPosition(pPosition);

	}

	public void setPosition(Case pPosition) {
		if (pPosition.getNature() != NatureTerrain.EAU) {
			this.position = pPosition;
			if (pPosition.getNature() == NatureTerrain.ROCHE)
				this.vitesse = this.vitesseRocher;
			else
				this.vitesse = this.vitesseNormale;
		} else {
			throw new IllegalArgumentException("Argument invalide : la case spécifiée n'est pas voisine de la case actuelle ou le terrain n'est pas adapté à ce robot.");
		}
	}

	public double getVitesse(NatureTerrain nature) {
		double vitesse;
		switch (nature) {
			case EAU :
				vitesse = 0;
				break;
			case ROCHE :
				vitesse = this.vitesseRocher;
				break;
			default :
				vitesse = this.vitesseNormale;
				break;
		}
		return vitesse;
	}

	public void deverserEau() {
	    if (this.volumeDisponible == 0) 
		throw new IllegalArgumentException("Impossible de deverser de l'eau : réservoir vide.");
	    else  
		this.volumeDisponible -= this.volumeDeversUnitaire;
	}

	public void remplirReservoir() {
	}

    @Override
    public String toString() {
	return "Pattes";
    }
}

