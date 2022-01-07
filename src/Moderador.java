import java.util.List;

// el moderador pot crear/modificar/eliminar publicacions d'altres usuaris
public class Moderador extends Usuari {

  public Moderador(String nomUsuari, String nom, String cognoms) {
    super(nomUsuari, nom, cognoms);
  }

  public void modificar_publicacio(Publicacio publicacio, List<Paraula> paraules) {
    publicacio.nou_contingut(paraules);
  }

}
