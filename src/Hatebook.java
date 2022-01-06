public class Hatebook {

  public static void main(String[] args) {
    {
      // comprovar paraules
      final Paraula paraula = new Paraula("normal");
      final Paraula bipolar = new ParaulaBipolar("bipolar", 30);
      final Paraula marejada = new ParaulaMarejada("marejada");
      final Paraula grollera = new ParaulaGrollera("dolenta", 66);

      System.out.println(paraula);
      System.out.println(marejada);
      for (int i = 0; i < 10; i++) {
        System.out.println(i + ": " + bipolar + " | " + grollera);
      }
    }

    {
      // comprovar relacions
      final Usuari tom = new Usuari("tom", "simons");
      final Usuari test = new Usuari("test", "ing");

      System.out.println(tom.tipusRelacio(test));
      tom.canviarRelacioUsuari(test, TipusRelacio.AMIC);
      System.out.println(tom.tipusRelacio(test));
      tom.canviarRelacioUsuari(test, TipusRelacio.SALUDAT);
      System.out.println(tom.tipusRelacio(test));
      tom.canviarRelacioUsuari(test, TipusRelacio.RES);
      System.out.println(tom.tipusRelacio(test));
    }
  }

}
