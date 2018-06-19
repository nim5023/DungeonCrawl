/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler;

import dungeoncrawler.DungeonCrawler.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Play extends JApplet implements KeyListener {
    //Called when this applet is loaded into the browser.

    public Timer timer;
    public DungeonCrawler DC;
    public static JApplet applet;
    public static int sizeX, sizeY;
    ActionListener Looper = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            DC.GameLoop();
            requestFocus();
        }
    };

    @Override
    public void init() {
        sizeX = 1280;
        sizeY = 720;
        this.setSize(sizeX, sizeY);
        applet = this;
        DC = new DungeonCrawler();
        // DC.setFocusable(true);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();


        timer = new Timer(1000 / 60, Looper);
        timer.setRepeats(true);
        timer.start();

        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    createGUI();
                }
            });
        } catch (InterruptedException | InvocationTargetException e) {
            System.err.println("createGUI didn't complete successfully");
        }

    }

    private void createGUI() {
        //Create and set up the content pane.

        DC.setEnabled(true);
        DC.setOpaque(true);
        setContentPane(DC);
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'y') {
            DungeonCrawler.UpPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 'h') {
            DungeonCrawler.DownPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'j') {
            DungeonCrawler.RightPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'g') {
            DungeonCrawler.LeftPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'y') {
            DC.UpButton();
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 'h') {
            DC.DownButton();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'j') {
            DC.RightButton();
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'g') {
            DC.LeftButton();
        }

        if (e.getKeyChar() == 'r') {
            if (DungeonCrawler.GameState == State.ROLL) {
                DungeonCrawler.RollHand.ReRollHeroInits();
            }
        }

        if (e.getKeyChar() == 's') {
            if (DungeonCrawler.GameState == State.STATS) {
                DungeonCrawler.LoadSaveHand.SaveFile();
            }
        }

        if (e.getKeyChar() == 'l') {
            if (DungeonCrawler.GameState == State.ROLL) {
                DungeonCrawler.LoadSaveHand.LoadFile();
                DC.openingSaved = true;
                DC.ConfirmButton();
            }
        }

        if (e.getKeyChar() == 'i') {
            if (DungeonCrawler.GameState == State.STATS) {
                DungeonCrawler.RewardHand.SwapDropInv();
            }
        }

        if (e.getKeyChar() == 'h') {
            DungeonCrawler.showHP = !DungeonCrawler.showHP;
        }

        if (e.getKeyChar() == 'z') {
            DC.ConfirmButton();
        }

        if (e.getKeyChar() == 'x') {
            DC.BackButton();
        }
    }
}