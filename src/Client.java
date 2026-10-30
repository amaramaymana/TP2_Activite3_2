import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/** Client : usage  java Client [adresse_serveur] [port] */
public class Client {
    public static void main(String[] args) {
        String hote = (args.length > 0) ? args[0] : "localhost";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 1234;

        try (Socket socket = new Socket(hote, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in)) {
            out.flush();
            System.out.println("Connecté à " + hote + ":" + port);

            while (true) {
                System.out.print("Opérande 1 (ou 'q' pour quitter) : ");
                String s1 = sc.nextLine().trim();
                if (s1.equalsIgnoreCase("q")) {
                    out.writeObject(new Operation(0, 'q', 0));
                    out.flush();
                    break;
                }
                System.out.print("Opérateur (+ - * /) : ");
                String sop = sc.nextLine().trim();
                System.out.print("Opérande 2 : ");
                String s2 = sc.nextLine().trim();

                try {
                    double a = Double.parseDouble(s1);
                    double b = Double.parseDouble(s2);
                    if (sop.length() != 1) { System.out.println("Opérateur invalide."); continue; }

                    out.writeObject(new Operation(a, sop.charAt(0), b));
                    out.flush();

                    Reponse rep = (Reponse) in.readObject();
                    if (rep.isErreur()) {
                        System.out.println("Erreur : " + rep.getMessage());
                    } else {
                        System.out.println("Résultat = " + rep.getResultat()
                                + "  (opération n°" + rep.getNumeroOperation() + " du serveur)");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Nombre invalide.");
                }
            }
            System.out.println("Déconnexion.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}


