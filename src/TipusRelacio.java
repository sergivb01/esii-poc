public enum TipusRelacio {
  RES("Desconegut"),
  CONEGUT("Conegut"),
  SALUDAT("Saludat"),
  AMIC("Amic");

  private final String tipus;

  TipusRelacio(String tipus) {
    this.tipus = tipus;
  }

  @Override
  public String toString() {
    return tipus;
  }

}
