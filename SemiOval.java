public class SemiOval extends Oval {
  public SemiOval(XY centre, int widthRadius, int heightRadius) {
    super(centre, widthRadius, heightRadius);
  }

  public boolean inside(XY point) {
    if (point.y > centre.y) {
      return false;
    } else {
      return super.inside(point);
    }
  }

  public String toString() {
    return "SemiOval{centre:" + centre.toString() + ",radius:" + new XY(widthRadius, heightRadius).toString() + "}";
  }
}
