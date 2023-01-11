import java.awt.Color;
import java.util.Random;

public class Spiral extends ColorGenerator {
  public XY centre;
  public double w;
  public ColorGenerator color1, color2;

  public Spiral(Random colorRNG, XY centre, double w, ColorGenerator color1, ColorGenerator color2) {
    super(colorRNG);
    this.centre = centre;
    this.w = w;
    this.color1 = color1;
    this.color2 = color2;
  }

  public Spiral(Random colorRNG, XY centre, double w) {
    this(colorRNG, centre, w, new DarkWater(colorRNG, new XY(centre.x, 0)), new Purple(colorRNG));
  }

  public double angle(int dx, int dy) {
    double qt = Math.PI / 2;
    double qt3 = 3 * qt;
    double rot = 4 * qt;
    if (dx == 0 && dy >= 0)
      return 0d;
    if (dx == 0 && dy < 0)
      return rot;
    double a = Math.atan(Math.abs(dy) / Math.abs(dx));
    if (dx > 0 && dy >= 0)
      return qt - a;
    if (dx > 0 && dy <= 0)
      return qt + a;
    if (dx < 0 && dy <= 0)
      return qt3 - a;
    if (dx < 0 && dy >= 0)
      return qt3 + a;
    throw new RuntimeException("How did we get here?");
  }

  public int generateColor(XY point, boolean kept) {
    if (!kept)
      return 0;
    int dx = point.x - centre.x;
    int dy = point.y - centre.y;
    double abs = Math.sqrt(dx * dx + dy * dy);
    double a = angle(dx, dy);
    abs /= (double) w;
    double diff = Math.abs(abs - a);
    float rot = 2f * (float) Math.PI;
    diff %= rot;
    float t = (float) diff / (float) rot;
    if (t <= 0.3f || t >= 0.7f) {
      System.out.println(dx + "," + dy + "," + a + "," + abs + "," + diff);
      return color1.generateColor(point, true);
    } else {
      return color2.generateColor(point, true);
    }
  }
}
