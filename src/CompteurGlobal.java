/**
 * Compteur global partagé entre tous les threads ClientProcess.
 * Ressource critique : l'accès est synchronisé (verrou sur la classe).
 */
public class CompteurGlobal {
    private static int valeur = 0;

    private CompteurGlobal() {}

    /** Incrémente le compteur et affiche sa nouvelle valeur de façon atomique. */
    public static synchronized int incrementerEtAfficher(String origine) {
        valeur++;
        System.out.println("[Compteur global] " + valeur + " opération(s) traitée(s) (dernière : " + origine + ")");
        return valeur;
    }

    public static synchronized int getValeur() {
        return valeur;
    }
}