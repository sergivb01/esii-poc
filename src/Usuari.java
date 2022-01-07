import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Usuari {

  private final String nomUsuari;
  private final String nom;
  private final String cognoms;
  private final Map<Usuari, TipusRelacio> relacions;

  public Usuari(String nomUsuari, String nom, String cognoms) {
    this.nomUsuari = nomUsuari;
    this.nom = nom;
    this.cognoms = cognoms;
    this.relacions = new LinkedHashMap<>();
  }

  public void canviarRelacioUsuari(final Usuari usuari, final TipusRelacio tipus) {
    if (tipus == TipusRelacio.RES) {
      // si ja no tenen cap relació amb l'usuari, el podem treure per no emmagatzemar "buits"
      relacions.remove(usuari);
      return;
    }

    relacions.put(usuari, tipus);
  }

  public TipusRelacio tipusRelacio(final Usuari usuari) {
    return relacions.getOrDefault(usuari, TipusRelacio.RES);
  }

  public String nomUsuari() {
    return this.nomUsuari;
  }

  public String nom() {
    return this.nom;
  }

  public String cognoms() {
    return this.cognoms;
  }

  @Override
  public String toString() {
    String prefix = "";
    final StringBuilder sb = new StringBuilder();
    for (Entry<Usuari, TipusRelacio> entry : relacions.entrySet()) {
      sb.append(prefix);
      prefix = ",";
      sb.append(entry.getKey().nomUsuari)
          .append("=")
          .append(entry.getValue());
    }

    return "Usuari{" + "@" + nomUsuari() + " (" + nom() + ", " + cognoms() + ", relacions={" + sb + "})";
  }
}
