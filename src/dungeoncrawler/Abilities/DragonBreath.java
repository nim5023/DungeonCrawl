/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities;

import dungeoncrawler.Abilities.Animations.MissileAnimation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;

/**
 *
 * @author Nick
 */
public class DragonBreath extends Ability{
    public double Fury;
     public DragonBreath(){
        super();
        name = "DragonBreath";
    }
    
     public void BurnHero(Monster monster, Hero hero, double numAlive,double Fury,double Strength){
         if(hero.isAlive){
              double ratio = Math.pow(monster.Magic,2) / (Math.pow(monster.Magic,2) + Math.pow(hero.Magic,2));
        int damage = (int)(ratio * monster.Power*Strength*(Fury+1.0)/numAlive);
        damage = monster.reducedDamage(damage,hero);
            DungeonCrawler.AnimationList.push(new MissileAnimation(monster.X,monster.Y,hero.X,hero.Y,ImageHandler.ImgFireBall,45));
       
          BattleHandler.addBattleText(" "+hero.name+" takes "+damage+" Damage"); 
         hero.TakeDamage( damage);
         }
     }
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget,double fury,double Strength){        
            
        
          BattleHandler.addBattleText(monster.name+" uses Incinerate ");
          int numALive = 0;
          if(DungeonCrawler.War.isAlive)
       numALive++;
          if(DungeonCrawler.Blm.isAlive)
       numALive++; 
          if(DungeonCrawler.Ftr.isAlive)
       numALive++;
          
          BurnHero(monster,DungeonCrawler.Ftr,numALive,fury,Strength);
          BurnHero(monster,DungeonCrawler.War,numALive,fury,Strength);
          BurnHero(monster,DungeonCrawler.Blm,numALive,fury,Strength);
          
          }
    
}
