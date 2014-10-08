package org.pybee.cassowary;

import java.util.*;


public class Variable extends AbstractVariable
{

    private static Hashtable _ourVarMap;

    private double _value;

    private Object _attachedObject;


    public Variable(String name, double value)
    {
        super(name);
        _value = value;
        if (_ourVarMap != null)
        {
            _ourVarMap.put(name,this);
        }
    }

    public Variable(String name)
    {
        super(name);
        _value = 0.0;
        if (_ourVarMap != null)
        {
            _ourVarMap.put(name,this);
        }
    }

    public Variable(double value)
    {
        _value = value;
    }

    public Variable()
    {
        _value = 0.0;
    }


    public Variable(long number, String prefix, double value)
    {
        super(number,prefix);
        _value = value;
    }

    public Variable(long number, String prefix)
    {
        super(number,prefix);
        _value = 0.0;
    }

    public boolean isDummy()
    {
        return false;
    }

    public boolean isExternal()
    {
        return true;
    }

    public boolean isPivotable()
    {
        return false;
    }

    public boolean isRestricted()
    {
        return false;
    }

    public String toString()
    {
        return "[" + name() + ":" + _value + "]";
    }

    // change the value held -- should *not* use this if the variable is
    // in a solver -- instead use addEditVar() and suggestValue() interface
    public final double value()
    {
        return _value;
    }

    public final void set_value(double value)
    {
        _value = value;
    }

    // permit overriding in subclasses in case something needs to be
    // done when the value is changed by the solver
    // may be called when the value hasn't actually changed -- just
    // means the solver is setting the external variable
    public void change_value(double value)
    {
        _value = value;
    }

    public void setAttachedObject(Object o)
    {
        _attachedObject = o;
    }

    public Object getAttachedObject()
    {
        return _attachedObject;
    }

    public static void setVarMap(Hashtable map)
    {
        _ourVarMap = map;
    }

    public static Hashtable getVarMap()
    {
        return _ourVarMap;
    }

}
