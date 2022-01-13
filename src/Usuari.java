import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Usuari {

  private final String nomUsuari;
  private final String nom, cognoms;

  private final Map<Usuari, TipusRelacio> relacions;
  private final List<MissatgePrivat> missatgePrivats;
  private final List<Publicacio> publicacions;

  public Usuari(String nomUsuari, String nom, String cognoms) {
    this.nomUsuari = nomUsuari;
    this.nom = nom;
    this.cognoms = cognoms;
    this.relacions = new LinkedHashMap<>();
    this.missatgePrivats = new LinkedList<>();
    this.publicacions = new LinkedList<>();
  }

  /**
   * Canvia el {@link TipusRelacio} d'un {@link Usuari}
   */
  public void canviarRelacioUsuari(final Usuari usuari, final TipusRelacio tipus) {
    if (tipus == TipusRelacio.RES) {
      // si ja no tenen cap relació amb l'usuari, el podem treure per no emmagatzemar "buits"
      relacions.remove(usuari);
      return;
    }

    relacions.put(usuari, tipus);
  }

  /**
   * Envia un missatge privat a un {@link Usuari}
   *
   * @return el {@link MissatgePrivat} enviat
   * @throws IllegalArgumentException si aquest Usuari no pot enviar missatges privats a {@code destinatari}
   */
  public MissatgePrivat enviarMissatgePrivat(final Usuari destinatari, final List<Paraula> paraules) {
    if (false) {
      throw new IllegalArgumentException(nom() + " no pot enviar missatges privats a " + destinatari.nom());
    }

    final MissatgePrivat missatge = new MissatgePrivat(this, destinatari, paraules);

    missatgePrivats.add(missatge);
    destinatari.missatgePrivats.add(missatge);

    return missatge;
  }

  public void afegirPublicacio(final TipusRelacio visibilitat, final List<Paraula> paraules) {
    final Publicacio publicacio = new Publicacio(this, visibilitat, paraules);

    publicacions.add(publicacio);
  }

  /**
   * @return el {@link TipusRelacio} que es té amb {@code usuari}, {@link TipusRelacio#RES} si no tenen cap relació
   */
  public TipusRelacio tipusRelacio(final Usuari usuari) {
    return relacions.getOrDefault(usuari, TipusRelacio.RES);
  }

  public List<MissatgePrivat> missatgePrivats() {
    return Collections.unmodifiableList(this.missatgePrivats);
  }

  public String nomUsuari() {
    return this.nomUsuari;
  }

  public String nom() {
    return this.nom;
  }

  public List<Publicacio> publicacions() {
    return Collections.unmodifiableList(this.publicacions);
  }

  public String cognoms() {
    return this.cognoms;
  }

  @Override
  public String toString() {
    String prefix = "";
    final StringBuilder sb = new StringBuilder();
    for (final Entry<Usuari, TipusRelacio> entry : relacions.entrySet()) {
      sb.append(prefix);
      prefix = ",";
      sb.append(entry.getKey().nomUsuari)
          .append("=")
          .append(entry.getValue());
    }

    return "Usuari{" + "@" + nomUsuari() + " (" + nom() + ", " + cognoms() + ", relacions={" + sb + "}, publicacions={"
        + publicacions + "})";
  }
}
