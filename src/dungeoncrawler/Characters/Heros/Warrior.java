/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Heros;

import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.*;
import dungeoncrawler.Abilities.Animations.Animation;
import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Abilities.Animations.HealAnimation;
import dungeoncrawler.Abilities.Animations.MissileAnimation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import dungeoncrawler.StateHandlers.WorldHandler;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Nick
 */
public class Warrior extends Hero{
    
    public int TauntHealCD = 0;
    public int SlashBashCD = 0;
    
    public boolean useMagicSetAbility;
     public void CoolDown(){
         TauntHealCD--;
         SlashBashCD--;
         if(TauntHealCD<0)
             TauntHealCD =0;
         if(SlashBashCD<0)
             SlashBashCD = 0;
             
     }
    @Override
    public void SetAbilityUsable(){
        
            abilityList.get(1).Usable = false; // Taunt
            abilityList.get(2).Usable = false;  // Heal
            abilityList.get(3).Usable = false;  // Slash          
            abilityList.get(4).Usable = false;  // Bash
        
        if(useMagicSetAbility){
              if(TauntHealCD ==0)
            abilityList.get(2).Usable = true;  // Heal 
              if(SlashBashCD ==0)
            abilityList.get(4).Usable = true;  // Bash
        }
        if(!useMagicSetAbility){            
              if(TauntHealCD ==0)
            abilityList.get(1).Usable = true; // Taunt  
              if(SlashBashCD ==0)
            abilityList.get(3).Usable = true;  // Slash  
        }
        
    }
     @Override
     public void TakeDamage(int damage){
      if(DungeonCrawler.Blm.Absorb)   {
          DungeonCrawler.Blm.HP -= damage;  
          DungeonCrawler.AnimationList.push(new MissileAnimation(X,Y,DungeonCrawler.Blm.X,DungeonCrawler.Blm.Y,ImageHandler.ImgAbsorb,80));
       
       BattleHandler.addBattleText( " "+DungeonCrawler.Blm.name+" Absorbs The Hit ");      
      }   
      else
          HP-=damage;
     }
    
    public Warrior(Random rnd,Image D,Image S,Image SL,Image SC,Image SLC
           ,Image RS,Image LS,Image Def){
        super(rnd,D,S,SL,SC,SLC,RS,LS,Def);
         name = "Warrior";
        
        LeftRing.Attack+=1;
        RightRing.Magic+=2;
    
        Ammy.Def+=3;
        Ammy.HP+=15;
        
        
        abilityList.add(new Taunt());
        abilityList.add(new Heal());
        abilityList.add(new Slash());
        abilityList.add(new Bash());
        abilityList.add(new Refresh());
    
    }
    
    public void WarriorUseAbility(){
        Defending = false; 
        CoolDown();
        useMagicSetAbility = true;
    }
    
public class Taunt extends Ability{
    
    public Taunt(){
        super();
        name = "Taunt";
    }
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
      WarriorUseAbility();
        TauntHealCD = 4;
        
        
        double ratio = Math.pow(Magic,2) / (Math.pow(Magic,2) + Math.pow(monster.Magic,2));
        int damage = (int)(ratio * Power);
     
        BattleHandler.addBattleText(hero.name+" Taunts "+ monster.name);
        BattleHandler.addBattleText(" "+monster.name+" takes "+damage+" Damage");
        
        
          monster.TakeDamage(damage, hero);
        monster.taunted = true;
          
    }
    
}


public class Heal extends Ability{
    
    public Heal(){
        super();
        name = "Heal";
        targetHero = true;
    }
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
        WarriorUseAbility();
        TauntHealCD = 4;        
        
        int heal = (int)((double)Magic/1.7);
        
        heroTarget.HP += heal;
        if(heroTarget.HP>heroTarget.MaxHP )
            heroTarget.HP = heroTarget.MaxHP;
        
        
          DungeonCrawler.AnimationList.push(new HealAnimation(heroTarget.X,heroTarget.Y,20,70,heroTarget.Stand));
        BattleHandler.addBattleText(" "+hero.name+" Heals "+ heroTarget.name + " for "+heal +" Health");
            
    }
    
}


public class Slash extends Ability{
    
    public Slash(){
        super();
        name = "Slash";
    }    
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
      
        WarriorUseAbility();
         SlashBashCD = 4;
       
        
        double ratio = Math.pow(Attack,2) / (Math.pow(Attack,2) + Math.pow(monster.Defense,2));
        int damage = (int)(ratio * Power)*2;
        
        BattleHandler.addBattleText(hero.name+" Slashes "+ monster.name+ " for "+damage+" Damage");
 DungeonCrawler.AnimationList.push(new SlashAnimation(monster.X,monster.Y));
      
          monster.TakeDamage(damage, hero);
    }
    
        
public class SlashAnimation extends Animation{
 
    int X,Y;
    public  SlashAnimation(int x,int y){
        X=x;
        Y=y;
        TIMER = 8;
     DungeonCrawler.AnimationList.push(new AttackAnimation(X,Y,false,16));
    }
        @Override
     public void Animate(Graphics2D g) {
        
    }

        @Override
        public void ContinueAnimation() {
            TIMER--;            
            if(TIMER== 1)
     DungeonCrawler.AnimationList.push(new AttackAnimation(X,Y,true,16));
                
        }
    
}
}
    
    public class Bash extends Ability{
    
    public Bash(){
        super();
        name = "Bash";
    }
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
     
        WarriorUseAbility();
        SlashBashCD = 4;
      
       
        monster.stunned = true;
        BattleHandler.addBattleText(hero.name+" Bashes "+ monster.name);
          
    }
    
}
    
    
    
public class Refresh extends Ability{
    
    public Refresh(){
        super();
        name = "Refresh";
        noTarget = true;
    }
    
    public void  HealAlly(Hero hero){
        if(hero.isAlive){
            int tempHP = hero.HP;
            hero.HP+=Magic/9;
        if(hero.HP > hero.MaxHP)
            hero.HP = hero.MaxHP;
          DungeonCrawler.AnimationList.push(new HealAnimation(hero.X,hero.Y,10,50,hero.Stand));
       
        BattleHandler.addBattleText(" "+hero.name+" heals "+(hero.HP-tempHP)+" Health");
         
        }
    }
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
        Defending = true;
        useMagicSetAbility =true;
        
        CoolDown();
        CoolDown();
        
        BattleHandler.addBattleText(hero.name+" uses Refresh");
       
        HealAlly(DungeonCrawler.Ftr);
        HealAlly(DungeonCrawler.War);
        HealAlly(DungeonCrawler.Blm);
          
    }
    
}
    
    public void Move(LinkedList<WorldHandler.MoveDirection> MovementQueue) {
  super.Move(MovementQueue, MovementQueue.getFirst());
    }
    
    
} 
