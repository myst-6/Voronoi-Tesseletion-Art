import java.awt.Color;
import java.util.Random;

public class Green extends ColorGenerator {
  public Green(Random colorRNG) {
    super(colorRNG);
  }

  public int generateColor(XY point, boolean kept) {
    if (!kept) {
      return 0;
    }
    int red = 0;
    int blue = 0;
    int green = 128 + colorRNG.nextInt(128);
    return new Color(red, green, blue).getRGB();
  }
}
