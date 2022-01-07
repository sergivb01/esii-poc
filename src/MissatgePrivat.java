import java.util.List;

public class MissatgePrivat {

  private final Usuari remitent;
  private final Usuari destinatari;
  private final List<Paraula> paraules;

  private String prova;

  public MissatgePrivat(Usuari remitent, Usuari destinatari, List<Paraula> paraules) {
    this.remitent = remitent;
    this.destinatari = destinatari;
    this.paraules = paraules;
  }

  public Usuari remitent() {
    return this.remitent;
  }

  public Usuari destinatari() {
    return this.destinatari;
  }

  public String contingut() {
    StringBuilder sb = new StringBuilder();
    for (Paraula paraula : paraules) {
      sb.append(paraula.contingut());
    }
    return sb.toString();
  }

}
