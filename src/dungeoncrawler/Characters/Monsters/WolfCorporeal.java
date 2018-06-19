/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.DungeonCrawler;

import dungeoncrawler.ImageHandler;

/**
 *
 * @author Nick
 */
public class WolfCorporeal extends Monster {
    
      public WolfCorporeal(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.Wolf;
         name = "Wolf";
         abilityList.get(0).abilityChance = 100;
    }
      
    @Override
      public void LevelUp(){
          super.LevelUp();
            MaxHP+=DungeonCrawler.RND.nextInt(5)+5;//7;
            HP = MaxHP;
            
            Attack+=1;
            Defense+=2;
                
            Power+=2.16;
      }
    
}
