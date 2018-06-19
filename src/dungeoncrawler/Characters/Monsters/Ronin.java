/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;
import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Abilities.MonsterAttackHero;
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
public class Ronin extends Monster {
    int CounterDamage = 0;
      public Ronin(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.Ronin;
         name = "Ronin";
         abilityList.clear();
         abilityList.push(new RoninAttackHero());
         abilityList.get(0).abilityChance = 100;
    }
      
    @Override
      public void LevelUp(){
          super.LevelUp();
            MaxHP+=DungeonCrawler.RND.nextInt(5)+29; //31;
            HP = MaxHP;
            
            Attack+=1;
            Defense+=1;
            Magic+=1;
                
            Power+=6.4;
      }
    
    @Override
     public void TakeDamage(int Damage, Hero hero){
                   HP -= Damage;
        CounterDamage += Damage;
    }
      @Override
       public void UseAbility(MonsterGroup Allies){
          boolean canCounter = true;
          if(stunned||confused)
              canCounter = false;
          boolean isWeakened = weakened;
          
          super.UseAbility(Allies);
                    
          if(canCounter){
            CounterDamage = reducedDamage(CounterDamage,TargetedHero);  
            if(isWeakened)
            CounterDamage /=2;
            CounterDamage *=3;
         if(CounterDamage > 0)
          BattleHandler.addBattleText(name+" Unleashes "+CounterDamage+" Damage on "+ TargetedHero.name);   
          TargetedHero.TakeDamage(CounterDamage);
          CounterDamage =0;
          }
      }
    
      
public class RoninAttackHero extends MonsterAttackHero{
    
    public RoninAttackHero(){
        super();
        name = "Ronin Attack";
    }
    
    @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){        
                
     TargetedHero = DungeonCrawler.Ftr;
     if(DungeonCrawler.War.HP > DungeonCrawler.Ftr.HP)
     TargetedHero = DungeonCrawler.War;
     if(DungeonCrawler.Blm.HP > DungeonCrawler.War.HP && DungeonCrawler.Blm.HP > DungeonCrawler.Ftr.HP)         
     TargetedHero = DungeonCrawler.Blm;
     
     super.ActivateAbility(TargetedHero, Ronin.this, heroTarget);
   
    }
    
}
  
      
      
}
