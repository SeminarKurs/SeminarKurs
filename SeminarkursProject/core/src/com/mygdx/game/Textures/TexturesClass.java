package com.mygdx.game.Textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Tobias on 22.10.2017.
 */

public class TexturesClass {

    //textures from the gound
    protected static Array<Texture> texturesActor = new Array<Texture>();
    //texture from the actors
    protected static Array<Texture> texturesGround = new Array<Texture>();
    //texture from the actors
    protected static Array<Texture> texturesResource = new Array<Texture>();
    //texture from the item that can be place in the world
    protected static Array<Texture> texturesItem = new Array<Texture>();

    public TexturesClass() {
        // add all textures
        // ground texture
        //texturesGround.add(new Texture("badlogic.jpg"));
        //texturesGround.add(new Texture("Black.png"));
        //texturesGround.add(new Texture("Blue.png"));
        texturesGround.add(new Texture("Test.png"));
        texturesGround.add(new Texture("Test.png"));
        //texturesGround.add(new Texture("Ground3.png"));
        //texturesGround.add(new Texture("Ground4.png"));


        //texturesGround.add(new Texture("Red.png"));
        // actor Textures
        texturesActor.add(new Texture("Green.png"));
        texturesActor.add(new Texture("Conveyor.png"));
        texturesActor.add(new Texture("Miner.png"));
        // resource textures
        texturesResource.add(new Texture("Cole.png"));
        texturesResource.add(new Texture("Tree.png"));

        // item textures
        texturesItem.add(new Texture("Cole.png"));

    }

    // gets the texture by num
    static public Texture getTextureActor(int num){if(num < texturesActor.size)return texturesActor.get(num); return null;}
    static public Texture getTextureGround(int num){if(num < texturesGround.size)return texturesGround.get(num); return null;}
    static public Texture getTextureResource(int num){if(num < texturesResource.size)return texturesResource.get(num); return null;}
    static public Texture getTextureItem(int num){if(num < texturesItem.size)return texturesItem.get(num); return null;}
    static public int getTexturesGroundLenght(){return texturesGround.size;}
    static public int getTexturesResourceLenght(){return texturesResource.size;}

    public void dispose() {
        for(int i=0; i < texturesGround.size; i++)
            texturesGround.get(i).dispose();
        for(int i=0; i < texturesActor.size; i++)
            texturesActor.get(i).dispose();
        for(int i=0; i < texturesResource.size; i++)
            texturesResource.get(i).dispose();
        for(int i = 0; i < texturesItem.size; i++)
            texturesItem.get(i).dispose();
    }
}

