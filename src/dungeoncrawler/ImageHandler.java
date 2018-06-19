package dungeoncrawler;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nick
 */
public class ImageHandler {
 
    class HeroImage{
    public Image  Stand;
    public Image  StandLegChest;
    public Image  StandLeg;
    public Image  StandChest;
    public Image  WalkLeft;
    public Image  WalkRight;
    public Image  Down;
    public Image  Defends;
        
    }
 
    HeroImage war  = new HeroImage();
    HeroImage mage = new HeroImage();
    HeroImage ftr  = new HeroImage();
  
    public Image  BG;
    
    public static Image Wolf;
    public static Image SpirtWolf;    
    public static Image Ogre;
    public static Image OgreShaman;
    public static Image Dragon;
    public static Image RedDragon;
    public static Image StoneDragon;
    public static Image Wraith;
    public static Image Bear;
    public static Image ElfWarrior;
    public static Image ElfMage;
    public static Image Ronin;
    
    public static Image ImgAttack;
    public static Image ImgWraithBomb;
    public static Image ImgDragonCharge;
    public static Image ImgFireBall;
    public static Image ImgQuestionMark;
    public static Image ImgStun;
    public static Image ImgAbsorb;
    public static Image ImgPentagram;
    public static Image ImgDemon;
            
    public static Image Cursor;
    public static Image Target;
    
    public static Image Hole;
    public static Image Portal;
    
    public static Image LeftRing;
    public static Image RightRing;
    public static Image Amulets;
    
    public Toolkit toolkit;
    
       
  public final Image  AddImage(String filename){
       URL url = ImageHandler.class.getResource(filename);
      return toolkit.createImage(url);
  }    
    public  ImageHandler(){
      
       toolkit = Toolkit.getDefaultToolkit();
  
            BG = AddImage("img/Back2.png");
  
            war.Stand =         AddImage("img/WarStand.png");
            war.StandLeg =      AddImage("img/WarStandLeg.png");
            war.StandChest =    AddImage("img/WarStandChest.png");
            war.StandLegChest = AddImage("img/WarStand1.png");
            war.WalkLeft =      AddImage("img/WarWalkLeft.png");
            war.WalkRight =     AddImage("img/WarWalkRight.png");
            war.Down =          AddImage("img/WarDown.png");
            war.Defends =       AddImage("img/WarDefend.png");
            
            mage.Stand =        AddImage("img/MageStand.png");
            mage.StandLeg =     AddImage("img/MageStandLeg.png");
            mage.StandChest =   AddImage("img/MageStandChest.png");
            mage.StandLegChest= AddImage("img/MageStandChestLeg.png");
            mage.WalkLeft =     AddImage("img/MageWalkLeft.png");
            mage.WalkRight =    AddImage("img/MageWalkRight.png");           
            mage.Down =         AddImage("img/BlmDown.png");
            mage.Defends =      AddImage("img/MageDefend.png");
            
            ftr.Stand =         AddImage("img/FtrStand.png");
            ftr.StandLeg =      AddImage("img/FtrStandLeg.png");
            ftr.StandChest =    AddImage("img/FtrStandChest.png");
            ftr.StandLegChest = AddImage("img/FtrStandChestLeg.png");
            ftr.WalkLeft =      AddImage("img/FtrWalkLeft.png");
            ftr.WalkRight =     AddImage("img/FtrWalkRight.png");
            ftr.Down =          AddImage("img/FtrDown.png");
            ftr.Defends =      AddImage("img/FtrDefend.png");
           
            ImgAttack     = AddImage("img/Abilities/Attack.png");
            ImgWraithBomb = AddImage("img/Abilities/WraithBomb.png");
            ImgDragonCharge = AddImage("img/Abilities/DragonCharge.png");            
            ImgFireBall= AddImage("img/Abilities/FireBall.png");   
            ImgQuestionMark= AddImage("img/Abilities/QuestionMark.png");
            ImgStun= AddImage("img/Abilities/Stun.png");
            ImgAbsorb= AddImage("img/Abilities/ImgAbsorb.png");
            ImgPentagram= AddImage("img/Pentagram.png");
            ImgDemon= AddImage("img/Demon.png");
            
            Wolf = AddImage("img/Wolf.png");
            SpirtWolf = AddImage("img/WolfSpirit.png");            
            Ogre = AddImage("img/Ogre.png");
            OgreShaman = AddImage("img/OgreShaman.png");
            Dragon = AddImage("img/DragonStand.png");
            RedDragon = AddImage("img/RedDragon.png");
            StoneDragon = AddImage("img/StoneDragon.png");
            Wraith = AddImage("img/wraith.png");
            Bear = AddImage("img/Bear.png");
            ElfWarrior = AddImage("img/DarkElfMale.png");
            ElfMage = AddImage("img/DarkElfMage.png");
            Ronin = AddImage("img/Ronin.png");
            
            Cursor = AddImage("img/pointer.png");
            Target = AddImage("img/Target.png");
            Hole = AddImage("img/hole.png");
            Portal = AddImage("img/portal.png");
            Amulets = AddImage("img/Amulet.png");
            LeftRing = AddImage("img/Ring1.png");
            RightRing = AddImage("img/Ring2.png");
    }
    
    
}
