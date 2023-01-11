import java.awt.Color;
import java.util.Random;

public class Purple extends ColorGenerator {
  public Purple(Random colorRNG) {
    super(colorRNG);
  }

  public int generateColor(XY point, boolean kept) {
    if (!kept)
      return 0;
    // red 100->150
    // green 0->50
    // blue: 200->250
    int red = 100 + colorRNG.nextInt(50);
    int green = colorRNG.nextInt(50);
    int blue = 100 + colorRNG.nextInt(50);
    return new Color(red, green, blue).getRGB();
  }
}
