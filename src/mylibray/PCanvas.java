/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylibray;

import processing.core.PApplet;
import processing.core.PGraphics;
/**
 *
 * @author Thales
 */
public abstract class PCanvas {
    
    public static PApplet applet;
    public static PGraphics canvas;
    
    public static void set(PApplet p){
        applet = p;
        canvas = p.g;
        canvas.strokeWeight(0.1f);
    }
    
    
}
