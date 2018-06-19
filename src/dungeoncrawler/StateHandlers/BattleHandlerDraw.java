/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.StateHandlers;

import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Heros.Mage;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.StringTokenizer;

/**
 *
 * @author Nick
 */
public class BattleHandlerDraw {
    
         int spaceWidth = 25;
         int SizeofMenu = 200;
         
         int FtrMenuX = spaceWidth;
         int WarMenuX = SizeofMenu+FtrMenuX+spaceWidth;         
         int BlmMenuX = SizeofMenu+WarMenuX+spaceWidth;
         
         int HeroMenuY = 450;
    
     public void drawBattleSceneFromDrawer(Graphics2D g2d){
                 
         for(int i = 0; i < BattleHandler.Enemies.monsterList.size();i++)
               BattleHandler.Enemies.monsterList.get(i).Draw(g2d);
          
       DrawHeroMenu(g2d,DungeonCrawler.Ftr,FtrMenuX,HeroMenuY, (BattleHandler.cursor.selectHero == 1)? true : false );
       DrawHeroMenu(g2d,DungeonCrawler.War,WarMenuX,HeroMenuY, (BattleHandler.cursor.selectHero == 2)? true : false);
       DrawHeroMenu(g2d,DungeonCrawler.Blm,BlmMenuX,HeroMenuY, (BattleHandler.cursor.selectHero == 3)? true : false);
       
       g2d.setColor(Color.BLUE);
        g2d.drawString(Integer.toString(DungeonCrawler.Blm.Mana), BlmMenuX+180, HeroMenuY+spaceWidth*2);
       
      g2d.setColor(new Color(200,120,0));
       g2d.drawString(Integer.toString(DungeonCrawler.Ftr.Rage), FtrMenuX+180, HeroMenuY+spaceWidth*2);
       
       g2d.setColor(Color.RED);
        g2d.drawString(Integer.toString(DungeonCrawler.War.TauntHealCD), WarMenuX+145, HeroMenuY+(int)(spaceWidth*3.5));
        g2d.drawString(Integer.toString(DungeonCrawler.War.SlashBashCD), WarMenuX+145, HeroMenuY+(int)(spaceWidth*5.5));
      
        
         
         int screenX = 600;
         int screenY = 100;         
         int textY = screenY + spaceWidth;
         int textX = screenX + spaceWidth;
         
              if(BattleHandler.cursor.Cursorstate == BattleHandler.BattleState.battleText){
         g2d.setColor(Color.RED);
         g2d.fillRect(screenX-3, screenY-3, 471, (spaceWidth*11)+6);
              }
         g2d.setColor(Color.BLACK);
         g2d.fillRect(screenX-1, screenY-1, 467, (spaceWidth*11)+2);
         g2d.setColor(Color.WHITE);
         g2d.fillRect(screenX, screenY, 465, spaceWidth*11);         
         g2d.setColor(Color.BLACK);
         g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));        
         int startingText = BattleHandler.BattleText.size()-1-BattleHandler.TextSlider;
         for(int i = startingText; i >= 0 && i > startingText-10;i--){
             DrawBattleText(g2d,BattleHandler.BattleText.get(i),textX,textY);
             textY +=spaceWidth;
         }
         if( BattleHandler.Enemies.monsterList.size()>0)
         DrawCursor(g2d);
    }
        
        public void DrawBattleText(Graphics2D g2d,String text,int x, int y){
             StringTokenizer st = new StringTokenizer(text);
             
             if(' ' == text.charAt(0))
                 x+=20;
             while (st.hasMoreTokens()) {
                  g2d.setColor(Color.BLACK);  
                 String DrawMe = st.nextToken();
                 if("Mage".equals(DrawMe))
                      g2d.setColor(Color.BLUE);
                 if("Fighter".equals(DrawMe))
                      g2d.setColor(new Color(200,120,0));
                 if("Warrior".equals(DrawMe))
                      g2d.setColor(Color.RED);
                 g2d.drawString(DrawMe,x,y);    
                 x+= (DrawMe.length()+1)*10;
             }
            
          
                
        }
    
    public void DrawHeroMenu(Graphics2D g2d,Hero hero, int x, int y, boolean selected){
         g2d.setColor(Color.RED);
        if(selected) 
            if(!(BattleHandler.cursor.Cursorstate == BattleHandler.BattleState.battleText))
         g2d.fillRect(x-3, y-3, SizeofMenu+6, (spaceWidth*8)+6);
        
         g2d.setColor(Color.BLACK);
         g2d.fillRect(x-1, y-1, SizeofMenu+2, (spaceWidth*8)+2);
         g2d.setColor(Color.WHITE);
         if(hero.turnDone)
               g2d.setColor(Color.DARK_GRAY);
         g2d.fillRect(x, y, SizeofMenu, spaceWidth*8);
         
         g2d.setColor(Color.BLACK);
         g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
         g2d.drawString(hero.name, x+spaceWidth, y+spaceWidth);
         
         g2d.drawString(Integer.toString(hero.HP)+"/"+Integer.toString(hero.MaxHP),x+110,y+spaceWidth);
         
         for(int i = 0; i < hero.abilityList.size() ; i++){
             g2d.setColor(Color.LIGHT_GRAY);
             if(hero.abilityList.get(i).Usable)
                  g2d.setColor(Color.BLACK);
         g2d.drawString(hero.abilityList.get(i).name, x+spaceWidth*2, y+spaceWidth*(i+2));
         }
         
         if (hero.getClass() == Mage.class)
             if(DungeonCrawler.Blm.imbued){                 
                  g2d.setColor(Color.BLUE);
         g2d.drawString(hero.abilityList.get(3).name, x+spaceWidth*2, y+spaceWidth*(2+3));
             }
    }
    
     static int bounceCursor = 0;
          
     public Hero getHeroFromCursor(int heroCursor){
         if(heroCursor == 1)
             return DungeonCrawler.Ftr;
         if(heroCursor == 2)
             return DungeonCrawler.War;
         if(heroCursor == 3)
             return DungeonCrawler.Blm;
         return null;
     }
    public void DrawCursor(Graphics2D g2d){
        
        if( bounceCursor > 50)
            bounceCursor = 0;
            bounceCursor++;
           
            
        if(BattleHandler.cursor.Cursorstate == BattleHandler.BattleState.Monster || BattleHandler.cursor.Cursorstate == BattleHandler.BattleState.Hero){
  
        AffineTransform trans = new AffineTransform();

        double pulsate = ((double)Math.abs(bounceCursor-25)/10.0+5.0)/7.0;
        double halftargetWidth = BattleHandler.target.getWidth(null)/2.0;
        double halftargetHieght = BattleHandler.target.getHeight(null)/2.0;
       if(BattleHandler.cursor.Cursorstate == BattleHandler.BattleState.Monster)
        trans.translate(BattleHandler.Enemies.monsterList.get(BattleHandler.cursor.selectMonster).X - halftargetWidth - (halftargetWidth*(pulsate-1)),
                        BattleHandler.Enemies.monsterList.get(BattleHandler.cursor.selectMonster).Y - halftargetHieght - (halftargetHieght*(pulsate-1)));    
       if(BattleHandler.cursor.Cursorstate == BattleHandler.BattleState.Hero)
        trans.translate(getHeroFromCursor(BattleHandler.cursor.selectHeroAsTarget).X - halftargetWidth - (halftargetWidth*(pulsate-1)),
                       getHeroFromCursor(BattleHandler.cursor.selectHeroAsTarget).Y - halftargetHieght - (halftargetHieght*(pulsate-1)));       
        trans.scale(pulsate,pulsate);
            
        g2d.drawImage(BattleHandler.target,trans,null);
        }
        
        if(BattleHandler.cursor.Cursorstate == BattleHandler.BattleState.Ability){
             int heroX = 0;
            if(BattleHandler.cursor.selectHero == 1)
           heroX = FtrMenuX;
            if(BattleHandler.cursor.selectHero == 2)
                heroX = WarMenuX;
            if(BattleHandler.cursor.selectHero == 3)
                heroX = BlmMenuX;
                                     
            g2d.drawImage(ImageHandler.Cursor,heroX + bounceCursor/10+spaceWidth, HeroMenuY + 8+ BattleHandler.cursor.selectAbility*spaceWidth, null);            
        }        
    }
    
    
    
    
    
}
