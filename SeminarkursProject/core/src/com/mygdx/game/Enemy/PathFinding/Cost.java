package com.mygdx.game.Enemy.PathFinding;

import com.mygdx.game.Types.IVector2;

/**
 * Created by Tobias on 17.12.2017.
 */

class Cost {

    public Cost(int valueCost, IVector2 pos)
    {
        cost = valueCost;
    }

    public float cost;

    public IVector2 pos;
    public Cost parent;
}
