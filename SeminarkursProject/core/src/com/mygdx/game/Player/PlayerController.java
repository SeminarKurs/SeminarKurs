package com.mygdx.game.Player;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Actor.Resource;
import com.mygdx.game.Item.ItemToolMaster;
import com.mygdx.game.Types.FMath;
import com.mygdx.game.Types.IVector2;
import com.mygdx.game.WorldM;

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

    private ItemToolMaster tool;

    public PlayerController(OrthographicCamera camera)
    {
        this.camera = camera;
        playerTex = new Texture("ImgTest.png");
        UpdPosition();
    }

    @Override public void create () { Gdx.input.setInputProcessor(this);}

    @Override public void render (){

    }

    public void update(float dt)
    {
        if(mineing && WorldM.hasResource(mineTile))
        {
            Resource r = WorldM.getResource(mineTile);
            mineProgress += dt * mineSpeed / r.hardness();
            if(mineProgress >= 1)
            {
                System.out.println("hi");
                if(r.amount < (int)mineProgress )
                {
                    r.amount = 0;
                    mineing = false;
                    WorldM.updateResource(mineTile);
                }
                else
                {
                    r.amount -= (int)mineProgress;
                }
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            Vector2 movement = new Vector2();
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                movement.y += 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                movement.y -= 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                movement.x += 1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
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

            camera.translate(movement.x, movement.y);

            camera.position.x = Math.round(camera.position.x * 1000f) / 1000f;
            camera.position.y = Math.round(camera.position.y * 1000f) / 1000f;

            UpdPosition();
        }
    }

    void SetEquipment(ItemToolMaster tool)
    {
        this.tool = tool;
        mineSpeed = tool.getEfficiency();
    }

    private boolean checkCollision(boolean x, Vector2 movement, IVector2 tile)
    {
        if(WorldM.getTileCollision(tile.x, tile.y) != 2)
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
        // ignore if its not left mouse button or first touch pointer
        if (button != Input.Buttons.LEFT) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        if(tp.x < 0){ tp.x -= 1; } // -0.01 to -0.99 it will be raunded to 0 so whe have tow 0,0 positons
        if(tp.y < 0){ tp.y -= 1; } // -0.01 to -0.99 it will be raunded to 0 so whe have tow 0,0 positons
        mineTile = FMath.getTile(tp);
        if(WorldM.validTile(mineTile))
            mineing = true;
        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button)
    {
        if (button != Input.Buttons.LEFT) return false;
        mineing = false;
        return true;
    }

    @Override public boolean scrolled (int amount)
    {
        camera.zoom += amount * mouseWheelScale;
                if(camera.zoom < MINZOOM){ camera.zoom = MINZOOM; }
        if(camera.zoom > MAXZOOM){ camera.zoom = MAXZOOM; }
        return true;
    }

    private void UpdPosition(){ pos = new Vector2(camera.position.x - 0.5f, camera.position.y - 0.5f);}
    public Texture getPlayerTex() { return playerTex;}
    public Vector2 getPosition() { return pos;}
    public float getMineSpeed() { return mineSpeed; }

    @Override public boolean keyDown (int keycode){ return false;}
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
