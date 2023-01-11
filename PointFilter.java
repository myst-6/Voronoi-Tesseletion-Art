public class PointFilter {
  public int width, height;
  public Mask mask;
  public int type;

  public PointFilter(int width, int height, Mask mask, int type) {
    this.width = width;
    this.height = height;
    this.mask = mask;
    this.type = type;
  }

  public PointFilter(Mask mask, int type) {
    this(Main.WIDTH, Main.HEIGHT, mask, type);
  }

  public PointFilter(Mask mask) {
    this(mask, 0);
  }

  public boolean filter(XY point) {
    return mask.inside(point);
  }

  public boolean[] filter(XY[] points) {
    if (type < 2) {
      boolean[] kept = new boolean[points.length];
      for (int i = 0; i < points.length; i++) {
        kept[i] = mask.inside(points[i]);
      }
      if (type == 1) {
        return kept;
      }
      for (int x = 0; x < width; x++) {
        if ((x - 1) % 16 == 0)
          System.out.println((x + 1) + " of " + width);
        for (int y = 0; y < height; y++) {
          XY here = new XY(x, y);
          Pair<Double, Integer> minPair = Main.closestTo(here, points);
          if (kept[minPair.second]) {
            kept[minPair.second] = mask.inside(here);
          }
        }
      }
      return kept;
    } else {
      boolean[] kept = new boolean[points.length];
      for (int i = 0; i < points.length; i++) {
        kept[i] = false;
      }
      for (int x = 0; x < width; x++) {
        if ((x - 1) % 16 == 0)
          System.out.println((x + 1) + " of " + width);
        for (int y = 0; y < height; y++) {
          XY here = new XY(x, y);
          Pair<Double, Integer> minPair = Main.closestTo(here, points);
          if (mask.inside(here)) {
            kept[minPair.second] = true;
          }
        }
      }
      return kept;
    }
  }
}
