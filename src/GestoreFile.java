import java.io.*;
import java.nio.file.*;

public class GestoreFile { // 00)
    private final static String PERCORSO_RELATIVO = "..\\..\\"; // 01)
    
    public static void salva(Object o, String file, boolean append) { // 02)
        try {
            Files.write( // 03)
                (file.compareTo("input.xml") == 0) ? Paths.get(file) : Paths.get(PERCORSO_RELATIVO + file), // 04)
                o.toString().getBytes(), 
                (append) ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING // 05)
            );
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void svuota(String file) { // 06)
        salva("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!-- " + file + " -->\n", file, false); // 07)
    }
    
    public static String carica(String file) { // 08)
        try {
            return new String(Files.readAllBytes(Paths.get(file))); // 09)
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null; // 10)
    }
}

/* Note

00) Classe che si occupa di scrivere e leggere dati da file.
01) GestoreFile.class si trova in \Portafoglio\build\classes
    (dove \Portafoglio\ e' la directory root dell'applicazione), mentre i file
    in cui e' necessario scrivere (XML e binari) si trovano nella root dell'
    applicazione.
02) Permette di salvare un Object o nel file indicato dalla String file.
    Il terzo parametro "append" consente di specificare se la scrittura va
    eseguita appunto in modalita' append o in modalita' di sovrascrittura.
03) Viene eseguita la scrittura.
04) Il primo parametro indica il nome del file.
    Normalmente, i file si trovano nella directory padre della directory padre,
    percio' si necessita di anteporre il percorso relativo "..\..\", ad
    eccezione del file XML delle nuove voci, che e' scritto (svuotato) dalla
    classe Portafoglio, che e' eseguita dalla root dell'applicazione.
05) Si specifica la modalita' di scrittura in base al valore dell'ultimo
    parametro.
    https://docs.oracle.com/javase/7/docs/api/java/nio/file/StandardOpenOption.html
06) Permette di svuotare un file XML.
07) Lo svuotamento di un file XML consiste semplicemente nella sovrascrittura
    del file, inserendo l'intestazione.
08) Carica il contenuto di un file e lo restituisce come String.
    Viene invocato per leggere i Parametri Di Configurazione dal relativo file
    XML e anche per leggere il file XML delle nuove voci.
09) Viene eseguita la lettura.
10) Il metodo ritorna null nel caso la lettura non sia andata a buon fine.

*/