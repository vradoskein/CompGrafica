package clipping;

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
public class ClipCirc {

    static final PixelMatrix pm = new PixelMatrix();

    public static void clipcircstart(Point p1, Point p2, Point p3, Point p4, Graphics2D g, Image img){
        int[] x = new int[4];
        int[] y = new int[4];

        x[0] = p1.x;
        x[1] = p2.x;
        x[2] = p3.x;
        x[3] = p4.x;

        y[0] = p1.y;
        y[1] = p2.y;
        y[2] = p3.y;
        y[3] = p4.y;

        int x_max=x[0],y_max=y[0];
        int x_min=x[0],y_min=y[0];

        //compara todos os pontos para achar os limites da janela de recorte
        for(int i=1;i<4;i++){
            if(x[i] > x_max){
                x_max = x[i];
            }
            if(y[i] > y_max){
                y_max = y[i];
            }

            if(x[i] < x_min){
                x_min = x[i];
            }

            if(y[i] < y_min){
                y_min = y[i];
            }
        }

        int m[][] = pm.convertToPixels(img);
        int width = m[0].length;
        int height = m.length;

        //pinta de branco tudo que estiver fora da janela
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if((i > y_min && i < y_max) && (j > x_min && j < x_max)){ //if dentro da janela
                    continue;
                }else{
                    m[i][j] = -1;
                }
            }
        }

        //converte a matriz de pixels para um vetor de pixels
        int px[] = pm.pixelMatrixToArray(m);

        // criacao da Imagem a partir do vetor de pixels
        BufferedImage image = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, width, height, px, 0, width);
        g.drawImage(image, 0, 0, null);
    }

}
