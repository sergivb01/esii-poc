public class Hatebook {

  public static void main(String[] args) {
    System.out.println(new ParaulaMarejada("hello"));
    final ParaulaBipolar paraula = new ParaulaBipolar("test", 50);
    for (int i = 0; i < 10; i++) {
      System.out.println(i + ": " + paraula);
    }
  }

}
