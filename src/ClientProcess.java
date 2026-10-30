import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/** Thread dédié à un client : lit les Operation, calcule, renvoie des Reponse. */
public class ClientProcess extends Thread {
    private final Socket socket;
    private final int idClient;

    public ClientProcess(Socket socket, int idClient) {
        this.socket = socket;
        this.idClient = idClient;
    }

    @Override
    public void run() {
        String adresse = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
        // Ordre important : flux de sortie d'abord (le client fait pareil) pour éviter un blocage
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            out.flush();

            while (true) {
                Operation op = (Operation) in.readObject();
                if (op.getOperateur() == 'q') {
                    break;
                }
                System.out.println("Client #" + idClient + " (" + adresse + ") -> " + op);
                out.writeObject(calculer(op));
                out.flush();
            }
        } catch (EOFException e) {
            // le client a fermé la connexion
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Client #" + idClient + " : erreur " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("Client #" + idClient + " (" + adresse + ") déconnecté.");
        }
    }

    private Reponse calculer(Operation op) {
        double a = op.getOperande1();
        double b = op.getOperande2();
        double r;
        switch (op.getOperateur()) {
            case '+': r = a + b; break;
            case '-': r = a - b; break;
            case '*': r = a * b; break;
            case '/':
                if (b == 0) return new Reponse("Division par zéro impossible");
                r = a / b;
                break;
            default:
                return new Reponse("Opérateur inconnu : " + op.getOperateur());
        }
        int n = CompteurGlobal.incrementerEtAfficher("client #" + idClient);
        return new Reponse(r, n);
    }
}