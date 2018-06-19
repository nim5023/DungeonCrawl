/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.StateHandlers;

import dungeoncrawler.DungeonCrawler;
import dungeoncrawler.Characters.Hero;
import dungeoncrawler.Items.Item;
import dungeoncrawler.Play;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick
 */
public class LoadSaveHandler {
    class PassByRef{
        int token;
        int index;
    }
       String Tokenizer(int index, char [] input){
        char [] rtn = new char [10];
        int si =0;
        while(input[index]!='\r')
        rtn[si++] = input[index++]; 
        return new String(rtn).trim();
    }
    PassByRef LoadInts(int index,char [] text){
        PassByRef PBR = new PassByRef();
        int wtf = Integer.parseInt(Tokenizer(index,text)); 
        PBR.token = wtf;
        PBR.index = index;
        if(PBR.token!=0)
        PBR.index+= Math.log10(PBR.token)+1;
        else
        PBR.index+=1;
        PBR.index+=2;
                return PBR;
    }
     public int LoadItem(Item item,int index,char [] text){
        PassByRef PBR = LoadInts(index,text);
        item.HP = PBR.token;  

        PBR = LoadInts(PBR.index,text);            
        item.Attack = PBR.token; 

        PBR = LoadInts(PBR.index,text);            
        item.Def = PBR.token;
       
        PBR = LoadInts(PBR.index,text);            
        item.Magic = PBR.token;
        
        item.setEmpty();
        return PBR.index;
     }
    public int LoadHero(Hero hero, int index,char [] text){
        
       PassByRef PBR = new PassByRef();        
       PBR = LoadInts(index,text);
       hero.MaxHP = PBR.token; 
       
       PBR = LoadInts(PBR.index,text);       
       hero.Attack = PBR.token; 
       
       PBR = LoadInts(PBR.index,text);       
       hero.Defense = PBR.token; 
       
       PBR = LoadInts(PBR.index,text);       
       hero.Magic = PBR.token; 
       
       index = PBR.index;
       
       index =  LoadItem(hero.LeftRing, index,text);
       index =  LoadItem(hero.Ammy,     index,text);
       index =  LoadItem(hero.RightRing,index,text);
       
       hero.LeftRing.isAmulet =false;
       hero.RightRing.isAmulet =false;
       hero.Ammy.isAmulet =true;
            
       return index;
    }
    
     public void LoadFile(){            
            char [] text = new char[500];
            int index = 0;
    try {  
        FileReader fr = new FileReader("Save.txt");      
            fr.read(text); 
            int Token = Integer.parseInt(Tokenizer(index,text));            
            index+= Math.log10(Token)+3;
            BattleHandler.HeroLevel = Token;
            BattleHandler.MonsterLevel=BattleHandler.HeroLevel-1;
            DungeonCrawler.War.Power =  BattleHandler.HeroLevel*2;
            DungeonCrawler.Ftr.Power =  BattleHandler.HeroLevel*2;
            DungeonCrawler.Blm.Power =  BattleHandler.HeroLevel*2;
            
            index = LoadHero(DungeonCrawler.War,index,text);
            index = LoadHero(DungeonCrawler.Ftr,index,text);
            index = LoadHero(DungeonCrawler.Blm,index,text);
            
            LoadItem(DungeonCrawler.RewardHand.InvItem,index,text);
            if(DungeonCrawler.RewardHand.InvItem.HP>0)
                DungeonCrawler.RewardHand.InvItem.isAmulet = true;
            else
                DungeonCrawler.RewardHand.InvItem.isAmulet = false;
            
                DungeonCrawler.RewardHand.InvItem.setEmpty();

    } catch (IOException ex) {
        Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
     
     
     
     
     
    
    public void SaveItem(PrintWriter writer,Item item){
            writer.println(item.HP); 
            writer.println(item.Attack); 
            writer.println(item.Def); 
            writer.println(item.Magic);        
    }
    public void SaveHero(PrintWriter writer, Hero hero){
              
            writer.println(hero.MaxHP);
            writer.println(hero.Attack);
            writer.println(hero.Defense);
            writer.println(hero.Magic);
            
              SaveItem(writer,hero.LeftRing);
              SaveItem(writer,hero.Ammy);
              SaveItem(writer,hero.RightRing);
    }
     public void SaveFile(){
    try {
        PrintWriter writer;
            writer = new PrintWriter("Save.txt", "UTF-8");            
            writer.println(BattleHandler.HeroLevel);
            SaveHero(writer,DungeonCrawler.War);
            SaveHero(writer,DungeonCrawler.Ftr);
            SaveHero(writer,DungeonCrawler.Blm);
            SaveItem(writer,DungeonCrawler.RewardHand.InvItem);
            
            writer.close();
    } catch (FileNotFoundException | UnsupportedEncodingException ex) {
        Logger.getLogger(RewardHandler.class.getName()).log(Level.SEVERE, null, ex);}
    }
     
    
}
