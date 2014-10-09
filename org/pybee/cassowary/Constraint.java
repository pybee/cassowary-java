package org.pybee.cassowary;

import java.lang.*;


public abstract class Constraint
{
    private Strength _strength;
    private double _weight;

    private Object _attachedObject;

    public Constraint(Strength strength, double weight)
    {
        _strength = strength;
        _weight = weight;
    }

    public Constraint(Strength strength)
    {
        _strength = strength;
        _weight = 1.0;
    }

    public Constraint()
    {
        _strength = Strength.REQUIRED;
        _weight = 1.0;
    }

    public abstract Expression expression();

    public boolean isEditConstraint()
    {
        return false;
    }

    public boolean isInequality()
    {
        return false;
    }

    public boolean isRequired()
    {
        return _strength == Strength.REQUIRED;
    }

    public boolean isStayConstraint()
    {
        return false;
    }

    public Strength strength()
    {
        return _strength;
    }

    public double weight()
    {
        return _weight;
    }

    public String toString()
    {
        return _strength + " {" + weight() + "} (" + expression();
    }

    public void setAttachedObject(Object o)
    {
        _attachedObject = o;
    }

    public Object getAttachedObject()
    {
        return _attachedObject;
    }

    private void setStrength(Strength strength)
    {
        _strength = strength;
    }

    private void setWeight(double weight)
    {
        _weight = weight;
    }

}
