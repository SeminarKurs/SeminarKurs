package com.mygdx.game.Player;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Actor.Clutch;
import com.mygdx.game.Actor.Miner;
import com.mygdx.game.Actor.Tile;
import com.mygdx.game.Inventory;
import com.mygdx.game.Item.ItemMaster;
import com.mygdx.game.Item.ItemToolMaster;
import com.mygdx.game.Types.Collision;
import com.mygdx.game.Types.FMath;
import com.mygdx.game.Types.IVector2;
import com.mygdx.game.WorldM;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by Tobias on 28.08.2017.
 */

public class PlayerController extends ApplicationAdapter implements InputProcessor {

    // we will use 32px/unit in worldM
    public final static float SCALE = 32f;
    public final static float INV_SCALE = 1.f/SCALE;
    // this is our "target" resolution, not that the window can be any size, it is not bound to this one
    public final static float VP_WIDTH = 1280 * INV_SCALE;
    public final static float VP_HEIGHT = 720 * INV_SCALE;
    public final static float MAXZOOM = 5;
    public final static float MINZOOM = 1;
    public final static float PlSIZEHX = .4f;
    public final static float PlSIZEHY = .4f;
    public final static int ACTINGRANGE = 2;

    private Texture playerTex;
    private Vector2 pos = new Vector2();

    private OrthographicCamera camera;
    private float mouseWheelScale = 1;
    // moveSpeed that the cam player moves (units per second)
    private float moveSpeed = 2f;
    // the moveSpeed mining is done.
    private float mineSpeed = 1f;
    private float mineProgress;
    private boolean mineing;
    IVector2 mineTile = new IVector2();
    // the object that need's to build
    private int objectToBuild;

    private ItemToolMaster tool;

    public PlayerController(OrthographicCamera camera)
    {
        this.camera = camera;
        playerTex = new Texture("Player.png");
        updCamPos();
    }

    @Override public void create () { input.setInputProcessor(this);}

    @Override public void render (){}

    public void update(float dt)
    {

        if (mineing && WorldM.hasResource(mineTile)) {
            Tile t = WorldM.getResource(mineTile);
            if (t.hasRes() && pos.dst(mineTile.x,mineTile.y) <= ACTINGRANGE)
            {
                mineProgress += dt * mineSpeed / t.resHardness();
                if (mineProgress >= 1) {
                    if (t.resAmount() <= (int) mineProgress) {
                        ItemMaster addItem = t.getItemResource(t.resAmount());
                        if(addItem != null) {
                            Inventory.playerInventory.addItem(addItem, t.resAmount());
                        }
                        t.resSetAmount(0);
                        mineing = false;
                        WorldM.updateResource(mineTile);
                    }
                    else
                    {
                        ItemMaster addItem = t.getItemResource(t.resAmount());
                        if(addItem != null) {
                            Inventory.playerInventory.addItem(addItem, (int) mineProgress);
                        }
                        t.resSetAmount(t.resAmount() - (int) mineProgress);
                    }
                    mineProgress -= 1;
                }

            }
            else
            {
                mineing = false;
            }
        }

        if(input.isKeyPressed(Input.Keys.ANY_KEY)) {
            // handle movement
            Vector2 movement = new Vector2();
            if (input.isKeyPressed(Input.Keys.W)) {
                movement.y += 1;
            }
            if (input.isKeyPressed(Input.Keys.S)) {
                movement.y -= 1;
            }
            if (input.isKeyPressed(Input.Keys.D)) {
                movement.x += 1;
            }
            if (input.isKeyPressed(Input.Keys.A)) {
                movement.x -= 1;
            }

            movement.nor(); // nor vector for same moveSpeed every dircetion
            movement.scl(moveSpeed * dt); // make it (moveSpeed*dt) times longer

            IVector2 posI = new IVector2();
            posI.set(FMath.getTile(pos));
            if (movement.x != 0) {
                if(movement.x > 0) {
                    checkCollision(true, movement, posI.addNew(1, 0));
                    checkCollision(true, movement, posI.addNew(1, 1));
                    checkCollision(true, movement, posI.addNew(1, -1));
                }else{
                    checkCollision(true, movement, posI.addNew(-1, 0));
                    checkCollision(true, movement, posI.addNew(-1, 1));
                    checkCollision(true, movement, posI.addNew(-1, -1));
                }
            }
            if (movement.y != 0) {
                if(movement.y > 0) {

                    checkCollision(false, movement, posI.addNew(0, 1));
                    checkCollision(false, movement, posI.addNew(1, 1));
                    checkCollision(false, movement, posI.addNew(-1, 1));
                }else{

                    checkCollision(false, movement, posI.addNew(0, -1));
                    checkCollision(false, movement, posI.addNew(1, -1));
                    checkCollision(false, movement, posI.addNew(-1, -1));
                }
            }

            // too tiny numbers make errors
            pos.x = Math.round((movement.x+pos.x)  * 1000f) / 1000f;
            pos.y = Math.round((movement.y+pos.y)* 1000f) / 1000f;

            updCamPos();
        }
    }



    void SetEquipment(ItemToolMaster tool)
    {
        this.tool = tool;
        mineSpeed = tool.getEfficiency();
    }

    private boolean checkCollision(boolean x, Vector2 movement, IVector2 tile)
    {
        if(WorldM.getTileCollision(tile.x, tile.y) == Collision.none)
        {
            return false;
        }
        float plSizeHX = PlSIZEHX;
        float plSizeHY = PlSIZEHY;
        IVector2 tileLO = new IVector2(tile);
        Vector2 posLO = new Vector2(pos);
        if(!x)
        {
            movement.set(movement.y, movement.x);
            plSizeHX = PlSIZEHY;
            plSizeHY = PlSIZEHX;
            posLO.set(posLO.y, posLO.x);
            tileLO.set(tileLO.y, tileLO.x);
        }

        boolean isCollision = true;
        float tileHSize = 0.5f;

        if(Math.abs(tileLO.x - posLO.x) < plSizeHX)
        {
            if(!x){movement.set(movement.y, movement.x);}// reset the movment that it wont get returned bad
            return false;
        }

        float distanceX = tileLO.x - posLO.x + ((tileLO.x - posLO.x) >= 0 ? -(tileHSize + plSizeHX) : (tileHSize + plSizeHX));

        // The y positon when the player collides
        if(tileLO.x - posLO.x >= 0 ? distanceX < movement.x : distanceX > movement.x)
        {
            float posY = (movement.y * (distanceX / movement.x)) + posLO.y;

            if (posY + plSizeHY > tileLO.y - tileHSize && posY - plSizeHY < tileLO.y + tileHSize)// collides in y too?
            {

                movement.set(distanceX, movement.y);//distanceX, movement.y * distanceX / movement.x);
                if(!x)
                {
                    movement.set(movement.y, movement.x);
                }
                return true;
            }
        }

        if(!x)
        {
            movement.set(movement.y, movement.x);
        }
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button)
    {
        Vector3 tp = new Vector3();
        // get mouse courser position
        camera.unproject(tp.set(screenX, screenY, 0));
        // if it's tight mouse button make colliosn test
        if(button == Input.Buttons.LEFT)
        {
            if(tp.x < 0){ tp.x -= 1; } // -0.01 to -0.99 it will be raunded to 0 so whe have two 0,0 positons
            if(tp.y < 0){ tp.y -= 1; } // -0.01 to -0.99 it will be raunded to 0 so whe have two 0,0 positons
            mineTile = FMath.getTile(tp);
            if(WorldM.validTile(mineTile))
                mineing = true;
        }
        if(button == Input.Buttons.RIGHT)
        {
            IVector2 pos = FMath.getTile(tp);
            if(WorldM.validTile(pos)&& this.pos.dst(tp.x, tp.y) <= ACTINGRANGE)
            {
                WorldM.addActor(new Miner(pos), pos);
            }
            return true;
        }


        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button)
    {
        if (button != Input.Buttons.LEFT) return false;
        mineing = false;
        mineTile = null;
        return true;
    }

    @Override public boolean scrolled (int amount)
    {
        camera.zoom += amount * mouseWheelScale;
                if(camera.zoom < MINZOOM){ camera.zoom = MINZOOM; }
        if(camera.zoom > MAXZOOM){ camera.zoom = MAXZOOM; }
        return true;
    }

    public void updCamPos(){
        camera.position.set(pos.x, pos.y, 0);
    }
    public Texture getPlayerTex() { return playerTex;}
    public Vector2 getPosition() { return pos;}
    public void setPosition (Vector2 pos){ this.pos =pos;}
    public float getMineSpeed() { return mineSpeed; }

    @Override public boolean keyDown (int keycode){
        if(keycode ==Input.Keys.F5) {
            System.out.println("save");
            WorldM.save();
        }
        if(keycode ==Input.Keys.F8) {
            System.out.println("Load");
            WorldM.load();
        }
        if (keycode == Input.Keys.C){
            IVector2 pos = new IVector2(5, 5);
            WorldM.addActor(new Clutch(pos), pos);
        }

        return false;
    }
    @Override public boolean mouseMoved (int screenX, int screenY){ return true; }
    @Override public boolean touchDragged (int screenX, int screenY, int pointer){ return true; }
    @Override public void resize (int width, int height){}
    @Override public void dispose (){}
    @Override public boolean keyUp (int keycode) {
        return false;
    }
    @Override public boolean keyTyped (char character) {
        return false;
    }
}
