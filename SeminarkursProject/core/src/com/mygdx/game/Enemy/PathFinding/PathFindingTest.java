package com.mygdx.game.Enemy.PathFinding;

import com.mygdx.game.Types.IVector2;
import com.mygdx.game.WorldM;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Tobias on 16.12.2017.
 */




public class PathFindingTest {

    ArrayList<Cost> unused = new ArrayList<Cost>();
    ArrayList<Cost> finished = new ArrayList<Cost>();
    IVector2 start = new IVector2(11,112);
    IVector2 goal = new IVector2(10,10);
    IVector2 relativeNull;
    Cost[][] fields;

    public PathFindingTest()
    {
        WorldM.getTileCollision(new IVector2(1,1));
        unused.add(new Cost(0,new IVector2(1,1)));

        findPath();
    }

    private void findPath()
    {
        fields = new Cost[Math.abs(start.x-goal.x)][Math.abs(start.y-goal.y)];
        relativeNull = new IVector2((start.x < goal.x) ? start.x:goal.x, (start.y < goal.y) ? start.y:goal.y);
        
        Cost current = unused.get(0);
        /*do
        {
            current = unused.get(0);
            unused.remove(0);
            if(current.pos.equals(goal))
                    return;
            finished.add(current);

        }while(true);*/

        //WorldM.addActor(new Actor(), new IVector2(1,2));
    }

    public void Calculate(float wert, int x, int y)
    {
        //if(finished.contains())
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


}
