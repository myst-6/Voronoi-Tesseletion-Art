import java.util.Random;

public class Marigold extends ColorGenerator {
  public XY centre;
  public int width, height;

  public Marigold(Random colorRNG, XY centre, int width, int height) {
    super(colorRNG);
    this.centre = centre;
    this.width = width;
    this.height = height;
  }

  @Override
  public int generateColor(XY point, boolean kept) {
    if (!kept) {
      return 0;
    }
    int d = Math.min(width / 20, height / 20);
    int dx = (point.x - centre.x) / d;
    int dy = (point.y - centre.y) / d;
    int D = Math.abs(dx) + Math.abs(dy);
    int lightYellow = hex("#FFE618");
    int yellow = hex("#FDC101");
    int darkYellow = hex("DA6100");
    return (new int[] {
        lightYellow,
        yellow,
        darkYellow
    })[D % 3];
  }
}
