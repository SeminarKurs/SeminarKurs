package com.mygdx.game.Item;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemList {
    public static ItemMaster mat_wood(int stackSize){return new ItemMaster("Wood_Desc", 0, stackSize,100,ItemId.MAT_WOOD);}
    public static ItemMaster mat_iron(int stackSize){return new ItemMaster("Iron_Bar_Desc", 0, stackSize, 100, ItemId.MAT_IRON);}
    public static ItemMaster mat_stone(int stackSize){return new ItemMaster("Stone_Desc", 0, stackSize, 100, ItemId.MAT_STONE);}

    public static ItemMaster coal(int stackSize){return new ItemMaster("Coal_Desc", 0, stackSize, 100, ItemId.COAL);}

    public static ItemMaster ore_iron(int stackSize){return new ItemMaster("Iron_Ore_Desc", 0, stackSize, 100, ItemId.ORE_IRON);}

    public static ItemWeaponMaster rifle(){return new ItemWeaponMaster("Rifle_Desc", 0, 50, 3, ItemId.RIFLE);}

    public static ItemBuildingMaster conveyor(){return new ItemBuildingMaster("Pipe_Desc", 0, 1, ItemId.CONVEYOR);}
    public static ItemBuildingMaster furnace(){return new ItemBuildingMaster("Furnace_Desc", 0, 1, ItemId.FURNACE);}

    public static ItemToolMaster axe_t1(){return new ItemToolMaster("Iron_Axe_Desc", 0, 150, 0.75f, ItemId.AXE);}

}