/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import static mylibray.PCanvas.canvas;
import processing.core.PImage;
/**
 *
 * @author Thales
 */
public class PImageFunctions {
    
    public static class HeatMap{
        public static final int[] BW = new int[] {
            F.toColor(0),
            F.toColor(255)
        };
        public static final int[] PRESSURE_MAP_1 = new int[] {
            F.toColor(  0,   0, 255),
            F.toColor(  0,   0,   0),
            F.toColor(255,   0,   0),
        };
        public static final int[] HEAT_MAP_1 = new int[] {
            F.toColor(  0,   0, 255),
            F.toColor(  0, 255, 255),
            F.toColor(  0, 255,   0),
            F.toColor(255, 255,   0),
            F.toColor(255,   0,   0),
        };
        public static final int[] HEAT_MAP_2 = new int[] {
            F.toColor(  0,   0,   0),
            F.toColor(  0,   0, 255),
            F.toColor(  0, 255, 255),
            F.toColor(  0, 255,   0),
            F.toColor(255, 255,   0),
            F.toColor(255,   0,   0),
            F.toColor(255, 255, 255),
        };
        
        public static int colorScale(int[] colors, double min, double max, double value){
            if (value <= min) return colors[0];
            if (value >= max) return colors[colors.length-1];
            double tempValue = (value-min)/(max-min); //map value from min, max to 0, 1
            double temp = 1.f/(colors.length-1);
            int index = (int)(tempValue/temp);
            tempValue = F.map(tempValue,temp*index,temp*index+temp,0,1);
            return canvas.lerpColor(colors[index],colors[index+1],(float)tempValue);
        }
        
        public static PImage heatMap(float[] pixels, int width, int height, int[] colorMap){
            float min = Float.POSITIVE_INFINITY;
            float max = Float.NEGATIVE_INFINITY;
            for (int i = 0; i < pixels.length; i++){
                if (min > pixels[i]) min = pixels[i];
                if (max < pixels[i]) max = pixels[i];
            }
            PImage temp = new PImage(width,height);
            for (int i = 0; i < width*height; i ++){
                temp.pixels[i] = colorScale(colorMap,min,max,pixels[i]);
            }
            return temp;
        }
        
        public static PImage heatMap(double[] pixels, int width, int height, double minValue, double maxValue, int[] colorMap){
            PImage temp = new PImage(width,height);
            for (int i = 0; i < width*height; i ++){
                temp.pixels[i] = colorScale(colorMap,minValue,maxValue,pixels[i]);
            }
            return temp;
        }
        public static PImage heatMap(double[] pixels, int width, int height, int[] colorMap){
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < pixels.length; i++){
                if (min > pixels[i]) min = pixels[i];
                if (max < pixels[i]) max = pixels[i];
            }
            PImage temp = new PImage(width,height);
            for (int i = 0; i < width*height; i ++){
                temp.pixels[i] = colorScale(colorMap,min,max,pixels[i]);
            }
            return temp;
        }
        
    }
    
    public static PImage expands (PImage p, int border) {
        PImage img = new PImage(p.width+border*2,p.height+border*2);
        for (int by = 0; by < border; by++){
            for (int bx = 0; bx < border; bx++){
                img.pixels[bx+by*img.width] = p.pixels[0];
                img.pixels[bx+by*img.width+p.width+border] = p.pixels[p.width-1];
                img.pixels[bx+(by+p.height+border)*img.width] = p.pixels[(p.height-1)*p.width];
                img.pixels[bx+(by+p.height+border)*img.width+p.width+border] = p.pixels[p.pixels.length-1];
            }
        }
        for (int i = 0; i < p.pixels.length; i++){
            int x = i%p.width;
            int y = i/p.width;
            img.pixels[(y+border)*img.width+x+border] = p.pixels[i];
        }
        for (int i = 0; i < p.width; i++){
            for (int b = 0; b < border; b++){
                img.pixels[i+border+b*img.width] = p.pixels[i];
                img.pixels[(img.height-1)*img.width+i+border-b*img.width] = p.pixels[(p.height-1)*p.width+i];
            }
        }
        for (int i = 0; i < p.width; i++){
            for (int b = 0; b < border; b++){
                img.pixels[i*img.width+border*img.width+b] = p.pixels[i*p.width];
                img.pixels[i*img.width+img.width-border+border*img.width+b] = p.pixels[i*p.width+p.width-1];
            }
        }
        return img;
    }
    public static PImage repeat (PImage p, int bl, int bu, int br, int bd) {
        PImage img = new PImage(p.width+bl+br,p.height+bu+bd);
        //COPY IMAGE
        for (int i = 0; i < p.pixels.length; i++){
            int x = i%p.width;
            int y = i/p.width;
            img.pixels[(y+bu)*img.width+x+bl] = p.pixels[i];
        }
        //BORDER UP
        for (int i = 0; i < p.width; i++){
            for (int b = 0; b < bu; b++){
                int pb = b%p.height;
                img.pixels[bl+i+(bu-b-1)*img.width] = p.pixels[i+(p.height-pb-1)*p.width];
            }
        }
        //BORDER DOWN
        for (int i = 0; i < p.width; i++){
            for (int b = 0; b < bd; b++){
                int pb = b%p.height;
                img.pixels[bl+i+(img.height-bd+b)*img.width] = p.pixels[i+pb*p.width];
            }
        }
        //BORDER LEFT
        for (int i = 0; i < img.height; i++){
            for (int b = 0; b < bl; b++){
                int pb = b%p.width;
                img.pixels[i*img.width+bl-b-1] = img.pixels[i*img.width+img.width-br-pb-1];
            }
        }
        //BORDER RIGHT
        for (int i = 0; i < img.height; i++){
            for (int b = 0; b < br; b++){
                int pb = b%p.width;
                img.pixels[i*img.width+img.width-br+b] = img.pixels[i*img.width+bl+pb];
            }
        }    
        return img;
    }
}
