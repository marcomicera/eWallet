import javafx.scene.layout.*;

public class BarraNavigazione { // 00)
    private final static int    SPAZIATURA_CONTENITORE = 120, // 01)
                                SPAZIATURA_SOTTOTITOLI = 260, // 02)
                                SPAZIATURA_BARRA_PERIODO = 5; // 03)
    private final HBox contenitore, sottotitoli, barraPeriodo;
    private final Periodo periodo; // 04)
    private final Cerca cerca; // 05)
    private final Saldo saldo; // 06)
    
    public BarraNavigazione(InterfacciaPortafoglio interfacciaPortafoglio) {
        contenitore = new HBox(SPAZIATURA_CONTENITORE);
        sottotitoli = new HBox(SPAZIATURA_SOTTOTITOLI);
        barraPeriodo = new HBox(SPAZIATURA_BARRA_PERIODO);
        periodo = new Periodo(interfacciaPortafoglio); // 04)
        cerca = new Cerca(interfacciaPortafoglio); // 05)
        saldo = new Saldo(); // 06)
        
        sottotitoli.getChildren().addAll(periodo.titolo, cerca.titolo, saldo.titolo);
        barraPeriodo.getChildren().addAll(periodo.dal, periodo.getDataInizio(), periodo.al, periodo.getDataFine());
        contenitore.getChildren().addAll(barraPeriodo, cerca.getMenu(), saldo.saldo);
    }
    
    public void impostaStile(String font, double dimensioneFont) { // 07)
        sottotitoli.setLayoutX(185);
        sottotitoli.setLayoutY(85);
        contenitore.setLayoutX(80);
        contenitore.setLayoutY(110);
        periodo.impostaStile(font, dimensioneFont);
        cerca.impostaStile(font, dimensioneFont);
        saldo.impostaStile(font, dimensioneFont);
    }
    
    public HBox getContenitore() { return contenitore; }
    public HBox getSottotitoli() { return sottotitoli; }
    public Periodo getPeriodo() { return periodo; }
    public Cerca getCerca() { return cerca; }
    public Saldo getSaldo() { return saldo; }
}

/* Note

00) Barra che consente all'utente di filtrare le voci per data e nome e di 
    visualizzare il Saldo totale.
01) Spaziatura tra la Barra del Periodo, la sezione Cerca e il Saldo.
02) Spaziatura tra i titoli delle tre sezioni.
03) Spaziatura tra gli elementi della Barra del Periodo, ovvero le due etichette
    e i due DatePicker.
04) Sezione dell'Interfaccia Grafica grazie alla quale e' possibile filtrare
    le Voci da visualizzare in base ad un periodo temporale.
05) Sezione dell'Interfaccia Grafica grazie alla quale e' possibile filtrare
    le Voci da visualizzare in base al nome selezionato.
06) Sezione dell'Interfaccia Grafica grazie alla quale e' possibile 
    visualizzare il Saldo totale del Portafoglio.
07) Imposta lo stile della Barra Di Navigazione.

*/