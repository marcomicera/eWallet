import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class Saldo { // 00)
    final Label titolo;
    Label saldo;
    
    public Saldo() {
        titolo = new Label("Saldo");
        saldo = new Label("0,00 €");
        aggiornaSaldo(); // 01)
    }
    
    public final void aggiornaSaldo() { // 01)
        saldo.setText(GestoreDatabase.ottieniSaldo() + " €"); // 02)
    }
    
    public void impostaStile(String font, double dimensioneFont) { // 03)
        titolo.setFont(Font.font(font, dimensioneFont)); // 04)
        saldo.setFont(Font.font(font, FontWeight.BOLD, dimensioneFont + 10)); // 05)
        saldo.setPadding(new Insets(-3, 0, 0, 70)); // 06)
    }
}

/* Note

00) Sezione della Barra Di Navigazione che mostra il Saldo totale del Portafoglio.
01) Ottiene il valore del Saldo totale dal database.
02) Il metodo invoca GestoreDatabase.ottieniSaldo() che effettua la giusta query
    al database, restituendo il valore del Saldo totale.
03) Imposta lo stile della sezione Saldo.
04) Imposta il font del titolo.
05) Imposta lo stile e il font della Label del Saldo, rendendola in grassetto,
    e di dimensione maggiore.
06) Imposta il valore della spaziatura attorno alla Label del Saldo per
    allinearla al titolo della sezione, grazie al metodo setPadding() di Label:
    https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/Region.html#setPadding-javafx.geometry.Insets-
    Come parametro necessita di un oggetto di tipo Insets, che richiede come
    parametri la spaziatura top, right, bottom e left:
    https://docs.oracle.com/javase/8/javafx/api/javafx/geometry/Insets.html#Insets-double-double-double-double-

*/