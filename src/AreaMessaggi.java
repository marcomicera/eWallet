import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class AreaMessaggi { // 00)
    private final static int    SPAZIATURA = 5, // 01)
                                SPAZIATURA_PULSANTI = 10; // 02)
    private final VBox contenitore;
    private final HBox pulsanti;
    private final Label titolo;
    private TextArea areaDiTesto; // 03)
    private Button elimina; // 04)
    private final Button annulla; // 05)
    private Voce voceSelezionata; // 06)
    private EventHandler<ActionEvent> eliminaHandler, confermaHandler; // 07)
    
    public AreaMessaggi(InterfacciaPortafoglio interfacciaPortafoglio, ParametriConfigurazioneXML config) {
        contenitore = new VBox(SPAZIATURA);
        pulsanti = new HBox(SPAZIATURA_PULSANTI);
        titolo = new Label("Area Messaggi");
        areaDiTesto = new TextArea("Selezionare una riga per eliminarla.");
        elimina = new Button("Elimina");
        annulla = new Button("Annulla");
        
        interfacciaPortafoglio.getStorico().getTabella().getSelectionModel().selectedItemProperty().addListener( // 08)
            (property, vecchiaVoce, nuovaVoce) -> { // 09)
                voceSelezionata = nuovaVoce; // 10)
                if(nuovaVoce != null) { // 11)
                    areaDiTesto.setText(
                        "Voce selezionata: " + nuovaVoce.getNome() + " di " +
                        nuovaVoce.getImporto() +  " del " +  nuovaVoce.getData() +  "."
                    );
                }
                else
                    areaDiTesto.setText("Per eliminare una voce, selezionarla dallo Storico."); // 12)
                
                if(elimina.getText().compareTo("Conferma") == 0) { // 13)
                    elimina.setText("Elimina");
                    elimina.setOnAction(eliminaHandler);
                }
            }
        );

        annulla.setOnAction( // 14)
            (ActionEvent ae) -> {
                GestoreLogAttivitaXML.creaLog("Pressione del pulsante annulla", config); // 15)
                
                if(elimina.getText().compareTo("Conferma") == 0) { // 16)
                    elimina.setText("Elimina");
                    elimina.setOnAction(eliminaHandler);
                    areaDiTesto.setText("Eliminazione annullata.");
                }
            }
        );
        eliminaHandler = (ActionEvent ae) -> { // 17)
            GestoreLogAttivitaXML.creaLog("Pressione del pulsante elimina", config); // 15)
            if(voceSelezionata == null) { // 18)
                areaDiTesto.setText("Per eliminare una voce, selezionarla dallo Storico.");
            }
            else {
                areaDiTesto.setText( // 19)
                    "Eliminare la voce " + voceSelezionata.getNome() + " di " +
                    voceSelezionata.getImporto() + " del " + voceSelezionata.getData() + "?"
                );
                elimina.setText("Conferma"); // 20)
                elimina.setOnAction(confermaHandler); // 21)
            }
        };
        confermaHandler = (ActionEvent ae) -> { // 22)
            GestoreLogAttivitaXML.creaLog("Pressione del pulsante conferma", config); // 15)
            GestoreDatabase.eliminaVoce(interfacciaPortafoglio); // 23)
            areaDiTesto.setText("Voce eliminata."); // 24)
        };
        elimina.setOnAction(eliminaHandler); // 25)
        
        pulsanti.getChildren().addAll(elimina, annulla);
        contenitore.getChildren().addAll(titolo, areaDiTesto, pulsanti);
    }
    
    public void impostaStile(ParametriConfigurazioneXML config, int dimensioneSezione) { // 26)
        areaDiTesto.setFont(Font.font(config.getFont(), config.getDimensioneFont())); // 27)
        areaDiTesto.setPrefHeight(config.getDimensioneFont() * 4); // 28)
        areaDiTesto.setEditable(false); // 29)
        titolo.setFont(Font.font(config.getFont(), config.getDimensioneFont() + dimensioneSezione)); // 27)
        elimina.setFont(Font.font(config.getFont(), config.getDimensioneFont())); // 27)
        annulla.setFont(Font.font(config.getFont(), config.getDimensioneFont())); // 27)
    }
    
    public VBox getContenitore() { return contenitore; }
    public TextArea getAreaDiTesto() { return areaDiTesto; }
}

/* Note

00) Area di testo che mostra messaggi all'utente, con due pulsanti per
    l'eliminazione delle voci.
01) Spaziatura tra il titolo della sezione, l'Area Di Testo e tra i pulsanti.
02) Spaziatura tra i pulsanti.
03) Area Di Testo con la quale si mostrano messaggi all'utente.
04) Pulsante che consente all'utente di eliminare una eventuale voce selezionata
    nello Storico.
05) Pulsante che consente di annullare l'operazione di eliminazione di una voce.
06) Voce selezionata con un click del mouse all'interno dello Storico.
07) Handlers per la gestione dei click sul pulsante "Elimina", che cambia
    la sua etichetta in "Conferma" ogni qual volta che viene clickato.
08) Listener per le variazioni di voci selezionate (SelectedItem).
    https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/SelectionModel.html#selectedItemProperty
    https://docs.oracle.com/javafx/2/api/javafx/beans/value/ObservableValue.html#addListener(javafx.beans.value.ChangeListener)
09) Definizione del metodo changed() dell'interfaccia ChangeListener<Voce>
    tramite lambda expression.
    https://docs.oracle.com/javafx/2/api/javafx/beans/value/ChangeListener.html
10) Si aggiorna il membro voceSelezionata grazie al parametro nuovaVoce fornito
    dal metodo changed().
11) Se la nuova Voce selezionata e' diversa da null (puo' esserlo nel caso in cui
    venga deselezionata una voce per qualsiasi motivo), vengono stampate
    le informazioni della voce nell'Area Messaggi.
12) Se la nuova Voce selezionata e' uguale a null, viene mostrata nell'Area
    Messaggi la modalita' con la quale e' possibile eliminare Voci.
13) Se viene selezionata una nuova voce nel corso di un'eliminazione,
    quest'ultima operazione viene annullata.
14) Viene assegnato un EventHandler<ActionEvent> al pulsante "Annulla", che
    permette l'annullamento di un'eliminazione.
15) Viene creato e inviato un log per ogni pulsante clickato.
16) Se era in corso un'eliminazione, la elimina e notifica l'annullamento
    dell'operazione nell'Area Messaggi.
17) EventHandler<ActionEvent> relativo al pulsante "Elimina", per iniziare
    la procedura di eliminazione.
18) Viene mostrato un messaggio nell'Area Messaggi qualora l'utente faccia
    click sul pulsante "Elimina" mentre nessuna Voce e' selezionata.
19) Viene stampato una richiesta di conferma per l'eliminazione della Voce
    selezionata.
20) Cambia l'etichetta del pulsante da "Elimina" a "Conferma".
21) Viene cambiato l'EventHandler<ActionEvent> relativo al pulsante che
    permette l'eliminazione.
22) EventHandler<ActionEvent> associato al click sul pulsante "Conferma",
    durante un'eliminazione.
23) Metodo che elimina la voce selezionata nello Storico dal database, e
    aggiorna di conseguenza tutte le sezioni dell'Interfaccia Grafica.
24) L'avvenuta eliminazione viene notificata nell'Area Messaggi.
25) All'avvio dell'applicazione non e' in corso un'eliminazione.
26) Viene imposto lo stile dell'intera Area Messaggi.
27) Viene impostato il font prelevato dal file XML di configurazione.
28) Viene impostata l'altezza dell'Area Di Testo in modo da permettere la
    visualizzazione di due righe.
29) Rende l'Area Di Testo non modificabile, in quanto ha la sola funzione
    di informare l'utente.

*/