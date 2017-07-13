import com.thoughtworks.xstream.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class LogAttivitaXML implements Serializable { // 00)
    private final String nomeEvento, indirizzoIpClient, dataOraCorrente; // 01)
    
    public LogAttivitaXML(String nomeEvento, String indirizzoIpClient) {
        this.nomeEvento = nomeEvento;
        this.indirizzoIpClient = indirizzoIpClient;
        dataOraCorrente = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date()); // 02)
    }
    
    public String toString() { // 03)
        XStream xs = new XStream();
        xs.useAttributeFor(LogAttivitaXML.class, "nomeEvento"); // 04)
        xs.useAttributeFor(LogAttivitaXML.class, "indirizzoIpClient"); // 04)
        xs.useAttributeFor(LogAttivitaXML.class, "dataOraCorrente"); // 04)
        return xs.toXML(this) + "\n"; // 05)
    }
}

/* Note

00) Classe che rappresenta un log da inviare ad un server log, ogni qual volta
    che viene lanciata/chiusa l'applicazione e ad ogni click su un pulsante.
01) Un log contiene la tipologia dell'evento, l'indirizzo IP del client, la data
    e l'ora di creazione del log.
02) Per ottenere la data e l'ora corrente, basta creare un oggetto di tipo
    java.util.Date utilizzando il suo costruttore senza parametri.
    https://docs.oracle.com/javase/8/docs/api/java/util/Date.html#Date--
    Quest'ultimo pero' fornisce l'istante di creazione in millisecondi.
    Per ottenerne una rappresentazione personalizzata, viene utilizzato il
    metodo format() della classe SimpleDateFormat
    http://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html#format-java.util.Date-java.lang.StringBuffer-java.text.FieldPosition-
03) Metodo che converte un oggetto di tipo LogAttivitaXML in una stringa XML.
04) In base alle regole di buona progettazione XML, ogni membro della classe
    e' stato scelto come attributo in quanto si tratta di una stringa semplice
    (seconda regola).
05) Viene inserito un "accapo" in fondo alla stringa XML per rendere più
    leggibile il file di log del server.

*/