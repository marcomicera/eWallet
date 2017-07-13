import java.io.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ValidatoreXML { // 00)
    private final static String PERCORSO_RELATIVO = "..\\..\\"; // 01)
    
    public static boolean valida(String xml, String fileSchema, boolean file) { // 02)
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); // 03)
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); // 04)
            Document d; // 05)
            if(file) // 06)
                d = db.parse(new File(xml)); // 07)
            else // 08)
                d = db.parse(new InputSource(new StringReader(xml))); // 09)
            Schema s = sf.newSchema(new StreamSource(new File(((fileSchema.compareTo("log.xsd") == 0) ? PERCORSO_RELATIVO : "") + fileSchema))); // 10)
            s.newValidator().validate(new DOMSource(d)); // 11)
        } catch(ParserConfigurationException | SAXException | IOException e) {
            if(e instanceof SAXException) // 12)
                System.err.println("Errore di validazione"); // 13)
            
            System.err.println(e.getMessage());
            return false; // 14)
        }
        return true; // 15)
    }
}

/* Note

00) Classe che gestisce la validazione di file o stringhe XML.
    Ritorna un booleano il cui valore e' true se la validazione avviene con
    successo, e viceversa.
01) Il metodo di ValidatoreXML e' chiamato dalla classe Portafoglio e dalla
    classe ServerLogAttivitaXML: quest'ultima viene eseguita dalla directory
    \Portafoglio\build\classes (dove \Portafoglio\ e' la directory root
    dell'applicazione), mentre il file schema di log di cui ha bisogno si
    trova nella root dell'applicazione.
    Si necessita quindi di anteporre, durante la ricerca del file schema di log,
    la stringa "..\..\", per specificare che il file in questione si trova
    nella directory padre della directory padre di \Portafoglio\build\classes.
02) Valida un file o una stringa XML (primo parametro) secondo un file schema
    specificato da fileSchema. Il terzo parametro distingue il caso di
    validazione di un file XML o direttamente di una stringa XML, utile nel caso
    dei log.
03) Definisce le API per ottenere documenti DOM da piu' tipi di sorgenti XML,
    come in questo caso da un file o da una stringa.
    https://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilder.html
04) Factory di oggetti Schema che funge da compilatore di schema XML: infatti
    per la sua creazione necessita l'URI di un XML schema namespace per definire
    un linguaggio di schema.
    https://docs.oracle.com/javase/8/docs/api/javax/xml/validation/SchemaFactory.html
05) Oggetto in grado di rappresentare un documento DOM, che puo' essere un
    documento HTML, o, come in questo caso, un documento XML.
    https://docs.oracle.com/javase/8/docs/api/org/w3c/dom/Document.html
06) Se l'input XML e' da prelevare in un file.
07) Effettua il parse del file XML e restituisce un oggetto DOM.
    https://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilder.html#parse-java.io.File-
08) Se l'input XML e' direttamente una stringa passata come primo parametro.
09) Si necessita di effettuare il parse di una stringa XML.
    DocumentBuiler permette il parsing non solo da file, ma anche da un InputSource:
    https://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilder.html#parse-org.xml.sax.InputSource-
    che rappresenta una sorgente di input XML ottenibile da un flusso di byte o
    di caratteri. E' possibile associare ad un InputSource un flusso di
    caratteri con il seguente costruttore:
    https://docs.oracle.com/javase/8/docs/api/org/xml/sax/InputSource.html#InputSource-java.io.Reader-
    che richiede un oggetto Reader. In questo caso viene utilizzata una sua
    sottoclasse, StringReader, il cui costruttore richiede come parametro la
    stringa XML in questione:
    https://docs.oracle.com/javase/8/docs/api/java/io/StringReader.html#StringReader-java.lang.String-
    Idea presa da:
    http://stackoverflow.com/questions/7607327/how-to-create-a-xml-object-from-string-in-java
10) Oggetto Schema creato dallo SchemaFactory, ottenuto prelevando il file
    di schema XSD. La classe ServerLogAttivitaXML e' l'unica mandata in
    esecuzione dalla directory \Portafolio\build\classes, percio' per
    raggiungere il file schema di log, necessita di anteporre al percorso del
    file, il percorso relativo prima descritto.
11) Viene creato un oggetto validatore che valida il documento DOM derivante
    dal documento XML, sullo Schema appena creato.
    Il parser SAX lancia una SAXException in caso di errore di validazione.
12) Se l'eccezione e' dovuta ad un errore di validazione.
13) Si stampa a video.
14) Qualsiasi sia il tipo di eccezione, la validazione si puo' considerare come
    avvenuta con insuccesso.
15) Se nessuna eccezione viene lanciata, la validazione e' avvenuta correttamente.

*/