package org.pybee.cassowary;


public class RequiredFailure extends Error
{
    public String description()
    {
        return "A required constraint cannot be satisfied.";
    }
}
