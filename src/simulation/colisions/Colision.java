/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.colisions;

import geometry.Edge;
import geometry.QuickHull;
import mylibray.Vector2D;


/**
 *
 * @author Thales
 */
public class Colision{
    
    public static boolean detectColisionSAT(Vector2D[] pA, Vector2D[] pB){
        // if (v - a) · n is greater than zero, then the vertex is in front of the edge.
        for (int i = 0 ; i < pA.length; i++){
            Vector2D a;
            Vector2D b;
            if (i == pA.length-1){
                a = pA[i];
                b = pA[0];
            }else{
                a = pA[i];
                b = pA[i+1];
            }
            Edge e = new Edge(a,b);
            boolean outside = true;
            for (Vector2D v : pB) {
                double res = Vector2D.sub(v, a).dot(e.getNormal());
                if (res < 0){
                    outside = false;
                    break;
                }
            }
            if (outside){
                return false;
            }
        }
        return true;
    }
    
    public static Vector2D[] msub(Vector2D[] pA, Vector2D[] pB){
        Vector2D[] msub = new Vector2D[pA.length*pB.length];
        int index = 0;
        for (Vector2D vp1 : pA){
            for (Vector2D vp2 : pB){
                msub[index] = Vector2D.sub(vp1, vp2);
                index++;
            }
        }
        return QuickHull.quickHull(msub);
    }
}
