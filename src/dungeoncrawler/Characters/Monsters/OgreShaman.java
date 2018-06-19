/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.Abilities.*;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.DungeonCrawler;

import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;

/**
 *
 * @author Nick
 */
public class OgreShaman extends Monster {
    
      public OgreShaman(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.OgreShaman;
         name = "Ogre Shaman";
         
            abilityList.get(0).abilityChance = 0;
            abilityList.add(new MonsterHeal(this));
            abilityList.get(1).abilityChance = 100;            
            abilityList.add(new SpiritRush());
            abilityList.get(2).abilityChance = 0;
         
    }
      public boolean healTarget(MonsterGroup Allies,int target){
           if(Allies.monsterList.get(target).MaxHP > Allies.monsterList.get(target).HP){
                  abilityList.get(1).ActivateAbility(null, Allies.monsterList.get(target),null);  // heal             
           return true;}
           
           return false;
      }
      
    @Override
       public void UseAbility(MonsterGroup Allies){
          if(Allies.monsterList.size()==2)
          if(!(Allies.monsterList.get(1).MaxHP == Allies.monsterList.get(1).HP)||
             !(Allies.monsterList.get(0).MaxHP == Allies.monsterList.get(0).HP))
          if(!taunted && !confused &&!stunned){
              abilityList.get(1).abilityChance=100;
              abilityList.get(2).abilityChance = 0;
           
              if(healTarget(Allies,1))
              return; 
              
            healTarget(Allies,0);             
               return;
          }
         
               abilityList.get(1).abilityChance=0;
               abilityList.get(2).abilityChance=100;
               super.UseAbility(Allies);            
          
          
       }
      
    @Override
      public void LevelUp(){
          super.LevelUp();
            MaxHP+=DungeonCrawler.RND.nextInt(5)+10; //12;
            HP = MaxHP;
            Magic+=3;
            Attack+=0;
            Defense+=1;
            Power+=2;
      }
    
}
