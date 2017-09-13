package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.Types.IVector2;

import static com.mygdx.game.WorldM.worldM;

/**
 * Created by 745379 on 28.08.2017.
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
    public final static float HIGHTH = 1;
    public final static float WIDTHH = 0.5f;




    private Texture playerTex;
    private Vector2 position = new Vector2();

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private ShapeRenderer shapes;
    private float mWScale = 1;
    // speed that the cam player moves (units per second)
    private float speed = 2f;

    public PlayerController(OrthographicCamera camera)
    {
        this.camera = camera;
        playerTex = new Texture("ImgTest.png");
    }

    @Override public void create () { Gdx.input.setInputProcessor(this);}

    @Override public void render (){

    }

    public void tick(float dt)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
        {
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

            movement.nor(); // nor vector for same speed every dircetion
            movement.scl(speed * dt); // make it (speed*dt) times longer
            WorldM.worldM.playerCollides(movement);
            camera.translate(movement.x, movement.y);
            UpdPosition();

            checkCollision();

        }
    }

    private void checkCollision()
    {
        Vector2 plSizeH = new Vector2(0.1f,0.1f);
        Vector2 plPos = new Vector2(10.5f, 10.5f);
        Vector2 movement = new Vector2(1f,0.5f);
        boolean isCollision = true;

        IVector2 tilePo = new IVector2((int) plPos.x, (int) plPos.y);
        IVector2 checkTile = new IVector2(tilePo.x, tilePo.y);

        checkTile.x += 1;

        collides(plPos, movement, plSizeH, checkTile);
        checkTile.x -= 2;
        System.out.println(":");
    }

    void collides(Vector2 plPos, Vector2 movement, Vector2 plSizeH, IVector2 checkTile)
    {
        float gap;
        gap = Math.abs(plPos.x - checkTile.x) - plSizeH.x;
        if(makePos(movement.x) > gap)
        {
            System.out.println("collides");
        }
        gap = Math.abs(plPos.y - checkTile.y) - plSizeH.y;
        if(makePos(movement.y) > gap)
        {
            System.out.println("collides");
        }
        System.out.println("");

    }

    float makePos(float f)
    {
        if(f < 0)return -f;
        return f;
    }



    Vector3 tp = new Vector3();
    boolean dragging;
    @Override public boolean mouseMoved (int screenX, int screenY)
    {
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button)
    {
        // ignore if its not left mouse button or first touch pointer
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        if(tp.x < 0){ tp.x -= 1; } // -0.01 to -0.99 it will be raunded to 0 so whe have tow 0,0 positons
        if(tp.y < 0){ tp.y -= 1; } // -0.01 to -0.99 it will be raunded to 0 so whe have tow 0,0 positons
        IVector2 position = new IVector2((int)(tp.x), (int)(tp.y));

        worldM.tileClicked(position);
        dragging = true;
        return true;
    }

    @Override public boolean touchDragged (int screenX, int screenY, int pointer)
    {
        if (!dragging) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button)
    {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        dragging = false;
        return true;
    }

    @Override public void resize (int width, int height)
    {
        // viewport must be updated for it to work properly
        viewport.update(width, height, true);
    }

    @Override public void dispose ()
    {
        // disposable stuff must be disposed
        shapes.dispose();
    }

    @Override public boolean scrolled (int amount)
    {
        camera.zoom += amount * mWScale;
                if(camera.zoom < MINZOOM){ camera.zoom = MINZOOM; }
        if(camera.zoom > MAXZOOM){ camera.zoom = MAXZOOM; }
        return true;
    }

    @Override public boolean keyDown (int keycode){ return false;}

    public void UpdPosition(){position = new Vector2(camera.position.x - 0.5f, camera.position.y - 0.5f);}
    public Texture getPlayerTex() { return playerTex;}
    public Vector2 getPosition() { return position;}

    @Override public boolean keyUp (int keycode) {
        return false;
    }

    @Override public boolean keyTyped (char character) {
        return false;
    }



}
