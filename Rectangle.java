public class Rectangle extends Mask {
  public XY centre;
  public int width, height;

  public Rectangle(XY centre, int width, int height) {
    this.centre = centre;
    this.width = width;
    this.height = height;
  }

  public boolean inside(XY point) {
    int dx = Math.abs(point.x - centre.x);
    if (dx > width / 2) {
      return false;
    } else {
      int dy = Math.abs(point.y - centre.y);
      if (dy > height / 2) {
        return false;
      } else {
        return true;
      }
    }
  }
}
