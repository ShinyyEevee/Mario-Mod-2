package io.github.Theray070696.mario2.util;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Theray070696 on 8/25/2017
 */
public class WeightedList<T extends IWeightProvider> extends ArrayList<T>
{
    private static final long serialVersionUID = 3287467180927507124L;

    private int totalWeight = 0;

    @Override
    public boolean add(T obj)
    {
        boolean b = super.add(obj);
        recalculateWeight();
        return b;
    }

    @Override
    public T remove(int index)
    {
        T is = super.remove(index);
        recalculateWeight();
        return is;
    }

    @Override
    public boolean remove(Object o)
    {
        boolean b = super.remove(o);
        recalculateWeight();
        return b;
    }

    public void recalculateWeight()
    {
        totalWeight = 0;
        for(T obj : this)
        {
            totalWeight += obj.getWeight();
        }
    }

    public int getTotalWeight()
    {
        return totalWeight;
    }

    public T getRandomItem(Random rand)
    {
        if(totalWeight == 0)
        {
            return null;
        }
        int i = rand.nextInt(totalWeight);

        for(T obj : this)
        {
            i -= obj.getWeight();
            if(i < 0)
            {
                return obj;
            }
        }
        return null;
    }

    public T getRandomItem(int weight)
    {
        if(totalWeight == 0)
        {
            return null;
        }

        for(T obj : this)
        {
            weight -= obj.getWeight();
            if(weight < 0)
            {
                return obj;
            }
        }
        return null;
    }
}
