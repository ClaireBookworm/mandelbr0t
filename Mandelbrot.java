import java.lang.Math;
import java.awt.*;


public class Mandelbrot {
  private double xMin, xMax;
  private double yMin, yMax;
  private static int maxIterations = 1000;
  private static int canvasWidth = 640;
  private static int canvasHeight = 640;
  private static Color[] randomColors;
  private static void assignColors() {
    randomColors = new Color[maxIterations+1];
    for (int i=1; i<=maxIterations; i++) {
      randomColors[i] = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
    }
  }
  public Mandelbrot(double x0, double x1, double y0, double y1) {
    xMin = x0;
    xMax = x1;
    yMin = y0;
    yMax = y1;
    StdDraw.setCanvasSize(canvasWidth, canvasHeight);
    StdDraw.setXscale(xMin, xMax);
    StdDraw.setYscale(yMin, yMax);
    StdDraw.enableDoubleBuffering();
  }
  public static void main(String[] args) {
    Mandelbrot m = new Mandelbrot(-2.0, 1.0, -1.5, 1.5);
    m.plot();
    boolean drawn = false;
    while (true) {
      if (!drawn) {
        m.plot();
      }
      drawn = true;
      
      if (StdDraw.isMousePressed()) {
        StdDraw.clear();
        m.zoomIn(StdDraw.mouseX(), StdDraw.mouseY());
        drawn = false;
      }
    }
  }
  private int numberOfIterationsToEscape(Complex c) {
    Complex current = new Complex(0,0);
    for (int i=0; i<maxIterations; i++) {
      // System.out.println(current + ", squared magnitude: " + current.squareMagnitude());
      if (current.squareMagnitude() > 4) {
        // System.out.println("Escaped after " + (i-1) + " iterations");
        return (i-1);
      }
      current.square();
      current.add(c);
    }
    // System.out.println("Didn't escape after " + maxIterations + " iterations.");
    return -1;
  }
  public void plot() {
    assignColors();
    double dX = (xMax - xMin) / canvasWidth;
    double dY = (yMax - yMin) / canvasHeight;
    StdDraw.setPenRadius(0);
    for (double rC = xMin; rC<xMax; rC += dX) {
      for (double iC = yMin; iC<yMax; iC += dY) {
        if (numberOfIterationsToEscape(new Complex(rC, iC)) == -1) {
          StdDraw.setPenColor(StdDraw.BLACK);
        }
        else {
          StdDraw.setPenColor(randomColors[numberOfIterationsToEscape(new Complex(rC, iC))]);
        }
        StdDraw.point(rC, iC);
      }
    }
    StdDraw.show();
  }
  
  public void zoomIn(double Xcenter, double Ycenter) {
    double w = (xMax - xMin)/2;
    double h = (yMax - yMin)/2;
    xMin = Xcenter - w/2;
    xMax = Xcenter + w/2;
    yMin = Ycenter - w/2;
    yMax = Ycenter + w/2;
    StdDraw.setXscale(xMin, xMax);
    StdDraw.setYscale(yMin, yMax);
    maxIterations += 1000;
    plot();
  }
}



class Complex {
	private double a;
  private double b;
  public Complex(double real, double imaginary) {
    a = real;
    b = imaginary;
  }
  public double getReal() {
    return a;
  }
  public double getImaginary() {
    return b;
  }
  public void setA(double input) {
    a = input;
  }
  public void setB(double input) {
    b = input;
  }
  public void add(Complex z) {
    a += z.getReal();
    b += z.getImaginary();
  }
  public void square() {
    double newFirst = (a*a) - (b*b); // a^2 - b^2
    double second = 2 * a * b;
    setA(newFirst);
    setB(second);
  }
  public double squareMagnitude() {
    return a * a + b * b; // a^2 + b^2, the magnitude of a complex vector
  }
  public String toString() {
    return a + " + " + b + "i";
  }
}

