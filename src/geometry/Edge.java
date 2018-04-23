//Bug when the line is perpendicular to the 'x' axis
//in y = ax + b => 'a' becomes infinty

package geometry;

import mylibray.Vector2D;
import static mylibray.PCanvas.canvas;

public final class Edge{
    public Vector2D p1, p2;
    
    public Edge(Vector2D p1, Vector2D p2){
        set(p1,p2);
    }
    public Edge(double x1, double y1, double x2, double y2){
        p1 = new Vector2D();
        p2 = new Vector2D();
        set(x1,y1,x2,y2);
    }
    
    public void set(Vector2D p1, Vector2D p2){
        this.p1 = p1; this.p2 = p2;
    }
    public void set(double x1, double y1, double x2, double y2){
        p1.set(x1,y1); p2.set(x2,y2);
    }
    
    public boolean obeyLineEquation(Vector2D p, double error){
        double[] f = getLineEquationVariables();
        double eq = f[0]*p.x+f[1];
        return (p.y >= eq-error && p.y <= eq+error);
    }
    public boolean isInLine(Vector2D p){
        if (p.x >= Math.min(p1.x,p2.x) && p.x <= Math.max(p1.x,p2.x)){
            if (p.y >= Math.min(p1.y,p2.y) && p.y <= Math.max(p1.y,p2.y)){
                return true;
            }
        }
        return false;
    }
     
    public Vector2D intersection (Edge e2){
        return Edge.intersection(this,e2);
    }
    public static Vector2D intersection (Edge e1, Edge e2){
        
        double[] f1 = e1.getLineEquationVariables();
        double[] f2 = e2.getLineEquationVariables();
        
        if (Math.abs(f1[0]) == Math.abs(f2[0])){
            return null;
        }

        double x = -(f2[1]-f1[1]) / (f2[0]-f1[0]);
        double y = f1[0]*x+f1[1];
        Vector2D i = new Vector2D(x,y);
        
        if (e1.isInLine(i) && e2.isInLine(i)){
            return new Vector2D(x,y);
        }else{
            return null;
        }
    }
    public Vector2D dist(Vector2D p){
        return dist(this,p);
    }
    public static Vector2D dist(Edge e, Vector2D p){
        Vector2D dot = getDistance(e, p);
        if (e.p1.x < e.p2.x){
            if (dot.x < e.p1.x){
                return e.p1;
            }else
            if (dot.x > e.p2.x){
                return e.p2;
            }
        }else
        if (e.p2.x < e.p1.x){
            if (dot.x < e.p2.x){
                return e.p2;
            }else
            if (dot.x > e.p1.x){
                return e.p1;
            }
        }else{
            if (e.p1.y < e.p2.y){
                if (dot.y < e.p1.y){
                    return e.p1;
                }else
                if (dot.y > e.p2.y){
                    return e.p2;
                }
            }else{
                if (dot.y < e.p2.y){
                    return e.p2;
                }else 
                if (dot.y > e.p1.y){
                    return e.p1;
                }
            }
        }
        return dot; 
    }
    
    public Vector2D getVector(){
        return new Vector2D(p2.x - p1.x, p2.y - p1.y);
    }
    public void display(){
        canvas.line((float)p1.x, (float)p1.y, (float)p2.x, (float)p2.y);
    }
    public void displayNormal(double size){
        double x = (p1.x+p2.x)/2;
        double y = (p1.y+p2.y)/2;
        Vector2D normal = getNormal();
        canvas.line((float)x,(float)y,(float)(x+normal.x*size),(float)(y+normal.y*size));
    }
    
    public double[] getLineEquationVariables(){
        double[] f = new double[2];
        f[0] = (p2.y - p1.y)/(p2.x - p1.x);
        f[1] = p1.y-f[0]*p1.x;
        return f;
    }
 
    public Vector2D getNormal(){
        Vector2D vec = getVector();
        Vector2D normal = new Vector2D();
        if (vec.y == 0){
          if (p1.x < p2.x){
            normal.set(0,1);
          }else{
            normal.set(0,-1);
          }
          return normal;
        }
        double a = -vec.x/vec.y;
        Vector2D n = new Vector2D(1,a);
        if (vec.y > 0){
          n.mult(-1);
        }
        normal.set(n.x,n.y);
        normal.normalize();
        return normal;
    }
    private static Vector2D getDistance(Edge e, Vector2D p){
        Vector2D l = e.getVector().normalize();
        return l.mult(Vector2D.dot(l,p.copy().sub(e.p1))).add(e.p1);
    }
    public Edge copy(){
        return new Edge(p1.copy(),p2.copy());
    }
}
