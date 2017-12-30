package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;

/**
 * Created by Implodee on 29.12.2017.
 */

public class InventoryV2 {
    public static InventoryV2 playerInventory;
    private int inventorySize = 40;
    private Array<Slot> slots = new Array<Slot>(40);
    public InventoryV2(){
        for(int i = 0; i < inventorySize; i++){
            slots.add(new Slot(null));
        }
    }
    public void addStarterItems(){
        addItem(ItemList.axe_t1(),1);
    }
    public boolean addItem(ItemMaster item, int quantity){
        for(int i = 0; i < inventorySize; i++) {
            if (slots.get(i).isItemOfType(item) == true && slots.get(i).getQuantity() + quantity > slots.get(i).getItem().getStackSizeMax()) {
                if (i > inventorySize) {
                    for(int o = 0; o < inventorySize; o++) {
                        if (slots.get(o).isEmpty() == true) {
                            if (slots.get(o).getItem() == null) {
                                slots.get(o).addItem(item, quantity);
                                return true;
                            }
                        }
                        if(o == inventorySize){
                            return false;
                        }
                    }
                } else {
                    slots.get(i).addItem(item, quantity);
                    return true;
                }
            } else if(slots.get(i).isEmpty()) {
                slots.get(i).addItem(item, quantity);
                return true;
            }
        }
        return false;
    }
    public boolean removeItem(ItemMaster item, int quantity){
        for(int i = 0; i < inventorySize; i++){
            if(slots.get(i).isItemOfType(item)==true && slots.get(i).getQuantity() - quantity >= 0){
                slots.get(i).removeItem(quantity);
                return true;
            } else {
                if(slots.get(i).isItemOfType(item)==true&&slots.get(i).getQuantity() - quantity < 0){
                    System.out.println("Quantity of item not big enough to remove");
                    return false;
                } else {
                    System.out.println("No items to remove found");
                    return false;
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
            return this.item == item;
        }
        public void addItem(ItemMaster item, int quantity){
            this.item = item;
            this.item.addStackSize(quantity);
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
    }
}