package org.pybee.cassowary;


public class StayConstraint extends EditOrStayConstraint
{

    public StayConstraint(Variable var, Strength strength, double weight)
    {
        super(var, strength, weight);
    }

    public StayConstraint(Variable var, Strength strength)
    {
        super(var, strength, 1.0);
    }

    public StayConstraint(Variable var)
    {
        super(var, Strength.WEAK, 1.0);
    }

    public boolean isStayConstraint()
    {
        return true;
    }

    public String toString()
    {
        return "stay " + super.toString();
    }

}
