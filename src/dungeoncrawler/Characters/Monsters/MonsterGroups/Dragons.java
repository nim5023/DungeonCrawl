/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters.MonsterGroups;

import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.Characters.Monsters.*;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;

/**
 *
 * @author Nick
 */
public class Dragons extends MonsterGroup{
public Dragons(int x, int y){      
super();       

if((BattleHandler.MonsterLevel+2)%3 == 0){
        MapImage = ImageHandler.StoneDragon;
        name = "A Stone Dragon"; 
        monsterList.push(new DragonStone(440,250,BattleHandler.MonsterLevel)); 
}
else if((BattleHandler.MonsterLevel+2)%2 == 0){
        MapImage = ImageHandler.RedDragon;
        name = "A Red Dragon"; 
        monsterList.push(new DragonMage(440,250,BattleHandler.MonsterLevel)); 
}
else{
        MapImage = ImageHandler.Dragon;
        name = "A Dragon"; 
        monsterList.push(new Dragon(440,250,BattleHandler.MonsterLevel));    
}
        X = x;
        Y = y;
       moveTime = 0;
       restTime = 100;
       Speed = 0;
           
           
  }

    @Override
  public void LevelUp(int lvl){
        super.LevelUp(lvl);            
            
        monsterList.get(0).Magic+=1;
        monsterList.get(0).Defense+=1;
        monsterList.get(0).Attack+=1;  
       
  }
}
