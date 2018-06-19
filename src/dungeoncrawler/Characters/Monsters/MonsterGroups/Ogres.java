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
public class Ogres extends MonsterGroup{
    
  public  Ogres(){
        super();       
        
        MapImage = ImageHandler.Ogre;
               
        name = "Ogres";
        
         
           monsterList.push(new OgreBrawler(450,320,BattleHandler.MonsterLevel));
           monsterList.push(new OgreShaman(500,160,BattleHandler.MonsterLevel));
       
       drctTime = 100;
       moveTime = 300;
       restTime = 400;
       restClock = DungeonCrawler.RND.nextInt(restTime);
       Speed = 2;
          
        
  }
    
}
