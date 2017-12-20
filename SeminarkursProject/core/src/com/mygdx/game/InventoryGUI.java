package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Actor.Actor;

/**
 * Created by Implodee on 20.11.2017.
 */

public class InventoryGUI extends Actor implements Disposable{

    private SpriteBatch sb;
    private final OrthographicCamera camera;
    private final TextureRegionDrawable background = new TextureRegionDrawable(new TextureRegion(new Texture("test.png")));
    private final Stage stage = new Stage();
    private Label inventoryLabel;
    private Table table;

    public InventoryGUI(OrthographicCamera camera, SpriteBatch sb){
        this.camera = camera;
        this.sb = sb;
        Label.LabelStyle ls = new Label.LabelStyle(new BitmapFont(), Color.BLACK);
        inventoryLabel = new Label("Inventory", ls); table = new Table();
        table.setHeight(0.1f);
        table.bottom();
        table.setFillParent(true);

        //debug
        //table.debugTable();
        //table.debugCell();
        table.debugAll();
        //table.setBackground(background);

        table.pad(0.5f);
        table.defaults().expandX();
        table.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //table.background(background);
        table.add(inventoryLabel).colspan(2);
        table.row();
        for(int i = 0; i < 3; i++){
            table.add(new Label(Inventory.playerInventory.getName(i), ls));
            table.add(new Label(Inventory.playerInventory.getQuantityString(i), ls));
            table.row();
        }
        stage.addActor(table);
    }

    public void render(){
        Matrix4 hudMatrix = camera.combined.cpy();
        hudMatrix.setToOrtho2D(0,0,5,5);
        hudMatrix.scale(1,1,1);
        sb.setProjectionMatrix(hudMatrix);
        sb.begin();
        sb.draw(new Texture("test.png"),0,0,5,0.5f);
        sb.end();
        /*sb.begin();
        table.draw(sb, 1f);
        sb.end();
        */
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