public enum TipusRelacio {
  RES("Desconegut"),
  CONEGUT("Conegut"),
  SALUDAT("Saludat"),
  AMIC("Amic");

  private final String tipus;

  TipusRelacio(String tipus) {
    this.tipus = tipus;
  }

  public String tipus() {
    return this.tipus;
  }
}
