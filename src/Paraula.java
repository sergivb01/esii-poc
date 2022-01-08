// paraula normal
public class Paraula {

  protected final String paraula;

  public Paraula(String paraula) {
    this.paraula = paraula;
  }

  public String contingut() {
    return paraula;
  }

  @Override
  public String toString() {
    return contingut();
  }
}
