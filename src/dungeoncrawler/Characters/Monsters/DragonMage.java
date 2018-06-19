/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters.Monsters;
import dungeoncrawler.Abilities.DragonBreath;
import dungeoncrawler.Characters.Monsters.Dragon;
import dungeoncrawler.ImageHandler;

/**
 *
 * @author Nick
 */
public class DragonMage extends Dragon{
    
    public DragonMage(int x, int y, int lvl){
        super( x,  y,  lvl);
         Stand = ImageHandler.RedDragon;
         name = "Red Dragon";
         AttackTurns = 2;         
    }  
     @Override
      public void LevelUp(){
        MaxHP+=47;
        HP = MaxHP;           
            
        Magic+=5;
        Defense+=3;
        Attack+=3;
        
        Power+=5.6;
      }
    
}
