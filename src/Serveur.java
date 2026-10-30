import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** Serveur multi-thread : un ClientProcess par client connecté. */
public class Serveur {
    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 1234;
        int compteurClients = 0;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur calculatrice en écoute sur le port " + port + "...");
            while (true) {
                Socket socket = serverSocket.accept();
                compteurClients++;
                System.out.println("Nouveau client #" + compteurClients + " : "
                        + socket.getInetAddress().getHostAddress());
                new ClientProcess(socket, compteurClients).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}