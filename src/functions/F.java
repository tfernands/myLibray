package functions;

import static mylibray.PCanvas.canvas;
import mylibray.Vector2D;
import processing.core.PVector;

public class F {
    /**
     * <p>Map a given value from a given range to an other</p>
     * @param value value
     * @param lr1 lower value range
     * @param hr1 higher value range
     * @param lr2 new lower value range
     * @param hr2 new higher value range
     * @return correspondent maped value
     */
    public static double map(double value, double lr1, double hr1, double lr2, double hr2){
        return (value*(hr2-lr2)-lr1*hr2+hr1*lr2)/(hr1-lr1);
    }
    /**
     * <p>generate a random number between a given range</p>
     * @param min inclusive min range
     * @param max exclusive max range
     * @return random number
     */
    public static double random(double min, double max){
        return Math.random()*(max-min)+min;
    }
    /**
     * <p>Return the Euclidean distance between two points</p>
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double dist(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx*dx + dy*dy);
    }
    /**
    * <p>Return the square of the Euclidean distance between two points</p>
    * @param x1
    * @param y1
    * @param x2
    * @param y2
    * @return
    */
    public static double distSq(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return dx*dx + dy*dy;
    }
    
    /**
     * <p>Calc the porcentage of that number relative to the total sum of the array</p>
     * <p>
     * Ex.: 
     * <ul>
     *  <li>{1, 1, 1, 1} returns {0.25, 0.25, 0.25, 0.25}</li>
     *  <li>{1, 5, 4, 0} returns {0.1, 0.5, 0.4, 0}</li>
     *  <li>{2, 10, 8, 0} also returns {0.1, 0.5, 0.4, 0}</li>
     * </ul>
     * </p>
     * @param values
     * @return porcentage of each value to the total
     */
    public static double[] porcentage(double[] values){
        double sum = 0;
        for (int i = 0; i < values.length; i++){
            sum += values[i];
        }
        double[] scores = new double[values.length];
        for (int i = 0; i < values.length; i++){
            scores[i] = values[i]/sum;
        }
        return scores;
    }
    /**
     * <p>Return a random index of the array based on a probability value of the given array</p>
     * <p>Ex.: for an array {0.1, 0.5, 0.4} the function will
     * return respectivity 1, 2 and 3,  10%, 50% and 40% of the time</p>
     * <p>the <b>sum</b> of the values of the array <b>MUST add up to 1</b> to work properly</p>
     * @param porcentages
     * @return index of the random probability
     */
    public static int randomByProbability(double[] porcentages){
        int select = 0;
        double selector = Math.random();
        while(selector > 0){
            selector-=porcentages[select];
            select++;
        }
        select--;
        return select;
    }
    
    //DRAW VECTORS
    /**
     *Draw the vector relative to origin
     * @param v vector
     */
    public static void displayVector(Vector2D v){
        canvas.line(0,0,(float)v.x, (float)v.y);
    }
    /**
     *Draw the vector relative to a given position vector
     * 
     * @param v vector
     * @param pos position vector
     */
    public static void displayVector(Vector2D v, Vector2D pos){
        canvas.line((float)pos.x, (float)pos.y, (float)(v.x+pos.x), (float)(v.y+pos.y));
    }
    /**
     *Draw the vector relative to a given position x and y
     * 
     * @param v vector
     * @param x position x
     * @param y position y
     */
    public static void displayVector(Vector2D v, double x, double y){
        canvas.line((float)x, (float)y, (float)(v.x+x), (float)(v.y+y));
    }
    /**
     *Draw the vector relative to origin
     * @param v vector
     */
    public static void displayVector(PVector v){
        canvas.line(0, 0, v.x, v.y);
    }
    /**
     *Draw the vector relative to a given position vector
     * 
     * @param v vector
     * @param pos position vector
     */
    public static void displayVector(PVector v, PVector pos){
        canvas.line(pos.x, pos.y, v.x+pos.x, v.y+pos.y);
    }
    /**
     *Draw the vector relative to a given position x and y
     * 
     * @param v vector
     * @param x position x
     * @param y position y
     */
    public static void displayVector(PVector v, float x, float y){
        canvas.line(x, y, v.x+x, v.y+y);
    }
    
    public static void drawArrow(float cx, float cy, float len, float angle, float size){
        canvas.pushMatrix();
        canvas.translate(cx, cy);
        canvas.rotate(angle);
        canvas.line(0,0,len, 0);
        canvas.line(len, 0, len - size, -size);
        canvas.line(len, 0, len - size, size);
        canvas.popMatrix();
    }
    public static void drawArrow(Vector2D v, float x, float y, float size){
        float len = (float)v.mag();
        canvas.pushMatrix();
        canvas.translate(x, y);
        canvas.rotate((float)(-v.angleOrigin()+Math.PI/2));
        canvas.line(0,0,len, 0);
        canvas.line(len, 0, len - size, -size);
        canvas.line(len, 0, len - size, size);
        canvas.popMatrix();
    }
    public static void drawArrow(Vector2D v, Vector2D pos, float size){
        float len = (float)v.mag();
        canvas.pushMatrix();
        canvas.translate((float)pos.x,(float)pos.y);
        canvas.rotate((float)(-v.angleOrigin()+Math.PI/2));
        canvas.line(0,0,len, 0);
        canvas.line(len, 0, len - size, -size);
        canvas.line(len, 0, len - size, size);
        canvas.popMatrix();
    }
    
    public static void drawArrow(float rotation, Vector2D pos, float size){
        canvas.pushMatrix();
        canvas.translate((float)pos.x,(float)pos.y);
        canvas.rotate((float)(-rotation+Math.PI/2));
        canvas.line(0, 0, size, -size);
        canvas.line(0, 0, size, size);
        canvas.popMatrix();
    }
    
    public static int getAlpha(int c){
        return (c >> 24) & 0xFF;
    }
    public static int getRed(int c){
        return (c >> 16) & 0xFF;
    }
    public static int getGreen(int c){
        return (c >> 8) & 0xFF;
    }
    public static int getBlue(int c){
        return c & 0xFF;
    }
    public static int toColor(int r, int g, int b, int a){
        if(r>255){r=255;}if(g>255){g=255;}if(b>255){b=255;}if(a>255){a=255;}
        if(r<0){r=0;}if(g<0){g=0;}if(b<0){b=0;}if(a<0){a=0;}
        return (a<<24|r<<16|g<<8|b);
      }
    public static int toColor(int r, int g, int b){
        if(r>255){r=255;}if(g>255){g=255;}if(b>255){b= 255;}
        if(r<0){r=0;}if(g<0){g=0;}if(b<0){b=0;}
        return (255<<24|r<<16|g<<8|b);
      }
    public static int toColor(int bw, int a){
        if(bw>255){bw=255;}if(a>255){a=255;}
        if(bw<0){bw=0;}if(a<0){a=0;}
        return (a<<24|bw<<16|bw<<8|bw);
      }
    public static int toColor(int bw){
        if(bw>255){bw=255;}
        if(bw<0){bw=0;}
        return (255<<24|bw<<16|bw<<8|bw);
      }
    public static int colorAddInt(int c, int intensity){
        int r = getRed(c) + intensity,
            g = getGreen(c) + intensity,
            b = getBlue(c) + intensity;
        return toColor(r,g,b,getAlpha(c));
      }
    public static int colorSetAlpha(int c, int alpha){
        int r = getRed(c),
            g = getGreen(c),
            b = getBlue(c);
        return toColor(r,g,b,alpha);
    }
    public static int colorAddColor(int c, int c2){
        int r = getRed(c) + getRed(c2),
            g = getGreen(c) + getGreen(c2),
            b = getBlue(c) + getBlue(c2),
            a = getAlpha(c) + getAlpha(c2);
        return toColor(r,g,b,a);
    }
}
