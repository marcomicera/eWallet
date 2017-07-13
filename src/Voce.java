import com.thoughtworks.xstream.*;
import java.sql.*;

public class Voce { // 00)
    private final int id; // 01)
    private final String nome; // 02)
    private final Date data; // 03)
    private final double importo; // 04)
    
    public Voce(int id, String nome, Date data, double importo) { // 05)
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.importo = importo;
    }
    
    public final static XStream creaXStream() {
        XStream xs = new XStream();
        xs.useAttributeFor(Voce.class, "id"); // 06)
        xs.useAttributeFor(Voce.class, "nome"); // 07)
        xs.useAttributeFor(Voce.class, "importo"); // 06)
        return xs;
    }
    
    public String toString() { // 08)
        return creaXStream().toXML(this); // 08)
    }
    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public Date getData() { return data; }
    public double getImporto() { return importo; }
}

/* Note

00) Classe contenente le informazioni di una entry del database.
01) Viene usato l'ID come identificatore unico di una Voce, in quanto gli altri
    dati non possono essere in grado di identificare univocamente una Voce.
    In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di una numero semplice (seconda regola).
02) Nome (tipologia) dell'entrata del Portafoglio.
    Sara' possibile filtrare le voci da visualizzare in base a questa proprieta'.
    In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di una stringa semplice (seconda regola).
03) Data nella quale la Voce e' stata effettuata.
    Sara' possibile filtrare le voci da visualizzare in base alla loro
    appartenenza ad un periodo temporale.
    In base alle regole di buona progettazione XML, viene modellato come
    elemento in quanto puo' assumere una moltitudine di valori (prima regola).
04) Importi negativi corrispondono a spese; importi positivi corrispondono
    a guadagni.
    In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di una numero semplice (seconda regola).
05) Costruttore usato da GestoreDatabase al momento dell'aggiunta delle Voci
    nell'ObservableList<Voce> dello Storico.
06) In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di una numero semplice (seconda regola).
07) In base alle regole di buona progettazione XML, viene modellato come
    attributo in quanto si tratta di una stringa semplice (seconda regola).
08) Serializza l'oggetto in XML.

*/