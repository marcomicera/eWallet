import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class Periodo { // 00)
    final Label titolo;
    final Label dal;
    private final DatePicker dataInizio; // 01)
    final Label al;
    private final DatePicker dataFine; // 02)
    private final EventHandler<ActionEvent> dataHandler; // 03)
    
    public Periodo(InterfacciaPortafoglio interfacciaPortafoglio) {
        titolo = new Label("Periodo");
        dal = new Label("Dal");
        dataInizio = new DatePicker();
        al = new Label("al");
        dataFine = new DatePicker();
        
        dataHandler = (ActionEvent e) -> { // 03)
            GestoreDatabase.ottieniVoci( // 04)
                interfacciaPortafoglio.getStorico().getVoci(),
                dataInizio.getValue(),
                dataFine.getValue(),
                interfacciaPortafoglio.getBarraNavigazione().getCerca().getVoceSelezionata()
            );
            GestoreDatabase.ottieniAndamento( // 05)
                interfacciaPortafoglio.getAndamento().getSerie().getData(),
                dataInizio.getValue(),
                dataFine.getValue(),
                interfacciaPortafoglio.getBarraNavigazione().getCerca().getVoceSelezionata()
            );
        };
        
        dataInizio.setOnAction(dataHandler); // 06)
        dataFine.setOnAction(dataHandler); // 06)
    }
    
    public void impostaStile(String font, double dimensioneFont) { // 07)
        titolo.setFont(Font.font(font, dimensioneFont)); // 08)
        dal.setFont(Font.font(font, dimensioneFont)); // 08)
        al.setFont(Font.font(font, dimensioneFont)); // 08)
        dataInizio.setPrefWidth(105); // 09)
        dataFine.setPrefWidth(105); // 09)
    }
    
    public DatePicker getDataInizio() { return dataInizio; }
    public DatePicker getDataFine() { return dataFine; }
}

/* Note

00) Sezione dell'Barra Di Navigazione che consente di specificare il Periodo
    per il quale visualizzare le Voci.
01) Selezionatore di data per specificare la Data Di Inizio del Periodo
    temporale per il quale si desidera visualizzare le Voci.
02) Selezionatore di data per specificare la Data Di Fine del Periodo
    temporale per il quale si desidera visualizzare le Voci.
03) EventHandler<ActionEvent> relativo al cambiamento di data selezionata da
    uno dei due DatePicker.
04) Ad ogni cambio di data, occorre aggiornare il contenuto dello Storico.
05) Ad ogni cambio di data, occorre aggiornare il contenuto dell'Andamento.
06) L'EventHandler e' uguale per i due DatePicker.
07) Imposta lo stile della sezione Periodo.
08) Imposta il font prelevato dal file XML di configurazione.
09) Imposta una larghezza fissata ai due DatePicker.

*/