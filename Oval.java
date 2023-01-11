public class Oval extends Mask {
  public XY centre;
  public int heightRadius, widthRadius;

  public Oval(XY centre, int widthRadius, int heightRadius) {
    this.centre = centre;
    this.widthRadius = widthRadius;
    this.heightRadius = heightRadius;
  }

  public Oval(int widthRadius, int heightRadius) {
    this(new XY(widthRadius, heightRadius), widthRadius, heightRadius);
  }

  public Oval() {
    this(Main.WIDTH, Main.HEIGHT);
  }

  public boolean inside(XY point) {
    int h = centre.x, k = centre.y;
    int rx = widthRadius, ry = heightRadius;
    int x = point.x, y = point.y;
    int dx = x - h, dy = y - k;
    double X = (double) dx / (double) rx;
    double Y = (double) dy / (double) ry;
    return X * X + Y * Y <= 1;
  }
}
