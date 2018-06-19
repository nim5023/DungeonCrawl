/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities;

import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.StateHandlers.BattleHandler;

 public class Rake extends Ability{
    
     public Rake(){
        super();
        name = "Rake";
    }
     
        public void RakeHero( Hero hero,Monster monster){
         if(hero.isAlive){
        double ratio = Math.pow(monster.Attack,2) / (Math.pow(monster.Attack,2) + Math.pow(hero.Defense,2));
        int damage = (int)(ratio * monster.Power/3.0);
        damage = monster.reducedDamage(damage,hero);
       
             DungeonCrawler.AnimationList.push(new AttackAnimation(hero.X,hero.Y,true,50));
          BattleHandler.addBattleText(" "+hero.name+" takes "+damage+" Damage"); 
             hero.TakeDamage( damage);
         }
     }
    
    @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){     
         int numALive = 0;
          if(DungeonCrawler.War.isAlive)
       numALive++;
          if(DungeonCrawler.Blm.isAlive)
       numALive++; 
          if(DungeonCrawler.Ftr.isAlive)
       numALive++;
          if(numALive == 3){
          BattleHandler.addBattleText(monster.name+" uses Rake");
          RakeHero(DungeonCrawler.Ftr,monster);
          RakeHero(DungeonCrawler.War,monster);
          RakeHero(DungeonCrawler.Blm,monster);
          }
          else
              new MonsterAttackHero().ActivateAbility(hero, monster, heroTarget);
         
          
          }
    }
