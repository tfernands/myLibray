/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationWORKINPROGRESS;

import simulation.fixtures.Fixture;
import simulation.objs.Body;
import java.util.ArrayList;
import mylibray.PCanvas;
import mylibray.Vector2D;

public class Engine {
    
    public double time = 0; //seconds
    public static double C_AIR_DRAG = 1;
    public static double C_FRICTION = 1;
    
    private double timeStep = 1/60d;

    public ArrayList<Body> bodies = new ArrayList<>();
    public ArrayList<Fixture> fixtures = new ArrayList<>();
    
    public void simulationSpeed(double speed){
        timeStep = speed/PCanvas.applet.frameRate;
    }
    
    public void update(){
        time += timeStep;
        for (int i = 0; i < bodies.size(); i++){
            bodies.get(i).updatePhysics(timeStep);
        }
        for (int i = 0; i < fixtures.size(); i++){
            fixtures.get(i).update(timeStep);
        }
    }
    
    public void add(Body o){
        bodies.add(o);
    }
    public void add(Fixture a){
        fixtures.add(a);
    }
    
    public void gravity(Vector2D f){
        bodies.forEach((Body o) -> {
            o.applyAcceleration(f);
        });
    }
    public void gravity(double x, double y){
        Vector2D g = new Vector2D(x,y);
        bodies.forEach((Body o) -> {
            o.applyAcceleration(g);
        });
    }
    
    
//    public void colisionHandler(Point a, Point b){
//        // get the mtd
//        Vector2D delta = Vector2D.sub(a.pos,b.pos);
//        double d = delta.mag();
//        
//        if (d > (a.displaySize/2 + b.displaySize/2)) return;
//        
//        // minimum translation distance to push balls apart after intersecting
//        delta.normalize();
//        delta.mult((a.displaySize/2 + b.displaySize/2)-d); 
//
//        // resolve intersection --
//        // inverse mass quantities
//        double im1 = 1 / a.mass; 
//        double im2 = 1 / b.mass;
//
//        // push-pull them apart based off their mass
//        a.pos.add(delta.copy().mult(im1 / (im1 + im2)));
//        b.pos.sub(delta.copy().mult(im2 / (im1 + im2)));
//        
//        // impact speed
//        Vector2D v = Vector2D.sub(a.vel, b.vel);
//        double vn = v.dot(delta.copy().normalize());
//
//        // sphere intersecting but moving away from each other already
//        if (vn > 0.0) return;
//        
//        // collision impulse
//        double i = -(1.0 + a.restitution*b.restitution) * vn;
//        Vector2D impulse = delta.setMag(i);
//        System.out.println(i);
//        
//        // change in momentum
//        impulse.div(timeStep);
//        a.applyForce(impulse);
//        b.applyForce(impulse);
//    }

    public String toString(){
        return "Current time: "+time+"s | time step: "+timeStep+"s";
    }
}
