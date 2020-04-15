package plots;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author bernardo6526
 */
public class Floodfill {
    
    static int widthg,heightg;
    
    public static void plot(Point p1, Graphics2D g, Image image , int cor_preenche, int cor_antiga) {
        Image image2 = flood(convertToPixels(image),p1,cor_preenche,cor_antiga);
        g.drawImage(image2, 0, 0, null);       
    }
    
    // nao foi utiliza, está aqui apenas para propósito de aprendizado
    private static void flood4(int x, int y, int cor_preenche , int cor_antiga,int[][] m){
        if((x > 0 && x < widthg) && (y > 0 && y < heightg) && (m[y][x] == cor_antiga))
        {
            m[y][x] = cor_preenche; // set pixel x,y atual
            flood4(x+1,y,cor_preenche,cor_antiga,m); // dir
            flood4(x-1,y,cor_preenche,cor_antiga,m); // esq
            flood4(x,y+1,cor_preenche,cor_antiga,m); // baixo (y cresce para baixo no nosso canvas)
            flood4(x,y-1,cor_preenche,cor_antiga,m); // cima
        }
    }
    
     private static void flood4Iterative(Point p1, int cor_preenche, int cor_antiga,int[][] m)
   {
      Queue<Point> q = new LinkedList(); 
         
      
      Point atual = new Point(p1.x,p1.y);
      q.add(atual);
   
      while(!q.isEmpty()){
        atual = q.poll(); // pega valores da chamada atual
        if((atual.x > 0 && atual.x < widthg) && (atual.y > 0 
         && atual.y < heightg) && (m[atual.y][atual.x] == cor_antiga)){            
            m[atual.y][atual.x] = cor_preenche; // colore o pixel
            
            //chamadas recursivas
            Point novo = new Point(atual.x+1,atual.y);q.add(novo); // dir
            novo = new Point(atual.x-1,atual.y);q.add(novo); // esq
            novo = new Point(atual.x,atual.y+1);q.add(novo); // baixo (y cresce para baixo no nosso canvas)
            novo = new Point(atual.x,atual.y-1);q.add(novo); // co,a
        }
      }       
   }
    
    private static Image flood(int[][] m, Point p1, int cor_preenche, int cor_antiga) {
        int width = m[0].length; int height = m.length;
        widthg = width;
        heightg = height;

        flood4Iterative(p1,cor_preenche,cor_antiga,m);

        // preenchimento do vetor de pixels novo
        int px[] = new int[width*height];
        int k = 0;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                //System.out.println("COR:"+m[i][j]);
                px[k++] = m[i][j];
            }
        }

        // criacao da Imagem a partir do vetor de pixels
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, width, height, px, 0, width);
        return image;
    }
    
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
