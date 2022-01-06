import java.util.Random;

// aquesta paraula pot desaparèixer en un % d'ocasions
public class ParaulaBipolar extends Paraula {

  private final int possibilitat;
  private final Random random = new Random();

  public ParaulaBipolar(String paraula, int possibilitat) {
    super(paraula);
    this.possibilitat = possibilitat;
  }

  @Override
  public String contingut() {
    if (random.nextInt(100) >= possibilitat) {
      return "";
    }

    return paraula;
  }
}
