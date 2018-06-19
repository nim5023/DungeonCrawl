/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities.Animations;

import dungeoncrawler.ImageHandler;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Nick
 */
public class AttackAnimation extends Animation{

    Image AnimImage;
    int X,Y,maxTIMER;
    double Size;
    boolean Left;
    boolean open;
  public  AttackAnimation(int x,int y,boolean left,int timer){
        X=x;
        Y=y;
        AnimImage = ImageHandler.ImgAttack;
        Left = left;
        TIMER = timer;
        maxTIMER = TIMER;
        open = true;
    }
    @Override
    public void Animate(Graphics2D g) {
        
        
         AffineTransform trans = new AffineTransform(); 
         
         double ratio = (double)TIMER/(double)(maxTIMER/2);
        double Openratio = 2.0-ratio; 
        if(Openratio >1)
            Openratio =1;
        double Closedratio = 1.0-(ratio); 
          if(Closedratio <0)
            Closedratio =0;
          
       trans.translate(X - (AnimImage.getWidth(null)/2), Y - (AnimImage.getHeight(null)/2));
       g.transform(trans); 
        
        if(Left){
     trans.setToIdentity();
     g.setTransform( trans);        
     
       trans.translate(X - (AnimImage.getWidth(null)/2), Y - (AnimImage.getHeight(null)/2));
       trans.scale(-1, 1);       
       trans.translate(-(AnimImage.getWidth(null)), 0);
            
       double scaler = 0.75;
       double transScale = (1.0-scaler)/2.0;
       
       trans.translate(transScale*AnimImage.getWidth(null), transScale*AnimImage.getHeight(null));
       trans.scale(scaler,scaler);       
            g.transform(trans);
        }
        
        
     g.drawImage(AnimImage,
                    0, (int)(AnimImage.getHeight(null)*Closedratio), 
                    AnimImage.getWidth(null), (int)(AnimImage.getHeight(null)*Openratio), 
                    0, (int)(AnimImage.getHeight(null)*Closedratio),
                    AnimImage.getWidth(null), (int)(AnimImage.getHeight(null)*Openratio),
                    null);
        
     
     
     
    trans.setToIdentity();
     g.setTransform( trans);
    
         }

    @Override
    public void ContinueAnimation() {
       TIMER--;
    }
    
}
