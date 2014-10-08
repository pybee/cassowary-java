package org.pybee.cassowary;


public class NonlinearExpression extends Error
{
    public String description()
    {
        return "The resulting expression would be nonlinear.";
    }
}
