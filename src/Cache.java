import java.io.*;
import java.time.*;

public class Cache implements Serializable { // 00)
    private final LocalDate dataInizio, dataFine; // 01)
    private final String cerca, areaMessaggi; // 02)
    private final int voceSelezionata; // 03)
    
    public Cache(InterfacciaPortafoglio interfacciaPortafoglio) { // 04)
        dataInizio = interfacciaPortafoglio.getBarraNavigazione().getPeriodo().getDataInizio().getValue();
        dataFine = interfacciaPortafoglio.getBarraNavigazione().getPeriodo().getDataFine().getValue();
        cerca = interfacciaPortafoglio.getBarraNavigazione().getCerca().getVoceSelezionata();
        areaMessaggi = interfacciaPortafoglio.getAreaMessaggi().getAreaDiTesto().getText();
        voceSelezionata = interfacciaPortafoglio.getStorico().getTabella().getSelectionModel().getSelectedIndex();
    }
    
    public LocalDate getDataInizio() { return dataInizio; }
    public LocalDate getDataFine() { return dataFine; }
    public String getCerca() { return cerca; }
    public String getAreaMessaggi() { return areaMessaggi; }
    public int getVoceSelezionata() { return voceSelezionata; }
}

/* Note

00) L'oggetto salva la cache locale degli input dell'utente.
01) La data di inizio e di fine relativo al periodo che l'utente desidera
    visualizzare.
02) La categoria di voci da visualizzare (il nome selezionato dal menu
    a tendina Cerca) e testo presente nell'Area Messaggi.
03) La voce selezionata nello Storico.
    E' sufficiente la memorizzazione dell'indice (un intero) in quanto lo Storico
    viene gia' precedentemente caricato con le voci relative al periodo
    selezionato, con nome uguale a quello immesso nel campo Cerca.
04) Il costruttore, invocato al momento del salvataggio della cache, e quindi
    alla chiusura dell'applicazione, salva tutti gli input dell'utente
    prelevandoli dall'oggetto interfacciaPortafoglio.

*/