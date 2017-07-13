import java.io.*;
import java.net.*;

public class GestoreLogAttivitaXML { // 00)
    private static void invia(LogAttivitaXML log, String ipServerLog, int portaServerLog) { // 01)
        try(Socket socket = new Socket(ipServerLog, portaServerLog); // 02)
            ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream()); // 03)
        ) {
            oout.writeObject(log.toString()); // 04)
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void creaLog(String tipoDiLog, ParametriConfigurazioneXML config) { // 05)
        invia(
            new LogAttivitaXML(tipoDiLog, config.getIpClient()), // 06)
            config.getIpServerLog(), 
            config.getPortaServerLog()
        );
    }
}

/* Note

00) Classe che gestisce i log XML da inviare ad un server log ogni qual volta
    che viene lanciata/chiusa l'applicazione e ad ogni click su un pulsante.
01) Invia un oggetto di tipo LogAttivitaXML ad un server log il cui indirizzo
    IP e' specificato nel secondo parametro, e la cui porta e' specificata nel
    terzo.
02) Viene aperto un Socket verso il server log.
03) Flusso oggetto concatenato al flusso in uscita dal Socket.
04) Viene inviato l'oggetto LogAttivitaXML, convertito in una stringa XML
    grazie al suo metodo ridefinito toString().
05) Funzione di utility che crea un oggetto LogAttivitaXML e lo invia
    tramite il metodo invia().
06) Creazione dell'oggetto LogAttivitaXML: esso conterra' la tipologia di log
    specificata come primo parametro, e l'indirizzo IP del client che sta
    inviando il log.

*/