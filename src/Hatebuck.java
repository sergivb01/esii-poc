import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Hatebuck {

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.out.println("Us: java Hatebuck /path/to/usuaris.csv /path/to/relacions.csv");
      return;
    }

    final String nomFitxerUsuaris = args[0];
    final String nomFitxerRelacions = args[1];

    List<Usuari> usuaris = new LinkedList<>();
    List<Moderador> moderadors = new LinkedList<>();
    llegirUsuaris(nomFitxerUsuaris, usuaris, moderadors);

    final LinkedList<Usuari> tots = new LinkedList<>(usuaris);
    tots.addAll(moderadors);
    llegirRelacions(nomFitxerRelacions, tots);

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

    for (Usuari usu : tots) {
      System.out.println(usu);
    }
  }

  private static void llegirUsuaris(String filename, List<Usuari> usuaris, List<Moderador> moderadors) throws IOException {
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

  private static void llegirRelacions(String filename, List<Usuari> usuaris) throws IOException {
    try (final BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
      br.readLine(); // ens saltem el header

      String line;
      while ((line = br.readLine()) != null) {
        final String[] args = line.split(",");
        final Usuari usuari1 = obtenirUsuari(usuaris, args[0]);
        final Usuari usuari2 = obtenirUsuari(usuaris, args[1]);
        final TipusRelacio tipusRelacio = TipusRelacio.desdeNom(args[2]);

        usuari1.canviarRelacioUsuari(usuari2, tipusRelacio);
      }
    }
  }

  private static Usuari obtenirUsuari(final List<Usuari> usuaris, final String nom) {
    for (Usuari usuari : usuaris) {
      if (usuari.nomUsuari().equalsIgnoreCase(nom)) {
        return usuari;
      }
    }
    throw new IllegalArgumentException("L'usuari amb nomusuari '" + nom + "' no existeix");
  }


}
