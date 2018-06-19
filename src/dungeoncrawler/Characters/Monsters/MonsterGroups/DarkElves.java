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
public class DarkElves extends MonsterGroup{
    
  public  DarkElves(){
        super();       
        
        MapImage = ImageHandler.ElfMage;
               
        name = "Dark Elves";
        
         
           monsterList.push(new ElfWarrior(400,320,BattleHandler.MonsterLevel));
           monsterList.push(new ElfMage(500,220,BattleHandler.MonsterLevel));
           monsterList.push(new ElfWarrior(400,120,BattleHandler.MonsterLevel));
           
       drctTime = 25;
       moveTime = 50;
       restTime = 20;
       restClock = DungeonCrawler.RND.nextInt(restTime);
       Speed = 2;      
  }
    
}
