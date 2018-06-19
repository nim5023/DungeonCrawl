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
public class ElfWarrior extends Monster {
    
      public ElfWarrior(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.ElfWarrior;
         name = "Dark Elf";
         abilityList.get(0).abilityChance = 100;
    }
      
    @Override
      public void LevelUp(){
          super.LevelUp();
            MaxHP+=DungeonCrawler.RND.nextInt(5)+8; //10;
            HP = MaxHP;
            
            Magic +=1;
            Attack+=1;
            Defense+=2;
                
            Power+=1.86;
      }
    
}
