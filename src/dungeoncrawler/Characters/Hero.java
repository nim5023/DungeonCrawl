/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Characters;

import dungeoncrawler.Abilities.Ability;
import dungeoncrawler.Abilities.HeroAttackMonster;
import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.DungeonCrawler.State;
import dungeoncrawler.Items.Item;
import dungeoncrawler.StateHandlers.BattleHandler;
import dungeoncrawler.StateHandlers.WorldHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Nick
 */
public class Hero {

    public Image Downed;
    public Image Stand;
    public Image StandLeg;
    public Image StandChest;
    public Image StandLegChest;
    public Image RightStep;
    public Image LeftStep;
    public Image Defends;
    public int X;
    public int Y;
    public String name;
    public int timer;
    public int breathImg;
    public int moveImg;
    
    public int AttackAnimateTimer;
    public boolean AttackAnimate;
    public boolean GoBackAnimate;
    
    public boolean moving;
    public boolean faceLeft;
    
    public boolean turnDone;
    
    public LinkedList<Ability> abilityList;
    
    
     Random rnd;
    
  public int MaxHP,HP,Magic,Defense,Attack, Power;
  public boolean isAlive;
  public boolean Defending;
 
  public Item LeftRing;
  public Item RightRing;
  public Item Ammy;
  
   public void SetAbilityUsable(){
        
    }
   public void TakeDamage(int damage){
       HP -= damage;              
   }

    public Hero(Random GlobalRND,
            Image D, Image S, Image SL, Image SC, Image SLC, Image LS, Image RS, Image Def)
    {
        LeftRing =  new Item(false,0,0,0,0);
        RightRing = new Item(false,0,0,0,0);
        Ammy =      new Item(true, 0,0,0,0);
        
        abilityList = new LinkedList<>();
        abilityList.push( new HeroAttackMonster() );
        
        rnd = GlobalRND;
        isAlive = true;
        Defending = false;

        ResetInits();
        
        timer = rnd.nextInt(10);

        breathImg = timer % 4;
        moveImg = 0;

        moving = false;
        faceLeft = false;
        turnDone = false;
        
        AttackAnimate = false;
        GoBackAnimate = false;
        AttackAnimateTimer = 0;

        Downed = D;

        Stand = S;
        StandLeg = SL;
        StandChest = SC;
        StandLegChest = SLC;

        RightStep = RS;
        LeftStep = LS;
        Defends = Def;
    }
    public void InitialEquipItem(Item item){
        MaxHP+=item.HP;
        HP=MaxHP;
        
        Attack += item.Attack;
        Defense += item.Def;
        Magic += item.Magic;
        item.setEmpty();
    }
    public void InitialEquip(){
           
            InitialEquipItem(LeftRing);
            InitialEquipItem(RightRing);
            InitialEquipItem(Ammy);
            
    }
    
    public void InitializeAnimations(){
        AttackAnimate = false;
        GoBackAnimate = false;
        AttackAnimateTimer = 0;
    }
    public void AnimateAttacks(){
        
        if(AttackAnimate){
             if(GoBackAnimate){
                 AttackAnimateTimer--;
                this.X--;
                if(AttackAnimateTimer  ==0){
                    GoBackAnimate = false; 
                    AttackAnimate = false;
                }
             }
                 
            if(!GoBackAnimate){
                AttackAnimateTimer++;
                this.X++;
                if(AttackAnimateTimer > 10)
                    GoBackAnimate = true;                
            }
           
        }
        
    }
    public boolean StatsOverLevel(){
        
        if(Magic   <5)
            return false;
        if(Attack  <5)
            return false;
        if(Defense <5)
            return false;
        if(HP < 28)
            return false;
        return true;
    }
    public void SetStatsToZero(){
        MaxHP   = 0;
        HP      = 0;
        Magic   = 0;
        Defense = 0;
        Attack  = 0;
        Power   = 0;  
    }
    public final void ResetInits(){
       SetStatsToZero();    
        
        while(!StatsOverLevel()){
            SetStatsToZero();
        for(int i =0; i <BattleHandler.HeroLevel;i++)
            levelUp();
        }
               
    }
    
    public void levelUp(){
                      
        MaxHP  +=rnd.nextInt(5)+8;
        HP = MaxHP;
        Magic  +=rnd.nextInt(3)+1;
        Defense+=rnd.nextInt(3)+1;
        Attack +=rnd.nextInt(3)+1;
        Power  +=2;
    }
    
    
    
    public void Move(LinkedList<WorldHandler.MoveDirection> MovementQueue, WorldHandler.MoveDirection index) {

        moving = false;
       
        if(index.X !=0 || index.Y != 0)
            moving = true;
        
        if(index.X < 0)
            faceLeft = true;
        if(index.X > 0)
            faceLeft = false;
        
        X += index.X;
        Y += index.Y;
        
    }
    

    // DRAW METHODS ***********************************************************************  
    public void changeImage() {
        timer++;
        if (timer > 20) { /// change timer
            timer = 0;

            if (!moving) {
                breathImg++;
                if (breathImg > 3) 
                    breathImg = 0;             
            }
            
            if (moving) {
                moveImg++;
                if (moveImg > 3) 
                    moveImg = 0;         
            }
        
        }        
    }


    public void Draw(Graphics2D g) throws NoninvertibleTransformException {

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform trans = new AffineTransform();

        trans.translate(X - (Stand.getWidth(null)/2), Y - (Stand.getHeight(null)/2));
       
        if (faceLeft) {
           trans.scale(-1, 1);
           trans.translate(-(Stand.getWidth(null)), 0);
        }

        if(isAlive)
        if (!moving)
        if(!Defending){
            switch (breathImg) {
                case 0:
                    g2d.drawImage(Stand, trans, null); // see javadoc for more info on the parameters  
                    break;
                case 1:
                    g2d.drawImage(StandChest, trans, null);
                    break;
                case 2:
                    g2d.drawImage(StandLegChest, trans, null);
                    break;
                case 3:
                    g2d.drawImage(StandLeg, trans, null);
                    break;
            }
        }
if(isAlive)
        if (moving)
        if(!Defending){
            switch (moveImg) {
                case 0:
                    g2d.drawImage(Stand, trans, null); // see javadoc for more info on the parameters  
                    break;
                case 1:
                    g2d.drawImage(RightStep, trans, null);
                    break;
                case 2:
                    g2d.drawImage(Stand, trans, null); // see javadoc for more info on the parameters  
                    break;
                case 3:
                    g2d.drawImage(LeftStep, trans, null);
                    break;
            }
        }
if(isAlive)
    if(Defending)
g2d.drawImage(Defends, trans, null);
        
if(!isAlive)
g2d.drawImage(Downed, trans, null);

if(DungeonCrawler.GameState == State.BATTLE)
 if( DungeonCrawler.showHP)
 if(isAlive){
     int halfWidth = Stand.getWidth(null)/2;
     int halfHeight = Stand.getHeight(null)/2;
              g.setColor(Color.BLACK);  
        g.drawRect(X-halfWidth-1,Y+ halfHeight+1,Stand.getWidth(null)+1 , 3);   
        g.setColor(Color.RED);
        g.fillRect(X-halfWidth,Y+ halfHeight+2,Stand.getWidth(null) , 2);
        g.setColor(Color.GREEN);
        
        double HPRatio = (double)HP/(double)MaxHP;
        g.fillRect(X-halfWidth,Y+ halfHeight+2,(int)((double)Stand.getWidth(null)*HPRatio) , 2);
        }



    }
}
