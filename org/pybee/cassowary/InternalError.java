package org.pybee.cassowary;


public class InternalError extends Error
{
    public InternalError(String s) {
        description_ = s;
    }

    public String description()
    {
        return "InternalError: " + description_;
    }

    private String description_;
}
