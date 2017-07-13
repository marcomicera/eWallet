import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class Storico { // 00)
    private final static int SPAZIATURA_CONTENITORE = 3; // 01)
    private final static double ALTEZZA_RIGA = 25; // 02)
    private final VBox contenitore;
    private final Label titolo;
    private final TableView<Voce> tabella; // 03)
    private final TableColumn nomeCol, importoCol, dataCol; // 04)
    private final ObservableList<Voce> voci; // 05)
    
    public Storico() {
        titolo = new Label("Storico");
        contenitore = new VBox(SPAZIATURA_CONTENITORE);
        
        nomeCol = new TableColumn("Nome");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        importoCol = new TableColumn("Importo");
        importoCol.setCellValueFactory(new PropertyValueFactory<>("importo"));
        dataCol = new TableColumn("Data");
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        
        voci = FXCollections.observableArrayList(); // 06)
        tabella = new TableView<>(voci); // 07)
        
        tabella.getColumns().addAll(nomeCol, importoCol, dataCol);
        contenitore.getChildren().addAll(titolo, tabella);
    }
    
    public void impostaStile(ParametriConfigurazioneXML config, int dimensioneSezione) { // 08)
        titolo.setFont(Font.font(config.getFont(), config.getDimensioneFont() + dimensioneSezione)); // 09)
        contenitore.setLayoutX(80);
        contenitore.setLayoutY(150);
        tabella.setFixedCellSize(ALTEZZA_RIGA); // 10)
        tabella.prefHeightProperty().set((config.getNumeroRighe() + 1) * ALTEZZA_RIGA); // 11)
    }
    
    public void aggiorna(BarraNavigazione barraNavigazione) { // 12)
        GestoreDatabase.ottieniVoci( // 13)
            voci,
            barraNavigazione.getPeriodo().getDataInizio().getValue(),
            barraNavigazione.getPeriodo().getDataFine().getValue(),
            barraNavigazione.getCerca().getVoceSelezionata());
    }
    
    public VBox getContenitore() { return contenitore; }
    public TableView<Voce> getTabella() { return tabella; }
    public ObservableList<Voce> getVoci() { return voci; } 
}

/* Note 

00) Sezione dell'Interfaccia Grafica contenente la tabella delle Voci.
01) Spaziatura tra il titolo della sezione e la tabella.
02) Altezza in pixel di una riga della tabella.
03) Tabella nella quale verranno mostrate le Voci eventualmente filtrate.
04) Nella tabella verranno visualizzate tre colonne relative al nome della Voce,
    al suo importo e alla sua data.
05) Lista osservabile contente le sole Voci da visualizzare nella tabella.
06) Viene inizializzata l'ObservableList<Voce>, al momento vuota,
    successivamente riempita dal metodo aggiorna(), invocato dalla classe
    InterfacciaPortafoglio.
07) Si associa alla tabella l'ObservableList<Voce> contenente le soli Voci da
    visualizzare.
08) Imposta lo stile della sezione Storico.
09) Imposta il font del titolo, prelevato dal file XML di configurazione.
10) Imposta l'altezza delle righe della tabella.
11) Imposta il numero delle righe da visualizzare nella tabella, informazione
    prelevata dal file XML di configurazione, agendo sull'altezza in pixel della
    TableView, come numero delle righe da mostrare (piu' l'intestazione)
    moltiplicata l'altezza di una riga.
12) Aggiorna il contenuto della tabella con le Voci da visualizzare.
    Necessita di barraNavigazione come parametro in quanto la Cache locale
    degli input e' gia' stata caricata.
13) Metodo della classe GestoreDatabase che agisce direttamente sulla
    ObservableList<Voce> dello Storico.

*/