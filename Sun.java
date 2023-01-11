import java.util.Random;

public class Sun extends ColorGenerator {
  public Sun(Random colorRNG) {
    super(colorRNG);
  }

  public int generateColor(XY point, boolean kept) {
    return hex("#FBE67C");
  }
}
