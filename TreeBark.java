import java.awt.Color;
import java.util.Random;

public class TreeBark extends ColorGenerator {
  public TreeBark(Random colorRNG) {
    super(colorRNG);
  }

  public int generateColor(XY point, boolean kept) {
    if (!kept) {
      return 0;
    }
    int r = 100 + colorRNG.nextInt(70);
    double p1 = 1.3d + 0.1d * colorRNG.nextDouble();
    double p2 = 2.4d + 0.2d * colorRNG.nextDouble();
    int g = (int) Math.round((double) r / p1);
    int b = (int) Math.round((double) r / p2);
    return new Color(r, g, b).getRGB();
  }
}
