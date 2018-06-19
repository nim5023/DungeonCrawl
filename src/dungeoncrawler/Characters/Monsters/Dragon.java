/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;

import dungeoncrawler.Abilities.*;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Nick
 */

public class Dragon extends Monster {
    
    public int turnFire =0;
    public int fury;
    protected int AttackTurns;
    public boolean GatheringFire;
    protected boolean FuryOnStun = true;
    protected double Strength = 3.0;
    DragonBreath Incinerate;
      public Dragon(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.Dragon;
         name = "Dragon";
         fury =0;
         AttackTurns = 3;
         GatheringFire = false;
            abilityList.get(0).abilityChance = 10;
            abilityList.add(new Rake());
            abilityList.get(1).abilityChance = 90;
             Incinerate = new DragonBreath();
         
    }  
    @Override  
       public void UseAbility(MonsterGroup Allies){
          
        boolean isStunned = stunned&&FuryOnStun;
        
         if(taunted || confused ||isStunned){
              fury++;    
             BattleHandler.addBattleText(" " +this.name+" Gains Fury ");
         turnFire++;
               super.UseAbility(Allies);   
         return;
         }
         
         if(!stunned)
         if(GatheringFire){
                Incinerate.ActivateAbility(null, this, null, fury,Strength);
                fury =0;
                GatheringFire = false;
               
                removeWeakness();
                return;
         }
         
          if(!stunned)
          if( turnFire>=AttackTurns){
               turnFire =0;
               GatheringFire = true;
               BattleHandler.addBattleText(this.name+" Is Drawing In Fire ");
               fury++;
              
               removeWeakness();               
               return;         
          }
              turnFire++;
             super.UseAbility(Allies);             
       }
      
    @Override
      public void LevelUp(){
        MaxHP+=63;
        HP = MaxHP;           
            
        Magic+=3;
        Defense+=3;
        Attack+=3;
        
        Power+=5.1;
      }
    Image FireWreath = ImageHandler.ImgDragonCharge;
    @Override
    public void Draw(Graphics2D g){
        AffineTransform trans = new AffineTransform();
        if(GatheringFire){
              double scaler = 0.3;
       double transScale = (1.0-scaler)/2.0;
       
       trans.translate(X - (FireWreath.getWidth(null)/2), Y - (FireWreath.getHeight(null)/2)-40);      
       trans.translate(transScale*FireWreath.getWidth(null), transScale*FireWreath.getHeight(null));
       trans.scale(scaler,scaler);       
          //  g.transform(trans);
            g.drawImage(FireWreath, trans, null);
        }
            
   
        super.Draw(g);
    }
    
}