import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Hatebook {

  public static void main(String[] args) throws IOException {
    List<Usuari> usuaris = new LinkedList<>();
    List<Moderador> moderadors = new LinkedList<>();
    llegirUsuaris("./src/usuaris.csv", usuaris, moderadors);
    System.out.println(usuaris);
    System.out.println(moderadors);

    {
      // comprovar paraules
      final Paraula paraula = new Paraula("normal");
      final Paraula bipolar = new ParaulaBipolar("bipolar", 30);
      final Paraula marejada = new ParaulaMarejada("marejada");
      final Paraula grollera = new ParaulaGrollera("dolenta", 66);

      System.out.println(paraula);
      System.out.println(marejada);
      for (int i = 0; i < 10; i++) {
        System.out.println(i + ": " + bipolar + " | " + grollera);
      }
    }

    {
      // comprovar relacions
      final Usuari tom = new Usuari("tomsimons123", "tom", "simons");
      final Usuari test = new Usuari("test123", "test", "ing");

      System.out.println(tom.tipusRelacio(test));
      tom.canviarRelacioUsuari(test, TipusRelacio.AMIC);
      System.out.println(tom.tipusRelacio(test));
      tom.canviarRelacioUsuari(test, TipusRelacio.SALUDAT);
      System.out.println(tom.tipusRelacio(test));
      tom.canviarRelacioUsuari(test, TipusRelacio.RES);
      System.out.println(tom.tipusRelacio(test));
    }
  }

  public static void llegirUsuaris(String filename, List<Usuari> usuaris, List<Moderador> moderadors) throws IOException {
    try (final BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
      br.readLine(); // ens saltem el header

      String line;
      while ((line = br.readLine()) != null) {
        final String[] args = line.split(",");
        final String nomUsuari = args[0];
        final String nom = args[1];
        final String cognoms = args[2];
        final String rawNoderador = args[3];

        if (rawNoderador.equalsIgnoreCase("no")) {
          usuaris.add(new Usuari(nomUsuari, nom, cognoms));
        } else {
          moderadors.add(new Moderador(nomUsuari, nom, cognoms));
        }
      }
    }
  }

}
