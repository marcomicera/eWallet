import java.sql.*;
import java.time.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.chart.*;

public class GestoreDatabase { // 00)
    public static void ottieniVoci(ObservableList<Voce> voci) { // 01)
        ottieniVoci(voci, null, null, null); // 02)
    }
    
    public static void ottieniVoci(ObservableList<Voce> voci, LocalDate inizio, LocalDate fine, String cerca) { // 03)
        String query = costruisciQuery("SELECT * FROM portafoglio", inizio, fine, cerca); // 04)
        
        try(Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/portafoglio?user=root&password=root"); // 05)
            PreparedStatement ps = co.prepareStatement(query); // 06)
        ) {
            int i = 1; // 07)
            if(inizio != null && fine != null) { // 08)
                ps.setString(i++, inizio.toString()); // 09)
                ps.setString(i++, fine.toString()); // 09)
            }
            if(cerca != null) // 10)
                ps.setString(i, cerca); // 09)
            ResultSet rs = ps.executeQuery(); // 11)
            voci.clear(); // 12)
            while(rs.next()) // 13)
                voci.add(new Voce(rs.getInt("id"), rs.getString("nome"), rs.getDate("data"), rs.getDouble("importo"))); // 14)
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
        
    public static void ottieniNomiVoci(ObservableList<String> voci) { // 15)
        try(Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/portafoglio?user=root&password=root"); // 05)
            Statement st = co.createStatement(); // 16)
        ) {
            ResultSet rs = st.executeQuery("SELECT DISTINCT(nome) FROM portafoglio ORDER BY nome"); // 11)
            voci.clear(); // 17)
            voci.add(null); // 18)
            while(rs.next()) // 13)
                voci.add(rs.getString("nome")); // 19)
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void eliminaVoce(InterfacciaPortafoglio interfacciaPortafoglio) { // 20)
        Voce voce = interfacciaPortafoglio.getStorico().getTabella().getSelectionModel().getSelectedItem(); // 21)
        
        try(Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/portafoglio?user=root&password=root"); // 05)
            PreparedStatement ps = co.prepareStatement("DELETE FROM portafoglio WHERE id = ?"); // 06)
        ) { 
            ps.setInt(1, voce.getId()); // 22)
            ps.executeUpdate(); // 23)

            interfacciaPortafoglio.getStorico().getVoci().remove(voce); // 24)
            interfacciaPortafoglio.getStorico().getTabella().getSelectionModel().clearSelection(); // 25)

            boolean ultimaVoceConQuestoNome = true; // 26)
            ObservableList<String> nomiVoci = FXCollections.observableArrayList(); // 27)
            ottieniNomiVoci(nomiVoci); // 27)
            for(String s: nomiVoci) { // 28)
                if(s == null) continue; // 29)
                if(voce.getNome().compareTo(s) == 0) { // 30)
                    ultimaVoceConQuestoNome = false;
                    break;
                }
            }
            if(ultimaVoceConQuestoNome) { // 31)
                interfacciaPortafoglio.getBarraNavigazione().getCerca().getVoci().remove(voce.getNome());
                interfacciaPortafoglio.getBarraNavigazione().getCerca().getMenu().getSelectionModel().select(0);
                
            }
            ottieniAndamento( // 32)
                interfacciaPortafoglio.getAndamento().getSerie().getData(),
                interfacciaPortafoglio.getBarraNavigazione().getPeriodo().getDataInizio().getValue(),
                interfacciaPortafoglio.getBarraNavigazione().getPeriodo().getDataFine().getValue(),
                interfacciaPortafoglio.getBarraNavigazione().getCerca().getVoceSelezionata()
            );
            interfacciaPortafoglio.getBarraNavigazione().getSaldo().aggiornaSaldo(); // 33)
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static double ottieniSaldo() { // 34)
        try(Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/portafoglio?user=root&password=root"); // 05)
            Statement st = co.createStatement(); // 16)
        ) {
            ResultSet rs = st.executeQuery("SELECT ROUND(SUM(importo), 2) AS saldo FROM portafoglio"); // 11)
            rs.next(); // 35)
            return rs.getDouble("saldo"); // 34)
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return 0.00; // 36)
    }
    
    public static void ottieniAndamento(ObservableList<XYChart.Data<String, Double>> voci) { // 37)
        ottieniAndamento(voci, null, null, null); // 38)
    }
    
    public static void ottieniAndamento(ObservableList<XYChart.Data<String, Double>> voci, LocalDate inizio, LocalDate fine, String cerca) { // 39)
        String query = costruisciQuery( // 04)
            "SELECT DISTINCT(data), (SELECT ROUND(SUM(importo), 2) FROM portafoglio P2 WHERE P2.data <= P1.data) AS saldo FROM portafoglio P1",
            inizio, fine, cerca
        );
        
        try(Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/portafoglio?user=root&password=root"); // 05)
            PreparedStatement ps = co.prepareStatement(query); // 06)
        ) {
            int i = 1; // 07)
            if(inizio != null && fine != null) { // 08)
                ps.setString(i++, inizio.toString()); // 09)
                ps.setString(i++, fine.toString()); // 09)
            }
            if(cerca != null) // 10)
                ps.setString(i, cerca); // 09)
            ResultSet rs = ps.executeQuery(); // 11)
            voci.clear(); // 40)
            while(rs.next()) // 13)
                voci.add(new XYChart.Data<>(rs.getString("data"), rs.getDouble("saldo"))); // 41)
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void inserisciVoci(ArrayList<Voce> voci) { // 42)
        if(voci != null && voci.size() > 0) { // 43)
            String query = "INSERT INTO portafoglio VALUES " + new String(new char[voci.size()]).replace("\0", "(?, ?, ?, ?),"); // 44)
            query = query.substring(0, query.length() - 1); // 45)
            
            try(Connection co = DriverManager.getConnection("jdbc:mysql://localhost:3306/portafoglio?user=root&password=root"); // 05)
                PreparedStatement ps = co.prepareStatement(query); // 06)
            ) {
                int i = 1; // 46)
                for(Voce v: voci) {
                    ps.setInt(i++, v.getId()); // 09)
                    ps.setString(i++, v.getNome()); // 09)
                    ps.setDouble(i++, v.getImporto()); // 09)
                    ps.setDate(i++, v.getData()); // 09)
                }
                ps.executeUpdate(); // 47)
            } catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public static String costruisciQuery(String inizioQuery, LocalDate inizio, LocalDate fine, String cerca) { // 04)
        String query = inizioQuery; // 48)
        if((inizio != null && fine != null) || cerca != null) // 49)
            query += " WHERE";
        if(inizio != null && fine != null) { // 50)
            query += " data BETWEEN ? AND ?";
            if(cerca != null)
                query += " AND";
        }
        if(cerca != null) // 51)
            query += " nome = ?";
        
        return query + " ORDER BY data"; // 52)
    }
}

/* Note

00) Classe che si occupa di gestire tutte le query verso il database contenente
    tutte le Voci del Portafoglio.
01) Riempie l'ObservableList<Voce> passato come parametro con tutte le voci
    presenti nel database.
02) Si tratta di overloading della funzione ottieniVoci(), specificando come
    "null" le date dell'intervallo temporale desiderato e del nome del campo
    Cerca.
03) Riempie l'ObservableList<Voce> passato come primo parametro con tutte le
    voci appartenenti al periodo temporale che va da "inizio" a "fine", il cui
    nome e' uguale a quello specificato da "cerca".
04) Funzione di utility che permette di costruire una query a partire da uno
    statement di base come primo parametro (inizioQuery), aggiungendo eventuali
    clausole WHERE in base al periodo selezionato o al nome selezionato nel
    campo Cerca.
05) Viene aperta una connessione con il database.
06) Inizializzazione di una query SQL parametrica con la query specificata
    come parametro.
07) Indice che serve per scorrere le eventuali incognite della query
    parametrica. La clausola WHERE potrebbe non essere presente nel caso in cui
    venga effettuata una richiesta non filtrata; se presente, invece, potrebbe
    riguardare il periodo, il nome, o entrambi.
08) Se sono presenti entrambe le date, la clausola WHERE riguarda sicuramente
    il periodo temporale.
09) Vengono impostati i parametri delle query con gli opportuni valori.
10) Se e' presente il nome selezionato dal campo Cerca, la clausola WHERE
    riguarda sicuramente il filtraggio per nome.
11) Viene eseguita la query, il cui risultato viene inserito in un oggetto di
    tipo ResultSet.
12) L'ObservableList<Voce> dello Storico viene svuotata completamente, pronta
    ad accogliere le voci filtrate opportunamente.
13) Ciclo while che scorre il ResultSet contenente la risposta del database.
14) Le voci vengono aggiunte all'ObservableList<Voce> dello Storico, utilizzando
    il costruttore della classe Voce.
15) Ottiene tutti i nomi diversi delle voci presenti nel database.
    Serve a fornire i nomi da inserire nel menu a tendina del campo Cerca.
16) Inizializzazione di una query SQL.
17) L'ObservableList<String> della classe Cerca viene svuotata completamente,
    pronta ad accogliere tutti i nomi diversi delle voci presenti nel database.
18) Viene aggiunto come primo elemento una String di valore null, che si
    presenta visivamente come una voce vuota, che permette di non specificare
    nessun tipo di filtro per nome.
19) I nomi vengono aggiunti all'ObservableList<String> della classe Cerca.
20) Elimina dal database la riga selezionata nello Storico.
    Il metodo viene invocato una volta che l'utente conferma l'operazione di
    eliminazione tramite un secondo click sul tasto di eliminazione.
21) Viene prelevata la Voce attualmente selezionata nello Storico, che dovra'
    essere eliminata dal database.
22) Viene imposto l'ID della Voce da eliminare.
    Viene usato l'ID come identificatore unico di una Voce, in quanto gli altri
    dati non possono essere in grado di identificare univocamente una Voce.
23) Viene eseguita l'eliminazione della suddetta Voce.
24) Viene rimossa la Voce appena eliminata dal database dall'ObservableList<Voce>
    dello Storico, in modo da non essere piu' mostrata dall'Interfaccia Grafica.
25) Viene annullata la selezione della Voce appena eliminata. Senza questa
    operazione, risulterebbe selezionata la Voce che prende il posto della Voce
    eliminata nella tabella.
26) Occorre verificare se la Voce appena eliminata sia stata l'ultima Voce
    presente nel database con quel nome. In caso affermativo, va rimossa la sua
    entrata dal campo Cerca.
27) Si ottengono tutti i nomi diversi delle voci presenti nel database dopo la
    eliminazione per essere in grado di determinare se la Voce appena eliminata
    sia stata l'ultima ad avere quel nome.
28) Si ricerca il nome della Voce appena eliminata nella lista dei nomi delle
    voci presenti nel database.
29) Il primo elemento di questa lista e' sempre una String di valore null, che
    permette all'utente di non filtrare le voci per nome.
    Pertanto, esso va ignorato, saltando alla successiva iterazione grazie alla
    istruzione continue.
30) Se e' ancora presente una voce con il nome della voce appena eliminata,
    la relativa entrata del menu a tendina del campo Cerca non va eliminata.
31) Viene eliminata l'entrata del menu a tendina del campo Cerca solo se la voce
    appena eliminata era l'ultima ad avere quel nome.
32) Viene aggiornato l'Andamento.
33) Viene aggiornato il Saldo.
34) Restituisce il Saldo totale del Portafoglio.
35) Si necessita della chiamata della next() di ResultSet per spostare il
    cursore rs di una riga.
    https://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html#next()
36) In caso di errore, restituisce un Saldo complessivo di 0.00 €.
37) Aggiorna l'ObservableList<XYChart.Data<String, Double>> dell'Andamento
    con le coppie nome-importo di tutte le voci presenti nel database.
38) Si tratta di overloading della metodo generale che consente di specificare
    anche il periodo temporale selezionato e la entrata selezionata del campo
    Cerca.
39) Aggiorna l'ObservableList<XYChart.Data<String, Double>> dell'Andamento
    con le coppie nome-importo delle voci nel database appartenenti al periodo
    di tempo selezionato, con nome specificato nel campo Cerca.
40) Viene svuotata l'ObservableList<XYChart.Data<String, Double>> dell'Andamento,
    pronta ad accogliere i nuovi punti del grafico eventualmente filtrati.
41) Viene aggiornata la ObservableList<XYChart.Data<String, Double>> della
    classe Andamento con la risposta fornita dal database
42) Permette l'inserimento di nuove voci, specificate come parametro.
    Questo metodo viene invocato all'avvio dell'applicazione se il file XML 
    delle nuove voci viene validato correttamente.
43) Se il file XML delle nuove voci e' stato trovato e contiene almeno una voce.
44) Viene formata la query che permette l'inserimento delle nuove voci.
    Piccolo miglioramento: per evitare un ciclo for che inserisse tante stringhe
    "(?, ?, ?, ?)," quante fossero le voci da inserire, viene prima creato
    un array di char vuoto (contenente il carattere di fine stringa: '\0'),
    viene poi convertito ad un'unica String (che contiene tanti caratteri
    di fine stringa quante sono le voci da inserire), e successivamente vengono
    rimpiazzati i caratteri di fine stringa con la stringa "(?, ?, ?, ?),".
    Idea presa da:
    http://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string-in-java
45) Si elimina l'ultima virgola della query che separerebbe le varie stringhe
    "(?, ?, ?, ?),", per evitare un errore di sintassi SQL.
46) Occorre un indice per scorrere i parametri della query parametrica.
47) Vengono inserite le nuovi voci nel database.
48) Statement di base: in caso di assenza di filtri, corrisponde alla query
    finale.
49) La clausola WHERE dev'essere presente se e' presente almeno uno dei due
    filtri (temporale o per nome).
50) Si applica il filtro temporale solo se entrambe le date sono state impostate.
51) Viene applicato il filtro in base al nome.
52) Si restituisce il risultato ordinato per data.

*/