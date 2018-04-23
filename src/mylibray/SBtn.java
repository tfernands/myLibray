/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylibray;

import functions.F;
import static processing.core.PConstants.LEFT;
import static mylibray.PCanvas.applet;
import static mylibray.PCanvas.canvas;

/**
 *
 * @author Thales
 */
public class SBtn{
    
    public PCamera camera;
    public Vector2D pos;
    public Vector2D size;
    public boolean pressed,onPress,released;
    public int id;
    
    private boolean mouseBusy;
    
    protected boolean isAEllipse = true;
    protected double  edge = 0;
    
    public SBtn(PCamera camera){
        this.camera = camera;
        pos = new Vector2D();
        size = new Vector2D();
        pressed = false;
        id = -1;
    }
    public SBtn(PCamera camera, int id){
        this.camera = camera;
        pos = new Vector2D();
        size = new Vector2D();
        pressed = false;
        this.id = id;
    }
    
    public void set(double x, double y, double size){
        isAEllipse = true;
        pos.set(x,y); this.size.set(size,size);
    }
    public void set(double x, double y, double sizeX, double sizeY){
        isAEllipse = false;
        pos.set(x,y); size.set(sizeX,sizeY);
    }
    public void set(double x, double y, double sizeX, double sizeY, double edge){
        set(x,y,sizeX,sizeY);
        this.edge = edge;
    }
    
    public void update(){
        updateBtn();
    }
    
    public void updateBtn(){
        boolean mouseOver = mouseOver();
        boolean mousePressed = applet.mousePressed && applet.mouseButton == LEFT;
        
        if (!mouseOver && mousePressed && !pressed){
            mouseBusy = true;
        }else
        if (!mousePressed){
            mouseBusy = false;
        }

        if (mousePressed && mouseOver && !mouseBusy){
            released = false;
            onPress = !pressed;
            pressed = true;
        }else
        if (!(mousePressed && pressed)){
            released = pressed;
            pressed = false;
            onPress = false;

        }
    }
    
    public boolean mouseOver(){
        double mx = camera.mouseX();
        double my = camera.mouseY();
        double half_size = size.x/2;
        if (isAEllipse){
            return F.dist(mx,my,pos.x+half_size,pos.y+half_size) <= half_size;
        }else{
            return (mx >= pos.x && mx <= pos.x+size.x && my >= pos.y && my <= pos.y+size.y);
        }
    }
    
    public void feedBack(){
        int fill = canvas.fillColor;
        if (pressed){
            canvas.fill(0,100);
        }else
        if (mouseOver()){
            canvas.fill(0,30);
        }
        display();
        canvas.fill(fill);
    }

    public void display(){
        if (isAEllipse){
            double half_size = size.x/2;
            canvas.ellipse((float)(pos.x+half_size),(float)(pos.y+half_size),(float)size.x,(float)size.x);
        }else
        if (edge == 0){
            canvas.rect((float)pos.x,(float)pos.y,(float)size.x,(float)size.y);
        }else{
            canvas.rect((float)pos.x,(float)pos.y,(float)size.x,(float)size.y, (float)edge);
        }
    }
    
}
