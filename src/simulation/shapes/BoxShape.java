/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.shapes;

import mylibray.Vector2D;
import static mylibray.PCanvas.canvas;
import static processing.core.PConstants.CORNERS;

/**
 *
 * @author Thales
 */
public class BoxShape implements PhysicsShape{
    
    Vector2D[] vertexs;
    
    public BoxShape(double sizeX, double sizeY){
        vertexs = new Vector2D[4];
        vertexs[0] = new Vector2D(-sizeX/2,sizeY/2);
        vertexs[1] = new Vector2D(sizeX/2,sizeY/2);
        vertexs[2] = new Vector2D(sizeX/2,-sizeY/2);
        vertexs[3] = new Vector2D(-sizeX/2,-sizeY/2);
    }

    @Override
    public double getMomentOfInertia(double mass){
        double sizeX = vertexs[1].x*2;
        double sizeY = vertexs[1].y*2;
        return mass*(sizeX*sizeX+sizeY*sizeY)/12;
    }
    
    @Override
    public Vector2D[] getVertexs(){
        Vector2D[] v = new Vector2D[vertexs.length];
        for (int i = 0; i < v.length; i++){
            v[i] = vertexs[i].copy();
        }
        return v;
    }
    
    @Override
    public Vector2D[] getVertexs(Vector2D pos, double angle){
        Vector2D[] v = new Vector2D[vertexs.length];
        for (int i = 0; i < v.length; i++){
            v[i] = vertexs[i].copy().rotate(angle).add(pos);
        }
        return v;
    }
    
    @Override
    public void display(Vector2D pos, double angle){
        canvas.rectMode(CORNERS);
        canvas.pushMatrix();
        canvas.translate((float)pos.x, (float)pos.y);
        canvas.rotate((float)angle);
        canvas.rect((float)(vertexs[1].x), (float)(vertexs[1].y), (float)vertexs[3].x, (float)vertexs[3].y);
        canvas.popMatrix();
    }
}
