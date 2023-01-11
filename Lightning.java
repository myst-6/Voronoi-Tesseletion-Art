import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Lightning extends Mask {
  public Random rng;
  public XY centre;
  public int width, height;
  public XY[] lightning;

  public Lightning(XY centre, int width, int height) {
    this.rng = new Random(1L);
    this.centre = centre;
    this.width = width;
    this.height = height;
    this.lightning = gen();
    System.out.println(Arrays.toString(this.lightning));
  }

  public XY[] gen() {
    // 2nd item is dx/dy.
    // the next dx/dy must never differ by more than 0.2 at a time
    ArrayList<Pair<XY, Double>> queue = new ArrayList<>();
    ArrayList<XY> list = new ArrayList<>();
    queue.add(new Pair<XY, Double>(new XY(centre.x, 0), 0d));
    queue.add(new Pair<XY, Double>(new XY(centre.x, 0), 0d));
    queue.add(new Pair<XY, Double>(new XY(centre.x, 0), 0d));
    list.add(new XY(centre.x, 0));
    while (queue.size() > 0) {
      // Pair<XY, Double> pair = queue.remove(queue.size() - 1);
      Pair<XY, Double> pair = queue.remove(0);
      XY pt1 = pair.first;
      double m1 = pair.second;
      int n = 1;
      double a = list.size() / 100;
      double a2 = 1d / (1d + list.size());
      // double p = 1d - 20d * a;
      double p = 1d - 0.8d * (1d - a2);
      if (rng.nextDouble() < p) {
        n++;
        if (rng.nextDouble() < p) {
          n++;
          if (rng.nextDouble() < p) {
            n++;
          }
        }
      }
      for (int i = 0; i < n; i++) {
        double b = 0.4d * (double) (i + 1);
        double m = m1 + b * rng.nextDouble() - (b / 2);
        int dy = rng.nextInt(height / 10);
        // (dx/dy)*dy = dx
        int dx = (int) Math.round(m * (double) dy);
        XY pt = new XY(pt1.x + dx, pt1.y + dy);
        if (Math.abs(pt.x - centre.x) <= width / 2) {
          list.add(pt);
          if (Math.abs(pt.y - centre.y) <= height / 2) {
            Pair<XY, Double> next = new Pair<>(pt, m);
            queue.add(next);
          }
        }
      }
    }
    return list.toArray(new XY[list.size()]);
  }

  public boolean inside(XY point) {
    Pair<Double, Integer> closest = Main.closestTo(point, lightning);
    return Math.sqrt(closest.first) <= width / 25;

  }
}
