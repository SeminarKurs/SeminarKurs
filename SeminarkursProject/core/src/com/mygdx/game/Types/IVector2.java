package com.mygdx.game.Types;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by 745379 on 30.08.2017.
 */

public class IVector2 {
    public int x;
    public int y;

    // default constructor
    public IVector2(){}
    // constructor sets the value of x and y
    public IVector2(int x, int y){ this.x = x; this.y = y; }
    public IVector2(IVector2 cop){ this.set(cop);}
    public IVector2(Vector2 pos){ x = (int)pos.x; y = (int)pos.y;}

    public void set(IVector2 cop){ this.x = cop.x; this.y = cop.y;}
    public void set(int x, int y){this.x = x; this.y = y;}
    public IVector2 setNew(IVector2 cop){ return new IVector2(cop);}
    public IVector2 setNew(int x, int y){return new IVector2(x,y);}

    public IVector2 addNew(int x, int y){return new IVector2(this.x+x, this.y+y);}
    public Vector2 toVector2(){return new Vector2(x,y);}
}
