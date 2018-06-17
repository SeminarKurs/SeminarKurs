package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Actor.Actor;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Item.ItemList;

/**
 * Created by Implodee on 20.11.2017.
 */

public class InventoryGUI extends Actor implements Disposable{

    private final OrthographicCamera camera;
    private final NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("red.png")));
    private final Stage stage = new Stage();
    Label.LabelStyle ls = new Label.LabelStyle(new BitmapFont(), Color.BLACK);
    private Table table;
    private Table fullInv;
    private Table craftingTable;
    private final int SELECTION = 0;
    private final int CRAFTING = 1;
    private final int INVENTORY = 2;
    private int mode;
    private int craftingMode;

    // LIST ALL CRAFTING ITEMS (LABEL) AND STRINGS AND CONSTANTS FOR MODE, RECIPES
    private final int furnaceCostWood = 10;
    private final int furnaceCostCoal = 10;
    private final int intFurnace = 0;
    private final String stringFurnace = new String("Furance");
    private Label craftingFurnace = new Label(stringFurnace, ls);

    private final int intPowerLine = 1;
    private final String stringPowerLine = new String("Powerline");
    private Label craftingPowerline = new Label(stringPowerLine, ls);

    //INT FOR MAX AVAILABLE CRAFTING RECIPES (-1), ALWAYS UPDATE MANUALLY
    private int craftingMax = 1;

    public InventoryGUI(OrthographicCamera camera){
        this.camera = camera;
        setupFullInventoryTable();
        setupCraftingTable();
        setupHotbar();
        mode = 0;
        stage.addActor(table);
    }
    private void setupCraftingTable(){
        craftingTable = new Table();fullInv.setHeight(0.1f);
        craftingTable.bottom();
        craftingTable.setFillParent(false);
        craftingTable.debugAll();
        craftingTable.pad(0.5f);
        craftingTable.defaults().expandX();
        craftingTable.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        craftingTable.row();
        craftingTable.add(new Label("crafting", ls)).colspan(4);
        craftingTable.row();
        craftingTable.add(new Label("item to craft", ls));
        craftingTable.add(new Label("cost", ls)).colspan(3);
        craftingTable.row();
        craftingMode = 0;
        craftingPowerline.setText(stringPowerLine);
        craftingFurnace.setText("-> craft Furnace");
        //RECIPES FOR CRAFTING, UPDATE MANUALLY.
        craftingTable.add(craftingFurnace);
        craftingTable.add(new Label(furnaceCostCoal + " coal", ls));
        craftingTable.add(new Label(furnaceCostWood + " wood", ls));
        craftingTable.add(new Label("", ls));
        craftingTable.row();
        craftingTable.add(craftingPowerline);
        craftingTable.add(new Label("", ls));
        craftingTable.add(new Label("", ls));
        craftingTable.add(new Label("", ls));
    }
    private void setupFullInventoryTable(){
        fullInv = new Table();
        fullInv.setHeight(0.1f);
        fullInv.bottom();
        fullInv.setFillParent(false);
        fullInv.debugAll();
        fullInv.pad(0.5f);
        fullInv.defaults().expandX();
        fullInv.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        for(int i = 0; i < Inventory.playerInventory.getSize(); i++){
            if(Inventory.playerInventory.getSlot(i).isEmpty()){
                fullInv.add(new Label("No Item", ls));
                fullInv.add(new Label(0 + "", ls));
                fullInv.row();
            } else if(Inventory.playerInventory.getSlot(i).isEmpty()==false) {
                fullInv.add(new Label(Inventory.playerInventory.getSlot(i).getItem().getDesc(),ls));
                fullInv.add(new Label(Inventory.playerInventory.getSlot(i).getQuantity()+"",ls));
                fullInv.row();
            }
        }
    }
    private void setupHotbar(){
        table = new Table();
        table.setHeight(0.1f);
        table.bottom();
        table.setFillParent(false);
        ls.background = background;
        table.debugAll();
        table.pad(0.5f);
        table.defaults().expandX();
        table.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.row();
        for(int i = 0; i < 3; i++){
            if(Inventory.playerInventory.getSlot(i).isEmpty()){
                table.add(new Label("No Item", ls));
                table.add(new Label(0 + "", ls));
                table.row();
            } else if(Inventory.playerInventory.getSlot(i).isEmpty()==false) {
                table.add(new Label(Inventory.playerInventory.getSlot(i).getItem().getDesc(),ls));
                table.add(new Label(Inventory.playerInventory.getSlot(i).getQuantity()+"",ls));
                table.row();
            }
        }
    }
    public void render(){
        Matrix4 hudMatrix = camera.combined.cpy();
        hudMatrix.setToOrtho2D(0,0,5,5);
        hudMatrix.scale(1,1,1);
        stage.draw();
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(mode < 2){
                mode++;
                if(mode == CRAFTING){
                    table.remove();
                    setupCraftingTable();
                    stage.addActor(craftingTable);
                    craftingMode = 0;
                }
                if(mode == INVENTORY){
                    craftingTable.remove();
                    setupFullInventoryTable();
                    stage.addActor(fullInv);
                }
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(mode > 0){
                mode--;
                if(mode == CRAFTING){
                    fullInv.remove();
                    setupCraftingTable();
                    stage.addActor(craftingTable);
                    craftingMode = 0;
                }
                if(mode == SELECTION){
                    craftingTable.remove();
                    setupHotbar();
                    stage.addActor(table);
                }
            }
        }
        if(mode == CRAFTING){
            if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && craftingMode < craftingMax){
                craftingMode++;
                if(craftingMode == intPowerLine){
                    craftingFurnace.setText(stringFurnace);
                    craftingPowerline.setText("-> craft Powerline");
                }
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && craftingMode > 0){
                craftingMode--;
                if(craftingMode == intFurnace){
                    craftingPowerline.setText(stringPowerLine);
                    craftingFurnace.setText("-> craft Furnace");
                }
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                switch(craftingMode){
                    //Craft Furnace; Recipe HERE
                    case intFurnace:
                        if(Inventory.playerInventory.hasItem(ItemId.COAL, furnaceCostCoal) &&
                           Inventory.playerInventory.hasItem(ItemId.MAT_WOOD, furnaceCostWood)){
                            //remove items
                            Inventory.playerInventory.removeItem(ItemId.COAL, 10);
                            Inventory.playerInventory.removeItem(ItemId.MAT_WOOD,10);
                            //add items
                            Inventory.playerInventory.addItem(ItemList.furnace(), 1);
                        }
                        break;
                }
            }
        }
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
    @Override
    public void update(float dt){
    }
    public ItemId getId() {
        System.out.println("InvewntoryGUI needs to be implemented");
        return null;
    }
}