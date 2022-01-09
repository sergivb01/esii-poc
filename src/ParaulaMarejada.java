import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// es barrejen les lletres cada vegada
// TODO: N lletres o totes?
public class ParaulaMarejada extends Paraula {

  public ParaulaMarejada(String paraula) {
    super(paraula);
  }

  @Override
  public String contingut() {
    final List<String> lletres = Arrays.asList(paraula.split(""));
    Collections.shuffle(lletres);

    return lletres.stream().reduce((resultat, lletra) -> resultat += lletra).orElse("");
  }
}
