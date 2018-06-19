/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters.MonsterGroups;

import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.Characters.Monsters.Bear;
import dungeoncrawler.Characters.Monsters.OgreBrawler;
import dungeoncrawler.Characters.Monsters.OgreShaman;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;

/**
 *
 * @author Nick
 */
public class Bears extends MonsterGroup{
    
  public  Bears(){
        super();       
        
        MapImage = ImageHandler.Bear;
               
        name = "A Bear";
        
         
           monsterList.push(new Bear(450,320,BattleHandler.MonsterLevel));
       
       drctTime = 25;
       moveTime = 300;
       restTime = 300;
       restClock = DungeonCrawler.RND.nextInt(restTime);
       Speed = 2;
          
        
  }
    
}
