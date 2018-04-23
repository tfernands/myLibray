/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import static mylibray.PCanvas.canvas;
import mylibray.Vector2D;
import static processing.core.PConstants.CLOSE;

/**
 *
 * @author Thales
 */
public class Polygon {
    
    Vector2D[] vertexs;
    Vector2D axis;
    
    public Polygon(Vector2D[] v){
        vertexs = v;
        axis = new Vector2D();
        centralizeAxis();
    }
    public void setPos(Vector2D p){
        translate(p.sub(axis));
    }
    public void translate(Vector2D p){
        axis.add(p);
        for (Vector2D v: vertexs){
            v.add(p);
        }
    }
    public void translate(double x, double y){
        axis.add(x,y);
        for (Vector2D v: vertexs){
            v.add(x,y);
        }
    }
    public void rotate(double r){
        Vector2D tempPos = axis.copy();
        translate(axis.mult(-0.5));
        for (Vector2D v: vertexs){
            v.rotate(r);
        }
        translate(tempPos);
        axis = tempPos;
    }
    public void scale(double m){
        Vector2D tempPos = axis.copy();
        translate(axis.mult(-1));
        for (Vector2D v: vertexs){
            v.mult(m);
        }
        translate(tempPos);
    }
    public void setAxis(Vector2D p){
        axis = p;
    }
    public void display(){
        canvas.beginShape();
        for (Vector2D v : vertexs){
            canvas.vertex((float)v.x, (float)v.y);
        }
        canvas.endShape(CLOSE);
    }
    public void displayAxis(){
        canvas.ellipse((float)axis.x,(float)axis.y,0.1f,0.1f);
    }
    
    public void centralizeAxis(){
        axis.set(0,0);
        for (Vector2D v: vertexs){
            axis.add(v);
        }
        axis.div(vertexs.length);
    }
}
