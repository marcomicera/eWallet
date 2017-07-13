import java.io.*;
import java.net.*;

public class ServerLogAttivitaXML { // 00)
    private final static int PORTA = 6789; // 01)
    private final static String FILE_LOG = "log.xml", // 02)
                                SCHEMA_LOG = "log.xsd"; // 03)
    
    public static void main(String[] args) {
        System.out.println("Server Log avviato.\n");
        
        try(ServerSocket serverSocket = new ServerSocket(PORTA)) { // 04)
            while(true) { // 05)
                try(Socket socket = serverSocket.accept(); // 06)
                    ObjectInputStream oin = new ObjectInputStream(socket.getInputStream()); // 07)
                ) {
                    String log = (String)oin.readObject(); // 08)
                    System.out.println(log); // 09)
                    if(ValidatoreXML.valida(log, SCHEMA_LOG, false)) // 10)
                        GestoreFile.salva(log, FILE_LOG, true); // 10)
                }
            }
        } catch(IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}

/* Note

00) Server che riceve ciclicamente log XML, e se validati correttamente, li
    aggiunge ad un file di log.
01) Porta del server log.
02) File nel quale il server salva i log XML.
03) File di schema per la validazione delle singole stringhe XML di log che il
    server riceve ciclicamente.
04) Crea un server socket, specificandone la porta.
    La coda di backlog di default e' di 50.
    https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html#ServerSocket(int)
05) Il server e' ciclico.
06) L'applicazione si blocca finche' viene creata una connessione, e la accetta.
07) Flusso oggetto concatenato al flusso in entrata dal Socket.
08) Viene letta la stringa XML di log dal flusso oggetto concatenato al flusso
    in entrata dal Socket.
09) Stampa la stringa XML di log a video.
10) Se la singola  stringa XML viene validata correttamente viene aggiunta ad un
    file di log XML.

*/