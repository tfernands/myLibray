package geometry;


import java.util.ArrayList;
import java.util.Arrays;
import mylibray.Vector2D;

public class QuickHull{
    
    public static Vector2D[] quickHull(Vector2D... points){
        ArrayList<Vector2D> convexHull = new ArrayList<>();
        
        if (points.length < 3){
            return points;
        }
        
        int minPoint = -1, maxPoint = -1;
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < points.length; i++){
            if (points[i].x < minX){
                minX = points[i].x;
                minPoint = i;
            }
            if (points[i].x > maxX){
                maxX = points[i].x;
                maxPoint = i;
            }
        }
        Vector2D A = points[minPoint];
        Vector2D B = points[maxPoint];
        convexHull.add(A);
        convexHull.add(B);
 
        ArrayList<Vector2D> leftSet = new ArrayList<>();
        ArrayList<Vector2D> rightSet = new ArrayList<>();
 
        for (Vector2D p : points) {
            if (p != A && p != B){
                if (pointLocation(A, B, p) == -1)
                    leftSet.add(p);
                else if (pointLocation(A, B, p) == 1)
                    rightSet.add(p);
            }
        }
        hullSet(A, B, rightSet, convexHull);
        hullSet(B, A, leftSet, convexHull);
        
        Vector2D[] convexArray = new Vector2D[convexHull.size()];
        convexHull.toArray(convexArray);
        return convexArray;
    }
 
    public static double distance(Vector2D A, Vector2D B, Vector2D C){
        double ABx = B.x - A.x;
        double ABy = B.y - A.y;
        double num = ABx * (A.y - C.y) - ABy * (A.x - C.x);
        if (num < 0)
            num = -num;
        return num;
    }
 
    public static void hullSet(Vector2D A, Vector2D B, ArrayList<Vector2D> set, ArrayList<Vector2D> hull){
        int insertPosition = hull.indexOf(B);
        if (set.isEmpty())
            return;
        if (set.size() == 1)
        {
            Vector2D p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }
        double dist = Double.NEGATIVE_INFINITY;
        int furthestPoint = -1;
        for (int i = 0; i < set.size(); i++)
        {
            Vector2D p = set.get(i);
            double distance = distance(A, B, p);
            if (distance > dist)
            {
                dist = distance;
                furthestPoint = i;
            }
        }
        Vector2D P = set.get(furthestPoint);
        set.remove(furthestPoint);
        hull.add(insertPosition, P);
 
        // Determine who's to the left of AP
        ArrayList<Vector2D> leftSetAP = new ArrayList<>();
        for (int i = 0; i < set.size(); i++)
        {
            Vector2D M = set.get(i);
            if (pointLocation(A, P, M) == 1)
            {
                leftSetAP.add(M);
            }
        }
 
        // Determine who's to the left of PB
        ArrayList<Vector2D> leftSetPB = new ArrayList<>();
        for (int i = 0; i < set.size(); i++)
        {
            Vector2D M = set.get(i);
            if (pointLocation(P, B, M) == 1)
            {
                leftSetPB.add(M);
            }
        }
        hullSet(A, P, leftSetAP, hull);
        hullSet(P, B, leftSetPB, hull);
 
    }
 
    public static int pointLocation(Vector2D A, Vector2D B, Vector2D P)
    {
        double cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
        if (cp1 > 0)
            return 1;
        else if (cp1 == 0)
            return 0;
        else
            return -1;
    }

}