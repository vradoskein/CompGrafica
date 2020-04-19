package plots;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import utilities.PixelMatrix;

/**
 *
 * @author bernardo6526
 */
public class Floodfill {
    
    static int widthg,heightg;
    static final PixelMatrix pm = new PixelMatrix();
    
    public static void plot(Point p1, Graphics2D g, Image image , int cor_preenche, int cor_antiga) {
        Image image2 = flood(pm.convertToPixels(image),p1,cor_preenche,cor_antiga);
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
            novo = new Point(atual.x,atual.y-1);q.add(novo); // cima
        }
      }       
   }
    
    private static Image flood(int[][] m, Point p1, int cor_preenche, int cor_antiga) {
        int width = m[0].length; int height = m.length;
        widthg = width;
        heightg = height;

        flood4Iterative(p1,cor_preenche,cor_antiga,m);

        // converte a matriz para um vetor de pixels
        int px[] = pm.pixelMatrixToArray(m);

        // criacao da Imagem a partir do vetor de pixels
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, width, height, px, 0, width);
        return image;
    }
}
