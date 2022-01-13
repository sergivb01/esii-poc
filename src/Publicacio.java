import java.util.List;

public class Publicacio {
    private final Usuari propietari;
    private final TipusRelacio visibilitat;
    private List<Paraula> paraules;

    public Publicacio(Usuari propietari, TipusRelacio visibilitat, List<Paraula> paraules) {
        this.propietari = propietari;
        this.visibilitat = visibilitat;
        this.paraules = paraules;
    }

    public Usuari propietari() {
        return this.propietari;
    }

    public String contingut() {
        StringBuilder sb = new StringBuilder();
        for (Paraula paraula : paraules) {
            sb.append(paraula.contingut());
        }
        return sb.toString();
    }

    public void nouContingut(List<Paraula> paraules) {
        this.paraules = paraules;
    }

    @Override
    public String toString() {
        return "Publicacio{" +
            "propietari=" + propietari.nomUsuari() +
            ", visibilitat=" + visibilitat +
            ", paraules=" + paraules +
            '}';
    }
}
