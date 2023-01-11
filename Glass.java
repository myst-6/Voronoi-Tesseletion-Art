public class Glass extends PointFilter {

  public Glass() {
    super(null);
  }

  public boolean filter(XY point) {
    return true;
  }

  public boolean[] filter(XY[] points) {
    boolean[] kept = new boolean[points.length];
    for (int i = 0; i < points.length; i++) {
      kept[i] = true;
    }
    return kept;
  }
}
