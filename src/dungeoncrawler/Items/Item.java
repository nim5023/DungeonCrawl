/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeoncrawler.Items;

/**
 *
 * @author Nick
 */
public class Item {
   
   public boolean isAmulet = true;
   public boolean isEmpty;
   public int Attack,Def,Magic;
       public int HP;
       
    public Item(boolean Ammy, int h,int a, int d, int m){
        isAmulet = Ammy;
        
        HP = h;
        Attack = a;
        Def = d;
        Magic = m;
        setEmpty();
    }
    
    public final void setEmpty(){
        
        if(Attack ==0)
        if(Def ==0)
        if(Magic ==0)
        if(HP ==0){
        isEmpty =  true;
        return;}
        
        isEmpty =  false;
    }
    
}
