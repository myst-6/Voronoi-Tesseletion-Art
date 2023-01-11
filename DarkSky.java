import java.awt.Color;
import java.util.Random;

public class DarkSky extends ColorGenerator {
  public DarkSky(Random colorRNG) {
    super(colorRNG);
  }

  public int generateColor(XY point, boolean kept) {
    if (!kept)
      return 0;
    // red: 25->40
    // green: 60->80
    // blue: 80->95
    int red = 25 + colorRNG.nextInt(15);
    int green = 60 + colorRNG.nextInt(20);
    int blue = 80 + colorRNG.nextInt(15);
    return new Color(red, green, blue).getRGB();
  }
}
