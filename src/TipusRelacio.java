public enum TipusRelacio {
  RES("Desconegut"),
  CONEGUT("Conegut"),
  SALUDAT("Saludat"),
  AMIC("Amic");

  private final String tipus;

  TipusRelacio(String tipus) {
    this.tipus = tipus;
  }

  /**
   * Semblant a {@link #valueOf(String)}, pero ignora majúscules
   *
   * @return el {@link TipusRelacio} amb nom {@code nomRelacio}
   * @throws IllegalArgumentException si el {@link TipusRelacio} no existeix
   */
  public static TipusRelacio desdeNom(final String nomRelacio) {
    for (TipusRelacio tipusRelacio : values()) {
      if (tipusRelacio.tipus.equalsIgnoreCase(nomRelacio)) {
        return tipusRelacio;
      }
    }
    throw new IllegalArgumentException("El TipusRelacio '" + nomRelacio + "' no existeix");
  }

  @Override
  public String toString() {
    return tipus;
  }

}
