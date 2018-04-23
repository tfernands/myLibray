/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geometry;

import java.util.ArrayList;
import mylibray.Vector2D;

/**
 *
 * @author Thales
 */
public class Path {
    
    public Vector2D[] points;
    public Edge[] edges;
    
    public Path(Vector2D... p){
        points = p;
        edges = new Edge[p.length-1];
        Vector2D temp = points[0];
        for (int i = 1 ; i < points.length; i++){
            edges[i-1] = new Edge(temp,points[i]);
            temp = points[i];
        }
    }
    public Path(double[]... p){
        points = new Vector2D[p.length];
        edges = new Edge[p.length-1];
        points[0] = new Vector2D(p[0]);
        Vector2D temp = points[0];
        for (int i = 1 ; i < points.length; i++){
            points[i] = new Vector2D(p[i]);
            edges[i-1] = new Edge(temp, points[i]);
            temp = points[i];
        }
    }
    
    public Vector2D dist(Vector2D p){
        Vector2D dist = edges[0].dist(p);
        for (int i = 1; i < edges.length; i++){
            Vector2D temp = edges[i].dist(p);
            if (Vector2D.distSq(p,dist) > Vector2D.distSq(p,temp)){
                dist = temp;
            }
        }
        return dist;
    }
    
    public Edge getClosestLine(Vector2D p){
        Edge closest = edges[0];
        Vector2D dist = edges[0].dist(p);
        for (int i = 1; i < edges.length; i++){
            Vector2D temp = edges[i].dist(p);
            if (Vector2D.distSq(p,dist) > Vector2D.distSq(p,temp)){
                dist = temp;
                closest = edges[i];
            }
        }
        return closest;
    }
    
    public Vector2D[] intersections(Edge p){
        ArrayList<Vector2D> tempI = new ArrayList<>();
        for (Edge e : edges) {
            Vector2D inter = e.intersection(p);
            if (inter != null){
                if (tempI.isEmpty()){
                    tempI.add(inter);
                }else
                if (!tempI.get(tempI.size()-1).equals(inter)){
                    tempI.add(inter);
                }
            }
        }
        Vector2D[] intersections = new Vector2D[tempI.size()];
        tempI.toArray(intersections);
        return intersections;
    }
    public Vector2D closerIntersectionToPoint(Edge p, int point){
        Vector2D intersection = null;
        Vector2D linePoint;
        if (point <= 1){
            linePoint = p.p1;
        }else{
            linePoint = p.p2;
        }
        double dist = Double.POSITIVE_INFINITY;
        for (Edge e : edges) {
            Vector2D inter = e.intersection(p);
            if (inter != null){
                double tempD = Vector2D.sub(inter, linePoint).magSq();
                if (dist > tempD){
                    dist = tempD;
                    intersection = inter;
                }
            }
        }
        return intersection;
    }
    
    public void display(){
        for (Edge e: edges){
            e.display();
        }
    }
    public void displayNormal(double size){
        for (Edge e: edges){
            e.displayNormal(size);
        }
    }
    
}
