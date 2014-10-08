package org.pybee.cassowary;


public class NotEnoughStays extends Error
{
    public String description()
    {
        return "There are not enough stays to give specific values to every variable.";
    }
}
