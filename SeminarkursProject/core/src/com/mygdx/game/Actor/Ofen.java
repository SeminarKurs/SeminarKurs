package com.mygdx.game.Actor;

import com.mygdx.game.Item.ItemList;
import com.mygdx.game.Item.ItemMaster;

/**
 * Created by Christopher Schleppe on 01.11.2017.
 */

public class Ofen extends Actor{

        private ItemMaster returnedItem;

        private float progress;

        private ItemMaster item;
        private ItemMaster kohle;


        public Ofen(){}

        public void addItem(ItemActor item){

            if (item.getItem().getID() == 30001){
                if (kohle == null){
                    kohle = item.getItem();
                }else{
                    kohle.addStackSize(item.getItem().getStackSize());
                }
            }else{
                this.item = item.getItem();
            }

        }

        public void melt (){
            if (item == null || kohle == null){
                return;
            }else{
                if (item.getStackSize() > 0 && kohle.getStackSize() > 0) {
                    switch (item.getID()) {
                        case 20001:
                            returnedItem = ItemList.mat_iron;
                            break;
                        case 20002:
                            returnedItem = ItemList.mat_copper;
                            break;
                        case 20003:
                            returnedItem = ItemList.mat_tin;
                            break;
                        case 20004:
                            returnedItem = ItemList.mat_gold;
                            break;
                        default:
                            return;
                    }
                    kohle.addStackSize(-1);
                    item.addStackSize(-1);
                }
            }
        }
        @Override
        public void update (float dt){
            progress += dt;
            if (progress >= 100){
                progress -= 100;
                melt();
            }
        }


}
