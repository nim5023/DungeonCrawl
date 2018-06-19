/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;

import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Abilities.Animations.MissileAnimation;
import dungeoncrawler.Abilities.Rake;
import dungeoncrawler.Abilities.SpiritRush;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;

/**
 *
 * @author Nick
 */
public class Bear extends Monster {
    
    public Bear(int x, int y, int lvl){
        super( x,  y,  lvl);
        Stand = ImageHandler.Bear;
        name = "Bear";
         
        abilityList.get(0).abilityChance = 0;
        abilityList.add(new Rake());
        abilityList.get(1).abilityChance = 90;
    }
    
    @Override  
    public void UseAbility(MonsterGroup Allies){
        if(confused){
             BattleHandler.addBattleText(" " +this.name+" is Immune to Confusion ");
             confused = false;            
        }
        super.UseAbility(Allies);
    }
      
    @Override
      public void LevelUp(){
          super.LevelUp();
            MaxHP+=DungeonCrawler.RND.nextInt(5)+25; // 27;
            HP = MaxHP;
            Magic+=1;
            Defense+=3;
            Attack+=1;
            Power+=16.3;
      }
    
       
}
