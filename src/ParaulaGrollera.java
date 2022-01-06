import java.util.Random;

public class ParaulaGrollera extends Paraula {

  private static final String[] PARAULES_GROLLERES;

  private final int possibilitat;
  private final Random random = new Random();

  public ParaulaGrollera(String paraula, int possibilitat) {
    super(paraula);
    this.possibilitat = possibilitat;
  }

  @Override
  public String contingut() {
    if (random.nextInt(100) < possibilitat) {
      // retornem una paraula aleatoria de la llista de grolleres
      final int index = random.nextInt(PARAULES_GROLLERES.length - 1);
      return PARAULES_GROLLERES[index];
    }

    return paraula;
  }

  static {
    PARAULES_GROLLERES = new String[]{
        "beneit",
        "collons",
        "hostia",
        "nap",
        ""
    };
  }

}
