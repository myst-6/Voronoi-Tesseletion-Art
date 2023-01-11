import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.function.Function;

import javax.imageio.ImageIO;

public class Main {
  public static double euclideanDistanceSqu(Pair<XY, XY> points) {
    double dx = points.first.x - points.second.x;
    double dy = points.first.y - points.second.y;
    // square root not needed, since comparing with other values
    double dist = dx * dx + dy * dy;
    return dist;
  }

  public static double euclideanDistance(Pair<XY, XY> points) {
    return Math.sqrt(euclideanDistanceSqu(points));
  }

  public static double taxicabDistance(Pair<XY, XY> points) {
    double dx = points.first.x - points.second.x;
    double dy = points.first.y - points.second.y;
    double dist = Math.abs(dx) + Math.abs(dy);
    return dist;
  }

  public static int N_PTS = 1024;
  public static int WIDTH = 1024;
  public static int HEIGHT = 1024;
  public static Function<Pair<XY, XY>, Double> DIST = Main::euclideanDistanceSqu;

  public static Pair<Double, Integer> closestTo(XY point, XY[] points) {
    double min = 0;
    int minIndex = -1;
    for (int i = 0; i < points.length; i++) {
      double dist = DIST.apply(new Pair<XY, XY>(point, points[i]));
      if (minIndex == -1 || dist < min) {
        min = dist;
        minIndex = i;
      }
    }
    return new Pair<Double, Integer>(min, minIndex);
  }

  public static XY[] distributePoints() {
    Random pointRNG = new Random(777L);
    XY[] points = new XY[N_PTS];
    for (int i = 0; i < N_PTS; i++) {
      int x = pointRNG.nextInt(WIDTH);
      int y = pointRNG.nextInt(HEIGHT);
      points[i] = new XY(x, y);
    }
    return points;
  }

  public static void drawImage(PointFilter[] filters, ColorGenerator[] colorGenerators) {
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    XY[] points = distributePoints();
    int[] colors = new int[N_PTS];
    for (int i = 0; i < N_PTS; i++) {
      if (i % 20 == 0)
        System.out.println((i + 1) + " of " + N_PTS);
      boolean flag = false;
      for (int j = 0; j < filters.length; j++) {
        if (filters[j].filter(points[i])) {
          colors[i] = colorGenerators[j].generateColor(points[i], true);
          flag = true;
          break;
        }
      }
      if (!flag) {
        ColorGenerator backup = colorGenerators[colorGenerators.length - 1];
        colors[i] = backup.generateColor(points[i], false);
      }
    }
    for (int x = 0; x < WIDTH; x++) {
      if (x % 16 == 0)
        System.out.println((x + 1) + " of " + WIDTH);
      for (int y = 0; y < HEIGHT; y++) {
        XY here = new XY(x, y);
        Pair<Double, Integer> minPair = closestTo(here, points);
        image.setRGB(x, y, colors[minPair.second % colors.length]);
      }
    }
    try {
      ImageIO.write(image, "png", new File("./out_" + System.currentTimeMillis() + ".png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void drawImage(PointFilter filter, PointFilter filter2, ColorGenerator colorGenerator,
      ColorGenerator colorGenerator2) {
    PointFilter[] filters = new PointFilter[] { filter, filter2 };
    ColorGenerator[] colorGenerators = new ColorGenerator[] { colorGenerator, colorGenerator2 };
    drawImage(filters, colorGenerators);
  }

  public static void drawImage(PointFilter filter, ColorGenerator colorGenerator) {
    PointFilter[] filters = new PointFilter[] { filter };
    ColorGenerator[] colorGenerators = new ColorGenerator[] { colorGenerator };
    drawImage(filters, colorGenerators);
  }

  public static void centricRose() {
    Mask mask = new Circle(WIDTH * 2 / 5);
    Mask mask2 = new Circle(WIDTH / 2);
    PointFilter filter = new PointFilter(mask);
    PointFilter filter2 = new PointFilter(mask2);
    Random colorRNG = new Random(777L);
    ColorGenerator colorGenerator = new CentricRose(colorRNG);
    ColorGenerator colorGenerator2 = new Green(colorRNG);
    drawImage(filter, filter2, colorGenerator, colorGenerator2);
  }

  public static void tree() {
    int w = WIDTH / 5;
    int h = HEIGHT * 3 / 5;
    Mask mask = new SemiOval(new XY(WIDTH / 2, h), WIDTH * 4 / 5 / 2, h);
    h = h * 4 / 5;
    Mask mask2 = new Rectangle(new XY(WIDTH / 2, (HEIGHT + h) / 2), w, HEIGHT - h);
    PointFilter filter = new PointFilter(mask);
    PointFilter filter2 = new PointFilter(mask2);
    Random colorRNG = new Random(777L);
    ColorGenerator colorGenerator = new Green(colorRNG);
    ColorGenerator colorGenerator2 = new TreeBark(colorRNG);
    drawImage(filter, filter2, colorGenerator, colorGenerator2);
  }

  public static void oval() {
    Mask mask = new Circle(new XY(WIDTH / 2, HEIGHT / 2), WIDTH / 4);
    Mask mask2 = new SemiOval(new XY(WIDTH / 2, HEIGHT / 2), WIDTH / 2, HEIGHT / 3);
    PointFilter filter = new PointFilter(mask);
    PointFilter filter2 = new PointFilter(mask2);
    Random colorRNG = new Random(777L);
    ColorGenerator colorGenerator = new Green(colorRNG);
    ColorGenerator colorGenerator2 = new TreeBark(colorRNG);
    drawImage(filter, filter2, colorGenerator, colorGenerator2);
  }

  public static void rainbow(long seed) {
    PointFilter filter = new Glass();
    Random colorRNG = new Random(seed);
    XY centre = new XY(WIDTH / 2, HEIGHT / 2);
    Object[] stripes = new Object[12];
    for (int i = 0; i < 12; i++) {
      float t;
      if (i == 0) {
        t = 0.0f;
      } else if (i == 11) {
        t = 1.0f;
      } else {
        t = (i - 1) * 0.1f + 0.05f + 0.1f * colorRNG.nextFloat();
      }
      float hue = 0.52f + 0.16f * colorRNG.nextFloat();
      stripes[i] = new Pair<Float, Float>(t, hue);
    }
    ColorGenerator colorGenerator = new StripesGradient(colorRNG, centre, WIDTH, HEIGHT, stripes);
    drawImage(filter, colorGenerator);
  }

  public static void marigold() {
    XY centre = new XY(WIDTH / 2, HEIGHT / 2);
    Mask mask = new Circle(centre, WIDTH / 5);
    Mask mask2 = new Circle(centre, WIDTH * 2 / 5);
    Mask mask3 = new Circle(centre, WIDTH * 49 / 100);
    PointFilter filter = new PointFilter(mask);
    PointFilter filter2 = new PointFilter(mask2);
    PointFilter filter3 = new PointFilter(mask3);
    Random colorRNG = new Random(777L);
    ColorGenerator colorGenerator = new DarkBrown(colorRNG);
    ColorGenerator colorGenerator2 = new Marigold(colorRNG, centre, WIDTH, HEIGHT);
    ColorGenerator colorGenerator3 = new Green(colorRNG);
    PointFilter[] filters = new PointFilter[] {
        filter, filter2, filter3
    };
    ColorGenerator[] colorGenerators = new ColorGenerator[] {
        colorGenerator, colorGenerator2, colorGenerator3
    };
    drawImage(filters, colorGenerators);
  }

  public static void sunset() {
    Mask water = new Rectangle(new XY(WIDTH / 2, 4 * HEIGHT / 5), WIDTH, HEIGHT * 2 / 5);
    Mask sun = new Circle(new XY(WIDTH / 2, 3 * HEIGHT / 5), WIDTH / 30);
    Mask sunsky = new Oval(new XY(WIDTH / 2, 3 * HEIGHT / 5), WIDTH / 2, HEIGHT / 2);
    PointFilter filter = new PointFilter(water);
    PointFilter filter2 = new PointFilter(sun);
    PointFilter filter3 = new PointFilter(sunsky);
    PointFilter filter4 = new Glass();
    Random colorRNG = new Random(777L);
    ColorGenerator colorGenerator = new DarkWater(colorRNG, new XY(WIDTH / 2, 3 * HEIGHT / 5));
    ColorGenerator colorGenerator2 = new Sun(colorRNG);
    ColorGenerator colorGenerator3 = new Sunset(colorRNG, new XY(WIDTH / 2, 3 * HEIGHT / 5), WIDTH / 2, HEIGHT / 2);
    ColorGenerator colorGenerator4 = new DarkSky(colorRNG);
    PointFilter[] filters = new PointFilter[] {
        filter, filter2, filter3, filter4
    };
    ColorGenerator[] colorGenerators = new ColorGenerator[] {
        colorGenerator, colorGenerator2, colorGenerator3, colorGenerator4
    };
    drawImage(filters, colorGenerators);
  }

  public static void spiral() {
    XY centre = new XY(WIDTH / 2, HEIGHT / 2);
    Mask mask = new Circle(centre, WIDTH * 9 / 20);
    PointFilter filter = new PointFilter(mask);
    Random colorRNG = new Random(777L);
    ColorGenerator colorGenerator = new Spiral(colorRNG, centre, 80);
    drawImage(filter, colorGenerator);
  }

  public static void lightning() {
    XY centre = new XY(WIDTH / 2, HEIGHT / 2);
    Mask mask = new Lightning(centre, WIDTH, HEIGHT);
    PointFilter filter = new PointFilter(mask);
    Random colorRNG = new Random(777L);
    ColorGenerator colorGenerator = new Green(colorRNG);
    ColorGenerator colorGenerator2 = new DarkSky(colorRNG);
    ColorGenerator colorGenerator3 = new Spiral(colorRNG, new XY(WIDTH / 3, HEIGHT / 3), 10, colorGenerator,
        colorGenerator2);
    ColorGenerator colorGenerator4 = new Spiral(colorRNG, new XY(WIDTH * 2 / 3, HEIGHT * 2 / 3), 10, colorGenerator2,
        colorGenerator);
    ColorGenerator colorGenerator5 = new Spiral(colorRNG, new XY(WIDTH / 2, HEIGHT / 2), 30, colorGenerator3,
        colorGenerator4);
    drawImage(filter, colorGenerator5);
  }

  public static void snowflake() {
    Mask mask = new Snowflake(new XY(WIDTH / 2, HEIGHT / 2), 500);
    PointFilter filter = new PointFilter(mask, 2);
    Random colorRNG = new Random(777L);
    ColorGenerator colorGenerator = new DarkSky(colorRNG);
    drawImage(filter, colorGenerator);
  }

  public static void main(String[] args) {
    snowflake();
  }
}