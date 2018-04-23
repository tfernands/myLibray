package mylibray;

/**
 *
 * @author Thales
 */
public class Vector2D {

    public double x,y;

    public Vector2D() {
    }
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D(double[] p) {
        this.x = p[0];
        this.y = p[1];
    }

    public Vector2D set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public Vector2D set(Vector2D v) {
        x = v.x;
        y = v.y;
        return this;
    } 
    public Vector2D set(double[] source) {
        x = source[0];
        y = source[1];
        return this;
    }

    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    public double mag() {
        return Math.sqrt(magSq());
    }

    public double magSq() {
        return x*x + y*y;
    }

    public Vector2D add(Vector2D v) {
        x += v.x;
        y += v.y;
        return this;
    }
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }
    static public Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x + v2.x,v1.y + v2.y);
    }
    
    public Vector2D sub(Vector2D v) {
        x -= v.x;
        y -= v.y;
        return this;
    }
    public Vector2D sub(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    static public Vector2D sub(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    public Vector2D mult(double n) {
        x *= n;
        y *= n;
        return this;
    }
    static public Vector2D mult(Vector2D v, double n) {
        return new Vector2D(v.x*n, v.y*n);
    }

    public Vector2D div(double n) {
      x /= n;
      y /= n;
      return this;
    }
    static public Vector2D div(Vector2D v, double n) {
        return new Vector2D(v.x/n, v.y/n);
    }

    public double dist(Vector2D v) {
        double dx = x - v.x;
        double dy = y - v.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
    
    public double distSq(Vector2D v) {
        double dx = x - v.x;
        double dy = y - v.y;
        return dx*dx + dy*dy;
    }
    static public double dist(Vector2D v1, Vector2D v2) {
        double dx = v1.x - v2.x;
        double dy = v1.y - v2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
    static public double distSq(Vector2D v1, Vector2D v2) {
        double dx = v1.x - v2.x;
        double dy = v1.y - v2.y;
        return dx*dx + dy*dy;
    }
    
    public static Vector2D closestPoint(Vector2D point, Vector2D lineP1, Vector2D lineP2) {
        Vector2D line = new Vector2D(lineP2.x - lineP1.x, lineP2.y - lineP1.y);
        line.normalize();
        double dot = Vector2D.dot(line,point.copy().sub(lineP1));
        line.mult(dot).add(lineP1);
        return line.mult(-1); // point
    }

    public double dot(Vector2D v) {
        return x*v.x + y*v.y;
    }
    public double dot(double x, double y) {
        return this.x*x + this.y*y;
    }
    static public double dot(Vector2D v1, Vector2D v2) {
        return v1.x*v2.x + v1.y*v2.y;
    }
    
    static public double cross(Vector2D v1, Vector2D v2){
        return v1.x * v2.y - v1.y * v2.x;
    }

    public Vector2D normalize() {
        double m = mag();
        if (m != 0 && m != 1) {
          div(m);
        }
        return this;
    }

    public Vector2D limit(double max) {
        if (magSq() > max*max) {
            normalize();
            mult(max);
        }
        return this;
    }

    public Vector2D setMag(double len) {
        normalize();
        mult(len);
        return this;
    }

    public double heading() {
        return Math.atan2(y, x);
    }

    public Vector2D rotate(double theta) {
        double temp = x;
        // Might need to check for rounding errors like with angleBetween function?
        x = x*Math.cos(theta) - y*Math.sin(theta);
        y = temp*Math.sin(theta) + y*Math.cos(theta);
        return this;
    }

//    public PVector lerp(PVector v, float amt) {
//      x = PApplet.lerp(x, v.x, amt);
//      y = PApplet.lerp(y, v.y, amt);
//      return this;
//    }
    
    public double angleOrigin(){
        double angle = Vector2D.angleBetween(new Vector2D(0,1),this);
        if (this.x >= 0){
            return angle;
        }else{
            return Math.PI*2-angle;
        }
    }
    
    static public double angleBetween(Vector2D v1, Vector2D v2){
        // We get NaN if we pass in a zero vector which can cause problems
        // Zero seems like a reasonable angle between a (0,0,0) vector and something else
        if (v1.x == 0 && v1.y == 0) return 0.0d;
        if (v2.x == 0 && v2.y == 0) return 0.0d;

        double amt = Vector2D.dot(v1, v2) / (v1.mag() * v2.mag());
        // But if it's not due to rounding error, then we need to fix it
        // http://code.google.com/p/processing/issues/detail?id=340
        // Otherwise if outside the range, acos() will return NaN
        // http://www.cppreference.com/wiki/c/math/acos
        if (amt <= -1) {
          return Math.PI;
        } else if (amt >= 1) {
          // http://code.google.com/p/processing/issues/detail?id=435
          return 0;
        }
        return Math.acos(amt);
    }


    @Override
    public String toString() {
      return "[ " + x + ", " + y + " ]";
    }

    public double[] array() {
        double[] array = new double[2];
        array[0] = x;
        array[1] = y;
        return array;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2D)) {
          return false;
        }
        final Vector2D p = (Vector2D) obj;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

}
