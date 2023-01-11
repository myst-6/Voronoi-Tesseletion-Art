public class XY {
  public int x, y;
  public float xf, yf;

  public XY(int x, int y) {
    this.x = x;
    this.y = y;
    this.xf = x;
    this.yf = y;
  }

  public XY(float xf, float yf) {
    this.xf = xf;
    this.yf = yf;
    this.x = (int) Math.round(xf);
    this.y = (int) Math.round(yf);
  }

  public String toString() {
    return "(" + x + "," + y + ")";
  }
}
