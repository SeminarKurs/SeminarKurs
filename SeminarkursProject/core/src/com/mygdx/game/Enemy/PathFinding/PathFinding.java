package com.mygdx.game.Enemy.PathFinding;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Types.Collision;
import com.mygdx.game.Types.IVector2;
import com.mygdx.game.WorldM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Tobias on 16.12.2017.
 */


public class PathFinding {

    ArrayList<Cost> unused = new ArrayList<Cost>();
    ArrayList<Cost> finished = new ArrayList<Cost>();
    //IVector2 goal = new IVector2(15,15);
    //IVector2 start = new IVector2(5,5);
    IVector2 fGoal;
    IVector2 fStart;
    IVector2 relativeNull;
    Cost[][] fields;

    public PathFinding(){}

    public Array<IVector2> findPath(IVector2 start, IVector2 goal)
    {
        unused.clear();
        finished.clear();
        fields = null;

        if(WorldM.getTileCollision(start) == Collision.collides)
        {
            System.out.println("start is coliding PathFinding");
        }

        fields = new Cost[Math.abs(goal.x- start.x)+2][Math.abs(goal.y- start.y)+2];

        relativeNull = new IVector2((goal.x < start.x) ? goal.x-1: start.x-1, (goal.y < start.y) ? goal.y-1: start.y-1);

        fGoal = fromWorld(goal);
        fStart = fromWorld(start);

        // start the search from the goal so that we cant get the way backwords => it will be forward in the cost
        System.out.println("FGoal: "+fGoal.x+ " : " +fGoal.y);
        fields[fGoal.x][fGoal.y] = new Cost(0, new IVector2(fGoal),null);
        unused.add(fields[fGoal.x][fGoal.y]);
        Cost current;

        costComperator cC =new costComperator();
        boolean foundGoal = false;
        System.out.println("Currentpos: " + unused.get(0).pos.x + " : "+ unused.get(0).pos.y);
        print();

        do
        {
            current = unused.get(0);
            unused.remove(0);
            finished.add(current);
            System.out.println("Currentpos: " + current.pos.x + " : "+ current.pos.y);
            if(calculateAllAround(current))
            {
                foundGoal = true;
            }

            Collections.sort(unused, cC);

            print();

        }while(unused.size() > 0 && !foundGoal);

        //print the path backwards
        if(foundGoal)
        {
            Cost now = fields[fStart.x][fStart.y];
            Array<IVector2> path = new Array<IVector2>();
            while(now != null && !now.read)
            {
                path.add(toWorld(now.pos));
                //System.out.println(toWorld(now.pos).x +","+ toWorld(now.pos).y);
                now.read = true;
                now = now.parent;
            }

            if(now != null && now.read)
            {
                System.out.println("read ");
            }

            return path;
        }
        return null;
    }

    // returns ture if a way to start was found
    private boolean calculateAllAround(Cost current)
    {
        int x = current.pos.x;
        int y = current.pos.y;
        // curent position
        int cX = x-1;
        int cY = y+1;
        if(updateCost(cX, cY, current, 1.4f))
            return true;
        cX = x+1;
        cY = y-1;
        if(updateCost(cX, cY, current, 1.4f))
            return true;
        cX = x+1;
        cY = y+1;
        if(updateCost(cX, cY, current, 1.4f))
            return true;
        cX = x-1;
        cY = y-1;
        if(updateCost(cX, cY, current, 1.4f))
            return true;

        cX = x+1;
        cY = y;
        if(updateCost(cX, cY, current, 1f))
            return true;
        cX = x;
        cY = y+1;
        if(updateCost(cX, cY, current, 1f))
            return true;
        cX = x;
        cY = y-1;
        if(updateCost(cX, cY, current, 1f))
            return true;
        cX = x-1;
        cY = y;
        if(updateCost(cX, cY, current, 1f))
            return true;
        return false;
    }

    private boolean updateCost(int cX, int cY, Cost current, float cost)
    {
        if(cX >= 0 && cY >= 0 && cX < fields.length && cY < fields[0].length && WorldM.getTileCollision(toWorld(cX,cY)) == Collision.none)
        {
            if (fields[cX][cY] == null) {
                fields[cX][cY] = new Cost(calculateCost(current.moveCost + cost, cX, cY), new IVector2(cX, cY), current);
                unused.add(fields[cX][cY]);
            } else {
                if ((!finished.contains(fields[cX][cY])) || calculateCost(current.moveCost + cost, cX, cY) < fields[cX][cY].cost) {
                    //System.out.println("better");
                    fields[cX][cY].cost = calculateCost(current.moveCost + cost, cX, cY);
                    fields[cX][cY].moveCost = current.moveCost + cost;
                    fields[cX][cY].parent = current;
                }
            }
        } else if(cX >= 0 && cY >= 0 && cX < fields.length && cY < fields[0].length && WorldM.getTileCollision(toWorld(cX, cY)) == Collision.collides)
        {
            //fields[cX][cY] = new Cost(-1, new IVector2(cX, cY), current);
        }
        else
        {
            /*if(!(cX >= 0 && cY >= 0 && cX < fields.length && cY < fields[0].length))
                System.out.println("out of fields");
            System.out.println("null");*/
        }

        return cX == fStart.x && cY == fStart.y;
    }

    private float calculateCost(float cost, int x, int y)
    {
        int moveX = x - fStart.x;
        int moveY = y - fStart.y;
        cost += Math.sqrt(moveX*moveX + moveY*moveY);
        return cost;
    }

    private class costComperator implements Comparator<Cost>
    {
        @Override
        public int compare(Cost c0, Cost c1) {
            if(c0.cost > c1.cost)
                return 1;
            if(c0.cost < c1.cost)
                return -1;
            return 0;
        }
    }

    private IVector2 fromWorld(IVector2 pos)
    {
        return new IVector2(pos.x -relativeNull.x, pos.y -relativeNull.y);
    }
    private IVector2 fromWorld(int x, int y)
    {
        return new IVector2(x -relativeNull.x, y -relativeNull.y);
    }

    private IVector2 toWorld(IVector2 pos)
    {
        return new IVector2(pos.x + relativeNull.x, pos.y + relativeNull.y);
    }
    private IVector2 toWorld(int x, int y)
    {
        return new IVector2(x + relativeNull.x, y + relativeNull.y);
    }

    private void print()
    {
        for(int y =fields[0].length-1; y >=0; y--)
        {
            for(int x=0; x < fields.length; x++)
            {
                if(fields[x][y] != null)
                {
                    /*if(fields[x][y].cost == -1)
                    {
                        System.out.println("ob ");
                    } else */
                    if(fields[x][y].cost < 10)
                    {
                        System.out.print("0" + (int) (fields[x][y].cost) + " ");
                    } else System.out.print((int) (fields[x][y].cost)+ " ");
                }
                else
                    System.out.print("nu ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
