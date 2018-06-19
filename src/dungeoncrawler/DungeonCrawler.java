/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import dungeoncrawler.Abilities.Animations.Animation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Heros.Fighter;
import dungeoncrawler.Characters.Heros.Mage;
import dungeoncrawler.Characters.Heros.Warrior;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.Characters.Monsters.MonsterGroups.Wolves;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.StateHandlers.RollHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import dungeoncrawler.StateHandlers.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JPanel;

public class DungeonCrawler extends JPanel {

    private ImageHandler IH;
    public static Warrior War;
    public static Mage Blm;
    public static Fighter Ftr;
    
    public boolean openingSaved;
    
    public static Random RND;
    public static boolean showHP;
    
    public static boolean UpPressed;
    public static boolean DownPressed;
    public static boolean LeftPressed;
    public static boolean RightPressed;
    
    public static RollHandler RollHand; 
    public WorldHandler WorldHand; 
    public static RewardHandler RewardHand; 
    public static BattleHandler BattleHand;
    public static LoadSaveHandler LoadSaveHand;
    public enum State {
        ROLL, WORLD, BATTLE, STATS,LVLUP
    };
    public static State GameState;
    public static LinkedList<Animation> AnimationList;
     
    public DungeonCrawler()  {
        //  super(new BorderLayout());
        
        RND = new Random();
        RND.setSeed(System.currentTimeMillis());
        
        openingSaved = false;
        
        AnimationList = new LinkedList<>();
        
        IH  = new ImageHandler();
        showHP = true;
        
        GameState = State.ROLL;
        
        RollHand = new RollHandler();
        WorldHand = new WorldHandler();
        BattleHand = new BattleHandler();          
        RewardHand = new RewardHandler();
        LoadSaveHand = new LoadSaveHandler();
        
        War = new Warrior(RND, IH.war.Down,  IH.war.Stand,  IH.war.StandChest,  IH.war.StandLeg,  IH.war.StandLegChest,  IH.war.WalkLeft,  IH.war.WalkRight,IH.war.Defends);
        Blm = new Mage(RND,    IH.mage.Down, IH.mage.Stand, IH.mage.StandChest, IH.mage.StandLeg, IH.mage.StandLegChest, IH.mage.WalkLeft, IH.mage.WalkLeft,IH.mage.Defends);
        Ftr = new Fighter(RND, IH.ftr.Down,  IH.ftr.Stand,  IH.ftr.StandChest,  IH.ftr.StandLeg,  IH.ftr.StandLegChest,  IH.ftr.WalkLeft,  IH.ftr.WalkRight,IH.ftr.Defends);

        RollHandler.PlaceHerosByStats();
               
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);

    }

    
    public void GameLoop() {

        War.changeImage();
        Blm.changeImage();
        Ftr.changeImage();     
        
          for(int anim = AnimationList.size()-1; anim >= 0; anim--){
                AnimationList.get(anim).ContinueAnimation();
            if( AnimationList.get(anim).TIMER <0)
                AnimationList.remove(anim);
        }
                
       if (GameState == State.WORLD){
       WorldHand.SpawnMonsters();
       WorldHand.MoveMonsters();
       WorldHand.moveHeros();
       WorldHand.WorldToBattle();   
       }
       if(GameState == State.BATTLE){
       BattleHand.BattleLoop();
       }
       
        
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(IH.BG, 0, 0, null); // see javadoc for more info on the parameters      

            if (GameState == State.ROLL) {
                RollHand.drawRollState(g2d);               
            }
            
              if(GameState == State.WORLD){
                WorldHand.drawWorldState(g2d);               
            }
            
            if (GameState == State.STATS)
                RewardHand.DrawStatState(g2d);
            
            if( GameState == State.LVLUP )
            RewardHand.DrawLvlUp(g2d);
            
              
            Blm.Draw(g2d);
            Ftr.Draw(g2d);
            War.Draw(g2d);
            
           // g2d.drawString(Integer.toString(BattleHandler.numberOfTurns)+ "  " + Double.toString(BattleHandler.PercentHealth), 10, 20);
             if(GameState == State.BATTLE){
            BattleHand.drawBattleScene(g2d);
            
              for(int anim = 0; anim < AnimationList.size(); anim++)
                AnimationList.get(anim).Animate(g2d);
              }
            

        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(DungeonCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
   
    public static void  ResetHeroPosition(int X, int Y){
        War.X = X;
        War.Y = Y;
        
        Ftr.X = X;
        Ftr.Y = Y;
        
        Blm.X = X;
        Blm.Y = Y;
    }
    public void ConfirmButton(){
        
        
          if(GameState == State.STATS)
             RewardHand.handleConfirm();
        
        if(GameState == State.WORLD){
                   GameState = State.STATS;
                   RewardHandler.EnterStats(War.X,War.Y);
               }
        
        
        if( GameState == State.LVLUP)
                RewardHand.handleConfirm();
        
        
        if(GameState == State.ROLL){ 
            BattleHandler.MonsterLevel = BattleHandler.HeroLevel-1;
            GameState = State.WORLD;
            ResetHeroPosition(50,Play.sizeY/2);
            if(!openingSaved){
         War.InitialEquip();
         Blm.InitialEquip();
         Ftr.InitialEquip();}
            
        }
         
        
        if(GameState == State.BATTLE)
            BattleHand.handleConfirm();
        
    }
    
    public void BackButton(){
                if(GameState == State.BATTLE)
                    BattleHand.handleBack();
            
               if(GameState == State.STATS)
                    RewardHand.handleBack();       
    }
    
    public void UpButton(){
        UpPressed = false;
          if(GameState == State.BATTLE)
               BattleHand.handleUp();
          
          if(GameState == State.STATS)
              RewardHand.handleUp();
        
    }
    public void LeftButton(){
         LeftPressed = false;
          if(GameState == State.BATTLE)
               BattleHand.handleLeft();
    
          if(GameState == State.STATS)
              RewardHand.handleLeft();      
        
    }public void RightButton(){
         RightPressed = false;
          if(GameState == State.BATTLE)
               BattleHand.handleRight();

          if(GameState == State.STATS)
              RewardHand.handleRight();        
    }
    public void DownButton(){
         DownPressed = false;
         if(GameState == State.BATTLE)
               BattleHand.handleDown();        
    
          if(GameState == State.STATS)
              RewardHand.handleDown();}
    
}
