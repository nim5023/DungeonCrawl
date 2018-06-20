/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.StateHandlers;

import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.Characters.Monsters.Dragon;
import dungeoncrawler.Characters.Monsters.MonsterGroups.*;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.Play;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 *
 * @author Nick
 */
public class WorldHandler {
    
    public LinkedList<MoveDirection> MovementQueue;
    public LinkedList<MonsterGroup> MonsterList;
    public static boolean ResetQueueFromBattle;
    
    public static Portals Up,Down;
    
    public static boolean isDragonHere = false;
                
      public class MoveDirection {
        public int X, Y;
        public MoveDirection(int x,int y){
            X=x;
            Y=y;
        }
    };
      public class Portals{
          public int X,Y;
          public Image worldImage;
          public Portals(int x,int y){
              X=x;Y=y;
          }
      };
      
    public WorldHandler(){
        Up = new Portals(50,50);
        Up.worldImage = ImageHandler.Portal;
        Down = new Portals(Play.sizeX-180,Play.sizeY/2);
        Down.worldImage = ImageHandler.Hole;
        
        ResetQueueFromBattle = true;
        MovementQueue = new LinkedList<>();             
        resetMovementQueue();        
        MonsterList= new LinkedList<>();
    }
    
    public final void resetMovementQueue(){
        if(ResetQueueFromBattle){
            MovementQueue.clear();
            for(int i  = 0; i < 20; i++)
        MovementQueue.push(new MoveDirection(0,0));
        }            
            ResetQueueFromBattle = false;
        
    }
    
    public void CreateWorldMonsters(){
        
        int pickMons = DungeonCrawler.RND.nextInt(6);
        
        if(pickMons == 0)
        MonsterList.push(new Wolves());
        else if(pickMons == 1)
         MonsterList.push(new Ogres());
        else if(pickMons == 2)
         MonsterList.push(new Wraiths());           
        else if(pickMons == 3)
         MonsterList.push(new Bears());
        else if(pickMons == 4)
         MonsterList.push(new Ronins());
        else if(pickMons == 5)
         MonsterList.push(new DarkElves());
        
           
      MonsterList.get(0).CreateNewGroup();
        
    }
    public void MoveMonsters(){
        for(int mons  =0 ; mons<MonsterList.size();mons++)
       MonsterList.get(mons).Move();
    }
    public void SpawnMonsters(){
       while(MonsterList.size() < 3)
            CreateWorldMonsters();
       
       if(isDragonHere){
           isDragonHere = false;
           if(MonsterList.get(MonsterList.size()-1).getClass() !=Dragons.class)
            MonsterList.add(new Dragons(Down.X+10,Down.Y));  
       }
    }
    
    public void StopHeroMovement(){
       DungeonCrawler.War.moving = false;
       DungeonCrawler.Ftr.moving = false;
       DungeonCrawler.Blm.moving = false;
    }
      
       public boolean UpdateMovementQueue(){        
       
       boolean L =  DungeonCrawler.LeftPressed;
       boolean R =  DungeonCrawler.RightPressed;
       boolean U =  DungeonCrawler.UpPressed;
       boolean D =  DungeonCrawler.DownPressed;
       
       if(!L && !R && !D && !U){
      StopHeroMovement();
           return false;
       }
        int speed = 4; 
        int diagSpeed = 3;
       
       MoveDirection addDirection = new MoveDirection(0,0);
       if(L)
           if(DungeonCrawler.War.X>0)
      addDirection.X -= speed;      
       
       if(R)
           if(DungeonCrawler.War.X<Play.sizeX)
      addDirection.X += speed;
       
       if(U)
           if(DungeonCrawler.War.Y>0)
      addDirection.Y -= speed;
       
       if(D)
           if(DungeonCrawler.War.Y<Play.sizeY)
      addDirection.Y += speed;
       
       if(addDirection.Y != 0 && addDirection.X !=0)
       {
           addDirection.Y *= 0.75;
           addDirection.X *= 0.75;
       }
       
       MovementQueue.removeLast();
       MovementQueue.push(addDirection);
          return true;
        
    }
       
       public void moveHeros(){
           
           resetMovementQueue();
             if( UpdateMovementQueue()){
       DungeonCrawler.War.Move(MovementQueue);
       DungeonCrawler.Blm.Move(MovementQueue);
       DungeonCrawler.Ftr.Move(MovementQueue);}
             
             heroGoesUp();
             heroGoesDown();
       }
    
       public static boolean heroCollideObject(Hero hero,int X,int Y,Image MapImage){
        if(hero.X>= X-(MapImage.getWidth(null)/2))        
        if(hero.X<= X+(MapImage.getWidth(null)/2))
        if(hero.Y>= Y-(MapImage.getHeight(null)/2))
        if(hero.Y<= Y+(MapImage.getHeight(null)/2))
            return true;
        
           return false;
       }
       
       public void heroGoesUp(){ 
           if( BattleHandler.MonsterLevel>1)
           if(heroCollideObject(DungeonCrawler.War,Up.X,Up.Y,Up.worldImage)){
               BattleHandler.MonsterLevel--;
              MonsterList.clear();
            DungeonCrawler.ResetHeroPosition(Down.X,Down.Y-50);
          ResetQueueFromBattle = true;
          isDragonHere  = false;
           }
       }
       
         public void heroGoesDown(){ 
           if(heroCollideObject(DungeonCrawler.War,Down.X,Down.Y,Down.worldImage)){
               BattleHandler.MonsterLevel++;
              MonsterList.clear();
              DungeonCrawler.ResetHeroPosition(Up.X,Up.Y+70);
              ResetQueueFromBattle = true;
              if(BattleHandler.MonsterLevel == BattleHandler.HeroLevel)
                  isDragonHere = true;
           }
       }
       
      public static boolean  checkCollision(Hero hero, MonsterGroup mon){
        
      if(heroCollideObject(hero,mon.X,mon.Y,mon.MapImage))
         return true;
        
        return false;
    }
      
       public static boolean  checkAllCollision(MonsterGroup mon){
        
      if(heroCollideObject(DungeonCrawler.War,mon.X,mon.Y,mon.MapImage))
          return true;
        if(heroCollideObject(DungeonCrawler.Blm,mon.X,mon.Y,mon.MapImage))
          return true;
          if(heroCollideObject(DungeonCrawler.Ftr,mon.X,mon.Y,mon.MapImage))
          return true;
        
        return false;
    }
      public static void HeroFaceRight(){
          DungeonCrawler.War.faceLeft = false;
          DungeonCrawler.Blm.faceLeft = false;
          DungeonCrawler.Ftr.faceLeft = false;
      }
      public void WorldToBattle(){
          for(int i = 0; i < MonsterList.size();i++)
              if(checkAllCollision(MonsterList.get(i))){
                   StopHeroMovement();
                   HeroFaceRight();
                  DungeonCrawler.BattleHand.InitalizeBattle( MonsterList.get(i));
                  MonsterList.remove(i);
                 
                  return;
              }
      }
      
       public void drawWorldState(Graphics2D g2d){
           
           if( BattleHandler.MonsterLevel>1)
            g2d.drawImage(Up.worldImage, Up.X-(Up.worldImage.getWidth(null)/2), Up.Y- (Up.worldImage.getHeight(null)/2), null);
            g2d.drawImage(Down.worldImage, Down.X-(Down.worldImage.getWidth(null)/2), Down.Y- (Down.worldImage.getHeight(null)/2), null);
  
             if( BattleHandler.MonsterLevel==2){ 
           g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
           g2d.setColor(Color.BLACK);
           g2d.drawString("Z: Menu",500, 100);
             }
            
           for(int i = 0; i < MonsterList.size();i++)
               MonsterList.get(i).Draw(g2d);           
       }
}
