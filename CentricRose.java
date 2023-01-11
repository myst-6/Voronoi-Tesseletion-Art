import java.awt.Color;
import java.util.Random;

public class CentricRose extends ColorGenerator {
  public int radius;
  public XY centre;
  public Green green;

  public CentricRose(Random colorRNG, int width, int height, int radius) {
    super(colorRNG);
    this.green = new Green(colorRNG);
    this.radius = radius;
    this.centre = new XY(width / 2, height / 2);
  }

  public CentricRose(Random colorRNG, int width, int height) {
    this(colorRNG, width, height, Math.min(width, height) / 2);
  }

  public CentricRose(Random colorRNG) {
    this(colorRNG, Main.WIDTH, Main.HEIGHT);
  }

  public int generateColor(XY point, boolean kept) {
    if (!kept) {
      return Color.BLACK.getRGB();
    }
    int green = 0;
    double dist = Main.euclideanDistance(new Pair<>(point, centre));
    double prop = dist / radius;
    double rnd = 0.90 + 0.2 * colorRNG.nextDouble();
    prop *= rnd;
    int blue = (int) Math.floor(256f * prop);
    if (blue > 255) {
      blue = 255;
    }
    int red = blue;
    return new Color(red, green, blue).getRGB();
  }
}
