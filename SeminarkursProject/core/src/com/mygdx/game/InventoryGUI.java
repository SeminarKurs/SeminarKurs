package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Actor.Actor;

/**
 * Created by Implodee on 20.11.2017.
 */

public class InventoryGUI extends Actor implements Disposable{

    private final SpriteBatch sb = new SpriteBatch();
    private final OrthographicCamera camera;
    private final NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("red.png")));
    private final Stage stage = new Stage();
    private Skin skin = new Skin();
    private Label inventoryLabel;
    private Table table;


    public InventoryGUI(OrthographicCamera camera){
        this.camera = camera;
        Label.LabelStyle ls = new Label.LabelStyle(new BitmapFont(), Color.BLACK);

        inventoryLabel = new Label("Inventory", ls); table = new Table();
        table.setHeight(0.1f);
        table.bottom();
        table.setFillParent(false);
        ls.background = background;
        table.debugAll();
        table.pad(0.5f);
        table.defaults().expandX();
        table.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.add(inventoryLabel).colspan(2);
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
        stage.addActor(table);
    }

    public void render(){
        Matrix4 hudMatrix = camera.combined.cpy();
        hudMatrix.setToOrtho2D(0,0,5,5);
        hudMatrix.scale(1,1,1);

        stage.draw();
    }

    @Override
    public void dispose() {
        //sb.dispose();
        stage.dispose();
    }

    @Override
    public void update(float dt){
        //if item added / removed from inventory, update;
        //code to come
    }

}