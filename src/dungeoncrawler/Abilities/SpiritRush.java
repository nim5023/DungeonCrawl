/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities;

import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.Abilities.Animations.Animation;
import dungeoncrawler.Abilities.Animations.MissileAnimation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.StateHandlers.BattleHandler;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author Nick
 */
public class SpiritRush extends Ability{
    
     public SpiritRush(){
        super();
        name = "Spirit Rush";
    }
    
    @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){        
            
        double ratio = Math.pow(monster.Magic,2) / (Math.pow(monster.Magic,2) + Math.pow(hero.Magic,2));
        int damage = (int)(ratio * monster.Power);
       damage = monster.reducedDamage(damage,hero);
       
          BattleHandler.addBattleText(monster.name+" uses Spirit Rush on "+ hero.name);
          BattleHandler.addBattleText(" "+hero.name+" takes "+damage+" Damage");
             DungeonCrawler.AnimationList.push(new SpiritRushAnim(monster.X,monster.Y,hero.X,hero.Y,monster.Stand));
             hero.TakeDamage( damage);
          
          }
    
    
   public class SpiritRushAnim extends MissileAnimation{       
       
       SpiritRushAnim(int startX,int startY,int destX,int destY,Image img){ 
           super(startX,startY,destX,destY,img,60);
       }

        @Override
        public void Animate(Graphics2D g) {         
                    float opacity = 0.6f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.Animate(g);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            
        }
       
   }
    
}
