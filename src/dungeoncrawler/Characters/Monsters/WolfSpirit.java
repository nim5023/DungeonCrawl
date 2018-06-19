/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.Abilities.SpiritRush;
import dungeoncrawler.DungeonCrawler;

import dungeoncrawler.ImageHandler;

/**
 *
 * @author Nick
 */
public class WolfSpirit extends Monster {
    
      public WolfSpirit(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.SpirtWolf;
         name = "Ghost Wolf";
         
            abilityList.get(0).abilityChance = 0;
            abilityList.add(new SpiritRush());
            abilityList.get(1).abilityChance = 100;
            
         
    }
      
    @Override
      public void LevelUp(){
          super.LevelUp();
            MaxHP+=DungeonCrawler.RND.nextInt(5)+14;//16;
            HP = MaxHP;
            Magic+=3;
            Defense+=0;
            Attack-=1;
            Power+=2.45;
      }
    
}
