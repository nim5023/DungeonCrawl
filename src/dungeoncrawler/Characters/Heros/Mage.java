/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Heros;

import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.*;
import dungeoncrawler.Abilities.Animations.HealAnimation;
import dungeoncrawler.Abilities.Animations.MissileAnimation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;

import dungeoncrawler.StateHandlers.WorldHandler;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.NoninvertibleTransformException;

import java.util.LinkedList;
import java.util.Random;


/**
 *
 * @author Nick
 */
public class Mage extends Hero{
    public int Mana;
    public boolean imbued;
    public boolean Absorb;
    
     public void SetAbilityUsableMana(int mana, int ability){
       if(Mana < mana)
            abilityList.get(ability).Usable = false; // Imbue
        else
             abilityList.get(ability).Usable = true; // Imbue
     }
    @Override
    public void SetAbilityUsable(){
        SetAbilityUsableMana(2,3);//Imbue
        SetAbilityUsableMana(3,4);//Drain
        SetAbilityUsableMana(5,5);//Flare
        
        if(imbued)
       abilityList.get(3).Usable = false; // Imbue
        
        
    }
    
    public Mage(Random rnd,Image D,Image S,Image SL,Image SC,Image SLC
           ,Image RS,Image LS,Image Def){
        super(rnd,D,S,SL,SC,SLC,RS,LS,Def);
         name = "Mage";
 
        Mana = 5;
        imbued = false;
        Absorb = false;
        
        LeftRing.Attack+=2;
        RightRing.Magic+=3;
    
        Ammy.Def+=1;
        Ammy.HP+=5;
                           
        
        abilityList.add(new Meditate());
        abilityList.add(new Absorb());
        abilityList.add(new Imbue());
        abilityList.add(new Drain());
        abilityList.add(new Flare());
        
    }
    
    
public class Meditate extends Ability{    
    public Meditate(){
      super();
        name = "Meditate    +4";        
        noTarget = true;
    }        
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){   
        Mana +=4;
        BattleHandler.addBattleText(hero.name+ " gains 4 mana");
        Defending = true;        
    }    
}

public class Absorb extends Ability{    
    public Absorb(){
      super();
        name = "Absorb      +2";        
        noTarget = true;
    }        
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){   
        Mana +=2;
        BattleHandler.addBattleText(hero.name+ " gains 2 mana");
        Defending = true;       
        Absorb = true;
    }    
}


public class Imbue extends Ability{    
    public Imbue(){
        super();
        name = "Imbue        2";
        noTarget = true;
    }   
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
        Defending = false;
           imbued= true;
           Mana -=2;
        
        BattleHandler.addBattleText(hero.name+" Imbues his next Attack ");        
    }    
}
    
    
    
public class Drain extends Ability{    
    public Drain(){
        super();
        name = "Drain        3";
    }
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
        Defending = false;
        
        Mana-=3;
        
        double ratio = Math.pow( Magic,2) / (Math.pow( Magic,2) + Math.pow(monster.Magic,2));
        int damage = (int)(ratio*1.3 * Power);
        
        DungeonCrawler.AnimationList.push(new MissileAnimation(monster.X,monster.Y,X,Y,ImageHandler.ImgWraithBomb,55));      
          DungeonCrawler.AnimationList.push(new HealAnimation(X,Y,10,70,Stand));
              
        BattleHandler.addBattleText(hero.name+" Uses Drain on "+ monster.name);
        BattleHandler.addBattleText(" "+monster.name+" takes "+damage+" Damage");
          monster.TakeDamage(damage, hero);
             HP += damage;
             int tempHP=0;
             if(HP > MaxHP){
             tempHP = HP-MaxHP;
                 HP = MaxHP;
             }
        
        BattleHandler.addBattleText(" "+hero.name+" heals "+(damage-tempHP)+" Health");
          
    }
    
}
    

public class Flare extends Ability{
    
    public Flare(){
        super();
        name = "Flare        5";
    }
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
        Defending = false;
        
        Mana-=5;
        
        double ratio = Math.pow( Magic,2) / (Math.pow( Magic,2) + Math.pow(monster.Magic,2));
        int damage = (int)(ratio * Power*9.0/4.0);
             
        DungeonCrawler.AnimationList.push(new MissileAnimation(X,Y,monster.X,monster.Y,ImageHandler.ImgFireBall,30));
         BattleHandler.addBattleText(hero.name+" Uses Flare on "+ monster.name);
        BattleHandler.addBattleText(" "+monster.name+" takes "+damage+" Damage");
          monster.TakeDamage(damage, hero);
    }    
}    
    public void Move(LinkedList<WorldHandler.MoveDirection> MovementQueue) {        
        super.Move(MovementQueue, MovementQueue.getLast());
    }
    
    @Override
     public void Draw(Graphics2D g) throws NoninvertibleTransformException {
        if(imbued){
            g.setColor(Color.magenta); 
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.fillOval(X-30, Y-30, 60, 60);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
          }
        if(Absorb){
            g.setColor(Color.CYAN); 
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g.fillOval(X-30, Y-30, 60, 60);
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
          }
        super.Draw(g);
    }
    
} 
