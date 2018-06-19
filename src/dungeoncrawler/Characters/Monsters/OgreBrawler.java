/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;
import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.Abilities.SpiritRush;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.DungeonCrawler;

import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Nick
 */
public class OgreBrawler extends Monster {
    public boolean isCharged;
    public Hero ChargeTarget;
      public OgreBrawler(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.Ogre;
         name = "Ogre Brawler";
         isCharged = false;
         ChargeTarget = null;
         
            abilityList.get(0).abilityChance = 70;
            abilityList.add(new Fury());
            abilityList.get(1).abilityChance = 30;
    }
      
      
public class Fury extends Ability{
    
    public Fury(){
        super();
        name = "Fury";      
    }
    
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     if(!isCharged){          
       isCharged = true;
     ChargeTarget = DungeonCrawler.Ftr;
     if(DungeonCrawler.War.HP > DungeonCrawler.Ftr.HP)
     ChargeTarget = DungeonCrawler.War;
     if(DungeonCrawler.Blm.HP > DungeonCrawler.War.HP && DungeonCrawler.Blm.HP > DungeonCrawler.Ftr.HP)         
     ChargeTarget = DungeonCrawler.Blm;
         
       BattleHandler.addBattleText(monster.name+" Builds Fury on " + ChargeTarget.name);
         }
        
    }
    
}


    @Override
       public void UseAbility(MonsterGroup Allies){
          if(!taunted && !confused &&!stunned)
    if(isCharged){
          GetRandomHero();
          isCharged = false;
          if(ChargeTarget.isAlive)
              TargetedHero = ChargeTarget;
          
          double ratio = Math.pow(Attack,2) / (Math.pow(Attack,2) + Math.pow(TargetedHero.Defense,2));
        int damage = (int)(ratio * Power)*4;        
     
            damage = reducedDamage(damage,TargetedHero);
            
         DungeonCrawler.AnimationList.push(new AttackAnimation(TargetedHero.X,TargetedHero.Y,false,40));          
          BattleHandler.addBattleText(name+" Unleashes Fury on "+ TargetedHero.name);
          BattleHandler.addBattleText(" "+TargetedHero.name+" takes "+damage+" Damage"); 
         
         TargetedHero.TakeDamage( damage);
          removeWeakness();
          return;
      }
       super.UseAbility(Allies);            
          
      
    }
    
    @Override
    public void Draw(Graphics2D g){
        if(isCharged){
        g.setColor(Color.red);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
           g.fillOval(X-Stand.getWidth(null)/2-15, Y-Stand.getHeight(null)/2-10, Stand.getWidth(null)+30, Stand.getHeight(null)+20);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
       
        }
        super.Draw(g);
    }
    @Override
      public void LevelUp(){
          super.LevelUp();
            MaxHP+=DungeonCrawler.RND.nextInt(5)+21; //23;
            HP = MaxHP;
            Magic+=1;
            Attack+=1;
            Defense+=1;
            Power+=8;
      }
    
}
