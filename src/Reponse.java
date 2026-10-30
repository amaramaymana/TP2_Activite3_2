import java.io.Serializable;

/** Réponse envoyée par le serveur : résultat ou message d'erreur. */
public class Reponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double resultat;
    private final boolean erreur;
    private final String message;
    private final int numeroOperation; // valeur du compteur global

    public Reponse(double resultat, int numeroOperation) {
        this.resultat = resultat;
        this.erreur = false;
        this.message = "OK";
        this.numeroOperation = numeroOperation;
    }

    public Reponse(String message) {
        this.resultat = 0;
        this.erreur = true;
        this.message = message;
        this.numeroOperation = -1;
    }

    public double getResultat()      { return resultat; }
    public boolean isErreur()        { return erreur; }
    public String getMessage()       { return message; }
    public int getNumeroOperation()  { return numeroOperation; }
}