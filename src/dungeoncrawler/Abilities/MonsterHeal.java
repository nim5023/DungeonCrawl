/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities;

import dungeoncrawler.Abilities.Animations.HealAnimation;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Heros.Warrior;
import dungeoncrawler.Characters.Monster;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.StateHandlers.BattleHandler;

/**
 *
 * @author Nick
 */

public class MonsterHeal extends Ability{
    
    Monster User;
    public MonsterHeal(Monster self){
        super();
        User= self;
        name = "MonsterHeal";
    }
    
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
          
        
        int heal = BattleHandler.MonsterLevel*3;
        
        monster.HP += heal;
        if(monster.HP>monster.MaxHP )
            monster.HP = monster.MaxHP;
        
          DungeonCrawler.AnimationList.push(new HealAnimation(monster.X,monster.Y,30,70,monster.Stand));
        BattleHandler.addBattleText(User.name+" Heals "+ monster.name );
        BattleHandler.addBattleText(" "+ monster.name+ " gains "+heal +" Health" );
       
          
    }
    
}
