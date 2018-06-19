/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters;


import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Abilities.MonsterAttackHero;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author Nick
 */
public class Monster {

    public Image Stand;
    
    public int X, Y;
    public String name;
    public int MaxHP,HP,Magic,Defense,Attack;
    public double Power;
    
    public int level;
    public LinkedList<Ability> abilityList;
    
    public int AttackAnimateTimer;
    public boolean AttackAnimate;
    public boolean GoBackAnimate;
    
    public boolean taunted;
    public boolean confused;
    public boolean stunned;
    public boolean weakened;

    protected Hero TargetedHero;
        
    public void LevelUp()
    {
        Magic  +=DungeonCrawler.RND.nextInt(3)+1;
        Defense+=DungeonCrawler.RND.nextInt(3)+1;
        Attack +=DungeonCrawler.RND.nextInt(3)+1;
        
    }
    
    public Monster(int x, int y, int lvl){
        
        TargetedHero = null;
        abilityList = new LinkedList<>();
        abilityList.push(new MonsterAttackHero());

        taunted = false;
        confused = false;
        stunned = false;
        weakened = false;
        
        X = x;
        Y = y;
        level = lvl;
        
        Power =0;
        HP=0;
        MaxHP = 0;
        Magic=0;
        Defense=0;
        Attack=0;
        
        
        AttackAnimate = false;
        GoBackAnimate = false;
        AttackAnimateTimer = 0;
        
    }
    
    public void removeWeakness(){
        if(weakened){
            weakened = false;
              BattleHandler.addBattleText(" "+this.name+" removes Weakness");
        
        }
    }
    
    public int reducedDamage(int damage,Hero hero){
        if(weakened)
              damage/=2;
          if(hero.Defending)
              damage/=2;
          return damage;
    }
    
    public void TakeDamage(int Damage, Hero hero){
        HP-=Damage;
    }
    
    
    public void GetRandomHero(){
        boolean foundAliveHero = false;
       
        int whichHero;
        do{
           whichHero =   DungeonCrawler.RND.nextInt(3);
         
           if(whichHero ==0)
            if(   DungeonCrawler.Ftr.isAlive){
            TargetedHero = DungeonCrawler.Ftr;
            foundAliveHero=true;
            }
           
           if(whichHero ==1)
            if(   DungeonCrawler.War.isAlive){
            TargetedHero = DungeonCrawler.War;
            foundAliveHero=true;
            }
           
           if(whichHero ==2)
            if(   DungeonCrawler.Blm.isAlive){
            TargetedHero = DungeonCrawler.Blm;
            foundAliveHero=true;
            }
            
        }while(!foundAliveHero);
    }
    
    
    public void UseAbility(MonsterGroup Allies){
        
       GetRandomHero();                
       
        int pickAbility = 0;
        int TotalWeight = 0;
        
        for(int abl = 0; abl < abilityList.size(); abl++)
            TotalWeight+=abilityList.get(abl).abilityChance;
        
         int chanceAbility = DungeonCrawler.RND.nextInt(TotalWeight)+1;        
         int CurrentWeight = 0; 
         
        for(int abl = 0; abl < abilityList.size(); abl++){
            if(CurrentWeight<chanceAbility)
                if(CurrentWeight+abilityList.get(abl).abilityChance>=chanceAbility)
            pickAbility = abl;
            CurrentWeight+=abilityList.get(abl).abilityChance;
        }
        
          
        
        if(taunted){
            pickAbility =0;
            taunted = false;
            
         BattleHandler.addBattleText(this.name+" is provoked by "+ DungeonCrawler.War.name);
            if(DungeonCrawler.War.isAlive)
            TargetedHero = DungeonCrawler.War;
        }
        if(stunned){
            stunned = false;
             BattleHandler.addBattleText(this.name+" is Stunned");
            return;
        }
        
         if(confused){
            confused = false;
             double ratio = Math.pow(this.Attack,2) / (Math.pow(this.Attack,2) + Math.pow(this.Defense,2));
        int damage = (int)(ratio * this.Power);
         if(this.HP <= damage)
             damage =HP-1;
        this.HP -= damage;         
        
        DungeonCrawler.AnimationList.push(new AttackAnimation(X,Y,true,40));   
         BattleHandler.addBattleText(this.name+" is Confused");
         BattleHandler.addBattleText(" "+this.name+" takes "+damage+" Damage");         
           return;             
     
        }
        
        abilityList.get(pickAbility).ActivateAbility(TargetedHero, this,null);
        
      removeWeakness();
    }

    
     public void AnimateAttacks(){
        
        if(AttackAnimate){
             if(GoBackAnimate){
                 AttackAnimateTimer--;
                this.X++;
                if(AttackAnimateTimer  ==0){
                    GoBackAnimate = false; 
                    AttackAnimate = false;
                }
             }
                 
            if(!GoBackAnimate){
                AttackAnimateTimer++;
                this.X--;
                if(AttackAnimateTimer > 20)
                    GoBackAnimate = true;                
            }
           
        }
        
    }
   
     Image ConfuseImage = ImageHandler.ImgQuestionMark;
     Image StunnedImage = ImageHandler.ImgStun;
    public void Draw(Graphics2D g){
        int halfWidth = (Stand.getWidth(null)/2);
        int halfHeight =(Stand.getHeight(null)/2);
        
              g.drawImage(Stand, X-halfWidth, Y- halfHeight, null);
              
              if(taunted)
              DrawTaunt(g);
              
              if(weakened)
              DrawWeakness(g);
              
              if(confused)                  
              g.drawImage(ConfuseImage, X-ConfuseImage.getWidth(null)/2, Y-ConfuseImage.getHeight(null)/2, null);  
             
              if(stunned)                  
              g.drawImage(StunnedImage, X-StunnedImage.getWidth(null)/2, Y-StunnedImage.getHeight(null)/2, null);  
              
        if( DungeonCrawler.showHP){
              g.setColor(Color.BLACK);  
        g.drawRect(X-halfWidth-1,Y+ halfHeight+1,Stand.getWidth(null)+1 , 3);   
        g.setColor(Color.RED);
        g.fillRect(X-halfWidth,Y+ halfHeight+2,Stand.getWidth(null) , 2);
        g.setColor(Color.GREEN);
        
        double HPRatio = (double)HP/(double)MaxHP;
        g.fillRect(X-halfWidth,Y+ halfHeight+2,(int)((double)Stand.getWidth(null)*HPRatio) , 2);
        }
    }
    double bounceArrow=0;
    boolean arrowDown =true;
    public void BounceArrow(){
           double bounce = 0.1;
        if(arrowDown){
            bounceArrow+=bounce;
            if(bounceArrow > 6)
                arrowDown = false;
        }
        else{
               bounceArrow-=bounce;
            if(bounceArrow < 0)
                arrowDown = true;
        }
    }
    public void DrawWeakness(Graphics2D g){
     BounceArrow();
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
     
    int arrowWidth = Stand.getWidth(null)/4;
    int arrowHeight = Stand.getHeight(null)/2;
        g.setColor(Color.BLUE);
        g.fillRoundRect(X-arrowWidth/2, (int)bounceArrow+Y-arrowHeight/2, arrowWidth, arrowHeight,5,5);
       int[] xs = {X-3*arrowWidth/4,X,X+3*arrowWidth/4};
       int[] ys = {(int)bounceArrow+Y+arrowHeight/2,(int)bounceArrow+Y+3*arrowHeight/4,(int)bounceArrow+Y+arrowHeight/2};
        g.fillPolygon(xs, ys,3);
              
           g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
     public void DrawTaunt(Graphics2D g){
     BounceArrow();
     
       double TimeRatio = 0.1-((double)bounceArrow/(double)100.0); 
                    int tX = X - Stand.getWidth(null)/2+(int)((DungeonCrawler.War.X-X+ Stand.getWidth(null)/2)*TimeRatio);
                    int tY = Y +(int)((DungeonCrawler.War.Y-Y)*TimeRatio); 
       g.setColor(Color.RED);
        g.drawLine(X- Stand.getWidth(null)/2,Y,tX,tY);
        g.drawLine(X,Y,tX,tY);
     
     
     }
    
    
}
