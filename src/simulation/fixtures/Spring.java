/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.fixtures;

import simulation.fixtures.Fixture;
import simulation.objs.Point;
import mylibray.PCanvas;
import mylibray.Vector2D;

/**
 *
 * @author Thales
 */
public class Spring implements Fixture{
    
    public Point a;
    public Point b;
    public double length;
    public double k;
    public double damping;
    
    public Spring(Point a, Point b, double length, double k, double damping){
        this.a = a; this.b = b; this.length = length; this.k = k; this.damping = damping;
    }

    @Override
    public void update(double step) {
        // get the mtd
        Vector2D delta = Vector2D.sub(a.pos,b.pos);
        double d = delta.mag();
        
        delta.normalize();
        delta.mult(length-d); 
        delta.mult(k);
        // push-pull them apart
        //Vector2D f = delta.copy().mult
        Vector2D v = Vector2D.sub(a.vel, b.vel);
        Vector2D fDamping = delta.copy().normalize();
        double vn = Vector2D.dot(v,fDamping);
        fDamping.mult(vn*damping);
        delta.sub(fDamping);
        a.applyForce(delta);
        b.applyForce(delta.mult(-1));
    }
    
    public void display(){
        PCanvas.canvas.line((float)a.pos.x, (float)a.pos.y, (float)b.pos.x, (float)b.pos.y);
    }

}
