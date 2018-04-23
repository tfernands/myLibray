/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation.objs;
import mylibray.Vector2D;
import simulation.shapes.BoundingBox;

/**
 *
 * @author Thales
 */
public interface Body {
    public void updatePhysics(double step);
    public void applyForce(Vector2D position, Vector2D f);
    public void applyAcceleration(Vector2D f);
    public BoundingBox getBoundingBox();
}
