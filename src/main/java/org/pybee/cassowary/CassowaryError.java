package org.pybee.cassowary;


public class CassowaryError extends Exception
{
    public String description()
    {
        return "An error has occured in Cassowary.";
    }
}
