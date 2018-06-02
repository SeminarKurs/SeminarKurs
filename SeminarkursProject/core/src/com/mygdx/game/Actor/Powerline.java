package com.mygdx.game.Actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Item.ItemId;
import com.mygdx.game.Textures.TexturesClass;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

import java.awt.Image;

/**
 * Created by Christopher Schleppe on 05.01.2018.
 */

public class Powerline extends ElectricActor {

    private Direction direction;
    private float progress = -1;

    public Powerline(IVector2 pos, Direction direction) {
        this.pos = pos;
        this.direction = direction;
        maxCapacity = 1;
    }

    public void update (float dt){
        if(capacity > 0) {
            progress += dt / 2;
            if (progress >= 0) {
                if(movePowerToElectricActor()){
                    progress = -1;
                }else   progress = 0;
            }
        }
    }

    @Override
    public void draw(Batch batch, int x, int y, Array<FLayer> fLayers) {
        DrawH.drawActorRot(batch, x,y, direction, image());
        if(capacity > 0) {
            switch (direction) {
                case left:
                    fLayers.add(new FLayer(x - progress, y, 1));
                    break;
                case right:
                    fLayers.add(new FLayer(x + progress, y, 1));
                    break;
                case up:
                    fLayers.add(new FLayer(x, y + progress, 1));
                    break;
                case down:
                    fLayers.add(new FLayer(x, y - progress, 1));
                    break;
            }
        }
    }

    @Override
    public boolean movePowerToElectricActor(){
        ElectricActor actor = (ElectricActor) checkForNearActor();
        if(actor != null){
            actor.addCapacity(1);
            capacity--;
            return true;
        }
        return false;
    }
    private Actor assistingMethodForCheckForNearActor(IVector2 pos){
        Actor actor = WorldM.getActor(pos);
        if(actor != null) {
            if (actor.getId() == ItemId.ELECTRICOVEN || actor.getId() == ItemId.POWERLINE)
                return actor;
        }
        return null;
    }

    public Actor checkForNearActor(){
        switch (direction){
            case left:
                return assistingMethodForCheckForNearActor(new IVector2(pos.x -  1, pos.y));
            case right:
                return assistingMethodForCheckForNearActor(new IVector2(pos.x + 1, pos.y));
            case up:
                return assistingMethodForCheckForNearActor(new IVector2(pos.x, pos.y + 1));
            case down:
                return assistingMethodForCheckForNearActor(new IVector2(pos.x, pos.y - 1));
        }
        return null;
    }

    @Override
    public Collision coll() {return Collision.none;}
    public int image(){return 6;}

    @Override
    public boolean needUpdate(){ return true;}
    public ItemId getId() {
        return ItemId.POWERLINE;
    }

}
