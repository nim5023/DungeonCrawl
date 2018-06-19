/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;
import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.Animations.Animation;
import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Abilities.Animations.HealAnimation;
import dungeoncrawler.Abilities.Animations.MissileAnimation;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.Abilities.SpiritRush;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.DungeonCrawler;

import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import java.awt.*;

/**
 *
 * @author Nick
 */
public class Wraith extends Monster {
    public boolean shield;
       public boolean mirror;
    public int LastTurnHp;
    
      
    
    
    public class Bomb extends Ability{
    
    public Bomb(){
        super();
        name = "Spectral Charge";      
    }
    
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
            
            if(shield)
                abilityList.get(0).ActivateAbility(hero, monster, heroTarget);
     if(!shield){
        shield = true;
         LastTurnHp = HP;      
       BattleHandler.addBattleText(monster.name+" Stores "+BattleHandler.MonsterLevel*2+" Health Charge ");
     }
        
    }
    
}
       public class Mirror extends Ability{
    
    public Mirror(){
        super();
        name = "Mirror";      
    }
    
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
   
        mirror = true;    
       BattleHandler.addBattleText(monster.name+" Reflects Damage ");
     
        
    }
    
}
    
    
    public Wraith(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.Wraith;
         name = "Wraith";
         shield = false;
         mirror =false;
         LastTurnHp = HP;
            abilityList.get(0).abilityChance = 40;
            abilityList.add(new SpiritRush());
            abilityList.get(1).abilityChance = 40;
            abilityList.add(new Bomb());
            abilityList.get(2).abilityChance = 10;
            abilityList.add(new Mirror());
            abilityList.get(3).abilityChance = 10;
            
         
    }
       
    @Override
      public void LevelUp(){
          super.LevelUp();
            MaxHP+=13;
            HP = MaxHP;
            Magic+=1;
            Attack+=1;
            Defense+=1;
            Power+=6.5;
      }
      
      
    @Override
      public void TakeDamage(int Damage, Hero hero){
        super.TakeDamage(Damage,hero);
        if(shield){     
             if(HP <= LastTurnHp - (BattleHandler.MonsterLevel*2))
                 shield = false;
                 }
        
        if(mirror){
               if(hero.HP < Damage)
            Damage = hero.HP-1;
              
          BattleHandler.addBattleText(" "+name+" Mirrors "+ hero.name+ " for "+ Damage + " Damage");
           hero.TakeDamage( Damage);
        }
      }
      public void LifeLink(MonsterGroup Allies){
            if(Allies.monsterList.size() >1)
        if(!(Allies.monsterList.get(0).HP == Allies.monsterList.get(1).HP)){
            
          if(Allies.monsterList.get(1).HP >  Allies.monsterList.get(0).HP){  
        DungeonCrawler.AnimationList.push(new HealAnimation( Allies.monsterList.get(0).X, Allies.monsterList.get(0).Y,30,50, Allies.monsterList.get(0).Stand));
        BattleHandler.addBattleText(" Wraith Life Links "+(Allies.monsterList.get(1).HP -  Allies.monsterList.get(0).HP) +" Health" );
        Allies.monsterList.get(0).HP =   Allies.monsterList.get(1).HP;
          }
          else{
        DungeonCrawler.AnimationList.push(new HealAnimation( Allies.monsterList.get(1).X, Allies.monsterList.get(1).Y,30,50, Allies.monsterList.get(1).Stand));
        BattleHandler.addBattleText(" Wraith Life Links "+(Allies.monsterList.get(0).HP -  Allies.monsterList.get(1).HP) +" Health" );
        Allies.monsterList.get(1).HP =   Allies.monsterList.get(0).HP;
          }
          
        }
      }
    @Override
         public void UseAbility(MonsterGroup Allies){
        mirror =false;
      LifeLink(Allies);
           if(shield){
               GetRandomHero();
          double ratio = Math.pow(Magic,2) / (Math.pow(Magic,2) + Math.pow(TargetedHero.Magic,2));
        int damage = (int)(ratio * Power)*3;   
        damage = reducedDamage(damage,TargetedHero);
        if(TargetedHero.HP < damage)
            damage = TargetedHero.HP-1; 
        shield = false;
        DungeonCrawler.AnimationList.push(new MissileAnimation(X,Y,TargetedHero.X,TargetedHero.Y,ImageHandler.ImgWraithBomb,60));
          BattleHandler.addBattleText(name+" Unleashes Charge on "+ TargetedHero.name);
          BattleHandler.addBattleText(" "+TargetedHero.name+" takes "+damage+" Damage");
         TargetedHero.TakeDamage( damage);
          
      }
        
        super.UseAbility(Allies);
               LifeLink(Allies);
         }
         
    
    Color shieldColor = new Color(146,26,171);
    Color mirrorColor = new Color(160,160,200);
    @Override
      public void Draw(Graphics2D g){
        super.Draw(g);
        if(shield){
            g.setColor(shieldColor);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
           int ovalSize = 120;
        g.fillOval(X-ovalSize/2, Y-ovalSize/2, ovalSize, ovalSize);
          g.fillOval(X-ovalSize/4, Y-ovalSize/4, ovalSize/2, ovalSize/2);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
        
          if(mirror){
            g.setColor(mirrorColor);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
          
        g.fillRect(X-Stand.getWidth(null)/2-30, Y-Stand.getHeight(null)/4   , 22, Stand.getHeight(null)/2);
        g.fillRect(X-Stand.getWidth(null)/2-22, Y-Stand.getHeight(null)/4 +10, 12, Stand.getHeight(null)/2-20);
           g.setColor(Color.WHITE);
        g.fillRect(X-Stand.getWidth(null)/2-26, Y-Stand.getHeight(null)/4 +2, 4, Stand.getHeight(null)/2-4);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
          
      }
    
}
