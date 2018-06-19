/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters.MonsterGroups;

import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.Characters.Monsters.*;
import dungeoncrawler.DungeonCrawler;


import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import java.util.Random;
/**
 *
 * @author Nick
 */
public class Ronins extends MonsterGroup{
    
  public  Ronins(){
        super();       
        
        MapImage = ImageHandler.Ronin;
               
        name = "A Ronin";
        
         
           monsterList.push(new Ronin(400,300,BattleHandler.MonsterLevel));
           
       drctTime = 50;
       moveTime = 50;
       restTime = 100;
       restClock = DungeonCrawler.RND.nextInt(restTime);
       Speed = 8;      
  }
    
}
