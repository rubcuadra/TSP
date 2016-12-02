package com.company;

/**
 * Created by Ruben on 11/22/16.
 */
public class City implements Comparable<City>
{
    private double x;
    private double y;
    private int cityNumber;

    City(int numCity,double x,double y)
    {
        cityNumber = numCity;
        this.x = x;
        this.y = y;
    }

    public static double distanceBetween(City one,City two)
    {
        //c = sqrt(a^2 + b^2);
        return Math.sqrt(Math.pow(one.getX()-two.getX(),2) + Math.pow(one.getY()-two.getY(),2));
    }

    public double getX() {return x;}
    public double getY() {return y;}

    public int getCityNumber() {return cityNumber;}

    @Override
    public String toString()
    {
        return "#"+cityNumber+" @ ("+x+","+y+")";
    }

    @Override
    public int compareTo(City o)
    {
        return cityNumber>o.getCityNumber()?1:
                cityNumber<o.getCityNumber()?-1:0;
    }
}
