import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class Cerca { // 00)
    final Label titolo;
    private final ChoiceBox menu; // 01)
    private final ObservableList<String> voci; // 02)
    private String voceSelezionata; // 03)
    
    public Cerca(InterfacciaPortafoglio interfacciaPortafoglio) {
        titolo = new Label("Cerca");
        voci = FXCollections.observableArrayList(); // 04)
        menu = new ChoiceBox(voci); // 05)
        menu.setPrefWidth(100); // 06)
        
        menu.getSelectionModel().selectedItemProperty().addListener( // 07)
            (property, vecchiaVoce, nuovaVoce) -> {
                voceSelezionata = (String)nuovaVoce; // 08)
                GestoreDatabase.ottieniVoci( // 09)
                    interfacciaPortafoglio.getStorico().getVoci(), 
                    interfacciaPortafoglio.getBarraNavigazione().getPeriodo().getDataInizio().getValue(), 
                    interfacciaPortafoglio.getBarraNavigazione().getPeriodo().getDataFine().getValue(), 
                    voceSelezionata
                );
                GestoreDatabase.ottieniAndamento( // 10)
                    interfacciaPortafoglio.getAndamento().getSerie().getData(),
                    interfacciaPortafoglio.getBarraNavigazione().getPeriodo().getDataInizio().getValue(), 
                    interfacciaPortafoglio.getBarraNavigazione().getPeriodo().getDataFine().getValue(), 
                    voceSelezionata
                );
            }
        );
    }
    
    public void aggiorna() { // 11)
        GestoreDatabase.ottieniNomiVoci(voci); // 12)
    }
    
    public void impostaStile(String font, double dimensioneFont) { // 13)
        titolo.setFont(Font.font(font, dimensioneFont)); // 14)
    }
    
    public ChoiceBox getMenu() { return menu; }
    public ObservableList<String> getVoci() { return voci; }
    public String getVoceSelezionata() { return voceSelezionata; }
}

/* Note

00) Sezione dell'Interfaccia Grafica che consente all'utente di filtrare le
    voci da visualizzare in base al loro nome.
01) Menu a tendina contenente tutti i nomi diversi presenti nel database.
    L'utente potra' sceglierne uno per visualizzare tutte le voci presenti nel
    database con il nome selezionato.
02) ObservableList<String> contenente i nomi delle voci presenti nel menu a
    tendina. Si necessita di una lista osservabile in quanto durante 
    l'esecuzione dell'applicazione, potrebbe essere eliminata una voce che
    risulta l'ultima ad avere quel nome: percio', si necessita la rimozione del 
    nome in questione anche dal menu a tendina del campo Cerca.
    La prima voce del menu a tendina e' rappresentata da una stringa vuota
    (una String null in codice), che consente di visualizzare le voci di
    qualsiasi nome.
03) Contiene il nome selezionato dal menu a tendina.
04) Viene inizializzato l'ObservableList<String>, al momento privo di elementi.
    Verra' riempito con il metodo aggiorna(), invocato dalla classe
    InterfacciaPortafoglio.
05) Si associa al menu a tendina l'ObservableList<String> contenente i nomi
    delle voci del portafoglio.
06) Si stabilisce una larghezza fissa per il menu a tendina.
07) Listener per la variazione di voce del menu a tendina selezionata.
08) Viene aggiornata la voce selezionata nel menu a tendina.
09) Viene aggiornato lo Storico, mostrando soltanto le voci con nome uguale a
    quello selezionato nel menu a tendina Cerca (o le voci di qualsiasi nome,
    se la voce selezionata dal menu a tendina e' la prima, vuota).
10) Viene aggiornato anche l'Andamento, mostrando il Saldo totale nelle date
    relative alle sole voci mostrare in quel momento nello Storico.
11) Aggiorna il contenuto del menu a tendina con i nomi delle voci presenti
    nel database.
12) Il metodo che aggiorna il contenuto del menu a tendina con i nomi delle
    voci presenti nel database agisce direttamente sull'ObservableList<String>
    della classe Cerca.
13) Imposta lo stile della sezione Cerca.
14) Imposta il font del titolo della sezione, prelevato dal file XML di
    configurazione.

*/