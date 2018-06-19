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

/**
 *
 * @author Nick
 */
public class MonsterAttackHero extends Ability{
    
    public MonsterAttackHero(){
        super();
        name = "Attack";
    }
    
    @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){        
            
        double ratio = Math.pow(monster.Attack,2) / (Math.pow(monster.Attack,2) + Math.pow(hero.Defense,2));
        int damage = (int)(ratio * monster.Power);
         damage = monster.reducedDamage(damage,hero);      
          BattleHandler.addBattleText(monster.name+" Attacks "+ hero.name+" for "+damage+" Damage");   
          
         DungeonCrawler.AnimationList.push(new AttackAnimation(hero.X,hero.Y,true,40));
             hero.TakeDamage( damage);
    }
    
}
