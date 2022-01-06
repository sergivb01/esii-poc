import java.util.HashMap;
import java.util.Map;

public class Hatebook {

  public static void main(String[] args) {
    for (TipusRelacio value : TipusRelacio.values()) {
      System.out.println(value.tipus() + " " + value.ordinal());
    }

    final Persona pere = new Persona("pere");
    final Persona joan = new Persona("Joan");

    pere.afegirRelacio(joan, TipusRelacio.AMIC);
    joan.afegirRelacio(pere, TipusRelacio.SALUDAT);

    System.out.println(pere);
    System.out.println(joan);
  }

  static class Persona {

    private final String nom;
    private Map<Persona, TipusRelacio> relacions;

    public Persona(String nom) {
      this.nom = nom;
      this.relacions = new HashMap<>();
    }

    public void afegirRelacio(final Persona usuari, final TipusRelacio tipus) {
      relacions.put(usuari, tipus);
    }

    @Override
    public String toString() {
      return "Persona{" +
          "nom='" + nom + '\'' +
          ", relacions=" + relacions.size() +
          '}';
    }
  }

}
