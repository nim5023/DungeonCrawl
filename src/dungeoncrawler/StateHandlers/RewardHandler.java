/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.StateHandlers;

import dungeoncrawler.Characters.Hero;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.DungeonCrawler.State;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.Items.Item;
import dungeoncrawler.Play;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick
 */
public class RewardHandler {
    
    public Image ring1Image   = ImageHandler.RightRing;
    public Image ring2Image   = ImageHandler.LeftRing;
    public Image ammuletImage = ImageHandler.Amulets;  
    
    public static Item DropItem;
    Item InvItem = new Item(true,0,0,0,0);
    
    int CursorItem =0;
    int CursorHero =0;
    
    static  int  saveWorldX,saveWorldY;
    
         
    static   HeroStats  Warlvl = new HeroStats() ;
    static   HeroStats  Blmlvl = new HeroStats() ;
    static   HeroStats  Ftrlvl = new HeroStats();
    
    private static class HeroStats{
        int Attack=0;
        int Def=0;
        int Magic=0;
        int HP=0;
        public void Set(Hero hero){
            Attack = hero.Attack;
            Def = hero.Defense;
            Magic = hero.Magic;
            HP = hero.MaxHP;
        }
        
    }
    
    public RewardHandler(){
        DropItem = new Item(false,0,0,0,0);
        saveWorldX = 0;
        saveWorldY = 0;
    }
    public static void EnterStats(int x,int y){
      
            saveWorldX = x;
            saveWorldY = y;
            
            RollHandler.PlaceHerosByStats();
            WorldHandler.HeroFaceRight();
            DropItem = new Item(false,0,0,0,0);
    }
    
    static boolean[]pickStats = {false,false,false};
    static int GetRandomStat( boolean gettingStat){
        int whichStat;
        while(true){
            whichStat = DungeonCrawler.RND.nextInt(3);
            if(pickStats[whichStat]==gettingStat)
                return whichStat;
        }
    }
    public static void CreateNewItem(){
     
          EnterStats(50,Play.sizeY/2-50);
            
           int AmmyOdds = DungeonCrawler.RND.nextInt(5);
           
           boolean isItemAmulet = true;
           if(AmmyOdds>1)
           isItemAmulet = false;
           
           int numStats = (DungeonCrawler.RND.nextInt(4)+3)/2;
           for(int ps = 0; ps < 3; ps++)
            pickStats[ps] = false;
      
           for(int i =0; i < numStats; i++)
              pickStats[GetRandomStat(false)] = true;
           
               HeroStats newItemStats = new HeroStats();
                     
               for(int lvl = 0; lvl < BattleHandler.MonsterLevel+1;lvl++){
                  int whichStat = GetRandomStat(true);
                   
                   if(isItemAmulet)
                   newItemStats.HP+=DungeonCrawler.RND.nextInt(5)+8;
                   
                   if(whichStat ==0)
                       newItemStats.Attack+=DungeonCrawler.RND.nextInt(3)+1;
                   if(whichStat ==1)
                       newItemStats.Def+=DungeonCrawler.RND.nextInt(3)+1;
                   if(whichStat ==2)
                       newItemStats.Magic+=DungeonCrawler.RND.nextInt(3)+1;
               }              
                   
               DropItem = new Item(isItemAmulet,newItemStats.HP/2,newItemStats.Attack/2,newItemStats.Def/2,newItemStats.Magic/2);      
        }
    
     static void levelUpHero(Hero hero, HeroStats heroStat){
        heroStat.Set(hero);
        hero.levelUp();
        FindStatDiffernce(hero,heroStat);
    }
    public static void levelUpHeros(){
        
         levelUpHero(DungeonCrawler.War,Warlvl);
         levelUpHero(DungeonCrawler.Ftr,Ftrlvl);
         levelUpHero(DungeonCrawler.Blm,Blmlvl);
        
        RollHandler.PlaceHerosByStats();
        WorldHandler.ResetQueueFromBattle = true;
        BattleHandler.HeroLevel++;    
        
    }
    
     static void FindStatDiffernce(Hero hero, HeroStats herostats){
             herostats.Attack = hero.Attack  - herostats.Attack;
             herostats.Def    = hero.Defense - herostats.Def;
             herostats.Magic  = hero.Magic   - herostats.Magic;
             herostats.HP     = hero.MaxHP   - herostats.HP;
    }
    
    public void handleRight(){
    CursorItem++;
    if(CursorItem>2)
        CursorItem=0;
    }
    public void handleLeft(){
    CursorItem--;
    if(CursorItem<0)
        CursorItem=2;
    }
    public void handleUp(){
    CursorHero--;
    if(CursorHero<0)
        CursorHero=2;
    }
    public void handleDown(){
    CursorHero++;
    if(CursorHero>2)
        CursorHero=0;
    }
    
    void SwapStats(Item i1, Item i2){
        int tempAtk,tempDef,tempMag,tempHP;
        
                                tempAtk = i1.Attack;
                                tempDef = i1.Def;
                                tempMag = i1.Magic;                         
                                tempHP  = i1.HP;
                                
                                i1.Attack = i2.Attack;
                                i1.Def    = i2.Def;
                                i1.Magic  = i2.Magic;                                
                                i1.HP     = i2.HP;
                                
                                i2.Attack = tempAtk;
                                i2.Def    = tempDef;
                                i2.Magic  = tempMag; 
                                i2.HP     = tempHP;
                                
                       i1.setEmpty();
                       i2.setEmpty();
    }
    public void EquipItemSwapStats(){        
  
        Item OldItem = getItemFromCursor();   
                        
                        getHeroFromCursor().Attack  -= OldItem.Attack;
                        getHeroFromCursor().Defense -= OldItem.Def;
                        getHeroFromCursor().Magic   -= OldItem.Magic;  
                        getHeroFromCursor().MaxHP   -= OldItem.HP ;
                        
                        getHeroFromCursor().Attack  += DropItem.Attack;
                        getHeroFromCursor().Defense += DropItem.Def;
                        getHeroFromCursor().Magic   += DropItem.Magic; 
                        getHeroFromCursor().MaxHP   += DropItem.HP;
                        
                        getHeroFromCursor().HP = getHeroFromCursor().MaxHP;
                         
                             SwapStats(OldItem,DropItem);
                                 
    }    
  
    public void EquipItemIsAmmy(boolean isAm){
          Item OldItem = getItemFromCursor();        
        
                   if(  OldItem.isAmulet == isAm)
                   if((DropItem.isAmulet == isAm) || DropItem.isEmpty){
                       DropItem.isAmulet =  isAm;
                       EquipItemSwapStats();
                      }
    }
    
     public void EquipItem(){
                   EquipItemIsAmmy(true);
                   EquipItemIsAmmy(false);
     }
   
    public void handleConfirm(){  
            
        if( DungeonCrawler.GameState ==DungeonCrawler.State.STATS)
        EquipItem();
        
          if(DungeonCrawler.GameState ==DungeonCrawler.State.LVLUP){
          DungeonCrawler.  ResetHeroPosition(WorldHandler.Down.X,WorldHandler.Down.Y-50);          
          DungeonCrawler.GameState = DungeonCrawler.State.WORLD;}
    }
      
    public void handleBack(){
        
           DungeonCrawler.ResetHeroPosition(saveWorldX,saveWorldY);       
           WorldHandler.ResetQueueFromBattle =true;
           DungeonCrawler. GameState = DungeonCrawler.State.WORLD; 
    }
    
       void PrintLvlUpValues(Hero hero,HeroStats herostats, int Ypixel, Graphics2D g2d) {

        g2d.drawString(Integer.toString(hero.MaxHP-herostats.HP) + "+"+Integer.toString(herostats.HP), 200, Ypixel);
        g2d.drawString(Integer.toString(hero.Attack-herostats.Attack)+"+"+Integer.toString(herostats.Attack), 400, Ypixel);
        g2d.drawString(Integer.toString(hero.Defense-herostats.Def)+"+"+Integer.toString(herostats.Def), 600, Ypixel);
        g2d.drawString(Integer.toString(hero.Magic-herostats.Magic)+"+"+Integer.toString(herostats.Magic), 800, Ypixel);

    }
      
      public void drawStats(Graphics2D g2d,int Xoffset){
                
                g2d.setColor(Color.BLACK);               
                g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
                g2d.drawString("HP",     200+Xoffset, 110);
                g2d.drawString("ATTACK", 400+Xoffset, 110);
                g2d.drawString("DEFENSE",600+Xoffset, 110);
                g2d.drawString("MAGIC",  800+Xoffset, 110);

                g2d.setColor(Color.red);
                g2d.drawString("Warrior", 10, 160);   
                
                g2d.setColor(new Color(200,120,0));
                g2d.drawString("Fighter", 10, 210);                
                
                g2d.setColor(Color.blue);
                g2d.drawString("Mage", 10, 260);
                
      }
      
      public void drawBox(Graphics2D g2d,int sX,int sY, int width, int height){
           g2d.setColor(Color.BLACK);
         g2d.fillRect(sX-1, sY-1, width+2, height+2);
         g2d.setColor(Color.WHITE);
         g2d.fillRect(sX, sY, width, height); 
         g2d.setColor(Color.BLACK);
      }
          int screenX = 5;
          int screenY = 20; 
      public void DrawLvlUp(Graphics2D g2d){        
          drawBox(g2d,screenX,screenY,895,370);
          drawStats(g2d,0);

                g2d.setColor(Color.red);              
                PrintLvlUpValues(DungeonCrawler.War,Warlvl, 160, g2d);
                
                g2d.setColor(new Color(200,120,0));               
                PrintLvlUpValues(DungeonCrawler.Ftr,Ftrlvl, 210, g2d);
                
                g2d.setColor(Color.blue);
                PrintLvlUpValues(DungeonCrawler.Blm,Blmlvl, 260, g2d);

                g2d.setColor(Color.GREEN);
                g2d.drawString("Level Up  *"+BattleHandler.HeroLevel+"*", 199, 49);
                g2d.setColor(Color.BLACK);
                g2d.drawString("Level Up  *"+BattleHandler.HeroLevel+"*", 200, 50);
                g2d.setColor(Color.BLACK);
                g2d.drawString("Press \"Z\" to Continue", 200, 360);
      }
      
      public int itemXGap = 30;
      public int itemYcenterd = -14;
      
      public void drawHeroItem(Graphics2D g2d, Hero hero){
          
           g2d.drawImage(ring1Image,hero.X+itemXGap*1,hero.Y+itemYcenterd, null); 
           g2d.drawImage(ammuletImage,hero.X+itemXGap*2,hero.Y+itemYcenterd*2, null); 
           g2d.drawImage(ring2Image,hero.X+itemXGap*3,hero.Y+itemYcenterd, null); 
      }       
      
          public void DrawCursor(Graphics2D g2d){
                Hero hero =null;
                
                if(CursorHero == 0){g2d.setColor(Color.red);            hero = DungeonCrawler.War;}
                if(CursorHero == 1){g2d.setColor(new Color(200,120,0)); hero = DungeonCrawler.Ftr;}
                if(CursorHero == 2){g2d.setColor(Color.BLUE);           hero = DungeonCrawler.Blm;}               
             
                int crsX = hero.X+itemXGap*(1+CursorItem);
                int crsY = hero.Y+itemYcenterd;
                
                if(CursorItem == 1)
                    crsY+=itemYcenterd;
                
                g2d.fillOval(crsX-4, crsY, 30, 30);             
         }
          
         public void PrintStatValues(Hero hero, int y, Graphics2D g2d) {
             int x=100;
        g2d.drawString(Integer.toString(hero.MaxHP) ,  x+200, y);
        g2d.drawString(Integer.toString(hero.Attack),  x+400, y);
        g2d.drawString(Integer.toString(hero.Defense), x+600, y);
        g2d.drawString(Integer.toString(hero.Magic),   x+800, y);

    }
          public void DrawOutLinedString(Graphics2D g2d, String s, int X, int Y, Color color){
               g2d.setColor(Color.BLACK);                
                g2d.drawString(s, X-1, Y-1);
                g2d.drawString(s, X+1, Y-1);
                g2d.drawString(s, X-1, Y+1);
                g2d.drawString(s, X+1, Y+1);
                g2d.setColor(color);
                g2d.drawString(s, X, Y);
          }
          int ItemX = 450;
          int ItemY = 320;
     
      public void DrawStatState(Graphics2D g2d){
         
          int screenWidth = 1100;
          int screenHeight = 500;
          drawBox(g2d,screenX,screenY,screenWidth,screenHeight);  
          drawStats(g2d,100);
                    
          DrawOutLinedString(g2d,"Level "+BattleHandler.HeroLevel,200,50,Color.GREEN);
          DrawOutLinedString(g2d,"Monster Lvl "+BattleHandler.MonsterLevel,800,50,Color.ORANGE);
                         
         g2d.setColor(Color.red);
         PrintStatValues(DungeonCrawler.War, 160, g2d);
         
         g2d.setColor(new Color(200,120,0));
         PrintStatValues(DungeonCrawler.Ftr, 210, g2d);
         
         g2d.setColor(Color.blue);
         PrintStatValues(DungeonCrawler.Blm, 260, g2d);
                
         DrawCursor(g2d);
         drawHeroItem(g2d,DungeonCrawler.War);
         drawHeroItem(g2d,DungeonCrawler.Ftr);
         drawHeroItem(g2d,DungeonCrawler.Blm);
         
         drawOldItem(g2d);
         drawDropItem(g2d); 
         drawInventoryItem(g2d);
         
         drawBox(g2d,screenX,screenY+screenHeight+10,1000,140);  
         g2d.drawString("Z: Equip", screenX+70, screenY+screenHeight+40);
         g2d.drawString("Arrow Keys: Move", screenX+70, screenY+screenHeight+80);
         g2d.drawString("X: Back", screenX+70, screenY+screenHeight+120);
         g2d.drawString("S: Save", screenX+570, screenY+screenHeight+80);        
         g2d.drawString("I: Move To Inventory", screenX+570, screenY+screenHeight+40);          
         g2d.drawString("H: Show HP", screenX+570, screenY+screenHeight+120);
         
      }
          
         Hero getHeroFromCursor(){
            Hero hero = null;
      
                if(CursorHero == 0)
                hero = DungeonCrawler.War;
                if(CursorHero == 1)
                hero = DungeonCrawler.Ftr;
                if(CursorHero == 2)
                hero = DungeonCrawler.Blm; 
                
                return hero;
         }
            public Item getItemFromCursor(){
                Item item = null;
                Hero hero=  null;
                
                if(CursorHero == 0)
                hero = DungeonCrawler.War;
                if(CursorHero == 1)
                hero = DungeonCrawler.Ftr;
                if(CursorHero == 2)
                hero = DungeonCrawler.Blm;                
                
                if(CursorItem == 0)
                    item = hero.LeftRing;
                if(CursorItem == 1)
                    item = hero.Ammy;
                if(CursorItem == 2)
                    item = hero.RightRing;
             
             return item;
         }
            
            public void drawItem(Graphics2D g2d,Item item,int x){
                if(!item.isEmpty){
                 if(item.isAmulet)                            
         g2d.drawImage(ammuletImage,x-30,ItemY+30, null);                 
                 else
         g2d.drawImage(ring1Image  ,x-30,ItemY+30, null);}                
                 
         DrawItemStats(item,g2d,x,ItemY);
            }
            
          int ItemscreenWidth = 300;
          int ItemscreenHeight = 200; 
          
          int OldItemX = 100;
         public void drawOldItem(Graphics2D g2d){
          int Sx = OldItemX-60;
          int Sy = ItemY-25;
               
          drawBox(g2d,Sx,Sy,ItemscreenWidth,ItemscreenHeight); 
          
         Item OldItem = getItemFromCursor();
                drawItem(g2d,OldItem,OldItemX);
     }
     
      public void drawDropItem(Graphics2D g2d){
          int Sx = ItemX-60;
          int Sy = ItemY-25;    
          drawBox(g2d,Sx,Sy,ItemscreenWidth,ItemscreenHeight);   
          
           drawItem(g2d,DropItem,ItemX);        
           g2d.drawString("Will Drop",ItemX,ItemY+155);
      }   
          int InvItemX = 800;
       public void drawInventoryItem(Graphics2D g2d){
          int Sx = InvItemX-60;
          int Sy = ItemY-25;   
          drawBox(g2d,Sx,Sy,ItemscreenWidth,ItemscreenHeight);   
          
           drawItem(g2d,InvItem,InvItemX);    
           g2d.drawString("Inventory",InvItemX,ItemY+155);
      } 
      public void DrawItemStats(Item item, Graphics2D g2d,int x, int y){          
      int yOffset = 30;
          yOffset +=  DrawStatOnItem(g2d,item.HP,    "HP:      ",x,y+yOffset);
          yOffset +=  DrawStatOnItem(g2d,item.Attack,"Attack:  ",x,y+yOffset);
          yOffset +=  DrawStatOnItem(g2d,item.Def,   "Defense: ",x,y+yOffset);
                      DrawStatOnItem(g2d,item.Magic, "Magic:   ",x,y+yOffset);
      }
     
      int DrawStatOnItem(Graphics2D g2d,int StatValue, String StatName,int x,int y){
           
          if(StatValue!=0){   
              g2d.drawString(StatName+Integer.toString(StatValue),x,y);
              return 30;            
            }
            return 0;             
      }
      
      public void SwapDropInv(){
      SwapStats(DropItem,InvItem);
      boolean isAmmy    = DropItem.isAmulet;
      DropItem.isAmulet = InvItem.isAmulet;
      InvItem.isAmulet  = isAmmy;
      InvItem.setEmpty();
      DropItem.setEmpty();
      }
   
}
