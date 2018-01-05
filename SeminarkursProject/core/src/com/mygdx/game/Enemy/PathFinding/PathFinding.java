package com.mygdx.game.Enemy.PathFinding;

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
    IVector2 goal = new IVector2(5,5);
    IVector2 start = new IVector2(0,0);
    IVector2 fGoal;
    IVector2 fStart;
    IVector2 relativeNull;
    Cost[][] fields;

    public PathFinding()
    {
        //WorldM.getTileCollision(new IVector2(1,1));

        findPath();
    }

    private void findPath()
    {
        if(WorldM.getTileCollision(start) == Collision.collides)
        {
            System.out.println("start is coliding PathFinding");
        }

        fields = new Cost[Math.abs(goal.x- start.x)+1][Math.abs(goal.y- start.y)+1];
        relativeNull = new IVector2((goal.x < start.x) ? goal.x: start.x, (goal.y < start.y) ? goal.y: start.y);

        fGoal = fromWorld(goal);
        fStart = fromWorld(start);

        fields[fGoal.x][fGoal.y] = new Cost(0, new IVector2(fGoal),null);
        unused.add(fields[fGoal.x][fGoal.y]);
        Cost current;
        //unused.sort(new costComperator());
        costComperator cC =new costComperator();
        boolean foundGoal = false;

        do
        {
            current = unused.get(0);
            unused.remove(0);
            if(current.pos.equals(start))
                    return;
            finished.add(current);
            if(calculateAllAround(current))
            {
                foundGoal = true;
                System.out.println("foundGoal");
            }

            Collections.sort(unused, cC);

            print();

        }while(unused.size() > 0 && !foundGoal);

        print();

        //print the path backwards
        if(foundGoal)
        {
            Cost now = fields[fStart.x][fStart.y];
            System.out.println(now == null);
            while(now != null)
            {
                System.out.println(toWorld(now.pos).x +","+ toWorld(now.pos).y);
                now = now.parent;
            }
        }
    }

    private boolean calculateAllAround(Cost current)
    {
        int x= current.pos.x;
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

    public boolean updateCost(int cX, int cY, Cost current, float cost)
    {
        if(cX >= 0 && cY >= 0 && cX < fields.length && cY < fields[0].length && WorldM.getTileCollision(toWorld(cX,cY)) == Collision.none)
            if(fields[cX][cY] == null)
            {
                fields[cX][cY] = new Cost(calculateCost(current.moveCost + cost, cX,cY), new IVector2(cX, cY), current);
                fields[cX][cY].moveCost = current.moveCost + cost;
                unused.add(fields[cX][cY]);
            }
            else
            {
                if(!finished.contains(fields[cX][cY]) &&calculateCost(current.moveCost + cost, cX,cY) < fields[cX][cY].cost)
                {
                    fields[cX][cY].cost = calculateCost(current.moveCost + cost, cX,cY);
                    fields[cX][cY].moveCost = current.moveCost + cost;
                    fields[cX][cY].parent = current;
                }
            }
        if(cX == fStart.x && cY == fStart.y)
            return true;
        return false;
    }

    private float calculateCost(float cost, int x, int y)
    {
        int moveX = x - fStart.x;
        int moveY = y - fStart.y;
        cost += Math.sqrt(moveX*moveX + moveY*moveY);
        return cost;
    }


    public class costComperator implements Comparator<Cost>
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
                    if(fields[x][y].cost < 10) System.out.print("0"+(int) (fields[x][y].cost)+ " ");
                    else System.out.print((int) (fields[x][y].cost)+ " ");
                }
                else
                    System.out.print("nu ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
