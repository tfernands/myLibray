package mylibray;

import functions.F;
import static mylibray.PCanvas.applet;
import static mylibray.PCanvas.canvas;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CORNER;

public class PCamera{
    
    private double translationX, translationY, zoom;
    private double anchorX, anchorY;
    private boolean flipY = false;
    
    private int gridSize;
    private int gridStep;
    private double gridMeters;
    
    public PCamera(){
        zoom = 1;
        anchorX = applet.width/2;
        anchorY = applet.height/2;
        gridSize = 70;
    }
    public void flipY(){
        flipY = !flipY;
    }
    
    public void pos(double x, double y){
        translationX = applet.width/2-anchorX-x;
        translationY = applet.height/2-anchorY-y;
    }
    public void setZoom(double z){
        if (z <= 0){
            zoom = 0.000001;
        }else{
            zoom = z;
        }
    }
    
    public void mouseControl(){
        if (applet.mousePressed && applet.mouseButton == CENTER){
            pan(applet.mouseX-applet.pmouseX,applet.mouseY-applet.pmouseY);
        } 
    }
    
    public void anchor(double x, double y){
        translationX -= (x-anchorX)/zoom;
        if (flipY){
            translationY += (y-anchorY)/zoom;
        }else{
            translationY -= (y-anchorY)/zoom;
        }
        anchorX = x;
        anchorY = y;
    }
    public void pan(double x, double y){
        translationX += x/zoom;
        if (flipY){
            translationY -= y/zoom;
        }else{
            translationY += y/zoom;
        }
    }
    public void zoom(double z){
        if (z < 0){
            setZoom(zoom/1.1);
        }else{
            setZoom(zoom*1.1);
        }
    }
    
    public void apply(){
        canvas.translate((float)anchorX, (float)anchorY);
        if (flipY){
            canvas.scale((float)zoom, (float)-zoom);
        }else{
            canvas.scale((float)zoom);
        }
        canvas.translate((float)(translationX), (float)(translationY));
        //update gridstep
        gridStep = (int)Math.round(Math.log10(gridSize)-Math.log10(zoom));
        gridMeters = Math.pow(10, gridStep);
    }
    public void clear(){
        canvas.translate((float)-translationX, (float)-translationY);
        if (flipY){
            canvas.scale((float)(1/zoom), (float)-(1/zoom));
        }else{
            canvas.scale((float)(1/zoom));
        }
        canvas.translate((float)-anchorX, (float)-anchorY);
    }
    
    public Vector2D getCameraPos(){
        return new Vector2D(getCameraX(), getCameraY());
    }
    public double getCameraX(){
        return screenXToWorldX(applet.width/2);
    }
    public double getCameraY(){
        return screenYToWorldY(applet.height/2);
    }
    public double getZoom(){
        return zoom;
    }
    
    public double mouseX(){
        return screenXToWorldX(applet.mouseX);
    }
    public double mouseY(){
        return screenYToWorldY(applet.mouseY);
    }
    
    public void setGridRelative(int size){
        gridSize = size;
    }
    public void displayGrid(){
        double start;
        double end;
        canvas.strokeWeight((float)(1/zoom));
        canvas.stroke(F.colorSetAlpha(canvas.strokeColor,100));
        //horisontalLines
        if (flipY){
            start = (int)(screenYToWorldY(applet.height)/gridMeters)*gridMeters;
            end = screenYToWorldY(0);
        }else{
            start = (int)(screenYToWorldY(0)/gridMeters)*gridMeters;
            end = screenYToWorldY(applet.height);
        }
        for (double i = start; i <= end; i += gridMeters){
            canvas.line((float)screenXToWorldX(0), (float)i, (float)screenXToWorldX(applet.width), (float)i);
        }
        
        //Vertical Lines
        start = (int)(screenXToWorldX(0)/gridMeters)*gridMeters;
        end = screenXToWorldX(applet.width);
        for (double i = start; i <= end; i += gridMeters){
            canvas.line((float)i, (float)screenYToWorldY(0), (float)i, (float)screenYToWorldY(applet.height));
        }
        canvas.stroke(canvas.strokeColor);
        canvas.line(0, (float)screenYToWorldY(0), 0, (float)screenYToWorldY(applet.height));
        canvas.line((float)screenXToWorldX(0), 0, (float)screenXToWorldX(applet.width), 0);
    }
    public void displayScale(){
        int gridPixels = (int)(gridMeters*zoom);
        String status = "x: "+String.format("%.2f",mouseX())+", y: "+String.format("%.2f",mouseY())+", zoom: "+String.format("%.2f",zoom);
        
        canvas.textSize(12);
        
        int spacing = 10;
        int sizeY = 40+spacing*2;
        int sizeX = (int)(applet.textWidth(status)+spacing*2);
        
        if (gridPixels+spacing*2 > sizeX){
            sizeX = gridPixels+spacing*2;
        }
        
        int posX = applet.width-sizeX-spacing*2;
        int posY = applet.height-sizeY-spacing*2;
        int scaleBarPosX = posX+(sizeX-gridPixels)/2;
        
        canvas.rectMode(CORNER);
        canvas.noStroke();
        canvas.fill(255,180);
        canvas.rect(posX, posY, sizeX, sizeY);
        canvas.fill(0);
        canvas.textAlign(CENTER, CENTER);
        canvas.text(getUnit(gridStep),scaleBarPosX+gridPixels/2,posY+sizeY*1/4);
        
        canvas.rect(scaleBarPosX, posY+sizeY*1.7f/4, gridPixels, 5);
        canvas.text(status,scaleBarPosX+gridPixels/2,posY+sizeY*3/4);
    }
    
    public double worldXToScreenX(double worldX){
        return zoom*(worldX+translationX)+anchorX;
    }
    public double worldYToScreenY(double worldY){
        if (flipY){
            return -zoom*(worldY+translationY)+anchorY;
        }
        return zoom*(worldY+translationY)+anchorY;
    }
    public double screenXToWorldX(double screenX){
        return (screenX-anchorX)/zoom-translationX;
    }
    public double screenYToWorldY(double screenY){
        if (flipY){
            return -(screenY-anchorY)/zoom-translationY;
        }
        return (screenY-anchorY)/zoom-translationY;
    }
    
    @Override
    public String toString(){
        return "CameraX: "+String.format("%.2f",getCameraX())+", CameraY: "+String.format("%.2f",getCameraY())+", Zoom: "+String.format("%.2f",getZoom());
    }
    
    private static String getUnit(int base){
        String unit = "m";
        switch(base){
            case -9:
                unit = "nm";
                break;
            case -8:
                unit = "10nm";
                break;
            case -7:
                unit = "100nm";
                break;
            case -6:
                unit = "µ";
                break;
            case -5:
                unit = "10µ";
                break;
            case -4:
                unit = "100µ";
                break;
            case -3:
                unit = "mm";
                break;
            case -2:
                unit = "cm";
                break;
            case -1:
                unit = "10cm";
                break;
            case 0:
                unit = "m";
                break;
            case 1:
                unit = "10m";
                break;
            case 2:
                unit = "100m";
                break;
            case 3:
                unit = "km";
                break;
        }
        if (base > 3){
            unit = (int)Math.pow(10, base-3)+"km";
        }
        return unit;
    }
}
