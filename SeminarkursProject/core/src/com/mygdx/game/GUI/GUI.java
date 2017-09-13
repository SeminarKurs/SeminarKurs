package com.mygdx.game.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.WorldM.worldM;

/**
 * Created by 745379 on 23.08.2017.
 */

public class GUI {

    private Texture tex;


    public GUI()
    {
        tex = new Texture("ImgTest.png");
    }

    public void render (SpriteBatch sb) {
        sb.begin();

        sb.draw(tex, worldM.getCamPosition().x - worldM.getViewportWidth()/2, worldM.getCamPosition().y - worldM.getViewportHeight()/2, worldM.getViewportHeight() *2, worldM.getViewportWidth()/4f);
        sb.end();
    }
}


