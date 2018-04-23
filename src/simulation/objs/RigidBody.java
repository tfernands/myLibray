/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.objs;

import mylibray.Vector2D;
import simulation.shapes.BoundingBox;
import simulation.shapes.PhysicsShape;

/**
 *
 * @author Thales
 */
public class RigidBody implements Body{
    
    //VECTORS
    public Vector2D pos, linearVel, force;
    public double angle, angularVel, torque;
    
    //PHYSHAPE
    public PhysicsShape shape;
    public BoundingBox boundingBox;
    
    //PROPRETIES
    private double mass = 1;
    private double momentOfInertia = 1;
    
    public RigidBody(PhysicsShape s){
        pos = new Vector2D();
        linearVel = new Vector2D();
        force = new Vector2D();
        shape = s;
        boundingBox = new BoundingBox();
        boundingBox.calcBoundingBox(shape, pos, angle);
        momentOfInertia = shape.getMomentOfInertia(mass);
    }
    
    public void setMass(double mass){
        this.mass = mass;
        momentOfInertia = shape.getMomentOfInertia(mass);
    }
    
    @Override
    public void applyForce(Vector2D position, Vector2D f){
        force.add(f);
        torque += Vector2D.cross(position, f);
    }
    
    @Override
    public void applyAcceleration(Vector2D f) {
        force.add(f.mult(mass));
    }
    
    @Override
    public void updatePhysics(double step){
        //Calc linear force
        Vector2D linearAcc = force.div(mass);
        linearVel.add(linearAcc.mult(step));
        pos.add(linearVel.copy().mult(step));
        force.mult(0);
        //Calc torque
        double angularAcc = torque / momentOfInertia;
        angularVel += angularAcc*step;
        angle += angularVel*step;
        torque = 0;
        //update boundingBox
        boundingBox.calcBoundingBox(shape, pos, angle);
    }
    
    public Vector2D[] getVertexs(){
        return shape.getVertexs(pos, angle);
    }
    
    @Override
    public BoundingBox getBoundingBox(){
        return boundingBox;
    }
    
    public void display(){
        shape.display(pos, angle);
    }

    
}
