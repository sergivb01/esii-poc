import java.util.LinkedHashMap;
import java.util.Map;

public class Usuari {

  private final String nom;
  private final String cognom;
  private final Map<Usuari, TipusRelacio> relacions;

  public Usuari(String nom, String cognom) {
    this.nom = nom;
    this.cognom = cognom;
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

  public String nom() {
    return this.nom;
  }

  public String cognom() {
    return this.cognom;
  }
}
