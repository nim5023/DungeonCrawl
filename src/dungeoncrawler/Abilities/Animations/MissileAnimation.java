/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities.Animations;

import dungeoncrawler.ImageHandler;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author Nick
 */

        
public class MissileAnimation extends Animation{
 
  protected  int sX,sY,dX,dY,maxTIMER;
   protected Image AnimImage;
    public  MissileAnimation(int startX,int startY, int destX,int destY,Image img,int time){
        sX=startX;
        sY=startY;
        dX = destX;
        dY = destY;
        AnimImage = ImageHandler.ImgWraithBomb;
        TIMER = time;
        maxTIMER = TIMER;
        AnimImage = img;
    }
        @Override
        public void Animate(Graphics2D g) {
            double TimeRatio = 1.0-((double)TIMER/(double)maxTIMER); 
                    int X = sX +(int)((dX-sX)*TimeRatio);
                    int Y = sY +(int)((dY-sY)*TimeRatio);          
              g.drawImage(AnimImage, X-AnimImage.getWidth(null)/2, Y- AnimImage.getHeight(null)/2, null);            
        }
        
        @Override
        public void ContinueAnimation(){
             TIMER--;
        }
    
}
