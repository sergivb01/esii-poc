import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hatebuck {

  private static final Pattern PATTERN_WORDS = Pattern.compile("(\\w+)?(\\W+)?"); // match paraula i no-paraula
  private static final int[] PERCENTATGES = new int[]{25, 33, 50, 66, 75};

  public static void main(String[] args) throws IOException {
    if (args.length != 3) {
      System.out.println("Us: java Hatebuck /path/to/usuaris.csv /path/to/relacions.csv /path/to/publicacions.csv");
      return;
    }

    final String nomFitxerUsuaris = args[0];
    final String nomFitxerRelacions = args[1];
    final String nomFitxerPublicacions = args[2];

    final List<Usuari> usuaris = new LinkedList<>();
    llegirUsuaris(nomFitxerUsuaris, usuaris);
    llegirRelacions(nomFitxerRelacions, usuaris);
    llegirPublicacions(nomFitxerPublicacions, usuaris);

    final List<Moderador> moderadors = usuaris.stream()
        .filter(Moderador.class::isInstance)
        .map(Moderador.class::cast)
        .collect(Collectors.toList());

    System.out.println("Usuaris: " + usuaris);
    System.out.println("Moderadors: " + moderadors);

    final Scanner scan = new Scanner(System.in);

    mostrarMenu();
    int opcio = scan.nextInt();
    scan.nextLine();
    while (opcio != 0) {
      switch (opcio) {
        case 1: {
          System.out.println("Escull l'usuari que vols ser:");
          final Usuari usuari = seleccionar(scan, usuaris);

          final LinkedList<Usuari> usuaris2 = new LinkedList<>(usuaris);
          usuaris2.remove(usuari);

          System.out.println("Escull a qui li vols enviar un missatge:");
          final Usuari destinatari = seleccionar(scan, usuaris2);

          System.out.println("Entra el missatge a enviar:");

          final String text = scan.nextLine();
          final List<Paraula> paraules = convertirParaules(text);

          System.out.println("Vols enviar notificacio? [si/no]");
          final String enviarNoti = scan.nextLine();

          final MissatgePrivat missatgePrivat = usuari.enviarMissatgePrivat(destinatari, paraules);
          System.out.println("Missatge \"" + missatgePrivat.contingut() + "\" enviat a " + destinatari);
          if (enviarNoti.equalsIgnoreCase("si")) {
            System.out.println("[" + destinatari.nomUsuari() + "] Has rebut un nou missatge de " + usuari.nomUsuari());
          }
          break;
        }
        case 2: {
          System.out.println("Escull el moderador que vols ser:");

          final Moderador moderador = seleccionar(scan, moderadors);

          System.out.println("Escull l'usuari de qui vols modificar una publicació:");
          final List<Usuari> usuarisAmbPublicacions = usuaris.stream()
              .filter(usuari -> !usuari.publicacions().isEmpty())
              .collect(Collectors.toList());

          final Usuari usuari = seleccionar(scan, usuarisAmbPublicacions);
          final List<Publicacio> publicacionsUsuari = usuari.publicacions();

          System.out.println("Escull la publicació que vols modificar:");
          final Publicacio publicacio = seleccionar(scan, publicacionsUsuari);
          final String original = publicacio.contingut();

          System.out.println("Entra el nou missatge: ");
          final String text = scan.nextLine();

          final List<Paraula> paraules = convertirParaules(text);
          moderador.modificarPublicacio(publicacio, paraules);

          System.out.println(
              "Publicacio de l'usuari " + usuari.nomUsuari() + " modificada: '" + original + "' a '" + publicacio.contingut()
                  + "'");

          break;
        }
        case 3: {
          System.out.println("Escull l'usuari que vols ser:");
          final Usuari usuari1 = seleccionar(scan, usuaris);

          System.out.println("Escull l'usuari amb qui vols establir o actualitzar una nova relació:");

          final LinkedList<Usuari> usuaris2 = new LinkedList<>(usuaris);
          usuaris2.remove(usuari1);
          final Usuari usuari2 = seleccionar(scan, usuaris2);

          System.out.println("Escull el tipus de relació:");
          final TipusRelacio relacio = seleccionar(scan, TipusRelacio.values());

          usuari1.canviarRelacioUsuari(usuari2, relacio);
          System.out.println("Nova/actualitzada relacio amb " + usuari2.nomUsuari() + ": " + usuari1);
          System.out.println(usuari1.tipusRelacio(usuari2));
          break;
        }
        default:
          System.out.println("Opció incorrecta.");
          break;
      }

      mostrarMenu();
      opcio = scan.nextInt();
      scan.nextLine();
    }
  }

  private static void mostrarMenu() {
    System.out.println("=".repeat(25));
    System.out.println("Menú principal:");
    System.out.println("[0] Sortir");
    System.out.println("[1] Enviar missatge privat");
    System.out.println("[2] Modificar publicació");
    System.out.println("[3] Establir nova relació");
    System.out.println("=".repeat(25));
  }

  private static void llegirUsuaris(String filename, List<Usuari> usuaris) throws IOException {
    try (final BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
      br.readLine(); // ens saltem el header

      String line;
      while ((line = br.readLine()) != null) {
        final String[] args = line.split(";");
        final String nomUsuari = args[0];
        final String nom = args[1];
        final String cognoms = args[2];
        final String moderador = args[3];

        Usuari usuari;
        if (moderador.equalsIgnoreCase("si")) {
          usuari = new Moderador(nomUsuari, nom, cognoms);
        } else {
          usuari = new Usuari(nomUsuari, nom, cognoms);
        }
        usuaris.add(usuari);
      }
    }
  }

  private static void llegirRelacions(String filename, List<Usuari> usuaris) throws IOException {
    try (final BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
      br.readLine(); // ens saltem el header

      String line;
      while ((line = br.readLine()) != null) {
        final String[] args = line.split(";");
        final Usuari usuari1 = obtenirUsuari(usuaris, args[0]);
        final Usuari usuari2 = obtenirUsuari(usuaris, args[1]);
        final TipusRelacio tipusRelacio = TipusRelacio.desdeNom(args[2]);

        usuari1.canviarRelacioUsuari(usuari2, tipusRelacio);
      }
    }
  }

  private static void llegirPublicacions(String filename, List<Usuari> usuaris) throws IOException {
    try (final BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
      br.readLine(); // ens saltem el header

      String line;
      while ((line = br.readLine()) != null) {
        final String[] args = line.split(";");
        final Usuari usuari = obtenirUsuari(usuaris, args[0]);
        final String text = args[1];

        final List<Paraula> paraules = convertirParaules(text);
        usuari.afegirPublicacio(TipusRelacio.AMIC, paraules);
      }
    }
  }

  /**
   * Crea una cadena de diferents tipus aleatoris de {@link Paraula}
   *
   * @return una {@link List} de tipus aleatoris
   * @throws IllegalArgumentException si la línia està buida
   */
  private static List<Paraula> convertirParaules(final String linia) {
    if (linia.trim().isEmpty()) {
      throw new IllegalArgumentException("El text no pot estar buit");
    }

    final Random rand = new Random();
    final int percentatge = PERCENTATGES[rand.nextInt(PERCENTATGES.length)];

    // llista de funcions com a alternativa a https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/function/Supplier.html
    // amb paràmetres
    final List<Function<String, Paraula>> providers = List.of(
        Paraula::new,
        (text) -> new ParaulaBipolar(text, percentatge),
        (text) -> new ParaulaGrollera(text, percentatge),
        ParaulaMarejada::new
    );

    List<Paraula> resultat = new LinkedList<>();

    final Stream<MatchResult> results = PATTERN_WORDS.matcher(linia).results();
    results.forEach(matchResult -> {
      final String text = matchResult.group(1);
      final String separadors = matchResult.group(2);

      if (text != null) {
        final Function<String, Paraula> providerTipusParaula = providers.get(rand.nextInt(providers.size()));
        final Paraula paraula = providerTipusParaula.apply(text);
        resultat.add(paraula);
      }

      if (separadors != null) { // pot ser que no hi hagi separador al final
        resultat.add(new Paraula(separadors));
      }
    });

    return resultat;
  }

  public static <T> T seleccionar(final Scanner scan, final T[] opcions) {
    return seleccionar(scan, Arrays.asList(opcions));
  }

  public static <T> T seleccionar(final Scanner scan, final List<T> opcions) {
    if (opcions.size() == 1) {
      final T eleccio = opcions.get(0);
      System.out.println("Seleccionat automaticament l'unica opció: " + eleccio);
      return eleccio;
    }

    int i = 1;
    for (final T opcio : opcions) {
      System.out.println(" [" + i + "] " + opcio);
      i++;
    }

    System.out.println("Escull una opció [1-" + opcions.size() + "]");

    int n = scan.nextInt();
    scan.nextLine();
    while (n < 1 || n > opcions.size()) {
      System.out.println("Nombre invàlid. Entra un nombre entre " + 1 + "-" + opcions.size() + " (ambdós inclosos)");
      n = scan.nextInt();
      scan.nextLine();
    }

    return opcions.get(n - 1);
  }

  private static Usuari obtenirUsuari(final List<Usuari> usuaris, final String nom) {
    for (Usuari usuari : usuaris) {
      if (usuari.nomUsuari().equalsIgnoreCase(nom)) {
        return usuari;
      }
    }
    throw new IllegalArgumentException("L'usuari amb nom usuari '" + nom + "' no existeix");
  }

}
