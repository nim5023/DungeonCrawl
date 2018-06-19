/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Abilities.Animations;

import java.awt.Graphics2D;

/**
 *
 * @author Nick
 */
public abstract class Animation {
    
    public int TIMER = 10;
    
    public abstract void Animate(Graphics2D g);
     public abstract void ContinueAnimation();
}
