package simulation.colisions;

import java.util.ArrayList;
import java.util.List;
import static mylibray.PCanvas.canvas;
import mylibray.Vector2D;
import static processing.core.PConstants.CLOSE;

/**
 * Abstract class implementation of a 2D GJK algorithm for convex polygon collision detection.
 * <br>
 * <br>
 * GJK collision detection relies on the fact that the Minkowski Difference created between
 * two polygon objects will always contain the origin if the two polygons are intersecting.
 * 
 * @author  Bryan Chacosky
 * @see     Polygon
 */
public abstract class GJK{
    
     /**
   * Returns the simplex if the two polygons are intersecting.
   * <br>
   * <br>
   * <b>assert</b> - Polygon 'a' must not be null.<br>
   * <b>assert</b> - Polygon 'b' must not be null.
   *
   * @param   p1 - Polygon a.
   * @param   p2 - Polygon b.
   * @return  True if the two polygons are intersecting, false otherwise.
   */
    public static Vector2D[] calcAndReturnSimplex(final Vector2D[] p1, final Vector2D[] p2){
        assert p1 != null;
        assert p2 != null;
        final List<Vector2D> simplex = new ArrayList<>( );
        final Vector2D direction = new Vector2D( 1.0, 0.0 );
        simplex.add( GJK.minkowski( p1, p2, direction ) );
        direction.mult(-1);
        while ( true ){
            Vector2D point = GJK.minkowski( p1, p2, direction );
            if ( point.dot( direction ) < 0 ) return null;
            simplex.add(point);
            if ( GJK.evaluate( simplex, direction ) == true ){
                Vector2D[] simplexArray = new Vector2D[3];
                simplex.toArray(simplexArray);
                return simplexArray;
            }
        }
    }
    
    /**
   * Returns true if the two polygons are intersecting.
   * <br>
   * <br>
   * <b>assert</b> - Polygon 'a' must not be null.<br>
   * <b>assert</b> - Polygon 'b' must not be null.
   *
   * @param   p1 - Polygon a.
   * @param   p2 - Polygon b.
   * @return  True if the two polygons are intersecting, false otherwise.
   */
    public static boolean intersects(final Vector2D[] p1, final Vector2D[] p2 ){
        assert p1 != null;
        assert p2 != null;

        // Create a simplex which is basically a dynamic polygon:
        final List<Vector2D> simplex = new ArrayList<>( );

        // Pick an arbitrary starting direction for simplicity:
        final Vector2D direction = new Vector2D( 1.0, 0.0 );

        // Initialize the simplex list with our first direction:
        simplex.add( GJK.minkowski( p1, p2, direction ) );

        // Negate the direction so we search in the opposite direction for the next Minkowski point
        // since the origin obviously won't be in the current direction:
        direction.mult(-1);

        while ( true ){
          // Search for a new point along the direction:
                Vector2D point = GJK.minkowski( p1, p2, direction );

            // For a potential collision, new point must go past the origin which means that the
            // dot product must be positive (ie. the angle between 'point' and 'direction' must
            // be less than 90 degrees ), otherwise the objects don't intersect.
            if ( point.dot( direction ) < 0 ) return false;

            // Point is valid so update the simplex:
            simplex.add(point);

            // Evaluate and update the simplex while checking for an early exit:
            if ( GJK.evaluate( simplex, direction ) == true ){
                canvas.beginShape();
                for (Vector2D v : simplex){
                    canvas.vertex((float)v.x,(float)v.y);
                }
                canvas.endShape(CLOSE);
                
                
                return true;
            }
        }
    }
  
    /**
   * Returns the Minkowski difference point (ie. the point further along the edge of the Minkowski
   * difference polygon in the direction of the vector).
   *
   * @param   p1 - First polygon.
   * @param   p2 - Section polygon.
   * @param   direction - Direction vector.
   * @return  Point along the edge of the Minkowski different in the passed direction.
   */
    private static Vector2D minkowski( final Vector2D[] p1, final Vector2D[] p2, final Vector2D direction ){
        /*
         * Minkowski difference is simply the support point from polygon A minus the
         * support point from polygon B in the opposite direction.
         */

        final Vector2D result = GJK.support( p1, direction );
        result.sub( GJK.support( p2, new Vector2D( -direction.x, -direction.y ) ) );
        return result;
    }

    /**
   * Returns the furthest point along the edge of the polygon in the director of the vector.
   *
   * @param   polygon - Polygon to evaluate.
   * @param   direction - Direction vector.
   * @return  Point along the edge of polygon in the passed direction.
   */
    private static Vector2D support( final Vector2D[] polygon, final Vector2D direction ){
        /*
         * The further point in any direction in the polygon must be a vertex point,
         * so iterate over each point in the polygon and compare the dot product (ie. scalar
         * evaluation of a point along a vector) and take the point with the highest value.
         */
        double  max   = -Double.MAX_VALUE;  // Maximum dot product value
        int     index = -1;                 // Index of furthest point in the direction

        for ( int i = 0; i != polygon.length; ++i ){
          double dot = direction.x * polygon[i].x + direction.y * polygon[i].y;

          if ( dot > max ){
            max   = dot;
            index = i;
          }
        }

        return new Vector2D( polygon[index].x, polygon[index].y );
  }
  
    /**
   * Returns true if the two vectors are pointing in the same direction.  Two vectors are
   * considered to be in the same direction if the angle between them is less than 90
   * degrees.
   *
   * @param   a - Vector a.
   * @param   b - Vector b.
   * @return  True if the two vectors are pointing in the same direction, otherwise false.
   */
    private static boolean sameDirection( final Vector2D a, final Vector2D b ){
        /*
         * For two vectors to be in the same direction, the angle between them
         * must be less than 90 degrees, ie. the cos(angle) will be greater
         * than zero.  Since dot(a,b) = |a| * |b| * cos(angle), and |?| is always
         * positive, we only need to compare the sign of the dot product.
         */

        return a.dot( b ) > 0;
  }
   
    /**
   * Evaluates a simplex object.  Simplex is expected to contain 2-3 points for
   * a 2D implementation.  Both the simplex and direction will be updated.
   *
   * @param   simplex   - Simplex object.  This will be modified.
   * @param   direction - Direction to search in.  This will be modified.
   * @return  True if the simplex contains the origin, otherwise false.
   * @throws  IllegalArgumentException If simplex does not contain 2-3 points.
   */
    private static boolean evaluate( List<Vector2D> simplex, Vector2D direction ){
        switch ( simplex.size() ){
            // Line segment:
            case 2:{
                // Pull the points from the simplex list:
                final Vector2D b  = simplex.get(1);
                final Vector2D c  = simplex.get(0);

                // Compute helper vectors:
                final Vector2D bo = Vector2D.sub(new Vector2D(), b);
                final Vector2D bc = Vector2D.sub(c, b);

                // Adjust the direction to be perpendicular to AB, pointing towards the origin:
                direction.set( -bc.y, bc.x );
                if ( GJK.sameDirection(direction, bo) == false ) direction.mult(-1);

                // Continue building the simplex:
                return false;
            }

            // Triangle:
            case 3:{

                // Pull the points from the simplex list:
                final Vector2D a  = simplex.get(2);
                final Vector2D b  = simplex.get(1);
                final Vector2D c  = simplex.get(0);

                // Compute helper vectors:
                final Vector2D ao = Vector2D.sub(new Vector2D(), a);
                final Vector2D ab = Vector2D.sub(b, a);
                final Vector2D ac = Vector2D.sub(c, a);

                // Adjust the direction to be perpendicular to AB, pointing away from C:
                direction.set( -ab.y, ab.x );
                if ( GJK.sameDirection( direction, c ) == true ) direction.mult(-1);

                // If the perpendicular vector from the edge AB is heading towards the origin,
                // then we know that C is furthest from the origin and we can safely
                // remove to create a new simplex away from C:
                if ( GJK.sameDirection( direction, ao ) == true ){
                    simplex.remove( 0 );
                    return false;
                }

                // Adjust the direction to be perpendicular to AC, pointing away from B:
                direction.set( -ac.y, ac.x );
                if ( GJK.sameDirection( direction, b ) == true ) direction.mult(-1);

                // If the perpendicular vector from the edge AC is heading towards the origin,
                // then we know that B is furthest from the origin and we can safely
                // remove to create a new simplex away from B:
                if ( GJK.sameDirection( direction, ao ) == true ){
                    simplex.remove( 1 );
                    return false;
                }

                // If the perendicular vectors generated from the edges of the triangle
                // do not point in the direction of the origin, then the origin must be
                // contained inside of the triangle:
                return true;
            }

            default:
                throw new IllegalArgumentException( "Invalid number of points in the GJK simplex: " + simplex.size( ) );
        }
    }
  
}
