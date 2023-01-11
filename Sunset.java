import java.awt.Color;
import java.util.Random;

public class Sunset extends ColorGenerator {
  public XY centre;
  public int widthRadius, heightRadius;

  public Sunset(Random colorRNG, XY centre, int widthRadius, int heightRadius) {
    super(colorRNG);
    this.centre = centre;
    this.widthRadius = widthRadius;
    this.heightRadius = heightRadius;
  }

  public int generateColor(XY point, boolean kept) {
    // red 255->200 ->60
    // green 120->200 ->100
    // blue 90->150 ->100
    int dx = point.x - centre.x;
    int dy = point.y - centre.y;
    double px = (double) dx / (double) widthRadius;
    double py = (double) dy / (double) heightRadius;
    double p = px * px + py * py;
    p *= 1.5d;
    if (p <= 1d) {
      double pr = p * (0.95d + 0.1d * colorRNG.nextDouble());
      double pg = p * (0.95d + 0.1d * colorRNG.nextDouble());
      double pb = p * (0.95d + 0.1d * colorRNG.nextDouble());
      int red = (int) Math.floor(255d - 105d * pr);
      int green = (int) Math.floor(120d + 80d * pg);
      int blue = (int) Math.floor(90d + 60d * pb);
      return new Color(red, green, blue).getRGB();
    } else {
      p -= 1d;
      p *= 2d;
      double pr = p * (0.95d + 0.1d * colorRNG.nextDouble());
      double pg = p * (0.95d + 0.1d * colorRNG.nextDouble());
      double pb = p * (0.95d + 0.1d * colorRNG.nextDouble());
      int red = (int) Math.floor(200d - 140d * pr);
      int green = (int) Math.floor(200d - 100d * pg);
      int blue = (int) Math.floor(150d - 50d * pb);
      return new Color(red, green, blue).getRGB();
    }
  }
}
