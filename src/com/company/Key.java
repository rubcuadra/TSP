package com.company;

import java.util.List;
import java.util.Set;

/**
 * Created by Ruben on 11/28/16.
 */
public class Key
{
    protected City toCity;
    protected Set<City> passThru;

    Key(City to, Set<City> pass)
    {
        this.toCity = to;
        this.passThru = pass;
    }
}
