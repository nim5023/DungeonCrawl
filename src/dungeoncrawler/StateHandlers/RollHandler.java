/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.StateHandlers;

import dungeoncrawler.Characters.Hero;
import dungeoncrawler.DungeonCrawler;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Nick
 */
public class RollHandler {
    
    public RollHandler(){
          
      }
    
    public static void PlaceHerosByStats(){
     
          DungeonCrawler.War.X = 170;
          DungeonCrawler.Blm.X = 170;
          DungeonCrawler.Ftr.X = 170;
          
          DungeonCrawler.War.Y = 150;          
          DungeonCrawler.Ftr.Y = 200;
          DungeonCrawler.Blm.Y = 250;
          
    }
    
      public static void PrintValues(Hero hero, int Ypixel, Graphics2D g2d) {

        g2d.drawString(Integer.toString(hero.MaxHP), 200, Ypixel);
        g2d.drawString(Integer.toString(hero.Attack), 400, Ypixel);
        g2d.drawString(Integer.toString(hero.Defense), 600, Ypixel);
        g2d.drawString(Integer.toString(hero.Magic), 800, Ypixel);

    }
      
      
      
       public void ReRollHeroInits() {
           BattleHandler.HeroLevel    = 3;
           BattleHandler.MonsterLevel = BattleHandler.HeroLevel;
           
            DungeonCrawler.War.ResetInits();
            DungeonCrawler.Blm.ResetInits();
            DungeonCrawler.Ftr.ResetInits();
        
    }
      
      
      public void drawRollState(Graphics2D g2d){
          
          int screenX = 5;
          int screenY = 20;
          
         g2d.setColor(Color.BLACK);
         g2d.fillRect(screenX-1, screenY-1, 897, 372);
         g2d.setColor(Color.WHITE);
         g2d.fillRect(screenX, screenY, 895, 370); 

                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
                g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
                g2d.drawString("HP", 200, 110);
                g2d.drawString("ATTACK", 400, 110);
                g2d.drawString("DEFENSE", 600, 110);
                g2d.drawString("MAGIC", 800, 110);

                g2d.setColor(Color.red);
                g2d.drawString("Warrior", 10, 160);                
                PrintValues(DungeonCrawler.War, 160, g2d);
                
                
                g2d.setColor(new Color(200,120,0));
                g2d.drawString("Fighter", 10, 210);                
                PrintValues(DungeonCrawler.Ftr, 210, g2d);
                
                g2d.setColor(Color.blue);
                g2d.drawString("Mage", 10, 260);
                PrintValues(DungeonCrawler.Blm, 260, g2d);

                g2d.setColor(Color.BLACK);
                g2d.drawString("R: Re~Roll", 200, 50);
                g2d.drawString("Z: Continue", 200, 330);
                g2d.drawString("L: Load", 200, 370);
      }
    
}
