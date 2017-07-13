import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;

public class Portafoglio extends Application {
    private final static String FILE_CONFIGURAZIONE = "config.xml", // 01)
                                SCHEMA_CONFIGURAZIONE = "config.xsd", // 02)
                                FILE_INPUT = "input.xml", // 03)
                                SCHEMA_INPUT = "input.xsd", // 04)
                                FILE_CACHE = "cache.bin"; // 05)
    private InterfacciaPortafoglio interfacciaPortafoglio; // 06)
    
    public void start(Stage stage) {
        ParametriConfigurazioneXML config = // 07)
            new ParametriConfigurazioneXML(
                ValidatoreXML.valida(GestoreFile.carica(FILE_CONFIGURAZIONE), SCHEMA_CONFIGURAZIONE, false) ? // 08)
                    GestoreFile.carica(FILE_CONFIGURAZIONE) : null // 09)
            );
        GestoreLogAttivitaXML.creaLog("Apertura applicazione", config); // 10)
        
        if(ValidatoreXML.valida(FILE_INPUT, SCHEMA_INPUT, true)) { // 11)
            GestoreDatabase.inserisciVoci((ArrayList<Voce>)Voce.creaXStream().fromXML(GestoreFile.carica(FILE_INPUT))); // 12)
            GestoreFile.svuota(FILE_INPUT); // 13)
        }
        
        interfacciaPortafoglio = new InterfacciaPortafoglio(config, FILE_CACHE); // 14)
        interfacciaPortafoglio.impostaStile(config); // 15)
        
        stage.setOnCloseRequest((WindowEvent we) -> { // 16)
            GestoreLogAttivitaXML.creaLog("Chiusura applicazione", config); // 17)
            GestoreCache.salva(interfacciaPortafoglio, FILE_CACHE); // 18)
        });
        stage.setTitle("Portafoglio"); // 19)
        Scene scene = new Scene(interfacciaPortafoglio.getContenitore(), config.getLarghezza(), config.getAltezza()); // 20)
        stage.setScene(scene);
        stage.show();
    }
}

/* Note

01) All'avvio, il sistema legge dal file di configurazione i seguenti dati:
        a) Font, dimensioni, colore del background
        b) Numberi di righe dello Storico da visualizzare
        c) Indirizzo IP del client, indirizzo IP e porta del server log
02) File di schema XSD per validare il file di configurazione XML.
03) L'inserimento delle Voci nel Portafoglio avviene tramite un file XML.
04) File di schema XSD per validare il file delle nuove voci XML.
05) File di cache locale degli input. Alla chiusura, il sistema salva:
        a) Il Periodo selezionato
        b) L'eventuale testo immesso nel campo Cerca
        c) L'eventuale testo presente nell'Area Messaggi
        d) L'eventuale voce selezionata nello Storico
06) Classe che gestisce l'Interfaccia Grafica.
07) Classe che contiene i parametri di configurazione XML.
08) Si prelevano i parametri di configurazione solo se la validazione del
    relativo file XML avviene con successo.
09) Se la validazione e' avvenuta con successo, si prelevano i parametri di
    configurazione dal file XML. Altrimenti, si passa null al costruttore
    di ParametriConfigurazioneXML, che si occupera' ad impostare dei valori
    di default.
10) Si invia un log ad un server remoto contenente, riguardo l'avvio
    dell'applicazione. L'IP e la porta del server log sono contenuti dentro
    l'oggetto di tipo ParametriConfigurazioneXML.
11) Si procede all'inserimento delle nuovi Voci nel database solo se il relativo
    file XML viene validato con successo.
12) Inserimento delle nuove Voci prelevate dal relativo file XML nel database.
13) Il file delle nuove voci viene svuotato in modo da impedire il
    re-inserimento delle stesse voci una seconda volta ("Duplicate entry error").
14) Viene creata l'interfaccia grafica con i parametri di configurazione
    appena prelevati dal relativo file XML e impostando il contenuto della
    cache locale degli input.
15) Imposta lo stile dell'interfaccia grafica.
16) Alla chiusura dell'applicazione.
17) Viene inviato un log relativo alla chiusura dell'applicazione.
18) Viene salvata la cache locale degli input nell'apposito file.
19) Imposta il titolo della finestra di Windows.
20) Le dimensioni della finestra sono prelevate dal file di configurazione XML.

*/