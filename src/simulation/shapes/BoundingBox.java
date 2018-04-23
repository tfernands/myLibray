/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.shapes;

import static mylibray.PCanvas.canvas;
import mylibray.Vector2D;
import static processing.core.PConstants.CORNERS;

/**
 *
 * @author Thales
 */
public class BoundingBox {
    public Vector2D min;
    public Vector2D max;
    
    public BoundingBox(){
        min = new Vector2D();
        max = new Vector2D();
    }
    
    public void calcBoundingBox(PhysicsShape shape, Vector2D pos, double angle){
        min.set(0,0);
        max.set(0,0);
        Vector2D[] vertexs = shape.getVertexs();
        for (Vector2D v : vertexs) {
            v.rotate(angle);
        }
        for (Vector2D v : vertexs) {
            if (v.x < min.x){
                min.x = v.x;
            }else
            if (v.x > max.x){
                max.x = v.x;
            }
            if (v.y < min.y){
                min.y = v.y;
            }else
            if (v.y > max.y){
                max.y = v.y;
            }
        }
        min.add(pos);
        max.add(pos);
    }
    
    public static boolean Intersection(BoundingBox a, BoundingBox b){
        double d1x = b.min.x - a.max.x;
        double d1y = b.min.y - a.max.y;
        double d2x = a.min.x - b.max.x;
        double d2y = a.min.y - b.max.y;
        if (d1x > 0.0f || d1y > 0.0f) return false;
        if (d2x > 0.0f || d2y > 0.0f) return false;
        return true;
    }
    
    public void display(){
        canvas.rectMode(CORNERS);
        canvas.rect((float)min.x, (float)min.y, (float)max.x, (float)max.y);
    }
}