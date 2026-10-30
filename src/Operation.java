import java.io.Serializable;

/** Requête envoyée par le client : deux opérandes et un opérateur. */
public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double operande1;
    private final double operande2;
    private final char operateur; // + - * / ou 'q' pour quitter

    public Operation(double operande1, char operateur, double operande2) {
        this.operande1 = operande1;
        this.operateur = operateur;
        this.operande2 = operande2;
    }

    public double getOperande1() { return operande1; }
    public double getOperande2() { return operande2; }
    public char getOperateur()   { return operateur; }

    @Override
    public String toString() {
        return operande1 + " " + operateur + " " + operande2;
    }
}