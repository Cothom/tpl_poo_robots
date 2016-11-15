package cpcc;

import java.util.Collections;
import java.util.Scanner;

import robots.*;
import maps.*;

/**
 * Classe contenant toutes les méthodes nécessaires aux calculs
 * d'un plus court chemin grâce à l'algorithme de dijksra
 */
public class CalculChemin {

    private int nbLignes;
    private int nbColonnes;
    private Robot robot;
    private Carte carte;
    private Sommet[][] sommets;

    public CalculChemin(Robot pRobot) {
        this.robot = pRobot;
    }

    public CalculChemin(Carte pCarte, Robot pRobot) {
        this.robot = pRobot;
        this.setCarte(pCarte);
    }

    public static double tempsTraverse(Carte pCarte, Case c, Robot r) {
        return (double) pCarte.getTailleCases() / r.getVitesse(c.getNature());
    }

    /**
     * Retourne le temps que mettra le robot r pour aller de src à dst
     * en calculant la moyenne des vitesses du robot sur les deux cases
     * @param pCarte
     * @param src
     * @param dst
     * @param r
     * @return
     */
    public static double calculPoidsArc(Carte pCarte, Case src, Case dst, Robot r) {
        if (r.getVitesse(src.getNature()) == 0 || r.getVitesse(dst.getNature()) == 0)
            return Double.POSITIVE_INFINITY;
        return (double) pCarte.getTailleCases() / ((r.getVitesse(src.getNature()) + r.getVitesse(dst.getNature())) / 2);
    }
    
    /**
     * Retourne la différence de colonne à laquelle correspond la direction d
     * @param d
     * @return
     */
    public static int getDeltaC(Direction d) {
        if (d == Direction.NORD || d == Direction.SUD) return 0;
        if (d == Direction.OUEST) return -1;
        if (d == Direction.EST) return 1;
        return 0;
    }
    
    /**
     * Retourne la différence de ligne à laquelle correspond la direction d
     * @param d
     * @return
     */
    public static int getDeltaL(Direction d) {
        if (d == Direction.OUEST || d == Direction.EST) return 0;
        if (d == Direction.NORD) return -1;
        if (d == Direction.SUD) return 1;
        return 0;
    }

    public Sommet getSommet(int i, int j) {
        if (i < 0 || j < 0)
            throw new IllegalArgumentException("Argument invalide: coordonnée négative.");
        if (i > this.carte.getNbLignes() || j > this.carte.getNbColonnes())
            throw new IllegalArgumentException("Argument invalide: coordonnée hors de la carte.");
        return this.sommets[i][j];
    }

    /**
     * L'attribut carte de la classe n'a pas forcément été défini lors de la
     * construction de l'objet.
     * @param pCarte permet de déduire tous les sommets permettant à la classe
     * de faire les calculs de plus court chemin.
     */
    public void setCarte(Carte pCarte) {
        this.carte = pCarte;
        this.nbLignes = pCarte.getNbLignes();
        this.nbColonnes = pCarte.getNbColonnes();
        this.sommets = new Sommet[pCarte.getNbLignes()][pCarte.getNbColonnes()];
        this.reinitSommets(pCarte);
    }
    
    /**
     * Réinitialise tous les sommets pour annuler les valeurs
     * retenues lors des calculs précédents.
     * @param c
     */
    public void reinitSommets(Carte c) {
        for (int i = 0; i < c.getNbLignes(); i++) {
            for (int j = 0; j < c.getNbColonnes(); j++) {
                this.sommets[i][j] = new Sommet(this, c.getCase(i, j));
            }
        }
        for (int i = 0; i < c.getNbLignes(); i++) {
            for (int j = 0; j < c.getNbColonnes(); j++) {
                this.sommets[i][j].ajouterVoisins();
            }
        }
    }

    public Carte getCarte() {
        return this.carte;
    }

    public Robot getRobot() {
        return this.robot;
    }

    /**
     * @return Renvoie le sommet actuellement le plus proche de la source du chemin
     */
    private Sommet sommetMin() {
        int x = 0;
        int y = 0;
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < this.nbLignes; i++) {
            for (int j = 0; j < this.nbColonnes; j++) {
                if (this.sommets[i][j].getDistanceSource() < min && !this.sommets[i][j].getEstMarque()) {
                    min = this.sommets[i][j].getDistanceSource();
                    x = i;
                    y = j;
                }
            }
        }	
        return this.sommets[x][y];
    }

    /**
     * Apres le marquage d'un sommet minimum, on met à jour
     * les distance à la source de ses voisins
     * @param s Sommet dont les voisins voient leur distances à la source
     * être actualisées
     */
    private void majDistances(Sommet s) {
		double poidsArc = 0;
        for(Sommet v : s.getVoisins()) {
			poidsArc = calculPoidsArc(this.carte, s.getCase(), v.getCase(), this.robot);
            if (poidsArc + s.getDistanceSource() < v.getDistanceSource()) {
                v.setDistanceSource(poidsArc + s.getDistanceSource());
                v.setVoisinVersSource(s);
            }

        }
    }

    private Chemin renverserChemin(Chemin c) {
        Chemin r = new Chemin(this.carte, this.robot);
        for (int i = c.getNbSommets()-1; i >= 0; i--) {
            r.ajouterSommet(c.getSommet(i));
        }
        return r;
    }
    
    /**
     * Permet de déduire un chemin complet à partir du fait que chaque sommet
     * connait son voisin par lequel le robot doit passer pour aller
     * jusquau point de départ, src
     * @param src point de départ du chemin
     * @param dest point d'arrivée du chemin
     * @return le chemin entre src et dest
     */
    private Chemin cheminComplet(Sommet src, Sommet dest) {
        Chemin chemin = new Chemin(this.carte, this.robot);
        Sommet tmp = dest;
        while (!tmp.equals(src)) {
            chemin.ajouterSommet(tmp);
            tmp = tmp.getVoisinVersSource();
        }
        chemin.ajouterSommet(tmp);
        Collections.reverse(chemin.getTabSommets());
        return chemin;
    }

    /**
     * Méthode principale de l'algorithme de dijkstra, elle permet de trouver
     * le plus court chemin entre les cases src et dst
     * @param src point de départ du chemin
     * @param dst point d'arrivée du chemin
     * @return le chemin le plus court entre src et dst
     */
    public Chemin dijkstra(Case src, Case dst) {
	if (this.sommets == null) {System.out.println("pd de conte");}
        Sommet source = this.sommets[src.getLigne()][src.getColonne()];
        Sommet destin = this.sommets[dst.getLigne()][dst.getColonne()];
        int lDestin = destin.getCase().getLigne();
        int cDestin = destin.getCase().getColonne();
        Sommet courant = source;
        source.setDistanceSource(0);

      if (this.robot.getVitesse(dst.getNature()) == 0) {
          Chemin c = new Chemin(this.carte, this.robot);
          c.setTempsParcours(Double.POSITIVE_INFINITY);
          return c;
      }

        while (!(courant.getCase().getColonne() == cDestin && courant.getCase().getLigne() == lDestin)) {
            this.majDistances(courant);
            courant = this.sommetMin();
            courant.setEstMarque(true);
        }
        return cheminComplet(source, destin);
    }

    /**
     * Affiche un chemin sur la sortie standard de manière lisible
     * @param c chemin à afficher
     */
    public void afficherChemin(Chemin c) {
        System.out.println("Chemin le plus court entre "+ c.getSommet(0).getCase().getLigne() + ", " + c.getSommet(0).getCase().getColonne() + " et " + c.getSommet(c.getNbSommets()-1).getCase().getLigne() + ", " + c.getSommet(c.getNbSommets()-1).getCase().getColonne() + " pour le robot " + this.robot.toString());
        System.out.println("Tailles des cases : " + this.carte.getTailleCases());
        double temps = c.getTempsParcours()-c.getSommet(0).getTempsTraverse();
        System.out.println("Temps de parcours du chemin :" + temps);
        for (int i = 0; i < c.getNbSommets(); i++) {
            System.out.print("(" + c.getSommet(i).getCase().getLigne() + ", " + c.getSommet(i).getCase().getColonne() + ") ");
        }

    }
    
    /**
     * Affiche les sommets qui non pas encore été marqués par l'algorithme de dijkstra
     */
    private void afficheSommetsNonMarques() {
        for (int i = 0; i < this.nbLignes; i++) {
            for (int j = 0; j < this.nbColonnes; j++) {
                if (!this.sommets[i][j].getEstMarque()) {
                    System.out.print("(" + this.sommets[i][j].getCase().getLigne() + ", " + this.sommets[i][j].getCase().getColonne() + ") ");
                }
            }
        }
    }

}
