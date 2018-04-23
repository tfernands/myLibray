/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.processing;

import simulation.colisions.GJK;
import geometry.Polygon;
import geometry.QuickHull;
import java.util.ArrayList;
import java.util.Arrays;
import mylibray.PCamera;
import mylibray.PCanvas;
import static mylibray.PCanvas.canvas;
import mylibray.Vector2D;
import processing.core.PApplet;
import processing.event.MouseEvent;
import simulationWORKINPROGRESS.Engine;
import simulation.colisions.Colision;
import simulation.objs.RigidBody;
import simulation.shapes.BoundingBox;
import simulation.shapes.BoxShape;

/**
 *
 * @author Thales
 */
public class physicsTeste extends PApplet{
    
    PCamera camera;
    Engine engine = new Engine();
    RigidBody sq;
    RigidBody sq2;

    @Override
    public void settings(){
        size(800,500);
    }
    
    @Override
    public void setup(){
        PCanvas.set(this);
        surface.setResizable(true);
        camera = new PCamera();
        camera.setZoom(40);
        camera.pos(0, 5);
        camera.flipY();
        engine = new Engine();
        
        sq = new RigidBody(new BoxShape(2,1));
        sq2 = new RigidBody(new BoxShape(1,1.5));
        
        engine.add(sq);
        engine.add(sq2);
    }
    
    @Override
    public void draw(){
        
        
        
        background(255);
        if (mousePressed && mouseButton == CENTER){
            camera.pan(mouseX-pmouseX,mouseY-pmouseY);
        } 
        pushMatrix();
            camera.apply();
            camera.displayGrid(70);

            if (mousePressed && mouseButton == LEFT){
                sq.applyForce(new Vector2D(1,0), new Vector2D(0,.1));
                sq.applyForce(new Vector2D(-1,0), new Vector2D(0,-.1));
            }

            engine.update();
            
            

            sq.pos.set(camera.mouseX(),camera.mouseY());
            strokeWeight((float)(1/camera.getZoom()));
            
            fill(200);
            rect(9,9,1,1);
            
            stroke(0);
            strokeWeight((float)(2/camera.getZoom()));
            sq.display();
            sq2.display();
            
            noFill();
            stroke(255,0,0);
            fill(255);
            
        popMatrix();
        camera.displayScale();
        //System.out.println(engine);
    }
    
    @Override
    public void mouseWheel(MouseEvent e){
        camera.anchor(mouseX, mouseY);
        camera.zoom(-e.getCount());
    }
    
    void drawVectorArray(Vector2D... a){
        beginShape();
        for (Vector2D point : a){
            vertex((float)point.x,(float)point.y);
        }
        endShape(CLOSE);
    }
    
}
