import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class InterfacciaPortafoglio { // 00)
    private final static int    DIMENSIONE_TITOLO = 30,
                                DIMENSIONE_SEZIONI = 9, // 01)
                                SPAZIATURA_PANNELLO_DESTRO = 5; // 02)
    private final AnchorPane contenitore; // 03)
    private final VBox pannelloDestro; // 04)
    private final Label titolo;
    private final BarraNavigazione barraNavigazione; // 05)
    private final Storico storico; // 06)
    private final Andamento andamento; // 07)
    private final AreaMessaggi areaMessaggi; // 08)
    
    public InterfacciaPortafoglio(ParametriConfigurazioneXML config, String fileCache) { // 09)
        contenitore = new AnchorPane();
        titolo = new Label("Portafoglio");
        pannelloDestro = new VBox(SPAZIATURA_PANNELLO_DESTRO);
        storico = new Storico(); // 06)
        andamento = new Andamento(); // 07)
        areaMessaggi = new AreaMessaggi(this, config); // 08)
        barraNavigazione = new BarraNavigazione(this); // 05)
        
        Cache cache = GestoreCache.carica(fileCache); // 10)
        if(cache != null) { // 11)
            barraNavigazione.getPeriodo().getDataInizio().setValue(cache.getDataInizio());
            barraNavigazione.getPeriodo().getDataFine().setValue(cache.getDataFine());
            barraNavigazione.getCerca().getMenu().getSelectionModel().select(cache.getCerca());
            areaMessaggi.getAreaDiTesto().setText(cache.getAreaMessaggi());
        }
        storico.aggiorna(barraNavigazione); // 12)
        if(cache != null)
            storico.getTabella().getSelectionModel().select(cache.getVoceSelezionata()); // 13)
        andamento.aggiorna(barraNavigazione); // 14)
        barraNavigazione.getCerca().aggiorna(); // 15)
        
        pannelloDestro.getChildren().addAll(andamento.getContenitore(), areaMessaggi.getContenitore());
        contenitore.getChildren().addAll(   titolo,
                                            barraNavigazione.getSottotitoli(),
                                            barraNavigazione.getContenitore(),
                                            storico.getContenitore(),
                                            pannelloDestro
        );
    }
    
    public void impostaStile(ParametriConfigurazioneXML config) { // 16)
        contenitore.setStyle("-fx-background-color: " + config.getColoreSfondo()); // 17)
        titolo.setFont(Font.font(config.getFont(), config.getDimensioneFont() + DIMENSIONE_TITOLO)); // 18)
        titolo.setLayoutX(375);
        titolo.setLayoutY(10);
        pannelloDestro.setLayoutX(400);
        pannelloDestro.setLayoutY(150);
        barraNavigazione.impostaStile(config.getFont(), config.getDimensioneFont()); // 19)
        storico.impostaStile(config, DIMENSIONE_SEZIONI); // 19)
        andamento.impostaStile(config.getFont(), config.getDimensioneFont(), DIMENSIONE_SEZIONI); // 19)
        areaMessaggi.impostaStile(config, DIMENSIONE_SEZIONI); // 19)
    }
    
    public AnchorPane getContenitore() { return contenitore; }
    public BarraNavigazione getBarraNavigazione() { return barraNavigazione; }
    public Storico getStorico() { return storico; }
    public AreaMessaggi getAreaMessaggi() { return areaMessaggi; }
    public Andamento getAndamento() { return andamento; }
}

/* Note

00) Interfaccia Grafica dell'applicazione.
01) Numero di pixel che i titoli delle tre sezioni (Storico, Andamento e
    AreaMessaggi) hanno in piu' rispetto al testo dell'applicazione.
02) Spaziatura tra la sezione Andamento e la sezione AreaMessaggi.
03) Contenitore dell'intera interfaccia grafica.
04) Contiene l'Andamento e l'Area Messaggi.
05) Barra che consente all'utente di filtrare le voci per data e nome e di 
    visualizzare il Saldo totale.
06) Visualizza tutte le Voci del Portafoglio filtrate dall'utente in una tabella.
07) Mostra l'Andamento del Saldo nel tempo.
08) Area di testo che mostra messaggi all'utente, con due pulsanti per
    l'eliminazione delle voci.
09) Costruzione dell'Interfaccia Grafica in base ai parametri di configurazione
    e alla cache locale degli input dell'utente.
10) Viene caricata la cache locale degli input dall'apposito file binario.
11) Il metodo carica() di GestoreCache restituisce null in caso di errore.
    Tutti i dati prelevati dalla cache locale vengono assegnati alle corrette
    sezioni dell'interfaccia.
12) Lo storico viene riempito delle voci appartenenti all'eventuale periodo
    e nome selezionati. In caso di assenza di cache, vengono mostrate le voci
    di qualsiasi data e di qualsiasi nome.
13) Seleziona l'eventuale voce selezionata in precedenza, memorizzata nella cache.
14) Aggiorna l'Andamento del saldo relativo alle sole date delle voci appartenenti
    al periodo selezionato e dello stesso nome selezionato dalla sezione Cerca.
15) Ottiene tutti i nomi delle Voci presenti nel database, per consentire
    all'utente di filtrare le voci in base al nome.
16) Imposta lo stile dell'intera Interfaccia Grafica.
17) Imposta il colore di sfondo, prelevato dal file XML di configuazione.
18) Imposta il font del titolo, prelevato dal file XML di configurazione.
19) Viene impostato lo stile degli altri componenti dell'Interfaccia Grafica.

*/