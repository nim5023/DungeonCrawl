/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities;

import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.Monster;
import java.util.LinkedList;

/**
 *
 * @author Nick
 */
public class Ability {
    
    public String name;
    public boolean Usable;
    public int abilityChance;
    public boolean targetMonster;
    public boolean targetHero;
    public boolean noTarget;
    
    public void ActivateAbility(Hero hero, Monster monster,Hero heroTarget){
        
    }
    
    public Ability(){
        Usable = true;
        
        targetMonster = false;
        targetHero = false;
        noTarget = false;
    }
    
}
