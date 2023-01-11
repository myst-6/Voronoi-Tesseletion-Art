import java.awt.Color;
import java.util.Random;

public class Stripes extends ColorGenerator {
  XY centre;
  int width, height;
  Object[] colors = new Object[] {
      new Pair<>(0.0f, 0.65f),
      new Pair<>(0.2f, 0.55f),
      new Pair<>(0.5f, 0.60f),
      new Pair<>(0.75f, 0.50f),
      new Pair<>(1.0f, 0.7f)
  };

  public Stripes(Random colorRNG, XY centre, int width, int height, Object[] colors) {
    super(colorRNG);
    this.centre = centre;
    this.width = width;
    this.height = height;
    this.colors = colors;
  }

  public int generateColor(XY point, boolean kept) {
    float xProp = (float) point.x / (float) width;
    float yProp = (float) point.y / (float) height;
    float t = Math.abs(2 - xProp - yProp) / 2;
    System.out.println(t);
    for (int i = 0; i < colors.length - 1; i++) {
      Pair<Float, Float> p1 = (Pair<Float, Float>) colors[i];
      Pair<Float, Float> p2 = (Pair<Float, Float>) colors[i + 1];
      if (t >= p1.first && t <= p2.first) {
        float d1 = t - p1.first, d2 = p2.first - t;
        if (d1 >= d2) {
          return Color.HSBtoRGB(p1.second, 1.0f, 1.0f);
        } else {
          return Color.HSBtoRGB(p1.second, 1.0f, 1.0f);
        }
      }
    }
    return 0;
  }
}
