package robots;

import java.util.Vector;

import chef.*;
import cpcc.*;
import events.*;
import maps.*;
import simulator.*;

public abstract class Robot {

    protected CalculChemin cc;
    protected Vector cheminsIncendies;
    protected Vector cheminsRecharge;
    protected Vector incendiesInaccessibles;
    protected Vector rechargesInaccessibles;
	protected Simulateur simulateur;
	protected Case position;
	protected double vitesse;
	protected int volumeReservoir;
	protected int volumeDisponible;
	protected int tempsRemplissage;
	protected int volumeDeversUnitaire;
	protected int tempsDeversUnitaire;
	protected EtatRobot etatRobot = EtatRobot.LIBRE; // ? Rana
	protected ChefPompier chefPompier;
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

	public void setSimulateur(Simulateur s) {
		this.simulateur = s;
        this.cc.setCarte(s.getCarte());
	}

	public EtatRobot getEtatRobot() {
		return this.etatRobot;
	}

	public void setEtatRobot(EtatRobot etat) {
		this.etatRobot = etat;
//        if (etat == EtatRobot.LIBRE)
//            this.trouveCheminsVersIncendies();
//        else if (etat == EtatRobot.ETEINDRE)
//            this.trouveCheminsVersRecharge();
	}

    public Chemin dijkstra(Case dst) {
        Chemin c = this.cc.dijkstra(this.position, dst);
        this.cc.reinitSommets(this.simulateur.getCarte());
        return c;
    }

    public boolean estInaccessible(Incendie incendie) {
        for (int i = 0; i < this.cheminsIncendies.size(); i++) {
            if (((Incendie)this.chefPompier.getIncendie(i)).getPosition() == incendie.getPosition()) {
                if (((Chemin) this.cheminsIncendies.get(i)).getTempsParcours() == Double.POSITIVE_INFINITY) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean estInaccessible(Case c) {
        for (int i = 0; i < this.cheminsRecharge.size(); i++) {
            if ((Case) this.chefPompier.getPointEau(i) == c) {
                if (((Chemin) this.cheminsRecharge.get(i)).getTempsParcours() == Double.POSITIVE_INFINITY) {
                    return true;
                }
            }
        }
        return (this.dijkstra(c).getTempsParcours() == Double.POSITIVE_INFINITY) ? true : false;
    }

    public Incendie getPlusProcheIncendie() {
        double tempsMin = Double.POSITIVE_INFINITY;
        int indicePPI = 0;
        for (int i = 0; i < this.cheminsIncendies.size(); i++) {
            if (((Chemin) this.cheminsIncendies.get(i)).getTempsParcours() < tempsMin) {
                tempsMin = ((Chemin) this.cheminsIncendies.get(i)).getTempsParcours();
                indicePPI = i;
            }
        }
        return this.chefPompier.getIncendie(indicePPI);
    }

    public Case getPlusProcheRecharge() {
        double tempsMin = Double.POSITIVE_INFINITY;
        int indicePPR = 0;
        for (int i = 0; i < this.cheminsRecharge.size(); i++) {
            if (((Chemin) this.cheminsRecharge.get(i)).getTempsParcours() < tempsMin) {
                tempsMin = ((Chemin) this.cheminsRecharge.get(i)).getTempsParcours();
                indicePPR = i;
            }
        }
        return this.chefPompier.getPointEau(indicePPR);
    }

    public void trouveCheminsVersIncendies() {
        //System.out.println("\n\nDebut trouveCheminVersIncendies");
        this.incendiesInaccessibles.clear();
        if (this.toString() == "Drone") {
            //System.out.println("Cas : Drone");
            for (int i = 0; i < this.chefPompier.getIncendies().size(); i++) {
                //System.out.println("\nIteration : " + i);
                this.cheminsIncendies.add(this.dijkstra(this.chefPompier.getIncendie(i).getPosition()));
            }
            //System.out.println("Fin Cas : Drone");
        } else {
            //System.out.println("Cas : Autres");
            Incendie iCourant;
//            Chemin cCourant;
//            Chemin pcChemin;
//            double tempsMin = Double.POSITIVE_INFINITY;
            PlusProcheObjet ppIncendie;
            for (int i = 0; i < this.chefPompier.getIncendies().size(); i++) {
                iCourant = (Incendie) this.chefPompier.getIncendie(i);
                ppIncendie = this.caseLaPlusProcheAutour(iCourant.getPosition(), this.chefPompier.getCarte());
//                for (Direction d : Direction.values()) {
//                    cCourant = this.dijkstra(this.chefPompier.getCarte().getVoisin(iCourant.getPosition()));
//                    if (cCourant.getTempsParcours() == Double.POSITIVE_INFINITY)
//                        this.incendiesInaccessibles.add(iCourant);
//                    if (cCourant.getTempsParcours() < tempsMin) {
//                        tempsMin = cCourant.getTempsParcours();
//                        pcChemin = cCourant;
//                    }
//                }
                this.cheminsIncendies.add(ppIncendie.getChemin());
            }
        }
        //System.out.println("Fin trouveCheminVersIncendies");
    }

    public void trouveCheminsVersRecharge() {
        this.rechargesInaccessibles.clear();
        if (this.toString() == "Drone") {
            for (int i = 0; i < this.chefPompier.getPointsEau().size(); i++) {
                this.cheminsRecharge.add(this.dijkstra(this.chefPompier.getPointEau(i)));
            }
        } else {
            Case ptCourant;
//            Chemin cCourant;
//            Chemin pcChemin;
            PlusProcheObjet ppRecharge;
            for (int i = 0; i < this.chefPompier.getPointsEau().size(); i++) {
                ptCourant = (Case) this.chefPompier.getPointEau(i);
                ppRecharge = this.caseLaPlusProcheAutour(ptCourant, this.chefPompier.getCarte());
//                for (Direction d : Direction.values()) {
//                    cCourant = this.dijkstra(this.chefPompier.getCarte().getVoisin(ptCourant));
//                    if (cCourant.getTempsParcours() == Double.POSITIVE_INFINITY)
//                        this.rechargesInaccessibles.add(ptCourant);
//                    if (cCourant.getTempsParcours() < tempsMin) {
//                        tempsMin = cCourant.getTempsParcours();
//                        pcChemin = cCourant;
//                    }
//                }
                this.cheminsRecharge.add(ppRecharge.getChemin());
            }
        }
    }

	public void setChefPompier(ChefPompier chefPompier) {
		this.chefPompier = chefPompier;
//        this.trouveCheminsVersIncendies();
//        this.trouveCheminsVersRecharge();
	}

	public void ajouteIncendiesNonAffectes(Incendie incendie) {
		chefPompier.getIncendiesNonAffectes().add(incendie);
	}

	public PlusProcheObjet caseLaPlusProcheAutour(Case dest, Carte carte) {
		Case voisin;
		Case casePlusProche = dest;
		Chemin chemin;
        Chemin pcChemin = new Chemin(carte, this);
		double temps = Double.POSITIVE_INFINITY;

		for (Direction d : Direction.values()) {

			if (carte.voisinExiste(dest, d)) {
				voisin = carte.getCase(dest.getLigne() + this.cc.getDeltaL(d), dest.getColonne() + this.cc.getDeltaC(d));
				chemin = this.dijkstra(voisin);

				if (chemin.getTempsParcours() < temps) {		  
					temps = chemin.getTempsParcours();
                    pcChemin = chemin;
					casePlusProche = voisin;
				}			    
			}
		}

		return new PlusProcheObjet(casePlusProche, pcChemin);

	}

	public void ajouteDeplacementsVersDest(Case dest, Carte carte) {
		/*
		   - requiert Etat du robot Libre ou Reservoir vide
		   */

		if (this.getEtatRobot() != EtatRobot.LIBRE && this.getEtatRobot() != EtatRobot.RESERVOIR_VIDE) { return; }
		CalculChemin cc = new CalculChemin(carte, this);
		Chemin chemin = cc.dijkstra(this.getPosition(), dest);

		if (chemin.getTempsParcours() == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException("Ce robot (" + this.toString() + ") ne peut pas se rendre sur cette case.");
		}

		long dateEvenement = simulateur.getDateSimulation();
		for (int i=1; i < chemin.getNbSommets(); i++) {
			dateEvenement += (long) chemin.getSommet(i).getTempsTraverse();
			simulateur.ajouteEvenement(new Deplacement(dateEvenement, this, chemin.getSommet(i).getCase(), carte));		    
		}

		this.etatRobot = EtatRobot.DEPLACEMENT;
		simulateur.ajouteEvenement(new Etat(dateEvenement, this, EtatRobot.LIBRE));
	}

	public void eteindreIncendie(long date, Incendie incendie, Carte carte) {

		System.out.println("Avant eteindre " + this.toString());
		simulateur.ajouteEvenement(new Etat(simulateur.getDateSimulation() + date, this, EtatRobot.ETEINDRE));
		int intensite = incendie.getIntensite();
		int nbDeversUnitaire;
		if (this.toString() == "Drone") {
			nbDeversUnitaire = 1;
		} else if (this.toString() == "Pattes") {
			nbDeversUnitaire = intensite / volumeDeversUnitaire;
		} else {
			nbDeversUnitaire = (intensite > this.volumeDisponible) ? (volumeDisponible / volumeDeversUnitaire) : (intensite / volumeDeversUnitaire);
		}

		System.out.println(" Test  eteindre " + this.toString() + " nbDeversUnitaire " + nbDeversUnitaire );
		System.out.println("Intensite " + incendie.getIntensite() + "volumeDeversUnitaire " + volumeDeversUnitaire + " volumeDisponible " + this.volumeDisponible);

		simulateur.ajouteEvenement(new Etat(simulateur.getDateSimulation() + date + (nbDeversUnitaire)*tempsDeversUnitaire, this, EtatRobot.LIBRE));
		System.out.println(" Test  avant boucle " + this.toString());
		for (int i=1; i <= nbDeversUnitaire; i++) {
			simulateur.ajouteEvenement(new Eteindre(simulateur.getDateSimulation() + date + i*tempsDeversUnitaire, this, incendie, carte));
		}
		System.out.println("Apres eteindre " + this.toString());


	}

	public void seRecharger(Carte carte) {

		Case caseRechargement = (this.toString() == "Drone") ? (Case) chefPompier.getPointsEau().get(0) : (Case) this.caseLaPlusProcheAutour((Case) chefPompier.getPointsEau().get(0), carte).getObjet();
		Case caseDest;
		CalculChemin cc = new CalculChemin(carte, this);
		Chemin chemin;
		Chemin cheminVersDest = cc.dijkstra(this.position, (Case) chefPompier.getPointsEau().get(0));
		double temps = Double.POSITIVE_INFINITY;

		for (int i=1; i < chefPompier.getPointsEau().size(); i++) {
			cc = new CalculChemin(carte, this);
			caseDest = (this.toString() == "Drone") ? (Case) chefPompier.getPointsEau().get(i) : (Case) this.caseLaPlusProcheAutour((Case) chefPompier.getPointsEau().get(i), carte).getObjet();
			chemin = cc.dijkstra(this.position, caseDest);

			if (chemin.getTempsParcours() < temps) {	  
				temps = chemin.getTempsParcours();
				caseRechargement = caseDest;
				cheminVersDest = chemin;
			}
		}

		this.ajouteDeplacementsVersDest(caseRechargement, carte);
		//cc.afficherChemin(cheminVersDest);

		long dateRechargement = simulateur.getDateSimulation();

		for (int i=1; i < cheminVersDest.getNbSommets(); i++) {
			dateRechargement += (long) cheminVersDest.getSommet(i).getTempsTraverse();
		}

		simulateur.ajouteEvenement(new Etat(dateRechargement, this, EtatRobot.RECHARGEMENT));
		simulateur.ajouteEvenement(new Recharger(dateRechargement + tempsRemplissage, this, carte));
	}


	public boolean estOccupe() {
		return (this.etatRobot == EtatRobot.LIBRE) ? false : true; 
	}


	public boolean cheminExiste(Case dest, Carte carte) {
		CalculChemin cc = new CalculChemin(carte, this);
		Chemin chemin = cc.dijkstra(this.getPosition(), dest);
		if (chemin.getTempsParcours() == Double.POSITIVE_INFINITY) {
			return false;
		}
		return true;
	}

    public void ajouteDeplacementChemin(Chemin chemin) {
        long dateEvenement = simulateur.getDateSimulation();
        for (int i=1; i < chemin.getNbSommets(); i++) {
            dateEvenement += (long) chemin.getSommet(i).getTempsTraverse();
            simulateur.ajouteEvenement(new Deplacement(dateEvenement, this, chemin.getSommet(i).getCase(), this.chefPompier.getCarte()));		    
        }
        this.etatRobot = EtatRobot.DEPLACEMENT;
        simulateur.ajouteEvenement(new Etat(dateEvenement, this, EtatRobot.LIBRE));
    }

}

