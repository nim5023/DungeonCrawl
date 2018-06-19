/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters.MonsterGroups;

import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.Characters.Monsters.*;


import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;
import java.util.Random;
/**
 *
 * @author Nick
 */
public class Wraiths extends MonsterGroup{
    
  public  Wraiths(){
        super();       
        
        MapImage = ImageHandler.Wraith;
               
        name = "Wraiths";
        
         
           monsterList.push(new Wraith(450,325,BattleHandler.MonsterLevel));
           monsterList.push(new Wraith(475,180,BattleHandler.MonsterLevel));
           
       drctTime = 150;      
       moveTime = 10;
       restTime = 0;
       Speed = 1.4;   
        
  }
  
  
    
}
