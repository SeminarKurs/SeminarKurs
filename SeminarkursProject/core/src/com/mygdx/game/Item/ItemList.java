package com.mygdx.game.Item;

/**
 * Created by Implodee on 22.10.2017.
 */

public class ItemList {
    public static ItemMaster mat_wood(int stackSize){return new ItemMaster("Wood","Wood_Desc",null, stackSize,100,10001);}
    public static ItemMaster mat_iron(int stackSize){return new ItemMaster("Iron Bar", "Iron_Bar_Desc", null, stackSize, 100, 10002);}
    public static ItemMaster mat_copper(int stackSize){return new ItemMaster("Copper Bar", "Copper_Bar_Desc", null, stackSize, 100, 10003);}
    public static ItemMaster mat_tin(int stackSize){return new ItemMaster("Tin Bar", "Tin_Bar_Desc", null, stackSize, 100, 10004);}
    public static ItemMaster mat_gold(int stackSize){return new ItemMaster("Gold Bar", "Gold_Bar_Desc", null, stackSize, 100, 10005);}
    public static ItemMaster mat_stone(int stackSize){return new ItemMaster("Stone", "Stone_Desc", null, stackSize, 100, 10006);}
    public static ItemMaster mat_plate_iron(int stackSize){return new ItemMaster("Iron Plate", "Iron_Plate_Desc", null, stackSize, 100, 10007);}
    public static ItemMaster mat_plate_copper(int stackSize){return new ItemMaster("Copper Plate", "Copper_Plate_Desc", null, stackSize, 100, 10008);}
    public static ItemMaster mat_rubber(int stackSize){return new ItemMaster("Rubber", "Rubber_Desc", null, stackSize, 100, 10009);}

    public static ItemMaster coal(int stackSize){return new ItemFuelMaster("Coal", "Coal_Desc", null, stackSize, 100, 30001);}

    public static ItemMaster ore_iron(int stackSize){return new ItemMaster("Iron Ore", "Iron_Ore_Desc", null, stackSize, 100, 20001);}
    public static ItemMaster ore_copper(int stackSize){return new ItemMaster("Copper Ore", "Copper_Ore_Desc", null, stackSize, 100, 20002);}
    public static ItemMaster ore_tin(int stackSize){return new ItemMaster("Tin Ore", "Tin_Ore_Desc", null, stackSize, 100, 20003);}
    public static ItemMaster ore_gold(int stackSize){return new ItemMaster("Gold Ore", "Gold_Ore_Desc", null, stackSize, 100, 20004);}

    public static ItemWeaponMaster rifle(){return new ItemWeaponMaster("Rifle", "Rifle_Desc", null, 50, 3, 40001);}

    public static ItemBuildingMaster gen_t1(){return new ItemBuildingMaster("Generator", "Gen_T1_Desc", null, 4, 50001);}
    public static ItemBuildingMaster gen_t2(){return new ItemBuildingMaster("Oil Plant", "Gen_T2_Desc", null, 4, 50002);}
    public static ItemBuildingMaster gen_t3(){return new ItemBuildingMaster("Geothermal Generator", "Gen_T3_Desc", null, 4, 50003);}
    public static ItemBuildingMaster gen_t4(){return new ItemBuildingMaster("Solar Plant", "Gen_T1_Desc", null, 2, 50004);}
    public static ItemBuildingMaster pipe(){return new ItemBuildingMaster("Pipe", "Pipe_Desc", null, 1, 50005);}
    public static ItemBuildingMaster cable_t1(){return new ItemBuildingMaster("Copper Cable", "Copper_Cable_Desc", null, 1, 50006);}
    public static ItemBuildingMaster cable_t2(){return new ItemBuildingMaster("Gold Cable", "Gold_Cable_Desc", null, 1, 50007);}

    public static ItemToolMaster axe_t1(){return new ItemToolMaster("Iron Axe", "Iron_Axe_Desc", null, 150, 0.75f, 60001);}
}