package cpcc;

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
