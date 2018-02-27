package com.mygdx.game.Enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Enemy.PathFinding.PathFinding;
import com.mygdx.game.Types.Collision;
import com.mygdx.game.Types.FMath;
import com.mygdx.game.Types.IVector2;
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

    public int getImage() { return image; }

    public void findPath(IVector2 playerPosition)
    {
        path = pathFinding.findPath(FMath.getTile(position), playerPosition);
    }

    public void move(float dt, IVector2 playerPosition)
    {
        if(path != null && path.size > 1) {
            progress += dt;
            if (progress >= 1) {
                progress -= 1;
                if(WorldM.getTileCollision(path.get(1)) == Collision.none)
                {
                    position = path.get(1).toVector2();
                }
                else
                {
                    System.out.println("Enemy can't move to here!");
                    findPath(playerPosition);
                }

                path.removeIndex(1);
                System.out.println(path.size);


            }
        }
        else
        {
            System.out.println("Start finding");
            findPath(playerPosition);
        }
    }

    public Vector2 getPosition() { return position; }

}
