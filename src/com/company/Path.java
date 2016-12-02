package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruben on 11/23/16.
 */
public class Path implements Comparable<Path>
{
    private List<City> cities;
    private Double cost;

    public Path()
    {
        cities = new ArrayList<>();
        cost = 0.0;
    }

    public void add(City n,double cost)
    {
        cities.add(n);
        this.cost+=cost;
    }

    public void join(Path other)
    {
        this.cities.addAll( other.getCities() );
        this.cost+=other.getCost();
    }
    public Double getCost() {return cost;}
    public List<City> getCities(){return cities;}

    @Override
    public int compareTo(Path o)
    {
        return this.cost>o.getCost()?1:this.cost==o.getCost()?0:-1;
    }
    @Override
    public String toString()
    {
        String msg = "";
        for (City c: cities)
            msg+=c.toString()+"\n";
        return msg+"Cost: "+this.cost+"\n";
    }
}
