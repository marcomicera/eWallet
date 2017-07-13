import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class Andamento { // 00)
    private final static int SPAZIATURA = 10; // 01)
    private final VBox contenitore;
    private final Label titolo;
    private final LineChart<String, Number> grafico; // 02)
    private final XYChart.Series serie; // 03)
    
    public Andamento() {
        contenitore = new VBox(SPAZIATURA);
        titolo = new Label("Andamento");
        serie = new XYChart.Series(); // 04)
        grafico = new LineChart<>(new CategoryAxis(), new NumberAxis()); // 05)
        grafico.getData().add(serie); // 06)
        grafico.setAnimated(false); // 07)
        grafico.setLegendVisible(false); // 08)
        
        contenitore.getChildren().addAll(titolo, grafico);
    }
    
    public void impostaStile(String font, double dimensioniFont, int dimensioneSezione) { // 09)
        titolo.setFont(Font.font(font, dimensioniFont + dimensioneSezione)); // 10)
        grafico.setPrefSize(500, 300); // 11)
    }
    
    public void aggiorna() { // 12)
        GestoreDatabase.ottieniAndamento(serie.getData()); // 12)
    }
    
    public void aggiorna(BarraNavigazione barraNavigazione) { // 13)
        GestoreDatabase.ottieniAndamento(
            serie.getData(),
            barraNavigazione.getPeriodo().getDataInizio().getValue(),
            barraNavigazione.getPeriodo().getDataFine().getValue(),
            barraNavigazione.getCerca().getVoceSelezionata()
        );
    }
    
    public VBox getContenitore() { return contenitore; }
    public XYChart.Series getSerie() { return serie; }
}

/* Note

00) Sezione dell'Interfaccia Grafica che mostra l'Andamento del Saldo nel tempo,
    relativo alle sole date delle voci visualizzate in quel momento.
01) Spaziatura tra il titolo dell'Andamento e il grafico.
02) Il grafico riporta l'Andamento del Saldo nel tempo.
    Sull'asse delle ascisse sono presenti le date relative alle voci
    visualizzate, e sull'asse delle ordinate e' presente il valore dell'
    importo delle voci, in euro.
03) Contiene una ObservableList di elementi XYChart.Data<String, Double>, che
    rappresentano coppie di Data-Importo da mostrare nel grafico.
    https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/XYChart.Data.html
04) Viene inizializzata la serie, al momento vuota.
05) Viene creato il grafico con un asse delle ordinate destinato a riportare
    valori numerici e con un asse delle ascisse destinato a riportare stringhe.
    https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/CategoryAxis.html
06) Si associa al grafico la serie delle coppie Data-Importo.
07) Si e' resa necessaria la disabilitazone delle animazioni per evitare
    glitch grafici ad ogni cambio di dati che interessasse il grafico.
08) Si nasconde la legenda in quanto e' presente solo una serie di valori,
    per cui non si ha ambiguita'.
09) Imposta lo stile dei questa sezione.
10) Imposta il font del titolo di questa sezione, prelevato dal file XML di
    configurazione.
11) Si stabiliscono le dimensioni del grafico.
12) Aggiorna l'Andamento con tutte le voci del database.
13) Aggiorna l'Andamento con le soli voci appartenenti al periodo selezionato,
    con lo stesso nome selezionato nella sezione Cerca: per questo si
    necessita la Barra Di Navigaizone come parametro.

*/