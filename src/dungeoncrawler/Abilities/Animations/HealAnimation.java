/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities.Animations;

import java.awt.Graphics2D;
import dungeoncrawler.DungeonCrawler;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author Nick
 */
public class HealAnimation extends Animation{
    int X,Y;
    Image Target;
    int numBalls;
    HealBall[] healBalls;
   public HealAnimation(int x,int y,int NumBalls,int time, Image img){
        X = x; Y= y;
        Target = img;
        TIMER = time;
        numBalls = NumBalls;
   
        healBalls = new HealBall[numBalls];
        
        for(int i =0; i < numBalls;i++){
            healBalls[i] = new HealBall();
            healBalls[i].InitHealBall();
        }
        
    }
    class HealBall{
        float opacity;
        int ballX,ballY;
        int lifeTime = 20+DungeonCrawler.RND.nextInt(10);;
        public void InitHealBall(){
            opacity = DungeonCrawler.RND.nextFloat();
            ballX = X+DungeonCrawler.RND.nextInt(Target.getWidth(null)) - Target.getWidth(null)/2;
            ballY = Y+DungeonCrawler.RND.nextInt(Target.getHeight(null)) - Target.getWidth(null)/2;
            
        }
        public void MoveBall(){
            lifeTime--;
            if(lifeTime == 0){
                lifeTime = 20+DungeonCrawler.RND.nextInt(10);
                InitHealBall();
            }
            ballY--;                
        }
        public void drawBall(Graphics2D g){           
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g.setColor(Color.GREEN);
              g.fillOval(ballX-3, ballY-5, 6, 10);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }

    @Override
    public void Animate(Graphics2D g) {
           for(int i =0; i < numBalls;i++)
               healBalls[i].drawBall(g);
    }

    @Override
    public void ContinueAnimation() {
        TIMER--;
            for(int i =0; i < numBalls;i++)
                healBalls[i].MoveBall();
        
    }  
}
