/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylibray;

import functions.F;
import processing.core.PGraphics;
import static mylibray.PCanvas.applet;
import static mylibray.PCanvas.canvas;
/**
 *
 * @author Thales
 */
public class Slider extends SBtn{
  
    private double value  = 0;
    private double value1 = 0;
    private double value2 = 1;

    private double   cursorSize;

    private final SBtn  btnSlider;
    private double mousePressPoint;
  
    public Slider(PCamera camera){
        super(camera);
        btnSlider = new SBtn(camera);
        
    }
    //=======================USER METHODS==========================
  
    @Override
    public void set(double x, double y, double sizeX, double sizeY){
        isAEllipse = false;
        pos.set(x,y); size.set(sizeX, sizeY);
        cursorSize = size.x/5;
        update();
    }
  
    @Override
    public void update(){
        updateBtn();
        btnSlider.update();
        sliderFunction();
    }
  
    public void setValue(double value){
        this.value = valueNormalize(value);
    }
    public void values(double value1, double value2){
        this.value1 = value1; this.value2 = value2;
    }
    public double getValue(){
        return value;
    }
    public void cursorSize(double size){
        cursorSize = size;
    }
  
    @Override
    public void display(){
        displayCurse();
        btnSlider.display();
        
    }
    
    public void displayValues(int c){
        int color = applet.color(c);
        float spacing = 5;
        canvas.fill(c);
        canvas.textSize(12);
        
        canvas.pushMatrix();
        canvas.scale(1/(float)camera.getZoom());
        canvas.translate((float)(pos.x*camera.getZoom())-spacing,(float)((pos.y+size.y/2)*camera.getZoom()));
        canvas.textAlign(PGraphics.RIGHT,PGraphics.CENTER);
        canvas.text(String.format("%1.1f",value1),0,0);
        canvas.popMatrix();
        
        canvas.pushMatrix();
        canvas.scale(1/(float)camera.getZoom());
        canvas.translate((float)((pos.x+size.x)*camera.getZoom())+spacing,(float)((pos.y+size.y/2)*camera.getZoom()));
        canvas.textAlign(PGraphics.LEFT,PGraphics.CENTER);
        canvas.text(String.format("%1.1f",value2),0,0);
        canvas.popMatrix();
        
        canvas.pushMatrix();
        canvas.scale(1/(float)camera.getZoom());
        canvas.translate((float)((btnSlider.pos.x+btnSlider.size.x/2)*camera.getZoom()),(float)((pos.y+size.y/2)*camera.getZoom()));
        canvas.textAlign(PGraphics.CENTER,PGraphics.CENTER);
        canvas.text(String.format("%1.2f",getValue()),0,0);
        canvas.popMatrix();
    }
  //======================/USER METHODS==========================

  private void sliderFunction(){
        double btnSliderPos = btnSlider.pos.x;
        double resultSize = pos.x+size.x;
        
        if (btnSlider.onPress){
            mousePressPoint = camera.mouseX()-btnSliderPos;
        }
        if (btnSlider.pressed){
            btnSliderPos = camera.mouseX()-mousePressPoint;
        }else if (released && !btnSlider.released){
            value = valueNormalize(F.map(camera.mouseX(),pos.x+cursorSize/2,resultSize-cursorSize/2,value1,value2));
        }else{
            btnSliderPos = F.map(value,value1,value2,pos.x,resultSize-cursorSize);
        }
        if (btnSliderPos < pos.x){
            btnSliderPos = pos.x;
        }else
        if (btnSliderPos > resultSize-cursorSize){
            btnSliderPos = resultSize-cursorSize;
        }
        value = F.map(btnSliderPos,pos.x,resultSize-cursorSize,value1,value2);
        btnSlider.set(btnSliderPos,pos.y,cursorSize,size.y,edge);
        
  }
  
  private double valueNormalize(double value){
    if (value1 < value2){
      if (value < value1){
        value = value1; return value;
      }else if (value > value2){
        value = value2; return value;
      }else{
        return value;
      }
    }else{
      if (value < value2){
        value = value2; return value;
      }else if (value > value1){
        value = value1; return value;
      }else{
        return value;
      }
    }
  }
  
  private void displayCurse(){
    canvas.fill(0,200);
    canvas.rect((float)pos.x, (float)pos.y, (float)size.x, (float)size.y, (float)edge);
  }

}
