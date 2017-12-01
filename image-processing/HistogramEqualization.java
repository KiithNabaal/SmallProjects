import java.io.File;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class HistogramEqualization {
  static final int RED_MASK = 0x00FF0000;
  static final int GREEN_MASK = 0x0000FF00;
  static final int BLUE_MASK = 0x000000FF;
  static final int RGB_MASK = 0x00FFFFFF;
  
  public static void main(String[] args) {
    try {
      File in = new File("C:\\Users\\Kiith Nabaal\\Desktop\\for now\\Computer Vision\\Image Processing\\Unequalized.jpg");
      BufferedImage img = ImageIO.read(in);
      BufferedImage equalizedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
      Graphics2D destImage = equalizedImage.createGraphics();
      
      double[] redHistogram = buildHistogram(img, 16);
      double[] greenHistogram = buildHistogram(img, 8);
      double[] blueHistogram = buildHistogram(img, 0);
      
      double[] redCDF = buildCDF(redHistogram);
      double[] greenCDF = buildCDF(greenHistogram);
      double[] blueCDF = buildCDF(blueHistogram);
      
      double[] redLUT = buildLUT(redCDF);
      double[] greenLUT = buildLUT(greenCDF);
      double[] blueLUT = buildLUT(blueCDF);
      
      for(int y = 0; y < img.getHeight(); y++) {
        for(int x = 0; x < img.getWidth(); x++) {
          int redIndex = ((((img.getRGB(x, y) & RGB_MASK) & RED_MASK) >> 16) & 0x000000FF);
          int greenIndex = ((((img.getRGB(x, y) & RGB_MASK) & GREEN_MASK) >> 8) & 0x000000FF);
          int blueIndex = ((img.getRGB(x, y) & RGB_MASK) & BLUE_MASK);
          
          int adjustedRed = (int)redLUT[redIndex] << 16;
          int adjustedGreen = (int)greenLUT[greenIndex] << 8;
          int adjustedBlue = (int)blueLUT[blueIndex];
          
          int adjustedColor = (adjustedRed | adjustedGreen | adjustedBlue);
          
          destImage.setColor(new Color(adjustedColor));
          destImage.fillRect(x, y, 1, 1);
        }
      }
      
      File out = new File("C:\\Users\\Kiith Nabaal\\Desktop\\for now\\Computer Vision\\Image Processing\\demo.jpg");
      ImageIO.write(equalizedImage, "jpg", out);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public static double[] buildHistogram(BufferedImage img, int shiftAmount) {
    double[] histogram = new double[256];
    
    for(int y = 0; y < img.getHeight(); y++) {
      for(int x = 0; x < img.getWidth(); x++) {
        int intensity = (img.getRGB(x, y) & RGB_MASK);
        int index = ((intensity >> shiftAmount) & 0x000000FF);
        histogram[index]++;
      }
    }
    
    double N = (double)(img.getHeight() * img.getWidth());
    
    for(int i = 0; i < histogram.length; i++) {
      histogram[i] /= N;
    }
    
    return histogram;
  }
  
  public static double[] buildCDF(double[] histogram) {
    double[] cdf = new double[256];
    
    for(int i = 0; i < histogram.length ; i++) {
      for(int j = 0; j <= i; j++) {
        cdf[i] += histogram[j];
      }
    }
    
    return cdf;
  }
  
  public static double[] buildLUT(double[] cdf) {
    double[] lut = new double[256];
    
    for(int i = 0; i < cdf.length; i++) {
      lut[i] = (cdf[i] * 256);
    }
    
    return lut;
  }
  
  public static BufferedImage visualizeChart(double[] chart) {
    BufferedImage img = new BufferedImage(2600, 500, BufferedImage.TYPE_INT_RGB);
    
    Graphics2D canvas = img.createGraphics();
    canvas.setColor(Color.GREEN);
    
    for(int i = 0; i < chart.length; i++) {
      int y = (int)(chart[i] * 100);
      canvas.fillRect(i * 10, img.getHeight() - y - 5, 10, 5);
    }
    
    return img;
  }
}