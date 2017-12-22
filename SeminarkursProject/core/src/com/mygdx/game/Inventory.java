package com.mygdx.game;

import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;

/**
 * Created by Implodee on 27.10.2017.
 */

public class Inventory {

    private Slot[] slots;
    public static Inventory playerInventory;

    //create new Inventory containing 40 slots
    public Inventory(){
        slots = new Slot[40];
        for(int i = 0; i < 40; i++){
            slots[i] = new Slot(null, 0);
        }
    }
    public void addStarterItems(){
        addItem(ItemList.axe_t1(), 1);
    }
    private void addItemToSlot(int p, ItemMaster it, int q){
        slots[p].addNewItems(it, q);
    }
    public boolean addItem(ItemMaster it, int q){
        if(checkAllIfFree() == true){
            if(checkForSameItem(it) == true){
                for(int i = 0; i < slots.length; i++){
                    if(slots[i].getItem() == it && (slots[i].getQuantity() + q) < it.getStackSizeMax()){
                        slots[i].addNewItems(it, slots[i].getQuantity()+q);
                        System.out.println("Item added to preexisting item");
                        return true;
                    } else {
                        slots[i++].addNewItems(it, q);
                        System.out.println("Item added to new Slot because Stacksize maxed out");
                        return true;
                    }
                }
            } else {
                for(int i = 0; i < slots.length; i++){
                    if(slots[i].getItem() == null){
                        slots[i].addNewItems(it, q);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public Slot[] getSlots(){
        return slots;
    }
    public boolean checkAllIfFree(){
        int a = 0;
        for(int i = 0; i < slots.length; i++){
            if(slots[i].getItem()!=null){
                a++;
            }
        }
        if(a < slots.length) {
            System.out.println("a < slots");
            return true;
        } else {
            System.out.println("a > slots");
            return false;
        }
    }
    public boolean checkForSameItem(ItemMaster it){
        for(int i = 0; i < slots.length; i++){
            if(slots[i].getItem() == it){
                System.out.println("same Item found");
                return true;
            }
        }
        System.out.println("new Item needed");
        return false;
    }

    public void debug(){
        for(int i = 0; i < slots.length; i++){
            System.out.println(slots[i].getItemName() + " : " + slots[i].getQuantity());
        }
    }

    public class Slot {
        //Items are added to a slot via Item name (seen in Class ItemList) and quantity q
        private com.mygdx.game.Item.ItemMaster it;
        private int q;

        public Slot(ItemMaster it, int q){
            this.it = it;
            this.q = q;
        }
        public void addNewItems(ItemMaster it, int q){
            if(this.q + q > it.getStackSizeMax()){
                System.out.println("Too many items in slot");
            } else {
                this.q += q;
                this.it = it;
            }
        }
        public void removeItems(int x){
            if(x == 1) {
                if (q == 1) {
                    this.q = 0;
                    this.it = null;
                } else {
                    q--;
                }
            } else if(x > 1){
                if(x > this.q){
                    System.out.println("Can't remove more items than available from slot");
                } else if(x <= this.q){
                    this.q -= x;
                    if(this.q == 0){
                        this.it = null;
                    }
                }
            }
        }
        public ItemMaster getItem(){
            return it;
        }
        public int getQuantity(){
            return q;
        }
        public String getItemName(){
            if(it != null){
                return it.getId().toString();
            }
            return null;
        }
    }
}