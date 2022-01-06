public class Hatebook {

  public static void main(String[] args) {
    {
      // comprovar paraula marejada i bipolar
      System.out.println(new ParaulaMarejada("hello"));
      final ParaulaBipolar paraula = new ParaulaBipolar("test", 50);
      for (int i = 0; i < 10; i++) {
        System.out.println(i + ": " + paraula);
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
