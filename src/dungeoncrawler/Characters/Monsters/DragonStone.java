/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;
import dungeoncrawler.Abilities.DragonBreath;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.Characters.Monsters.Dragon;
import dungeoncrawler.ImageHandler;
import dungeoncrawler.StateHandlers.BattleHandler;

/**
 *
 * @author Nick
 */
public class DragonStone extends Dragon{
    
    public DragonStone(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.StoneDragon;
         name = "Stone Dragon";
         AttackTurns = 4;   
         FuryOnStun = false;
         Strength = 2.0;
    }  
     @Override
      public void LevelUp(){
        MaxHP+=55;
        HP = MaxHP;           
            
        Magic+=3;
        Defense+=5;
        Attack+=3;
        
        Power+=9.37;
      }    
    
}
