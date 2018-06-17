package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;

/**
 * Created by Implodee on 29.12.2017.
 */

public class Inventory {
    public static Inventory playerInventory;
    private int inventorySize = 40;
    private Array<Slot> slots = new Array<Slot>(40);
    public Inventory(){
        for(int i = 0; i < inventorySize; i++){
            slots.add(new Slot(null));
        }
    }
    public void addStarterItems(){
        addItem(ItemList.axe_t1(),1);
        addItem(ItemList.coal(20),20);
        addItem(ItemList.mat_wood(20),20);
    }
    public boolean addItem(ItemMaster item, int quantity){
        for(int i = 0; i < inventorySize; i++) {
            if (!slots.get(i).isEmpty() && slots.get(i).isItemOfType(item) == true){
                if (i == inventorySize){
                    for(int o = 0; o < inventorySize; o++) {
                        if (slots.get(o).isEmpty() == true) {
                            if (slots.get(o).getItem() == null) {
                                slots.get(i).addItem(item, slots.get(i).getQuantity()+quantity);
                                return true;
                            }
                        }
                        if(o == inventorySize){
                            return false;
                        }
                    }
                } else {
                    if(slots.get(i).getQuantity()+quantity <= slots.get(i).getItem().getStackSizeMax()){
                        slots.get(i).addItem(item, slots.get(i).getQuantity()+quantity);
                        return true;
                    } else if(slots.get(i).getQuantity()+quantity > slots.get(i).getItem().getStackSizeMax()){
                        if(i < inventorySize){
                            i++;
                            slots.get(i).addItem(item,quantity);
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            } else if(slots.get(i).isEmpty()) {
                slots.get(i).addItem(item, quantity);
                return true;
            }
        }
        return false;
    }

    public int getSize(){
        return inventorySize;
    }

    public boolean removeItem(ItemId id, int quantity){
        for(int i = 0; i < inventorySize; i++){
            if(slots.get(i).getID() == id && slots.get(i).getQuantity() - quantity >= 0){
                slots.get(i).removeItem(quantity);
                System.out.println("Items successfully removed");
                return true;
            } else {
                if(slots.get(i).getID() == id && slots.get(i).getQuantity() - quantity <= 0){
                    System.out.println("Quantity of item not big enough to remove");
                } else {
                    System.out.println("No items to remove found");
                }
            }
        }
        System.out.println("Inventory check failed, no items removed, returning false");
        return false;
    }
    public Array<Slot> getSlots(){
        return slots;
    }
    public Slot getSlot(int i){
        return slots.get(i);
    }
    public boolean hasItem(ItemId id, int quantity){
        for(int i = 0; i < inventorySize; i++){
            if(getSlot(i).getID() == id && getSlot(i).getQuantity() >= quantity){
                return true;
            }
        }
        return false;
    }
    public class Slot {
        private ItemMaster item;
        public Slot(ItemMaster item) {
            this.item = item;
        }
        public int getQuantity() {
            return this.item.getStackSize();
        }
        public ItemMaster getItem() {
            return item;
        }
        public boolean isEmpty(){
            return item == null;
        }
        public boolean isItemOfType(ItemMaster item){
            return this.item.getId() == item.getId();
        }
        public void addItem(ItemMaster item, int quantity){
            this.item = item;
            this.item.addStackSize(this.getItem().getStackSize() + quantity);
        }
        public boolean removeItem(int quantity){
            if(this.item.getStackSize()-quantity>=0){
                this.item.removeStackSize(quantity);
                if(this.item.getStackSize()==0){
                    item=null;
                }
                return true;
            } else {
                return false;
            }
        }
        public ItemId getID(){
            if(item != null){
                return this.item.getId();
            } else {return null;}
        }
    }
}
