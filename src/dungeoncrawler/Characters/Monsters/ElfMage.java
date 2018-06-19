/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;
import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Abilities.Animations.DeathAnimation;
import dungeoncrawler.Abilities.Animations.MissileAnimation;
import dungeoncrawler.Abilities.SpiritRush;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.DungeonCrawler;

import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author Nick
 */
public class ElfMage extends Monster {
    boolean isCharged = false;
    int PentagramX = X - 200;   
    Image Pentagram = ImageHandler.ImgPentagram;
      public ElfMage(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.ElfMage;
         name = "Warlock";
         abilityList.get(0).abilityChance = 0;
            abilityList.add(new SpiritRush());
            abilityList.get(1).abilityChance = 60;  
            abilityList.add(new Summon());
            abilityList.get(2).abilityChance = 20;
            
         
    }
      
    @Override
      public void LevelUp(){
          super.LevelUp();
            MaxHP+=DungeonCrawler.RND.nextInt(5)+23;//25;
            HP = MaxHP;
            Magic+=3;
            Defense+=1;
            Attack-=1;
            Power+=3.5;
      }
    
    public void DemonAttackHero(Hero hero){
        if(hero.isAlive){
         double ratio = Math.pow(Magic,2) / (Math.pow(Magic,2) + Math.pow(hero.Defense,2));
         int damage = (int)(ratio * Power)*2; 
             damage = reducedDamage(damage,hero);
             
             BattleHandler.addBattleText(" "+hero.name+" takes "+damage+" Damage"); 
             hero.TakeDamage( damage);
        }
    }
    
    @Override
       public void UseAbility(MonsterGroup Allies){
          if(!taunted && !confused &&!stunned)
    if(isCharged){           
          isCharged = false;      
          BattleHandler.addBattleText(name+" Summons a Demon ");
            DemonAttackHero(DungeonCrawler.Ftr);
            DemonAttackHero(DungeonCrawler.War);
            DemonAttackHero(DungeonCrawler.Blm);
         DungeonCrawler.AnimationList.push(new MissileAnimation(PentagramX+50,Y,50,Y,ImageHandler.ImgDemon,99)); 
         DungeonCrawler.AnimationList.push(new DeathAnimation(BattleHandler.Enemies));   
          
          removeWeakness();
          return;
      }
       super.UseAbility(Allies);            
          
      
    }
    
     @Override
    public void Draw(Graphics2D g){
        if(isCharged){
        g.setColor(Color.BLACK);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
           g.fillOval(X-Stand.getWidth(null)/2-15, Y-Stand.getHeight(null)/2-10, Stand.getWidth(null)+30, Stand.getHeight(null)+20);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
           g.drawImage(Pentagram,PentagramX-Pentagram.getWidth(null)/2,Y, null);
       
        }
        super.Draw(g);
    }
         
public class Summon extends Ability{
    
    public Summon(){
        super();
        name = "Summon";      
    }    
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     if(!isCharged){          
       isCharged = true;    
         
       BattleHandler.addBattleText(monster.name+" Begins Summoning");
         }
        
    }
    
}
    
}
