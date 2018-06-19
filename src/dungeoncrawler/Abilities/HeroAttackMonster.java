/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities;

import dungeoncrawler.Abilities.Animations.AttackAnimation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Heros.Fighter;
import dungeoncrawler.Characters.Heros.Mage;
import dungeoncrawler.Characters.Heros.Warrior;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.StateHandlers.BattleHandler;

/**
 *
 * @author Nick
 */
public class HeroAttackMonster extends Ability{
    
    public HeroAttackMonster(){
        super();
        name = "Attack";
    }
    
    
    @Override
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
     
        hero.Defending = false;
        
        if(hero.getClass() == Fighter.class){         
           DungeonCrawler.Ftr.Rage++;}
        
         if(hero.getClass() == Warrior.class){       
           DungeonCrawler.War.CoolDown();
         DungeonCrawler.War.useMagicSetAbility= false;}
        
        double ratio = Math.pow(hero.Attack,2) / (Math.pow(hero.Attack,2) + Math.pow(monster.Defense,2));
        int damage = (int)(ratio * hero.Power);
        
          if(hero.getClass() == Mage.class){          
          if(DungeonCrawler.Blm.imbued){
              DungeonCrawler.Blm.imbued = false;
              damage *=(3.0);
           }}
        
         DungeonCrawler.AnimationList.push(new AttackAnimation(monster.X,monster.Y,false,20));
          
        BattleHandler.addBattleText(hero.name+" Attacks "+ monster.name+ " for "+damage+" Damage");
          monster.TakeDamage(damage, hero);
        
    }
    
}
