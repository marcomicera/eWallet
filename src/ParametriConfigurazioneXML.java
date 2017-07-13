import com.thoughtworks.xstream.*;

public class ParametriConfigurazioneXML { // 00)
    private final static double LARGHEZZA_DEFAULT_FINESTRA = 970, // 01)
                                ALTEZZA_DEFAULT_FINESTRA = 650; // 01)
    private final static int    NUMERO_RIGHE_DEFAULT = 16,  // 01)
                                PORTA_SERVER_LOG_DEFAULT = 6789, // 01)
                                DIMENSIONE_FONT_DEFAULT = 11;  // 01)
    private final static String FONT_DEFAULT = "Arial",  // 01)
                                COLORE_SFONDO_DEFAULT = "WHITE";  // 01)
    private final String font; // 02)
    private final double dimensioneFont; // 03)
    private final double[] dimensioni; // 04)
    private final String coloreSfondo; // 05)
    private final int numeroRighe; // 06)
    private final String ipClient; // 07)
    private final String ipServerLog; // 08)
    private final int portaServerLog; // 09)
    
    public ParametriConfigurazioneXML(String xml) {
        dimensioni = new double[2];
        
        if(xml == null || xml.compareTo("") == 0) { // 10)
            font = FONT_DEFAULT;
            dimensioneFont = DIMENSIONE_FONT_DEFAULT;
            dimensioni[0] = LARGHEZZA_DEFAULT_FINESTRA;
            dimensioni[1] = ALTEZZA_DEFAULT_FINESTRA;
            coloreSfondo = COLORE_SFONDO_DEFAULT;
            numeroRighe = NUMERO_RIGHE_DEFAULT;
            ipClient = "127.0.0.1";
            ipServerLog = "127.0.0.1";
            portaServerLog = PORTA_SERVER_LOG_DEFAULT;
        } else {
            ParametriConfigurazioneXML p = (ParametriConfigurazioneXML)creaXStream().fromXML(xml); // 11)
            font = p.font;
            dimensioneFont = p.dimensioneFont;
            dimensioni[0] = p.dimensioni[0];
            dimensioni[1] = p.dimensioni[1];
            coloreSfondo = p.coloreSfondo;
            numeroRighe = p.numeroRighe;
            ipClient = p.ipClient;
            ipServerLog = p.ipServerLog;
            portaServerLog = p.portaServerLog;
        }
    }

    public final XStream creaXStream() {
        XStream xs = new XStream();
        xs.useAttributeFor(ParametriConfigurazioneXML.class, "numeroRighe"); // 12)
        xs.useAttributeFor(ParametriConfigurazioneXML.class, "ipClient"); // 13)
        xs.useAttributeFor(ParametriConfigurazioneXML.class, "ipServerLog"); // 13)
        xs.useAttributeFor(ParametriConfigurazioneXML.class, "portaServerLog"); // 12)
        return xs;
    }
    
    public String toString() { // 14)
        return creaXStream().toXML(this); // 14)
    }
    
    public String getFont() { return font; }
    public double getDimensioneFont() { return dimensioneFont; }
    public double getLarghezza() { return dimensioni[0]; }
    public double getAltezza() { return dimensioni[1]; }
    public String getColoreSfondo() { return coloreSfondo; }
    public int getNumeroRighe() { return numeroRighe; }
    public String getIpClient() { return ipClient; }
    public String getIpServerLog() { return ipServerLog; }
    public int getPortaServerLog() { return portaServerLog; }
}

/* Note

00) Classe contenente i Parametri Di Configurazione, prelevati da un file XML.
    Esso contiene:
        a) Font, dimensioni, colore del background
        b) Numberi di righe dello Storico da visualizzare
        c) Indirizzo IP del client, indirizzo IP e porta del server log
01) Valori di default nel caso in cui il file XML di configuazione dovesse
    mancare o che non venga validato.
02) Font del testo dell'Interfaccia Grafica.
    In base alle regole di buona progettazione XML, viene modellato come
    elemento in quanto puo' assumere una moltitudine di valori (prima regola).
03) Dimensione del font del testo dell'Interfaccia Grafica.
    In base alle regole di buona progettazione XML, viene modellato come
    elemento in quanto occorre specificare la sua presenza in un certo ordine,
    ovvero dopo il campo font, in quanto le due informazioni sono strettamente
    correlate (terza regola).
04) Dimensioni della fineestra in pixel.
    In base alle regole di buona progettazione XML, viene modellato come
    elemento in quanto e' strutturato su linee multiple (array) (prima regola).
05) Colore dello sfondo dell'Interfaccia Grafica.
    In base alle regole di buona progettazione XML, viene modellato come
    elemento in quanto puo' assumere una moltitudine di valori (prima regola).
06) Numero di righe dello Storico da visualizzare.
    In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di un numero semplice (seconda regola).
07) Indirizzo IP del client, da inserire nei log.
    In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di una stringa semplice (seconda regola).
08) Indirizzo IP del server log.
    In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di una stringa semplice (seconda regola).
09) Porta del server log.
    In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di un numero semplice (seconda regola).
10) Nel caso in cui il file XML di configuazione dovesse mancare o venga
    validato, il costruttore usa dei valori di default.
11) Viene deserializzata la stringa XML dei Parametri Di Configurazione
    passata come argomento al costruttore.
12) In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di un numero semplice (seconda regola).
13) In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di una stringa semplice (seconda regola).
14) Serializza l'oggetto in XML.

*/