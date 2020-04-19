package utilities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author bernardo6526
 */
public class PixelMatrix {

    // converts the pixel matrix to a singular array
    public static int[] pixelMatrixToArray(int[][] m) {
        int width = m[0].length;
        int height = m.length;

        int px[] = new int[width * height];
        int k = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // System.out.println("COR:"+m[i][j]);
                px[k++] = m[i][j];
            }
        }
        return px;
    }

    // conver the image into a pixel matrix
    public static int[][] convertToPixels(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage img = toBufferedImage(image);
        int[][] matrix = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                matrix[row][col] = img.getRGB(col, row);
            }
        }

        return matrix;
    }

    // converts the matrix into an immage
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
