/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.shapes;

import mylibray.Vector2D;

/**
 *
 * @author Thales
 */
public interface PhysicsShape {
    public double getMomentOfInertia(double mass);
    public void display(Vector2D pos, double angle);
    public Vector2D[] getVertexs();
    public Vector2D[] getVertexs(Vector2D pos, double angle);
}
