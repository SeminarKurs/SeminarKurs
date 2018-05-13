package com.mygdx.game.Enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Enemy.PathFinding.PathFinding;
import com.mygdx.game.Player.PlayerController;
import com.mygdx.game.Tools.Collision;
import com.mygdx.game.Tools.FMath;
import com.mygdx.game.Tools.IVector2;
import com.mygdx.game.WorldM;

/**
 * Created by Tobias on 17.12.2017.
 */

public class Enemy {
    public final static float ENEMYSIZEHX = com.mygdx.game.Player.PlayerController.PlSIZEHX;
    public final static float ENEMYSIZEHY = com.mygdx.game.Player.PlayerController.PlSIZEHY;

    private PathFinding pathFinding = new PathFinding();
    private Array<IVector2> path;
    private float progress = 0;

    private int image;

    private Vector2 position = new Vector2(2f,2f);
    private Vector2 relPos = new Vector2();
    public int getImage() { return image; }

    public void findPath(IVector2 playerPosition)
    {
        IVector2 enemyIVector = FMath.getTile(position);
        if(enemyIVector.x != playerPosition.x || enemyIVector.y != playerPosition.y)
        {
            path = pathFinding.findPath(enemyIVector, playerPosition);
        }
        else
        {
            path = new Array<IVector2>();
            path.add(new IVector2(playerPosition));
        }
    }

    public void move(float dt, IVector2 playerPosition)
    {
        float xDif = position.x - playerPosition.x;
        float yDif = position.y - playerPosition.y;
        if(path != null && path.size > 1 && Math.sqrt(xDif*xDif + yDif*yDif) < 10)
        {
            Vector2 moveVect = new Vector2(path.get(1).x - path.get(0).x, path.get(1).y - path.get(0).y);
            //sssSystem.out.println("1x: "+ path.get(1).x + " 1y: " + path.get(1).y + " 0x: " + path.get(0).x + " 0y: " + path.get(0).y);
            IVector2 playerPosI = FMath.getTile(PlayerController.getPosition());
            IVector2 enemyPOsI = FMath.getTile(position.x + relPos.x, position.y + relPos.y);
            if(enemyPOsI.x == playerPosI.x && enemyPOsI.y == playerPosI.y)
            {
                PlayerController.kill();
            }

            progress += dt;
            if (progress >= moveVect.len())
            {
                relPos = new Vector2(0,0);
                progress -= moveVect.len();
                if(WorldM.getTileCollision(path.get(1)) == Collision.none)
                {
                    position = path.get(1).toVector2();
                }
                else
                {
                    findPath(playerPosition);
                }
                path.removeIndex(0);
            }
            else
            {
                float movVecLen = moveVect.len();
                //System.out.println("moveVect length " + moveVect.len());
                relPos = new Vector2(moveVect.x * progress / movVecLen, moveVect.y * progress / movVecLen);
            }
        }
        else
        {
            findPath(playerPosition);
        }
    }

    public Vector2 getPosition() { return new Vector2(position.x + relPos.x, position.y + relPos.y); }

}
