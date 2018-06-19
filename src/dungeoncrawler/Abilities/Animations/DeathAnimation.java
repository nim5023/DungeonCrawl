/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities.Animations;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Characters.MonsterGroup;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.Play;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick
 */
public class DeathAnimation extends Animation{
MonsterGroup Enemy;
    
    public DeathAnimation(MonsterGroup MG){
        Enemy = MG;
        TIMER = 100;
    }

    
        @Override
    public void Animate(Graphics2D g) {
        try {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Play.sizeX, Play.sizeY);
            for(int mg = 0;mg < Enemy.monsterList.size(); mg++)
            Enemy.monsterList.get(mg).Draw(g);
            
            DungeonCrawler.Ftr.Draw(g);
            DungeonCrawler.War.Draw(g);
            DungeonCrawler.Blm.Draw(g);
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(DeathAnimation.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @Override
    public void ContinueAnimation() {
         TIMER--;
    }
    
}
