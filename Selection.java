import java.util.Random;

public class Selection extends ColorGenerator {
  int[] rgbs;

  public Selection(Random colorRNG, int[] rgbs) {
    super(colorRNG);
    this.rgbs = rgbs;
  }

  public int generateColor(XY point, boolean kept) {
    int idx = colorRNG.nextInt(this.rgbs.length);
    return this.rgbs[idx];
  }
}
