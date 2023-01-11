import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Snowflake extends Mask {
  public XY centre;
  public int size;
  public ArrayList<Pair<XY, Double>> pairs;
  public Random rng;

  public Snowflake(XY centre, int size) {
    this.centre = centre;
    this.size = size;
    this.rng = new Random(777L);
    this.pairs = (ArrayList<Pair<XY, Double>>) init();
  }

  public XY advance(XY pt, float angle) {
    float mag = 1f;
    float dx = (float) Math.sin(Math.toRadians((double) angle));
    float dy = (float) Math.cos(Math.toRadians((double) angle));
    return new XY(pt.xf + mag * dx, pt.yf + mag * dy);
  }

  public List<Pair<XY, Double>> init() {
    // every point in the list grows one unit forward on each iteration
    // additionally, there is a small chance that EVERY point will have two
    // branches leading out, spawned with the same angle from the original.
    // initially, the snowflakes are 60deg apart from each other, but each
    // time they branch, the angle decreases by a factor (1/2?)
    int n = 6;
    ArrayList<Pair<XY, Pair<Float, Integer>>> pairs = new ArrayList<>();
    ArrayList<Pair<XY, Pair<Float, Integer>>> front = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      Pair<XY, Pair<Float, Integer>> pair = new Pair<>(centre, new Pair<>(i * 60f, 0));
      pairs.add(pair);
      front.add(pair);
    }
    for (int i = 0; i < size; i++) {
      boolean isSplitting = rng.nextDouble() < 0.1f;
      HashMap<Integer, Boolean> isAlive = new HashMap<>();
      isAlive.put(0, true);
      ArrayList<Pair<XY, Pair<Float, Integer>>> nextFront = new ArrayList<>();
      for (Pair<XY, Pair<Float, Integer>> pair : front) {
        XY point = pair.first;
        Pair<Float, Integer> data = pair.second;
        Float angle = data.first;
        Integer count = data.second;
        // survive
        if (!isAlive.containsKey(count)) {
          isAlive.put(count, rng.nextDouble() < Math.pow(0.98d, count));
        }
        if (isAlive.get(count)) {
          XY nextPoint = advance(point, angle);
          Pair<Float, Integer> nextData = new Pair<>(angle, count);
          Pair<XY, Pair<Float, Integer>> nextPair = new Pair<>(nextPoint, nextData);
          nextFront.add(nextPair);
          pairs.add(nextPair);
          if (isSplitting) {
            float diff = 60f * (float) Math.pow(0.5d, count + 1);
            Pair<Float, Integer> leftData = new Pair<>(angle - diff, count + 1);
            Pair<Float, Integer> rightData = new Pair<>(angle + diff, count + 1);
            Pair<XY, Pair<Float, Integer>> leftPair = new Pair<>(advance(point, angle - diff), leftData);
            Pair<XY, Pair<Float, Integer>> rightPair = new Pair<>(advance(point, angle - diff), rightData);
            nextFront.add(leftPair);
            nextFront.add(rightPair);
            pairs.add(leftPair);
            pairs.add(rightPair);
          }
        }
      }
      front = nextFront;
    }
    // System.out.println(pairs.toString());
    return pairs.stream().map((Pair<XY, Pair<Float, Integer>> pair) -> new Pair<XY, Double>(pair.first,
        10d * Math.pow(0.05d, (double) pair.second.second))).collect(Collectors.toList());
  }

  @Override
  public boolean inside(XY point) {
    for (Pair<XY, Double> pair : pairs) {
      if (Main.DIST.apply(new Pair<>(point, pair.first)) <= 1) {
        return true;
      }
    }
    return false;
  }
}
