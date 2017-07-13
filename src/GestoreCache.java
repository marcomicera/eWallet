import java.io.*;

public class GestoreCache { // 00)
    public final static void salva(InterfacciaPortafoglio interfacciaPortafoglio, String file) { // 01)
        try(ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file))) { // 02)
            oout.writeObject(new Cache(interfacciaPortafoglio)); // 03)
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public final static Cache carica(String file) { // 04)
        try(ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))) { // 05)
            return (Cache)oin.readObject(); // 06)
        } catch(IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return null; // 07)
    }
}

/* Note

00) Classe che gestisce la lettura e la scrittura della cache locale degli input.
01) Salva la cache degli input prelevata dal parametro interfacciaPortafoglio
    nel file indicato da String file.
02) Flusso oggetto agganciato al flusso file per salvare la cache degli input.
03) Scrittura dell'oggetto Cache nel file binario.
    L'oggetto Cache viene creato sul momento, prelevando gli input presenti
    nell'Interfaccia Grafica.
04) Carica dal file binario, la cache degli input relativa ad una esecuzione
    precedente. Questo metodo verra' chiamato ad ogni avvio dell'applicazione.
05) Flusso oggetto agganciato al flusso file per carica la cache degli input.
06) Il metodo restituisce l'oggetto Cache letto dal file binario, che verra'
    successivamente usato per ripristinare lo stato dell'Interfaccia Grafica.
07) In caso di errore (file binario non trovato o file Cache.class non trovato),
    il metodo restituisce null. In tal caso, la classe InterfacciaPortafoglio
    non ripristinera' alcuna cache degli input.

*/