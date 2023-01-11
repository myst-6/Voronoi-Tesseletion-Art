public class Circle extends Mask {
  public int radius;
  public XY centre;

  public Circle(XY centre, int radius) {
    this.centre = centre;
    this.radius = radius;
  }

  public Circle(int radius) {
    this(new XY(radius, radius), radius);
  }

  public Circle() {
    this(new XY(Main.WIDTH / 2, Main.HEIGHT / 2), Math.min(Main.WIDTH, Main.HEIGHT) / 2);
  }

  @Override
  public boolean inside(XY point) {
    double dist = Main.euclideanDistance(new Pair<>(point, centre));
    return dist <= radius;
  }
}
