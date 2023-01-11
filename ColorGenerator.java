import java.util.Random;

public abstract class ColorGenerator {
  Random colorRNG;

  public ColorGenerator(Random colorRNG) {
    this.colorRNG = colorRNG;
  }

  public static int hex(String str) {
    if (str.startsWith("#")) {
      str = str.substring(1);
    }
    return Integer.decode("0x" + str);
  }

  public abstract int generateColor(XY point, boolean kept);
}
