/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Heros;

import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.Animations.Animation;
import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Abilities.Animations.MissileAnimation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import dungeoncrawler.StateHandlers.WorldHandler;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Nick
 */
 public class Fighter extends Hero{
     public int Rage;
     public boolean isCharged;
     public int ChargeMonster;
     
    @Override
     public void TakeDamage(int damage){
      if(DungeonCrawler.Blm.Absorb)   {
          DungeonCrawler.Blm.HP -= damage;    
          DungeonCrawler.AnimationList.push(new MissileAnimation(X,Y,DungeonCrawler.Blm.X,DungeonCrawler.Blm.Y,ImageHandler.ImgAbsorb,100));
       
       BattleHandler.addBattleText( " "+DungeonCrawler.Blm.name+" Absorbs The Hit ");      
      }   
      else
          HP-=damage;
     }
     public void SetAbilityUsableRage(int rage, int ability){
          if(Rage < rage)
            abilityList.get(ability).Usable = false; 
        else
            abilityList.get(ability).Usable = true; 
     }
     
    @Override
    public void SetAbilityUsable(){
        SetAbilityUsableRage(1,1);// Charge 
        SetAbilityUsableRage(2,3);// Weaken    
        SetAbilityUsableRage(2,4);// Confuse                
        SetAbilityUsableRage(3,5);// OmniSlash
                
    }
    public Fighter(Random rnd,Image D,Image S,Image SL,Image SC,Image SLC
           ,Image RS,Image LS,Image Def){
        super(rnd,D,S,SL,SC,SLC,RS,LS,Def);
        name = "Fighter";
 
        LeftRing.Attack+=3;
        RightRing.Magic+=1;
        Ammy.HP+=10;
        Ammy.Def+=2;
        
        Rage = 0;
        isCharged = false;
        ChargeMonster = 0;
        
        Ability TEST = new Ability();
        TEST.name = "TEST      0";
        TEST.Usable = false;
        
        abilityList.add(new Charge());
        abilityList.add(TEST);
        abilityList.add(new Weaken());
        abilityList.add(new Confuse());
        abilityList.add(new OmniSlash());
    }
    
    
    public class Charge extends Ability{
    
    public Charge(){
        super();
        name = "Charge       1";      
    }
    
    Image ChargeBall = ImageHandler.ImgWraithBomb;
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     if(!isCharged){
        Defending = true;        
        isCharged = true;
        Rage--;
       BattleHandler.addBattleText(hero.name+" Stores Charge ");
     return;}
     
      if(isCharged){            
          double ratio = Math.pow(Magic,2) / (Math.pow(monster.Magic,2) + Math.pow(Magic,2));
        int damage = (int)(ratio * Power*2.4);         
        DungeonCrawler.AnimationList.push(new MissileAnimation(X,Y,monster.X,monster.Y,ImageHandler.ImgFireBall,20));  
          BattleHandler.addBattleText(hero.name+" Unleashes Charge on "+ monster.name);
          BattleHandler.addBattleText(" "+monster.name+" takes "+damage+" Damage");
          monster.TakeDamage(damage, hero);
          isCharged = false;
           Defending = false;           
      }
        
    }    
}
    
      
      public boolean UseChargeAttack(){
           if(!turnDone)
           if(isAlive)
              if(isCharged){
                  if(ChargeMonster >= BattleHandler.Enemies.monsterList.size())
               ChargeMonster = BattleHandler.Enemies.monsterList.size()-1;
              abilityList.get(1).ActivateAbility( this, BattleHandler.Enemies.monsterList.get(ChargeMonster), null);
                  
              return true;
              }
      return false;
      }
    
public class Weaken extends Ability{
    
    public Weaken(){
        super();
        name = "Weaken       2";
    }
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
        Defending = false;        
      
        Rage-=2;
        
        monster.weakened = true;
        BattleHandler.addBattleText(hero.name+" Weakens "+ monster.name);         
    }
    
}
    
    public class Confuse extends Ability{
    
    public Confuse(){
        super();
        name = "Confuse      2";
    }
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
        Defending = false;        
      
        Rage-=2;
        
        monster.confused = true;
        BattleHandler.addBattleText(hero.name+" Confuses "+ monster.name);         
    }
    
}
    
    
    
public class OmniSlash extends Ability{
    
    public OmniSlash(){
        super();
        name = "OmniSlash    3";
          noTarget = true;
    }
    
        @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
        Defending = false;
        
        
        BattleHandler.addBattleText(hero.name+" UsesOmniSlash ");
      Rage-=3;
     
      for(int mons =0; mons < BattleHandler.Enemies.monsterList.size();mons++){
        
        double ratio = Math.pow(Attack,2) / (Math.pow(Attack,2) + Math.pow(BattleHandler.Enemies.monsterList.get(mons).Defense,2));
        int damage = (int) (ratio * Power*3.0/(double)BattleHandler.Enemies.monsterList.size());     
        
        DungeonCrawler.AnimationList.push(new OmniSlashAnimation(BattleHandler.Enemies.monsterList.get(mons).X,BattleHandler.Enemies.monsterList.get(mons).Y));
        BattleHandler.addBattleText(" "+BattleHandler.Enemies.monsterList.get(mons).name+" takes "+damage+" Damage");    
        BattleHandler.Enemies.monsterList.get(mons).TakeDamage(damage, hero);
      }
          
    }
        
public class OmniSlashAnimation extends Animation{
 
    int X,Y,T1,T2,T3;
    public  OmniSlashAnimation(int x,int y){
        X=x;
        Y=y;
        T3 = 0;
        T2 = T3 + DungeonCrawler.RND.nextInt(7)+5;
        T1 = T2 + DungeonCrawler.RND.nextInt(7)+5;
        TIMER = T1+T2+T3+DungeonCrawler.RND.nextInt(7)+5;
              
    }
        @Override
     public void Animate(Graphics2D g) {
        
    }

        @Override
        public void ContinueAnimation() {
            TIMER--;  
            if(TIMER== T1)
     DungeonCrawler.AnimationList.push(new AttackAnimation(X,Y,false,16));      
            if(TIMER== T2)
     DungeonCrawler.AnimationList.push(new AttackAnimation(X,Y,true,16));
            if(TIMER== T3)
     DungeonCrawler.AnimationList.push(new AttackAnimation(X,Y,false,16));
                
        }
    
}
    
}

    
    
     public void Move(LinkedList<WorldHandler.MoveDirection> MovementQueue) {
      super.Move(MovementQueue, MovementQueue.get(10));
     }
    
    
    
} 
