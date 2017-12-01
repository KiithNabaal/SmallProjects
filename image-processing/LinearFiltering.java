import java.io.File;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/*
 * Filtering can be used as a way to reduce noise that results from imperfect
 * cameras.
 */

public class LinearFiltering {
  public static final int RED_MASK = 0x00FF0000;
  public static final int GREEN_MASK = 0x0000FF00;
  public static final int BLUE_MASK = 0x000000FF;
  
  public static final int RED_SHIFT = 16;
  public static final int GREEN_SHIFT = 8;
  
  public static void main(String[] args) {
    try {
      File imageFile = new File("C:\\Users\\Kiith Nabaal\\Desktop\\for now\\Computer Vision\\Image Processing\\1.jpg");
      BufferedImage src = ImageIO.read(imageFile);
      BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
      Graphics2D destCanvas = dest.createGraphics();
      
      /* kernels can also use weights for better effects. The closer to the center
       * of the kernel, the higher the value. The further away, the lower the value. */
      double[][] kernel = {{ 1.0, 1.0, 1.0, 1.0, 1.0 }, 
                           { 1.0, 1.0, 1.0, 1.0, 1.0 },
                           { 1.0, 1.0, 1.0, 1.0, 1.0 },
                           { 1.0, 1.0, 1.0, 1.0, 1.0 },
                           { 1.0, 1.0, 1.0, 1.0, 1.0 }};
      
      /*
      double[][] kernel = {{ -1, -1, -1 },
                           { -1, 8,  -1 },
                           { -1, -1, -1 }};*/
      
      int k = (kernel.length / 2);
      int maxHeight = (src.getHeight() - k);
      int maxWidth = (src.getWidth() - k);
      
      for(int y = k; y < maxHeight; y++) {
        for(int x = k; x < maxWidth; x++) {
          int blurredColor = meanBlur(kernel, src, x, y);
          //int gaussianColor = gaussianBlur(kernel, src, x, y);
          //int blurredColor = copyPixel(kernel, src, x, y);
          
          destCanvas.setColor(new Color(blurredColor));
          destCanvas.fillRect(x, y, 1, 1);
        }
      }
      
      File out = new File("C:\\Users\\Kiith Nabaal\\Desktop\\for now\\Computer Vision\\Image Processing\\filtered.jpg");
      ImageIO.write(dest, "jpg", out);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public static double kernelSum(double[][] kernel) {
    double sum = 0.0;
    
    for(int i = 0; i < kernel.length; i++) {
      for(int j = 0; j < kernel[0].length; j++) {
        sum += kernel[i][j];
      }
    }
    
    return sum;
  }
  
  public static int meanBlur(double[][] kernel, BufferedImage f, int x, int y) {
    int redSum = 0, greenSum = 0, blueSum = 0, k = (kernel.length / 2);
    double kernelArea = (double)(kernel.length * kernel.length);
    
    for(int i = -k; i <= k; i++) {
      for(int j = -k; j <= k; j++) {
        int color = f.getRGB((x + i), (y + j));
        double kernelFactor = kernel[i + k][j + k];
        
        redSum += ((int)((color & RED_MASK) >> RED_SHIFT) * kernelFactor);
        greenSum += ((int)((color & GREEN_MASK) >> GREEN_SHIFT) * kernelFactor);
        blueSum += (int)((color & BLUE_MASK) * kernelFactor);
      }
    }
    
    int red = (int)(redSum / kernelArea);
    int green = (int)(greenSum / kernelArea);
    int blue = (int)(blueSum / kernelArea);
    
    return ((red << RED_SHIFT) | (green << GREEN_SHIFT) | blue);
  }
  
  public static int gaussianBlur() {
    
    return 0; 
  }
  
  public static int copyPixel(double[][] kernel, BufferedImage f, int x, int y) {
    int redSum = 0, greenSum = 0, blueSum = 0, k = (kernel.length / 2);
    
    for(int i = -k; i <= k; i++) {
      for(int j = -k; j <= k; j++) {
        int color = f.getRGB((x + i), (y + j));
        double kernelFactor = kernel[i + k][j + k];
        
        redSum += ((int)((color & RED_MASK) * kernelFactor) >> RED_SHIFT);
        greenSum += ((int)((color & GREEN_MASK) * kernelFactor) >> GREEN_SHIFT);
        blueSum += (int)((color & BLUE_MASK) * kernelFactor);
      }
    }
    
    int red = redSum;
    int green = greenSum;
    int blue = blueSum;
    
    return ((red << RED_SHIFT) | (green << GREEN_SHIFT) | blue);
  }
  
  
}