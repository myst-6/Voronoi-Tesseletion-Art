import java.awt.Color;
import java.util.Random;

public class DarkWater extends ColorGenerator {
  public XY centre;

  public DarkWater(Random colorRNG, XY centre) {
    super(colorRNG);
    this.centre = centre;
  }

  public int generateColor(XY point, boolean kept) {
    if (!kept) {
      return 0;
    }
    int dy = point.y - centre.y;
    int dx = point.x - centre.x;
    dy += Math.abs(dx) / 10;
    double iblue = (dy % 120) / 2;
    double p = 0.95d + 0.10d * colorRNG.nextDouble();
    int blue = (int) Math.floor(iblue * p);
    blue += 40;
    int red = blue / 10;
    int green = blue / 10;
    if (Math.abs(dx) < 0) {
      if (colorRNG.nextDouble() < 0.5d) {
        red += 100;
      } else if (colorRNG.nextDouble() < 0.5d) {
        red += 50;
      }
    }
    System.out.println(red + "," + green + "," + blue);
    return new Color(red, green, blue).getRGB();
  }
}
