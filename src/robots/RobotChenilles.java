package robots;
 
import maps.*;

public class RobotChenilles extends Robot {

	private static double vitesseMaximale = 80;

	private double vitesseNormale;
	private double vitesseForet;

	public RobotChenilles(Case pPosition) {
	    this(pPosition, 60);
	}

	public RobotChenilles(Case pPosition, double pVitesse) {
		this.vitesse = (pVitesse < vitesseMaximale) ? pVitesse : vitesseMaximale;
		this.volumeReservoir = 2000;
		this.volumeDisponible = this.volumeReservoir;
		this.tempsRemplissage = 300;
		this.volumeDeversUnitaire = 100;
		this.tempsDeversUnitaire = 8;

		this.vitesseNormale = this.vitesse;
		this.vitesseForet = this.vitesse / 2;

		this.setPosition(pPosition);
	}

	public void setPosition(Case pPosition) {
		if (pPosition.getNature() != NatureTerrain.EAU && pPosition.getNature() != NatureTerrain.ROCHE) {
			this.position = pPosition;
			if (pPosition.getNature() == NatureTerrain.FORET)
				this.vitesse = this.vitesseForet;
			else
				this.vitesse = this.vitesseNormale;
		} else {
			throw new IllegalArgumentException("Argument invalide : le terrain n'est pas adapté à ce robot.");
		}
	}

	public double getVitesse(NatureTerrain nature) {
		double vitesse;
		switch (nature) {
			case EAU :
				vitesse = 0;
				break;
			case ROCHE :
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

	public void deverserEau() {
	    if (this.volumeDisponible == 0) 
		throw new IllegalArgumentException("Impossible de deverser de l'eau : réservoir vide.");
	     else  
		 this.volumeDisponible -= this.volumeDeversUnitaire;
	    
	}

	public void remplirReservoir() {
		if (this.position.getNature() == NatureTerrain.EAU)
			this.volumeDisponible = this.volumeReservoir;
		else 
			throw new IllegalArgumentException("Impossible de remplir le reservoir : la case ne contient pas d'eau.");
	}

    @Override
    public String toString() {
	return "Chenille";
    }

}
