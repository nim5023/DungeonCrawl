/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters;

import dungeoncrawler.Characters.Monster;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.Play;
import java.awt.AlphaComposite;
import java.util.LinkedList;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Nick
 */
public class MonsterGroup {

    public LinkedList<Monster> monsterList;
    public Image MapImage;
    public int X, Y, level;
    public String name;

    public MonsterGroup() {

        monsterList = new LinkedList<>();

    }

    public void CreateNewGroup() {

        X = 300 + DungeonCrawler.RND.nextInt(500);
        Y = 300 + DungeonCrawler.RND.nextInt(300);

    }

    public void LevelUp(int lvl) {
        for (int i = 0; i < lvl; i++) {
            for (int mons = 0; mons < monsterList.size(); mons++) {
                monsterList.get(mons).LevelUp();
            }
        }
    }
    protected int drctTime = 20;
    protected int moveTime = 10;
    protected int restTime = 50;
    protected double Speed = 10;
    int drctClock = 0;
    int moveClock = 0;
    public int restClock = 0;
    boolean isMoving = false;
    boolean AttackMoving = false;

    public void Move() {
        if (!TryAttackHeros()) {
            if (isMoving) {

                moveClock++;
                if (moveClock > moveTime) {
                    moveClock = 0;
                    isMoving = false;
                }

                drctClock++;
                if (drctClock > drctTime) {
                    drctClock = 0;
                    changeDirection();
                }

                Move1Space();
            }
            if (!isMoving) {
                restClock++;
                if (restClock > restTime) {
                    restClock = 0;
                    isMoving = true;
                }
            }
        } else {
            Move1Space();
        }

    }
    double Theta = 0;

    public boolean TryAttackHeros() {

        double thetaY = Y - DungeonCrawler.War.Y;
        double thetaX = DungeonCrawler.War.X - X;

        double attackDist = 175;
        if (Math.sqrt(Math.pow(thetaX, 2) + Math.pow(thetaY, 2)) < attackDist) {

            double attackTheta = Math.atan(thetaY / thetaX);

            if (thetaX < 0) {
                attackTheta += Math.PI;
            }

            if (thetaY == 0) {
                if (thetaX == 0) {
                    attackTheta = 0;
                }
            }
            Theta = attackTheta;
            return true;
        } else {
            return false;
        }

    }

    public void changeDirection() {
        int RNDdrct = DungeonCrawler.RND.nextInt(360);
        RNDdrct *= Math.PI / 180.0;

        Theta = RNDdrct;
    }
    boolean faceRight = false;

    public void Move1Space() {


        int deltaX = (int) Math.round(Math.cos(Theta) * Speed);
        int deltaY = -(int) Math.round(Math.sin(Theta) * Speed);


        if (X < Play.sizeX && deltaX > 0) {
            X += deltaX;
            faceRight = true;
        }

        if (X > 0 && deltaX <= 0) {
            X += deltaX;
            faceRight = false;
        }

        if (Y > 0 && deltaY <= 0) {
            Y += deltaY;
        }

        if (Y < Play.sizeY && deltaY > 0) {
            Y += deltaY;
        }

    }

    public void Draw(Graphics2D g) {

        AffineTransform trans = new AffineTransform();
        trans.translate(X - (MapImage.getWidth(null) / 2), Y - (MapImage.getHeight(null) / 2));
        if (faceRight) {
            trans.scale(-1, 1);
            trans.translate(-(MapImage.getWidth(null)), 0);
        }
        g.drawImage(MapImage, trans, null);


    }
}
