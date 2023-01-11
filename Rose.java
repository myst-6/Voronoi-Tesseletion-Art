import java.util.Random;

public class Rose extends Selection {
  public static int[] rgbs = new int[] {
      // hex("#f9e0ec"),
      hex("#ec7ead"),
      hex("#c82f63"),
      hex("#7a1b38"),
      hex("#78383d")
  };

  public Rose(Random colorRNG) {
    super(colorRNG, rgbs);
  }
}
