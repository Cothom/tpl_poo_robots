package cpcc;

/**
 * Classe permettant de stocker à la fois le chemin
 * et l'objet vers lequel il va, on s'en sert avec la méthode
 * Robot.caseLaPlusProcheAutour.
 *
 */
public class PlusProcheObjet {

    private Object objet;
    private Chemin chemin;

    public PlusProcheObjet(Object o, Chemin c) {
        this.objet = o;
        this.chemin = c;
    }

    public Object getObjet() {
        return this.objet;
    }

    public Chemin getChemin() {
        return this.chemin;
    }
}
