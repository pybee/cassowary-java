package org.pybee.cassowary;


public class TooDifficult extends Error
{
    public String description()
    {
        return "The constraints are too difficult to solve.";
    }
}
