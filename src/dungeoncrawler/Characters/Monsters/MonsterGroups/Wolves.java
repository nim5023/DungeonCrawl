/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters.MonsterGroups;

import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.Characters.Monsters.WolfCorporeal;
import dungeoncrawler.Characters.Monsters.WolfSpirit;
import dungeoncrawler.DungeonCrawler;


import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import java.util.Random;
/**
 *
 * @author Nick
 */
public class Wolves extends MonsterGroup{
    
  public  Wolves(){
        super();       
        
        MapImage = ImageHandler.Wolf;
               
        name = "Wolves";
        
         
           monsterList.push(new WolfCorporeal(500,400,BattleHandler.MonsterLevel));
           monsterList.push(new WolfSpirit(520,300,BattleHandler.MonsterLevel));
           monsterList.push(new WolfSpirit(520,200,BattleHandler.MonsterLevel));
           monsterList.push(new WolfCorporeal(500,100,BattleHandler.MonsterLevel));
           
       drctTime = 14;
       moveTime = 15;
       restTime = 50;
       restClock = DungeonCrawler.RND.nextInt(restTime);
       Speed = 5;      
  }
    
}
