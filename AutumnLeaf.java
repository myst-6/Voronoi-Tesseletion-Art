import java.util.Random;

public class AutumnLeaf extends Selection {
  public static int[] colors = new int[] {
      hex("#A4414F"),
      hex("#BEB36D"),
      hex("#BE6F5F"),
      hex("#560202"),
      hex("#723F40")
  };

  public AutumnLeaf(Random colorRNG) {
    super(colorRNG, colors);
  }
}
